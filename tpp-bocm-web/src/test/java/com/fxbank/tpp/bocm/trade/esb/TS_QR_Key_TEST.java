/**   
* @Title: TS_QR_Key_TEST.java 
* @Package com.fxbank.tpp.bocm.trade.esb 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年6月20日 上午10:11:36 
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
import com.fxbank.tpp.bocm.dto.esb.REQ_30061000701;
import com.fxbank.tpp.bocm.dto.esb.REQ_30061000701.REQ_BODY;
import com.fxbank.tpp.bocm.dto.esb.REQ_TS_10104;
import com.fxbank.tpp.esb.service.ISafeService;

/** 
* @ClassName: TS_QR_Key_TEST 
* @Description: 交行密钥更新测试
* @author YePuLiang
* @date 2019年6月20日 上午10:11:36 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TS_QR_Key_TEST {
	private static Logger logger = LoggerFactory.getLogger(DB_BocmCashTest.class);
	
//	private static final String URL="http://57.25.3.165:8001/tcex/city.do";
     private static final String URL="http://127.0.0.1:7006/esb/bocm.do";

	@Autowired
	private MockMvc mockMvc;
	
	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private ISafeService passwordService;
	
	private REQ_TS_10104 req ;
	private REQ_SYS_HEAD reqSysHead;
	private REQ_TS_10104.REQ_BODY reqBody ;
	
	@Before
	public void init(){
		req = new REQ_TS_10104();
		reqSysHead = new REQ_SYS_HEAD();
		reqSysHead.setServiceId("TS_10104");
		reqSysHead.setSceneId("");
		reqSysHead.setSystemId("301907");
		reqSysHead.setTranMode("ONLINE");
		reqSysHead.setSourceType("MT");	//网联
//		reqSysHead.setSourceType("302200");	//银联
		reqSysHead.setBranchId("02002");
		reqSysHead.setUserId("000917");
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
		logger.info("交行密钥更新测试 ");
		
		String macDataStr = JsonUtil.toJson(reqBody);
		byte[] macBytes = macDataStr.getBytes();
		reqSysHead.setMacValue(passwordService.calcCITY(logPool.get(), macBytes));
		
		String reqContent = JSON.toJSONString(req);
		logger.info("交行密钥更新");
		RequestBuilder request = MockMvcRequestBuilders.post(URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(reqContent);
		MvcResult mvcResult = mockMvc.perform(request).andReturn();
		logger.info("交行密钥更新请求完毕");
		int status = mvcResult.getResponse().getStatus();
		assertEquals(status, 200);
		String repContent = mvcResult.getResponse().getContentAsString();
	}
}
