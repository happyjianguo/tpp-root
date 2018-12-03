package com.fxbank.cap.paf.nettty.client;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fxbank.cap.paf.model.CLI_REP_DATA;
import com.fxbank.cap.paf.model.CLI_REQ_BDC;
import com.fxbank.cap.paf.utils.JAXBUtils;
import com.fxbank.cap.paf.utils.StringUtils;

import io.netty.channel.socket.SocketChannel;

@Component
public class ClientSyncNetty {
	
	private static Logger logger = LoggerFactory.getLogger(ClientSyncNetty.class);

	private static final String NODEID = "123456";
	
	@Resource
	private ClientConfig client;
	
	private ConcurrentHashMap<Integer,ClientSyncFuture<CLI_REP_DATA>> syncMap = new ConcurrentHashMap<Integer,ClientSyncFuture<CLI_REP_DATA>>();
	
	public CLI_REP_DATA comm(String txCode,String isLogin,Integer sysDate,Integer sysTime,Integer sysTraceno,CLI_REQ_BDC reqData) throws JAXBException, UnsupportedEncodingException {
		StringBuffer packHeader = new StringBuffer();
		
		//组成head
		packHeader.append(NODEID);
		packHeader.append(isLogin);
		packHeader.append(StringUtils.fillStringAppend(txCode,7,"\0"));
		packHeader.append(StringUtils.fillStringAppend("",2,"\0"));
		
		//组成xml Head
		//TODO
		byte[] bs = JAXBUtils.marshal(reqData);
		
		//通讯
		SocketChannel socketChannel = client.connect("127.0.0.1",6000);
		packHeader.append(new String(bs,"UTF-8"));
		ClientSyncFuture<CLI_REP_DATA> future = new ClientSyncFuture<CLI_REP_DATA>();
		syncMap.put(sysTraceno, future);
		socketChannel.writeAndFlush(packHeader.toString());
		
		//同步等待
		CLI_REP_DATA repData = null;
		try {
			repData = future.get(30, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.error("客户端等待超时",e);
		}finally{
			syncMap.remove(sysTraceno);
			logger.info("同步MAP中元素个数["+syncMap.size()+"]");
		}
        return repData;
	}

	public ConcurrentHashMap<Integer, ClientSyncFuture<CLI_REP_DATA>> getSyncMap() {
		return syncMap;
	}
	
}
