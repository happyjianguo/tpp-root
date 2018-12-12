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
 * @ClassName: TSK01
 * @Description: 商行通兑快捷查询村镇账户信息
 * @author 
 * @date 2018年4月3日 下午3:46:30
 * 
 */
@Service("REQ_TSK01")
public class CityQueryAcctInfo extends TradeBase implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CityQueryAcctInfo.class);


	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		
		/**
		 * 
		REQ_TSK01 reqDto = (REQ_TSK01) dto;
		REQ_TSK01.REQ_BODY reqBody = reqDto.getReqBody();
		String payerAcno = reqBody.getPayerAcno();
		//请求村镇账户信息接口，反馈结果写入REP_TSK01
		REP_TSK01 repDto = new REP_TSK01();
		REP_TSK01.REP_BODY repBody = repDto.getRepBody();
		repBody.setPayerAcno(payerAcno);
		repBody.setPayerName("测试");
		repBody.setAcnoSeq("1");
		repBody.setBal("1000.00");
		
		**/
		
		//测试用
		REP_TSK01 repDto = tstInterface(dto);
		return repDto;
	}

	private REP_TSK01 tstInterface(DataTransObject dto) {
		REQ_TSK01 reqDto = (REQ_TSK01) dto;
		REQ_TSK01.REQ_BODY reqBody = reqDto.getReqBody();
		String payerAcno = reqBody.getPayerAcno();
		
		REP_TSK01 repDto = new REP_TSK01();
		REP_TSK01.REP_BODY repBody = repDto.getRepBody();
		repBody.setPayerAcno(payerAcno);
		repBody.setPayerName("测试");
		repBody.setAcnoSeq("1");
		repBody.setBal("1000.00");
		return repDto;
	}

	

}
