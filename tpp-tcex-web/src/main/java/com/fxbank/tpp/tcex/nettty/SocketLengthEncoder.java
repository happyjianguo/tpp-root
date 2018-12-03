package com.fxbank.tpp.tcex.nettty;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fxbank.tpp.tcex.common.MyRefbdcUtil;
import com.fxbank.tpp.tcex.constant.TCEX;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@Component("socketLengthEncoder")
@Sharable
public class SocketLengthEncoder extends MessageToByteEncoder<Object> {
	
	private static Logger logger = LoggerFactory.getLogger(SocketLengthEncoder.class);

	private static final int HEADERLENGTH = 16;
	
	@Resource
	private MyRefbdcUtil myRefbdcUtil;
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		String msgStr = (String) msg;
		String head = msgStr.substring(0,HEADERLENGTH);
		logger.info("发送的报文头："+head);
		String body = msgStr.substring(HEADERLENGTH);
		
		String login = head.substring(6, 7); // 0-业务类交易，1-签到交易
		byte[] data=null;
		if (login.equals("0")) { // 0-业务类交易，服务端只会收到业务交易
			data = myRefbdcUtil.encrypt(body.getBytes(TCEX.ENCODING));
			logger.info("业务类交易加密成功");
		}else{	//-签到交易
			logger.error("测试用签到交易");
			data = body.getBytes(TCEX.ENCODING);
		}
		
		int bodyLen = data.length;
		logger.info("发送的报文体长度["+bodyLen+"]");
		
		out.writeBytes(head.getBytes(TCEX.ENCODING));
		out.writeInt(bodyLen);
		out.writeBytes(data);
	}

}
