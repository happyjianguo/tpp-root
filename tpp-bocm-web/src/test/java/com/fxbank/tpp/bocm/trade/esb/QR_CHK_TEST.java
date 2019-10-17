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
import com.fxbank.tpp.bocm.dto.esb.REP_30063001201;
import com.fxbank.tpp.bocm.dto.esb.REQ_30063001201;
import com.fxbank.tpp.bocm.dto.esb.REQ_30063001201.REQ_BODY;
import com.fxbank.tpp.esb.service.ISafeService;

/** 
* @ClassName: QR_CHK_TEST 
* @Description: 对账结果查询测试
* @author YePuLiang
* @date 2019年7月11日 上午8:11:35 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QR_CHK_TEST  {

	private static Logger logger = LoggerFactory.getLogger(QR_CHK_TEST.class);

	// private static final String URL="http://57.25.3.165:8001/tcex/city.do";
	private static final String URL = "http://127.0.0.1:7006/esb/bocm.do";

	@Autowired
	private MockMvc mockMvc;

	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private ISafeService passwordService;

	private REQ_30063001201 req;
	private REQ_SYS_HEAD reqSysHead;
	private REQ_30063001201.REQ_BODY reqBody;

	@Before
	public void init() {
		req = new REQ_30063001201();
		reqSysHead = new REQ_SYS_HEAD();
		reqSysHead.setServiceId("QR_CHK");
		reqSysHead.setSceneId("");
		reqSysHead.setSystemId("301907");
		reqSysHead.setTranMode("ONLINE");
		reqSysHead.setSourceType("MT");
		reqSysHead.setBranchId("01016");
		reqSysHead.setUserId("000917");
		reqSysHead.setTranDate(String.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date())));
		reqSysHead.setTranTimestamp(String.valueOf(new SimpleDateFormat("HHmmss").format(new Date())));
		reqSysHead.setUserLang("CHINESE");
		reqSysHead.setSeqNo(String.valueOf(Math.abs(new Random().nextInt())));
		reqSysHead.setCompany("COMPANY");
		reqSysHead.setSourceBranchNo("SOURCE_BRANCH_NO");
		reqSysHead.setDestBranchNo("DEST_BRANCH_NO");
		reqSysHead.setFilePath("FILE_PATH");
		reqSysHead.setGloabalSeqNo(reqSysHead.getSeqNo());
		reqSysHead.setAuthUserId("999");
		reqSysHead.setProgramId("7J12");
		reqBody = req.new REQ_BODY();
		req.setReqSysHead(reqSysHead);
		req.setReqBody(reqBody);
	}

	@Test
	public void payOk() throws Exception {

		String macDataStr = JsonUtil.toJson(reqBody);
		byte[] macBytes = macDataStr.getBytes();
		reqSysHead.setMacValue(passwordService.calcCITY(logPool.get(), macBytes));

		String reqContent = JSON.toJSONString(req);
		RequestBuilder request = MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(reqContent);
		MvcResult mvcResult = mockMvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(status, 200);
		String repContent = mvcResult.getResponse().getContentAsString();
		REP_30063001201 rep = JsonUtil.toBean(repContent, REP_30063001201.class);
		System.out.println(rep);
	}
}
