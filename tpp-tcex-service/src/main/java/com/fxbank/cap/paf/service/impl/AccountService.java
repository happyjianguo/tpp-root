package com.fxbank.cap.paf.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.xml.bind.JAXBException;

import com.fxbank.cap.paf.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.paf.constant.PAF;
import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.service.IAccountService;
import com.fxbank.cap.paf.socket.client.ClientSyncSocket;
import com.fxbank.cip.base.log.MyLog;

@Service(validation = "true", version = "1.0.0")
public class AccountService implements IAccountService {
	private static Logger logger = LoggerFactory.getLogger(AccountService.class);

	@Resource
	private ClientSyncSocket clientSyncSocket;


	@Override
	public CLI_REP_DATA notify(CLI_REQ_BDC reqData,String txCode) throws PafTradeExecuteException, JAXBException {
		MyLog mylog = reqData.getMylog();

		CLI_REP_DATA pack = clientSyncSocket.comm(txCode, reqData.getSysDate(), reqData.getSysTime(),
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
