package com.fxbank.tpp.mivs.service.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.mivs.model.MODEL_BASE;
import com.fxbank.tpp.mivs.mq.MqQaClient;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.sync.SyncCom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service(version = "1.0.0")
public class ForwardToPmtsService implements IForwardToPmtsService {

	private static Logger logger = LoggerFactory.getLogger(ForwardToPmtsService.class);

	@Resource
	private MqQaClient mqQaClient;

	@Resource
	private SyncCom syncCom;

	@Override
	public MODEL_BASE sendToPmts(MODEL_BASE modelBase) throws SysTradeExecuteException {
		MyLog myLog = modelBase.getMylog();

		// 请求头赋值
		modelBase.getHeader().setOrigSendDate(modelBase.getSysDate());
		modelBase.getHeader().setOrigSendTime(modelBase.getSysTime());
		modelBase.getHeader().setMesgType(modelBase.getMesgType());
		modelBase.getHeader().setMesgID(String.format("%08d%08d", modelBase.getSysDate(), modelBase.getSysTraceno()));
		modelBase.getHeader()
				.setMesgRefID(String.format("%08d%08d", modelBase.getSysDate(), modelBase.getSysTraceno()));

		String signData = modelBase.signData(); // 待签名数据
		if (signData != null) { // 需要签名
			String signature = "SS+" + signData; // TODO 计算签名
			modelBase.getSign().setDigitalSignature(signature);
		}
		String pack = modelBase.creaPack();
		myLog.info(logger, "发送至PMTS的请求报文=[" + pack + "]");
		mqQaClient.put(myLog, pack);

		CCMS_990_001_02_ComConf ccms990ComConf = syncCom.get(myLog, "990_" + modelBase.getHeader().getMesgID(), 60,
				TimeUnit.SECONDS);
		String msgPrcCd = ccms990ComConf.getConfInf().getMsgPrcCd();
		if(!msgPrcCd.equals("PM1I0000")){
			myLog.error(logger, "收到990错误应答"+msgPrcCd);
			throw new MivsTradeExecuteException(msgPrcCd, "请求发送失败");
		}

		return modelBase; //发送原报文返回，用于请求端做下一次同异步转换
	}
	
	@Override
	public MODEL_BASE sendToPmtsNoWait(MODEL_BASE modelBase) throws SysTradeExecuteException {
		MyLog myLog = modelBase.getMylog();

		// 请求头赋值
		modelBase.getHeader().setOrigSendDate(modelBase.getSysDate());
		modelBase.getHeader().setOrigSendTime(modelBase.getSysTime());
		modelBase.getHeader().setMesgType(modelBase.getMesgType());
		//modelBase.getHeader().setMesgID(String.format("%08d%08d", modelBase.getSysDate(), modelBase.getSysTraceno()));
		modelBase.getHeader()
				.setMesgRefID(String.format("%08d%08d", modelBase.getSysDate(), modelBase.getSysTraceno()));

		String signData = modelBase.signData(); // 待签名数据
		if (signData != null) { // 需要签名
			String signature = "SS+" + signData; // TODO 计算签名
			modelBase.getSign().setDigitalSignature(signature);
		}
		String pack = modelBase.creaPack();
		myLog.info(logger, "发送至PMTS的请求报文=[" + pack + "]");
		mqQaClient.put(myLog, pack);

		return modelBase; //发送原报文返回，用于请求端做下一次同异步转换
	}

}
