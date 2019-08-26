package com.fxbank.tpp.mivs.trade;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import com.fxbank.tpp.mivs.dto.esb.REP_50023000204;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000204;
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
import com.fxbank.tpp.esb.service.ISafeService;
/**
 * @Description: 纳税信息联网核查模拟
 * @Author: 王鹏
 * @Date: 2019/4/29 9:27.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetTxPmtVrfctnTest {


    private static Logger logger = LoggerFactory.getLogger(GetTxPmtVrfctnTest.class);

    private static final String URL="http://127.0.0.1:7006/esb/mivs.do";

    @Resource
    private LogPool logPool;

    @Autowired
    private MockMvc mockMvc;

    private REQ_50023000204 req ;
    private REQ_SYS_HEAD reqSysHead;
    private REQ_50023000204.REQ_BODY reqBody ;

    @Reference(version = "1.0.0")
    private ISafeService passwordService;

    @Before
    public void init(){
        req = new REQ_50023000204();
        reqSysHead = new REQ_SYS_HEAD();
        reqSysHead.setServiceId("500230002");
        reqSysHead.setSceneId("04");
        reqSysHead.setSystemId("301907");
        reqSysHead.setTranMode("ONLINE");
        reqSysHead.setSourceType("301907");	//网联
//		reqSysHead.setSourceType("302200");	//银联
        reqSysHead.setBranchId("00001");
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

        reqBody.setCompanyName("北京市政交通一卡通有限公司");
        reqBody.setUniSocCdtCd("91110000801145381H");
//        reqBody.setTaxPayerId("19989898");
        reqBody.setOpNm("李四");

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
        REP_50023000204 rep = JsonUtil.toBean(repContent, REP_50023000204.class);
    }


}
