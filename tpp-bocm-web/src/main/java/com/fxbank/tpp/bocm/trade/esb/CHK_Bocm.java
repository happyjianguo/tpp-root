package com.fxbank.tpp.bocm.trade.esb;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.dto.esb.REP_30063001304;
import com.fxbank.tpp.bocm.dto.esb.REQ_30061001101;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmAcctCheckErrModel;
import com.fxbank.tpp.bocm.model.BocmChkStatusModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceUpdModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceUpdModel;
import com.fxbank.tpp.bocm.model.REP_10103;
import com.fxbank.tpp.bocm.model.REQ_10103;
import com.fxbank.tpp.bocm.service.IBocmAcctCheckErrService;
import com.fxbank.tpp.bocm.service.IBocmChkStatusService;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.bocm.service.IBocmSndTraceService;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;
import com.fxbank.tpp.bocm.util.NumberUtil;

import redis.clients.jedis.Jedis;

/**
 * @ClassName: CHK_Bocm
 * @Description: 交行对账
 * @author YePuLiang
 * @date 2019年5月6日 下午5:18:44
 * 
 */
public class CHK_Bocm {

	private static Logger logger = LoggerFactory.getLogger(CHK_Bocm.class);

	private LogPool logPool;

	private IForwardToBocmService forwardToBocmService;

	private IBocmSndTraceService sndTraceService;

	private IBocmRcvTraceService rcvTraceService;

	private IBocmAcctCheckErrService acctCheckErrService;

	private IBocmChkStatusService chkStatusService;

	private MyJedis myJedis;

	private final static String COMMON_PREFIX = "bocm.";

	public CHK_Bocm(LogPool logPool,
			IForwardToBocmService forwardToBocmService, IBocmSndTraceService sndTraceService,
			IBocmRcvTraceService rcvTraceService, IBocmAcctCheckErrService acctCheckErrService,
			IBocmChkStatusService chkStatusService,MyJedis myJedis) {
		this.logPool = logPool;
		this.forwardToBocmService = forwardToBocmService;
		this.sndTraceService = sndTraceService;
		this.rcvTraceService = rcvTraceService;
		this.acctCheckErrService = acctCheckErrService;
		this.chkStatusService = chkStatusService;
		this.myJedis = myJedis;
	}

	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {

		MyLog myLog = logPool.get();
		REQ_30061001101 reqDto = (REQ_30061001101) dto;
		REQ_30061001101.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30063001304 rep = new REP_30063001304();

		myLog.info(logger, "外围与交行对账定时任务开始");

		Integer date = reqDto.getSysDate();
		Integer sysTime = reqDto.getSysTime();
		Integer sysTraceno = reqDto.getSysTraceno();

		date = Integer.parseInt(reqBody.getStmtDtT2());
		Integer sysDate = date;
		BocmChkStatusModel chkModel = chkStatusService.selectByDate(date + "");
		if (chkModel.getHostStatus() == 0) {
			myLog.error(logger, "渠道未与核心对账，与交行对账失败");
			BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013,
					"渠道未与核心对账");
			throw e;
		}

		// 交行总行行号
		String JHNO = "";
		// 阜新银行总行行号
		String FXNO = "";
		try (Jedis jedis = myJedis.connect()) {
			// 从redis中获取交行总行行号
			JHNO = jedis.get(COMMON_PREFIX + "JHNO");
			FXNO = jedis.get(COMMON_PREFIX + "FXNO");
		}

		acctCheckErrService.delete(date.toString());

		REQ_10103 req10103 = null;
		REP_10103 rep10103 = null;

		req10103 = new REQ_10103(myLog, date, sysTime, sysTraceno);
		req10103.setSbnkNo(FXNO);
		req10103.setRbnkNo(FXNO);

