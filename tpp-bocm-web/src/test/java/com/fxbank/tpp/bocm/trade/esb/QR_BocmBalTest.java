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
import com.fxbank.tpp.esb.service.ISafeService;

/**
 * @Description: 交行余额查询单元测试
 * @Author: 周勇沩
 * @Date: 2019-06-26 15:55:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QR_BocmBalTest {

	private static Logger logger = LoggerFactory.getLogger(QR_BocmBalTest.class);

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
		reqSysHead.setServiceId("300630012");
		reqSysHead.setSceneId("01");
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
		reqBody.setCurNo("CNY");
		reqBody.setAcctName("姓名");
		reqBody.setCardNo("6222600530011742438");
		reqBody.setAcctType("2");
		reqBody.setPwd("B65F18BB3A26941988F87ECA678B1AF4");
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
