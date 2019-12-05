package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.tpp.esb.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.tcex.ESB_REP_TS0014;
import com.fxbank.tpp.esb.model.tcex.ESB_REQ_TS0014;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.esb.service.IForwardToTownService;
import com.fxbank.tpp.tcex.dto.esb.REP_TS0014;
import com.fxbank.tpp.tcex.dto.esb.REQ_TS0014;

/** 
* @ClassName: TS0014 
* @Description: 模拟村镇通兑冲正 
* @author Duzhenduo
* @date 2019年1月31日 上午10:06:53 
*  
*/
@Service("REQ_TS0014")
public class TS0014 implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CityDeposit.class);

	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IForwardToTownService forwardToTownService;
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		/**
		REP_TS004 repDto = new REP_TS004();
		repDto.getRepBody().setSts("1");
		**/
		REQ_TS0014 reqDto = (REQ_TS0014) dto;
		REP_TS0014 repDto = new REP_TS0014();
		ESB_REQ_TS0014 esbReq_TS004 = new ESB_REQ_TS0014(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		/** Add by 叶浦亮 At 2019/12/3 15:48 For 不同渠道平台调用核心接口使用不一样的systemID */
		ESB_REQ_SYS_HEAD reqTS004SysHead = new EsbReqHeaderBuilder(esbReq_TS004.getReqSysHead(), reqDto.getSourceType(),reqDto.getSysDate(),reqDto.getSysTime(),reqDto.getSysTraceno())
				.setBranchId(reqDto.getReqSysHead().getBranchId()).setUserId(reqDto.getReqSysHead().getUserId())
				.build();
		esbReq_TS004.setReqSysHead(reqTS004SysHead);
		ESB_REQ_TS0014.REQ_BODY esbReqBody_TS004 = esbReq_TS004.getReqBody();
		esbReqBody_TS004.setPlatDate(reqDto.getReqBody().getPlatDate());
		esbReqBody_TS004.setPlatTraceno(reqDto.getReqBody().getPlatTraceno());
		ESB_REP_TS0014 esbRep_TS004 = forwardToTownService.sendToTown(esbReq_TS004, esbReqBody_TS004,
				ESB_REP_TS0014.class);
		repDto.getRepBody().setSts(esbRep_TS004.getRepBody().getSts());
		return repDto;
	}
}
