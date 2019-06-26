package com.fxbank.tpp.bocm.trade.bocm;

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
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043000101;

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
	* @Fields hostTimeoutException : 核心记账超时，提示处理失败
	*/ 
	public SysTradeExecuteException acctStatusException = null;
	
	/** 
	* @Fields hostTimeoutException : 磁条卡验证
	*/ 
	public SysTradeExecuteException cardValidateException = null;

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
	* @Fields ESB_TIMEOUT_CODE2 :  核心超时ESB响应码
	*/ 
	private static final String ESB_TIMEOUT_CODE2 = "ES000033";
	
	/** 
	* @Fields othTimeoutQuery : 第三方记账超时，是否发起查询该笔交易 
	*/ 
	public Boolean othTimeoutQuery = false;
	
	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IBocmRcvTraceService bocmRcvTraceService;

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
		
	
	private BocmRcvTraceQueryModel checkBocmRcvTrace(DataTransObject dto,int i) throws SysTradeExecuteException{
		MyLog myLog = logPool.get();
		BocmRcvTraceQueryModel model = queryRcvTrace(dto);
		if(model==null)
			return null;
		if(i>5){
			SysTradeExecuteException e2 = new SysTradeExecuteException(BocmTradeExecuteException.BOCM_E_10004,"核心记账失败");
			throw e2;
		}
		if(model.getHostState().equals("0")){
			try {
				myLog.info(logger, "等待2秒");	
				Thread.sleep(2000);
				return checkBocmRcvTrace(dto,i+1);
			} catch (InterruptedException e) {
				SysTradeExecuteException e1 = new SysTradeExecuteException(BocmTradeExecuteException.BOCM_E_10004,model.getRetMsg());
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
		
//		//磁条卡二磁道校验
//		try {
//			myLog.info(logger, "磁条卡二磁道校验");	
//			validateMag(dto);
//		} catch (SysTradeExecuteException e) {
//			myLog.info(logger, "磁条卡状态异常,磁条卡二磁道校验失败");	
//			throw cardValidateException;
//		}
//		
//		//IC卡校验
//		try {
//			myLog.info(logger, "IC卡55域校验");	
//			validateIC(dto);
//		} catch (SysTradeExecuteException e) {
//			myLog.info(logger, "磁条卡状态异常,磁条卡二磁道校验失败");	
//			throw cardValidateException;
//		}
		
		//递归调用来账查询
		BocmRcvTraceQueryModel revModel = checkBocmRcvTrace(dto,1);	
		String timeoutFlag="0";
		
		//判断交易流水是否存在
		if(revModel!=null){
			myLog.info(logger, TRADE_DESC+",流水已存在重发交易");	
			if("1".equals(revModel.getHostState())){									
				//记账成功，通过model组装返回报文		
				return backMsgOnTradeHave(dto, revModel);
			}else if("3".equals(revModel.getHostState())){
				timeoutFlag="1";
				//超时状态确认记账状态查询
				//核心记账
				String hostDate=null,hostTraceno=null,retCode=null,retMsg=null,hostBranch=null;
				int i=0;
				dto.setSysDate(revModel.getPlatDate());
				dto.setSysTime(revModel.getPlatTime());
				dto.setSysTraceno(revModel.getPlatTrace());
				//调用核心接口确认该笔流水是否入账成功，若登记成功直接反馈，若三次查询都失败则调用核心接口重新登记
				for(;i<3;i++) {
					//调用核心接口确认该笔流水是否入账成功
					myLog.info(logger, TRADE_DESC+",核心记账超时,查询交易是否存在");	
					try {
						//核心记账结果查询
						model = hostTranResult(dto);							
						ESB_REP_30043000101 esbRep_30043000101 = (ESB_REP_30043000101)model;
						myLog.info(logger,"记账结果查询："+esbRep_30043000101.getRepBody().getAcctResult());
						retCode = esbRep_30043000101.getRepSysHead().getRet().get(0).getRetCode();
						retMsg = esbRep_30043000101.getRepSysHead().getRet().get(0).getRetMsg();
						if(esbRep_30043000101.getRepBody().getAcctResult().equals("00")) {
							hostDate = esbRep_30043000101.getRepBody().getTranDate();
							hostTraceno = esbRep_30043000101.getRepBody().getReference();
							//如果查询到结果，返回交易信息
							return backMsgOnTradeHave(dto, revModel);
						}
					}catch (Exception e) {
						retCode="error";
						myLog.error(logger,TRADE_DESC+"记账结果查询失败，返回超时，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e);
						throw hostTimeoutException;
					}				
				}
				try {
					myLog.info(logger,TRADE_DESC+"核心未查询到交易记录，重新发起记账交易，渠道日期【"+dto.getSysDate()+"】渠道流水号【"+dto.getSysTraceno()+"】");
					//交易流水核心未记录重新发起核心记账
					model = hostCharge(dto);
				} catch (SysTradeExecuteException e) {
					myLog.error(logger,TRADE_DESC+"重发交易核心记账失败，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e);
					throw hostErrorException;
				}
				updateOthSuccess(dto, model);
				return backMsg(dto,model);
			}
		}
		
		try {
			//核心记账
			model = hostCharge(dto);
			myLog.info(logger,TRADE_DESC+"核心记账成功，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno());
		} catch (SysTradeExecuteException e) {
			// 超时
			if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000004) || e.getRspCode().equals(ESB_TIMEOUT_CODE1)
					|| e.getRspCode().equals(ESB_TIMEOUT_CODE2)) {
				// 超时登记
				hostTimeoutInitLog(dto); 
				myLog.error(logger,TRADE_DESC+"核心记账超时，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e);
				throw hostTimeoutException;
			} else {
				if(e.getRspCode().equals("RB4005")){
					throw acctStatusException;
				}
				myLog.error(logger,TRADE_DESC+"核心记账失败，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e);
				throw hostErrorException;
			}
		}
		// 主机成功登记
		hostSuccessInitLog(dto, model); 				
		return backMsg(dto,model);
	}
}