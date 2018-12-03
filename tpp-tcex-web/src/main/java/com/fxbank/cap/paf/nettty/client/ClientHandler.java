package com.fxbank.cap.paf.nettty.client;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fxbank.cap.paf.model.CLI_REP_BDC;
import com.fxbank.cap.paf.model.CLI_REP_DATA;
import com.fxbank.cap.paf.model.FIELD;
import com.fxbank.cap.paf.utils.ByteUtils;
import com.fxbank.cap.paf.utils.JAXBUtils;
import com.fxbank.cap.paf.utils.StringUtils;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.log.MyLog;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

@Component("clientPackConvInHandler")
@Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(ClientHandler.class);

	@Resource
	private LogPool logPool;
	
	@Resource
	private ClientSyncNetty clientPublic;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		MyLog logUtil = logPool.get();
		try {
			String strMsg = (String) msg;
			String packHeader = strMsg.substring(0, 16);
			String systemId = strMsg.substring(0, 6);
			String login = strMsg.substring(6, 7); // 0-业务类交易，1-签到交易
			String txCode = StringUtils.trimEspChar(strMsg.substring(7, 14), (byte)0);
			String reserv = strMsg.substring(14, 16);
			String data = strMsg.substring(16);

			if (login.equals("0")) { // 0-业务类交易
				;
				// TODO 3DES解密
			} else if (login.equals("0")) { // 1-签到交易
				;
				// TODO openssl 解密
			}else{
				logUtil.error(logger, "交易类型标识错误" + login);
				throw new RuntimeException("交易类型标识错误" + login);
			}

			logUtil.info(logger, "应答交易代码【" + txCode + "】");

			Class<?> beanClass = null;
			String className = "com.fxbank.cap.paf.dto.CLI_REP_BDC";
			try {
				beanClass = Class.forName(className);
			} catch (ClassNotFoundException e) {
				logUtil.error(logger, "类文件[" + className + "]未定义", e);
				throw e;
			}

			CLI_REP_DATA repData = null;
			try {
				CLI_REP_BDC reqBDC = (CLI_REP_BDC) JAXBUtils.unmarshal(data.getBytes(), beanClass);
				repData = new CLI_REP_DATA();
				for (FIELD field : reqBDC.getHead().getField()) {
					repData.getHead().put(field.getName(), field.getValue());
				}
				if (reqBDC.getBody().getField() != null) {
					for (FIELD field : reqBDC.getBody().getField()) {
						repData.getBody().put(field.getName(), field.getValue());
					}
				}
			} catch (RuntimeException e) {
				logUtil.error(logger, "解析报文失败[" + data + "]", e);
				throw e;
			}

			//应答给同步等待进程
			ClientSyncFuture<CLI_REP_DATA> future = clientPublic.getSyncMap().get("1");
			if(future==null){
				logUtil.error(logger, "客户端等待超时,丢弃应答"+repData);
			}else{
				future.setResponse(repData);
			}
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		logger.info("接收到客户端请求");
	}
	
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接关闭! ");
        super.channelInactive(ctx);
    }

}
