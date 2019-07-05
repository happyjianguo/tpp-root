/**   
* @Title: CHK_10103.java 
* @Package com.fxbank.tpp.bocm.trade.bocm 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月10日 上午8:16:10 
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
import com.fxbank.tpp.bocm.model.REP_10103;
import com.fxbank.tpp.bocm.model.REQ_10103;

/** 
* @ClassName: CHK_10103 
* @Description: 交行请求对账测试
* @author YePuLiang
* @date 2019年5月10日 上午8:16:10 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CHK_10103 extends BASE_TEST {
	
	private REQ_10103 req;
	
	@Before
	public void init(){
		req = new REQ_10103();
		super.initReqHeader("10103", req);
	}
	
	@Test
	public void ok() throws Exception {

		
		req.setFilNam("BUPS31322900000820190704.dat");
		
		String repData = super.comm(FixedUtil.toFixed(req,BASE_TEST.CODING));
		REP_10103 rep = new REP_10103();
		rep = (REP_10103)new FixedUtil(repData,BASE_TEST.CODING).toBean(rep.getClass());		
		assertEquals(rep.getTmsgTyp(), "N");
		assertEquals(rep.getTrspCd(), "FX0000");
	}

}
