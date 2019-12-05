package com.fxbank.tpp.manager.quartz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dcfs.esb.ftp.client.FtpClientConfigSet;
import com.dcfs.esb.ftp.client.FtpGet;
import com.dcfs.esb.ftp.server.error.FtpException;
import com.fxbank.tpp.esb.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.pub.service.IPublicService;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmAcctCheckErrModel;
import com.fxbank.tpp.bocm.model.BocmChkStatusModel;
import com.fxbank.tpp.bocm.model.BocmDayCheckLogInitModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceUpdModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceUpdModel;
import com.fxbank.tpp.bocm.service.IBocmAcctCheckErrService;
import com.fxbank.tpp.bocm.service.IBocmChkStatusService;
import com.fxbank.tpp.bocm.service.IBocmDayCheckLogService;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.bocm.service.IBocmSndTraceService;
import com.fxbank.tpp.esb.model.ses.ESB_REP_50015000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_50015000101;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.esb.service.IForwardToTownService;

import redis.clients.jedis.Jedis;

/** 
* @ClassName: BocmHostCheckAcctTasK 
* @Description: 渠道与核心对账定时任务
* @author YePuLiang
* @date 2019年5月24日 上午9:52:41 
*  
*/

//@Configuration
//@Component
//@EnableScheduling
public class BocmHostCheckAcctTasK {
	
	private static Logger logger = LoggerFactory.getLogger(BocmHostCheckAcctTasK.class);

	private static final String JOBNAME = "BocmHostCheck";

	@Reference(version = "1.0.0")
	private IPublicService publicService;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IForwardToTownService forwardToTownService;
	
	@Reference(version = "1.0.0")
	private IBocmDayCheckLogService dayCheckLogService;
	
	@Reference(version = "1.0.0")
	private IBocmSndTraceService sndTraceService;
	
	@Reference(version = "1.0.0")
	private IBocmRcvTraceService rcvTraceService;

	@Reference(version = "1.0.0")
	private IBocmAcctCheckErrService acctCheckErrService;
	
	@Reference(version = "1.0.0")
	private IBocmChkStatusService chkStatusService;
	
	@Resource
	private MyJedis myJedis;

	private final static String COMMON_PREFIX = "bocm.";
	
	public void exec() throws Exception {
		MyLog myLog = new MyLog();
		
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try(Jedis jedis = myJedis.connect()){
			txBrno = jedis.get(COMMON_PREFIX+"TXBRNO");
			txTel = jedis.get(COMMON_PREFIX+"TXTEL");
        }

		String settlementDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		myLog.info(logger, "对账日期："+settlementDate);
		Integer date = Integer.parseInt(settlementDate);
		Integer sysTime = publicService.getSysTime();
		Integer sysTraceno = publicService.getSysTraceno();
		
		BocmChkStatusModel chkModel = chkStatusService.selectByDate(date.toString());
		if (chkModel == null) {
			BocmChkStatusModel record = new BocmChkStatusModel();
			record.setTxDate(date);
			record.setTxBranch(txBrno);
			record.setTxTel(txTel);
			chkStatusService.chkStatusInit(record);
			chkModel = chkStatusService.selectByDate(date.toString());
		}
		//2019-09-24 判断与核心对账状态如果成功则退出
		myLog.info(logger, "检索核心对账状态");
		if(chkModel.getHostStatus()==1){
			myLog.info(logger, "渠道与核心已对账,跳出定时任务");
			return;
		}
		
		myLog.info(logger, "核心对账状态： " + chkModel.getHostStatus() + " 交行对账状态：  " + chkModel.getBocmStatus());
		myLog.info(logger, "核心与外围对账开始");
		// 删除对账错误日志
		acctCheckErrService.delete(date.toString());

		// 获取核心记账流水列表
		List<BocmDayCheckLogInitModel> dayCheckLogList = getCheckLogList(myLog, date, sysTime, sysTraceno, txBrno,
				txTel);
		// 核对来账
		myLog.info(logger, "核对来账流水");
		checkRcvLog(myLog, dayCheckLogList, date, sysTime, sysTraceno, txTel);
		// 核对往帐
		myLog.info(logger, "核对往账流水");
		checkSndLog(myLog, dayCheckLogList, date, sysTime, sysTraceno, txTel);
		// 核对来账、核对往账原则是以本行为主的更新记账状态和对账状态，以交行为主的对账只更新核心记账状态，不更新对账标识
		myLog.info(logger, "核心与外围对账结束");
		myLog.info(logger, "外围与核心对账开始");

		// 获取未对账的往帐信息
		List<BocmSndTraceQueryModel> sndTraceList = sndTraceService.getCheckSndTrace(myLog, date, sysTime, sysTraceno,
				date.toString());
		myLog.info(logger, "往账未对账记录数【" + sndTraceList.size() + "】");
		for (BocmSndTraceQueryModel sndTraceQueryModel : sndTraceList) {
			BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(),
					sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
			// 如果往账交易类型是交行卡转本行不更新对账状态
			if (sndTraceQueryModel.getTranType().equals("JH11")) {
				continue;
			}

			if (sndTraceQueryModel.getHostState().equals("1")) {
				String msg = "对账失败，渠道多账";
				initSndErrRecord(myLog, sndTraceQueryModel, msg, "1" ,"0", date, sysTime, txTel);
				myLog.error(logger,
						"渠道多出往账数据，渠道日期【" + date + "】对账失败: 多出往账记录，渠道流水号【" + sndTraceQueryModel.getPlatTrace() + "】，核心状态【"
								+ sndTraceQueryModel.getHostState() + "】，通存通兑标志【" + sndTraceQueryModel.getDcFlag()
								+ "】");
				BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013,
						"与核心往账对账失败,渠道多账,渠道流水号【" + sndTraceQueryModel.getPlatTrace() + "】");
				
				//更新对账状态
				BocmChkStatusModel chkStatusModel = new BocmChkStatusModel();
				chkStatusModel.setTxDate(date);
				chkStatusModel.setHostStatus(2);
				chkStatusModel.setChkTime(sysTime);
				chkStatusModel.setTxTel(txTel);
				chkStatusService.chkStatusUpd(chkStatusModel);
				
				throw e;
			} else {
				String msg = "渠道多账";
				initSndErrRecord(myLog, sndTraceQueryModel, msg, "1" ,"0", date, sysTime, txTel);
				myLog.info(logger,
						"渠道多出往账数据，渠道日期【" + sndTraceQueryModel.getPlatDate() + "】，渠道流水【"
								+ sndTraceQueryModel.getPlatTrace() + "】，核心状态【" + sndTraceQueryModel.getHostState()
								+ "】，通存通兑标志【" + sndTraceQueryModel.getDcFlag() + "】");
				record.setCheckFlag("4");
				myLog.error(logger, "更新往账表对账状态：渠道流水号【" + sndTraceQueryModel.getPlatTrace() + "】");
				sndTraceService.sndTraceUpd(record);
			}
		}

