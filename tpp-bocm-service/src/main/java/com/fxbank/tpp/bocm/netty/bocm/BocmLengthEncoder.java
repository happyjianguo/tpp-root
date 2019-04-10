package com.fxbank.tpp.bocm.netty.bocm;

import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class BocmLengthEncoder extends MessageToByteEncoder<Object> {
	
	private static Logger logger = LoggerFactory.getLogger(BocmLengthEncoder.class);

	private MyLog myLog;

	public BocmLengthEncoder(MyLog myLog){
		this.myLog = myLog;
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		String msgStr = (String) msg;
		byte[] data = msgStr.getBytes();
		out.writeBytes(data);
	}

}
