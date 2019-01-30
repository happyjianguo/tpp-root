package com.fxbank.tpp.tcex.trade;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
import com.fxbank.tpp.tcex.dto.esb.REP_TS002;

/** 
* @ClassName: TS0012 
* @Description: 模拟商行通兑村镇
* @author Duzhenduo
* @date 2019年1月30日 下午2:14:36 
*  
*/
@Service("REQ_TS0012")
public class TS0012 implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CityDeposit.class);

	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	static SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REP_TS002 repDto = new REP_TS002();
		repDto.getRepBody().setBrno("10001");
		repDto.getRepBody().setTownDate(sdf1.format(new Date()));
		repDto.getRepBody().setTownTraceno(UUID.randomUUID().toString().replace("-", "").substring(0, 15));
		repDto.getRepBody().setBackTal("10000.00");
		return repDto;
	}
}
