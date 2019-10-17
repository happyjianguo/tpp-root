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
		double txAmt = 501d;
		req.setTxnAmt(NumberUtil.addPoint(txAmt));
		req.setPin("2CD56A5DC9427881");
		req.setOprFlg("0");
		req.setTxnMod("1");


		req.setRactNo("6222622520000173491");
		req.setRecNam("张秀秀");
		req.setRactTp("2");
		req.setRecBnk("301229000026");
		
		req.setPactNo("623166000000085273");
		req.setPayNam("翟淼");
		req.setPayBnk("313229000137");
		req.setPactTp("2");


		req.setSeqNo("001");
		req.setARQC("08B5C0D43ED647BA909F");
		req.setICAID("A0000003330101");
		req.setICOutDate("20241128");
		req.setICData("9F2608B5C0D43ED647BA909F2701809F101307020103A0B000010A0100000000004322884C9F37042397C3D49F360200AB950500000000009A031907299C01309F02060000000000005F2A02015682027C009F1A0201569F03060000000000009F33030000009F34033F0000");		
		
		String repData = super.comm(FixedUtil.toFixed(req,BASE_TEST.CODING));
		REP_20001 rep = new REP_20001();
		rep = (REP_20001)new FixedUtil(repData,BASE_TEST.CODING).toBean(rep.getClass());		
		assertEquals(rep.getTmsgTyp(), "N");
		assertEquals(rep.getTrspCd(), "FX0000");
	}
	
}
