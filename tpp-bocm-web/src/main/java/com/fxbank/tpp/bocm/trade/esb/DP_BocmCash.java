package com.fxbank.tpp.bocm.trade.esb;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.bocm.dto.esb.REP_30061000901;
import com.fxbank.tpp.bocm.dto.esb.REQ_30061000901;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmSndTraceInitModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceUpdModel;
import com.fxbank.tpp.bocm.model.REP_10000;
import com.fxbank.tpp.bocm.model.REP_20000;
import com.fxbank.tpp.bocm.model.REQ_10000;
import com.fxbank.tpp.bocm.model.REQ_20000;
import com.fxbank.tpp.bocm.service.IBocmSndTraceService;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30011000104;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30014000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30011000104;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30014000101;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import redis.clients.jedis.Jedis;

/**
 * @ClassName: DP_BocmCash
 * @Description: 交行卡存现金
 * @author Duzhenduo
 * @date 2019年4月15日 下午4:26:16
 * 
 */
@Service("REQ_30061000901")
public class DP_BocmCash extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(DP_BocmCash.class);

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
		REQ_30061000901 reqDto = (REQ_30061000901) dto;
		REQ_30061000901.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30061000901 rep = new REP_30061000901();		
		//1. 核心记账
		ESB_REP_30011000104 esbRep_30011000104 = null;
		//核心记账日期
		String hostDate = null;
		//核心记账流水号
		String hostTraceno = null;
		//核心记账返回状态码
		String retCode = null;
		//核心记账返回状态信息
		String retMsg = null;
		//核心记账超时标记
