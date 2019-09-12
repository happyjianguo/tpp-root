package com.fxbank.tpp.bocm.trade.bocm;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmRcvTraceQueryModel;
import com.fxbank.tpp.bocm.nettty.ServerInitializer;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30014000101;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043000101;
import com.fxbank.tpp.frms.model.REP_FRMS;
import com.fxbank.tpp.frms.model.REQ_FRMS;
import com.fxbank.tpp.frms.service.IForwardToFRMSService;

/**
 * @Description: 来账交易模版。适用场景：判断交易状态->核心记账,日终对账以银行为准。
 * @Author: 周勇沩
 * @Date: 2019-05-20 22:53:50
 */
public abstract class BaseTradeT1 {
	
	/** 
	* @Fields hostTimeoutException : 核心记账超时，提示处理失败
	*/ 
	public SysTradeExecuteException hostTimeoutException = null;
	
	/** 
	* @Fields hostTimeoutException : 核心记账失败
	*/ 
	public SysTradeExecuteException hostErrorException = null;
	
	/** 
	* @Fields hostTimeoutException : 磁条卡验证
	*/ 
	public SysTradeExecuteException cardMagValidateException = null;
	
	/** 
	* @Fields hostTimeoutException : IC卡验证
	*/ 
	public SysTradeExecuteException cardIcValidateException = null;

	/** 
	* @Fields othTimeoutException : 第三方记账超时，提示第三方记账超时
	*/ 
	public SysTradeExecuteException othTimeoutException = null;
	
	/** 
	* @Fields TRADE_DESC : 交易描述
	*/ 
	public String TRADE_DESC = "";
	
	public Logger logger = null;
	
	/** 
	* @Fields ESB_TIMEOUT_CODE : 核心超时ESB响应码
	*/ 
	private static final String ESB_TIMEOUT_CODE1 = "ESB_E_000052";
	
	/** 
	* @Fields othTimeoutQuery : 第三方记账超时，是否发起查询该笔交易 
	*/ 
	public Boolean othTimeoutQuery = false;
	
	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IBocmRcvTraceService bocmRcvTraceService;
	
	@Reference(version = "1.0.0")
	private IForwardToFRMSService forwardToFRMSService;
	
	private static final String BLOCK = "BLOCK";
	//账号
	public String payerAcno = "";
	//转账对手
	public String payeeAcno = "";
	//操作金额
	public long amt = 0L;
	//业务渠道
	public String bizChnl = "";
	//业务类型
	public String bizCode = "";
	
	/**
	* @Title: validateMag 
	* @Description: 磁条卡验证
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ModelBase    返回类型 
	* @throws
	 */
	public abstract ModelBase validateMag(DataTransObject dto) throws SysTradeExecuteException;
	
	/**
	* @Title: validateMag 
	* @Description: 磁条卡验证
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ModelBase    返回类型 
	* @throws
	 */
	public abstract ModelBase validateIC(DataTransObject dto) throws SysTradeExecuteException;
	
	/**
	 * @Description: 核心记账
	 * @Author: 周勇沩
	 * @Date: 2019-05-20 22:55:25
	 */
	public abstract ModelBase hostCharge(DataTransObject dto) throws SysTradeExecuteException;
	
	/**
	 * @Description: 核心记账状态查询
	 */
	public abstract ModelBase hostTranResult(DataTransObject dto) throws SysTradeExecuteException;


	/** 
	* @Title: hostTimeoutInitLog 
	* @Description: 核心记账超时登记 
	* @param @param dto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public abstract void hostTimeoutInitLog(DataTransObject dto) throws SysTradeExecuteException;

	/** 
	* @Title: hostSuccessInitLog 
	* @Description: 核心记账成功登记 
	* @param @param dto
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public abstract void hostSuccessInitLog(DataTransObject dto, ModelBase model) throws SysTradeExecuteException;
	
	/** 
	* @Title: hostSuccessInitLog 
	* @Description: 重发请求超时状态更新为记账成功 
	* @param @param dto
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public abstract void updateTimeoutSuccess(DataTransObject dto, ModelBase model) throws SysTradeExecuteException;
	
	/** 
	* @Title: hostSuccessInitLog 
	* @Description: 核心状态更新 
	* @param @param dto
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public abstract void updateOthSuccess(DataTransObject dto, ModelBase model) throws SysTradeExecuteException;

	/** 
	* @Title: backMsg 
	* @Description: 返回渠道日期和渠道流水号等
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return DataTransObject    返回类型 
	* @throws 
	*/
	public abstract DataTransObject backMsg(DataTransObject dto,ModelBase model) throws SysTradeExecuteException;
	
