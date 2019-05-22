package com.fxbank.tpp.bocm.trade.bocm;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import com.fxbank.tpp.bocm.model.REP_10000;
import com.fxbank.tpp.bocm.model.REQ_10000;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
* @ClassName: DP_10000_TEST 
* @Description: 磁条卡通存测试
* @author YePuLiang
* @date 2019年5月17日 上午10:41:59 
*
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DP_10000_TEST extends BASE_TEST {
	
	private REQ_10000 req;
	
	@Before
	public void init(){
		req = new REQ_10000();
		super.initReqHeader("10000", req.getHeader());
	}
	
	@Test
	public void ok() throws Exception {

		
		req.setTxnAmt(new BigDecimal(1000));
		req.setFeeFlg("0");
		req.setFee(new BigDecimal(0));
		req.setOprFlg("0");
		//业务模式  0现金  1转账
		req.setTxnMod("1");
		
		//本行卡付款转账
		//付款人开户行号
		req.setPayBnk("04002");
		//付款人账户类型
		req.setpActTp("2");
		//付款人账号
		req.setpActNo("623166001016830991");
		//付款人名称
		req.setPayNam("测试");
		
		//收款人开户行号
		req.setRecBnk("623166000009");
		//收款人账户类型
		req.setrActTp("2");
		//收款人账号 6288880210000209903 622126010001048643
		req.setrActNo("6288880210000209903");
		//收款人名称
		req.setRecNam("ZZZ");
		
		req.setSecMag("623166001016830991=4912567019123456");
		
		
		String repData = super.comm(req.creaFixPack());
		REP_10000 rep = new REP_10000();
		rep.chanFixPack(repData);
		assertEquals(rep.getHeader().gettMsgTyp(), "N");
		assertEquals(rep.getHeader().gettRspCd(), "FX0000");
	}
	
}
