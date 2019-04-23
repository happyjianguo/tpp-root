package com.fxbank.tpp.mivs.trade.esb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TradeBase {
	private static Logger logger = LoggerFactory.getLogger(TradeBase.class);

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	/*
	public void setBankno(MyLog myLog, DataTransObject dto, String branchId, REQ_HEADER reqHeader) throws SysTradeExecuteException {
		if(branchId == null){
			myLog.error(logger, "发起机构号不能为空");
			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
			myLog.error(logger,e.getRspCode() + " | " + e.getRspMsg());
			throw e;
		}
				*
		ESB_REQ_30013000201 esbReq_30013000201 = new ESB_REQ_30013000201(myLog, dto.getSysDate(), dto.getSysTime(),
				dto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30013000201.getReqSysHead(), dto).build();
		esbReq_30013000201.setReqSysHead(reqSysHead);
		ESB_REQ_30013000201.REQ_BODY reqBody_30013000201 = esbReq_30013000201.getReqBody();
		reqBody_30013000201.setBaseAcctNo("");
		reqBody_30013000201.setCcy("CNY");

		myLog.info(logger, "通过本行机构号查询人行行号");
		ESB_REP_30013000201 esbRep_30013000201 = forwardToESBService.sendToESB(esbReq_30013000201, reqBody_30013000201,
				ESB_REP_30013000201.class);
		*
		reqHeader.setsBnkNo("313131000008"); // 总行 取上面接口返回值
		reqHeader.setrBnkNo("313131000007"); // 网点 取上面接口返回值
	}

	public String convPin(String oPin){
		String nPin = oPin;
		//TODO
		return nPin;
	}
	*/
}
