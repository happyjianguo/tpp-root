package com.fxbank.tpp.bocm.trade.bocm;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.model.ModelBase;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.pub.service.IPublicService;
import com.fxbank.tpp.bocm.dto.bocm.REP_10001;
import com.fxbank.tpp.bocm.dto.bocm.REQ_10000;
import com.fxbank.tpp.bocm.dto.bocm.REQ_10001;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmRcvTraceInitModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceUpdModel;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.bocm.service.IBocmSafeService;
import com.fxbank.tpp.bocm.util.NumberUtil;
import com.fxbank.tpp.esb.common.ESB;
import com.fxbank.tpp.esb.common.EsbReqHeaderBuilder;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30011000104;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30011000104.Fee;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30014000101;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30033000202;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30033000203;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30011000104;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30014000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30033000203;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30043000101;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import redis.clients.jedis.Jedis;


/** 
* @ClassName: WD_FxMag 
* @Description: 交行向本行发起磁条卡通兑记账请求
* @author Duzhenduo
* @date 2019年4月18日 上午10:59:30 
*  
*/
@Service("REQ_10001")
public class WD_FxMag extends BaseTradeT1 implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(WD_FxMag.class);

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IBocmRcvTraceService bocmRcvTraceService;
	
    @Reference(version = "1.0.0")
    private IBocmSafeService safeService;

	@Resource
	private LogPool logPool;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "bocm.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();		
		REQ_10001 req = (REQ_10001) dto;
		
		String sbnkNo = req.getSbnkNo();//发起行行号
		if(sbnkNo.substring(0, 3).equals("313")){
			myLog.info(logger, "交易发起行为本行，启用挡板数据");
			REP_10001 rep = new REP_10001();
			
			String sDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			rep.setSysDate(Integer.valueOf(sDate.substring(0, 8)));
			rep.setSysTime(Integer.valueOf(sDate.substring(8))); 
			
			rep.setOtxnAmt(req.getTxnAmt());		
			//JHF1-异地手续费JHF2-代理手续费
			Double fee = new Double(5d);
			rep.setFee(fee);
			rep.setActBal(10000d);
			return rep;
		}
		
		//风险检查
		//风险监控检查调用
		super.payerAcno = req.getPactNo();
		super.payeeAcno = req.getRactNo();
		String txnAmt = NumberUtil.removePointToString(req.getTxnAmt());
		super.amt = (long)Double.parseDouble(txnAmt);
		super.bizChnl = "F";
		super.bizCode = "F01";
		
		
		myLog.info(logger, "流水号："+req.getSlogNo()+"  渠道流水："+req.getSysTraceno());
		if(req.getSlogNo()==null||req.getSlogNo().trim().equals("")){
			BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10014,"交易流水号为空");
			throw e2;
		}
		
		super.hostErrorException = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10004);
		super.cardMagValidateException = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10007);
		super.hostTimeoutException = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_16203);
		super.othTimeoutException = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_16203);
		super.TRADE_DESC = "交行向本行发起磁条卡通兑记账请求";
		super.othTimeoutQuery = false;
		super.logger = logger;		
		return super.execute(req);
	}
	public ESB_REP_30033000202 validateIC(DataTransObject dto) throws SysTradeExecuteException {
		return null;
	}
	
	public ESB_REP_30033000203 validateMag(DataTransObject dto) throws SysTradeExecuteException {		
		MyLog myLog = logPool.get();
		REQ_10001 reqDto = (REQ_10001) dto;
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "TXBRNO");
			txTel = jedis.get(COMMON_PREFIX + "TXTEL");
		}

		ESB_REQ_30033000203 esbReq_30033000203 = new ESB_REQ_30033000203(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		/** Add by 叶浦亮 At 2019/12/3 15:48 For 不同渠道平台调用核心接口使用不一样的systemID */
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30033000203.getReqSysHead(), reqDto.getSourceType(),reqDto.getSysDate(),reqDto.getSysTime(),reqDto.getSysTraceno())
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30033000203.setReqSysHead(reqSysHead);

		ESB_REQ_30033000203.REQ_BODY reqBody_30033000203 = esbReq_30033000203.getReqBody();
		reqBody_30033000203.setCardNo(reqDto.getPactNo());
		reqBody_30033000203.setScdTrk(reqDto.getSecMag());
		reqBody_30033000203.setCurDate(reqDto.getTtxnDat().toString());

		ESB_REP_30033000203 esbRep_30033000203 = forwardToESBService.sendToESB(esbReq_30033000203, reqBody_30033000203,
				ESB_REP_30033000203.class);
		return esbRep_30033000203;
	}
	
	
	/** 
	* @Title: backMsg 
	* @Description: 给柜面输出 
	* @param @param dto
	* @param @param model
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public DataTransObject backMsg(DataTransObject dto,ModelBase model) throws SysTradeExecuteException {
		REQ_10001 reqDto = (REQ_10001) dto;
		REP_10001 rep = new REP_10001();
		ESB_REP_30011000104 repPayment = (ESB_REP_30011000104)model;
		rep.setOtxnAmt(reqDto.getTxnAmt());		
		Double fee = new Double(0);
		List<Fee> feeList = repPayment.getRepBody().getFeeDetail();
		for(Fee temp:feeList) {
			if(!temp.getFeeAmt().equals("")){
				fee = Double.valueOf(temp.getFeeAmt());
			}
		}
		double actbal = NumberUtil.addPoint(Double.parseDouble(repPayment.getRepBody().getAvailBal()));
		fee = NumberUtil.addPoint(fee);
		rep.setActBal(actbal);
		rep.setFee(fee);
		
		return rep;
	}
	
	
	/** 
	* @Title: backMsg 
	* @Description: 给柜面输出 
	* @param @param dto
	* @param @param model
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public DataTransObject backMsgOnTradeHave(DataTransObject dto,ModelBase rcvModel) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		myLog.info(logger, "交易已经存在，根据渠道记录的数据返回报文");
		REP_10001 rep = new REP_10001();
		BocmRcvTraceQueryModel model = (BocmRcvTraceQueryModel)rcvModel;
		//通过model组装返回报文
		double txAmt = Double.parseDouble(model.getTxAmt().toString());
		rep.setOtxnAmt(NumberUtil.addPoint(txAmt));
		
		if(model.getActBal()!=null){
			double actBal = Double.parseDouble(model.getActBal().toString());
			rep.setActBal(NumberUtil.addPoint(actBal));
		}
		if(model.getFee()!=null){
			double fee = Double.parseDouble(model.getFee().toString());
			rep.setFee(NumberUtil.addPoint(fee));
		}
		
		return rep;
	}
	
	/** 
	* @Title: hostCharge 
	* @Description: 本行核心记账 
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30011000103    返回类型 
	* @throws 
	*/
	public ESB_REP_30011000104 hostCharge(DataTransObject dto) throws SysTradeExecuteException {
		REQ_10001 reqDto = (REQ_10001) dto;
		MyLog myLog = logPool.get();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		// 加密节点编码
		String sourceNo = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "TXBRNO");
			txTel = jedis.get(COMMON_PREFIX + "TXTEL");
			sourceNo = jedis.get(COMMON_PREFIX + "SOURCE");
		}

		ESB_REQ_30011000104 esbReq_30011000104 = new ESB_REQ_30011000104(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		/** Add by 叶浦亮 At 2019/12/3 15:48 For 不同渠道平台调用核心接口使用不一样的systemID */
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000104.getReqSysHead(), reqDto.getSourceType(),reqDto.getSysDate(),reqDto.getSysTime(),reqDto.getSysTraceno())
				.setBranchId(txBrno).setUserId(txTel).build();	
		reqSysHead.setSourceBranchNo(sourceNo);
		reqSysHead.setSourceType("BU");	
		esbReq_30011000104.setReqSysHead(reqSysHead);
		reqSysHead.setDestBranchNo(txBrno);
		
		esbReq_30011000104.setReqSysHead(reqSysHead);

		ESB_REQ_30011000104.REQ_BODY reqBody_30011000104 = esbReq_30011000104.getReqBody();
		
		reqBody_30011000104.setBaseAcctNo(reqDto.getPactNo());
		reqBody_30011000104.setAcctName(reqDto.getPayNam());		
		
		//交行转阜新pin
		String pin = safeService.transPinToFX(myLog, reqDto.getPactNo(), reqDto.getPactNo(), reqDto.getPin());
		reqBody_30011000104.setPassword(pin);
		
		//JH01-交行代理我行账户付款
		//JH02-交行代理我行账户存款
		//JH10-本行卡付款转账
		//JH11-交行卡转账
		//JH12-交行卡存现金
		reqBody_30011000104.setTranType("JH01");
		reqBody_30011000104.setTranCcy("CNY");

		Double txnAmt = NumberUtil.removePoint(reqDto.getTxnAmt());
		reqBody_30011000104.setTranAmt(txnAmt.toString());
		
		reqBody_30011000104.setWithdrawalType("P");
		reqBody_30011000104.setOthBaseAcctNo(reqDto.getRactNo());
		reqBody_30011000104.setOthBaseAcctName(reqDto.getRecNam());
		reqBody_30011000104.setChannelType("BU");
		//SEND_BANK_CODE	 发起行行号
		//BANK_CODE	                        我方银行行号
		//OTH_BANK_CODE	            对方银行行号
		reqBody_30011000104.setSendBankCode(reqDto.getSbnkNo());
		reqBody_30011000104.setBankCode(reqDto.getPayBnk());
		reqBody_30011000104.setOthBankCode(reqDto.getRecBnk());
		//记账系统日期
		//String settlementDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		reqBody_30011000104.setSettlementDate(dto.getSysDate()+"");
		reqBody_30011000104.setCollateFlag("Y");
		reqBody_30011000104.setDirection("I");

		ESB_REP_30011000104 esbRep_30011000104 = forwardToESBService.sendToESB(esbReq_30011000104, reqBody_30011000104,
				ESB_REP_30011000104.class);
		return esbRep_30011000104;
	}
	
	
	/** 
	* @Title: hostSuccessInitLog 
	* @Description: 核心记账成功登记
	* @param @param dto
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void hostSuccessInitLog(DataTransObject dto, ModelBase model) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_10001 reqDto = (REQ_10001) dto;
		ESB_REP_30011000104 rep = (ESB_REP_30011000104) model;
		
		BocmRcvTraceInitModel record = new BocmRcvTraceInitModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "TXBRNO");
			txTel = jedis.get(COMMON_PREFIX + "TXTEL");
		}	
		record.setSourceType(reqDto.getSourceType());
		record.setTxBranch(txBrno);
		record.setTxTel(txTel);
		// 通存通兑标志；0通存、1通兑
		record.setDcFlag("1");
		record.setTranType("JH01");
		String txnAmt = NumberUtil.removePointToString(reqDto.getTxnAmt());
		//记录核心收取手续费
		ESB_REP_30011000104.Fee tradFee = rep.getRepBody().getFeeDetail().get(0);	
		if(!tradFee.getFeeAmt().equals("")){
			record.setFee(new BigDecimal(tradFee.getFeeAmt()));
		}else{
			record.setFee(new BigDecimal("0"));
		}		
		record.setTxAmt(new BigDecimal(txnAmt));
		record.setActBal(new BigDecimal(rep.getRepBody().getAvailBal()));
		//现转标志；0现金、1转账
		record.setTxInd(reqDto.getTxnMod());
		record.setHostState("1");
		record.setHostDate(Integer.parseInt(rep.getRepSysHead().getTranDate()));
		record.setHostTraceno(rep.getRepBody().getReference());
		record.setRetCode(rep.getRepSysHead().getRet().get(0).getRetCode());
		record.setRetMsg(rep.getRepSysHead().getRet().get(0).getRetMsg());

		record.setSndBankno(reqDto.getSbnkNo());
		record.setRcvBankno(reqDto.getRbnkNo());
		record.setPayerBank(reqDto.getPayBnk());
		record.setPayerActtp(reqDto.getPactTp());
		record.setPayeeBank(reqDto.getRecBnk());
		record.setPayeeActtp(reqDto.getRactTp());
		
		record.setPayerAcno(reqDto.getPactNo());
		record.setPayerName(reqDto.getPayNam());
		record.setPayeeAcno(reqDto.getRactNo());
		record.setPayeeName(reqDto.getRecNam());
		record.setPrint("0");
		record.setCheckFlag("1");
		
		record.setBocmState("0");
		//交行流水号
		record.setBocmTraceNo(reqDto.getSlogNo());		
		record.setBocmDate(reqDto.getTtxnDat());
		record.setBocmTime(reqDto.getTtxnTim());
		//发起行行号
		record.setBocmBranch(reqDto.getSbnkNo());
		//交易码
		record.setTxCode(reqDto.getTtxnCd());
		//记账系统日期
		//String settlementDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		record.setTxDate(dto.getSysDate());	
		bocmRcvTraceService.rcvTraceInit(record);
		myLog.info(logger,TRADE_DESC+"插入来账流水表，核心日期"+rep.getRepSysHead().getTranDate()+"核心流水号"+rep.getRepSysHead().getReference());
		
		
	}
	
	public BocmRcvTraceQueryModel queryRcvTrace(DataTransObject dto) throws SysTradeExecuteException{
		REQ_10001 reqDto = (REQ_10001) dto;
		MyLog myLog = logPool.get();
		BocmRcvTraceQueryModel model = null;
		int townDate = reqDto.getTtxnDat();
		String townTraceno = reqDto.getSlogNo();
		myLog.info(logger,TRADE_DESC+",查询来账流水，交行流水号："+townTraceno);
		model = bocmRcvTraceService.getConfirmTrace(myLog, townDate, townTraceno);
		return model;
	}
	
	/** 
	* @Title: hostTranResult 
	* @Description: 核心交易结果查询
	* @param reqDto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30043000101  返回类型 
	* @throws 
	*/
	public ESB_REP_30043000101 hostTranResult(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_10001 reqDto = (REQ_10001) dto;
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		// 加密节点编码
		String sourceNo = null;
		try(Jedis jedis = myJedis.connect()){
			txBrno = jedis.get(COMMON_PREFIX+"TXBRNO");
			txTel = jedis.get(COMMON_PREFIX+"TXTEL");
			sourceNo = jedis.get(COMMON_PREFIX + "SOURCE");
        }	

		ESB_REQ_30043000101 esbReq_30043000101 = new ESB_REQ_30043000101(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		/** Add by 叶浦亮 At 2019/12/3 15:48 For 不同渠道平台调用核心接口使用不一样的systemID */
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30043000101.getReqSysHead(), reqDto.getSourceType(),reqDto.getSysDate(),reqDto.getSysTime(),reqDto.getSysTraceno())
				.setBranchId(txBrno).setUserId(txTel)
				.build();
		reqSysHead.setSourceBranchNo(sourceNo);
		reqSysHead.setSourceType("BU");
		
		esbReq_30043000101.setReqSysHead(reqSysHead);

		ESB_REQ_30043000101.REQ_BODY reqBody_30043000101 = esbReq_30043000101.getReqBody();
		// 记账渠道类型GJ
		reqBody_30043000101.setChannelType("BU");

		String platTrace = String.format("%08d", reqDto.getSysTraceno());// 左补零
		// 渠道流水号
		reqBody_30043000101.setChannelSeqNo(ESB.SYSTEM_ID + reqDto.getSysDate() + platTrace);
		ESB_REP_30043000101 esb_rep_30043000101 = null;
		try {
			// 如果第一次查询没查到内容再查询一次
			esb_rep_30043000101 = forwardToESBService.sendToESB(esbReq_30043000101, reqBody_30043000101,
					ESB_REP_30043000101.class);
		} catch (SysTradeExecuteException e) {
			if (e.getRspCode().equals("RB4029")) {
				try {
					esb_rep_30043000101 = forwardToESBService.sendToESB(esbReq_30043000101, reqBody_30043000101,
							ESB_REP_30043000101.class);
				} catch (SysTradeExecuteException e1) {
					if (e1.getRspCode().equals("RB4029")) {
						try {
							esb_rep_30043000101 = forwardToESBService.sendToESB(esbReq_30043000101, reqBody_30043000101,
									ESB_REP_30043000101.class);
						} catch (SysTradeExecuteException e2) {
							if (e2.getRspCode().equals("RB4029")) {
								return esb_rep_30043000101;
							} else {
								logger.error(e2.getRspCode() + " | " + e2.getRspMsg());
								throw e2;
							}

						}
					} else {
						logger.error(e1.getRspCode() + " | " + e1.getRspMsg());
						throw e1;
					}
				}
			} else {
				logger.error(e.getRspCode() + " | " + e.getRspMsg());
				throw e;
			}
		}

		return esb_rep_30043000101;
	}
	
	/** 
	* @Title: hostTimeoutInitLog 
	* @Description: 核心记账超时登记
	* @param @param dto
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void hostTimeoutInitLog(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_10001 reqDto = (REQ_10001) dto;

		
		BocmRcvTraceInitModel record = new BocmRcvTraceInitModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "TXBRNO");
			txTel = jedis.get(COMMON_PREFIX + "TXTEL");
		}	
		record.setSourceType(reqDto.getSourceType());
		record.setTxBranch(txBrno);
		record.setTxTel(txTel);
		// 通存通兑标志；0通存、1通兑
		record.setDcFlag("1");
		record.setTranType("JH01");
		String txnAmt = NumberUtil.removePointToString(reqDto.getTxnAmt());
		record.setTxAmt(new BigDecimal(txnAmt));
		//现转标志；0现金、1转账
		record.setTxInd(reqDto.getTxnMod());
		
		record.setHostState("3");
		record.setHostTraceno("");
		record.setRetCode("");
		record.setRetMsg("核心记账超时");


		record.setSndBankno(reqDto.getSbnkNo());
		record.setRcvBankno(reqDto.getRbnkNo());
		record.setPayerBank(reqDto.getPayBnk());
		record.setPayerActtp(reqDto.getPactTp());
		record.setPayeeBank(reqDto.getRecBnk());
		record.setPayeeActtp(reqDto.getRactTp());
		
		record.setPayerAcno(reqDto.getPactNo());
		record.setPayerName(reqDto.getPayNam());
		record.setPayeeAcno(reqDto.getRactNo());
		record.setPayeeName(reqDto.getRecNam());
		record.setPrint("0");
		record.setCheckFlag("1");
		
		record.setBocmState("0");
		//交行流水号
		record.setBocmTraceNo(reqDto.getSlogNo());		
		record.setBocmDate(reqDto.getTtxnDat());
		record.setBocmTime(reqDto.getTtxnTim());
		//发起行行号
		record.setBocmBranch(reqDto.getSbnkNo());
		//交易码
		record.setTxCode(reqDto.getTtxnCd());
		//记账系统日期
		//String settlementDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		record.setTxDate(dto.getSysDate());	
		bocmRcvTraceService.rcvTraceInit(record);
		myLog.info(logger,TRADE_DESC+"，核心记账超时，插入来账流水表");
		
	}
	
	public void updateTimeoutSuccess(DataTransObject dto, ModelBase model) throws SysTradeExecuteException{
		MyLog myLog = logPool.get();
		REQ_10000 reqDto = (REQ_10000) dto;
		ESB_REP_30043000101 rep = (ESB_REP_30043000101) model;
		BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());		
		record.setHostState("1");
		record.setHostTraceno(rep.getRepBody().getReference());
		record.setRetCode(rep.getRepSysHead().getRet().get(0).getRetCode());
		record.setRetMsg(rep.getRepSysHead().getRet().get(0).getRetMsg());
		bocmRcvTraceService.rcvTraceUpd(record);

	}
	
	public void updateOthSuccess(DataTransObject dto, ModelBase model) throws SysTradeExecuteException{
		MyLog myLog = logPool.get();
		REQ_10001 reqDto = (REQ_10001) dto;
		ESB_REP_30011000104 rep = (ESB_REP_30011000104) model;
		BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());		
		record.setHostState("1");
		record.setHostTraceno(rep.getRepBody().getReference());
		record.setRetCode(rep.getRepSysHead().getRet().get(0).getRetCode());
		record.setRetMsg(rep.getRepSysHead().getRet().get(0).getRetMsg());
		bocmRcvTraceService.rcvTraceUpd(record);

	}
	
	/** 
	* @Title: hostReversal 
	* @Description: 本行核心冲正
	* @param @param reqDto
	* @param @param hostSeqno
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30014000101    返回类型 
	* @throws 
	*/
	public ESB_REP_30014000101 hostReversal(DataTransObject dto)
			throws SysTradeExecuteException {
		
		MyLog myLog = logPool.get();
		REQ_10001 reqDto = (REQ_10001)dto;
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		// 加密节点编码
		String sourceNo = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "TXBRNO");
			txTel = jedis.get(COMMON_PREFIX + "TXTEL");
			sourceNo = jedis.get(COMMON_PREFIX + "SOURCE");
		}
		ESB_REQ_30014000101 esbReq_30014000101 = new ESB_REQ_30014000101(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		/** Add by 叶浦亮 At 2019/12/3 15:48 For 不同渠道平台调用核心接口使用不一样的systemID */
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30014000101.getReqSysHead(), reqDto.getSourceType(),reqDto.getSysDate(),reqDto.getSysTime(),reqDto.getSysTraceno())
				.setBranchId(txBrno).setUserId(txTel).build();
		
		reqSysHead.setSourceBranchNo(sourceNo);
		reqSysHead.setSourceType("BU");
		
		esbReq_30014000101.setReqSysHead(reqSysHead);

		ESB_REQ_30014000101.REQ_BODY reqBody_30014000101 = esbReq_30014000101.getReqBody();
		esbReq_30014000101.setReqSysHead(reqSysHead);	
		reqBody_30014000101.setChannelSeqNo(esbReq_30014000101.getReqSysHead().getSeqNo());
		reqBody_30014000101.setReversalReason("本行核心记账超时,本行核心冲正");
		reqBody_30014000101.setEventType("");

		ESB_REP_30014000101 esbRep_30014000101 = forwardToESBService.sendToESB(esbReq_30014000101, reqBody_30014000101,
				ESB_REP_30014000101.class);
		return esbRep_30014000101;
	}
}