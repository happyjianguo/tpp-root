package com.fxbank.tpp.tcex.nettty.client;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fxbank.tpp.tcex.utils.JAXBUtils;
import com.fxbank.tpp.tcex.utils.StringUtils;

import io.netty.channel.socket.SocketChannel;

@Component
public class ClientSyncNetty {
	
	private static Logger logger = LoggerFactory.getLogger(ClientSyncNetty.class);

	private static final String NODEID = "123456";
	
	@Resource
	private ClientConfig client;
	
	private ConcurrentHashMap<Integer,ClientSyncFuture<String>> syncMap = new ConcurrentHashMap<Integer,ClientSyncFuture<String>>();
	
	public String comm(String txCode,String isLogin,Integer sysDate,Integer sysTime,Integer sysTraceno,String reqData) throws JAXBException, UnsupportedEncodingException {
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
		ClientSyncFuture<String> future = new ClientSyncFuture<String>();
		syncMap.put(sysTraceno, future);
		socketChannel.writeAndFlush(packHeader.toString());
		
		//同步等待
		String repData = null;
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

	public ConcurrentHashMap<Integer, ClientSyncFuture<String>> getSyncMap() {
		return syncMap;
	}
	
}