//		String hostStatus = "0";
		//1.核心记账
		myLog.info(logger, "发送交行卡存现金核心记账请求");
		try {
			esbRep_30011000104 = hostCharge(reqDto);
			hostDate = esbRep_30011000104.getRepSysHead().getRunDate();
			hostTraceno = esbRep_30011000104.getRepBody().getReference();
			retCode = esbRep_30011000104.getRepSysHead().getRet().get(0).getRetCode();
			retMsg = esbRep_30011000104.getRepSysHead().getRet().get(0).getRetMsg();
			//2.核心记账成功，插入流水表
			initRecord(reqDto, hostDate, hostTraceno, "1", retCode, retMsg);
			myLog.info(logger, "交行卡存现金，本行核心记账成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
		} catch (SysTradeExecuteException e) {
			//接收ESB报文应答超时
			if("CIP_E_000004".equals(e.getRspCode())) {		
				//记录记账状态为超时，继续执行交易流程
				initRecord(reqDto, hostDate, hostTraceno, "3", retCode, retMsg);
				myLog.error(logger, "交行卡存现金，本行核心记账接收ESB报文应答超时，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				SysTradeExecuteException e2 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000004,"交易失败:核心记账超时，请核实账务，"+e.getRspMsg());
				throw e2;
			//其他错误
			}else {
				myLog.error(logger, "交行卡存现金，本行核心记账失败，渠道日期" + reqDto.getSysDate() + 
					"渠道流水号" + reqDto.getSysTraceno(), e);
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败:"+e.getRspMsg());
				throw e2;
			}
		}
		//交行记账流水号
		String bocmTraceNo = null;
		int bocmDate = 0;
		int bocmTime = 0;
		//3.交行记账.通过标识判断调用磁条卡记账还是ic卡记账
		//IC_CARD_FLG_T4判断IC卡磁条卡标志
			String cardTypeName = "";
			//原交易代码，用于异常判断请求磁条卡交易还是IC卡交易
			String oTxnCd = null;
			REP_10000 rep10000 = null;
			REP_20000 rep20000 = null;
			REQ_10000 req10000 = null;
			REQ_20000 req20000 = null;
			if("2".equals(reqBody.getIcCardFlgT4())){
				oTxnCd = "10000";
				req10000 = new REQ_10000(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
				super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req10000); // 设置报文头中的行号信息
			}else{
				oTxnCd = "20000";
				req20000 = new REQ_20000(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
				super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req20000); // 设置报文头中的行号信息
			}						
			try {
				if (oTxnCd.equals("10000")) {						
					myLog.info(logger, "发送磁条卡现金通存请求至交行");		
					cardTypeName = "磁条卡";
					rep10000 = magCardCharge(reqDto,req10000);
					bocmTraceNo = rep10000.getRlogNo();
					bocmDate = rep10000.getSysDate();
					bocmTime = rep10000.getSysTime();
					rep.getRepBody().setOpnAcctBnkFeeT(rep10000.getFee().toString());
					rep.getRepBody().setAcctBalT2(rep10000.getActBal().toString());
				}else{					
					myLog.info(logger, "发送IC卡现金通存请求至交行");
					cardTypeName = "IC卡";
					rep20000 = iCCardCharge(reqDto,req20000);
					bocmTraceNo = rep20000.getRlogNo();
					bocmDate = rep20000.getSysDate();
					bocmTime = rep20000.getSysTime();
					rep.getRepBody().setOpnAcctBnkFeeT(rep20000.getFee().toString());
					rep.getRepBody().setAcctBalT2(rep20000.getActBal().toString());
				}
				//4.交行记账成功，更新流水表交行记账状态
				updateBocmRecord(reqDto,bocmDate,bocmTime,bocmTraceNo,"1");
				myLog.info(logger, "交行卡存现金，交行"+cardTypeName+"通存记账成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
			} catch (SysTradeExecuteException e) { // 记账交易参考一下方式处理，查询交易不用
				// 如果不是账务类请求，可以不用分类处理应答码，统一当成失败处理即可
				// 如果交易不关心返回的异常类型，直接可以不捕获，直接省略catch，抛出异常即可
				if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000006) // 生成请求失败
						|| e.getRspCode().equals(SysTradeExecuteException.CIP_E_000007)
						|| e.getRspCode().equals(SysTradeExecuteException.CIP_E_000008)) {
					//交行核心记账报错，本行核心冲正
					updateBocmRecord(reqDto, bocmDate,bocmTime,"", "2");
					myLog.error(logger, "交行卡存现金，交行"+cardTypeName+"通存记账失败，本行核心冲正，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					//核心记账状态，1-成功，4-冲正成功，5-冲正失败，6-冲正超时
					ESB_REP_30014000101 esbRep_30014000101 = null;
					String hostReversalCode = null;
					String hostReversalMsg = null;
					try {
						esbRep_30014000101 = hostReversal(reqDto,hostTraceno);
						hostReversalCode = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetCode();
						hostReversalMsg = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetMsg();
					}catch(SysTradeExecuteException e1) {
						//对于冲正失败处理：返回交易失败，对账的时候忽略核心记账的成功状态
						//接收ESB报文应答超时
						if("CIP_E_000004".equals(e1.getRspCode())) {
							updateHostCheck(reqDto, "", "", "6", e.getRspCode(), e.getRspMsg(), "0");
							myLog.error(logger, "交行卡存现金，本行核心冲正超时，渠道日期" + reqDto.getSysDate() + 
									"渠道流水号" + reqDto.getSysTraceno(), e1);							
							SysTradeExecuteException e2 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000004,"交易失败:"+e.getRspMsg()+",核心冲正超时");
							throw e2;													
						//其他冲正错误
						}else {
							updateHostCheck(reqDto, "", "", "5", e.getRspCode(), e.getRspMsg(), "0");
							myLog.error(logger, "交行卡存现金，本行核心冲正失败，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e1);						
							BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败:"+e.getRspMsg()+",核心冲正失败");
							throw e2;						
						}
					}
					updateHostCheck(reqDto, hostDate, hostTraceno, "4", hostReversalCode, hostReversalMsg,"0");
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败:"+e.getRspMsg());
					myLog.error(logger, "交行卡存现金，本行核心冲正成功，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(),e2);
					throw e2;
				}else if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000009)||e.getRspCode().equals("JH6203")) { // 接收交行返回结果超时
					updateBocmRecord(reqDto, bocmDate,bocmTime,"", "3");
					myLog.error(logger, "交行卡存现金，交行"+cardTypeName+"通存记账返回结果超时，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					try {
						myLog.error(logger, "交行卡存现金，交行"+cardTypeName+"通存记账返回结果超时,请求重发");
						rep = reBcomCharge(rep, reqDto, req10000, req20000, oTxnCd, cardTypeName);	
						myLog.info(logger, "交行卡存现金，交行"+cardTypeName+"通存记账请求重发成功");
						updateBocmRecord(reqDto, bocmDate,bocmTime,"", "1");
					    return rep;
					}catch(SysTradeExecuteException e1) {
						myLog.error(logger, "交行卡存现金，交行"+cardTypeName+"通存记账重发报错，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e1);
						updateBocmRecord(reqDto, bocmDate,bocmTime,"", "3");
						myLog.error(logger, "重发返回异常，给柜面返回成功，防止短款");
						//如果还是超时返回成功，防止短款
					}
				} else { // 目标系统应答失败
							// 确认是否有冲正操作
					//交行核心记账报错，本行核心冲正
					updateBocmRecord(reqDto, bocmDate,bocmTime,"", "2");
					myLog.error(logger, "交行卡存现金，交行"+cardTypeName+"通存记账失败，本行核心冲正，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					//核心记账状态，1-成功，4-冲正成功，5-冲正失败，6-冲正超时
					ESB_REP_30014000101 esbRep_30014000101 = null;
					String hostReversalCode = null;
					String hostReversalMsg = null;
					try {
						esbRep_30014000101 = hostReversal(reqDto,hostTraceno);
						hostReversalCode = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetCode();
						hostReversalMsg = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetMsg();
					}catch(SysTradeExecuteException e1) {
						//对于冲正失败处理：返回交易失败，对账的时候忽略核心记账的成功状态
						//接收ESB报文应答超时
						if("CIP_E_000004".equals(e1.getRspCode())) {
							updateHostCheck(reqDto, "", "", "6", e.getRspCode(), e.getRspMsg(),"0");
							myLog.error(logger, "交行卡存现金，本行核心冲正超时，渠道日期" + reqDto.getSysDate() + 
									"渠道流水号" + reqDto.getSysTraceno(), e1);							
							SysTradeExecuteException e2 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000004,"交易失败:"+e.getRspMsg()+",核心冲正超时");
							throw e2;
						//其他冲正错误
						}else {
							updateHostCheck(reqDto, "", "", "5", e.getRspCode(), e.getRspMsg(),"0");
							myLog.error(logger, "交行卡存现金，本行核心冲正失败，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e1);
							BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败:"+e.getRspMsg()+",核心冲正失败");
							throw e2;
						}
					}
					updateHostCheck(reqDto, hostDate, hostTraceno, "4", hostReversalCode, hostReversalMsg,"0");
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败:"+e.getRspMsg());
					myLog.error(logger, "交行卡存现金，本行核心冲正成功，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(),e2);
					throw e2;
				}
			} catch (Exception e) { // 其它未知错误，可以当成超时处理
				// 确认是否有冲正操作				
				updateBocmRecord(reqDto, bocmDate,bocmTime,"", "3");
				myLog.error(logger, "交行卡存现金，交行"+cardTypeName+"通存记账其它未知错误，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				try {
					myLog.error(logger, "交行卡存现金，其它未知错误，可以当成超时处理,请求重发");
					rep = reBcomCharge(rep, reqDto, req10000, req20000, oTxnCd, cardTypeName);
					myLog.info(logger, "交行卡存现金，交行"+cardTypeName+"通存记账请求重发成功");
					updateBocmRecord(reqDto, bocmDate,bocmTime,"", "1");
				    return rep;
				}catch(SysTradeExecuteException e1) {
					myLog.error(logger, "交行卡存现金，交行"+cardTypeName+"通存记账重发报错，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e1);
					updateBocmRecord(reqDto, bocmDate,bocmTime,"", "3");
					myLog.error(logger, "重发返回异常，给柜面返回成功，防止短款");
					//如果还是超时返回成功，防止短款					
				}
			}
 
		return rep;
	}
	
	private REP_30061000901 reBcomCharge(REP_30061000901 rep, REQ_30061000901 reqDto, REQ_10000 req10000, REQ_20000 req20000,
			String oTxnCd, String cardTypeName) throws SysTradeExecuteException{
		MyLog myLog = logPool.get();
		if(oTxnCd.equals("10000")){
			REP_10000 rep10000 = magCardCharge(reqDto,req10000);
			String reBocmTraceNo = rep10000.getRlogNo();
		    updateBocmRecord(reqDto, rep10000.getSysDate(),rep10000.getSysTime(),reBocmTraceNo, "1");
		    myLog.info(logger, "6.交行卡存现金，交行"+cardTypeName+"通存记账重发成功，渠道日期" + reqDto.getSysDate() + 
					"渠道流水号" + reqDto.getSysTraceno());
			rep.getRepBody().setOpnAcctBnkFeeT(rep10000.getFee().toString());
			rep.getRepBody().setAcctBalT2(rep10000.getActBal().toString());
		}else{
			REP_20000 rep20000 = iCCardCharge(reqDto,req20000);
			String reBocmTraceNo = rep20000.getRlogNo();
		    updateBocmRecord(reqDto, rep20000.getSysDate(),rep20000.getSysTime(),reBocmTraceNo, "1");
		    myLog.info(logger, "6.交行卡存现金，交行"+cardTypeName+"通存记账重发成功，渠道日期" + reqDto.getSysDate() + 
					"渠道流水号" + reqDto.getSysTraceno());
			rep.getRepBody().setOpnAcctBnkFeeT(rep20000.getFee().toString());
			rep.getRepBody().setAcctBalT2(rep20000.getActBal().toString());
		}
		return rep;
	}


	/** 
	* @Title: initRecord 
	* @Description: 登记流水表
	* @param @param reqDto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void initRecord(DataTransObject dto, String hostDate, String hostTraceno,
			String hostState, String retCode, String retMsg) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061000901 reqDto = (REQ_30061000901)dto;
		REQ_30061000901.REQ_BODY reqBody = reqDto.getReqBody();
		REQ_SYS_HEAD reqSysHead = reqDto.getReqSysHead();

		BocmSndTraceInitModel record = new BocmSndTraceInitModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setSourceType(reqSysHead.getSourceType());
		record.setTxBranch(reqSysHead.getBranchId());
		// 通存通兑
		record.setDcFlag("0");
		record.setTxAmt(reqBody.getDpsAmtT());
		record.setTxInd("0");
		//'核心记账状态，0-登记，1-成功，2-失败，3-超时，4-冲正成功，5-冲正失败，6-冲正超时';
		record.setHostState("1");
		//'交通银行记账状态，0-登记，1-成功，2-失败，3-超时，4-冲正成功，5-冲正失败，6-冲正超时';
		record.setBocmState("0");
		record.setTxTel(reqSysHead.getUserId());
		record.setPayeeAcno(reqBody.getCardNoT3());
		record.setPayeeName(reqBody.getNaT1());
		record.setChkTel(reqSysHead.getApprUserId());
		record.setAuthTel(reqSysHead.getAuthUserId());
		record.setPrint("0");
		record.setCheckFlag("1");
		String IcCardFlag = reqBody.getIcCardFlgT4();
		if("2".equals(IcCardFlag)){
			record.setTxCode("10000");
		}else{
			record.setTxCode("20000");
		}
		record.setHostDate(Integer.parseInt(hostDate));
		record.setHostTraceno(hostTraceno);
		record.setRetCode(retCode);
		record.setRetMsg(retMsg);
		record.setCheckFlag("1");
		bocmSndTraceService.sndTraceInit(record);
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
		MyLog myLog = logPool.get();
		REQ_30061000901 reqDto = (REQ_30061000901)dto;
		REQ_30061000901.REQ_BODY reqBody = reqDto.getReqBody();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		txTel = reqDto.getReqSysHead().getUserId();
		txBrno = reqDto.getReqSysHead().getBranchId();
		ESB_REQ_30011000104 esbReq_30011000104 = new ESB_REQ_30011000104(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000104.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		reqSysHead.setProgramId(reqDto.getReqSysHead().getProgramId());
		reqSysHead.setSourceBranchNo(reqDto.getReqSysHead().getSourceBranchNo());
		reqSysHead.setSourceType(reqDto.getReqSysHead().getSourceType());
		
		esbReq_30011000104.setReqSysHead(reqSysHead);

		ESB_REQ_30011000104.REQ_BODY reqBody_30011000104 = esbReq_30011000104.getReqBody();
		reqBody_30011000104.setBaseAcctNo(reqBody.getCardNoT3());
		reqBody_30011000104.setAcctName(reqBody.getNaT1());
		reqBody_30011000104.setTranType("JH12");
		reqBody_30011000104.setTranCcy("CNY");
		reqBody_30011000104.setTranAmt(reqBody.getDpsAmtT());
		reqBody_30011000104.setChannelType("BU");
		reqBody_30011000104.setSettlementDate(reqDto.getSysDate()+"");
		reqBody_30011000104.setChargeMethod("1");
		reqBody_30011000104.setCollateFlag("Y");
		
		//SEND_BANK_CODE	 发起行行号
		//BANK_CODE	                        我方银行行号
		//OTH_BANK_CODE	            对方银行行号
		reqBody_30011000104.setSendBankCode("313226090656");
		reqBody_30011000104.setOthBankCode(reqBody.getOpnAcctBnkNoT8());
//		reqBody_30011000104.setBankCode("");
		//TT-账户内扣 CA-现金
		reqBody_30011000104.setChargeMethod(reqBody.getRcveWyT());

		ESB_REP_30011000104 esbRep_30011000104 = forwardToESBService.sendToESB(esbReq_30011000104, reqBody_30011000104,
				ESB_REP_30011000104.class);
		return esbRep_30011000104;
	}
	
	/** 
	* @Title: magCardCharge 
	* @Description: 交行磁条卡通存记账
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return REP_10000    返回类型 
	* @throws 
	*/
	public REP_10000 magCardCharge(DataTransObject dto, REQ_10000 req10000) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061000901 reqDto = (REQ_30061000901)dto;
		REQ_30061000901.REQ_BODY reqBody = reqDto.getReqBody();
		req10000.setTxnAmt(Double.parseDouble(reqBody.getDpsAmtT()));
		req10000.setFeeFlg(reqBody.getRcveWyT());
		req10000.setFee(Double.parseDouble(reqBody.getFeeT3()));
		req10000.setOprFlg(reqBody.getRdCardWyT());
		//业务模式，0 现金1 转账（即实时转账）9 其他
		req10000.setTxnMod("0");
		req10000.setRecBnk(reqBody.getOpnAcctBnkNoT8());
		req10000.setRactTp(reqBody.getAcctTpT());
		req10000.setRactNo(reqBody.getCardNoT3());
		req10000.setRecNam(reqBody.getNaT1());
		req10000.setCuIdTp(reqBody.getIdTpT2());
		req10000.setCuIdNo(reqBody.getHldrGlblIdT());
		req10000.setAgIdTp(reqBody.getAgentCrtfT());
		req10000.setAgIdNo(reqBody.getAgentCrtfNoT());
		req10000.setSecMag(reqBody.getScdTrkInfoT2());
		req10000.setThdMag(reqBody.getThrTrkInfoT1());
		myLog.info(logger, "向交行发送发送磁条卡现金通存请求报文");
		
		REP_10000 rep_10000 = forwardToBocmService.sendToBocm(req10000, 
				REP_10000.class);		
		return rep_10000;
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
	public REP_20000 iCCardCharge(DataTransObject dto, REQ_20000 req20000) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061000901 reqDto = (REQ_30061000901)dto;
		REQ_30061000901.REQ_BODY reqBody = reqDto.getReqBody();
		req20000.setTxnAmt(Double.parseDouble(reqBody.getDpsAmtT()));
		req20000.setFeeFlg(reqBody.getRcveWyT());
		req20000.setFee(Double.parseDouble(reqBody.getFeeT3()));
		req20000.setOprFlg(reqBody.getRdCardWyT());
		//业务模式，0 现金1 转账（即实时转账）9 其他
		req20000.setTxnMod("0");
		req20000.setRecBnk(reqBody.getOpnAcctBnkNoT8());
		req20000.setRactTp(reqBody.getAcctTpT());
		req20000.setRactNo(reqBody.getCardNoT3());
		req20000.setRecNam(reqBody.getNaT1());
		req20000.setCuIdTp(reqBody.getIdTpT2());
		req20000.setCuIdNo(reqBody.getHldrGlblIdT());
		req20000.setAgIdTp(reqBody.getAgentCrtfT());
		req20000.setAgIdNo(reqBody.getAgentCrtfNoT());
		req20000.setSeqNo(reqBody.getIcCardSeqNoT1());
		req20000.setARQC(reqBody.getIcCard91T());
		req20000.setICAID(reqBody.getIcCard9f09T());
		req20000.setICOutDate(reqBody.getIcCardAvaiDtT());
		req20000.setICData(reqBody.getIcCardF55T());
        
		REP_20000 rep_20000 = forwardToBocmService.sendToBocm(req20000, 
				REP_20000.class);
		
		return rep_20000;
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
	public ESB_REP_30014000101 hostReversal(DataTransObject dto,String hostSeqno)
			throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061000901 reqDto = (REQ_30061000901)dto;
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;

		txTel = reqDto.getReqSysHead().getUserId();
		txBrno = reqDto.getReqSysHead().getBranchId();
		ESB_REQ_30014000101 esbReq_30014000101 = new ESB_REQ_30014000101(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30014000101.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		

		reqSysHead.setProgramId(reqDto.getReqSysHead().getProgramId());
		reqSysHead.setSourceBranchNo(reqDto.getReqSysHead().getSourceBranchNo());
		reqSysHead.setSourceType(reqDto.getReqSysHead().getSourceType());
		
		
		
		esbReq_30014000101.setReqSysHead(reqSysHead);

		ESB_REQ_30014000101.REQ_BODY reqBody_30014000101 = esbReq_30014000101.getReqBody();
		esbReq_30014000101.setReqSysHead(reqSysHead);	

		reqBody_30014000101.setChannelSeqNo(esbReq_30014000101.getReqSysHead().getSeqNo());
		reqBody_30014000101.setReversalReason("交行记账失败,本行核心冲正");
		reqBody_30014000101.setEventType("");

		ESB_REP_30014000101 esbRep_30014000101 = forwardToESBService.sendToESB(esbReq_30014000101, reqBody_30014000101,
				ESB_REP_30014000101.class);
		return esbRep_30014000101;
	}
	
	/** 
	* @Title: updateHostRecord 
	* @Description: 更新核心记账状态 
	*/
	public BocmSndTraceUpdModel updateHostRecord(DataTransObject dto, String hostDate, String hostTraceno,
			String hostState, String retCode, String retMsg) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, dto.getSysDate(), dto.getSysTime(),
				dto.getSysTraceno());
		if(!"".equals(hostDate)) {
		record.setHostDate(Integer.parseInt(hostDate));
		}
		record.setHostState(hostState);
		record.setHostTraceno(hostTraceno);
		record.setRetCode(retCode);
		record.setRetMsg(retMsg);
		bocmSndTraceService.sndTraceUpd(record);
		return record;
	}
	
	/** 
	* @Title: updateHostRecord 
	* @Description: 更新核心记账状态标记对账状态 
	*/
	public BocmSndTraceUpdModel updateHostCheck(DataTransObject dto, String hostDate, String hostTraceno,
			String hostState, String retCode, String retMsg, String checkFlag) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, dto.getSysDate(), dto.getSysTime(),
				dto.getSysTraceno());
		if(!"".equals(hostDate)) {
		record.setHostDate(Integer.parseInt(hostDate));
		}
		record.setHostState(hostState);
		record.setHostTraceno(hostTraceno);
		record.setRetCode(retCode);
		record.setRetMsg(retMsg);
		record.setCheckFlag(checkFlag);
		bocmSndTraceService.sndTraceUpd(record);
		return record;
	}
	
	/** 
	* @Title: updateBocmRecord 
	* @Description: 更新交行记账状态 
	*/
	public BocmSndTraceUpdModel updateBocmRecord(DataTransObject dto,
			int bocmDate,int bocmTime,String bocmTraceno, 
			String bocmState) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, dto.getSysDate(), dto.getSysTime(),
				dto.getSysTraceno());
		record.setBocmState(bocmState);
		record.setBocmDate(bocmDate);
		record.setBocmTime(bocmTime);
		record.setBocmTraceno(bocmTraceno);
		bocmSndTraceService.sndTraceUpd(record);
		return record;
	}

}
