package com.fxbank.tpp.bocm.netty.bocm;

import java.util.List;

import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class BocmLenghtDecoder extends ByteToMessageDecoder {

	private static Logger logger = LoggerFactory.getLogger(BocmLenghtDecoder.class);

	private MyLog myLog;


	public BocmLenghtDecoder(MyLog myLog) {
		this.myLog = myLog;
	}

	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

		int dataLen = in.readableBytes();
		byte[] data = new byte[dataLen];
		ByteBuf buf = in.readBytes(dataLen);
		buf.readBytes(data,0,dataLen);

		String rcvPack = new String(data);
		this.myLog.debug(logger, "接收到解码后的报文：" + rcvPack);
		return rcvPack;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		Object decoded = decode(ctx, in);
		if (decoded != null) {
			out.add(decoded);
		}
	}

}
