package com.fxbank.tpp.mivs.trade.esb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043003001;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30043003001;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TradeBase {
	private static Logger logger = LoggerFactory.getLogger(TradeBase.class);

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	/** 
	* @Title: queryBankno 
	* @Description: 通过机构号查询渠道接口获取（机构号查行号） 
	* @param @param myLog
	* @param @param dto
	* @param @param branchId 机构号
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30043003001    返回类型 
	* @throws 
	*/
	public ESB_REP_30043003001 queryBankno(MyLog myLog, DataTransObject dto, String branchId) throws SysTradeExecuteException {
		if(branchId == null){
			myLog.error(logger, "发起机构号不能为空");
			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
			myLog.error(logger,e.getRspCode() + " | " + e.getRspMsg());
			throw e;
		}
    	ESB_REQ_30043003001 esbReq_30043003001 = new ESB_REQ_30043003001(myLog, dto.getSysDate(), dto.getSysTime(),
				dto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30043003001.getReqSysHead(), dto).build();
		reqSysHead.setBranchId(branchId);
		esbReq_30043003001.setReqSysHead(reqSysHead);
		ESB_REQ_30043003001.REQ_BODY reqBody_30043003001 = esbReq_30043003001.getReqBody();
		reqBody_30043003001.setBrchNoT4(branchId);
		ESB_REP_30043003001 esbRep_30043003001 = forwardToESBService.sendToESB(esbReq_30043003001, reqBody_30043003001,
				ESB_REP_30043003001.class);
		return esbRep_30043003001;
	}
	/*
	public String convPin(String oPin){
		String nPin = oPin;
		//TODO
		return nPin;
	}
	*/
}