	/** 
	* @Title: backMsg 
	* @Description: 返回渠道日期和渠道流水号等
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return DataTransObject    返回类型 
	* @throws 
	*/
	public abstract DataTransObject backMsgOnTradeHave(DataTransObject dto,ModelBase model) throws SysTradeExecuteException;
	
	/**
	* @Title: queryRcvTrace 
	* @Description: 查询来账信息 
	* @param @param req
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return BocmRcvTraceQueryModel    返回类型 
	* @throws
	 */
	public abstract BocmRcvTraceQueryModel queryRcvTrace(DataTransObject req) throws SysTradeExecuteException;
	/**
	* @Title: hostReversal 
	* @Description: 核心冲正
	* @param @param req
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30014000101    返回类型 
	* @throws
	 */	
	public abstract ESB_REP_30014000101 hostReversal(DataTransObject dto)
			throws SysTradeExecuteException;
	
	private BocmRcvTraceQueryModel checkBocmRcvTrace(DataTransObject dto,int i) throws SysTradeExecuteException{
		MyLog myLog = logPool.get();
		BocmRcvTraceQueryModel model = queryRcvTrace(dto);
		if(model==null)
			return null;
		if(i>5){
			SysTradeExecuteException e2 = new SysTradeExecuteException(BocmTradeExecuteException.BOCM_E_10004,"核心记账失败");
			throw e2;
		}
		//如果核心记账状态为0，说明之前的流程没有执行完毕 ，等待2秒继续查询直到返回交易结果，如果超过10秒返回核心记账失败
		if(model.getHostState().equals("0")){
			try {
				myLog.info(logger, "等待2秒");	
				Thread.sleep(2000);
				return checkBocmRcvTrace(dto,i+1);
			} catch (InterruptedException e) {
				SysTradeExecuteException e1 = new SysTradeExecuteException(BocmTradeExecuteException.BOCM_E_10004);
				throw e1;
			}
		}else{
			return model;
		}		
	}	
	


	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {		
		
		ModelBase model = null;
		MyLog myLog = logPool.get();
		myLog.info(logger, TRADE_DESC);		
		
		//风险检查
		riskCheck(myLog, dto, payerAcno, payeeAcno, amt,bizChnl,bizCode);
		
		//磁条卡二磁道校验
		try {
			myLog.info(logger, "磁条卡二磁道校验");	
			validateMag(dto);
		} catch (SysTradeExecuteException e) {
			myLog.info(logger, "磁条卡状态异常,磁条卡二磁道校验失败",e);	
			throw cardMagValidateException;
		}
		
//		IC卡校验
		try {
			myLog.info(logger, "IC卡55域校验");	
			validateIC(dto);
		} catch (SysTradeExecuteException e) {
			myLog.info(logger, "IC卡状态异常,IC卡55域校验失败");	
			throw cardIcValidateException;
		}
		
		//递归调用来账查询
		BocmRcvTraceQueryModel revModel = checkBocmRcvTrace(dto,1);	
		
		//判断交易流水是否存在
		if(revModel!=null){
			myLog.info(logger, TRADE_DESC+",流水已存在重发交易");	
			if("1".equals(revModel.getHostState())){									
				//记账成功，通过model组装返回报文		
				return backMsgOnTradeHave(dto, revModel);
			}else if("3".equals(revModel.getHostState())){
				//超时状态确认记账状态查询
				//核心记账
				dto.setSysDate(revModel.getPlatDate());
				dto.setSysTime(revModel.getPlatTime());
				dto.setSysTraceno(revModel.getPlatTrace());
				//调用核心接口确认该笔流水是否入账成功，若登记成功直接反馈，若三次查询都失败则调用核心接口重新登记
				//调用核心接口确认该笔流水是否入账成功
				myLog.info(logger, TRADE_DESC+",核心记账超时,查询交易是否存在");	
				try {
					//核心记账结果查询
					model = hostTranResult(dto);
					if(model!=null){
						ESB_REP_30043000101 esbRep_30043000101 = (ESB_REP_30043000101)model;
						myLog.info(logger,"记账结果查询："+esbRep_30043000101.getRepBody().getAcctResult());
						if(esbRep_30043000101.getRepBody().getAcctResult().equals("00")) {
							//如果查询到结果，返回交易信息
							updateTimeoutSuccess(dto,esbRep_30043000101);	
							//状态通知 
							statusNotify(myLog, dto, payerAcno, payeeAcno, amt,bizChnl,bizCode,"01","00");
							return backMsgOnTradeHave(dto, revModel);
						}
					}
				}catch (Exception e) {
					myLog.error(logger,TRADE_DESC+"记账结果查询失败，返回超时，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e);
					//状态通知 
					statusNotify(myLog, dto, payerAcno, payeeAcno, amt,bizChnl,bizCode,"02","01");
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10004);
					throw e2;
				}	
				try {
					myLog.info(logger,TRADE_DESC+"核心未查询到交易记录，重新发起记账交易，渠道日期【"+dto.getSysDate()+"】渠道流水号【"+dto.getSysTraceno()+"】");
					//交易流水核心未记录重新发起核心记账
					model = hostCharge(dto);
				} catch (SysTradeExecuteException e) {
					myLog.error(logger,TRADE_DESC+"核心记账失败，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e);
					String errMsg = e.getRspMsg();
					try {
						if(errMsg.getBytes(ServerInitializer.CODING).length>30){
							errMsg = getErrorMsg(errMsg);
							//状态通知 
							statusNotify(myLog, dto, payerAcno, payeeAcno, amt,bizChnl,bizCode,"02","01");
							BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10004,errMsg);
							throw e2;
						}else{
							//状态通知 
							statusNotify(myLog, dto, payerAcno, payeeAcno, amt,bizChnl,bizCode,"02","01");
							BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10004,e.getRspMsg());
							throw e2;
						}
					} catch (UnsupportedEncodingException e1) {
						//状态通知 
						statusNotify(myLog, dto, payerAcno, payeeAcno, amt,bizChnl,bizCode,"02","01");
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10004);
						throw e2;
					}
				}
				updateOthSuccess(dto, model);
				//状态通知 
				statusNotify(myLog, dto, payerAcno, payeeAcno, amt,bizChnl,bizCode,"01","00");
				return backMsg(dto,model);
			}
		}
		
		try {
			//核心记账
			myLog.info(logger,TRADE_DESC+"核心记账");
			model = hostCharge(dto);
			myLog.info(logger,TRADE_DESC+"核心记账成功，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno());
		} catch (SysTradeExecuteException e) {
			// 超时
			if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000004) || e.getRspCode().equals(ESB_TIMEOUT_CODE1)) {
				// 超时登记
				hostTimeoutInitLog(dto); 
				try {
					// 核心冲正
					hostReversal(dto);
				} catch (SysTradeExecuteException e1) {
					//状态通知 
					statusNotify(myLog, dto, payerAcno, payeeAcno, amt,bizChnl,bizCode,"02","01");
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10004);
					throw e2;
				}
				myLog.error(logger,TRADE_DESC+"核心记账超时，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e);
				//状态通知 
				statusNotify(myLog, dto, payerAcno, payeeAcno, amt,bizChnl,bizCode,"02","01");
				throw hostTimeoutException;
			} else {
				myLog.error(logger,TRADE_DESC+"核心记账失败，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e);
				String errMsg = e.getRspMsg();
				try {
					if(errMsg.getBytes(ServerInitializer.CODING).length>30){
						errMsg = getErrorMsg(errMsg);
						//状态通知 
						statusNotify(myLog, dto, payerAcno, payeeAcno, amt,bizChnl,bizCode,"02","01");
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10004,errMsg);
						throw e2;
					}else{
						//状态通知 
						statusNotify(myLog, dto, payerAcno, payeeAcno, amt,bizChnl,bizCode,"02","01");
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10004,e.getRspMsg());
						throw e2;
					}
				} catch (UnsupportedEncodingException e1) {
					//状态通知 
					statusNotify(myLog, dto, payerAcno, payeeAcno, amt,bizChnl,bizCode,"02","01");
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10004);
					throw e2;
				}
				
			}
		}
		// 主机成功登记
		hostSuccessInitLog(dto, model); 
		//状态通知 
		statusNotify(myLog, dto, payerAcno, payeeAcno, amt,bizChnl,bizCode,"01","00");
		return backMsg(dto,model);
	}
	
	
	private String getErrorMsg(String msg){
		StringBuffer errMsg = new StringBuffer();
		int length = 0;
		try {
			for(int i=0;i<msg.length();i++){
				   String s = msg.substring(i, i+1);
				   int size = s.getBytes(ServerInitializer.CODING).length;
				   length = length + size;
				   errMsg.append(s);
				   if(length>=29){
					   return errMsg.toString();
				   }else{
					   continue;
				   }			   
			   }
		} catch (UnsupportedEncodingException e) {
			return "";
		}
		return errMsg.toString();
	}
	
	//风险检查
	public void riskCheck(MyLog myLog,DataTransObject dto,String payerAcno,String payeeAcno,Long amt,
			String bizChnl,String bizCode) throws SysTradeExecuteException{		
		/*
		// 增加风险监控 检查20190809 begin
		REQ_FRMS frmsModel = new REQ_FRMS(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());
		frmsModel.setSerialId(dto.getSysDate()+String.valueOf(dto.getSysTraceno()));
		//业务渠道    String  O-柜面通
		frmsModel.setBizChannel(bizChnl);
		frmsModel.setBizCode(bizCode);
		frmsModel.setOperTime(String.valueOf(new Date().getTime()));
		frmsModel.setOperAmount(amt);
		//如果业务类型为F01跨行转账赋值转账对手和应答码
		//他代本取款
		if(bizCode.equals("F01")){
			if(payeeAcno.equals("")){
				return ;
			}
			frmsModel.setCardNo(payerAcno);
			frmsModel.setRecAcct(payeeAcno);
		}
		//O04为跨行转入,cardNo为本行卡号
		//他代本存款
		if(bizCode.equals("O04")){
			frmsModel.setCardNo(payeeAcno);		
		}
		frmsModel.setWhoReport("01");
		REP_FRMS frmsRep = forwardToFRMSService.sendToFRMS(frmsModel, REP_FRMS.class);
		if(frmsRep.getVerifyPolicy()!=null){
			myLog.info(logger, "风险监控应答  Code："+frmsRep.getVerifyPolicy().getCode()+"  Name:"+frmsRep.getVerifyPolicy().getName());
		}
		if (frmsRep.getVerifyPolicy()!=null&&BLOCK.equalsIgnoreCase(frmsRep.getVerifyPolicy().getCode())) {
			throw new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10019);
		}
		// 增加风险监控 检查20190809 end		
		 */
	}
	
	//状态通知
	public void statusNotify(MyLog myLog,DataTransObject dto,String payerAcno,String payeeAcno,Long amt
			,String bizChnl,String bizCode,String operStatus,String respCode) throws SysTradeExecuteException{
		/*
		// 增加风险监控 检查20190809 begin
		REQ_FRMS frmsModel = new REQ_FRMS(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());
		frmsModel.setSerialId(dto.getSysDate()+String.valueOf(dto.getSysTraceno()));
		frmsModel.setBizChannel(bizChnl);
		frmsModel.setBizCode(bizCode);
		frmsModel.setOperTime(String.valueOf(new Date().getTime()));
		frmsModel.setOperAmount(1L);
		frmsModel.setCardNo(payerAcno);
		frmsModel.setWhoReport("01");
		frmsModel.setOperStatus(operStatus);
		//如果业务类型为F01跨行转账赋值转账对手和应答码
		if(bizCode.equals("F01")){
			if(payeeAcno.equals("")){
				return ;
			}
			frmsModel.setRespCode(respCode);
			frmsModel.setCardNo(payerAcno);
			frmsModel.setRecAcct(payeeAcno);
		}
		//O04为跨行转入,cardNo为本行卡号
		//他代本存款
		if(bizCode.equals("O04")){
			frmsModel.setCardNo(payeeAcno);			
		}
		REP_FRMS frmsRep = forwardToFRMSService.sendToFRMS(frmsModel, REP_FRMS.class);
		if(frmsRep.getVerifyPolicy()!=null){
			myLog.info(logger, "风险监控应答  Code："+frmsRep.getVerifyPolicy().getCode()+"  Name:"+frmsRep.getVerifyPolicy().getName());
		}
		if (frmsRep.getVerifyPolicy()!=null&&BLOCK.equalsIgnoreCase(frmsRep.getVerifyPolicy().getCode())) {
			throw new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10019);
		}
		// 增加风险监控 检查20190809 end		
		 */
	}
}