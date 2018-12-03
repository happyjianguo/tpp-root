package com.fxbank.cap.paf.socket.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fxbank.cap.paf.common.MyRefbdcUtil;
import com.fxbank.cap.paf.common.ParmGeter;
import com.fxbank.cap.paf.constant.PAF;
import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.model.CLI_REP_BDC;
import com.fxbank.cap.paf.model.CLI_REP_DATA;
import com.fxbank.cap.paf.model.CLI_REQ_BDC;
import com.fxbank.cap.paf.model.FIELD;
import com.fxbank.cap.paf.model.FIELDS_LIST_INNER;
import com.fxbank.cap.paf.utils.ByteUtils;
import com.fxbank.cap.paf.utils.JAXBUtils;
import com.fxbank.cap.paf.utils.StringUtils;
import com.fxbank.cip.base.common.MyJedis;

import redis.clients.jedis.Jedis;

@Component
public class ClientSyncSocket {

	private static Logger logger = LoggerFactory.getLogger(ClientSyncSocket.class);

	private static final String COMMON_PREFIX = "paf_common.";

	@Resource
	private MyJedis myJedis;

	@Resource
	private MyRefbdcUtil myRefbdcUtil;

	public CLI_REP_DATA comm(String txCode, Integer sysDate, Integer sysTime, Integer sysTraceno, String receiveNode,
			CLI_REQ_BDC reqData) throws PafTradeExecuteException {

		String isLogin = null;
		if ("BDC001".equals(txCode)) {
			isLogin = "1"; // 签到交易
		} else {
			isLogin = "0"; // 业务交易
		}

		byte[] reqPack = creaPack(txCode, isLogin, sysDate, sysTime, sysTraceno, receiveNode, reqData);

		String repPack = sndAndRcvPack(isLogin, creaHead(txCode, isLogin), reqPack);
		logger.info("收到往账应答报文:" + repPack);
		return chanPack(repPack);

	}

	private String creaHead(String txCode, String isLogin) {
		StringBuffer sndHeader = new StringBuffer();
		sndHeader.append(PAF.BANK_CODE);
		sndHeader.append(isLogin);
		sndHeader.append(StringUtils.fillStringAppend(txCode, 7, "\0"));
		sndHeader.append(StringUtils.fillStringAppend("", 2, "\0"));
		logger.info("发送报文头:" + sndHeader.toString());
		return sndHeader.toString();
	}

	private byte[] creaPack(String txCode, String isLogin, Integer sysDate, Integer sysTime, Integer sysTraceno,
			String receiveNode, CLI_REQ_BDC reqData) throws PafTradeExecuteException {

		reqData.getHead().getField().add(new FIELD("SendDate", String.valueOf(sysDate)));
		reqData.getHead().getField().add(new FIELD("SendTime", String.format("%06d", sysTime)));
		reqData.getHead().getField().add(new FIELD("SendSeqNo", String.format("%s%06d%09d",
				PAF.BANK_CODE.substring(PAF.BANK_CODE.length() - 5), sysDate % 1000000, sysTraceno)));
		reqData.getHead().getField().add(new FIELD("SendNode", PAF.BANK_CODE));
		reqData.getHead().getField().add(new FIELD("TxCode", txCode));
		reqData.getHead().getField().add(new FIELD("ReceiveNode", receiveNode));
		byte[] reqPack = null;
		try {
			byte[] reqByte = JAXBUtils.marshal(reqData);
			logger.info("生成往账请求报文:" + new String(reqByte, PAF.ENCODING));
			if ("0".equals(isLogin)) {
				reqPack = myRefbdcUtil.encrypt(reqByte);
			} else {
				reqPack = reqByte;
			}
		} catch (Exception e) {
			logger.error("生成往账报文异常", e);
			PafTradeExecuteException e1 = new PafTradeExecuteException(PafTradeExecuteException.PAF_E_10004);
			logger.error(e1.getRspCode() + " | " + e1.getRspMsg());
			throw e1;
		}

		return reqPack;
	}

