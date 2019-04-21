package com.fxbank.tpp.mivs.service.impl;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.model.MIVS_320_001_01;
import com.fxbank.tpp.mivs.model.REQ_BASE;
import com.fxbank.tpp.mivs.mq.MqQaClient;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

@Service(version = "1.0.0")
public class ForwardToPmtsService extends JedisPubSub implements IForwardToPmtsService {

	private static Logger logger = LoggerFactory.getLogger(ForwardToPmtsService.class);

	@Resource
	private MqQaClient MqQaClient;

	@Resource
	private MyJedis myJedis;

	@Override
	public void sendToPmts(REQ_BASE reqBase) throws SysTradeExecuteException {
		MyLog myLog = reqBase.getMylog();

		// 请求头赋值
		reqBase.getHeader().setOrigSendDate(reqBase.getSysDate());
		reqBase.getHeader().setOrigSendTime(reqBase.getSysTime());
		reqBase.getHeader().setMesgType(reqBase.getMesgType());
		reqBase.getHeader().setMesgID(String.format("%08d%08d", reqBase.getSysDate(), reqBase.getSysTraceno()));
		reqBase.getHeader().setMesgRefID(String.format("%08d%08d", reqBase.getSysDate(), reqBase.getSysTraceno()));

		String signData = reqBase.signData(); // 待签名数据
		if (signData != null) { // 需要签名
			String signature = "SS+" + signData; // TODO 计算签名
			reqBase.getSign().setDigitalSignature(signature);
		}
		String pack = reqBase.creaPack();
		myLog.info(logger, "发送至PMTS的请求报文=[" + pack + "]");
		MqQaClient.put(pack);
		// redis 订阅发布TODO 990的处理 990不是成功，则抛出异常
		try (Jedis jedis = myJedis.connect()) {
			jedis.subscribe(this, "990"); // 通过subscribe 的api去订阅，入参是订阅者和频道名
		}

	}

	@Override
	public void onMessage(String channel, String message) { // 收到消息会调用
	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) { // 订阅了频道会调用
	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) { // 取消订阅 会调用

	}

	public static void main(String[] args) {
		MIVS_320_001_01 mivs = new MIVS_320_001_01(new MyLog(), 20190909, 12323, 12);
		mivs.getHeader().setOrigSender("313131000008"); // TODO 通过机构号查询渠道接口获取（机构号查行号）
		mivs.getHeader().setOrigReceiver("0000");
		mivs.getGetIdVrfctn().getMsgHdr().getInstgPty().setInstgDrctPty("3131310000008");
		mivs.getGetIdVrfctn().getMsgHdr().getInstgPty().setDrctPtyNm("阜新银行清算中心");
		mivs.getGetIdVrfctn().getMsgHdr().getInstgPty().setInstgPty("313131000009");
		mivs.getGetIdVrfctn().getMsgHdr().getInstgPty().setPtyNm("阜新银行银河支行");

		mivs.getGetIdVrfctn().getVryDef().setMobNb("18309872813");
		mivs.getGetIdVrfctn().getVryDef().setNm("周勇沩");
		mivs.getGetIdVrfctn().getVryDef().setIdTp("1");
		mivs.getGetIdVrfctn().getVryDef().setId("210381198302252714");
		mivs.getGetIdVrfctn().getVryDef().setUniSocCdtCd("uniSocCdtCd");
		mivs.getGetIdVrfctn().getVryDef().setOpNm("张志宏");

		IForwardToPmtsService pmtsService = new ForwardToPmtsService();
		try {
			pmtsService.sendToPmts(mivs);
		} catch (SysTradeExecuteException e) {
			e.printStackTrace();
		}
	}

}
