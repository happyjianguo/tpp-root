package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.tcex.dto.esb.REP_TRK01;
import com.fxbank.tpp.tcex.dto.esb.REQ_TRK01;

/**
 * 村镇通兑快捷查询账户信息
 * @author liye
 *
 */
public class TownQueryAcctInfo extends TradeBase implements TradeExecutionStrategy{
	private static Logger logger = LoggerFactory.getLogger(CityQueryAcctInfo.class);


	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		
		REQ_TRK01 reqDto = (REQ_TRK01)dto;
		String payerAcno = reqDto.getReqBody().getPayerAcno();
		//请求核心获取账户信息
		
		REP_TRK01 repDto = new REP_TRK01();
		
		return repDto;
	}
	
	
}