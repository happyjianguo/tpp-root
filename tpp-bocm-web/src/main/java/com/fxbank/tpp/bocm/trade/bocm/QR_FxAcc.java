/**   
* @Title: QR_FxAcc.java 
* @Package com.fxbank.tpp.bocm.trade.bocm 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月6日 上午10:54:43 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.trade.bocm;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.bocm.dto.bocm.REP_20102;
import com.fxbank.tpp.bocm.dto.bocm.REQ_20102;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.esb.service.IForwardToESBService;

/** 
* @ClassName: QR_FxAcc 
* @Description: 账户信息查询
* @author YePuLiang
* @date 2019年5月6日 上午10:54:43 
*  
*/
@Service("REQ_20102")
public class QR_FxAcc implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(DP_FxICC.class);
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IBocmRcvTraceService bocmRcvTraceService;
	

	@Resource
	private LogPool logPool;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "bocm.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_20102 req = (REQ_20102) dto;
		REP_20102 rep = new REP_20102();
		rep.setActNo("6288880210000209903");
		rep.setActBnk("623166000009");
		rep.setActNam("ZZZ");
		rep.setActTyp("5");
		rep.setAmtLmt("0");
		
//		logger.info(rep.creaFixPack());
		
		return rep;
	}

}
