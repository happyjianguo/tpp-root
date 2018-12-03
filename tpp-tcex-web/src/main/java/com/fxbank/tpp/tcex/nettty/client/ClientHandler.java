package com.fxbank.tpp.tcex.nettty.client;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.tcex.utils.ByteUtils;
import com.fxbank.tpp.tcex.utils.JAXBUtils;
import com.fxbank.tpp.tcex.utils.StringUtils;

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

			String repData = null;

			//应答给同步等待进程
			ClientSyncFuture<String> future = clientPublic.getSyncMap().get("1");
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
