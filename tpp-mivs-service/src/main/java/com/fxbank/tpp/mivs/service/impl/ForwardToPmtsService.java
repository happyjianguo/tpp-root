package com.fxbank.tpp.mivs.service.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.common.MyJedis;
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

import cn.highsuccess.connPool.api.tssc.HisuTSSCAPIResult;
import cn.highsuccess.connPool.api.tssc.secondpayment.HisuTSSCAPIForSecondPayment;
import redis.clients.jedis.Jedis;

@Service(version = "1.0.0")
public class ForwardToPmtsService implements IForwardToPmtsService {

	private static Logger logger = LoggerFactory.getLogger(ForwardToPmtsService.class);

	private static final String TIMEOUT_990 = "mivs.timeout_990";

	@Resource
	private MqQaClient mqQaClient;

	@Resource
	private SyncCom syncCom;

	@Resource
	private MyJedis myJedis;

	@Resource
	private HisuTSSCAPIForSecondPayment hisuTSSCAPIForSecondPayment;

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
//		myLog.info(logger, "签名字段为：" + signData.toString());
		if (signData != null) { // 需要签名
			String signature = null;
			try {
				HisuTSSCAPIResult result = this.hisuTSSCAPIForSecondPayment.hisuUniveralGenDataSign("CNAPS2",
						modelBase.getHeader().getOrigSender(), "SM2", signData.getBytes());
				if (result.getErrCode() < 0) {
					myLog.error(logger, "计算签名错误[" + result.getErrCode() + "]");
					throw new RuntimeException("计算签名错误");
				}
				signature = result.getSign();
			} catch (Exception e) {
				myLog.error(logger, "计算签名错误",e);
				throw new RuntimeException("计算签名错误");
			}
			myLog.info(logger, "计算签名成功");
			modelBase.getSign().setDigitalSignature(signature);
		}
		String pack = modelBase.creaPack();
		myLog.info(logger, "发送至PMTS的请求报文=[" + pack + "]");
		mqQaClient.put(myLog, pack);

		Integer timeout = 0;
		try (Jedis jedis = myJedis.connect()) {
			String stimeout = jedis.get(TIMEOUT_990);
			if (stimeout == null) {
				timeout = 60;
			} else {
				try {
					timeout = Integer.valueOf(stimeout);
				} catch (Exception e) {
					myLog.error(logger, "ccms990报文同步等待超时时间配置异常，取默认值60");
					timeout = 60;
				}
			}
		}

		CCMS_990_001_02_ComConf ccms990ComConf = syncCom.get(myLog, "990_" + modelBase.getHeader().getMesgID(), timeout,
				TimeUnit.SECONDS);
		String msgPrcCd = ccms990ComConf.getConfInf().getMsgPrcCd();
		if (!msgPrcCd.equals("PM1I0000")) {
			myLog.error(logger, "收到990错误应答" + msgPrcCd);
			throw new MivsTradeExecuteException(msgPrcCd, "请求发送失败");
		}

		return modelBase; // 发送原报文返回，用于请求端做下一次同异步转换
	}

	@Override
	public MODEL_BASE sendToPmtsNoWait(MODEL_BASE modelBase) throws SysTradeExecuteException {
		MyLog myLog = modelBase.getMylog();

		// 请求头赋值
		modelBase.getHeader().setOrigSendDate(modelBase.getSysDate());
		modelBase.getHeader().setOrigSendTime(modelBase.getSysTime());
		modelBase.getHeader().setMesgType(modelBase.getMesgType());
		// modelBase.getHeader().setMesgID(String.format("%08d%08d",
		// modelBase.getSysDate(), modelBase.getSysTraceno()));
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

		return modelBase; // 发送原报文返回，用于请求端做下一次同异步转换
	}

}
