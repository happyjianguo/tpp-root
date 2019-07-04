package com.fxbank.tpp.bocm.trade.bocm;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.tpp.bocm.model.REP_10104_MAC;
import com.fxbank.tpp.bocm.model.REQ_10104;



/** 
* @ClassName: QR_10104 
* @Description: 工作密钥申请测试
* @author YePuLiang
* @date 2019年5月23日 下午3:39:22 
*  
*/

@RunWith(SpringRunner.class)
@SpringBootTest
public class QR_10104_TEST extends BASE_TEST {
	
	private REQ_10104 req;
	
	@Before
	public void init(){
		req = new REQ_10104();
		super.initReqHeader("10104", req);
	}
	
	@Test
	public void ok() throws Exception {
		req.setKeyId("RZAK");
		//密钥类型  0 zpk （Pin key） 1 zak （MAC key先申请）
		req.setKeyTyp(1);
		//密钥长度  16 byt	32 byt
		req.setKeyLen(1);

		
		
		String repData = super.comm(FixedUtil.toFixed(req,BASE_TEST.CODING));
		REP_10104_MAC rep = new REP_10104_MAC();
		rep = (REP_10104_MAC)new FixedUtil(repData,BASE_TEST.CODING).toBean(rep.getClass());		
		assertEquals(rep.getTmsgTyp(), "N");
		assertEquals(rep.getTrspCd(), "FX0000");
		
	}

}
