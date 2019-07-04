/**   
* @Title: WD_10001_Tra.java 
* @Package com.fxbank.tpp.bocm.trade.bocm 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年6月3日 上午11:30:01 
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
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.tpp.bocm.model.REP_10001;
import com.fxbank.tpp.bocm.model.REQ_10001;
import com.fxbank.tpp.esb.service.ISafeService;

/** 
* @ClassName: WD_10001_Tra 
* @Description: 他代本   本行卡付款转账
* @author YePuLiang
* @date 2019年6月3日 上午11:30:01 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class WD_10001_Tra extends BASE_TEST {
	
private static Logger logger = LoggerFactory.getLogger(WD_10001_TEST.class);
	
	@Resource
	private LogPool logPool;
	
	private REQ_10001 req;
	
	@Reference(version = "1.0.0")
	private ISafeService passwordService;
	
	@Before
	public void init(){
		req = new REQ_10001();
		super.initReqHeader("10001", req);
	}
	
	@Test
	public void ok() throws Exception {

		//交行卡付款转账
		req = createMagTraReqBody();
		
		
		String repData = super.comm(FixedUtil.toFixed(req,BASE_TEST.CODING));
		REP_10001 rep = new REP_10001();
		rep = (REP_10001)new FixedUtil(repData,BASE_TEST.CODING).toBean(rep.getClass());		
		assertEquals(rep.getTmsgTyp(), "N");
		assertEquals(rep.getTrspCd(), "FX0000");
		
	}
	
	
	private REQ_10001 createMagTraReqBody() throws SysTradeExecuteException{
		//6.2.3.1	磁条卡的通兑10001
		req.setTxnAmt(10d);
		//卡输入方式
		req.setOprFlg("0");
		//业务模式 0现金
		req.setTxnMod("1");
				
		//付款人开户行号
		req.setPayBnk("313229000442");
		//付款人账户类型
		req.setPactTp("2");
		//付款人账号
		req.setPactNo("623166001016830991");
		req.setSecMag("623166001016830991=4912567019123456");
		//付款人名称
		req.setPayNam("测试");
		//付款人密码
//		SafeModel passwordModel = new SafeModel(logPool.get(), 20181226, 10000,
//				1111);
//		passwordModel.setAcctNo("623166001016830991");
//		passwordModel.setPassword("147258");
//		passwordModel = passwordService.encryptPwd(passwordModel);
//		req.setPin(passwordModel.getPassword());
//		
//		logger.info(req.getPin());
		
		req.setPin("147258");
		
		//收款人开户行号
		req.setRecBnk("301100000015");
		//收款人账户类型
		req.setRactTp("0");
		//收款人账号 6288880210000209903 622126010001048643
		req.setRactNo("6288880210000209903");
		//收款人名称
		req.setRecNam("ZZZ");
		
		return req;
	}
	
}
