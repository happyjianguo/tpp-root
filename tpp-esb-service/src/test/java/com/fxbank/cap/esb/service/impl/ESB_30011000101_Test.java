package com.fxbank.cap.esb.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fxbank.cap.esb.model.ses.ESB_REP_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30011000101;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ESB_30011000101_Test {

	private MyLog myLog;
	private Integer sysDate;
	private Integer sysTime;
	private Integer sysTraceno;
	
	@Autowired
    private ForwardToESBService forwardToESBService;
	
	@Before
	public void init(){
		myLog = new MyLog(UUID.randomUUID().toString(),"UnsignAgreementServiceTest");
		sysDate = Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		sysTime = Integer.valueOf(new SimpleDateFormat("HHmmss").format(new Date()));
		sysTraceno = Math.abs(new Random().nextInt());
	}
	
	@Test
	public void sendToESB() throws SysTradeExecuteException{
		ESB_REQ_30011000101 dto = new ESB_REQ_30011000101(myLog,sysDate,sysTime,sysTraceno);
		ESB_REP_30011000101 ESB_REP_30011000101 = forwardToESBService.sendToESB(dto, dto.getReqBody(), ESB_REP_30011000101.class);
	}
}
