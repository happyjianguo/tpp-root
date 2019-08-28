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
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.pub.service.IPublicService;
import com.fxbank.tpp.bocm.dto.esb.REP_30061001001;
import com.fxbank.tpp.bocm.dto.esb.REQ_30061001001;
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
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30011000104;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30014000101;
import com.fxbank.tpp.esb.service.IForwardToESBService;

/** 
* @ClassName: WD_BocmCash 
* @Description: 交行卡取现金
* @author YePuLiang
* @date 2019年4月16日 下午3:03:04 
*  
*/
@Service("REQ_30061001001")
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
	
	@Reference(version = "1.0.0")
	private IPublicService publicService;

	@Resource
	private MyJedis myJedis;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061001001 reqDto = (REQ_30061001001) dto;
		REQ_30061001001.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30061001001 rep = new REP_30061001001();
		
		//风险监控检查调用
		String payerAcno = reqBody.getCardNoT3();
		String payeeAcno = "";
		Long amt = (long)Double.parseDouble(reqBody.getWthrAmtT());
		//F-柜面  F01-跨行转账
		super.riskCheck(myLog, reqDto, payerAcno, payeeAcno, amt,"F","F01");
		
		//交行记账流水号  原发起方交易流水
		String bocmTraceNo = null;
		int bocmDate = 0;
		int bocmTime = 0;
		//卡类型名称
		String cardTypeName = "";
		//原交易代码
		String oTxnCd = null;
		REP_10001 rep10001 = null;
		REP_20001 rep20001 = null;
		REQ_10001 req10001 = null;
		REQ_20001 req20001 = null;
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
		//判断是磁条卡还是IC卡
		if("2".equals(reqBody.getIcCardFlgT4())){
			oTxnCd = "10001";
			cardTypeName = "磁条卡";
			req10001 = new REQ_10001(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
			super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req10001); // 设置报文头中的行号信息
			rbnkNo = req10001.getRbnkNo();
			sbnkNo = req10001.getSbnkNo();
		}else{
			cardTypeName = "IC卡";
			oTxnCd = "20001";
			req20001 = new REQ_20001(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
			super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req20001); // 设置报文头中的行号信息
			rbnkNo = req20001.getRbnkNo();
			sbnkNo = req20001.getSbnkNo();
		}			
		try {
			//1.交行记账    IC卡和磁条卡走不同的交行接口
			if("10001".equals(oTxnCd)){
				myLog.info(logger, "发送磁条卡通兑请求至交行");
				rep10001 = magCardCharge(reqDto,req10001);
				bocmTraceNo = rep10001.getRlogNo();
				bocmDate = rep10001.getSysDate();
				bocmTime = rep10001.getSysTime();	
				fee = rep10001.getFee().toString();
				actBal = rep10001.getActBal().toString();	
				bocmRepcd = rep10001.getTrspCd();
				bocmRepmsg = rep10001.getTrspMsg();							
			}else{
				myLog.info(logger, "发送IC卡通兑请求至交行");
				rep20001 = iCCardCharge(reqDto,req20001);		
				bocmTraceNo = rep20001.getRlogNo();
				bocmDate = rep20001.getSysDate();
				bocmTime = rep20001.getSysTime();	
				fee = rep20001.getFee().toString();
				actBal = rep20001.getActBal().toString();	
				bocmRepcd = rep20001.getTrspCd();
				bocmRepmsg = rep20001.getTrspMsg();				
			}
	
		} catch (SysTradeExecuteException e) {
			// 如果不是账务类请求，可以不用分类处理应答码，统一当成失败处理即可
			// 如果交易不关心返回的异常类型，直接可以不捕获，直接省略catch，抛出异常即可
			if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000006) // 生成请求失败
					|| e.getRspCode().equals(SysTradeExecuteException.CIP_E_000007)
					|| e.getRspCode().equals(SysTradeExecuteException.CIP_E_000008)) {
				//交行核心记账报错，交易失败
				myLog.error(logger, "交行卡取现金，交行"+cardTypeName+"通兑记账失败，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败:交行记账失败,"+e.getRspMsg());
				throw e2;
			}else if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000009)||e.getRspCode().equals("JH6203")) { // 接收交行返回结果超时
				//'交通银行记账状态，0-登记，1-成功，2-失败，3-超时，4-冲正成功，5-冲正失败';
				myLog.error(logger, "交行卡取现金，交行"+cardTypeName+"通兑记账返回结果超时，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				try {
					//登记流水表，记录请求交行记账为超时					
					myLog.error(logger, "交行卡取现金，交行"+cardTypeName+"交行系统记账超时，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					myLog.error(logger, "发送"+cardTypeName+"通兑抹账请求至交行");
					bocmReversal(reqDto,bocmTraceNo,oTxnCd);			
				    myLog.info(logger, "交行卡取现金，交行磁条卡通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno());
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败，请求交行记账超时");
					throw e2;
				}catch(Exception e1) {
					myLog.error(logger, "交行卡取现金，交行磁条卡通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e1);
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败，请求交行记账超时");
					throw e2;
				}
			}else { // 目标系统应答失败
						// 确认是否有冲正操作
				//交行核心记账报错，交易失败
				myLog.error(logger, "交行卡取现金，交行"+cardTypeName+"通兑记账失败，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败:交行记账失败,"+e.getRspMsg());
				throw e2;
			}
		}catch (Exception e) { // 其它未知错误，可以当成超时处理
			// 确认是否有冲正操作
			//'交通银行记账状态，0-登记，1-成功，2-失败，3-超时，4-冲正成功，5-冲正失败';
			myLog.error(logger, "交行卡取现金，交行"+cardTypeName+"通兑记账其他未知错误，渠道日期" + reqDto.getSysDate() + 
					"渠道流水号" + reqDto.getSysTraceno(), e);
			try {
				//登记流水表，记录请求交行记账为超时					
				myLog.error(logger, "交行卡取现金，交行"+cardTypeName+"交行系统记账未知错误，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				myLog.error(logger, "发送"+cardTypeName+"通兑抹账请求至交行");
				bocmReversal(reqDto,bocmTraceNo,oTxnCd);			
			    myLog.info(logger, "交行卡取现金，交行"+cardTypeName+"通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno());
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败，请求交行记账未知错误");
				throw e2;
			}catch(Exception e1) {
				myLog.error(logger, "交行卡取现金，交行"+cardTypeName+"通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
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
		//2.插入流水表
		//交行记账状态，0-登记，1-成功，2-失败，3-超时，4-冲正成功，5-冲正失败， 6-冲正超时
		initRecord(reqDto, bocmDate, bocmTime, bocmTraceNo, "1",sbnkNo,rbnkNo,actBal,bocmRepcd,bocmRepmsg);		
		myLog.info(logger, "交行卡取现金，交行"+cardTypeName+"通兑记账成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());	
	
					
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
			//3.核心记账  暂时不考虑延时转账
			myLog.info(logger, "发送交行卡取现金核心记账请求");
			esbRep_30011000104 = hostCharge(reqDto);
			ESB_REP_30011000104.Fee tradFee = esbRep_30011000104.getRepBody().getFeeDetail().get(0);	
			//如果返回手续费为空，则手续费赋值为0
			fee = tradFee.getFeeAmt();
			if(fee.equals("")){
				fee = "0";
			}
			hostDate = esbRep_30011000104.getRepSysHead().getRunDate();//核心日期
			hostTraceno = esbRep_30011000104.getRepBody().getReference();//核心流水
			retCode = esbRep_30011000104.getRepSysHead().getRet().get(0).getRetCode();//核心返回
			retMsg = esbRep_30011000104.getRepSysHead().getRet().get(0).getRetMsg();	
			
		}catch(SysTradeExecuteException e) {			
			if (SysTradeExecuteException.CIP_E_000004.equals(e.getRspCode())||"ESB_E_000052".equals(e.getRspCode())) { // ESB超时							
				myLog.info(logger, "交行卡取现金核心记账请求超时",e);
				//本行记账超时，核心冲正，交行冲正,如果记账成功,对账时交行补账
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
				myLog.error(logger, "交行卡存现金，本行核心冲正成功，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(),e);
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败:"+e.getRspMsg()+",核心冲正成功");			
				throw e2;
				//本行冲正
			}else { //ESB调用其他错误
				//核心记账失败，交行冲正，提示核心错误，		
				myLog.error(logger, "交行卡取现金核心记账失败",e);
				try {
					bocmReversal(reqDto,bocmTraceNo,oTxnCd);
					updateBocmRecord(reqDto, bocmDate, bocmTime, bocmTraceNo, "4");
				    myLog.info(logger, "交行卡取现金，交行系统"+cardTypeName+"通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno());
				}catch(Exception e1) {
					updateBocmRecord(reqDto, bocmDate, bocmTime, bocmTraceNo, "5");
					myLog.error(logger, "交行卡取现金，交行系统"+cardTypeName+"通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e1);
				}
				updateHostRecord(reqDto, hostDate, hostTraceno, "2", fee,e.getRspCode(), e.getRspMsg());
				myLog.error(logger, "交行卡取现金，核心记账失败，更新核心记账状态，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败，核心记账失败，"+e.getRspMsg());
				throw e2;
			}
		}
		//4.更新流水表核心记账状态
		myLog.info(logger, "交行卡取现金核心记账成功，渠道日期" + reqDto.getSysDate() + 
				"渠道流水号" + reqDto.getSysTraceno());
		updateHostRecord(reqDto, hostDate, hostTraceno, "1",fee, retCode, retMsg);
		rep.getRepBody().setHostTraceNo(hostTraceno);
		rep.getRepBody().setBalance3T(actBal);
		rep.getRepBody().setFeeT3(fee);
		
		//风险监控通知   oper_status 01-成功，02-失败  resp_code 应答码   55-密码输错，51-余额不足，00-交易成功
		super.statusNotify(myLog, reqDto, payerAcno, payeeAcno, amt,"F","F01","01","00");
		
		return rep;
	}
	
	public void initRecord(DataTransObject dto, int bocmDate, int bocmTime, String bocmTraceNo,
			String bocmState,String sndBankno,String rcvBankno, String actBal,String bocmRepcd,String bocmRepmsg) throws SysTradeExecuteException{
		MyLog myLog = logPool.get();
		REQ_30061001001 reqDto = (REQ_30061001001)dto;
		REQ_SYS_HEAD reqSysHead = reqDto.getReqSysHead();
		REQ_30061001001.REQ_BODY reqBody = reqDto.getReqBody();
		BocmSndTraceInitModel record = new BocmSndTraceInitModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
		record.setSourceType(reqDto.getSourceType());
		record.setTxBranch(reqDto.getReqSysHead().getBranchId());
		record.setTranType("JH13");
		//现转标志
		record.setTxInd("0");
		//通存通兑标志 0通存、1通兑
		record.setDcFlag("1");
		
		String IcCardFlag = reqBody.getIcCardFlgT4();
		if("2".equals(IcCardFlag)){
			record.setTxCode("10001");
		}else{
			record.setTxCode("20001");
		}	
		
		//交易金额
		record.setTxAmt(reqBody.getWthrAmtT());
		//账户余额
		if(actBal!=null&&!actBal.equals("")){
			record.setActBal(new BigDecimal(actBal));
		}	
		record.setFeeFlag("0");
		//交易发起行
		record.setSndBankno(sndBankno);
		//交易接收行
		record.setRcvBankno(rcvBankno);
		
		//付款人开户行
		record.setPayerBank(reqBody.getOpnAcctBnkNoT7());
		//付款人账户类型
		record.setPayerActtp(reqBody.getAcctNoTpT());
		//付款人账户
		record.setPayerAcno(reqBody.getCardNoT3());
		//付款人姓名
		record.setPayerName(reqBody.getNmT());
		
		//手续费收取方式
		record.setFeeFlag(reqBody.getFeeRcveWyT1());
		//应收手续费
		record.setFee(new BigDecimal(reqBody.getHndlPymntFeeT5()));
		
		//交通银行记账机构
		record.setBocmBranch("");
		//对账标志
		record.setCheckFlag("1");
		//核心记账状态
		record.setHostState("0");
		//交行记账状态
		record.setBocmState(bocmState);
		record.setBocmDate(bocmDate);
		record.setBocmTime(bocmTime);
		record.setBocmTraceno(bocmTraceNo);
		record.setBocmRepcd(bocmRepcd);
		record.setBocmRepmsg(bocmRepmsg);	
		
		record.setTxTel(reqSysHead.getUserId());
		record.setSourceType(reqSysHead.getSourceType());
		record.setTxBranch(reqSysHead.getBranchId());
		record.setChkTel(reqSysHead.getApprUserId());
		record.setAuthTel(reqSysHead.getAuthUserId());
		//记账系统日期
		String settlementDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		record.setTxDate(Integer.parseInt(settlementDate));	
	
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
	public REP_10001 magCardCharge(DataTransObject dto, REQ_10001 req10001) throws SysTradeExecuteException { 
		REQ_30061001001 reqDto = (REQ_30061001001)dto;
		REQ_30061001001.REQ_BODY reqBody = reqDto.getReqBody();
		
		
		//交易金额
		String amt = reqBody.getWthrAmtT();
		//交易金额补零
		req10001.setTxnAmt(NumberUtil.addPoint(Double.parseDouble(amt)));
		//柜面的加密密码字段转交行密码字段加密
		String pin = super.convPin(reqDto,reqBody.getCardNoT3(),reqBody.getPwdT());
		req10001.setPin(pin);
		req10001.setOprFlg("0");//卡输入方式  通兑必须刷卡
		req10001.setTxnMod("0");//业务模式 0 现金 1 转账（实时转账）
		req10001.setPayBnk(reqBody.getOpnAcctBnkNoT7());//付款人开户行行号
		req10001.setPactTp(reqBody.getAcctNoTpT());//付款人账户类型
		req10001.setPactNo(reqBody.getCardNoT3());//付款人账号
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
	public REP_20001 iCCardCharge(DataTransObject dto,REQ_20001 req20001) throws SysTradeExecuteException {
		REQ_30061001001 reqDto = (REQ_30061001001)dto;
		REQ_30061001001.REQ_BODY reqBody = reqDto.getReqBody();
		//交易金额
		String amt = reqBody.getWthrAmtT();
		//交易金额补零
		req20001.setTxnAmt(NumberUtil.addPoint(Double.parseDouble(amt)));
		//pin转加密
		String pin = super.convPin(reqDto,reqBody.getCardNoT3(),reqBody.getPwdT());
		req20001.setPin(pin);
		req20001.setOprFlg("0");//卡输入方式  通兑必须刷卡
		req20001.setTxnMod("0");//业务模式 0 现金 1 转账（实时转账）
		req20001.setPayBnk(reqBody.getOpnAcctBnkNoT7());//付款人开户行行号
		req20001.setPactTp(reqBody.getAcctNoTpT());//付款人账户类型
		req20001.setPactNo(reqBody.getCardNoT3());//付款人账号
		req20001.setPayNam(reqBody.getNmT());//付款人名称
		req20001.setCuIdTp(reqBody.getIdTpT2());//证件种类
		req20001.setCuIdNo(reqBody.getHldrGlblIdT());//证件号码
		req20001.setAgIdTp(reqBody.getAgentIdTpT3());//代理人证件类型
		req20001.setAgIdNo(reqBody.getCmsnHldrGlblIdT());//代理人证件号码	
		req20001.setSeqNo(reqBody.getIcCardSeqNoT1());//IC卡顺序号
		req20001.setARQC(reqBody.getIcCard91T());//IC卡发卡行认证
		req20001.setICAID(reqBody.getIcCard9f09T());//IC卡应用编号
		req20001.setICOutDate(reqBody.getIcCardAvaiDtT());//IC卡有效期
		req20001.setICData(reqBody.getIcCardF55T());//IC卡数据域（55域）
		//向交行系统发送交行IC卡通兑记账
		REP_20001 rep_20001 = forwardToBocmService.sendToBocm(req20001, 
				REP_20001.class);
		return rep_20001;
	}
	
	/** 
	* @Title: hostCharge 
	* @Description: 核心记账（交行柜面通统一记账）
	* @param reqDto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30011000103    返回类型 
	* @throws 
	*/
	private ESB_REP_30011000104 hostCharge(REQ_30061001001 reqDto)
			throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		REQ_30061001001.REQ_BODY reqBody = reqDto.getReqBody();
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
		esbReq_30011000104.setReqSysHead(reqSysHead);
		
		reqSysHead.setProgramId(reqDto.getReqSysHead().getProgramId());
		reqSysHead.setSourceBranchNo(reqDto.getReqSysHead().getSourceBranchNo());
		reqSysHead.setSourceType("BU");

		ESB_REQ_30011000104.REQ_BODY reqBody_30011000104 = esbReq_30011000104.getReqBody();
		reqBody_30011000104.setBaseAcctNo(reqBody.getCardNoT3());
		reqBody_30011000104.setPassword(reqBody.getPwdT());
		reqBody_30011000104.setAcctName(reqBody.getNmT());
		reqBody_30011000104.setTranType("JH13");
		reqBody_30011000104.setChannelType("BU");
		reqBody_30011000104.setTranCcy("CNY");
		reqBody_30011000104.setTranAmt(reqBody.getWthrAmtT());
		//记账系统日期
		String settlementDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		reqBody_30011000104.setSettlementDate(settlementDate);
		reqBody_30011000104.setCollateFlag("Y");
		//TT-账户内扣 CA-现金
		reqBody_30011000104.setChargeMethod(reqBody.getFeeRcveWyT1());
		//对方银行账号
		reqBody_30011000104.setOthBankCode(reqBody.getOpnAcctBnkNoT7());
		reqBody_30011000104.setDirection("O");

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
	private REP_10009 bocmReversal(REQ_30061001001 reqDto, String bocmTraceNo, String oTxnCd)
			throws SysTradeExecuteException {
		MyLog myLog = logPool.get();		
		Integer sysTraceno = publicService.getSysTraceno();		
		REQ_10009 req_10009 = new REQ_10009(myLog, reqDto.getSysDate(), reqDto.getSysTime(), sysTraceno);
		REQ_30061001001.REQ_BODY reqBody = reqDto.getReqBody();
		super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req_10009); // 设置报文头中的行号信息		
		//组装抹账报文体
		//原发起方交易流水	
		req_10009.setOlogNo(String.format("%06d%08d", reqDto.getSysDate() % 1000000, reqDto.getSysTraceno()));
		//原交易代码 原交易代码       通兑-10001 IC通兑-20001
		req_10009.setOtxnCd(oTxnCd);
		//业务模式
		req_10009.setTxnMod("0");
		
		//交易金额
		String amt = reqBody.getWthrAmtT();
		//交易金额补零
		req_10009.setTxnAmt(NumberUtil.addPoint(Double.parseDouble(amt)));
				
		req_10009.setTxnMod("0");//业务模式 0 现金 1 转账（实时转账）
		req_10009.setPayBnk(reqBody.getOpnAcctBnkNoT7());//付款人开户行行号
		req_10009.setPactTp(reqBody.getAcctNoTpT());//付款人账户类型
		req_10009.setPactNo(reqBody.getCardNoT3());//付款人账号
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
		REQ_30061001001 reqDto = (REQ_30061001001)dto;
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
			String hostState, String fee, String retCode, String retMsg) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, dto.getSysDate(), dto.getSysTime(),
				dto.getSysTraceno());
		if(!"".equals(hostDate)&&hostDate!=null) {
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
