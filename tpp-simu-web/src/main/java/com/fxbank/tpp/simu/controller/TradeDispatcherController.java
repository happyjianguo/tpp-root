package com.fxbank.tpp.simu.controller;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.model.ESB_BASE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
* @ClassName: TradeDispatcherController 
* @Description: 交易处理控制器
* @author ZhouYongwei
* @date 2018年4月3日 上午11:10:05 
*  
*/
@Controller
public class TradeDispatcherController {

	private static Logger logger = LoggerFactory.getLogger(TradeDispatcherController.class);

	@Resource
	private TradeDispatcherBase tradeDispatcherBase;
	
	@RequestMapping({
		"/simu.do",
		})
	public void txMainFlowController(HttpServletRequest request, HttpServletResponse response) throws SysTradeExecuteException {
		String txCode= (String) request.getAttribute("TXCODE");
		ESB_BASE reqDto = (ESB_BASE) request.getAttribute("REQDTO");
		ESB_BASE repDto = tradeDispatcherBase.txMainFlowController(txCode, reqDto);
		request.setAttribute("REPDTO", repDto);
	}

}
