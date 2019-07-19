package com.fxbank.tpp.bocm.trade.bocm;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.tpp.bocm.dto.bocm.REP_20001;
import com.fxbank.tpp.bocm.model.REQ_20001;
import com.fxbank.tpp.bocm.util.NumberUtil;

/** 
* @ClassName: WD_20000_Tra 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author YePuLiang
* @date 2019年6月3日 下午2:20:37 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class WD_20001_Tra extends BASE_TEST {
	
	private REQ_20001 req;
	
	@Before
	public void init(){
		req = new REQ_20001();
		super.initReqHeader("20001", req);
	}
	
	@Test
	public void ok() throws Exception {
		double txAmt = 10d;
		req.setTxnAmt(NumberUtil.addPoint(txAmt));
		req.setPin("CB86B297E3814EE8");
		req.setOprFlg("0");
		req.setTxnMod("0");
		req.setRecBnk("301290050029");
		req.setRactTp("2");
//		req.setrActNo("6222600530011742438");
		
		req.setPactNo("6222600110063573484");
		req.setPayNam("朱倞杰");
//		req.setSecMag("622126010001048643=4912567019123456");

		req.setSeqNo("001");
		req.setARQC("0822432DD009F009E89F");
		req.setICAID("A0000003330101");
		req.setICOutDate("241231");
		req.setICData("9F260822432DD009F009E89F2701809F101307020103A0B000010A01000000000038B9875C9F37042E2518AB9F36020069950500000000009A031907189C01309F02060000000000005F2A02015682027C009F1A0201569F03060000000000009F33030000009F34033F0000");
		//
		
//		req.setPactNo("623166001016830991");
//		req.setPayBnk("301651000015");
//		req.setPactTp("2");
//		req.setRecNam("测试");
//		req.setSecMag("622126010001048643=4912567019123456");

//		req.setSeqNo("001");
//		req.setARQC("1111");
//		req.setICAID("A0000003330101");
//		req.setICOutDate("20251231");
//		req.setICData("11111");
		

		req.setRactNo("623166000000085273");
		req.setRecNam("翟淼");
		req.setRactTp("2");
		req.setRecBnk("313229000137");
		
//		//收款人开户行号
//		req.setRecBnk("313229000442");
//		//收款人账户类型
//		req.setRactTp("2");
//		//收款人账号 6288880210000209903 622126010001048643
//		req.setRactNo("623166001016830991");
//		//收款人名称
//		req.setRecNam("测试");
//		
//		req.setSecMag("623166001016830991=4912567019123456");
		
		
		String repData = super.comm(FixedUtil.toFixed(req,BASE_TEST.CODING));
		REP_20001 rep = new REP_20001();
		rep = (REP_20001)new FixedUtil(repData,BASE_TEST.CODING).toBean(rep.getClass());		
		assertEquals(rep.getTmsgTyp(), "N");
		assertEquals(rep.getTrspCd(), "FX0000");
	}
	
}
