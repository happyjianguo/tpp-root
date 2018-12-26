package com.fxbank.tpp.tcex.trade;

import static org.junit.Assert.assertEquals;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

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
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.tpp.esb.model.ses.PasswordModel;
import com.fxbank.tpp.esb.service.IPasswordService;
import com.fxbank.tpp.tcex.dto.esb.REP_TR002;
import com.fxbank.tpp.tcex.dto.esb.REQ_TR002;

@RunWith(SpringRunner.class)
@SpringBootTest
 @AutoConfigureMockMvc	
public class TownExchangeTest {
	
	private static Logger logger = LoggerFactory.getLogger(TownExchangeTest.class);
	
	@Resource
	private LogPool logPool;
	
	static SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
	
	private static final String URL="http://57.25.8.158:7000/tcex/town.do";
	
	@Reference(version = "1.0.0")
	private IPasswordService passwordService;

	@Autowired
	private MockMvc mockMvc;
	
	private REQ_TR002 req ;
	private REQ_SYS_HEAD reqSysHead;
	private REQ_TR002.REQ_BODY reqBody ;
	
	@Before
	public void init(){
		req = new REQ_TR002();
		reqSysHead = new REQ_SYS_HEAD();
		reqSysHead.setServiceId("TR0");
		reqSysHead.setSceneId("02");
		reqSysHead.setSystemId("301907");
		reqSysHead.setTranMode("ONLINE");
		reqSysHead.setSourceType("LV");	//网联
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
		reqBody = req.new REQ_BODY(); 
		req.setReqSysHead(reqSysHead);
		req.setReqBody(reqBody);
	}
	
	@Test
	public void payOk() throws Exception {
		
		reqBody.setBrnoFlag("1");
		reqBody.setTxAmt("1000.00");
		reqBody.setPayerName("张三");
		reqBody.setPayerAcc("623166000002485919");
		PasswordModel passwordModel = new PasswordModel(logPool.get(), 20181226, 10000,
				1111);
		passwordModel.setAcctNo("623166000002485919");
		passwordModel.setPassword("1111");
		passwordModel = passwordService.encryptPwd(passwordModel);	
		reqBody.setPayerPwd(passwordModel.getPassword());
		reqBody.setIDtype("0");
		reqBody.setIDno("2110031991");
		reqBody.setInfo("商行账户在村镇取款");
		reqBody.setBrno("10001");
		reqBody.setTownDate(sdf1.format(new Date()));
		reqBody.setTownTraceNo(UUID.randomUUID().toString().replace("-", "").substring(0, 15));
		
		String reqContent = JsonUtil.toJson(req);
		
		RequestBuilder request = MockMvcRequestBuilders.post(URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(reqContent);
		MvcResult mvcResult = mockMvc.perform(request).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(status, 200);
		String repContent = mvcResult.getResponse().getContentAsString();
		REP_TR002 rep = JsonUtil.toBean(repContent, REP_TR002.class);
	}
	
}
