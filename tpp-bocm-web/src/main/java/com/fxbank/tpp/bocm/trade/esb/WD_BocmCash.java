/**   
* @Title: WD_BocmCash.java 
* @Package com.fxbank.tpp.bocm.trade.esb 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年4月16日 下午3:03:04 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.trade.esb;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.bocm.dto.esb.REP_30061800401;
import com.fxbank.tpp.bocm.dto.esb.REQ_30061800301;
import com.fxbank.tpp.bocm.dto.esb.REQ_30061800401;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmSndTraceInitModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceUpdModel;
import com.fxbank.tpp.bocm.model.REP_10001;
import com.fxbank.tpp.bocm.model.REP_10009;
import com.fxbank.tpp.bocm.model.REP_20001;
import com.fxbank.tpp.bocm.model.REQ_10001;
import com.fxbank.tpp.bocm.model.REQ_10009;
import com.fxbank.tpp.bocm.model.REQ_20001;
import com.fxbank.tpp.bocm.service.IBocmSndTraceService;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30011000104;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30011000104;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import redis.clients.jedis.Jedis;

/** 
* @ClassName: WD_BocmCash 
* @Description: 交行卡取现金
* @author YePuLiang
* @date 2019年4月16日 下午3:03:04 
*  
*/
@Service("REQ_30061800401")
public class WD_BocmCash extends TradeBase implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(WD_BocmCash.class);
	
	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IForwardToBocmService forwardToBocmService;
	
	@Reference(version = "1.0.0")
	private IBocmSndTraceService bocmSndTraceService;

	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "bocm.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061800401 reqDto = (REQ_30061800401) dto;
		REQ_30061800401.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30061800401 rep = new REP_30061800401();
		//1.登记通兑往账流水表
		initRecord(reqDto);		
		myLog.info(logger, "交行卡取现金，登记成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
		//2.交行记账    IC卡和磁条卡走不同的交行接口
		//交行记账流水号
		String bocmTraceNo = null;
		//卡类型  磁条还是IC卡
		String IC_CARD_FLG_T4 = reqBody.getIcCardFlgT4();//IC卡磁条卡标志
		String bocmBranch = null;
		String bocmDate = null;	
		if("0".equals(IC_CARD_FLG_T4)){
			//磁条卡的通兑10001
			myLog.info(logger, "发送磁条卡通兑请求至交行");
			REP_10001 rep10001 = null;
			try {
				rep10001 = magCardCharge(reqDto);
				bocmTraceNo = rep10001.getHeader().getrLogNo();
				//手续费
				rep.getRepBody().setFeeT3(rep10001.getFee());
				//余额
				rep.getRepBody().setBalance3T(rep10001.getActBal());
				//3.更新流水表交行记账状态
				//交行记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败
				updateBocmRecord(reqDto,bocmTraceNo,"1");
				myLog.info(logger, "交行卡取现金，交行磁条卡通兑记账成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
			} catch (SysTradeExecuteException e) {
				throw e;
			}				
		}else{
			//IC卡的通兑20001
			myLog.info(logger, "发送IC卡通兑请求至交行");
			REP_20001 rep20001 = null;
			try {
				rep20001 = iCCardCharge(reqDto);
				bocmTraceNo = rep20001.getHeader().getrLogNo();
				//手续费
				rep.getRepBody().setFeeT3(rep20001.getFee());
				//余额
				rep.getRepBody().setBalance3T(rep20001.getActBal());
				//3.更新流水表交行记账状态
				//交行记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败
				updateBocmRecord(reqDto,bocmTraceNo,"1");
				myLog.info(logger, "交行卡取现金，交行IC卡通兑记账成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
			} catch (SysTradeExecuteException e) {
				throw e;
			}	
		}
		//4.核心记账  暂时不考虑延时转账
		ESB_REP_30011000104 esbRep_30011000104 = null;
		//核心记账日期
		String hostDate = null;
		//核心记账流水号
		String hostTraceno = null;
		//核心记账返回状态码
		String retCode = null;
		//核心记账返回状态信息
		String retMsg = null;
		//记账机构
		String accounting_branch = null;
		try {
			esbRep_30011000104 = hostCharge(reqDto);
			hostDate = esbRep_30011000104.getRepSysHead().getRunDate();
			hostTraceno = esbRep_30011000104.getRepBody().getReference();
			retCode = esbRep_30011000104.getRepSysHead().getRet().get(0).getRetCode();
			retMsg = esbRep_30011000104.getRepSysHead().getRet().get(0).getRetMsg();			
			accounting_branch = esbRep_30011000104.getRepBody().getAcctBranch();
		}catch(SysTradeExecuteException e) {			
			//商行核心记账失败，交行冲正
			//5.更新流水表核心记账状态
			updateHostRecord(reqDto, "", "", "2", e.getRspCode(), e.getRspMsg(),"");
			myLog.error(logger, "商行通兑交行核心记账失败，渠道日期" + reqDto.getSysDate() + 
					"渠道流水号" + reqDto.getSysTraceno(), e);
			//交行冲正
			try {	
				 bocmReversal(reqDto, bocmDate, bocmTraceNo);
			}catch(SysTradeExecuteException e1) {
				//6.更新流水表交行记账状态
				//交行记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败
				updateBocmRecord(reqDto, "", "6");
				myLog.error(logger, "商行通兑交行冲正失败，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e1);
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10005,e.getRspMsg()+"(交行冲正失败)");
				throw e2;
			}
			//6.更新流水表村镇记账状态
			// 村镇记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败
			updateBocmRecord(reqDto, "", "5");
			BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10006,e.getRspMsg()+"(交行冲正成功)");
			myLog.error(logger, "商行通兑村镇村镇冲正成功，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(),e2);
			throw e2;
		}
		//5.更新流水表核心记账状态
		myLog.info(logger, "商行通兑交行核心记账成功，渠道日期" + reqDto.getSysDate() + 
				"渠道流水号" + reqDto.getSysTraceno());
		updateHostRecord(reqDto, hostDate, hostTraceno, "1", retCode, retMsg,accounting_branch);
		return rep;
	}
	
	private void initRecord(REQ_30061800401 reqDto) throws SysTradeExecuteException{
		MyLog myLog = logPool.get();
		BocmSndTraceInitModel record = new BocmSndTraceInitModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
		record.setSourceType(reqDto.getSourceType());
		record.setTxBranch(reqDto.getReqSysHead().getBranchId());
		record.setTxInd("");//现转标志
		record.setDcFlag("1");//通存通兑标志 0通存、1通兑
		record.setTxAmt("");//交易金额
		record.setPayerAcno("payer");//付款人账户
		record.setPayerName("payerName");//付款人姓名
		record.setPayeeAcno("payeeAcno");//收款人账户
		record.setPayeeName("payeeName");//收款人姓名
		record.setBocmBranch("");//交通银行记账机构
		record.setCheckFlag("1");//对账标志
		record.setHostState("0");//核心记账状态
		record.setBocmState("0");//交行记账状态
		record.setAuthTel(record.getAuthTel());
		record.setTxTel(record.getTxTel());
		record.setChkTel(record.getChkTel());
		record.setPrint(record.getPrint());
		record.setInfo(record.getInfo());		
		bocmSndTraceService.sndTraceInit(record);
	}
	
	/** 
	* @Title: magCardCharge 
	* @Description: 交行磁条卡通兑记账
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return REP_10000    返回类型 
	* @throws 
	*/
	private REP_10001 magCardCharge(REQ_30061800401 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_10001 req10001 = new REQ_10001(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
		super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req10001.getHeader()); // 设置报文头中的行号信息
		REQ_30061800401.REQ_BODY reqBody = reqDto.getReqBody();
		req10001.setTxnAmt(reqBody.getWthrAmtT());//交易金额
		req10001.setPin(reqBody.getPwdT());//交易密码
		req10001.setOprFlg("0");//卡输入方式  通兑必须刷卡
		req10001.setTxnMod("0");//业务模式 0 现金 1 转账（实时转账）
		req10001.setPayBnk(reqBody.getOpnAcctBnkNoT7());//付款人开户行行号
		req10001.setpActTp(reqBody.getAcctNoTpT());//付款人账户类型
		req10001.setpActNo(reqBody.getCardNoT3());//付款人账号
		req10001.setPayNam(reqBody.getNmT());//付款人名称
		req10001.setCuIdTp(reqBody.getIdTpT2());//证件种类
		req10001.setCuIdNo(reqBody.getHldrGlblIdT());//证件号码
		req10001.setAgIdTp(reqBody.getAgentIdTpT3());//代理人证件类型
		req10001.setAgIdNo(reqBody.getCmsnHldrGlblIdT());//代理人证件号码	
		req10001.setSecMag(reqBody.getScdTrkInfoT2());//第二磁道
		req10001.setThdMag(reqBody.getThrTrkInfoT1());//第三磁道
		//向交行系统发送交行磁条卡通兑记账
		REP_10001 rep_10001 = forwardToBocmService.sendToBocm(req10001, 
				REP_10001.class);
		return rep_10001;
	}
	
	/** 
	* @Title: iCCardCharge 
	* @Description: 交行IC卡通存记账 
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return REP_20000    返回类型 
	* @throws 
	*/
	private REP_20001 iCCardCharge(REQ_30061800401 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061800401.REQ_BODY reqBody = reqDto.getReqBody();
		REQ_20001 req20001 = new REQ_20001(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
		super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req20001.getHeader()); // 设置报文头中的行号信息

		req20001.setTxnAmt(reqBody.getWthrAmtT());//交易金额
		req20001.setPin(reqBody.getPwdT());//交易密码
		req20001.setOprFlg("0");//卡输入方式  通兑必须刷卡
		req20001.setTxnMod("0");//业务模式 0 现金 1 转账（实时转账）
		req20001.setPayBnk(reqBody.getOpnAcctBnkNoT7());//付款人开户行行号
		req20001.setpActTp(reqBody.getAcctNoTpT());//付款人账户类型
		req20001.setpActNo(reqBody.getCardNoT3());//付款人账号
		req20001.setPayNam(reqBody.getNmT());//付款人名称
		req20001.setCuIdTp(reqBody.getIdTpT2());//证件种类
		req20001.setCuIdNo(reqBody.getHldrGlblIdT());//证件号码
		req20001.setAgIdTp(reqBody.getAgentIdTpT3());//代理人证件类型
		req20001.setAgIdNo(reqBody.getCmsnHldrGlblIdT());//代理人证件号码	
		req20001.setSeqNo(reqBody.getIcCardSeqNoT1());//IC卡顺序号
		req20001.setaRQC(reqBody.getIcCard91T());//IC卡发卡行认证
		req20001.setiCAID(reqBody.getIcCard9f09T());//IC卡应用编号
		req20001.setiCOutDate(reqBody.getIcCardAvaiDtT());//IC卡有效期
		req20001.setiCData(reqBody.getIcCardF55T());//IC卡数据域（55域）
		//向交行系统发送交行IC卡通兑记账
		REP_20001 rep_20001 = forwardToBocmService.sendToBocm(req20001, 
				REP_20001.class);
		return rep_20001;
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
	private ESB_REP_30011000104 hostCharge(REQ_30061800301 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		REQ_30061800301.REQ_BODY reqBody = reqDto.getReqBody();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "txbrno");
			txTel = jedis.get(COMMON_PREFIX + "txtel");
		}

		ESB_REQ_30011000104 esbReq_30011000104 = new ESB_REQ_30011000104(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000104.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30011000104.setReqSysHead(reqSysHead);

		ESB_REQ_30011000104.REQ_BODY reqBody_30011000104 = esbReq_30011000104.getReqBody();
		reqBody_30011000104.setBaseAcctNo(reqBody.getCardNoT3());
		reqBody_30011000104.setAcctName(reqBody.getNaT1());
		reqBody_30011000104.setTranType("JH12");
		reqBody_30011000104.setTranCcy("CNY");
		reqBody_30011000104.setTranAmt(reqBody.getDpsAmtT());
		reqBody_30011000104.setSettlementDate("");
		reqBody_30011000104.setCollateFlag("Y");
		//TT-账户内扣 CA-现金
		reqBody_30011000104.setChargeMethod(reqBody.getRcveWyT());

		ESB_REP_30011000104 esbRep_30011000104 = forwardToESBService.sendToESB(esbReq_30011000104, reqBody_30011000104,
				ESB_REP_30011000104.class);
		return esbRep_30011000104;
	}
	
	/** 
	* @Title: updateBocmRecord 
	* @Description: 更新交行记账状态 
	* @param @param reqDto
	* @param @param bocmTraceno
	* @param @param bocmState
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return BocmSndTraceUpdModel    返回类型 
	* @throws 
	*/
	private BocmSndTraceUpdModel updateBocmRecord(REQ_30061800401 reqDto,
			String bocmTraceno, String bocmState) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setBocmState(bocmState);
		record.setBocmTraceno(bocmTraceno);
		bocmSndTraceService.sndTraceUpd(record);
		return record;
	}
	
	/** 
	* @Title: updateHostRecord 
	* @Description: 更新核心记账状态
	* @param reqDto
	* @param hostDate 核心日期
	* @param hostTraceno 核心流水号
	* @param hostState 核心记账状态
	* @param retCode 核心响应码
	* @param retMsg 核心响应信息
	* @param accounting_branch 核心记账机构
	* @param @throws SysTradeExecuteException    设定文件 
	* @return SndTraceUpdModel  返回类型 
	* @throws 
	*/
	private BocmSndTraceUpdModel updateHostRecord(REQ_30061800401 reqDto, String hostDate, String hostTraceno,
			String hostState, String retCode, String retMsg,String accounting_branch) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		if(!"".equals(hostDate)) {
		record.setHostDate(Integer.parseInt(hostDate));
		}
		record.setHostState(hostState);
		record.setHostTraceno(hostTraceno);
		record.setRetCode(retCode);
		record.setRetMsg(retMsg);
		record.setHostBranch(accounting_branch);
		bocmSndTraceService.sndTraceUpd(record);
		return record;
	}
	
	/** 
	* @Title: hostCharge 
	* @Description: 核心记账（交行柜面通统一记账）
	* @param reqDto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30011000103    返回类型 
	* @throws 
	*/
	private ESB_REP_30011000104 hostCharge(REQ_30061800401 reqDto)
			throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		REQ_30061800401.REQ_BODY reqBody = reqDto.getReqBody();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "txbrno");
			txTel = jedis.get(COMMON_PREFIX + "txtel");
		}

		ESB_REQ_30011000104 esbReq_30011000104 = new ESB_REQ_30011000104(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000104.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30011000104.setReqSysHead(reqSysHead);

		ESB_REQ_30011000104.REQ_BODY reqBody_30011000104 = esbReq_30011000104.getReqBody();
		reqBody_30011000104.setBaseAcctNo(reqBody.getCardNoT3());
		reqBody_30011000104.setAcctName(reqBody.getNmT());
		reqBody_30011000104.setTranType("JH01");
		reqBody_30011000104.setTranCcy("CNY");
		reqBody_30011000104.setTranAmt(reqBody.getWthrAmtT());
		reqBody_30011000104.setSettlementDate("");
		reqBody_30011000104.setCollateFlag("Y");
		//TT-账户内扣 CA-现金
		reqBody_30011000104.setChargeMethod(reqBody.getFeeRcveWyT1());

		ESB_REP_30011000104 esbRep_30011000104 = forwardToESBService.sendToESB(esbReq_30011000104, reqBody_30011000104,
				ESB_REP_30011000104.class);
		return esbRep_30011000104;
	}
	
	/** 
	* @Title: bocmReversal 
	* @Description: 交行撤销
	* @param reqDto
	* @param bocmDate 交行日期
	* @param bocmTraceNo 交行流水号
	* @param @throws SysTradeExecuteException    设定文件 
	* @return REP_10009    返回类型 
	* @throws 
	*/
	private REP_10009 bocmReversal(REQ_30061800401 reqDto, String bocmDate, String bocmTraceNo)
			throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_10009 req_10009 = new REQ_10009(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
		REQ_30061800401.REQ_BODY reqBody = reqDto.getReqBody();
		super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req_10009.getHeader()); // 设置报文头中的行号信息
		
		//组装抹账报文体
		//原发起方交易流水
		req_10009.setOlogNo(bocmTraceNo);
		//原交易代码 原交易代码       通兑-10001 IC通兑-20001
		String IC_CARD_FLG_T4 = reqDto.getReqBody().getIcCardFlgT4();//IC卡磁条卡标志
		if("0".equals(IC_CARD_FLG_T4)){
			req_10009.setOtxnCd("10001");
		}else{
			req_10009.setOtxnCd("20001");
		}		
		//交易金额
		req_10009.setTxnAmt(reqBody.getWthrAmtT());
		//业务模式
		req_10009.setTxnMod("");
		req_10009.setTxnAmt(reqBody.getWthrAmtT());//交易金额
		req_10009.setTxnMod("0");//业务模式 0 现金 1 转账（实时转账）
		req_10009.setPayBnk(reqBody.getOpnAcctBnkNoT7());//付款人开户行行号
		req_10009.setpActTp(reqBody.getAcctNoTpT());//付款人账户类型
		req_10009.setpActNo(reqBody.getCardNoT3());//付款人账号
		req_10009.setPayNam(reqBody.getNmT());//付款人名称
		req_10009.setCuIdTp(reqBody.getIdTpT2());//证件种类
		req_10009.setCuIdNo(reqBody.getHldrGlblIdT());//证件号码
		req_10009.setAgIdTp(reqBody.getAgentIdTpT3());//代理人证件类型
		req_10009.setAgIdNo(reqBody.getCmsnHldrGlblIdT());//代理人证件号码	
		//向交行系统发送抹账请求
		REP_10009 rep_10009 = forwardToBocmService.sendToBocm(req_10009, 
				REP_10009.class);
		return rep_10009;
	}
}
