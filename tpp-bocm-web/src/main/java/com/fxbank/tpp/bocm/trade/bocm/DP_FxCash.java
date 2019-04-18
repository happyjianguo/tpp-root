package com.fxbank.tpp.bocm.trade.bocm;

import java.math.BigDecimal;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.bocm.dto.bocm.REP_10000;
import com.fxbank.tpp.bocm.dto.bocm.REQ_10000;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** 
* @ClassName: DP_FxCash 
* @Description: 交行向本行发起磁条卡通存记账请求
* @author Duzhenduo
* @date 2019年4月17日 下午4:22:56 
*  
*/
@Service("REQ_10000")
public class DP_FxCash implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(DP_FxCash.class);

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Resource
	private LogPool logPool;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_10000 req = (REQ_10000) dto;
		//调用ESB余额查询
		REP_10000 rep = new REP_10000();
		rep.setoTxnAmt(new BigDecimal(1000));
		rep.setFee(new BigDecimal(10));
		rep.setActBal(new BigDecimal(2000));
		return rep;
	}
}