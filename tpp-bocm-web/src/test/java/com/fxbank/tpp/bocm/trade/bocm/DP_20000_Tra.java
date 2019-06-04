/**   
* @Title: DP_20000_Tra.java 
* @Package com.fxbank.tpp.bocm.trade.bocm 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年6月3日 上午10:42:53 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.trade.bocm;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.tpp.bocm.model.REP_20000;
import com.fxbank.tpp.bocm.model.REQ_20000;

/** 
* @ClassName: DP_20000_Tra 
* @Description: 交行IC卡付款转账
* @author YePuLiang
* @date 2019年6月3日 上午10:42:53 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class DP_20000_Tra extends BASE_TEST {
	
	private REQ_20000 req;
	
	@Before
	public void init(){
		req = new REQ_20000();
		super.initReqHeader("20000", req);
	}
	
	@Test
	public void ok() throws Exception {
		req.setTxnAmt(90.87d);
		req.setFeeFlg("0");
		req.setFee(0.1d);
		req.setOprFlg("0");
		
		//业务模式  0现金  1转账
		req.setTxnMod("1");
		
		//付款人开户行号
		req.setPayBnk("301100000015");
		//付款人账户类型
		req.setPactTp("2");
		//付款人账号
		req.setPactNo("6288880210000209903");
		//付款人名称
		req.setPayNam("ZZZ");
		
		//收款人开户行号
		req.setRecBnk("313229000442");
		//收款人账户类型
		req.setRactTp("0");
		//收款人账号 6288880210000209903 622126010001048643
		req.setRactNo("623166001016830991");
		//收款人名称
		req.setRecNam("测试");
		
		req.setSeqNo("001");
		req.setARQC("1111");
		req.setICAID("A0000003330101");
		req.setICOutDate("20251231");
		req.setICData("9F260879CC8EC5A09FB9479F2701809F100807010199A0B806019F3704000000009F360201C2950500001800009A031205089C01609F02060000000000005F2A02015682027D009F1A0201569F03060000000000009F3303E0F0F09F34036003029F3501119F1E0832303033313233318405FFFFFFFFFF9F09022");
		
		
		String repData = super.comm(FixedUtil.toFixed(req,"UTF-8"));
		REP_20000 rep = new REP_20000();
		rep = (REP_20000)new FixedUtil(repData,"UTF-8").toBean(rep.getClass());		
		assertEquals(rep.getTmsgTyp(), "N");
		assertEquals(rep.getTrspCd(), "FX0000");
		

	}
}
