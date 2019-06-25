/**   
* @Title: QR_20102.java 
* @Package com.fxbank.tpp.bocm.trade.bocm 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年6月14日 上午8:39:55 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.trade.bocm;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.tpp.bocm.model.REP_20102;
import com.fxbank.tpp.bocm.model.REQ_10102;
import com.fxbank.tpp.esb.service.ISafeService;

/** 
* @ClassName: QR_20102 
* @Description: 账户信息查询
* @author YePuLiang
* @date 2019年6月14日 上午8:39:55 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class QR_10102_TEST extends BASE_TEST {
	
private static Logger logger = LoggerFactory.getLogger(WD_10001_TEST.class);
	
	@Resource
	private LogPool logPool;
	
	private REQ_10102 req;
	
	@Reference(version = "1.0.0")
	private ISafeService passwordService;
	
	@Before
	public void init(){
		req = new REQ_10102();
		super.initReqHeader("10102", req);
	}
	
	@Test
	public void ok() throws Exception {
		//查询账户信息
		req.setActNo("623166001016830991");
		//00 存款01 取款02 转出03 转入	
		req.setActTyp("2");
		req.setTxnTyp("00");
		//0 账户内扣  1现金外收
		req.setFeeFlg("0");

		
		String repData = super.comm(FixedUtil.toFixed(req,"UTF-8"));
		REP_20102 rep = new REP_20102();
		rep = (REP_20102)new FixedUtil(repData,"UTF-8").toBean(rep.getClass());		
		assertEquals(rep.getTmsgTyp(), "N");
		assertEquals(rep.getTrspCd(), "FX0000");
		
		
	}
	

	

	
}
