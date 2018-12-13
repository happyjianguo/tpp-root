package com.fxbank.tpp.tcex.trade;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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

import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.tpp.tcex.dto.esb.REP_30042001701;
import com.fxbank.tpp.tcex.dto.esb.REQ_30042001701;

@RunWith(SpringRunner.class)
@SpringBootTest
 @AutoConfigureMockMvc	
public class CityCheckAcctTest {

	
	private static Logger logger = LoggerFactory.getLogger(CityDcHistoryTest.class);
	
	private static final String URL="http://127.0.0.1:7000/tcex/city.do";

	@Autowired
	private MockMvc mockMvc;
	
	private REQ_30042001701 req ;
	private REQ_SYS_HEAD reqSysHead;
	private REQ_30042001701.REQ_BODY reqBody ;
	
	@Before
	public void init(){
		req = new REQ_30042001701();
		reqSysHead = new REQ_SYS_HEAD();
		reqSysHead.setServiceId("300420017");
		reqSysHead.setSceneId("01");
		reqSysHead.setSystemId("301907");
		reqSysHead.setTranMode("ONLINE");
		reqSysHead.setSourceType("301907");	//网联
//		reqSysHead.setSourceType("302200");	//银联
		reqSysHead.setBranchId("BRANCH_ID");
		reqSysHead.setUserId("USER_ID");
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
		
		reqBody.setCollateDt("20181206");
		
		String reqContent = JsonUtil.toJson(req);
		
		RequestBuilder request = MockMvcRequestBuilders.post(URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(reqContent);
		MvcResult mvcResult = mockMvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(status, 200);
		String repContent = mvcResult.getResponse().getContentAsString();
		REP_30042001701 rep = JsonUtil.toBean(repContent, REP_30042001701.class);
	}
	

}
