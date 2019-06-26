package com.fxbank.tpp.bocm.trade.bocm;

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
import com.fxbank.cip.pub.service.IPublicService;
import com.fxbank.tpp.bocm.dto.bocm.REP_10104;
import com.fxbank.tpp.bocm.dto.bocm.REQ_10104;
import com.fxbank.tpp.bocm.service.IBocmSafeService;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;
import com.fxbank.tpp.esb.service.IForwardToESBService;

/** 
* @ClassName: QR_Key 
* @Description: 模拟工作秘钥申请
* @author YePuLiang
* @date 2019年5月23日 下午3:07:03 
*  
*/
@Service("REQ_10104")
public class QR_Key implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(QR_Key.class);
	
	@Reference(version = "1.0.0")
	private IBocmSafeService safeService;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "bocm.";
	
	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToBocmService forwardToBocmService;
	
	@Reference(version = "1.0.0")
	private IPublicService publicService;
	
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_10104 reqDto = (REQ_10104) dto;
		REP_10104 rep = new REP_10104();
		rep.setBlkVal("密文值");
		rep.setChkVal("密钥 校验值");
		myLog.info(logger, "密钥更新");		
		return rep;
	}

}
