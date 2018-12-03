package com.fxbank.cap.paf.service.impl;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.paf.common.MyRefbdcUtil;
import com.fxbank.cap.paf.constant.PAF;
import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.model.CLI_REP_DATA;
import com.fxbank.cap.paf.model.CLI_REQ_BDC;
import com.fxbank.cap.paf.service.ILoginoutService;
import com.fxbank.cap.paf.socket.client.ClientSyncSocket;
import com.fxbank.cip.base.log.MyLog;

@Service(validation = "true", version = "1.0.0")
public class LoginoutService implements ILoginoutService {

	private static Logger logger = LoggerFactory.getLogger(LoginoutService.class);

	@Resource
	private ClientSyncSocket clientSyncSocket;
	
	@Resource
	private MyRefbdcUtil myRefbdcUtil;
	
	@Override
	public CLI_REP_DATA login(CLI_REQ_BDC reqData) throws PafTradeExecuteException, JAXBException {
		MyLog mylog = reqData.getMylog();

		CLI_REP_DATA pack = clientSyncSocket.comm("BDC001", reqData.getSysDate(), reqData.getSysTime(),
				reqData.getSysTraceno(), PAF.CENTER_CODE, reqData);
		mylog.info(logger, "通讯成功");

		// 解析应答
		Map<String, Object> head = pack.getHead();
		if (head != null) {
			for (Entry<String, Object> e : head.entrySet()) {
				System.out.println("HEAD| " + e.getKey() + " || " + e.getValue());
			}
		}

		String encryptSessionKey = null;
		Map<String, Object> body = pack.getBody();
		if (body != null) {
			for (Entry<String, Object> e : body.entrySet()) {
				System.out.println("BODY| " + e.getKey() + " || " + e.getValue());
				if (e.getKey().equals("HandKey")) {
					encryptSessionKey = (String) e.getValue();
				}
			}
		}

		try {
			myRefbdcUtil.saveSessionKey(encryptSessionKey);
			System.out.println("保存密钥成功！！！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pack;
	}

	@Override
	public CLI_REP_DATA logout(CLI_REQ_BDC reqData) throws PafTradeExecuteException, JAXBException {
		MyLog mylog = reqData.getMylog();

		CLI_REP_DATA pack = clientSyncSocket.comm("BDC002", reqData.getSysDate(), reqData.getSysTime(),
				reqData.getSysTraceno(), PAF.CENTER_CODE, reqData);
		mylog.info(logger, "通讯成功");

		// 解析应答
		Map<String, Object> head = pack.getHead();
		if (head != null) {
			for (Entry<String, Object> e : head.entrySet()) {
				System.out.println("HEAD| " + e.getKey() + " || " + e.getValue());
			}
		}

		Map<String, Object> body = pack.getBody();
		if (body != null) {
			for (Entry<String, Object> e : body.entrySet()) {
				System.out.println("BODY| " + e.getKey() + " || " + e.getValue());
			}
		}

		return pack;
	}
}
