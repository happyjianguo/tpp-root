package com.fxbank.cap.paf.TEST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fxbank.cap.paf.dto.SER_REP_BDC;
import com.fxbank.cap.paf.model.FIELD;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;

@Service("BDC001")
public class TEST_login implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(TEST_login.class);

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		SER_REP_BDC repDto = new SER_REP_BDC();
		repDto.getBody().getField().add(new FIELD("HandKey",
				"28ea8b88a3c40383b8d506513c537eb08be88548cd1652eb503931890290ea478f58b5cf068053b70a6604bfb9fc0334c03700a4201fc3fcb1efbde377024391628f57934591b8e3c1a259c4078422c24154bf70ae1e45daa4df807e5408fd6f8f292d55bbc300be38773b3120bfa31fb087452595269215669f91e94ffdf54c"));
		return repDto;
	}

}
