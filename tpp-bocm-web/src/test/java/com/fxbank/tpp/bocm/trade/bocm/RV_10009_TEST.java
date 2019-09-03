/**   
* @Title: RV_10009.java 
* @Package com.fxbank.tpp.bocm.trade.bocm 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年7月3日 上午11:32:28 
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
import com.fxbank.tpp.bocm.model.BocmRcvTraceQueryModel;
import com.fxbank.tpp.bocm.model.REP_10000;
import com.fxbank.tpp.bocm.model.REP_10009;
import com.fxbank.tpp.bocm.model.REQ_10009;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.bocm.trade.esb.CHK_HostTest;

/** 
* @ClassName: RV_10009 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author YePuLiang
* @date 2019年7月3日 上午11:32:28 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RV_10009_TEST extends BASE_TEST {
	
	private static Logger logger = LoggerFactory.getLogger(RV_10009_TEST.class);
	
	private REQ_10009 req;
	
	@Reference(version = "1.0.0")
	private IBocmRcvTraceService bocmRcvTraceService;
	
	@Resource
	private LogPool logPool;
	
	@Before
	public void init(){
		req = new REQ_10009();
		super.initReqHeader("10009", req);
	}
	
	@Test
	public void ok() throws Exception {

		BocmRcvTraceQueryModel model = bocmRcvTraceService.getConfirmTrace(logPool.get(), 20200407, "19080722371588");
		logger.info("交易模型：  "+model);
		logger.info("交易模型：  "+model.getPlatDate()+"   "+model.getPlatTrace());
		logger.info("交行流水：  "+model.getBocmTraceno());
		//原交易流水
		String ologNo = model.getBocmTraceno();
		
		req.setOlogNo(ologNo);
		//原交易码
		req.setOtxnCd(model.getTxCode());
		//交易金额
		req.setTxnAmt(Double.parseDouble(model.getTxAmt().toString()));
		//业务模式
		req.setTxnMod("0");
		//付款开户行号
		req.setPayBnk(model.getPayerBank());
		//付款人账号类型
		req.setPactTp(model.getPayerActtp());
		logger.info("交行流水：  "+req.getOlogNo()+"|"+model.getBocmTraceno());
		
		String repData = super.comm(FixedUtil.toFixed(req,BASE_TEST.CODING));
		REP_10009 rep = new REP_10009();
		rep = (REP_10009)new FixedUtil(repData,BASE_TEST.CODING).toBean(rep.getClass());		
		assertEquals(rep.getTmsgTyp(), "N");
		assertEquals(rep.getTrspCd(), "FX0000");
	}
}
