package com.fxbank.tpp.bocm.trade.bocm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dcfs.esb.ftp.client.FtpClientConfigSet;
import com.dcfs.esb.ftp.client.FtpGet;
import com.dcfs.esb.ftp.server.error.FtpException;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.bocm.dto.bocm.REP_10000;
import com.fxbank.tpp.bocm.dto.bocm.REP_10103;
import com.fxbank.tpp.bocm.dto.bocm.REQ_10103;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmAcctCheckErrModel;
import com.fxbank.tpp.bocm.model.BocmDayCheckLogInitModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceUpdModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceUpdModel;
import com.fxbank.tpp.bocm.service.IBocmAcctCheckErrService;
import com.fxbank.tpp.bocm.service.IBocmDayCheckLogService;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.bocm.service.IBocmSndTraceService;
import com.fxbank.tpp.bocm.trade.esb.CHK_Bocm;
import com.fxbank.tpp.esb.model.ses.ESB_REP_50015000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_50015000101;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.esb.service.IForwardToTownService;

import redis.clients.jedis.Jedis;

/** 
* @ClassName: CHK_Fx 
* @Description: 取对账文件
* @author YePuLiang
* @date 2019年5月6日 上午10:02:07 
*  
*/
@Service("REQ_10103")
public class CHK_Fx implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(CHK_Fx.class);
	
	@Resource
	private LogPool logPool;
	
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
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "bocm.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_10103 req = (REQ_10103) dto;
		//发起行行号
		String sbnkNo = req.getSbnkNo();
		if(sbnkNo.substring(0, 3).equals("313")){
			myLog.info(logger, "交易发起行为本行，启用挡板数据");
			TS_10103 ts10103 = new TS_10103(logPool, forwardToESBService, forwardToTownService, dayCheckLogService, 
					sndTraceService, rcvTraceService, acctCheckErrService, myJedis);
			DataTransObject dtoRep = ts10103.execute(dto);
			return dtoRep;
		}
		
		REP_10103 rep = new REP_10103();
		myLog.info(logger, "外围与核心对账开始");
		
		String fileName = req.getFilNam();
		String txBrno = fileName.substring(4,16);
		String date = fileName.substring(16,24);
		
		// 柜员号
		String txTel = null;
		try(Jedis jedis = myJedis.connect()){
			txBrno = jedis.get(COMMON_PREFIX+"TXBRNO");
			txTel = jedis.get(COMMON_PREFIX+"TXTEL");
        }
		myLog.info(logger, "渠道与核心对账开始");
		acctCheckErrService.delete(date);

		//获取核心对账文件，处理核心对账文件信息入库（bocm_chk_log）
		myLog.info(logger, "获取核心对账文件");
		List<BocmDayCheckLogInitModel> dayCheckLogList = getCheckLogList(myLog, date, txBrno, txTel, dto);
		myLog.info(logger, "成功获取对账文件信息");
		
		//通过核心记账信息找柜面中来账的流水记录
		myLog.info(logger, "核对来账数据开始");
		checkRcvLog(myLog, dto, dayCheckLogList, date);
		myLog.info(logger, "核对来账数据结束");
		
		//通过核心记账信息找柜面中往账的流水记录
		myLog.info(logger, "核对往账数据开始");
		checkSndLog(myLog, dto, dayCheckLogList, date);
		myLog.info(logger, "核对往账数据结束");
		
		myLog.info(logger, "渠道与核心对账结束");
		
		
		
		myLog.info(logger, "外围与核心对账开始");
		//获取未对账的来账信息
		List<BocmRcvTraceQueryModel> rcvTraceList = rcvTraceService.getCheckRcvTrace(myLog,dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno(), date);
		
				//失败、超时、存款确认、冲功？
				//获取到的数据为渠道多出来的流水记录
				for(BocmRcvTraceQueryModel model : rcvTraceList) {
					BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, model.getPlatDate(), model.getPlatTime(), model.getPlatTrace());
					record.setCheckFlag("4");
					rcvTraceService.rcvTraceUpd(record);
					
					if(model.getHostState().equals("1")) {
						myLog.error(logger,"柜面通【"+date+"】对账失败: 多出来账记录，渠道流水号【"+model.getPlatTrace()+"】，核心状态【"+model.getHostState()+"】，通存通兑标志【"+model.getDcFlag()+"】");
						BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
						throw e;
					}else {
						myLog.info(logger, "渠道多出来账数据，渠道日期【"+model.getPlatDate()+"】，渠道流水【"+model.getPlatTrace()+"】，核心状态【"+model.getHostState()+"】，通存通兑标志【"+model.getDcFlag()+"】");
						myLog.info(logger, "渠道清理数据 SQL: delete * from bocm_rcv_log where plat_date ='"+model.getPlatDate()+"' and plat_trace='"+model.getPlatTrace()+"' ");
					}
				}
				
				//获取未对账的往帐信息
				List<BocmSndTraceQueryModel> sndTraceList = sndTraceService.getCheckSndTrace(myLog,dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno(), date);
				for(BocmSndTraceQueryModel model:sndTraceList) {
					BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, model.getPlatDate(), model.getPlatTime(), model.getPlatTrace());
					record.setCheckFlag("4");
					sndTraceService.sndTraceUpd(record);
					
					if(model.getHostState().equals("1")) {
						myLog.error(logger,"柜面通【"+date+"】对账失败: 多出往账记录，渠道流水号【"+model.getPlatTrace()+"】，核心状态【"+model.getHostState()+"】，通存通兑标志【"+model.getDcFlag()+"】");
						BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
						throw e;
					}else {
						myLog.info(logger, "渠道多出往账数据，渠道日期【"+model.getPlatDate()+"】，渠道流水【"+model.getPlatTrace()+"】，核心状态【"+model.getHostState()+"】，通存通兑标志【"+model.getDcFlag()+"】");
					}
				}
				
				myLog.info(logger, "外围与核心对账结束");
				
				
			
				
				int sysTime = req.getSysTime();
				int sysTraceno = req.getSysTraceno();
				int sysDate = req.getSysDate();
				
				
				String rcvCheckFlag2 = rcvTraceService.getTraceNum(date, "2");
				String rcvCheckFlag3 = rcvTraceService.getTraceNum(date, "3");
				int rcvTotal = Integer.parseInt(rcvCheckFlag2)+Integer.parseInt(rcvCheckFlag3);
				myLog.info(logger, "来账对账记录数【已对账】【"+rcvCheckFlag2+"】");
				myLog.info(logger, "来账对账记录数【核心多】【"+rcvCheckFlag3+"】");
				
				String sndCheckFlag2 = sndTraceService.getTraceNum(date, "2");
				String sndCheckFlag3 = sndTraceService.getTraceNum(date, "3");
				int sndTotal = Integer.parseInt(sndCheckFlag2)+Integer.parseInt(sndCheckFlag3);
				
				myLog.info(logger, "往账对账记录数【已对账】【"+sndCheckFlag2+"】");
				myLog.info(logger, "往账对账记录数【核心多】【"+sndCheckFlag3+"】");
				
				int tolCnt = rcvTotal + sndTotal;
				myLog.info(logger, "返回对账交易数量【"+tolCnt+"】");
				
				String rcvTotalAmt = rcvTraceService.getRcvTotalChkSum(myLog, date);
				if(rcvTotalAmt==null){
					rcvTotalAmt = "0.00";
				}
				
				String sndTotalAmt = sndTraceService.getSndTotalChkSum(myLog, date);
				if(sndTotalAmt==null){
					sndTotalAmt = "0.00";
				}
				myLog.info(logger, "来账总金额【"+rcvTotalAmt+"】");
				myLog.info(logger, "往账总金额【"+sndTotalAmt+"】");
				BigDecimal rcv = new BigDecimal(rcvTotalAmt);
				BigDecimal snd = new BigDecimal(sndTotalAmt);
				
				BigDecimal totalAmt = rcv.add(snd);
				
				StringBuffer fileTxt = new StringBuffer();
				//组装对账文件报文
				
				//组装来账文件报文
				List<BocmRcvTraceQueryModel> upRcvTraceList = rcvTraceService.getUploadCheckRcvTrace(myLog, sysDate,sysTime,sysTraceno, date);
				List<REP_10103.Detail> tradList = new ArrayList<REP_10103.Detail>();
				
				for(BocmRcvTraceQueryModel model :upRcvTraceList){
					//本方交易流水号
					REP_10103.Detail trad = modelToRcvTradDetail(model);
					tradList.add(trad);
				}					
				
				//组装往账文件报文
				List<BocmSndTraceQueryModel> upSndTraceList = sndTraceService.getUploadCheckSndTrace(myLog, sysDate,sysTime,sysTraceno, date);
				for(BocmSndTraceQueryModel model :upSndTraceList){
					//本方交易流水号
					REP_10103.Detail trad = modelToSndTradDetail(model);
					tradList.add(trad);
				}	
				
				
				rep.setFilLen(254*tradList.size());	
				rep.setTolCnt(tradList.size());
				rep.setTolAmt(Double.parseDouble(totalAmt.toString()));
				myLog.info(logger, "返回报文文件长度："+rep.getFilLen());
				if(tradList.size()==0){
					rep.setFilTxt(null);
					BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013,"无交易记录");
					throw e;
				}else{
					rep.setFilTxt(tradList);
				}

		return rep;
	}
	
	
	private REP_10103.Detail modelToRcvTradDetail(BocmRcvTraceQueryModel model){
		REP_10103.Detail chk = new REP_10103.Detail();
		//给交行返回交易流水
		chk.setTlogNo(String.format("%06d%08d", model.getSysDate()%1000000,model.getSysTraceno()));
		chk.setLogNo(model.getBocmTraceno());
		
		//本行模拟交行接口测试
//		chk.setTlogNo(model.getBocmTraceno());
//		chk.setLogNo(model.getPlatTrace()+"");
		
		chk.setThdCod(model.getTxCode());
		chk.setBbusTyp("000");
		
		//交行总行行号
		String JHNO = "";
		String FXNO = "";
		try(Jedis jedis = myJedis.connect()){
			//从redis中获取交行总行行号
			JHNO = jedis.get(COMMON_PREFIX+"JHNO");
			FXNO = jedis.get(COMMON_PREFIX+"FXNO");
        }
		
		//发起行行号  来账本行为联机交易接收行（填发起行人行行号）
		//联机交易发起方出文件时指发起方网点，
		//联机交易接收方出文件时指发起方总行行号。
		chk.setSbnkNo(JHNO);
		//接收行行号  联机交易接收方总行行号。
		chk.setRbnkNo(FXNO);
		chk.setTactDt(model.getPlatDate()+"");
		chk.setTxnAmt(Double.parseDouble(model.getTxAmt().toString()));
		//手续费收取方式
		chk.setFeeFlg(model.getFeeFlag());
		//开户行手续费
		if(model.getFee()!=null){
			chk.setFee(Double.parseDouble(model.getFee().toString()));
		}
		//通存通兑业务模式 0 现金 1 转账
		chk.setTxnMod(model.getTxInd());
		//付款人开户行
		chk.setPayBnk(model.getPayerBank());
		//付款人账户类型
		chk.setPactTp(model.getPayerActtp());
		//付款人账号
		chk.setPactNo(model.getPayerAcno());
		//付款人名称
		chk.setPayNam(model.getPayerName());
		//收款人开户行
		chk.setRecBnk(model.getPayeeBank());
		//收款人账户类型
		chk.setRactTp(model.getPayeeActtp());
		//收款人账号
		chk.setRactNo(model.getPayeeAcno());
		//收款人名称
		chk.setRecNam(model.getPayeeName());
		//交易状态
		if("1".equals(model.getHostState())){
			chk.setTxnSts("S");	
		}else{
			chk.setTxnSts("F");	
		}
		
		return chk;
	}
	
	/**
	* @Title: modelToSndTradDetail 
	* @Description: 往账组装单笔交易报文 
	* @param @param model
	* @param @return    设定文件 
	* @return REP_10103.Detail    返回类型 
	* @throws
	 */
	private REP_10103.Detail modelToSndTradDetail(BocmSndTraceQueryModel model){
		REP_10103.Detail chk = new REP_10103.Detail();
		
		//本方交易流水号  TlogNo长度不足前补‘0’，例如农商行生成对账文件给交行，本方交易流水号填农商行流水号，对方交易流水号填交行流水号（如果存在），发起方流水号必输		
		//对方交易流水号 logNo 长度不足前补‘0’，发起方流水号必输
		//给交行返回交易流水
		chk.setTlogNo(String.format("%06d%08d", model.getSysDate()%1000000,model.getSysTraceno()));
		chk.setLogNo(model.getBocmTraceno());
		
		//本行模拟交行接口测试
//		chk.setTlogNo(model.getBocmTraceno());
//		chk.setLogNo(model.getPlatTrace()+"");
		
		chk.setThdCod(model.getTxCode());
		chk.setBbusTyp("000");
		
		
		//交行总行行号
		String JHNO = "";		
		try(Jedis jedis = myJedis.connect()){
			//从redis中获取交行总行行号
			JHNO = jedis.get(COMMON_PREFIX+"JHNO");
        }
		
		//发起行行号  往账本行为联机交易发起行（填发起行人行行号）
		//联机交易发起方出文件时指发起方网点，
		//联机交易接收方出文件时指发起方总行行号。
		chk.setSbnkNo(model.getSndBankno());
		//接收行行号  联机交易接收方总行行号。(往账交易，接收方为交行)
		chk.setRbnkNo(JHNO);
		chk.setTactDt(model.getPlatDate()+"");
		chk.setTxnAmt(Double.parseDouble(model.getTxAmt().toString()));
		//手续费收取方式
		chk.setFeeFlg(model.getFeeFlag());
		//开户行手续费
		if(model.getFee()!=null){
			chk.setFee(Double.parseDouble(model.getFee().toString()));
		}
		//通存通兑业务模式 0 现金 1 转账
		chk.setTxnMod(model.getTxInd());
		//付款人开户行
		chk.setPayBnk(model.getPayerBank());
		//付款人账户类型
		chk.setPactTp(model.getPayerActtp());
		//付款人账号
		chk.setPactNo(model.getPayerAcno());
		//付款人名称
		chk.setPayNam(model.getPayerName());
		//收款人开户行
		chk.setRecBnk(model.getPayeeBank());
		//收款人账户类型
		chk.setRactTp(model.getPayeeActtp());
		//收款人账号
		chk.setRactNo(model.getPayeeAcno());
		//收款人名称
		chk.setRecNam(model.getPayeeName());
		//交易状态
		if("1".equals(model.getHostState())){
			chk.setTxnSts("S");	
		}else{
			chk.setTxnSts("F");	
		}
		
		return chk;
	}
	
	
	/**
	* @Title: checkRcvLog 
	* @Description: 来账对账 
	* @param @param myLog
	* @param @param dto
	* @param @param rcvDayCheckLogList
	* @param @param date
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void checkRcvLog(MyLog myLog,DataTransObject dto,List<BocmDayCheckLogInitModel> rcvDayCheckLogList,String date) throws SysTradeExecuteException {
		int i = 0;
		for(BocmDayCheckLogInitModel model:rcvDayCheckLogList) {
			//JH10 本行付款转账   JH11 交行付款转账   JH12交行卡存现金  JH13交行卡取现金 如果判断为下面的类型为往账，跳过
			if(model.getTranType().equals("JH10")||model.getTranType().equals("JH11")
					||model.getTranType().equals("JH12")||model.getTranType().equals("JH13")){			
				continue;
			}
			//根据核心对账数据取渠道来账数据
			BocmRcvTraceQueryModel rcvTraceQueryModel = rcvTraceService.getRcvTraceByKey(myLog, dto.getSysTime(), dto.getSysTraceno(), dto.getSysDate(),model.getSettleDate(),model.getPlatTrace());
			//若渠道缺少数据则报错
			if(rcvTraceQueryModel == null) {
				BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, model.getSettleDate(), dto.getSysTime(), model.getPlatTrace());
				aceModel.setPlatDate(model.getSettleDate());
				aceModel.setPlatTrace(model.getPlatTrace());
				aceModel.setPreHostState("");
				aceModel.setReHostState("1");
				aceModel.setDcFlag("");
				aceModel.setCheckFlag("3");
				aceModel.setDirection("I");
				aceModel.setTxAmt(model.getTxAmt());
				aceModel.setMsg("渠道补充来账数据，渠道日期【"+model.getSettleDate()+"】，渠道流水【"+model.getPlatTrace()+"】，交易类型【"+model.getTranType()+"】");
				acctCheckErrService.insert(aceModel);	
				myLog.error(logger, "柜面通【"+date+"】来帐对账失败,渠道数据丢失: 交易类型【"+model.getTranType()+"】核心流水号【"+model.getHostTraceno()+"】核心日期为【"+model.getSysDate()+"】渠道流水号：【"+model.getPlatTrace()+"】");
				BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
				throw e;
			}else {
				//渠道记录的核心状态
				String hostState = rcvTraceQueryModel.getHostState(); 
				if(hostState.equals("1")) {
					//核心与渠道状态一致  更新对账状态
					BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(), rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
					record.setCheckFlag("2");
					rcvTraceService.rcvTraceUpd(record);
					myLog.info(logger,"渠道调整来账数据核心状态，渠道日期【"+rcvTraceQueryModel.getPlatDate()+"】，渠道流水【"+rcvTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+rcvTraceQueryModel.getDcFlag()+"】");
					i++;
				}else if(hostState.equals("0")||hostState.equals("3")||hostState.equals("5")||hostState.equals("6")) {
					//核心记账成功，渠道状态为超时、冲正超时、冲正失败，修改渠道状态为成功
					BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(), rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
					record.setHostState("1");
					record.setCheckFlag("2");
					rcvTraceService.rcvTraceUpd(record);
					myLog.info(logger,"渠道调整来账数据核心状态，渠道日期【"+rcvTraceQueryModel.getPlatDate()+"】，渠道流水【"+rcvTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+rcvTraceQueryModel.getDcFlag()+"】");

					BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, model.getSettleDate(), dto.getSysTime(), model.getPlatTrace());
					aceModel.setPlatDate(model.getSettleDate());
					aceModel.setPlatTrace(model.getPlatTrace());
					aceModel.setPreHostState(rcvTraceQueryModel.getHostState());
					aceModel.setReHostState("1");
					aceModel.setDcFlag(rcvTraceQueryModel.getDcFlag());
					aceModel.setCheckFlag("2");
					aceModel.setDirection("I");
					aceModel.setTxAmt(rcvTraceQueryModel.getTxAmt());
					aceModel.setPayeeAcno(rcvTraceQueryModel.getPayeeAcno());
					aceModel.setPayeeName(rcvTraceQueryModel.getPayeeName());
					aceModel.setPayerAcno(rcvTraceQueryModel.getPayerAcno());
					aceModel.setPayerName(rcvTraceQueryModel.getPayerName());
					aceModel.setMsg("渠道调整来账数据核心状态，渠道日期【"+rcvTraceQueryModel.getPlatDate()+"】，渠道流水【"+rcvTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+rcvTraceQueryModel.getDcFlag()+"】");
					acctCheckErrService.insert(aceModel);
					i++;
				}else if(hostState.equals("4")){
					//4.有冲正成功的记账返回特殊处理
					BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(), rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
					record.setCheckFlag("5");					
					rcvTraceService.rcvTraceUpd(record);
					myLog.error(logger, "渠道记录核心记账状态为冲正成功，对账忽略，渠道日期【"+rcvTraceQueryModel.getPlatDate()+"】，渠道流水【"+rcvTraceQueryModel.getPlatTrace()+"】");
				}else {
					//核心记账成功，渠道状态为2.失败、
					myLog.error(logger, "柜面通【"+date+"】对账失败: 渠道流水号【"+rcvTraceQueryModel.getPlatTrace()+"】记录核心状态为【"+rcvTraceQueryModel.getHostState()+"】,与核心记账状态不符");
					BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
					throw e;
				}
				myLog.error(logger, "核心流水号【"+model.getHostTraceno()+"】来帐对账成功,渠道流水号：【"+model.getPlatTrace()+"】");
			}
		}
		myLog.error(logger, "来帐对账成功,来账交易笔数：【"+i+"】");
	}
	
	private List<BocmDayCheckLogInitModel> getCheckLogList(MyLog myLog, String date, String txBrno, String txTel,DataTransObject dto) throws SysTradeExecuteException{
		//请求核心接口，获取来账文件
		String localFile = getEsbCheckFile(myLog,date,txBrno,txTel,dto);
		
		//对账文件入库
		InitCheckLog(localFile,myLog, dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno());
		
		//取对账文件数据
		List<BocmDayCheckLogInitModel> dayCheckLogList = dayCheckLogService.getDayCheckLog(myLog, dto.getSysTime(), dto.getSysTraceno(), dto.getSysDate());
			
		return 	dayCheckLogList;
	}
	
	private void InitCheckLog(String localFile, MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno) throws SysTradeExecuteException {
		BufferedReader br = null;
		myLog.info(logger, "账户变动信息入库开始");
		int i = 0;
		int rcv = 0;
		int snd = 0;
		try {
			dayCheckLogService.delete();
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(localFile)),"UTF-8"));
			String lineTxt=null;
			while ((lineTxt = br.readLine()) != null) {
				lineTxt += "*|";
				String[] array = lineTxt.split("\\|");
                if(array.length<11){
                    myLog.info(logger,"文件【"+localFile+"】内容缺失");
                    continue;
                }
                myLog.info(logger, "核心流水："+array[0]+"入库");
                BocmDayCheckLogInitModel model = new BocmDayCheckLogInitModel(myLog, sysDate,sysTime,sysTraceno);
                //渠道日期
                model.setPlatDate(sysDate); 
                //渠道流水
                model.setPlatTrace(Integer.parseInt(array[1].substring(14))); 
                //交易类型
                model.setTranType(array[3]); 
                
                //清算日期
                model.setSettleDate(Integer.parseInt(array[6]));
                //清算机构
                model.setSettleBranch(array[2]); 
                //核心交易日期
                model.setHostDate(Integer.parseInt(array[6])); 
                //核心流水号
                model.setHostTraceno(array[0]); 
                model.setCcy(array[7]); //交易币种
                BigDecimal bg = new BigDecimal(array[8]==null?"0":array[8]);
                model.setTxAmt(bg); //交易金额
                //交易账户 
                model.setAccountno(array[18]); 
                model.setReversal(""); //冲正标志
                model.setTxStatus(array[9]); //交易状态
                model.setDirection(""); //来往账标识
                
        		dayCheckLogService.dayCheckLogInit(model);
        		i++;
        		String tranType = model.getTranType();
                if("JH10".equals(tranType)||"JH11".equals(tranType)||"JH12".equals(tranType)||"JH13".equals(tranType)){
                	snd++;
                }
                if("JH01".equals(tranType)||"JH02".equals(tranType)){
                	rcv++;
                }
			}

		} catch (Exception e) {
            myLog.error(logger, "文件【"+localFile+"】插入失败", e);
            throw new RuntimeException("文件【"+localFile+"】插入失败");
		} finally {
			if(null != br) {
				try {
					br.close();
				} catch (IOException e) {
					myLog.error(logger, "文件流关闭失败", e);
				}
			}
			myLog.info(logger, "核心对账信息入库结束");
		}
		myLog.info(logger, "文件【"+localFile+"】插入【"+i+"】条数据,来账交易【"+rcv+"】往账交易【"+snd+"】");
		
	}
	
	private String getEsbCheckFile(MyLog myLog, String date, String txBrno, String txTel,DataTransObject dto) throws SysTradeExecuteException {
		ESB_REQ_50015000101 esbReq_50015000101 = new ESB_REQ_50015000101(myLog, dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_50015000101.getReqSysHead(),dto).setBranchId(txBrno).setUserId(txTel).setSourceType("BU").build();
		esbReq_50015000101.setReqSysHead(reqSysHead);
		ESB_REQ_50015000101.REQ_BODY esbReqBody_50015000101 = esbReq_50015000101.getReqBody();
		esbReqBody_50015000101.setChannelType("BU");
		//结算分行
		esbReqBody_50015000101.setSettleBranch(txBrno);
		esbReqBody_50015000101.setStartDate(date);
		esbReqBody_50015000101.setEndDate(date);
		esbReqBody_50015000101.setDirection("");
		
		
		ESB_REP_50015000101 esbRep_50015000101 = forwardToESBService.sendToESB(esbReq_50015000101, esbReqBody_50015000101, ESB_REP_50015000101.class);
		String remoteFile = esbRep_50015000101.getRepSysHead().getFilePath();
		String fileName = esbRep_50015000101.getRepBody().getFileName();
		String localPath="";
		try (Jedis jedis = myJedis.connect()) {
			localPath = jedis.get(COMMON_PREFIX+"txt_path");
		}
		loadTraceLogFile(myLog, remoteFile, localPath+File.separator+"_"+fileName);
		return localPath+File.separator+"_"+fileName;
	}
	
	/**
	 * @Title: loadFile @Description: 从文件传输平台下载文件 @param @param
	 * myLog @param @param remoteFile 文件传输平台路径+文件名 @param @param localFile
	 * 文件本地路径+文件名 @param @throws PafTradeExecuteException 设定文件 @return void
	 * 返回类型 @throws
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
	
	private void checkSndLog(MyLog myLog, DataTransObject dto, List<BocmDayCheckLogInitModel> sndDayCheckLogList,
			String date) throws SysTradeExecuteException {
		int i = 0;
		for(BocmDayCheckLogInitModel model:sndDayCheckLogList) {
			//JH01交行代理本行存款   JH02 交行代理我行取款  如果判断为下面的类型为来账，跳过
			if(model.getTranType().equals("JH01")||model.getTranType().equals("JH02")){
					continue;
			}
			//根据核心对账数据取渠道往账数据
			BocmSndTraceQueryModel sndTraceQueryModel = sndTraceService.getSndTraceByKey(myLog, dto.getSysTime(), dto.getSysTraceno(), dto.getSysDate(),model.getSettleDate(),model.getPlatTrace());
			//若渠道缺少数据则报错
			if(sndTraceQueryModel == null) {
				BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, model.getSettleDate(), dto.getSysTime(), model.getPlatTrace());
				aceModel.setPlatDate(model.getSettleDate());
				aceModel.setPlatTrace(model.getPlatTrace());
				aceModel.setPreHostState("");
				aceModel.setReHostState("1");
				aceModel.setDcFlag("");
				aceModel.setCheckFlag("3");
				aceModel.setDirection("O");
				aceModel.setMsg("渠道补充往账数据，渠道日期【"+model.getSettleDate()+"】，渠道流水【"+model.getPlatTrace()+"】");
				acctCheckErrService.insert(aceModel);
				
//				myLog.error(logger, "柜面通【"+date+"】往帐对账失败,渠道数据丢失: 核心流水号【"+model.getHostTraceno()+"】核心日期为【"+model.getSysDate()+"】");
				myLog.error(logger, "补数据SQL： insert into bocm_snd_log (plat_date,plat_trace,tx_amt,host_date,host_traceno,host_state,check_flag) VALUES ("+model.getPlatDate()+","+model.getPlatTrace()+",'"+model.getTxAmt()
				+"',"+model.getHostDate()+",'"+model.getHostTraceno()+"','1','1');");
				continue;
//				BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
//				throw e;
			}else {
				String dcFlag = sndTraceQueryModel.getDcFlag();//通存通兑标志
				String hostState = sndTraceQueryModel.getHostState(); //渠道记录的核心状态
				//myLog.info(logger, "柜面通核对往帐，核心流水号【"+model.getHostTraceno()+"】渠道流水号【"+model.getPlatTrace()+"】");
				//通存
				if(hostState.equals("1")) {
					//核心与渠道状态一致
					BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
					record.setCheckFlag("2");
					sndTraceService.sndTraceUpd(record);
				}else if(hostState.equals("0")||hostState.equals("3")||hostState.equals("5")||hostState.equals("6")) {
					//核心记账成功，渠道状态为超时、存款确认、冲正超时、冲正失败，修改渠道状态为成功
					BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
					record.setHostState("1");
					record.setCheckFlag("2");
					sndTraceService.sndTraceUpd(record);
					myLog.info(logger,"渠道调整往账数据核心状态，渠道日期【"+sndTraceQueryModel.getPlatDate()+"】，渠道流水【"+sndTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+dcFlag+"】");
				
					BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, model.getSettleDate(), dto.getSysTime(), model.getPlatTrace());
					aceModel.setPlatDate(model.getSettleDate());
					aceModel.setPlatTrace(model.getPlatTrace());
					aceModel.setPreHostState(hostState);
					aceModel.setReHostState("1");
					aceModel.setDcFlag(dcFlag);
					aceModel.setCheckFlag("2");
					aceModel.setDirection("O");
					aceModel.setTxAmt(sndTraceQueryModel.getTxAmt());
					aceModel.setPayeeAcno(sndTraceQueryModel.getPayeeAcno());
					aceModel.setPayeeName(sndTraceQueryModel.getPayeeName());
					aceModel.setPayerAcno(sndTraceQueryModel.getPayerAcno());
					aceModel.setPayerName(sndTraceQueryModel.getPayerName());
					aceModel.setMsg("渠道调整往账数据核心状态，渠道日期【"+sndTraceQueryModel.getPlatDate()+"】，渠道流水【"+sndTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+dcFlag+"】");
					acctCheckErrService.insert(aceModel);
				}else if(hostState.equals("4")){
					BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
					record.setCheckFlag("5");
					sndTraceService.sndTraceUpd(record);
					myLog.error(logger, "渠道记录核心记账状态为冲正成功，对账忽略，渠道日期【"+sndTraceQueryModel.getPlatDate()+"】，渠道流水【"+sndTraceQueryModel.getPlatTrace()+"】");
				}else {
					myLog.error(logger, "柜面通【"+date+"】往帐对账失败: 渠道流水号【"+sndTraceQueryModel.getPlatTrace()+"】记录核心状态为【"+sndTraceQueryModel.getHostState()+"】,与核心记账状态不符");
					BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
					throw e;
				}
	
				myLog.error(logger, "核心流水号【"+model.getHostTraceno()+"】往帐对账成功,渠道流水号：【"+model.getPlatTrace()+"】");
				i++;
			}
		}
		myLog.error(logger, "往帐对账成功,往账交易笔数：【"+i+"】");
	}
}
