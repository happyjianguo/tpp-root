package com.fxbank.cap.paf.trade.esb;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fxbank.cap.paf.dto.esb.REP_30012002001;
import com.fxbank.cap.paf.dto.esb.REQ_30012002001;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;

/**
 * @ClassName: AgreementSign
 * @Description: 无卡协议签约
 * @author ZhouYongwei
 * @date 2018年4月3日 下午3:46:30
 * 
 */
@Service("REQ_30012002001")
public class AgreementSign implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(AgreementSign.class);

	@Resource
	private LogPool logPool;
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30012002001 reqDto = (REQ_30012002001) dto;
		REQ_30012002001.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30012002001 repDto = new REP_30012002001();
		REP_30012002001.REP_BODY repBody = repDto.getRepBody();
		
		
		return repDto;
	}


}
