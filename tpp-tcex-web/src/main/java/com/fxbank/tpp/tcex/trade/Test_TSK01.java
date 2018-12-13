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
import com.fxbank.tpp.tcex.dto.esb.REP_TEST_TSK01;
import com.fxbank.tpp.tcex.dto.esb.REQ_TEST_TSK01;
/**
 * 模拟反馈村镇账户信息
 * @author liye
 *
 */
@Service("REQ_TEST_TSK01")
public class Test_TSK01 extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(CityDeposit.class);

	@Resource
	private LogPool logPool;
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_TEST_TSK01 reqDto = (REQ_TEST_TSK01)dto;
		
		System.out.println("模拟反馈村镇账户信息,REQ_TEST_TSK01开始");
		System.out.println("接收报文："+JsonUtil.toJson(reqDto));
		
		REP_TEST_TSK01 repDto = new REP_TEST_TSK01();
		repDto.getRepBody().setPayerAcno("623166001015086827");
		repDto.getRepBody().setPayerName("张三");
		repDto.getRepBody().setAcnoSeq("1");
		repDto.getRepBody().setBal("50000");
		
		System.out.println("反馈报文："+JsonUtil.toJson(repDto));
		System.out.println("模拟反馈村镇账户信息,REQ_TEST_TSK01结束");
		return repDto;
	}

}
