/**   
* @Title: WD_BocmTra.java 
* @Package com.fxbank.tpp.bocm.trade.esb 
* @Description: 交行卡付款转账
* @author YePuLiang
* @date 2019年4月23日 上午8:18:48 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.trade.esb;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.tpp.bocm.dto.esb.REP_30061800301;
import com.fxbank.tpp.bocm.dto.esb.REQ_30061800101;
import com.fxbank.tpp.bocm.dto.esb.REQ_30061800101.REQ_BODY;
import com.fxbank.tpp.bocm.dto.esb.REQ_30061800201;
import com.fxbank.tpp.esb.service.ISafeService;

/** 
* @ClassName: WD_BocmTra 
* @Description: 交行卡付款转账
* @author YePuLiang
* @date 2019年4月23日 上午8:18:48 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc	
public class WD_BocmTraTest {
	
	private static Logger logger = LoggerFactory.getLogger(DB_BocmCashTest.class);
	
//	private static final String URL="http://57.25.3.165:8001/tcex/city.do";
     private static final String URL="http://127.0.0.1:7006/esb/bocm.do";

	@Autowired
	private MockMvc mockMvc;
	
	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private ISafeService passwordService;
	
	private REQ_30061800201 req ;
	private REQ_SYS_HEAD reqSysHead;
	private REQ_30061800201.REQ_BODY reqBody ;
	
	@Before
	public void init(){
		req = new REQ_30061800201();
		reqSysHead = new REQ_SYS_HEAD();
		reqSysHead.setServiceId("300618002");
		reqSysHead.setSceneId("01");
		reqSysHead.setSystemId("301907");
		reqSysHead.setTranMode("ONLINE");
		reqSysHead.setSourceType("BOCM");	//网联
//		reqSysHead.setSourceType("302200");	//银联
		reqSysHead.setBranchId("02002");
		reqSysHead.setUserId("002241");
		reqSysHead.setTranDate(String.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date())));
		reqSysHead.setTranTimestamp(String.valueOf(new SimpleDateFormat("HHmmss").format(new Date())));		
		reqSysHead.setUserLang("CHINESE");
		reqSysHead.setSeqNo(String.valueOf(Math.abs(new Random().nextInt())));
		reqSysHead.setSystemId("301907");	//网联
		reqSysHead.setCompany("COMPANY");
		reqSysHead.setSourceBranchNo("SOURCE_BRANCH_NO");
		reqSysHead.setDestBranchNo("DEST_BRANCH_NO");
		reqSysHead.setFilePath("FILE_PATH");
		reqSysHead.setGloabalSeqNo(reqSysHead.getSeqNo());
		reqSysHead.setAuthUserId("999");
		reqBody = req.new REQ_BODY(); 
		req.setReqSysHead(reqSysHead);
		req.setReqBody(reqBody);
	}
	
	@Test
	public void payOk() throws Exception {
		logger.info("交行卡付款转账 ");
		
		
		reqBody.setBnkCardUseCcyT("CNY");
		reqBody.setBcmCardUseCcyT("CNY");
		reqBody.setPyrNaT("付款人姓名");
		reqBody.setRcptPrNmT7("收款人姓名");
		reqBody.setPyOpnBrNoT("01233");//收款人开户机构号
		reqBody.setPyrAcctNoT2("21321");//付款人账号
		reqBody.setRcptPrAcctNoT2("12321");//收款卡人账号
		reqBody.setTrsrAmtT3("100.00");//转账金额
		reqBody.setFeeAmtT3("0.00");//手续费
		reqBody.setBalT("1000.00");//余额
		reqBody.setHndlPymntFeeT5("0.00");//应收手续费
		reqBody.setBnkCardAcctVchrNoT("123123");//本行卡账户凭证号码
		reqBody.setHldrGlblIdT("210222");//证件号码
		reqBody.setPyrAcctTpT("0");//付款人账户类型
		
		

		reqBody.setIcCardFlgT4("0");//IC卡磁条卡标志   0磁条卡  1IC卡
		String macDataStr = JsonUtil.toJson(reqBody);
		byte[] macBytes = macDataStr.getBytes();
		reqSysHead.setMacValue(passwordService.calcCITY(logPool.get(), macBytes));
		
		String reqContent = JSON.toJSONString(req);
		logger.info("交行卡付款转账测试请求");
		RequestBuilder request = MockMvcRequestBuilders.post(URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(reqContent);
		MvcResult mvcResult = mockMvc.perform(request).andReturn();
		logger.info("交行卡付款转账测试请求完毕");
		int status = mvcResult.getResponse().getStatus();
		assertEquals(status, 200);
		String repContent = mvcResult.getResponse().getContentAsString();
		REP_30061800301 rep = JsonUtil.toBean(repContent, REP_30061800301.class);
		System.out.println(rep);
	}

}
