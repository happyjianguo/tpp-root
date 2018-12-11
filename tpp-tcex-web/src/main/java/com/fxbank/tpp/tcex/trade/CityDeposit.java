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
import com.fxbank.tpp.tcex.dto.esb.REP_TS001;
import com.fxbank.tpp.tcex.dto.esb.REQ_TS001;
/**
 * 商行通存业务
 * @author liye
 *
 */
@Service("REQ_TS001")
public class CityDeposit implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(CityDeposit.class);


	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		String lsh = ""; //渠道流水号
		
		MyLog myLog = logPool.get();
		REQ_TS001 reqDto = (REQ_TS001) dto;
		REQ_TS001.REQ_BODY reqBody = reqDto.getReqBody();
		REP_TS001 repDto = new REP_TS001();
		REP_TS001.REP_BODY repBody = repDto.getRepBody();
		
		//插入流水表
		boolean b = true;
		
		if(b) {
			//核心记账：由商行核心将客户存入金额转至头寸。
			
			
			//更新流水表核心记账状态
			b=true;
			
			if(b) {
				//通知村镇进行记账
				String rst = "";
				
				//更新流水表村镇记账状态
				
				
				if(rst.equals("success")) {
					//结束
				}else if(rst.equalsIgnoreCase("timeout")) {
					//发送存款确认
					
					//更新流水表村镇存款确认状态
				}else {
					//核心撤销：冲正

					//更新流水表核心记账冲正状态
				}
				
			}else {
				throw new SysTradeExecuteException("1111");
			}
			
		}else {
			throw new SysTradeExecuteException("1111");
		}
		
		
		
		return null;
	}

}
