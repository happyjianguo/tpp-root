package com.fxbank.tpp.beps.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.controller.TradeDispatcherBase;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.log.MyLog;

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
	
	@Resource
	private LogPool logPool;
	
	@RequestMapping({ 
		"/esb/beps.do",
		})
	public void txMainFlowController(HttpServletRequest request, HttpServletResponse response) {
		MyLog myLog = logPool.get();
		myLog.debug(logger, "交易流程执行开始...");
		DataTransObject reqDto = (DataTransObject) request.getAttribute("REQDTO");
		DataTransObject repDto = tradeDispatcherBase.txMainFlowController(reqDto);
		request.setAttribute("REPDTO", repDto);
		myLog.debug(logger, "交易流程执行完毕...");
	}

}
