package com.fxbank.tpp.bocm.trade.esb;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.constant.CIP;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.pub.service.IPublicService;
import com.fxbank.tpp.bocm.dto.esb.REP_30061000801;
import com.fxbank.tpp.bocm.dto.esb.REQ_30061000801;
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
import com.fxbank.tpp.bocm.util.NumberUtil;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30011000104;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30014000101;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30011000104;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30014000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30043000101;
import com.fxbank.tpp.esb.service.IForwardToESBService;


/** 
* @ClassName: WD_BocmTrsr 
* @Description: 交行卡付款转账
* @author Duzhenduo
* @date 2019年4月17日 上午10:38:50 
*  
*/
@Service("REQ_30061000801")
public class WD_BocmTra extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(DP_BocmTra.class);

	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IForwardToBocmService forwardToBocmService;

	@Reference(version = "1.0.0")
	private IBocmSndTraceService bocmSndTraceService;

	@Reference(version = "1.0.0")
	private IPublicService publicService;
	
	@Resource
	private MyJedis myJedis;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		myLog.info(logger, "交行卡付款转账");
		REQ_30061000801 reqDto = (REQ_30061000801) dto;
		REQ_30061000801.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30061000801 rep = new REP_30061000801();
		int bocmDate = 0;
		int bocmTime = 0;
		String cardTypeName = "";
		//原发起方交易流水 交行记账流水号
		String bocmTraceNo = null;
		//原交易代码
		String oTxnCd = null;		
		REQ_10001 req10001 = null;
		REP_10001 rep10001 = null;
		REQ_20001 req20001 = null;
		REP_20001 rep20001 = null;
		//发起行
		String rbnkNo = "";
		//接收行
		String sbnkNo = "";
		//账户余额
		String actBal = "0";
		//手续费
		String fee = "0";
		//交行响应码
		String bocmRepcd = "";
		//交行响应信息
		String bocmRepmsg = "";
		// IC_CARD_FLG_T4判断IC卡磁条卡标志   IC卡和磁条卡走不同的交行接口
		if("2".equals(reqBody.getIcCardFlgT4())){
			oTxnCd = "10001";			
			req10001 = new REQ_10001(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
			super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req10001); // 设置报文头中的行号信息
			rbnkNo = req10001.getRbnkNo();
			sbnkNo = req10001.getSbnkNo();
		}else{
			oTxnCd = "20001";
			req20001 = new REQ_20001(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
			super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req20001); // 设置报文头中的行号信息
			rbnkNo = req20001.getRbnkNo();
			sbnkNo = req20001.getSbnkNo();
		}	
		try {
			
			//1.交行记账   
			if (oTxnCd.equals("10001")) {						
				myLog.info(logger, "交行卡付款转账,发送"+cardTypeName+"付款转账请求至交行");		
				cardTypeName = "磁条卡";
				rep10001 = magCardCharge(reqDto,req10001);
				bocmTraceNo = rep10001.getRlogNo();
				bocmDate = rep10001.getSysDate();
				bocmTime = rep10001.getSysTime();	
				fee = rep10001.getFee().toString();
				actBal = rep10001.getActBal().toString();	
				bocmRepcd = rep10001.getTrspCd();
				bocmRepmsg = rep10001.getTrspMsg();				
			}else{					
				myLog.info(logger, "交行卡付款转账,发送"+cardTypeName+"付款转账请求至交行");
				cardTypeName = "IC卡";
				rep20001 = iCCardCharge(reqDto,req20001);
				bocmTraceNo = rep20001.getRlogNo();
				bocmDate = rep20001.getSysDate();
				bocmTime = rep20001.getSysTime();	
				fee = rep20001.getFee().toString();
				actBal = rep20001.getActBal().toString();	
				bocmRepcd = rep20001.getTrspCd();
				bocmRepmsg = rep20001.getTrspMsg();	
			}	
		} catch (SysTradeExecuteException e) { // 记账交易参考一下方式处理，查询交易不用
			// 如果不是账务类请求，可以不用分类处理应答码，统一当成失败处理即可
			// 如果交易不关心返回的异常类型，直接可以不捕获，直接省略catch，抛出异常即可
			// 请求交行失败，返回交易失败，不计入流水表
			if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000006) // 生成请求失败
					|| e.getRspCode().equals(SysTradeExecuteException.CIP_E_000007)
					|| e.getRspCode().equals(SysTradeExecuteException.CIP_E_000008)) {
				myLog.error(logger, "交行卡付款转账，交行"+cardTypeName+"通兑记账失败，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败:交行记账失败,"+e.getRspMsg());
				throw e2;
			}else if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000009)||e.getRspCode().equals("JH6203")) { // 接收交行返回结果超时
				//请求记账超时，调用交行冲正，不记录流水
				myLog.error(logger, "交行卡付款转账，交行"+cardTypeName+"通兑记账返回结果超时，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				try {
					myLog.error(logger, "发送"+cardTypeName+"通兑抹账请求至交行");
					bocmReversal(reqDto,bocmTraceNo,oTxnCd);
				    myLog.info(logger, "交行卡付款转账，交行"+cardTypeName+"通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno());
				}catch(Exception e1) {
					initRecord(reqDto, bocmDate, bocmTime, bocmTraceNo, "5",rbnkNo,sbnkNo,actBal,bocmRepcd,bocmRepmsg);	
					myLog.error(logger, "交行卡付款转账，交行"+cardTypeName+"通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e1);
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败，请求交行系统超时,请确认交行卡记账状态");
					throw e2;
				}
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败，请求交行系统超时,请确认交行卡记账状态");
				throw e2;
			} else { // 目标系统应答失败
						// 确认是否有冲正操作
				//交行核心记账报错，交易失败
				myLog.error(logger, "交行卡付款转账，交行"+cardTypeName+"通兑记账失败，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败:交行记账失败,"+e.getRspMsg());
				throw e2;
			}
		} catch (Exception e) { // 其它未知错误，可以当成超时处理
			// 确认是否有冲正操作
			//请求记账未知错误，调用交行冲正，不记录流水
			myLog.error(logger, "交行卡付款转账，交行"+cardTypeName+"通兑记账返回其他错误，渠道日期" + reqDto.getSysDate() + 
					"渠道流水号" + reqDto.getSysTraceno(), e);
			try {
				myLog.error(logger, "发送"+cardTypeName+"通兑抹账请求至交行");
				bocmReversal(reqDto,bocmTraceNo,oTxnCd);
			    myLog.info(logger, "交行卡付款转账，交行"+cardTypeName+"通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno());
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败，请求交行记账未知错误");
				throw e2;
			}catch(Exception e1) {
				initRecord(reqDto, bocmDate, bocmTime, bocmTraceNo, "5",rbnkNo,sbnkNo,actBal,bocmRepcd,bocmRepmsg);	
				myLog.error(logger, "交行卡付款转账，交行"+cardTypeName+"通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e1);
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败，请求交行记账未知错误");
				throw e2;
			}
		}
		
		if(!"".equals(fee)){
			fee = NumberUtil.removePointToString(Double.parseDouble(fee));
		}
		actBal = NumberUtil.removePointToString(Double.parseDouble(actBal));
		//手续费
		rep.getRepBody().setFeeT3(fee);
		//余额
		rep.getRepBody().setBalance3T(actBal);	
		//2.交行记账成功,插入流水表
		//交行记账状态，0-登记，1-成功，2-失败，3-超时，4-冲正成功，5-冲正失败， 6-冲正超时
		initRecord(reqDto, bocmDate, bocmTime, bocmTraceNo, "1",rbnkNo,sbnkNo,actBal,bocmRepcd,bocmRepmsg);	
		myLog.info(logger, "交行卡付款转账，交行"+cardTypeName+"通兑记账成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
		
		ESB_REP_30011000104 esbRep_30011000104 = null;
		//核心记账日期
		String hostDate = null;
		//核心记账流水号
		String hostTraceno = null;
		//核心记账返回状态码
		String retCode = null;
		//核心记账返回状态信息
		String retMsg = null;
		try {
			//3. 核心记账
			myLog.info(logger,"交行卡付款转账，本行核心记账");
			esbRep_30011000104 = hostCharge(reqDto);
			ESB_REP_30011000104.Fee tradFee = esbRep_30011000104.getRepBody().getFeeDetail().get(0);	
			//如果返回手续费为空，则手续费赋值为0
			fee = tradFee.getFeeAmt();
			if(fee.equals("")){
				fee = "0";
			}
			hostDate = esbRep_30011000104.getRepSysHead().getRunDate();
			hostTraceno = esbRep_30011000104.getRepBody().getReference();
			retCode = esbRep_30011000104.getRepSysHead().getRet().get(0).getRetCode();
			retMsg = esbRep_30011000104.getRepSysHead().getRet().get(0).getRetMsg();
			if(1==1){
				SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000004);		
				throw e;
			}
		} catch (SysTradeExecuteException e) {			
			// 核心记账超时或ESB超时
			if (SysTradeExecuteException.CIP_E_000004.equals(e.getRspCode())||"ESB_E_000052".equals(e.getRspCode())) { 
				//本行记账超时,交行冲正,核心冲正,对账时如果交行记账成功，我方按照成功处理,在比对核心对账状态
				try {
					bocmReversal(reqDto,bocmTraceNo,oTxnCd);
					updateBocmRecord(reqDto, bocmDate, bocmTime, bocmTraceNo, "4");
				    myLog.info(logger, "交行卡取现金，交行"+cardTypeName+"通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno());
				}catch(Exception e1) {
					myLog.error(logger, "交行卡取现金，交行"+cardTypeName+"通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e1);
					//交行记账状态，1-成功，4-冲正成功，5-冲正失败，6-冲正超时		
					updateBocmRecord(reqDto, bocmDate, bocmTime, bocmTraceNo, "5");
				}
				ESB_REP_30014000101 esbRep_30014000101 = null;
				String hostReversalCode = null;
				String hostReversalMsg = null;
				try {
					if(1==1){
						SysTradeExecuteException e3 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);		
						throw e3;
					}
					//本行核心抹账	
					esbRep_30014000101 = hostReversal(reqDto,hostTraceno);
					hostReversalCode = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetCode();
					hostReversalMsg = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetMsg();
				}catch(SysTradeExecuteException e1) {
					//核心记账状态，1-成功，4-冲正成功，5-冲正失败，6-冲正超时
					//接收ESB报文应答超时
					if(SysTradeExecuteException.CIP_E_000004.equals(e1.getRspCode())||"ESB_E_000052".equals(e1.getRspCode())) {
						updateHostRecord(reqDto, "", "", "6",fee, e.getRspCode(), e.getRspMsg());
						myLog.error(logger, "交行卡取现金，本行核心冲正超时，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e1);
						SysTradeExecuteException e2 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000004,"交易失败:"+e.getRspMsg()+",核心冲正超时，请核对记账状态,如果记账成功请进行抹账处理");
						throw e2;
					//其他冲正错误
					}else {						
						updateHostRecord(reqDto, "", "", "5",fee, e.getRspCode(), e.getRspMsg());
						myLog.error(logger, "交行卡取现金，本行核心冲正失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e1);
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败:"+e.getRspMsg()+",核心冲正失败,请核对记账状态,如果记账成功请进行抹账处理");
						throw e2;
					}
				}
				updateHostRecord(reqDto, hostDate, hostTraceno, "4",fee, hostReversalCode, hostReversalMsg);
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败:"+e.getRspMsg());
				myLog.error(logger, "交行卡存现金，本行核心冲正成功，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(),e2);
				throw e2;				
			}else{
				//核心记账失败，交行冲正，提示核心错误				
				try {
					bocmReversal(reqDto,bocmTraceNo,oTxnCd);
					updateBocmRecord(reqDto, bocmDate, bocmTime, bocmTraceNo, "4");
				    myLog.info(logger, "交行卡取现金，交行"+cardTypeName+"通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno());
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败，核心记账失败，"+e.getRspMsg());
					throw e2;
				}catch(Exception e1) {
					myLog.error(logger, "交行卡取现金，交行"+cardTypeName+"通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e1);
					updateBocmRecord(reqDto, bocmDate, bocmTime, bocmTraceNo, "5");
				}
				updateHostRecord(reqDto, hostDate, hostTraceno, "2",fee, e.getRspCode(), e.getRspMsg());
				myLog.error(logger, "交行卡取现金，核心记账失败，发送"+cardTypeName+"通兑抹账请求至交行，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败，核心记账失败，"+e.getRspMsg());
				throw e2;
			}					
			

		}
		
		//4. 核心记账成功，更新流水表核心记账状态
		updateHostRecord(reqDto, hostDate, hostTraceno, "1",fee, retCode, retMsg);
		myLog.info(logger, "交行卡付款转账，本行核心记账成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
		rep.getRepBody().setHostTraceNo(hostTraceno);
		rep.getRepBody().setBalance3T(actBal);
		rep.getRepBody().setFeeT3(fee);
		return rep;
	}
	
	/** 
	* @Title: hostTranResult 
	* @Description: 核心交易结果查询
	* @param reqDto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30043000101  返回类型 
	* @throws 
	*/
	public ESB_REP_30043000101 hostTranResult(REQ_30061000801 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		txTel = reqDto.getReqSysHead().getUserId();
		txBrno = reqDto.getReqSysHead().getBranchId();
		
		ESB_REQ_30043000101 esbReq_30043000101 = new ESB_REQ_30043000101(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30043000101.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel)
				.build();
		reqSysHead.setSourceBranchNo("PINP|pinpToesb|RZPK|64510637BCD9|");
		reqSysHead.setSourceType("BU");
		
		esbReq_30043000101.setReqSysHead(reqSysHead);

		ESB_REQ_30043000101.REQ_BODY reqBody_30043000101 = esbReq_30043000101.getReqBody();
		// 记账渠道类型GJ
		reqBody_30043000101.setChannelType("BU");

		String platTrace = String.format("%08d", reqDto.getSysTraceno());// 左补零
		// 渠道流水号
		reqBody_30043000101.setChannelSeqNo(CIP.SYSTEM_ID + reqDto.getSysDate() + platTrace);
		ESB_REP_30043000101 esb_rep_30043000101 = null;
		try {
			myLog.info(logger, "核心交易结果查询，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqBody_30043000101.getChannelSeqNo());
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
	* @Title: initRecord 
	* @Description: 登记流水表
	* @param @param reqDto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void initRecord(DataTransObject dto, int bocmDate, int bocmTime, String bocmTraceNo,
			String bocmState,String sndBankno,String rcvBankno,String actBal,String bocmRepcd,String bocmRepmsg) throws SysTradeExecuteException{
		MyLog myLog = logPool.get();
		REQ_30061000801 reqDto = (REQ_30061000801)dto;
		REQ_30061000801.REQ_BODY reqBody = reqDto.getReqBody();
		REQ_SYS_HEAD reqSysHead = reqDto.getReqSysHead();

		BocmSndTraceInitModel record = new BocmSndTraceInitModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setSourceType(reqSysHead.getSourceType());
		record.setTxBranch(reqSysHead.getBranchId());
		record.setTranType("JH11");
		// 通存通兑标志；0通存、1通兑
		record.setDcFlag("1");
		
		String IcCardFlag = reqBody.getIcCardFlgT4();
		if("2".equals(IcCardFlag)){
			record.setTxCode("10001");
		}else{
			record.setTxCode("20001");
		}
		
		//账户余额
		if(actBal!=null&&!actBal.equals("")){
			record.setActBal(new BigDecimal(actBal));
		}	
		record.setTxAmt(reqBody.getTrsrAmtT3());
		//现转标志；0现金、1转账
		record.setTxInd("1");

		//手续费收取方式
		record.setFeeFlag("0");
		
		//交易发起行
		record.setSndBankno(sndBankno);
		//交易接收行
		record.setRcvBankno(rcvBankno);
		
		//付款方信息
		record.setPayerBank(reqBody.getPyrOpnBnkNoT2());
		record.setPayerActtp(reqBody.getPyrAcctTpT());
		record.setPayerAcno(reqBody.getPyrAcctNoT2());
		record.setPayerName(reqBody.getPyrNaT());
		
		//收款方信息
		record.setPayeeBank(reqBody.getPyeeOpnBnkNoT6());
		record.setPayeeActtp(reqBody.getPyAcctTpT());
		record.setPayeeAcno(reqBody.getRcptPrAcctNoT2());
		record.setPayeeName(reqBody.getRcptPrNmT7());

		//记账状态
		record.setHostState("0");
		record.setBocmState(bocmState);
		record.setBocmDate(bocmDate);
		record.setBocmTime(bocmTime);
		record.setBocmTraceno(bocmTraceNo);
		
		record.setCheckFlag("1");
		
		record.setTxTel(reqSysHead.getUserId());
		record.setChkTel(reqSysHead.getApprUserId());
		record.setAuthTel(reqSysHead.getAuthUserId());
		//记账系统日期
		String settlementDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		record.setTxDate(Integer.parseInt(settlementDate));	
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
		REQ_30061000801 reqDto = (REQ_30061000801)dto;
		REQ_30061000801.REQ_BODY reqBody = reqDto.getReqBody();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		//交易机构号，交易柜员号
		txTel = reqDto.getReqSysHead().getUserId();
		txBrno = reqDto.getReqSysHead().getBranchId();
		ESB_REQ_30011000104 esbReq_30011000104 = new ESB_REQ_30011000104(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000104.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30011000104.setReqSysHead(reqSysHead);

		reqSysHead.setProgramId(reqDto.getReqSysHead().getProgramId());
		reqSysHead.setSourceBranchNo(reqDto.getReqSysHead().getSourceBranchNo());
		reqSysHead.setSourceType("BU");
		
		ESB_REQ_30011000104.REQ_BODY reqBody_30011000104 = esbReq_30011000104.getReqBody();
		//账号
		reqBody_30011000104.setBaseAcctNo(reqBody.getPyrAcctNoT2());
		reqBody_30011000104.setAcctName(reqBody.getPyrNaT());
		reqBody_30011000104.setTranType("JH11");
		reqBody_30011000104.setTranCcy("CNY");
		reqBody_30011000104.setTranAmt(reqBody.getTrsrAmtT3());
		reqBody_30011000104.setWithdrawalType("P");
		reqBody_30011000104.setPassword(reqBody.getPwdT());
		reqBody_30011000104.setOthBaseAcctNo(reqBody.getRcptPrAcctNoT2());
		reqBody_30011000104.setOthBaseAcctName(reqBody.getRcptPrNmT7());
		reqBody_30011000104.setChannelType("BU");
		//记账系统日期
		String settlementDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		reqBody_30011000104.setSettlementDate(settlementDate);
		reqBody_30011000104.setCollateFlag("Y");
		reqBody_30011000104.setDirection("O");
		
//		我方银行账号
		reqBody_30011000104.setBankCode(reqBody.getPyeeOpnBnkNoT6());
//		对方银行账号
		reqBody_30011000104.setOthBankCode(reqBody.getPyrOpnBnkNoT2());

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
	public BocmSndTraceUpdModel updateBocmRecord(REQ_30061000801 reqDto,
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
	* @Title: magCardCharge 
	* @Description: 交行磁条卡通兑记账
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return REP_10001    返回类型 
	* @throws 
	*/
	public REP_10001 magCardCharge(DataTransObject dto, REQ_10001 req10001) throws SysTradeExecuteException { 
		REQ_30061000801 reqDto = (REQ_30061000801)dto;
		REQ_30061000801.REQ_BODY reqBody = reqDto.getReqBody();
		
		//交易金额
		String amt = reqBody.getTrsrAmtT3();
		//交易金额补零
		req10001.setTxnAmt(NumberUtil.addPoint(Double.parseDouble(amt)));
		//pin转加密
		String pin = super.convPin(reqDto,reqBody.getPyrAcctNoT2(),reqBody.getPwdT());
		req10001.setPin(pin);
		req10001.setOprFlg(reqBody.getCardInWyT());
		//业务模式，0 现金1 转账（即实时转账）9 其他
		req10001.setTxnMod("1");
		req10001.setPayBnk(reqBody.getPyrOpnBnkNoT2());
		//付款人账户类型,0 银行账号1 贷记卡2 借记卡3其他
		req10001.setPactTp(reqBody.getPyrAcctTpT());
		req10001.setPactNo(reqBody.getPyrAcctNoT2());
		req10001.setPayNam(reqBody.getPyrNaT());
		req10001.setRecBnk(reqBody.getPyeeOpnBnkNoT6());
		req10001.setRactTp(reqBody.getPyAcctTpT());
		req10001.setRactNo(reqBody.getRcptPrAcctNoT2());
		req10001.setRecNam(reqBody.getRcptPrNmT7());
		req10001.setCuIdTp(reqBody.getIdTpT2());
		req10001.setCuIdNo(reqBody.getHldrGlblIdT());
		req10001.setAgIdTp(reqBody.getAgentCrtfT());
		req10001.setAgIdNo(reqBody.getCmsnHldrGlblIdT());
		req10001.setSecMag(reqBody.getScdTrkT());
		req10001.setThdMag(reqBody.getThrTrkInfoT1());
		req10001.setRemark(reqBody.getNoteT2());
        

		REP_10001 rep_10001 = forwardToBocmService.sendToBocm(req10001, 
				REP_10001.class);
		
		return rep_10001;
	}
	/** 
	* @Title: iCCardCharge 
	* @Description: 交行IC卡通兑记账 
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return REP_20001    返回类型 
	* @throws 
	*/
	public REP_20001 iCCardCharge(DataTransObject dto,REQ_20001 req20001) throws SysTradeExecuteException {
		REQ_30061000801 reqDto = (REQ_30061000801)dto;
		REQ_30061000801.REQ_BODY reqBody = reqDto.getReqBody();
		
		//交易金额
		String amt = reqBody.getTrsrAmtT3();
		//交易金额补零
		req20001.setTxnAmt(NumberUtil.addPoint(Double.parseDouble(amt)));
		//pin转加密
		String pin = super.convPin(reqDto,reqBody.getPyrAcctNoT2(),reqBody.getPwdT());
		req20001.setPin(pin);
		req20001.setOprFlg(reqBody.getCardInWyT());
		//业务模式，0 现金1 转账（即实时转账）2 普通转账（2小时后）3 隔日转账9 其他
		req20001.setTxnMod("1");
		req20001.setPayBnk(reqBody.getPyrOpnBnkNoT2());
		//付款人账户类型,0 银行账号1 贷记卡2 借记卡3其他
		req20001.setPactTp(reqBody.getPyrAcctTpT());
		req20001.setPactNo(reqBody.getPyrAcctNoT2());
		req20001.setPayNam(reqBody.getPyrNaT());
		req20001.setRecBnk(reqBody.getPyeeOpnBnkNoT6());
		req20001.setRactTp(reqBody.getPyAcctTpT());
		req20001.setRactNo(reqBody.getRcptPrAcctNoT2());
		req20001.setRecNam(reqBody.getRcptPrNmT7());
		req20001.setCuIdTp(reqBody.getIdTpT2());
		req20001.setCuIdNo(reqBody.getHldrGlblIdT());
		req20001.setAgIdTp(reqBody.getAgentCrtfT());
		req20001.setAgIdNo(reqBody.getCmsnHldrGlblIdT());
		req20001.setSeqNo(reqBody.getIcCardSeqNoT1());
		req20001.setARQC(reqBody.getIcCard91T());
		req20001.setICAID(reqBody.getIcCard9f09T());
		req20001.setICOutDate(reqBody.getIcCardAvaiDtT());
		req20001.setICOutDate("241231");//IC卡有效期
		req20001.setICData(reqBody.getIcCardF55T());
		req20001.setRemark(reqBody.getNoteT2());
        
		REP_20001 rep_20001 = forwardToBocmService.sendToBocm(req20001, 
				REP_20001.class);
		return rep_20001;
	}

	/** 
	* @Title: bocmReversal 
	* @Description: 交行个人储蓄抹帐业务
	* @param @param reqDto
	* @param @param oLogNo
	* @param @param oTxnCd
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return REP_10009    返回类型 
	* @throws 
	*/
	private REP_10009 bocmReversal(REQ_30061000801 reqDto,String oLogNo,String oTxnCd)
			throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061000801.REQ_BODY reqBody = reqDto.getReqBody();
		Integer sysTraceno = publicService.getSysTraceno();		
		REQ_10009 req10009 = new REQ_10009(myLog, reqDto.getSysDate(), reqDto.getSysTime(), sysTraceno);
		super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req10009); // 设置报文头中的行号信息
		req10009.setOlogNo(String.format("%06d%08d", reqDto.getSysDate() % 1000000, reqDto.getSysTraceno()));

		//交易金额
		String amt = reqBody.getTrsrAmtT3();
		//交易金额补零
		req10009.setTxnAmt(NumberUtil.addPoint(Double.parseDouble(amt)));
		//业务模式，0 现金1 转账（即实时转账）2 普通转账（2小时后）3 隔日转账9 其他
		req10009.setTxnMod("1");
		req10009.setPayBnk(reqBody.getPyrOpnBnkNoT2());
		//付款人账户类型,0 银行账号1 贷记卡2 借记卡3其他
		req10009.setPactTp(reqBody.getPyrAcctTpT());
		req10009.setPactNo(reqBody.getPyrAcctNoT2());
		req10009.setPayNam(reqBody.getPyrNaT());
		req10009.setRecBnk(reqBody.getPyeeOpnBnkNoT6());
		req10009.setRactTp(reqBody.getPyAcctTpT());
		req10009.setRactNo(reqBody.getRcptPrAcctNoT2());
		req10009.setRecNam(reqBody.getRcptPrNmT7());
		req10009.setCuIdTp(reqBody.getIdTpT2());
		req10009.setCuIdNo(reqBody.getHldrGlblIdT());
		req10009.setAgIdTp(reqBody.getAgentCrtfT());
		req10009.setAgIdNo(reqBody.getCmsnHldrGlblIdT());
		req10009.setRemark(reqBody.getNoteT2());
        
		REP_10009 rep_10009 = forwardToBocmService.sendToBocm(req10009, 
				REP_10009.class);
		return rep_10009;
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
		REQ_30061000801 reqDto = (REQ_30061000801)dto;
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
			String hostState,String fee, String retCode, String retMsg) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, dto.getSysDate(), dto.getSysTime(),
				dto.getSysTraceno());
		if(hostDate!=null&&!"".equals(hostDate)) {
			record.setHostDate(Integer.parseInt(hostDate));
		}
		record.setHostState(hostState);
		record.setHostTraceno(hostTraceno);
		record.setRetCode(retCode);
		record.setRetMsg(retMsg);
		record.setFee(new BigDecimal(fee));
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