		req10103.setFilNam("BUPS" + FXNO + date + ".dat");
		// 获取交行对账文件
		myLog.info(logger, "外围与交行对账获取交行对账文件");
		// 获取交行交易流水信息
		rep10103 = forwardToBocmService.sendToBocm(req10103, REP_10103.class);

		// 以交行为主交易笔数
		int tolCnt = 0;
		tolCnt = rep10103.getTolCnt();
		// 以交行为主交易金额
		Double tolAmt = rep10103.getTolAmt();
		tolAmt = NumberUtil.removePoint(tolAmt);
		List<REP_10103.Detail> tradList = rep10103.getFilTxt();

		int snd = 0;
		int rcv = 0;
		// 拆分对账文件与渠道对账
		for (REP_10103.Detail bocmTrace : tradList) {
			// 获取交行交易流水号
			String bocmTraceno = bocmTrace.getTlogNo();
			// 交易业务码
			String thdCod = bocmTrace.getThdCod();
			// 通存通兑业务模式 0现金 1转账
			String txnMod = bocmTrace.getTxnMod();
			// 交易发起行行号
			String SbnkNo = bocmTrace.getSbnkNo();
			myLog.info(logger,
					"外围与交行对账,交行流水号【" + bocmTraceno + "】发起行行号【" + SbnkNo + "】交易代码【" + thdCod + "】业务模式【" + txnMod + "】");
			// 判断交易发起方人行行号，如果为本行行号说明本条对账文件对应的我方往账记录
			if (SbnkNo.substring(0, 3).equals("313")) {
				// 根据交行核心对账数据取渠道往账数据
				BocmSndTraceQueryModel sndTraceQueryModel = sndTraceService.getBocmSndTraceByKey(myLog, sysTime,
						sysTraceno, sysDate, bocmTraceno);

				// 若渠道缺少数据则报错
				if (sndTraceQueryModel == null) {
					BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, sysDate, sysTime, sysTraceno);
					aceModel.setPlatDate(sysDate);
					if(bocmTrace.getLogNo()!=null&&!bocmTrace.getLogNo().equals("")){
						int platTraceno = Integer.parseInt(bocmTrace.getLogNo().substring(6));
						aceModel.setPlatTrace(platTraceno);
					}else{
						aceModel.setPlatTrace(sysTraceno);
					}
					aceModel.setTxDate(sysDate);
					aceModel.setTxCode(bocmTrace.getThdCod());
					aceModel.setTxSource("BU");
					aceModel.setSndBankno(bocmTrace.getSbnkNo());
					aceModel.setTxInd(bocmTrace.getTxnMod());
					aceModel.setPayerBank(bocmTrace.getPayBnk());
					aceModel.setPayerAcno(bocmTrace.getPactNo());
					aceModel.setPayerName(bocmTrace.getPayNam());
					aceModel.setPayeeBank(bocmTrace.getRbnkNo());
					aceModel.setPayeeAcno(bocmTrace.getRactNo());
					aceModel.setPayeeName(bocmTrace.getRecNam());
					aceModel.setPreHostState("0");
					aceModel.setReHostState("1");
					aceModel.setDcFlag("");
					aceModel.setCheckFlag("3");
					aceModel.setDirection("I");
					aceModel.setTxAmt(new BigDecimal(bocmTrace.getTxnAmt()));
					aceModel.setHostState("0");
					aceModel.setBocmState("1");
					aceModel.setBocmFlag("1");
					aceModel.setHostFlag("0");
					aceModel.setCheckFlag("以交行对账为准");
					aceModel.setMsg("渠道缺少往账数据,核心缺少记账数据,需补账");
					acctCheckErrService.insert(aceModel);	
					myLog.error(logger,
							"柜面通【" + date + "】往帐对账失败,渠道数据丢失: 交行流水号【" + bocmTraceno + "】交行记账日期为【" + sysDate + "】");
					BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013
							,"与交行来账对账失败,渠道少数据,核心少数据,交行流水号【" +bocmTrace.getTlogNo()+ "】");
					throw e;
				} else {
					checkBocmSndLog(myLog, sysDate, sysTime, sndTraceQueryModel, bocmTrace, date + "");
					snd++;
				}
			} else if (SbnkNo.substring(0, 3).equals("301")) {
				// 判断交易发起方人行行号，如果不是本行行号说明本条对账文件对应的我方来账记录
				// 根据交行对账数据取渠道来账数据
				BocmRcvTraceQueryModel rcvTraceQueryModel = rcvTraceService.getBocmRcvTraceByKey(myLog, sysTime,
						sysTraceno, sysDate, bocmTraceno);
				// 若渠道缺少数据则报错
				if (rcvTraceQueryModel == null) {
					myLog.error(logger, "柜面通来帐对账失败,渠道数据丢失: 交行流水号【" + bocmTraceno + "】核心日期为【" + sysDate + "】渠道流水【"
							+ bocmTrace.getLogNo() + "】");
					BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, sysDate, sysTime, sysTraceno);
					aceModel.setPlatDate(sysDate);
					if(bocmTrace.getLogNo()!=null&&!bocmTrace.getLogNo().equals("")){
						int platTraceno = Integer.parseInt(bocmTrace.getLogNo().substring(6));
						aceModel.setPlatTrace(platTraceno);
					}else{
						aceModel.setPlatTrace(sysTraceno);
					}
					aceModel.setTxDate(sysDate);
					aceModel.setTxCode(bocmTrace.getThdCod());
					aceModel.setTxSource("BU");
					aceModel.setSndBankno(bocmTrace.getSbnkNo());
					aceModel.setTxInd(bocmTrace.getTxnMod());
					aceModel.setPayerBank(bocmTrace.getPayBnk());
					aceModel.setPayerAcno(bocmTrace.getPactNo());
					aceModel.setPayerName(bocmTrace.getPayNam());
					aceModel.setPayeeBank(bocmTrace.getRbnkNo());
					aceModel.setPayeeAcno(bocmTrace.getRactNo());
					aceModel.setPayeeName(bocmTrace.getRecNam());
					aceModel.setPreHostState("0");
					aceModel.setReHostState("1");
					aceModel.setDcFlag("");
					aceModel.setCheckFlag("3");
					aceModel.setDirection("I");
					aceModel.setTxAmt(new BigDecimal(bocmTrace.getTxnAmt()));
					aceModel.setHostState("0");
					aceModel.setBocmState("1");
					aceModel.setBocmFlag("1");
					aceModel.setHostFlag("3");
					aceModel.setCheckFlag("以交行对账为准");
					aceModel.setMsg("渠道缺少来账数据,核心缺少记账数据,需补账");
					acctCheckErrService.insert(aceModel);					
					BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013
							,"与交行来账对账失败,渠道少数据,核心少数据,交行流水号【" +bocmTrace.getTlogNo()+ "】");
					throw e;
				} else {
					checkBocmRcvLog(myLog, sysDate, sysTime, rcvTraceQueryModel, bocmTrace, date + "");
					rcv++;
				}
			}
		}

		
		// 获取未对账的往帐信息
		List<BocmSndTraceQueryModel> sndTraceList = sndTraceService.getCheckSndTrace(myLog, date, sysTime, sysTraceno,
				date.toString());
		myLog.info(logger, "获取未对账的往账账信息记录：【" + sndTraceList.size() + "】");
		for (BocmSndTraceQueryModel model : sndTraceList) {
			BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, model.getPlatDate(), model.getPlatTime(),
					model.getPlatTrace());
			// 如果往账交易类型是交行卡转本行,已交行对账为主，如果存在成功的记账则为多出来的渠道记账信息，需要冲正
			if (model.getTranType().equals("JH11")) {
				
				if (model.getHostState().equals("1")) {
					initSndErrRecord(myLog,model,"渠道多出往账数据,与核心记账不一致");
					myLog.error(logger, "柜面通【" + date + "】对账失败: 多出往账记录，渠道流水号【" + model.getPlatTrace() + "】，核心状态【"
							+ model.getHostState() + "】，通存通兑标志【" + model.getDcFlag() + "】");
					BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013,
							"与交行往账账对账失败，渠道多出往账数据,渠道流水号【" + model.getPlatTrace() + "】");
					throw e;
				} else {
					initSndErrRecord(myLog,model,"渠道多出往账数据,不处理");
					myLog.info(logger, "渠道多出往账数据，渠道日期【" + model.getPlatDate() + "】，渠道流水【" + model.getPlatTrace()
							+ "】，核心状态【" + model.getHostState() + "】，通存通兑标志【" + model.getDcFlag() + "】");
					record.setCheckFlag("4");
					sndTraceService.sndTraceUpd(record);
				}
			}
		}

		// 获取未对账的来账信息,交行无记录的数据
		List<BocmRcvTraceQueryModel> rcvTraceList = rcvTraceService.getCheckRcvTrace(myLog, date, sysTime, sysTraceno,
				date.toString());
		myLog.info(logger, "获取未对账的来账信息记录：【" + rcvTraceList.size() + "】");
		for (BocmRcvTraceQueryModel model : rcvTraceList) {
			BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, model.getPlatDate(), model.getPlatTime(),
					model.getPlatTrace());
			// 如果来账交易类型是他代本通兑转账,不更新对账状态
			if (model.getTranType().equals("JH01") && model.getTxInd().equals("1")) {
				continue;
			}			
			if (model.getHostState().equals("1")) {
				String msg = "渠道多出来账数据,与交行记账不一致";
				initRcvErrRecord(myLog, model, msg, "2", "0");
				myLog.error(logger, "柜面通【" + date + "】对账失败: 多出来账记录，渠道流水号【" + model.getPlatTrace() + "】，核心状态【"
						+ model.getHostState() + "】，通存通兑标志【" + model.getDcFlag() + "】");
				BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013,
						"与交行来账对账失败，渠道多出来账数据,渠道流水号【" + model.getPlatTrace() + "】");
				throw e;
			} else {
				String msg = "渠道多出来账数据,不处理";
				initRcvErrRecord(myLog, model, msg, "0", "0");
				myLog.info(logger, "渠道多出来账数据，渠道日期【" + model.getPlatDate() + "】，渠道流水【" + model.getPlatTrace() + "】，核心状态【"
						+ model.getHostState() + "】，通存通兑标志【" + model.getDcFlag() + "】");
				record.setCheckFlag("4");
				rcvTraceService.rcvTraceUpd(record);
			}
		}


		myLog.info(logger, "外围与交行对账往账记录：【" + snd + "】");
		myLog.info(logger, "外围与交行对账来账记录：【" + rcv + "】");
		myLog.info(logger, "外围与交行对账结束");
		myLog.info(logger, "外围与交行对账成功");
		// 更新对账状态表交行对账状态
		BocmChkStatusModel record = new BocmChkStatusModel();
		record.setTxDate(date);
		record.setBocmStatus(1);
		record.setBocmTxCnt(tolCnt);
		record.setBocmTxAmt(new BigDecimal(tolAmt.toString()));
		chkStatusService.chkStatusUpd(record);
		myLog.info(logger, "更新与核心对账状态为已对账：  对账日期：" + date);

		return rep;
	}

	// 来账对账校验
	private void checkBocmRcvLog(MyLog myLog, int sysDate, int sysTime, BocmRcvTraceQueryModel rcvTraceQueryModel,
			REP_10103.Detail bocmTrace, String date) throws SysTradeExecuteException {
		// 检查交行记账文件来账记录
		String hostState = rcvTraceQueryModel.getHostState(); // 渠道记录的核心记账状态
		String txnStatus = bocmTrace.getTxnSts();
		if ("S".equals(txnStatus)) {
			// 交行存款，现金通兑，交行是转出行，记账以交行为主
			if (rcvTraceQueryModel.getTranType().equals("JH02")
					|| (rcvTraceQueryModel.getTranType().equals("JH01") && rcvTraceQueryModel.getTxInd().equals("0"))) {
				// 交易结果以交行为准
				if (hostState.equals("1")) {
					BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(),
							rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
					record.setCheckFlag("2");
					record.setBocmState("1");
					rcvTraceService.rcvTraceUpd(record);
					myLog.info(logger, "更新渠道对账状态，渠道日期【" + rcvTraceQueryModel.getPlatDate() + "】，渠道流水【"
							+ rcvTraceQueryModel.getPlatTrace() + "】");
				} else {
					BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, rcvTraceQueryModel.getPlatDate(),
							rcvTraceQueryModel.getSysTime(), rcvTraceQueryModel.getPlatTrace());
					aceModel.setPlatDate(rcvTraceQueryModel.getPlatDate());
					aceModel.setPlatTrace(rcvTraceQueryModel.getPlatTrace());
					aceModel.setTxCode(rcvTraceQueryModel.getTxCode());
					aceModel.setTxSource(rcvTraceQueryModel.getSourceType());
					aceModel.setTxDate(rcvTraceQueryModel.getTxDate());
					aceModel.setHostDate(rcvTraceQueryModel.getHostDate());
					aceModel.setHostTraceno(rcvTraceQueryModel.getHostTraceno());
					aceModel.setTxDate(rcvTraceQueryModel.getTxDate());
					aceModel.setSndBankno(rcvTraceQueryModel.getSndBankno());
					aceModel.setTxBranch(rcvTraceQueryModel.getTxBranch());
					aceModel.setTxTel(rcvTraceQueryModel.getTxTel());
					aceModel.setTxInd(rcvTraceQueryModel.getTxInd());
					aceModel.setTxAmt(rcvTraceQueryModel.getTxAmt());
					aceModel.setPayerBank(rcvTraceQueryModel.getPayerBank());
					aceModel.setPayerAcno(rcvTraceQueryModel.getPayerAcno());
					aceModel.setPayerName(rcvTraceQueryModel.getPayerName());
					aceModel.setPayeeBank(rcvTraceQueryModel.getPayeeBank());
					aceModel.setPayeeAcno(rcvTraceQueryModel.getPayeeAcno());
					aceModel.setPayeeName(rcvTraceQueryModel.getPayeeName());
					aceModel.setHostState(rcvTraceQueryModel.getHostState());
					aceModel.setBocmState(rcvTraceQueryModel.getBocmState());
					aceModel.setBocmFlag("1");
					aceModel.setHostFlag("3");
					aceModel.setCheckFlag("以交行对账为准");
					aceModel.setMsg("核心少,需补账");
					// aceModel.setMsg("渠道调整来账数据核心状态，渠道日期【"+rcvTraceQueryModel.getPlatDate()+"】，渠道流水【"+rcvTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+rcvTraceQueryModel.getDcFlag()+"】");
					acctCheckErrService.insert(aceModel);
					myLog.error(logger, "柜面通来帐对账失败,本行记账失败，交行记账成功，流水号【" + rcvTraceQueryModel.getBocmTraceno() + "】核心日期为【"
							+ sysDate + "】渠道流水【" + rcvTraceQueryModel.getPlatTrace() + "】");
					BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013,"与交行来账对账失败，核心少账,需补账,渠道流水号【" + rcvTraceQueryModel.getPlatTrace()
									+ "】");
					throw e;
				}
			}
		} else if ("F".equals(txnStatus)) {
			// 交行卡付款转账（磁条卡和IC卡） 通兑（交行转出行）交易结果以交行为准
			if (rcvTraceQueryModel.getTranType().equals("JH02")
					|| (rcvTraceQueryModel.getTranType().equals("JH01") && rcvTraceQueryModel.getTxInd().equals("0"))) {
				// 交易结果以交行为准,核心记账成功，对账失败(交行记账失败返回)
				if (hostState.equals("1")) {
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
					aceModel.setProxyFee(rcvTraceQueryModel.getProxy_fee());
					aceModel.setProxyFlag(rcvTraceQueryModel.getProxy_flag());
					aceModel.setPayerBank(rcvTraceQueryModel.getPayerBank());
					aceModel.setPayerAcno(rcvTraceQueryModel.getPayerAcno());
					aceModel.setPayerName(rcvTraceQueryModel.getPayerName());
					aceModel.setPayeeBank(rcvTraceQueryModel.getPayeeBank());
					aceModel.setPayeeAcno(rcvTraceQueryModel.getPayeeAcno());
					aceModel.setPayeeName(rcvTraceQueryModel.getPayeeName());
					aceModel.setHostState(rcvTraceQueryModel.getHostState());
					aceModel.setBocmState(rcvTraceQueryModel.getBocmState());
					aceModel.setBocmFlag("0");
					aceModel.setHostFlag("2");
					aceModel.setCheckFlag("以交行对账为准");
					aceModel.setMsg("核心多账,需冲正");
					// aceModel.setMsg("渠道调整来账数据核心状态，渠道日期【"+rcvTraceQueryModel.getPlatDate()+"】，渠道流水【"+rcvTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+rcvTraceQueryModel.getDcFlag()+"】");
					acctCheckErrService.insert(aceModel);
					myLog.error(logger, "柜面通来帐对账失败,本行记账成功，交行记账失败，流水号【" + rcvTraceQueryModel.getBocmTraceno() + "】核心日期为【"
							+ sysDate + "】渠道流水【" + rcvTraceQueryModel.getPlatTrace() + "】");
					BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013,"与交行来账对账失败，核心多账,需冲正,渠道流水号【" + rcvTraceQueryModel.getPlatTrace()
									+ "】");
					throw e;
				} else {
					BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(),
							rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
					record.setCheckFlag("2");
					record.setHostState("2");
					record.setBocmState("2");
					rcvTraceService.rcvTraceUpd(record);
					myLog.info(logger, "渠道更新来账数据已交行为主记账状态，渠道日期【" + rcvTraceQueryModel.getPlatDate() + "】，渠道流水【"
							+ rcvTraceQueryModel.getPlatTrace() + "】");
				}

			}
		}
	}

	// 往账流水对账
	private void checkBocmSndLog(MyLog myLog, int sysDate, int sysTime, BocmSndTraceQueryModel sndTraceQueryModel,
			REP_10103.Detail bocmTrace, String date) throws SysTradeExecuteException {

		String bocmState = sndTraceQueryModel.getBocmState(); // 渠道记录的交行记账状态
		String hostState = sndTraceQueryModel.getHostState(); // 渠道记录的核心记账状态
		String txnStatus = bocmTrace.getTxnSts();

		if ("S".equals(txnStatus)) {
			// 交行卡付款转账(JH11)（磁条卡和IC卡） 通兑（交行转出行）交易结果以交行为准
			if (sndTraceQueryModel.getTranType().equals("JH11")) {
				// 交易结果以交行为准
				if (hostState.equals("1")) {
					BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(),
							sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
					record.setCheckFlag("2");
					record.setBocmState("1");
					sndTraceService.sndTraceUpd(record);
					myLog.info(logger, "更新渠道对账状态，渠道日期【" + sndTraceQueryModel.getPlatDate() + "】，渠道流水【"
							+ sndTraceQueryModel.getPlatTrace() + "】");
				} else {
					// 我行往账与核心对账对账结果核心状态只为1，其他状态对账失败
					BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, sndTraceQueryModel.getPlatDate(),
							sndTraceQueryModel.getSysTime(), sndTraceQueryModel.getPlatTrace());
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
					aceModel.setCheckFlag("以交行对账为准");
					aceModel.setBocmFlag("1");
					aceModel.setHostFlag("0");
					aceModel.setMsg("核心少账,需补账");
					acctCheckErrService.insert(aceModel);
					myLog.error(logger,
							"柜面通【" + date + "】往帐对账失败: 交行记账成功,记账以交行为主,渠道流水号【" + sndTraceQueryModel.getPlatTrace()
									+ "】记录核心状态为【" + sndTraceQueryModel.getHostState() + "】");
					BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013,"与交行往账对账失败，核心少账,需补账,渠道流水号【" + sndTraceQueryModel.getPlatTrace()
									+ "】");
					throw e;
				}
			}
		} else if ("F".equals(txnStatus)) {
			// 交行卡付款转账（磁条卡和IC卡） 通兑（交行转出行）交易结果以交行为准
			if (sndTraceQueryModel.getTranType().equals("JH11")) {
				// 交易结果以交行为准，如果交行记账失败，核心记账成功对账失败
				if (hostState.equals("1")) {
					myLog.info(logger, "更新渠道对账状态，渠道日期【" + sndTraceQueryModel.getPlatDate() + "】，渠道流水【"
							+ sndTraceQueryModel.getPlatTrace() + "】");
					BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, sndTraceQueryModel.getPlatDate(),
							sndTraceQueryModel.getSysTime(), sndTraceQueryModel.getPlatTrace());
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
					aceModel.setCheckFlag("以交行对账为准");
					aceModel.setBocmFlag("0");
					aceModel.setHostFlag("2");
					aceModel.setMsg("核心多账,需冲正");
					acctCheckErrService.insert(aceModel);
					myLog.error(logger,
							"柜面通【" + date + "】往帐对账失败:交行记账失败,记账以交行为主,渠道流水号【" + sndTraceQueryModel.getPlatTrace()
									+ "】记录核心状态为【" + sndTraceQueryModel.getHostState() + "】");
					BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
					throw e;
				} else {
					BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(),
							sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
					record.setCheckFlag("2");
					record.setHostState("2");
					sndTraceService.sndTraceUpd(record);
					myLog.info(logger,
							"渠道更新往账数据已交行为主记账状态，渠道日期【" + sndTraceQueryModel.getPlatDate() + "】，渠道流水【"
									+ sndTraceQueryModel.getPlatTrace() + "】，" + "交行记账调整前状态【" + bocmState
									+ "】，调整后状态【1】，核心记账调整前状态【" + hostState + "】，调整后状态【1】");
				}

			}
		}
	}
	
	private void initSndErrRecord(MyLog myLog, BocmSndTraceQueryModel sndTraceQueryModel, String msg)
			throws SysTradeExecuteException {
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
		aceModel.setCheckFlag("以交行对账为准");
		aceModel.setMsg(msg);
		myLog.error(logger, "插入调账明细表：渠道流水号【" + sndTraceQueryModel.getPlatTrace() + "】");
		acctCheckErrService.insert(aceModel);
	}

	private void initRcvErrRecord(MyLog myLog, BocmRcvTraceQueryModel rcvTraceQueryModel, String msg
			,String hostFlag, String bocmFlag)
			throws SysTradeExecuteException {
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
		 aceModel.setProxyFee(rcvTraceQueryModel.getProxy_fee());
		 aceModel.setProxyFlag(rcvTraceQueryModel.getProxy_flag());
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
		aceModel.setCheckFlag("以交行对账为准");
		aceModel.setMsg(msg);
		// aceModel.setMsg("渠道调整来账数据核心状态，渠道日期【"+rcvTraceQueryModel.getPlatDate()+"】，渠道流水【"+rcvTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+rcvTraceQueryModel.getDcFlag()+"】");
		acctCheckErrService.insert(aceModel);
	}

}
