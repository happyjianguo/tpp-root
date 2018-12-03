package com.fxbank.cap.paf.TEST;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fxbank.cap.paf.dto.SER_REP_BDC;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;

@Service("BDC002")
public class TEST_logout implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(TEST_logout.class);
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		SER_REP_BDC repDto = new SER_REP_BDC();
		return repDto;
	}
	
}
