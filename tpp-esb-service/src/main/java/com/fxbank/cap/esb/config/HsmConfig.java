package com.fxbank.cap.esb.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.highsuccess.connPool.api.tssc.HisuTSSCAPI;

@Configuration
@ConditionalOnProperty(name = "spring.hsm.enable", havingValue = "true")
@ConfigurationProperties(prefix = "spring.hsm")
public class HsmConfig {

	private String ip;
	private int port;
	private int connTimeOut;
	private int hsmMsgLen;
	private int connNum;
	private int connType;
	private String appID;

	@Bean
	public HisuTSSCAPI getHisuTSSCAPI(){
		return new HisuTSSCAPI( ip,  port,  connTimeOut,  hsmMsgLen,  connNum,  connType,  appID);
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getConnTimeOut() {
		return connTimeOut;
	}

	public void setConnTimeOut(int connTimeOut) {
		this.connTimeOut = connTimeOut;
	}

	public int getHsmMsgLen() {
		return hsmMsgLen;
	}

	public void setHsmMsgLen(int hsmMsgLen) {
		this.hsmMsgLen = hsmMsgLen;
	}

	public int getConnNum() {
		return connNum;
	}

	public void setConnNum(int connNum) {
		this.connNum = connNum;
	}

	public int getConnType() {
		return connType;
	}

	public void setConnType(int connType) {
		this.connType = connType;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

}
