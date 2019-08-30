package com.fxbank.tpp.bocm.trade.esb;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.REQ_BASE;
import com.fxbank.tpp.bocm.service.IBocmSafeService;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043003001;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30043003001;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.frms.model.REP_FRMS;
import com.fxbank.tpp.frms.model.REQ_FRMS;
import com.fxbank.tpp.frms.service.IForwardToFRMSService;

/**
 * @Description: 交易程序基础公共类
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:15:15
 */
@Component
public class TradeBase {
	private static Logger logger = LoggerFactory.getLogger(TradeBase.class);
	
	@Reference(version = "1.0.0")
    private IBocmSafeService safeService;
	
	@Reference(version = "1.0.0")
	private IForwardToFRMSService forwardToFRMSService;
	
	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	private static final String BLOCK = "BLOCK";

	public void setBankno(MyLog myLog, DataTransObject dto, String branchId, REQ_BASE reqBase) throws SysTradeExecuteException {
		if(branchId == null){
			myLog.error(logger, "发起机构号不能为空");
			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
			myLog.error(logger,e.getRspCode() + " | " + e.getRspMsg());
			throw e;
		}		
		//行号查询
		ESB_REQ_30043003001 esbReq_30043003001 = new ESB_REQ_30043003001(myLog, dto.getSysDate(), dto.getSysTime(),
				dto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30043003001.getReqSysHead(), dto).build();
		reqSysHead.setBranchId(branchId);
		esbReq_30043003001.setReqSysHead(reqSysHead);
		ESB_REQ_30043003001.REQ_BODY reqBody_30043003001 = esbReq_30043003001.getReqBody();
		reqBody_30043003001.setBrchNoT4(branchId);

		myLog.info(logger, "TradeBase通过本行机构号查询人行行号");
		ESB_REP_30043003001 esbRep_30043003001 = forwardToESBService.sendToESB(esbReq_30043003001, reqBody_30043003001,				
				ESB_REP_30043003001.class);	
		//发起行人行行号
		String BANK_NUMBER = esbRep_30043003001.getRepBody().getBankNumber();
		//人行清算行号
		String SETTLEMENT_BANK_NO = esbRep_30043003001.getRepBody().getSettlementBankNo();
		// SBnkNo指合作银行总行行号（12位） RBnkNo指合作银行发起交易网点行号
		// 交通银行发起交易时，SBnkNo指交行发起交易网点号（12位）     RBnkNo指合作银行总行行号
		//注意：合作银行发起交易时，SBnkNo指合作银行总行行号（12位）
        //                 RBnkNo指合作银行发起交易网点行号
		//BANK_NUMBER	行号
		//BNK_NM_T	行名
		//SETTLEMENT_BANK_NO	清算行号
		//LQTN_BNK_NM_T1	清算行名
		//发起行行号
		reqBase.setSbnkNo(SETTLEMENT_BANK_NO); // 总行 取上面接口返回值
		//接收行行号 办理业务网点的人行行号
		reqBase.setRbnkNo(BANK_NUMBER); // 网点 取上面接口返回值
	}

	/**
	 * 
	* @Title: convPin 
	* @Description: 密码Pin转加密
	* @param @param dto
	* @param @param acctNo
	* @param @param oPin
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String convPin(DataTransObject dto, String acctNo, String oPin) throws SysTradeExecuteException{
		//Pin值转加密
		String pin = safeService.transPinToJH(logPool.get(), acctNo, acctNo, oPin);
		return pin;

	}
	//风险检查
	public void riskCheck(MyLog myLog,DataTransObject dto,String payerAcno,String payeeAcno,Long amt,
			String bizChnl,String bizCode) throws SysTradeExecuteException{
		// 增加风险监控 检查20190809 begin
		REQ_FRMS frmsModel = new REQ_FRMS(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());
		//业务渠道    String  O-柜面通
		frmsModel.setBizChannel(bizChnl);
		frmsModel.setBizCode(bizCode);
		frmsModel.setOperTime(String.valueOf(new Date().getTime()));
		frmsModel.setOperAmount(amt);
		frmsModel.setCardNo(payerAcno);
		//如果业务类型为F01跨行转账赋值转账对手和应答码
		if(bizCode.equals("F01")){
			frmsModel.setRecAcct(payeeAcno);
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
	}
	//状态通知
	public void statusNotify(MyLog myLog,DataTransObject dto,String payerAcno,String payeeAcno,Long amt
			,String bizChnl,String bizCode,String operStatus,String respCode) throws SysTradeExecuteException{
		// 增加风险监控 检查20190809 begin
		REQ_FRMS frmsModel = new REQ_FRMS(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());
		frmsModel.setSerialId(dto.getSysDate()+String.valueOf(dto.getSysTraceno()));
		frmsModel.setBizChannel(bizChnl);
		frmsModel.setBizCode(bizCode);
		frmsModel.setOperTime(String.valueOf(new Date().getTime()));
		frmsModel.setOperAmount(amt);
		frmsModel.setCardNo(payerAcno);		
		frmsModel.setWhoReport("01");
		frmsModel.setOperStatus(operStatus);
		//如果业务类型为F01跨行转账赋值转账对手和应答码
		if(bizCode.equals("F01")){
			frmsModel.setRespCode(respCode);
			frmsModel.setRecAcct(payeeAcno);
		}
		REP_FRMS frmsRep = null;
		try {
			frmsRep = forwardToFRMSService.sendToFRMS(frmsModel, REP_FRMS.class);
		} catch (Exception e) {
			//避免短款暂时通知异常记录日志，不抛异常
			myLog.info(logger, "风险监控状态通知请求失败,渠道日期"+dto.getSysDate()+"  渠道流水："+dto.getSysTraceno());
			return;
		}
		if(frmsRep.getVerifyPolicy()!=null){
			myLog.info(logger, "风险监控应答  Code："+frmsRep.getVerifyPolicy().getCode()+"  Name:"+frmsRep.getVerifyPolicy().getName());
		}
		
	}
}
