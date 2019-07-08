/**   
* @Title: DP_10000_Tra.java 
* @Package com.fxbank.tpp.bocm.trade.bocm 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年6月3日 上午10:17:47 
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
import com.fxbank.tpp.bocm.model.REP_10000;
import com.fxbank.tpp.bocm.model.REQ_10000;
import com.fxbank.tpp.bocm.nettty.ServerInitializer;

/** 
* @ClassName: DP_10000_Tra 
* @Description: 他代本  交行磁条卡付款转账
* @author YePuLiang
* @date 2019年6月3日 上午10:17:47 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class DP_10000_Tra extends BASE_TEST {
	
	private REQ_10000 req;
	
	@Before
	public void init(){
		req = new REQ_10000();
		super.initReqHeader("10000", req);
	}
	
	@Test
	public void ok() throws Exception {

		
		req.setTxnAmt(190.09d);
		req.setFeeFlg("0");
		req.setFee(0.1d);
		req.setOprFlg("0");
		
		//业务模式  0现金  1转账
		req.setTxnMod("1");
		
		//本行卡付款转账
		//付款人开户行号
		req.setPayBnk("301100000015");
		//付款人账户类型
		req.setPactTp("2");
		//付款人账号
		req.setPactNo("6288880210000209903");
		//付款人名称
		req.setPayNam("交行账户");
		
		//收款人开户行号
		req.setRecBnk("313229000442");
		//收款人账户类型
		req.setRactTp("2");
		//收款人账号 6288880210000209903 622126010001048643
		req.setRactNo("623166001016830991");
		//收款人名称
		req.setRecNam("测试");
		
		req.setSecMag("623166001016830991=4912567019123456");
		
		
		
		
		
//		req.setTtxnDat(20190531);
//		req.setTtxnTim(82520);
//		req.setSlogNo("19053199440952");
		
		
		String repData = super.comm(FixedUtil.toFixed(req,ServerInitializer.CODING));
		REP_10000 rep = new REP_10000();
		rep = (REP_10000)new FixedUtil(repData,ServerInitializer.CODING).toBean(rep.getClass());		
		assertEquals(rep.getTmsgTyp(), "N");
		assertEquals(rep.getTrspCd(), "FX0000");
	}
	
}
