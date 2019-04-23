package com.fxbank.tpp.mivs.trade;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.model.CCMS_990_001_02;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Ccms990Test {

	private static Logger logger = LoggerFactory.getLogger(Ccms990Test.class);

	@Reference(version = "1.0.0")
	private IForwardToPmtsService pmtsService;

	@Before
	public void init() {
	}

	@Test
	public void payOk() throws Exception {
		CCMS_990_001_02 mivs = new CCMS_990_001_02(new MyLog(), 20190909, 122321, 12);
		mivs.getHeader().setOrigSender("313131000008"); // TODO 通过机构号查询渠道接口获取（机构号查行号）
		mivs.getHeader().setOrigReceiver("0000");
		mivs.getComConf().getConfInf().setMT("MT");
		mivs.getComConf().getConfInf().setMsgId("msgId");
		mivs.getComConf().getConfInf().setMsgPrcCd("msgPrcCd");
		mivs.getComConf().getConfInf().setMsgRefId("msgRefId");
		mivs.getComConf().getConfInf().setOrigSndDt("20190909");
		mivs.getComConf().getConfInf().setOrigSndr("origSndr");

		try {
			pmtsService.sendToPmts(mivs);
		} catch (SysTradeExecuteException e) {
			e.printStackTrace();
		}
	}
}