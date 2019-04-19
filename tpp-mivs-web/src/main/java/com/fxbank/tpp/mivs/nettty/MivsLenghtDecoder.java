package com.fxbank.tpp.mivs.nettty;

import java.net.InetAddress;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ReferenceCountUtil;

/**
 * @Description: 交行来账请求接收
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:17:58
 */
public class MivsLenghtDecoder extends ByteToMessageDecoder {

	private static Logger logger = LoggerFactory.getLogger(MivsLenghtDecoder.class);
	private final Integer DATALENGTH = 8;
	private LogPool logPool;
	private MyLog myLog;

	public MivsLenghtDecoder(LogPool logPool) {
		this.logPool = logPool;
	}

	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		this.myLog = new MyLog(UUID.randomUUID().toString(), InetAddress.getLocalHost().getHostAddress().toString());
		logPool.init(this.myLog);
		if (in == null) {
			return null;
		}
		int readableLen = in.readableBytes();
		if ( readableLen < DATALENGTH ) {
			byte[] lenBytes = new byte[readableLen];
			ByteBuf buf = in.readBytes(readableLen);
			buf.readBytes(lenBytes);
			this.myLog.error(logger, "接收数据太短，不处理["+readableLen+"]["+new String(lenBytes, ServerInitializer.CODING)+"]");
			return null;
		}
		in.markReaderIndex();
		ByteBuf lenbuf = in.readBytes(DATALENGTH);
		byte[] lenbyte = new byte[DATALENGTH];
		lenbuf.readBytes(lenbyte); 
		ReferenceCountUtil.release(lenbuf);
		String lenStr = new String(lenbyte,ServerInitializer.CODING);
		if (!isInteger(lenStr)) {
			Exception e = new RuntimeException("报文长度不合法");
			this.myLog.error(logger, "报文长度不合法"+lenStr, e);
			throw e;
		}
		Integer len = new Integer(lenStr);
		// 判断是否分包,数据长度大于等于总长度或者本次读取数据长度与上次相同认为分包结束
		int readLength = in.readableBytes();
		if (readLength < len) {
			this.myLog.error(logger, "等待后续分包["+readLength+"]["+len+"]");
			in.resetReaderIndex();
			return null;
		}

		// 读取数据超时后关闭客户端连接，丢弃已读取的报文
		if (!ctx.channel().isActive()) {
			return null;
		}
		StringBuffer msgbuf = new StringBuffer(1024);
		// 一次做多只能读取1024个字节
		int onceLen = 1024;
		if (len > onceLen) {
			while (in.readableBytes() > 0) {
				int readLen = in.readableBytes() > onceLen ? onceLen: in.readableBytes();
				ByteBuf buf = in.readBytes(readLen);
				byte[] data = new byte[readLen];
				buf.readBytes(data);
				msgbuf.append(new String(data, ServerInitializer.CODING));
				ReferenceCountUtil.release(buf);
				this.myLog.info(logger, "剩余字节数量"+in.readableBytes());
			}
		} else {
			ByteBuf buf = in.readBytes(len);
			byte[] req = new byte[buf.readableBytes()];
			buf.readBytes(req);
			msgbuf.append(new String(req, ServerInitializer.CODING));
			ReferenceCountUtil.release(buf);
		}

		String body = msgbuf.toString();

		this.myLog.info(logger, "接收到客户端请求["+body+"]");

		return body;
	}

	public boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[\\d]*$");
		return pattern.matcher(str).matches();
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		Object decoded = decode(ctx, in);
		if (decoded != null) {
			out.add(decoded);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		this.myLog.info(logger, "接收客户端请求异常",cause);
		ctx.close();
	}

}
