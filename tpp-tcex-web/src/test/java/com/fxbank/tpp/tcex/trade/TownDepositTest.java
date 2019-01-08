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
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.tpp.esb.common.TOWN;
import com.fxbank.tpp.esb.service.IPasswordService;
import com.fxbank.tpp.tcex.dto.esb.REP_TR001;
import com.fxbank.tpp.tcex.dto.esb.REQ_TR001;

@RunWith(SpringRunner.class)
@SpringBootTest
 @AutoConfigureMockMvc	
public class TownDepositTest {
	
	private static Logger logger = LoggerFactory.getLogger(TownDepositTest.class);
	
	static SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
	
	private static final String URL="http://57.25.3.165:7003/tcex/town.do";

	@Autowired
	private MockMvc mockMvc;
	
	@Reference(version = "1.0.0")
	private IPasswordService passwordService;
	
	@Resource
	private LogPool logPool;
	
	private REQ_TR001 req ;
	private REQ_SYS_HEAD reqSysHead;
	private REQ_TR001.REQ_BODY reqBody ;
	
	@Before
	public void init(){
		req = new REQ_TR001();
		reqSysHead = new REQ_SYS_HEAD();
		reqSysHead.setServiceId("TR001");
		reqSysHead.setSceneId("");
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
		
		reqBody.setBrnoFlag("2");
		reqBody.setTxAmt("1000.00");
		reqBody.setPayeeName("张三");
		reqBody.setPayeeAcc("623166000000810480");
		reqBody.setInfo("商行账户在村镇存款");
		reqBody.setBrno("10001");
		reqBody.setTownDate(sdf1.format(new Date()));
		reqBody.setTownTraceNo(UUID.randomUUID().toString().replace("-", "").substring(0, 15));
		
		String macDataStr = JsonUtil.toJson(reqBody);
		byte[] macBytes = macDataStr.getBytes();
		reqSysHead.setMacValue(passwordService.calcTOWN(logPool.get(), macBytes));
		
		String reqContent = JsonUtil.toJson(req);
		
		RequestBuilder request = MockMvcRequestBuilders.post(URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(reqContent);
		MvcResult mvcResult = mockMvc.perform(request).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(status, 200);
		String repContent = mvcResult.getResponse().getContentAsString();
		REP_TR001 rep = JsonUtil.toBean(repContent, REP_TR001.class);
	}
	
}