		// 获取未对账的来账信息,核心无记录的数据
		List<BocmRcvTraceQueryModel> rcvTraceList = rcvTraceService.getCheckRcvTrace(myLog, date, sysTime, sysTraceno,
				date.toString());
		myLog.info(logger, "来账未对账记录数【" + rcvTraceList.size() + "】");
		for (BocmRcvTraceQueryModel rcvTraceQueryModel : rcvTraceList) {
			BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(),
					rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
			// 如果来账交易类型是他代本通存和他代本现金通兑,不更新对账状态
			if (rcvTraceQueryModel.getTranType().equals("JH02")
					|| (rcvTraceQueryModel.getTranType().equals("JH01") && rcvTraceQueryModel.getTxInd().equals("0"))) {
				continue;
			}
			if (rcvTraceQueryModel.getHostState().equals("1")) {
				String msg = "对账失败，渠道多账";
				initRcvErrRecord(myLog, rcvTraceQueryModel, msg, "1" ,"0",date, sysTime, txTel);
				myLog.error(logger,
						"柜面通【" + date + "】对账失败: 多出来账记录，渠道流水号【" + rcvTraceQueryModel.getPlatTrace() + "】，核心状态【"
								+ rcvTraceQueryModel.getHostState() + "】，通存通兑标志【" + rcvTraceQueryModel.getDcFlag()
								+ "】");
				BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013
						,"与核心来账对账失败,渠道多账,渠道流水【" + rcvTraceQueryModel.getPlatTrace() + "】");
				
				//更新对账状态
				BocmChkStatusModel chkStatusModel = new BocmChkStatusModel();
				chkStatusModel.setTxDate(date);
				chkStatusModel.setHostStatus(2);
				chkStatusModel.setChkTime(sysTime);
				chkStatusModel.setTxTel(txTel);
				chkStatusService.chkStatusUpd(chkStatusModel);
				
				throw e;
			} else {
				String msg = "渠道多账";
				initRcvErrRecord(myLog, rcvTraceQueryModel, msg, "0" ,"0", date, sysTime, txTel);
				myLog.info(logger,
						"渠道多出来账数据，渠道日期【" + rcvTraceQueryModel.getPlatDate() + "】，渠道流水【"
								+ rcvTraceQueryModel.getPlatTrace() + "】，核心状态【" + rcvTraceQueryModel.getHostState()
								+ "】，通存通兑标志【" + rcvTraceQueryModel.getDcFlag() + "】");
				record.setCheckFlag("4");
				rcvTraceService.rcvTraceUpd(record);
			}
		}

		myLog.info(logger, "外围与核心对账结束");

		String check_date = String.valueOf(date);
		// 来账对账统计
		String rcvCheckFlag2 = rcvTraceService.getTraceNum(check_date, "2");
		String rcvCheckFlag3 = rcvTraceService.getTraceNum(check_date, "3");
		String rcvCheckFlag4 = rcvTraceService.getTraceNum(check_date, "4");
		int rcvTotal = Integer.parseInt(rcvCheckFlag2) + Integer.parseInt(rcvCheckFlag3);
		myLog.info(logger, "来账对账记录数【已对账】【" + rcvCheckFlag2 + "】");
		myLog.info(logger, "来账对账记录数【核心多】【" + rcvCheckFlag3 + "】");
		myLog.info(logger, "来账对账记录数【渠道多】【" + rcvCheckFlag4 + "】");
		// 往账对账统计
		String sndCheckFlag2 = sndTraceService.getTraceNum(check_date, "2");
		String sndCheckFlag3 = sndTraceService.getTraceNum(check_date, "3");
		String sndCheckFlag4 = sndTraceService.getTraceNum(check_date, "4");
		int sndTotal = Integer.parseInt(sndCheckFlag2) + Integer.parseInt(sndCheckFlag3);
		myLog.info(logger, "往账对账记录数【已对账】【" + sndCheckFlag2 + "】");
		myLog.info(logger, "往账对账记录数【核心多】【" + sndCheckFlag3 + "】");
		myLog.info(logger, "往账对账记录数【渠道多】【" + sndCheckFlag4 + "】");
		// 对账总数
		int tolCnt = rcvTotal + sndTotal;
		myLog.info(logger, "返回对账交易数量【" + tolCnt + "】");

		String rcvTotalAmt = rcvTraceService.getRcvTotalChkSum(myLog, check_date);
		if (rcvTotalAmt == null) {
			rcvTotalAmt = "0.00";
		}
		String sndTotalAmt = sndTraceService.getSndTotalChkSum(myLog, check_date);
		if (sndTotalAmt == null) {
			sndTotalAmt = "0.00";
		}
		myLog.info(logger, "来账总金额【" + rcvTotalAmt + "】");
		myLog.info(logger, "往账总金额【" + sndTotalAmt + "】");
		BigDecimal totalAmt = new BigDecimal(rcvTotalAmt).add(new BigDecimal(sndTotalAmt));

		// 更新核心对账状态
		BocmChkStatusModel record = new BocmChkStatusModel();
		record.setTxDate(date);
		record.setHostStatus(1);
		record.setHostTxCnt(tolCnt);
		record.setHostTxAmt(totalAmt);
		chkStatusService.chkStatusUpd(record);
		myLog.info(logger, "更新与核心对账状态为已对账：  对账日期：" + date);

	}

	// 核对往账
	private void checkSndLog(MyLog myLog, List<BocmDayCheckLogInitModel> sndDayCheckLogList, Integer date,
			Integer sysTime, Integer sysTraceno, String txTel) throws SysTradeExecuteException {
		for (BocmDayCheckLogInitModel model : sndDayCheckLogList) {
			// JH01交行代理本行存款 JH02 交行代理我行取款 如果判断为下面的类型为来账，跳过
			if (model.getTranType().equals("JH01") || model.getTranType().equals("JH02")) {
				continue;
			}
			// 根据核心对账数据取渠道往账数据
			BocmSndTraceQueryModel sndTraceQueryModel = sndTraceService.getSndTraceByKey(myLog, sysTime, sysTraceno,
					date, model.getSettleDate(), model.getPlatTrace());
			// 若渠道缺少数据则补充,尽量渠道不少数据
			if (sndTraceQueryModel == null) {
				BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, model.getSettleDate(),
						model.getSysTime(), model.getPlatTrace());
				aceModel.setPlatDate(model.getSettleDate());
				aceModel.setPlatTrace(model.getPlatTrace());
				aceModel.setPreHostState("");
				aceModel.setReHostState("1");
				aceModel.setDcFlag("");
				aceModel.setCheckFlag("3");
				aceModel.setDirection("O");
				aceModel.setTxAmt(model.getTxAmt());			
				aceModel.setTxCode(model.getTranType());
				aceModel.setTxSource("MT");
				aceModel.setTxDate(date);
				aceModel.setHostDate(model.getHostDate());
				aceModel.setHostTraceno(model.getHostTraceno());
				aceModel.setHostFlag("2");
				aceModel.setBocmFlag("0");
				if (model.getTranType().equals("JH11")){
					aceModel.setCheckFlag("以交行对账为准");
				}else{
					aceModel.setCheckFlag("以我行对账为准");
				}
				
				aceModel.setMsg("对账失败,渠道少往账数据");
				acctCheckErrService.insert(aceModel);				
				myLog.error(logger, "渠道补充往账数据，渠道日期【" + model.getSettleDate() + "】交易类型【" + model.getTranType() + "】渠道流水【"
						+ model.getPlatTrace() + "】");
				myLog.error(logger, "柜面通【" + date + "】往帐对账失败,渠道数据丢失或无效记账请核对: 核心流水号【" + model.getHostTraceno() + "】交易类型【"
						+ model.getTranType() + "】核心日期为【" + model.getSysDate() + "】");
				BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013,
						"与核心往账对账失败,查询不到渠道流水,渠道少数据,渠道流水【"+ model.getPlatTrace() + "】");
				
				BocmChkStatusModel record = new BocmChkStatusModel();
				record.setTxDate(date);
				record.setHostStatus(2);
				record.setChkTime(sysTime);
				record.setTxTel(txTel);
				chkStatusService.chkStatusUpd(record);
				myLog.info(logger, "更新与核心对账状态为失败：  对账日期：" + date);
				throw e;
			} else {
				// dc_flag IS '通存通兑标志；0通存、1通兑';
				String hostState = sndTraceQueryModel.getHostState(); // 渠道记录的核心状态
				// 通存
				if (hostState.equals("1")) {
					// 如果往账交易类型是交行卡转本行不更新对账状态
					if (sndTraceQueryModel.getTranType().equals("JH11")) {
						continue;
					}
					// 核心与渠道状态一致
					BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(),
							sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
					record.setCheckFlag("2");
					sndTraceService.sndTraceUpd(record);
				} else {
					// 其他状态说明交易有问题,核心记账异常说明柜面交易失败,如果核心记录了说明多记账了,正常的流水核心记账状态只能为1
					myLog.error(logger, "柜面通【" + date + "】往帐对账失败: 渠道流水号【" + sndTraceQueryModel.getPlatTrace()
							+ "】记录核心状态为【" + sndTraceQueryModel.getHostState() + "】,与核心记账状态不符");
					BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, sndTraceQueryModel.getPlatDate(),
							model.getSysTime(), model.getPlatTrace());
					aceModel.setPlatDate(sndTraceQueryModel.getPlatDate());
					aceModel.setPlatTrace(sndTraceQueryModel.getPlatTrace());
					aceModel.setTxCode(sndTraceQueryModel.getTxCode());
					aceModel.setTxSource(sndTraceQueryModel.getSourceType());
					aceModel.setTxDate(sndTraceQueryModel.getTxDate());
					aceModel.setHostDate(model.getHostDate());
					aceModel.setHostTraceno(model.getHostTraceno());
					aceModel.setTxDate(sndTraceQueryModel.getTxDate());
					aceModel.setSndBankno(sndTraceQueryModel.getSndBankno());
					aceModel.setTxBranch(sndTraceQueryModel.getTxBranch());
					aceModel.setTxTel(sndTraceQueryModel.getTxTel());
					aceModel.setTxInd(sndTraceQueryModel.getTxInd());
					aceModel.setTxAmt(sndTraceQueryModel.getTxAmt());
					aceModel.setProxyFee(sndTraceQueryModel.getProxy_fee());
					aceModel.setProxyFlag(sndTraceQueryModel.getProxy_flag());
					aceModel.setPayerBank(sndTraceQueryModel.getPayerBank());
					aceModel.setPayerAcno(sndTraceQueryModel.getPayerAcno());
					aceModel.setPayerName(sndTraceQueryModel.getPayerName());
					aceModel.setPayeeBank(sndTraceQueryModel.getPayeeBank());
					aceModel.setPayeeAcno(sndTraceQueryModel.getPayeeAcno());
					aceModel.setPayeeName(sndTraceQueryModel.getPayeeName());
					aceModel.setHostState(sndTraceQueryModel.getHostState());
					aceModel.setBocmState(sndTraceQueryModel.getBocmState());
					if (sndTraceQueryModel.getTranType().equals("JH11")) {
						aceModel.setCheckFlag("以交行对账为准");
					}else{
						aceModel.setCheckFlag("以我行对账为准");
					}
					
					aceModel.setHostFlag("2");
					aceModel.setBocmFlag("0");
					aceModel.setMsg("核心多账,需冲正");
					acctCheckErrService.insert(aceModel);
					BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013,
							"与核心往账对账失败,渠道记账状态异常,核心记账成功,核心多账,需冲正,渠道流水号【"+sndTraceQueryModel.getPlatTrace()+"】");
					
					BocmChkStatusModel record = new BocmChkStatusModel();
					record.setTxDate(date);
					record.setHostStatus(2);
					record.setChkTime(sysTime);
					record.setTxTel(txTel);
					chkStatusService.chkStatusUpd(record);
					myLog.info(logger, "更新与核心对账状态为失败：  对账日期：" + date);
					
					throw e;
				}
			}
		}
	}

	// 核对来账
	private void checkRcvLog(MyLog myLog, List<BocmDayCheckLogInitModel> rcvDayCheckLogList, Integer date,
			Integer sysTime, Integer sysTraceno, String txTel) throws SysTradeExecuteException {
		for (BocmDayCheckLogInitModel model : rcvDayCheckLogList) {
			// 根据核心对账数据取渠道来账数据
			// JH10 本行付款转账 JH11 交行付款转账 JH12交行卡存现金 JH13交行卡取现金 如果判断为下面的类型为往账，跳过
			if (model.getTranType().equals("JH10") || model.getTranType().equals("JH11")
					|| model.getTranType().equals("JH12") || model.getTranType().equals("JH13")) {
				continue;
			}
			BocmRcvTraceQueryModel rcvTraceQueryModel = rcvTraceService.getRcvTraceByKey(myLog, sysTime, sysTraceno,
					date, model.getSettleDate(), model.getPlatTrace());
			// 若渠道缺少数据则补充
			if (rcvTraceQueryModel == null) {
				BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, model.getSettleDate(),
						model.getSysTime(), model.getPlatTrace());
				aceModel.setPlatDate(model.getSettleDate());
				aceModel.setPlatTrace(model.getPlatTrace());
				aceModel.setPreHostState("");
				aceModel.setReHostState("1");
				aceModel.setDcFlag("");
				aceModel.setDirection("I");
				aceModel.setTxAmt(model.getTxAmt());
				aceModel.setTxCode(model.getTranType());
				aceModel.setTxSource("MT");
				aceModel.setTxDate(date);
				aceModel.setHostDate(model.getHostDate());
				aceModel.setHostTraceno(model.getHostTraceno());
				aceModel.setHostState("1");
				aceModel.setBocmState("0");
				aceModel.setHostFlag("1");
				aceModel.setBocmFlag("0");	
				if(model.getTranType().equals("JH02")){
					aceModel.setCheckFlag("以交行对账为准");
				}			
				aceModel.setMsg("对账失败,渠道少来账数据");
				acctCheckErrService.insert(aceModel);
				myLog.error(logger, "渠道补充往账数据，渠道日期【" + model.getSettleDate() + "】，渠道流水【" + model.getPlatTrace() + "】");
				myLog.error(logger, "柜面通【" + date + "】来帐对账失败,渠道数据丢失: 核心流水号【" + model.getHostTraceno() + "】交易类型【"
						+ model.getTranType() + "】核心日期为【" + model.getSysDate() + "】");
				
				
				BocmChkStatusModel record = new BocmChkStatusModel();
				record.setTxDate(date);
				record.setHostStatus(2);
				record.setChkTime(sysTime);
				record.setTxTel(txTel);
				chkStatusService.chkStatusUpd(record);
				myLog.info(logger, "更新与核心对账状态为失败：  对账日期：" + date);
				
				BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013
						,"与核心来账对账失败,查询不到渠道流水,渠道少账,渠道流水【" + model.getPlatTrace() + "】");
				throw e;
			} else {
				String hostState = rcvTraceQueryModel.getHostState(); // 渠道记录的核心状态
				if (hostState.equals("1")) {
					// 如果来账交易类型是他代本通存和他代本现金通兑,不更新对账状态
					if (rcvTraceQueryModel.getTranType().equals("JH02")
							|| (rcvTraceQueryModel.equals("JH01") && rcvTraceQueryModel.getTxInd().equals("0"))) {
						continue;
					}
					// 核心与渠道状态一致
					BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(),
							rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
					record.setCheckFlag("2");
					rcvTraceService.rcvTraceUpd(record);
				} else {
					// 来账核心记账状态只有成功超时,其他状态返回核心记账当成功处理
					// '核心记账状态，0-登记，1-成功，2-失败，3-超时，4-冲正成功，5-冲正失败，6-冲正超时';
					BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(),
							rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
					// 如果来账交易类型是他代本通存和他代本现金通兑,不更新对账状态,已交行为主的记录只更新核心记账状态，不修改对账状态
					if (rcvTraceQueryModel.getTranType().equals("JH02")
							|| (rcvTraceQueryModel.getTranType().equals("JH01") && rcvTraceQueryModel.getTxInd().equals("0"))) {
						record.setHostState("1");
						rcvTraceService.rcvTraceUpd(record);
					}else{
						//以我行为主的记录如果超时修改状态为成功，更新对账状态
						record.setHostState("1");
						record.setCheckFlag("2");
						rcvTraceService.rcvTraceUpd(record);
					} 							
				} 
			}
		}
	}

	// 获取对账文件，入库，取出入库核心成功的交易流水
	private List<BocmDayCheckLogInitModel> getCheckLogList(MyLog myLog, Integer date, Integer sysTime,
			Integer sysTraceno, String txBrno, String txTel) throws SysTradeExecuteException {
		// 请求核心接口，获取来账文件
		String localFile = getEsbCheckFile(myLog, date, sysTime, sysTraceno, txBrno, txTel);
		// 对账文件入库
		InitCheckLog(localFile, myLog, date, sysTime, sysTraceno);
		// 取对账文件数据
		List<BocmDayCheckLogInitModel> dayCheckLogList = dayCheckLogService.getDayCheckLog(myLog, sysTime, sysTraceno,
				date);
		return dayCheckLogList;
	}

	private void InitCheckLog(String localFile, MyLog myLog, Integer date, Integer sysTime, Integer sysTraceno)
			throws SysTradeExecuteException {
		BufferedReader br = null;
		myLog.info(logger, "账户变动信息入库开始");
		int i = 0;
		String hostChkTraceno = "";
		String platChkTraceno = "";
		try {
			dayCheckLogService.delete();
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(localFile)), "UTF-8"));
			String lineTxt = null;
			while ((lineTxt = br.readLine()) != null) {
				lineTxt += "*|";
				String[] array = lineTxt.split("\\|");
				if (array.length < 11) {
					myLog.info(logger, "文件【" + localFile + "】内容缺失");
					continue;
				}

				BocmDayCheckLogInitModel model = new BocmDayCheckLogInitModel(myLog, date, 0, 0);
				// 渠道日期
				model.setPlatDate(date);
				// 渠道流水
				model.setPlatTrace(Integer.parseInt(array[1].substring(14)));
				// 清算日期
				model.setSettleDate(Integer.parseInt(array[6]));
				// 交易类型
				model.setTranType(array[3]);
				// 清算机构
				model.setSettleBranch(array[2]);
				// 核心交易日期
				model.setHostDate(Integer.parseInt(array[6]));
				// 核心流水号
				model.setHostTraceno(array[0]);

				model.setCcy(array[7]); // 交易币种
				BigDecimal bg = new BigDecimal(array[8] == null ? "0" : array[8]);
				model.setTxAmt(bg); // 交易金额
				// 交易账户
				model.setAccountno(array[18]);
				model.setReversal(""); // 冲正标志
				model.setTxStatus(array[9]); // 交易状态
				model.setDirection(""); // 来往账标识

				// 记录核心流水
				hostChkTraceno = model.getHostTraceno();
				// 记录渠道流水
				platChkTraceno = model.getPlatTrace() + "";

				// 核心记账流水插入对账临时表
				dayCheckLogService.dayCheckLogInit(model);

				i++;
				myLog.info(logger, "核心记账流水入库,核心流水号：【" + model.getHostTraceno() + "】交易类型【" + model.getTranType()
						+ "】,渠道日期【" + date + "】,交易金额【" + model.getTxAmt() + "】");
			}
			myLog.info(logger, "记账日期【" + date + "】核心记账笔数【" + i + "】");

		} catch (Exception e) {
			myLog.error(logger, "对账文件流水入库失败:存在重复记账记录,核心流水号【" + hostChkTraceno + "】渠道流水号【" + platChkTraceno + "】 ");
			myLog.error(logger, "文件【" + localFile + "】插入失败", e);
			throw new RuntimeException("文件【" + localFile + "】插入失败");
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					myLog.error(logger, "文件流关闭失败", e);
				}
			}
			myLog.info(logger, "核心对账信息入库结束");
		}

	}

	private String getEsbCheckFile(MyLog myLog, Integer date, Integer sysTime, Integer sysTraceno, String txBrno,
			String txTel) throws SysTradeExecuteException {
		ESB_REQ_50015000101 esbReq_50015000101 = new ESB_REQ_50015000101(myLog, date, sysTime, sysTraceno);
		DataTransObject reqDto = new DataTransObject();
		reqDto.setSysDate(date);
		reqDto.setSysTime(sysTime);
		reqDto.setSysTraceno(sysTraceno);
		/** Add by 叶浦亮 At 2019/12/3 15:48 For 不同渠道平台调用核心接口使用不一样的systemID */
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_50015000101.getReqSysHead(), reqDto.getSourceType(),reqDto.getSysDate(),reqDto.getSysTime(),reqDto.getSysTraceno())
				.setBranchId(txBrno).setUserId(txTel).setSourceType("LV").build();
		esbReq_50015000101.setReqSysHead(reqSysHead);
		ESB_REQ_50015000101.REQ_BODY esbReqBody_50015000101 = esbReq_50015000101.getReqBody();
		esbReqBody_50015000101.setChannelType("BU");
		esbReqBody_50015000101.setStartDate(date.toString());
		esbReqBody_50015000101.setEndDate(date.toString());
		// esbReqBody_50015000101.setDirection(direction);

		ESB_REP_50015000101 esbRep_50015000101 = forwardToESBService.sendToESB(esbReq_50015000101,
				esbReqBody_50015000101, ESB_REP_50015000101.class);
		String remoteFile = esbRep_50015000101.getRepSysHead().getFilePath();
		String fileName = esbRep_50015000101.getRepBody().getFileName();
		String localPath = "";
		try (Jedis jedis = myJedis.connect()) {
			localPath = jedis.get(COMMON_PREFIX + "txt_path");
		}
		loadTraceLogFile(myLog, remoteFile, localPath + File.separator + "HOST_" + fileName);
		return localPath + File.separator + "HOST_" + fileName;
	}

	/**
	 * @Title: loadFile @Description: 从文件传输平台下载文件 @param @param
	 *         myLog @param @param remoteFile 文件传输平台路径+文件名 @param @param
	 *         localFile 文件本地路径+文件名 @param @throws PafTradeExecuteException
	 *         设定文件 @return void 返回类型 @throws
	 */
	private void loadTraceLogFile(MyLog myLog, String remoteFile, String localFile) throws SysTradeExecuteException {
		FtpClientConfigSet configSet = new FtpClientConfigSet();
		FtpGet ftpGet = null;
		try {
			ftpGet = new FtpGet(remoteFile, localFile, configSet);
			boolean result = ftpGet.doGetFile();
			if (!result) {
				myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载失败！");
				BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10012);
				throw e;
			}
			myLog.info(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载成功！");
		} catch (Exception e) {
			myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载失败！", e);
			BocmTradeExecuteException e1 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10012,
					e.getMessage());
			throw e1;
		} finally {
			if (ftpGet != null) {
				try {
					ftpGet.close();
				} catch (FtpException e) {
					myLog.error(logger, "文件传输关闭连接失败！", e);
				}
			}
		}
	}

	private void initSndErrRecord(MyLog myLog, BocmSndTraceQueryModel sndTraceQueryModel, String msg
			,String hostFlag, String bocmFlag, Integer date, Integer sysTime, String txTel)throws SysTradeExecuteException {
		BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, sndTraceQueryModel.getPlatDate(),
				sndTraceQueryModel.getSysTime(), sndTraceQueryModel.getPlatTrace());
		aceModel.setPlatDate(sndTraceQueryModel.getPlatDate());
		aceModel.setPlatTrace(sndTraceQueryModel.getPlatTrace());
		aceModel.setPreHostState(sndTraceQueryModel.getHostState());
		aceModel.setReHostState("2");
		aceModel.setDcFlag("");
		// 对账标志，1-未对账，2-已对账，3-核心多，4-渠道多
		aceModel.setCheckFlag("4");
		aceModel.setDirection("O");

		aceModel.setMsg("渠道多出往账数据，渠道日期【" + sndTraceQueryModel.getPlatDate() + "】交易类型【"
				+ sndTraceQueryModel.getTranType() + "】渠道流水【" + sndTraceQueryModel.getPlatTrace() + "】");

		aceModel.setPlatDate(sndTraceQueryModel.getPlatDate());
		aceModel.setPlatTrace(sndTraceQueryModel.getPlatTrace());
		aceModel.setTxCode(sndTraceQueryModel.getTxCode());
		aceModel.setTxSource(sndTraceQueryModel.getSourceType());
		aceModel.setTxDate(sndTraceQueryModel.getTxDate());
		aceModel.setHostDate(sndTraceQueryModel.getHostDate());
		aceModel.setHostTraceno(sndTraceQueryModel.getHostTraceno());
		aceModel.setTxDate(sndTraceQueryModel.getTxDate());
		aceModel.setSndBankno(sndTraceQueryModel.getSndBankno());
		aceModel.setTxBranch(sndTraceQueryModel.getTxBranch());
		aceModel.setTxTel(sndTraceQueryModel.getTxTel());
		aceModel.setTxInd(sndTraceQueryModel.getTxInd());
		aceModel.setTxAmt(sndTraceQueryModel.getTxAmt());
		aceModel.setProxyFee(sndTraceQueryModel.getProxy_fee());
		aceModel.setProxyFlag(sndTraceQueryModel.getProxy_flag());
		aceModel.setPayerBank(sndTraceQueryModel.getPayerBank());
		aceModel.setPayerAcno(sndTraceQueryModel.getPayerAcno());
		aceModel.setPayerName(sndTraceQueryModel.getPayerName());
		aceModel.setPayeeBank(sndTraceQueryModel.getPayeeBank());
		aceModel.setPayeeAcno(sndTraceQueryModel.getPayeeAcno());
		aceModel.setPayeeName(sndTraceQueryModel.getPayeeName());
		aceModel.setHostState(sndTraceQueryModel.getHostState());
		aceModel.setBocmState(sndTraceQueryModel.getBocmState());
		aceModel.setHostFlag(hostFlag);
		if(sndTraceQueryModel.getTranType().equals("JH11")){
			aceModel.setCheckFlag("以交行对账为准");
		}else{
			aceModel.setCheckFlag("以本行对账为准");
		}
		aceModel.setBocmFlag(bocmFlag);
		
		aceModel.setMsg(msg);
		myLog.error(logger, "插入调账明细表：渠道流水号【" + sndTraceQueryModel.getPlatTrace() + "】");
		acctCheckErrService.insert(aceModel);
		
		BocmChkStatusModel record = new BocmChkStatusModel();
		record.setTxDate(date);
		record.setHostStatus(2);
		record.setChkTime(sysTime);
		record.setTxTel(txTel);
		chkStatusService.chkStatusUpd(record);
		myLog.info(logger, "更新与核心对账状态为失败：  对账日期：" + date);
	}

	private void initRcvErrRecord(MyLog myLog, BocmRcvTraceQueryModel rcvTraceQueryModel, String msg
			,String hostFlag, String bocmFlag, Integer date, Integer sysTime, String txTel)throws SysTradeExecuteException {
		BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, rcvTraceQueryModel.getPlatDate(),
				rcvTraceQueryModel.getSysTime(), rcvTraceQueryModel.getPlatTrace());
		aceModel.setPlatDate(rcvTraceQueryModel.getPlatDate());
		aceModel.setPlatTrace(rcvTraceQueryModel.getPlatTrace());
		aceModel.setTxCode(rcvTraceQueryModel.getTxCode());
		aceModel.setTxSource(rcvTraceQueryModel.getSourceType());
		aceModel.setTxDate(rcvTraceQueryModel.getTxDate());
		aceModel.setTxDate(rcvTraceQueryModel.getTxDate());
		aceModel.setSndBankno(rcvTraceQueryModel.getSndBankno());
		aceModel.setTxBranch(rcvTraceQueryModel.getTxBranch());
		aceModel.setTxTel(rcvTraceQueryModel.getTxTel());
		aceModel.setTxInd(rcvTraceQueryModel.getTxInd());
		aceModel.setTxAmt(rcvTraceQueryModel.getTxAmt());
		// aceModel.setProxyFee(rcvTraceQueryModel.getProxy_fee());
		// aceModel.setProxyFlag(rcvTraceQueryModel.getProxy_flag());
		aceModel.setPayerBank(rcvTraceQueryModel.getPayerBank());
		aceModel.setPayerAcno(rcvTraceQueryModel.getPayerAcno());
		aceModel.setPayerName(rcvTraceQueryModel.getPayerName());
		aceModel.setPayeeBank(rcvTraceQueryModel.getPayeeBank());
		aceModel.setPayeeAcno(rcvTraceQueryModel.getPayeeAcno());
		aceModel.setPayeeName(rcvTraceQueryModel.getPayeeName());
		aceModel.setHostState(rcvTraceQueryModel.getHostState());
		aceModel.setBocmState(rcvTraceQueryModel.getBocmState());
		aceModel.setHostFlag(hostFlag);
		aceModel.setBocmFlag(bocmFlag);
		if(rcvTraceQueryModel.getTranType().equals("JH01")&&rcvTraceQueryModel.getTxInd().equals("1")){
			aceModel.setCheckFlag("以交行对账为准");
		}else{
			aceModel.setCheckFlag("以本行对账为准");
		}
		aceModel.setCheckFlag("以本行对账为准");
		aceModel.setMsg(msg);
		// aceModel.setMsg("渠道调整来账数据核心状态，渠道日期【"+rcvTraceQueryModel.getPlatDate()+"】，渠道流水【"+rcvTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+rcvTraceQueryModel.getDcFlag()+"】");
		acctCheckErrService.insert(aceModel);
		
		BocmChkStatusModel record = new BocmChkStatusModel();
		record.setTxDate(date);
		record.setHostStatus(2);
		record.setChkTime(sysTime);
		record.setTxTel(txTel);
		chkStatusService.chkStatusUpd(record);
		myLog.info(logger, "更新与核心对账状态为失败：  对账日期：" + date);
	}



	
	@Bean(name = "cityHostCheckAcctJobDetail")
	public MethodInvokingJobDetailFactoryBean cityCheckAcctJobDetail(BocmHostCheckAcctTasK task) {// ScheduleTask为需要执行的任务
		MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
		jobDetail.setConcurrent(false);
		jobDetail.setName(JOBNAME);
		jobDetail.setGroup(QuartzJobConfigration.JOBGROUP);
		jobDetail.setTargetObject(task);
		jobDetail.setTargetMethod(QuartzJobConfigration.METHODNAME);
		return jobDetail;
	}

	@Bean(name = "cityHostCheckAcctJobTrigger")
	public CronTriggerFactoryBean cityCheckAcctJobTrigger(
			@Qualifier("cityHostCheckAcctJobDetail") MethodInvokingJobDetailFactoryBean jobDetail) {
		CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
		tigger.setJobDetail(jobDetail.getObject());
		String exp = null;
		try (Jedis jedis = myJedis.connect()) {
			exp = jedis.get(QuartzJobConfigration.TPP_CRON + JOBNAME);
		}
		if (exp == null) {
			exp = "0 15 18 ? * *";
		}
		logger.info("任务[" + JOBNAME + "]启动[" + exp + "]");
		tigger.setCronExpression(exp);
		tigger.setName(JOBNAME);
		return tigger;
	}

	@Bean(name = "cityHostCheckAcctScheduler")
	public SchedulerFactoryBean schedulerFactory(@Qualifier("cityHostCheckAcctJobTrigger") Trigger cronJobTrigger) {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setOverwriteExistingJobs(true);
		bean.setStartupDelay(5);
		bean.setTriggers(cronJobTrigger);
		return bean;
	}

}
