package com.fxbank.cap.paf.nettty.server;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.controller.TradeDispatcherBase;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Service("tradeDispatcherHandler")
@Sharable
public class TradeDispatcherHandler extends ChannelInboundHandlerAdapter {
	private static Logger logger = LoggerFactory.getLogger(TradeDispatcherHandler.class);

	@Resource
	private TradeDispatcherBase tradeDispatcherBase;

	@Resource
	private LogPool logPool;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		MyLog myLog = logPool.get();
		myLog.debug(logger, "交易流程执行开始...");
		DataTransObject reqDto = (DataTransObject) msg;
		DataTransObject repDto = tradeDispatcherBase.txMainFlowController(reqDto);
		Map<String,DataTransObject> dtoMap = new HashMap<String,DataTransObject>();
		dtoMap.put("reqDto", reqDto);
		dtoMap.put("repDto", repDto);
		myLog.debug(logger, "交易流程执行完毕...");
		ctx.write(dtoMap);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		MyLog logUtil = logPool.get();
		logUtil.error(logger, "TradeDispatcherHandler process error!", cause);
		Map<String,DataTransObject> dtoMap = new HashMap<String,DataTransObject>();
		DataTransObject repDto = new DataTransObject(new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999));
		dtoMap.put("repDto", repDto);
		ctx.write(dtoMap);
	}

}
