package com.fxbank.tpp.bocm.netty.bocm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.netty.NettySyncClient;
import com.fxbank.cip.base.netty.NettySyncSlot;
import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.tpp.bocm.model.REP_BASE;
import com.fxbank.tpp.bocm.model.REP_ERROR;
import com.fxbank.tpp.bocm.service.IBocmSafeService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;

/**
 * @Description: 交行客户端应答拆包
 * @Author: 周勇沩
 * @Date: 2019-04-15 12:00:22
 */
public class BocmPackConvInHandler<T> extends ChannelInboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(BocmPackConvInHandler.class);

	private MyLog myLog;

	private Class<T> clazz;
	
    @Reference(version = "1.0.0")
    private IBocmSafeService safeService;

	public BocmPackConvInHandler(MyLog myLog, Class<T> clazz, IBocmSafeService safeService) {
		this.myLog = myLog;
		this.clazz = clazz;
		this.safeService = safeService;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			StringBuffer pack = new StringBuffer((String) msg);
			String mac = pack.substring(pack.length() - 16);
			this.myLog.info(logger, "接收报文  =[" + pack + "]");
			this.myLog.info(logger, "mac=[" + mac + "]");

			String fixPack = null;
			REP_BASE repBase = (REP_BASE) this.clazz.newInstance();	
			//判断行通讯应答基础类是否校验mac
			if(repBase.isCheckMac()){
				//校验MAC	联机交易Mac验证
				safeService.verifyBocmMac(myLog, fixPack, mac);	
				fixPack = pack.substring(0, pack.length() - 16);
			}else{
				fixPack = pack.toString();
			}
			
			if(fixPack.substring(0, 1).equals("N")){
				repBase = (REP_BASE)new FixedUtil(fixPack,BocmClient.CODING).toBean(repBase.getClass());
			}else{
				repBase = new REP_ERROR();
				repBase = (REP_BASE)new FixedUtil(fixPack,BocmClient.CODING).toBean(repBase.getClass());
				this.myLog.info(logger, "相应错误码【" + repBase.getTrspCd() + "】");
				this.myLog.info(logger, "错误描述【" + repBase.getTrspMsg() + "】");
			}

			ctx.fireChannelRead(repBase);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Attribute<NettySyncSlot<T>> slotAttr = ctx.channel().attr(AttributeKey.valueOf(NettySyncClient.SLOTKEY));
		NettySyncSlot<T> slot = slotAttr.get();
		slot.setResponse(null);
		this.myLog.info(logger, "解析服务端应答异常", cause);
	}

}
