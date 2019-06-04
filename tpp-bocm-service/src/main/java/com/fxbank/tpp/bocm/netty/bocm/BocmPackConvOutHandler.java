package com.fxbank.tpp.bocm.netty.bocm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.tpp.bocm.model.REQ_BASE;

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
	
	private String MAC;

	public BocmPackConvOutHandler(MyLog myLog, String MAC) {
		this.myLog = myLog;
		this.MAC = MAC;
	}
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		REQ_BASE reqBase = (REQ_BASE)msg;
		StringBuffer fixPack = new StringBuffer(FixedUtil.toFixed(reqBase,"UTF-8"));
		//TODO 生成MAC
//		myLog.info(logger, "组包发送交行报文");
//		String jsonReq = fixPack.toString();
		
//		//需要生成MAC
//		byte[] macBytes = jsonReq.getBytes();
//		String mac = macService.calcBOCM(myLog,macBytes);
//		jsonReq = jsonReq.replaceFirst(BOCM.macPlaceHolder, mac);
//		myLog.info(logger, "组包发送交行报文MAC:"+jsonReq);
		
		myLog.info(logger, "组包发送交行报文");
//		String jsonReq = fixPack.toString();
		//需要生成MAC
//		byte[] macBytes = jsonReq.getBytes();
//		reqBase.getMylog();
//		String mac = macService.calcBOCM(reqBase.getMylog(),macBytes);
//		jsonReq = jsonReq.replaceFirst(BOCM.macPlaceHolder, mac);
//		myLog.info(logger, "组包发送交行报文MAC:"+jsonReq);
		
		
		
//		fixPack.append("FFFFFFFFFFFFFFFF");
		fixPack.append(MAC);
		
		this.myLog.info(logger, "交行请求模拟返回报文  =[" + fixPack.toString() + "]");
		
		ctx.writeAndFlush(fixPack.toString(), promise);
	}

}
