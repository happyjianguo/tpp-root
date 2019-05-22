/**   
* @Title: HostCharge.java 
* @Package com.fxbank.tpp.bocm.trade.esb.simu 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年4月25日 上午10:31:57 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.trade.esb.simu;

import java.util.ArrayList;
import java.util.List;

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
import com.fxbank.tpp.bocm.dto.esb.REP_30011000104;
import com.fxbank.tpp.bocm.dto.esb.REQ_30011000104;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.bocm.trade.bocm.DP_FxICC;
import com.fxbank.tpp.esb.service.IForwardToESBService;

/** 
* @ClassName: HostCharge 
* @Description: 模拟核心记账
* @author YePuLiang
* @date 2019年4月25日 上午10:31:57 
*  
*/
@Service("REQ_30011000104")
public class HostCharge implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(HostCharge.class);

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
		logger.info(" 模拟核心记账 ");
		MyLog myLog = logPool.get();
		try {
			REQ_30011000104 req = (REQ_30011000104) dto;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		REP_30011000104 rep = new REP_30011000104();
		REP_30011000104.REP_BODY repBody = rep.getRepBody();
		repBody.setAvailBal("1000");
		repBody.setReference("ENS20190425TTT");
		repBody.setAcctBranch("01001");
		REP_30011000104.Fee fee = new REP_30011000104.Fee();
		fee.setFeeCcy("CNY");
		fee.setFeeAmt("1000");
		fee.setFeeType("JHF2");
		List<REP_30011000104.Fee> list = new ArrayList<REP_30011000104.Fee>();
		list.add(fee);
		repBody.setFeeDetail(list);
		
		if(repBody.getAvailBal()==null){
			//模拟交易执行异常
//			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
//			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000007);
			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_000000);
			throw e;
		}
		
		
		return rep;
	}
}
