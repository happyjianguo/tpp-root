package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.tcex.dto.esb.REP_KEY01;
import com.fxbank.tpp.tcex.dto.esb.REQ_KEY01;

/**
 * 工作密钥更新申请
 * @author liye
 *
 */
@Service("REQ_KEY01")
public class TownQueryKey extends TradeBase implements TradeExecutionStrategy{
	private static Logger logger = LoggerFactory.getLogger(CityQueryAcctInfo.class);


	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		
		REQ_KEY01 reqDto = (REQ_KEY01)dto;
		String keyModel = reqDto.getReqBody().getKeyModel();
		//请求核心获取账户信息
		
		REP_KEY01 repDto = new REP_KEY01();
		repDto.getRepBody().setKeyValue("1");
		repDto.getRepBody().setChkKeyValue("2");
		
		return repDto;
	}
	
	
}
