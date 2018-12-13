package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.tpp.tcex.dto.esb.REP_TEST_TCHK01;
import com.fxbank.tpp.tcex.dto.esb.REP_TEST_TSK01;
import com.fxbank.tpp.tcex.dto.esb.REQ_TEST_TCHK01;
import com.fxbank.tpp.tcex.dto.esb.REQ_TEST_TSK01;
/**
 * 模拟商行村镇柜面通对账
 * @author liye
 *
 */
@Service("REQ_TEST_TCHK01")
public class Test_TCHK01 extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(CityDeposit.class);

	@Resource
	private LogPool logPool;
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_TEST_TCHK01 reqDto = (REQ_TEST_TCHK01)dto;
		
		System.out.println("模拟商行村镇柜面通对账,REQ_TEST_TCHK01开始");
		System.out.println("接收报文："+JsonUtil.toJson(reqDto));
		
		REP_TEST_TCHK01 repDto = new REP_TEST_TCHK01();
//		repDto.getRepSysHead().getRet().get(0).setRetCode("100000");
//		repDto.getRepSysHead().getRet().get(0).setRetMsg("对账失败");
		
		System.out.println("反馈报文："+JsonUtil.toJson(repDto));
		System.out.println("模拟商行村镇柜面通对账,REQ_TEST_TCHK01结束");
		return repDto;
	}

}
