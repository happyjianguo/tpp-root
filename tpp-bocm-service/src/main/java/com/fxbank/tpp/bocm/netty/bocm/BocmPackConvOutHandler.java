package com.fxbank.tpp.bocm.netty.bocm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.tpp.bocm.model.REQ_BASE;
import com.fxbank.tpp.bocm.service.IBocmSafeService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @Description: 交行客户端请求组包
 * @Author: 周勇沩
 * @Date: 2019-04-15 12:00:43
 */
public class BocmPackConvOutHandler extends ChannelOutboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(BocmPackConvOutHandler.class);

	private MyLog myLog;
	
	private IBocmSafeService safeService;

	public BocmPackConvOutHandler(MyLog myLog, IBocmSafeService safeService) {
		this.myLog = myLog;
		this.safeService = safeService;
	}
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		REQ_BASE reqBase = (REQ_BASE)msg;
		StringBuffer fixPack = new StringBuffer(FixedUtil.toFixed(reqBase,BocmClient.CODING));
		myLog.info(logger, "组包发送交行报文");	
		//REQ_BASE交行通讯请求基础类checkMac是否为true（默认true），fasle不添加Mac校验
		if(reqBase.isCheckMac()==true){
			//校验MAC	联机交易Mac验证
			String mac = safeService.calcBocmMac(myLog, fixPack.toString());	
			fixPack.append(mac);
			myLog.info(logger, "调用加密平台生成Mac： 【"+mac+"】");		
		}else{
			//特殊交易不进行mac验证（对账），在初始化报文时赋值checkmac为false
			myLog.info(logger, "不校验mac");		
		}			
		ctx.writeAndFlush(fixPack.toString(), promise);
	}

}
