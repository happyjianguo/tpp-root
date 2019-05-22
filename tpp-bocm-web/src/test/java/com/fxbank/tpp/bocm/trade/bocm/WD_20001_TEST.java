package com.fxbank.tpp.bocm.trade.bocm;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fxbank.tpp.bocm.model.REP_10001;
import com.fxbank.tpp.bocm.model.REQ_10001;
import com.fxbank.tpp.bocm.model.REQ_20001;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WD_20001_TEST extends BASE_TEST {
	
	private REQ_20001 req;
	
	@Before
	public void init(){
		req = new REQ_20001();
		super.initReqHeader("20001", req.getHeader());
	}
	
	@Test
	public void ok() throws Exception {
		req.setTxnAmt(new BigDecimal(1000));
		req.setPin("0123456789ABCDEF");
		req.setOprFlg("0");
		req.setTxnMod("0");
		req.setRecBnk("301651000015");
		req.setrActTp("2");
//		req.setrActNo("6222600530011742438");
		
		req.setrActNo("622126010001048643");
		req.setRecNam("测试");
//		req.setSecMag("622126010001048643=4912567019123456");

		req.setSeqNo("001");
		req.setaRQC("1111");
		req.setiCAID("A0000003330101");
		req.setiCOutDate("20251231");
		req.setiCData("11111");
		
//		req.setRecNam("钱晓勋");
//		req.setSecMag("6222600530011742438=4912120343981195");
//		req.setThdMag("996222600530011742438=1561560500050002116013000000010000000000==0981195343");
		
		String repData = super.comm(req.creaFixPack());
		REP_10001 rep = new REP_10001();
		rep.chanFixPack(repData);
		assertEquals(rep.getHeader().gettMsgTyp(), "N");
		assertEquals(rep.getHeader().gettRspCd(), "FX0000");
	}
	
}
