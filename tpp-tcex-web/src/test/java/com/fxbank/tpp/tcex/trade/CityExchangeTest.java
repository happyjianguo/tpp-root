package com.fxbank.tpp.tcex.trade;

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
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.tpp.esb.model.ses.PasswordModel;
import com.fxbank.tpp.esb.service.IPasswordService;
import com.fxbank.tpp.tcex.dto.esb.REP_30041001001;
import com.fxbank.tpp.tcex.dto.esb.REQ_30041001001;

@RunWith(SpringRunner.class)
@SpringBootTest
 @AutoConfigureMockMvc	
public class CityExchangeTest {
	
	private static Logger logger = LoggerFactory.getLogger(CityExchangeTest.class);
	
	@Resource
	private LogPool logPool;
	
	private static final String URL="http://57.25.3.165:7003/tcex/city.do";

	@Autowired
	private MockMvc mockMvc;
	
	private REQ_30041001001 req ;
	private REQ_SYS_HEAD reqSysHead;
	private REQ_30041001001.REQ_BODY reqBody ;
	
	@Reference(version = "1.0.0")
	private IPasswordService passwordService;
	
	@Before
	public void init(){
		req = new REQ_30041001001();
		reqSysHead = new REQ_SYS_HEAD();
		reqSysHead.setServiceId("300410010");
		reqSysHead.setSceneId("01");
		reqSysHead.setSystemId("301907");
		reqSysHead.setTranMode("ONLINE");
		reqSysHead.setSourceType("MT");	//网联
//		reqSysHead.setSourceType("302200");	//银联
		reqSysHead.setBranchId("02002");
		reqSysHead.setUserId("002241");
		reqSysHead.setTranDate(String.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date())));
		reqSysHead.setTranTimestamp(String.valueOf(new SimpleDateFormat("HHmmss").format(new Date())));
		reqSysHead.setUserLang("CHINESE");
		reqSysHead.setSeqNo(String.valueOf(Math.abs(new Random().nextInt())));
		reqSysHead.setSystemId("301907");	//网联
//		reqSysHead.setSystemId("302200");	//银联
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
		
		reqBody.setVillageBrnachFlag("2");
		reqBody.setPayerName("张三思");
		reqBody.setPayerAcctNo("2330210110000002014");
		PasswordModel passwordModel = new PasswordModel(logPool.get(), 20181226, 10000,
				1111);
		passwordModel.setAcctNo("2330210110000002014");
		passwordModel.setPassword("325654");
		passwordModel = passwordService.encryptPwd(passwordModel);
		reqBody.setPayPassword(passwordModel.getPassword());
		reqBody.setTranAmt("100.00");
		reqBody.setChannelType("TCEX");
		reqBody.setNarrative("测试");
		reqBody.setDocClass("2");
		reqBody.setVoucherNo("111");
		reqBody.setDocumentType("0");
		reqBody.setDocumentID("211003199105271510");
		
		String macDataStr = JsonUtil.toJson(reqBody);
		byte[] macBytes = macDataStr.getBytes();
		reqSysHead.setMacValue(passwordService.calcCITY(logPool.get(), macBytes));
		
		String reqContent = JsonUtil.toJson(req);
		
		RequestBuilder request = MockMvcRequestBuilders.post(URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(reqContent);
		MvcResult mvcResult = mockMvc.perform(request).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(status, 200);
		String repContent = mvcResult.getResponse().getContentAsString();
		REP_30041001001 rep = JsonUtil.toBean(repContent, REP_30041001001.class);
	}
	
}