	private String sndAndRcvPack(String isLogin, String reqHead, byte[] reqPack) throws PafTradeExecuteException {
		Socket socket = null;
		OutputStream os = null;
		InputStream is = null;
		try {
			
			try {
				String host = null ;
		    	Integer port = null;
		        try(Jedis jedis = myJedis.connect()){
		        	host = jedis.get(COMMON_PREFIX+"paf_host");
		        	port = Integer.parseInt(jedis.get(COMMON_PREFIX+"paf_port"));
		        }
				logger.info("目标 HOST[" + host + "]");
				logger.info("目标 PORT[" + port + "]");
				socket = new Socket(host, port);
				socket.setSoTimeout(10000);
				os = socket.getOutputStream();
				os.write(reqHead.getBytes(PAF.ENCODING));
				int len = reqPack.length;
				logger.info("发送往账报文长度[" + len + "]");
				byte[] byteLens = ByteUtils.intToBytes2(len);
				os.write(byteLens);
				os.write(reqPack);
				os.flush();
			} catch (Exception e) {
				logger.error("发送往账报文异常", e);
				PafTradeExecuteException e1 = new PafTradeExecuteException(PafTradeExecuteException.PAF_E_10004);
				logger.error(e1.getRspCode() + " | " + e1.getRspMsg());
				throw e1;
			}

			String repPack = null;
			try {
				is = socket.getInputStream();
				byte[] repHeadByte = new byte[16];
				for (int i = 0; i < 16; ++i) {
					repHeadByte[i] = (byte) is.read();
				}
				String repHead = new String(repHeadByte, PAF.ENCODING);
				logger.info("接收到报文头[" + repHead + "]");

				byte[] repBodyLenByte = new byte[4];
				for (int i = 0; i < 4; ++i) {
					repBodyLenByte[i] = (byte) is.read();
				}
				int repBodyLen = ByteUtils.bytesToInt2(repBodyLenByte, 0);
				logger.info("接收到的报文长度[" + repBodyLen + "]");

				byte[] repBodyByte = new byte[repBodyLen];
				for (int i = 0; i < repBodyLen; ++i) {
					repBodyByte[i] = (byte) is.read();
				}

				byte[] repPackByte = null;
				if ("0".equals(isLogin)) {
					repPackByte = myRefbdcUtil.decrypt(repBodyByte);
				} else {
					repPackByte = repBodyByte;
				}
				repPack = new String(repPackByte, PAF.ENCODING);
			} catch (Exception e) {
				logger.error("接收往账应答报文异常", e);
				PafTradeExecuteException e1 = new PafTradeExecuteException(PafTradeExecuteException.PAF_E_10005);
				logger.error(e1.getRspCode() + " | " + e1.getRspMsg());
				throw e1;
			}
			return repPack;
		} finally {
			try {
				if (os != null)
					os.close();
				if (is != null)
					is.close();
				if (socket != null)
					socket.close();
			} catch (Exception e) {
				logger.error("关闭异常", e);
			}
		}
	}

	private CLI_REP_DATA chanPack(String repPack) throws PafTradeExecuteException {
		CLI_REP_DATA repData = null;
		try {
			String className = "com.fxbank.cap.paf.model.CLI_REP_BDC";
			Class<?> beanClass = Class.forName(className);

			CLI_REP_BDC repBDC = (CLI_REP_BDC) JAXBUtils.unmarshal(repPack.getBytes(PAF.ENCODING), beanClass);
			repData = new CLI_REP_DATA();
			for (FIELD field : repBDC.getHead().getField()) {
				repData.getHead().put(field.getName(), field.getValue());
			}
			if (repBDC.getBody() != null) {
				for (FIELD field : repBDC.getBody().getField()) {
					repData.getBody().put(field.getName(), field.getValue());
				}
				if (repBDC.getBody().getField_list() != null) {
					for (FIELDS_LIST_INNER fieldList : repBDC.getBody().getField_list().getField_list()) {
						for (FIELD field : fieldList.getField()) {
							repData.getBodyList().put(field.getName() + fieldList.getName(), field.getValue());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("解析往账应答报文异常", e);
			PafTradeExecuteException e1 = new PafTradeExecuteException(PafTradeExecuteException.PAF_E_10006);
			logger.error(e1.getRspCode() + " | " + e1.getRspMsg());
			throw e1;
		}
		return repData;
	}

}
