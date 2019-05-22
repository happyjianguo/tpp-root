package com.fxbank.tpp.bocm.trade.bocm;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

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
import com.fxbank.tpp.bocm.model.REP_10001;
import com.fxbank.tpp.bocm.model.REQ_10001;
import com.fxbank.tpp.esb.model.tcex.SafeModel;
import com.fxbank.tpp.esb.service.ISafeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WD_10001_TEST extends BASE_TEST {
	
private static Logger logger = LoggerFactory.getLogger(WD_10001_TEST.class);
	
	@Resource
	private LogPool logPool;
	
	private REQ_10001 req;
	
	@Reference(version = "1.0.0")
	private ISafeService passwordService;
	
	@Before
	public void init(){
		req = new REQ_10001();
		super.initReqHeader("10001", req.getHeader());
	}
	
	@Test
	public void ok() throws Exception {
		//现金通兑
		req = createMagCashReqBody();
		//转账
//		req = createMagTraReqBody();
		
		
		System.out.println(req.creaFixPack());
//		return;
		String repData = super.comm(req.creaFixPack());
		REP_10001 rep = new REP_10001();
		rep.chanFixPack(repData);
		assertEquals(rep.getHeader().gettMsgTyp(), "N");
		assertEquals(rep.getHeader().gettRspCd(), "FX0000");
	}
	
	
	private REQ_10001 createMagCashReqBody() throws SysTradeExecuteException{
		//6.2.3.1	磁条卡的通兑10001
		req.setTxnAmt(new BigDecimal(10));
		//卡输入方式
		req.setOprFlg("0");
		//业务模式 0现金
		req.setTxnMod("0");
				
		//付款人开户行号
		req.setPayBnk("04002");
		//付款人账户类型
		req.setpActTp("2");
		//付款人账号
		req.setpActNo("623166001016830991");
		//付款人名称
		req.setPayNam("测试");
		//付款人密码
//		SafeModel passwordModel = new SafeModel(logPool.get(), 20181226, 10000,
//				1111);
//		passwordModel.setAcctNo("623166001016830991");
//		passwordModel.setPassword("147258");
//		passwordModel = passwordService.encryptPwd(passwordModel);
//		req.setPin(passwordModel.getPassword());
//		passwordModel.setPassword("147258");
		req.setPin("147258");
		return req;
	}
	
	
	private REQ_10001 createMagTraReqBody() throws SysTradeExecuteException{
		//6.2.3.1	磁条卡的通兑10001
		req.setTxnAmt(new BigDecimal(10));
		//卡输入方式
		req.setOprFlg("0");
		//业务模式 0现金
		req.setTxnMod("1");
				
		//付款人开户行号
		req.setPayBnk("04002");
		//付款人账户类型
		req.setpActTp("2");
		//付款人账号
		req.setpActNo("623166001016830991");
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
		req.setRecBnk("623166000009");
		//收款人账户类型
		req.setrActTp("2");
		//收款人账号 6288880210000209903 622126010001048643
		req.setrActNo("6288880210000209903");
		//收款人名称
		req.setRecNam("ZZZ");
		
		return req;
	}
	
}
