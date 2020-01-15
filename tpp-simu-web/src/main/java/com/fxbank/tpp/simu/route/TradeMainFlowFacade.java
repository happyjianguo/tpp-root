package com.fxbank.tpp.simu.route;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.model.ESB_BASE;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/** 
* @ClassName: TradeMainFlowFacade 
* @Description: 交易执行门面 
* @author ZhouYongwei
* @date 2018年4月3日 下午3:41:18 
*  
*/
@Service("tradeMainFlowFacade")
public class TradeMainFlowFacade {

	@Resource(name = "tradeMainFlowHelper")
	private TradeMainFlowHelper tradeMainFlowHelper;
	
	public ESB_BASE invoke(String txCode, ESB_BASE dto) throws SysTradeExecuteException {
		return this.tradeMainFlowHelper.invoke(txCode, dto);
	}

}
