package com.fxbank.tpp.ccex.nettty;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fxbank.cap.paf.constant.PAF;
import com.fxbank.cap.paf.dto.SER_REQ_BDC;
import com.fxbank.cap.paf.dto.SER_REQ_DATA;
import com.fxbank.cap.paf.model.FIELD;
import com.fxbank.cap.paf.model.FIELDS_LIST_INNER;
import com.fxbank.cap.paf.utils.JAXBUtils;
import com.fxbank.cap.paf.utils.StringUtils;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.log.MyLog;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

@Component("serverPackConvInHandler")
@Sharable
public class ServerPackConvInHandler extends ChannelInboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(ServerPackConvInHandler.class);

	@Resource
	private LogPool logPool;

	@Resource
	private MyJedis myJedis;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		MyLog logUtil = logPool.get();
		try {
			String strMsg = (String) msg;
			String packHeader = strMsg.substring(0, 16);
			String txCode = StringUtils.trimEspChar(strMsg.substring(7, 14), (byte) 0);
			String data = strMsg.substring(16);

			logUtil.info(logger, "交易代码【" + txCode + "】");

			Class<?> beanClass = null;
			String className = "com.fxbank.cap.paf.dto.SER_REQ_BDC";
			try {
				beanClass = Class.forName(className);
			} catch (ClassNotFoundException e) {
				logUtil.error(logger, "类文件[" + className + "]未定义", e);
				throw e;
			}

			logUtil.info(logger, "接收到报文体" + data);

			SER_REQ_DATA reqData = null;
			try {
				SER_REQ_BDC reqBDC = (SER_REQ_BDC) JAXBUtils.unmarshal(data.getBytes(PAF.ENCODING), beanClass);
				reqData = new SER_REQ_DATA();
				reqData.setPackHeader(packHeader); // 保存通讯报文头，返回用
				for (FIELD field : reqBDC.getHead().getField()) {
					reqData.getHead().put(field.getName(), field.getValue());
				}
				if (reqBDC.getBody() != null) {
					for (FIELD field : reqBDC.getBody().getField()) {
						reqData.getBody().put(field.getName(), field.getValue());
					}
				}
				if (reqBDC.getBody().getField_list() != null) {
					for (FIELDS_LIST_INNER fieldList : reqBDC.getBody().getField_list().getField_list()) {
						for (FIELD field : fieldList.getField()) {
							reqData.getBodyList().put(field.getName() + fieldList.getName(), field.getValue());
						}
					}
				}
				reqData.setTxCode(txCode);
				reqData.setOthDate(Integer.valueOf((String)reqData.getHead().get("SendDate")));
				reqData.setOthTraceno((String)reqData.getHead().get("SendSeqNo"));
				reqData.setSourceType("GJ");
			} catch (RuntimeException e) {
				logUtil.error(logger, "解析报文失败[" + data + "]", e);
				throw e;
			}

			ctx.fireChannelRead(reqData);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		logger.debug("接收到客户端请求");
	}

}
