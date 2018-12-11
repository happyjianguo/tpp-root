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
import com.fxbank.tpp.tcex.dto.esb.REP_TSK01;
import com.fxbank.tpp.tcex.dto.esb.REQ_TSK01;


/**
 * @ClassName: TS001
 * @Description: 商行通存村镇业务
 * @author 
 * @date 2018年4月3日 下午3:46:30
 * 
 */
@Service("REQ_TSK01")
public class CityQueryAcctInfo implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CityQueryAcctInfo.class);


	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_TSK01 reqDto = (REQ_TSK01) dto;
		REQ_TSK01.REQ_BODY reqBody = reqDto.getReqBody();
		REP_TSK01 repDto = new REP_TSK01();
		REP_TSK01.REP_BODY repBody = repDto.getRepBody();
		String payerAcno = reqBody.getPayerAcno();
		repBody.setPayerAcno(payerAcno);
		repBody.setPayerName("测试");
		repBody.setAcnoSeq("");
		repBody.setBal("1000.00");
		return repDto;
	}

	

}
