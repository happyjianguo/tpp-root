package com.fxbank.cap.simu.trade;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.tpp.esb.service.IForwardToESBService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EsbSimuTest {
	
	private static Logger logger = LoggerFactory.getLogger(EsbSimuTest.class);
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	String ESB_URL = "";

	@Before
	public void init() throws UnknownHostException, IOException, SysTradeExecuteException {
	    /*
		try (Jedis jedis = myJedis.connect()){
			ESB_URL = jedis.get("ecc_common.esb_cip_url");
			jedis.set("ecc_common.esb_cip_url", "http://127.0.0.1:7028/cip/simu.do");
		}
		*/
	}
	
	
	@Test
	public void EsbTest() throws JAXBException, IOException, SysTradeExecuteException {
		logger.info("测试");
	}
	
	
	
	@After
	public void destroy() throws IOException {
	    /*
		try (Jedis jedis = myJedis.connect()){
			jedis.set("ecc_common.esb_cip_url", ESB_URL);
		}
		*/
	}
}
