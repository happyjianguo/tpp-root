package com.fxbank.tpp.bocm.trade.bocm;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import com.fxbank.tpp.bocm.model.REP_20000;
import com.fxbank.tpp.bocm.model.REQ_20000;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DP_20000_TEST extends BASE_TEST {
	
	private REQ_20000 req;
	
	@Before
	public void init(){
		req = new REQ_20000();
		super.initReqHeader("20000", req.getHeader());
	}
	
	@Test
	public void ok() throws Exception {
		req.setTxnAmt(new BigDecimal(1000));
		req.setFeeFlg("0");
		req.setFee(new BigDecimal(10));
		req.setOprFlg("0");
		req.setTxnMod("0");
		req.setRecBnk("301651000015");
		req.setrActTp("2");
		req.setrActNo("6222600530011742438");
		req.setRecNam("钱晓勋");
		req.setSeqNo("001");
		req.setaRQC("1111");
		req.setiCAID("A0000003330101");
		req.setiCOutDate("20251231");
		req.setiCData("11111");
		
		String repData = super.comm(req.creaFixPack());
		REP_20000 rep = new REP_20000();
		rep.chanFixPack(repData);
		assertEquals(rep.getHeader().gettMsgTyp(), "N");
		assertEquals(rep.getHeader().gettRspCd(), "FX0000");
	}
	
}
