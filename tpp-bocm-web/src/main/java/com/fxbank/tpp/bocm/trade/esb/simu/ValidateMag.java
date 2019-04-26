/**   
* @Title: ValidateMag.java 
* @Package com.fxbank.tpp.bocm.trade.esb.simu 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年4月25日 下午2:43:10 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.trade.esb.simu;

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
import com.fxbank.tpp.bocm.dto.esb.REP_30033000203;
import com.fxbank.tpp.bocm.dto.esb.REQ_30033000203;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.bocm.trade.bocm.DP_FxICC;
import com.fxbank.tpp.esb.service.IForwardToESBService;

/** 
* @ClassName: ValidateMag 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author YePuLiang
* @date 2019年4月25日 下午2:43:10 
*  
*/
@Service("REQ_30033000203")
public class ValidateMag implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(DP_FxICC.class);

	@Resource
	private LogPool logPool;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "bocm.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		logger.info(" 磁条卡校验模拟验证 ");
		MyLog myLog = logPool.get();
		try {
			REQ_30033000203 req = (REQ_30033000203) dto;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		REP_30033000203 rep = new REP_30033000203();
		return rep;
	}
}
