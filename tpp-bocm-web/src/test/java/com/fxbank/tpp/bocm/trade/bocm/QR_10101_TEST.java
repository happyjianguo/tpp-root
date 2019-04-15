package com.fxbank.tpp.bocm.trade.bocm;

import static org.junit.Assert.assertEquals;

import com.fxbank.tpp.bocm.model.REP_10101;
import com.fxbank.tpp.bocm.model.REQ_10101;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QR_10101_TEST extends BASE_TEST {
	
	private REQ_10101 req;
	
	@Before
	public void init(){
		req = new REQ_10101();
		super.initReqHeader("10101", req.getHeader());
	}
	
	@Test
	public void ok() throws Exception {
		req.setActTyp("2");
		req.setActNo("62316600000018");
		req.setPin("0123456789ABCDEF");
		String repData = super.comm(req.creaFixPack());
		REP_10101 rep = new REP_10101();
		rep.chanFixPack(repData);
		assertEquals(rep.getHeader().gettMsgTyp(), "N");
		assertEquals(rep.getHeader().gettRspCd(), "FX0000");
	}
	
}
