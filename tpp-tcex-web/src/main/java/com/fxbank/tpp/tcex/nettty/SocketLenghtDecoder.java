package com.fxbank.tpp.tcex.nettty;

import java.net.InetAddress;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.tcex.constant.TCEX;
import com.fxbank.tpp.tcex.utils.ByteUtils;
import com.tienon.util.RefbdcUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import redis.clients.jedis.Jedis;

public class SocketLenghtDecoder extends ByteToMessageDecoder {

	private static Logger logger = LoggerFactory.getLogger(SocketLenghtDecoder.class);
	private static final String COMMON_PREFIX = "paf_common.";

	private static final int HEADERLENGTH = 16;

	private int lastLength = 0;

	private LogPool logPool;

	@Resource
	private MyJedis myJedis;

	public SocketLenghtDecoder(LogPool logPool, MyJedis myJedis) {
		this.logPool = logPool;
		this.myJedis = myJedis;
	}

	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		MyLog logUtil = new MyLog(UUID.randomUUID().toString(), InetAddress.getLocalHost().getHostAddress().toString());
		logPool.init(logUtil);

		if (in == null) {
			return null;
		}

		if (in.readableBytes() < HEADERLENGTH + 4) {
			logUtil.error(logger, "接收数据太短，不处理[" + in.readableBytes() + "]");
			int readLen = in.readableBytes();
			byte[] req = new byte[readLen];
			ByteBuf buf = in.readBytes(readLen);
			buf.readBytes(req);
			logUtil.error(logger, new String(req, TCEX.ENCODING));
			return null;
		}

		in.markReaderIndex();
		ByteBuf headerBuf = in.readBytes(HEADERLENGTH);
		byte[] headerBytes = new byte[HEADERLENGTH];
		headerBuf.readBytes(headerBytes);
		String header = new String(headerBytes, TCEX.ENCODING);
		logUtil.info(logger, "接收到的报文头[" + header + "]");

		Integer dataLen = 0;
		try {
			dataLen = in.readInt();
			byte[] dataLenBytes = ByteUtils.intToBytes2(dataLen);
			logger.info("接收长度十六进制：" + ByteUtils.byteArrToHexStr(dataLenBytes));
			logUtil.info(logger, "接收到的报文长度[" + dataLen + "]");
		} catch (Exception e) {
			logUtil.error(logger, "报文长度不合法！", e);
			throw new RuntimeException("报文长度不合法");
		}

		// 判断是否分包,数据长度大于等于总长度或者本次读取数据长度与上次相同认为分包结束
		int readLength = in.readableBytes();
		if (readLength < dataLen) {
			in.resetReaderIndex();
			// 缓存当前剩余的buffer数据，等待剩下数据包到来
			if (lastLength != readLength) {
				lastLength = readLength;
				return null;
			}
			lastLength = 0;
		}

		// 读取数据超时后关闭客户端连接，丢弃已读取的报文
		if (!ctx.channel().isActive()) {
			return null;
		}

		StringBuffer bodyBuf = new StringBuffer(1024);
		bodyBuf.append(header);
		int pos = 0;
		byte[] data = new byte[dataLen];
		while (pos<dataLen) {
			int readLen = in.readableBytes() > 1024 ? 1024 : in.readableBytes();
			ByteBuf buf = in.readBytes(readLen);
			buf.readBytes(data,pos,readLen);
			pos += readLen;
		}

		String login = header.substring(6, 7); // 0-业务类交易，1-签到交易
		if (login.equals("0")) { // 0-业务类交易，服务端只会收到业务交易
			byte[] sessionKey = null;
			try (Jedis jedis = myJedis.connect()) {
				String encryptSessionKey = jedis.get(TCEX.SESSIONKEY);
				String absolutePath = jedis.get(COMMON_PREFIX+"key_path");
				sessionKey = RefbdcUtil.decryptSessionKey(absolutePath, encryptSessionKey);
				logger.info("秘钥明文转换成功");
			}
			byte[] decryptMsg = RefbdcUtil.decryptMsg(data, sessionKey);
			logger.info("业务类交易解密成功");
			bodyBuf.append(new String(decryptMsg, TCEX.ENCODING));
		} else if (login.equals("1")) {
			logUtil.error(logger, "测试用签到交易");
			bodyBuf.append(new String(data, TCEX.ENCODING));
		} else {
			logUtil.error(logger, "交易类型标识错误" + login);
			throw new RuntimeException("交易类型标识错误" + login);
		}
		String rcvPack = bodyBuf.toString();
		logUtil.debug(logger, "接收到解码后的报文：" + rcvPack);
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
