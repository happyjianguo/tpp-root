package com.fxbank.tpp.bocm.trade.esb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.tpp.bocm.model.REQ_HEADER;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043003001;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30043003001;
import com.fxbank.tpp.esb.service.IForwardToESBService;

/**
 * @Description: 交易程序基础公共类
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:15:15
 */
@Component
public class TradeBase {
	private static Logger logger = LoggerFactory.getLogger(TradeBase.class);

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	public void setBankno(MyLog myLog, DataTransObject dto, String branchId, REQ_HEADER reqHeader) throws SysTradeExecuteException {
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
		myLog.info(logger, "通过本行机构号查询人行行号");
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
		//发起行行号
		reqHeader.setsBnkNo(SETTLEMENT_BANK_NO); // 总行 取上面接口返回值
		//接收行行号 办理业务网点的总行行号
		reqHeader.setrBnkNo(BANK_NUMBER); // 网点 取上面接口返回值
		
		
		
//		//发起行行号  313229000660
//		reqHeader.setsBnkNo("313229000660"); // 总行 取上面接口返回值
//		//接收行行号 办理业务网点的总行行号
//		reqHeader.setrBnkNo("313229000660"); // 网点 取上面接口返回值
	}

	public String convPin(String oPin){
		String nPin = oPin;
		//TODO
		return nPin;
	}
}
