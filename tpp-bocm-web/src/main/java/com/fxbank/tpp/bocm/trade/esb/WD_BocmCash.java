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

	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "bocm.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061001001 reqDto = (REQ_30061001001) dto;
		REQ_30061001001.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30061001001 rep = new REP_30061001001();
		//1.交行记账    IC卡和磁条卡走不同的交行接口
		//交行记账流水号  原发起方交易流水
		String bocmTraceNo = null;
		int bocmDate = 0;
		int bocmTime = 0;
		//卡类型  磁条还是IC卡
		//String IC_CARD_FLG_T4 = reqBody.getIcCardFlgT4();//IC卡磁条卡标志
		//卡类型名称
		String cardTypeName = "";
		//原交易代码
		String oTxnCd = null;
		REP_10001 rep10001 = null;
		REP_20001 rep20001 = null;
		REQ_10001 req10001 = null;
		REQ_20001 req20001 = null;
		
		if("2".equals(reqBody.getIcCardFlgT4())){
			oTxnCd = "10001";
			cardTypeName = "磁条卡";
			req10001 = new REQ_10001(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
			super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req10001); // 设置报文头中的行号信息
		}else{
			cardTypeName = "IC卡";
			oTxnCd = "20001";
			req20001 = new REQ_20001(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
			super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req20001); // 设置报文头中的行号信息
		}	
		

			//磁条卡的通兑10001
			try {
				if("10001".equals(oTxnCd)){
					myLog.info(logger, "发送磁条卡通兑请求至交行");
					rep10001 = magCardCharge(reqDto,req10001);
					bocmTraceNo = rep10001.getRlogNo();
					bocmDate = rep10001.getSysDate();
					bocmTime = rep10001.getSysTime();
					//手续费
					rep.getRepBody().setFeeT3(rep10001.getFee().toString());
					//余额
					rep.getRepBody().setBalance3T(rep10001.getActBal().toString());				
				}else{
					myLog.info(logger, "发送IC卡通兑请求至交行");
					rep20001 = iCCardCharge(reqDto,req20001);
					bocmTraceNo = rep20001.getRlogNo();
					//手续费
					rep.getRepBody().setFeeT3(rep20001.getFee().toString());
					//余额
					rep.getRepBody().setBalance3T(rep20001.getActBal().toString());
				}
				//插入流水表
				//交行记账状态，0-登记，1-成功，2-失败，3-超时，4-冲正成功，5-冲正失败， 6-冲正超时
				initRecord(reqDto, bocmDate, bocmTime, bocmTraceNo, "1");
				myLog.info(logger, "交行卡取现金，交行"+cardTypeName+"通兑记账成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
				
			} catch (SysTradeExecuteException e) {
				// 如果不是账务类请求，可以不用分类处理应答码，统一当成失败处理即可
				// 如果交易不关心返回的异常类型，直接可以不捕获，直接省略catch，抛出异常即可
				if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000006) // 生成请求失败
						|| e.getRspCode().equals(SysTradeExecuteException.CIP_E_000007)
						|| e.getRspCode().equals(SysTradeExecuteException.CIP_E_000008)) {
					//交行核心记账报错，交易失败
					myLog.error(logger, "交行卡取现金，交行"+cardTypeName+"通兑记账失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败:"+e.getRspMsg());
					throw e2;
				}else if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000009)||e.getRspCode().equals("JH6203")) { // 接收交行返回结果超时
					//'交通银行记账状态，0-登记，1-成功，2-失败，3-超时，4-冲正成功，5-冲正失败';
					myLog.error(logger, "交行卡取现金，交行"+cardTypeName+"通兑记账返回结果超时，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					try {
						myLog.error(logger, "发送"+cardTypeName+"通兑抹账请求至交行");
						bocmReversal(reqDto,bocmTraceNo,oTxnCd);
						initRecord(reqDto, bocmDate, bocmTime, bocmTraceNo, "3");
					    myLog.info(logger, "交行卡取现金，交行磁条卡通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno());
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败，请求交行记账超时");
						throw e2;
					}catch(SysTradeExecuteException e1) {
						myLog.error(logger, "交行卡取现金，交行磁条卡通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e1);
						initRecord(reqDto, bocmDate, bocmTime, bocmTraceNo, "3");
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败，请求交行记账超时");
						throw e2;
					}
				}else { // 目标系统应答失败
							// 确认是否有冲正操作
					//交行核心记账报错，交易失败
					myLog.error(logger, "交行卡取现金，交行"+cardTypeName+"通兑记账失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败:"+e.getRspMsg());
					throw e2;
				}
			}catch (Exception e) { // 其它未知错误，可以当成超时处理
				// 确认是否有冲正操作
				myLog.error(logger, "交行卡取现金，交行磁条卡通兑记账其它未知错误，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				try {
					myLog.error(logger, "发送"+cardTypeName+"通兑抹账请求至交行");
					bocmReversal(reqDto,bocmTraceNo,oTxnCd);
					initRecord(reqDto, bocmDate, bocmTime, bocmTraceNo, "2");
				    myLog.info(logger, "交行卡取现金，交行磁条卡通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno());
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败，请求交行记账超时");
					throw e2;
				}catch(SysTradeExecuteException e1) {
					myLog.error(logger, "交行卡取现金，交行磁条卡通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e1);
					initRecord(reqDto, bocmDate, bocmTime, bocmTraceNo, "2");
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败，请求交行记账超时");
					throw e2;
				}
			}						
		//3.核心记账  暂时不考虑延时转账
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
			myLog.info(logger, "发送交行卡取现金核心记账请求");
			esbRep_30011000104 = hostCharge(reqDto);
			hostDate = esbRep_30011000104.getRepSysHead().getRunDate();//核心日期
			hostTraceno = esbRep_30011000104.getRepBody().getReference();//核心流水
			retCode = esbRep_30011000104.getRepSysHead().getRet().get(0).getRetCode();//核心返回
			retMsg = esbRep_30011000104.getRepSysHead().getRet().get(0).getRetMsg();			
			accounting_branch = esbRep_30011000104.getRepBody().getAcctBranch();
		}catch(SysTradeExecuteException e) {			
			if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000004)) { // ESB超时	
				//本行记账超时，如果冲正也超时，提示交易失败（不出钱），核心记账超时，核心冲正超时。（交行记账成功，交易结果以本行为主，对账不返回）在完美一点把交行的冲正加上				
				//超时处理是交易失败方向走还是往				
				//提示超时;
				//核心记账状态，1-成功，4-冲正成功，5-冲正失败，6-冲正超时	
				
				//交行抹账
				try {
					bocmReversal(reqDto,bocmTraceNo,oTxnCd);
					updateBocmRecord(reqDto, bocmDate, bocmTime, bocmTraceNo, "4");
				    myLog.info(logger, "交行卡取现金，交行"+cardTypeName+"通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno());
				}catch(Exception e1) {
					myLog.error(logger, "交行卡取现金，交行"+cardTypeName+"通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e1);
					updateBocmRecord(reqDto, bocmDate, bocmTime, bocmTraceNo, "5");
				}
				
				//本行核心抹账
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
						myLog.error(logger, "交行卡取现金，本行核心冲正超时，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e1);
						SysTradeExecuteException e2 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000004,"交易失败:"+e.getRspMsg()+",核心记账超时，核心冲正超时");
						throw e2;
					//其他冲正错误
					}else {						
						updateHostCheck(reqDto, "", "", "5", e.getRspCode(), e.getRspMsg(),"0");
						myLog.error(logger, "交行卡取现金，本行核心冲正失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e1);
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败:"+e.getRspMsg()+"，核心记账超时，核心冲正失败");
						throw e2;
					}
				}
				updateHostCheck(reqDto, hostDate, hostTraceno, "4", hostReversalCode, hostReversalMsg,"0");
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败:"+e.getRspMsg());
				myLog.error(logger, "交行卡存现金，本行核心冲正成功，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(),e2);
				throw e2;
				//本行冲正
			}else { //ESB调用其他错误
				//本行记账失败，交行冲正
				//核心记账失败，交行冲正，提示核心错误，
				updateHostRecord(reqDto, hostDate, hostTraceno, "2", e.getRspCode(), e.getRspMsg());
				myLog.error(logger, "交行卡取现金，核心记账失败，发送"+cardTypeName+"通兑抹账请求至交行，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				try {
					bocmReversal(reqDto,bocmTraceNo,oTxnCd);
					updateBocmRecord(reqDto, bocmDate, bocmTime, bocmTraceNo, "4");
				    myLog.info(logger, "交行卡取现金，交行"+cardTypeName+"通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno());
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败，核心记账失败，"+e.getRspMsg());
					throw e2;
				}catch(SysTradeExecuteException e1) {
					myLog.error(logger, "交行卡取现金，交行"+cardTypeName+"通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e1);
					updateBocmRecord(reqDto, bocmDate, bocmTime, bocmTraceNo, "5");
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败，核心记账失败，"+e.getRspMsg());
					throw e2;
				}
			}
		}
		//5.更新流水表核心记账状态
		myLog.info(logger, "交行卡取现金核心记账成功，渠道日期" + reqDto.getSysDate() + 
				"渠道流水号" + reqDto.getSysTraceno());
		updateHostRecord(reqDto, hostDate, hostTraceno, "1", retCode, retMsg,accounting_branch);
		return rep;
	}
	
	public void initRecord(DataTransObject dto, int bocmDate, int bocmTime, String bocmTraceNo,
			String bocmState) throws SysTradeExecuteException{
		MyLog myLog = logPool.get();
		REQ_30061001001 reqDto = (REQ_30061001001)dto;
		REQ_30061001001.REQ_BODY reqBody = reqDto.getReqBody();
		BocmSndTraceInitModel record = new BocmSndTraceInitModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
		record.setSourceType(reqDto.getSourceType());
		record.setTxBranch(reqDto.getReqSysHead().getBranchId());
		//现转标志
		record.setTxInd("");
		//通存通兑标志 0通存、1通兑
		record.setDcFlag("1");
		//交易金额
		record.setTxAmt(reqBody.getWthrAmtT());
		//付款人账户
		record.setPayerAcno(reqBody.getCardNoT3());
		//付款人姓名
		record.setPayerName(reqBody.getNmT());
		//收款人账户
		record.setPayeeAcno("");
		//收款人姓名
		record.setPayeeName("");
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
	public REP_10001 magCardCharge(DataTransObject dto, REQ_10001 req10001) throws SysTradeExecuteException { 
		REQ_30061001001 reqDto = (REQ_30061001001)dto;
		REQ_30061001001.REQ_BODY reqBody = reqDto.getReqBody();
		req10001.setTxnAmt(Double.parseDouble(reqBody.getWthrAmtT()));//交易金额
		req10001.setPin(reqBody.getPwdT());//交易密码
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
		req20001.setTxnAmt(Double.parseDouble(reqBody.getWthrAmtT()));//交易金额
		req20001.setPin(reqBody.getPwdT());//交易密码
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
	* @Description: 本行核心记账 
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30011000103    返回类型 
	* @throws 
	*/
	public ESB_REP_30011000104 hostCharge(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061001001 reqDto = (REQ_30061001001)dto;
		REQ_30061001001.REQ_BODY reqBody = reqDto.getReqBody();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
//		try (Jedis jedis = myJedis.connect()) {
//			txBrno = jedis.get(COMMON_PREFIX + "txbrno");
//			txTel = jedis.get(COMMON_PREFIX + "txtel");
//		}

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
		reqBody_30011000104.setAcctName(reqBody.getNmT());
		reqBody_30011000104.setTranType("JH13");
		reqBody_30011000104.setTranCcy("CNY");
		reqBody_30011000104.setTranAmt(reqBody.getWthrAmtT());
		reqBody_30011000104.setSettlementDate("");
		reqBody_30011000104.setCollateFlag("Y");
		//TT-账户内扣 CA-现金
		reqBody_30011000104.setChargeMethod(reqBody.getFeeRcveWyT1());
		
		
//		if(1==1){
//			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000006);
//			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000009);	
//			SysTradeExecuteException e = new SysTradeExecuteException("JH6203");			
//		throw e;
//	}	

		ESB_REP_30011000104 esbRep_30011000104 = forwardToESBService.sendToESB(esbReq_30011000104, reqBody_30011000104,
				ESB_REP_30011000104.class);
		return esbRep_30011000104;
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
	private BocmSndTraceUpdModel updateHostRecord(REQ_30061001001 reqDto, String hostDate, String hostTraceno,
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
	private ESB_REP_30011000104 hostCharge(REQ_30061001001 reqDto)
			throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		REQ_30061001001.REQ_BODY reqBody = reqDto.getReqBody();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
//		try (Jedis jedis = myJedis.connect()) {
//			txBrno = jedis.get(COMMON_PREFIX + "txbrno");
//			txTel = jedis.get(COMMON_PREFIX + "txtel");
//		}
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
		reqBody_30011000104.setAcctName(reqBody.getNmT());
		reqBody_30011000104.setTranType("JH13");
		reqBody_30011000104.setChannelType("BU");
		reqBody_30011000104.setTranCcy("CNY");
		reqBody_30011000104.setTranAmt(reqBody.getWthrAmtT());
		reqBody_30011000104.setSettlementDate(reqDto.getSysDate()+"");
		reqBody_30011000104.setCollateFlag("Y");
		//TT-账户内扣 CA-现金
		reqBody_30011000104.setChargeMethod(reqBody.getFeeRcveWyT1());
		
		//发起行行号
//		reqBody_30011000104.setSendBankCode(reqBody.getPyOpnBrNoT());
		//我方银行账号
//		reqBody_30011000104.setBankCode(reqBody.getPyeeOpnBnkNoT6());
//		reqBody_30011000104.setSendBankCode("313221099020");
		reqBody_30011000104.setSendBankCode("313226090656");
		//对方银行账号
		reqBody_30011000104.setOthBankCode(reqBody.getOpnAcctBnkNoT7());

		ESB_REP_30011000104 esbRep_30011000104 = forwardToESBService.sendToESB(esbReq_30011000104, reqBody_30011000104,
				ESB_REP_30011000104.class);
		
//		if(1==1){
//			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000004);
//			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000006);
//			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000009);			
//		throw e;
//	}
		

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
		REQ_10009 req_10009 = new REQ_10009(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
		REQ_30061001001.REQ_BODY reqBody = reqDto.getReqBody();
		super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req_10009); // 设置报文头中的行号信息		
		//组装抹账报文体
		//原发起方交易流水
		req_10009.setOlogNo(bocmTraceNo);
		//原交易代码 原交易代码       通兑-10001 IC通兑-20001
		req_10009.setOtxnCd(oTxnCd);
		//业务模式
		req_10009.setTxnMod("");
		//交易金额
		req_10009.setTxnAmt(Double.parseDouble(reqBody.getWthrAmtT()));
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
//		try (Jedis jedis = myJedis.connect()) {
//			txBrno = jedis.get(COMMON_PREFIX + "TXBRNO");
//			txTel = jedis.get(COMMON_PREFIX + "TXTEL");
//		}
		
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
		reqBody_30014000101.setReversalReason("核心记账超时,本行核心冲正");
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
		if(!"".equals(hostDate)&&hostDate!=null) {
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
	
	/** 
	* @Title: updateHostRecord 
	* @Description: 更新核心记账状态标记对账状态 
	*/
	public BocmSndTraceUpdModel updateHostCheck(DataTransObject dto, String hostDate, String hostTraceno,
			String hostState, String retCode, String retMsg, String checkFlag) throws SysTradeExecuteException {
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
		record.setCheckFlag(checkFlag);
		bocmSndTraceService.sndTraceUpd(record);
		return record;
	}
}
