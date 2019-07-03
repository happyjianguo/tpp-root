package com.fxbank.tpp.bocm.trade.bocm;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.tpp.bocm.model.REP_10000;
import com.fxbank.tpp.bocm.model.REP_BASE;
import com.fxbank.tpp.bocm.model.REP_ERROR;
import com.fxbank.tpp.bocm.model.REQ_10000;
import com.fxbank.tpp.bocm.nettty.ServerInitializer;
import com.fxbank.tpp.bocm.util.NumberUtil;

/**
* @ClassName: DP_10000_TEST 
* @Description: 他代本现金通存
* @author YePuLiang
* @date 2019年5月17日 上午10:41:59 
*
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DP_10000_TEST extends BASE_TEST {
	
	private static Logger logger = LoggerFactory.getLogger(DP_10000_TEST.class);
	
	private REQ_10000 req;
	
	@Before
	public void init(){
		req = new REQ_10000();
		super.initReqHeader("10000", req);
	}
	
	@Test
	public void ok() throws Exception {
		
		req.setTxnAmt(1000d);
		req.setFeeFlg("0");
		req.setFee(100d);
		req.setOprFlg("0");		
		req.setTxnMod("0");	
		//收款人开户行号
		req.setRecBnk("313229000442");
		//收款人账户类型
		req.setRactTp("2");
		//收款人账号 6288880210000209903 622126010001048643
		//UAT
//		req.setRactNo("623166001016830991");
		//DEV
		req.setRactNo("623166000001058980");
		req.setCuIdTp("15");
		req.setCuIdNo("1234");
		//收款人名称
		req.setRecNam("测试");		
		req.setSecMag("623166099020908241=4912567019123456");
//		req.setTtxnDat(20190701);
//		req.setTtxnTim(145033);
//		req.setSlogNo("19070159840880");		
		REP_10000 rep = new REP_10000();
		String repData = super.comm(FixedUtil.toFixed(req,ServerInitializer.CODING));
		logger.info("他代本存现金测试返回："+repData);
		REP_BASE repBase = new REP_ERROR();
		if(repData.substring(0, 1).equals("N")){		
			rep = (REP_10000)new FixedUtil(repData,ServerInitializer.CODING).toBean(rep.getClass());	
			rep = (REP_10000)new FixedUtil(repData,ServerInitializer.CODING).toBean(rep.getClass());		
			assertEquals(rep.getTmsgTyp(), "N");
			assertEquals(rep.getTrspCd(), "FX0000");
		}else{
			repBase = (REP_ERROR)new FixedUtil(repData,ServerInitializer.CODING).toBean(REP_ERROR.class);
			logger.info("相应错误码【" + repBase.getTrspCd() + "】");
			logger.info("错误描述【" + repBase.getTrspMsg() + "】");
			assertEquals(rep.getTmsgTyp(), "N");
		}

	}
	
}
