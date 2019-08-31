package com.fxbank.tpp.mivs.trade;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.tpp.esb.service.ISafeService;
import com.fxbank.tpp.mivs.dto.esb.REP_50023000204;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000207;
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

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * @Description: 纳税信息核查结果疑义反馈 测试
 * @Author: 王鹏
 * @Date: 2019/5/14 7:58
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TxPmtVrfctnFdbkTest {


    private static Logger logger = LoggerFactory.getLogger(GetTxPmtVrfctnTest.class);

    private static final String URL="http://127.0.0.1:7006/esb/mivs.do";

    @Resource
    private LogPool logPool;

    @Autowired
    private MockMvc mockMvc;

    private REQ_50023000207 req ;
    private REQ_SYS_HEAD reqSysHead;
    private REQ_50023000207.REQ_BODY reqBody ;

    @Reference(version = "1.0.0")
    private ISafeService passwordService;

    @Before
    public void init(){
        req = new REQ_50023000207();
        reqSysHead = new REQ_SYS_HEAD();
        reqSysHead.setServiceId("500230002");
        reqSysHead.setSceneId("06");
        reqSysHead.setSystemId("301907");
        reqSysHead.setTranMode("ONLINE");
        reqSysHead.setSourceType("301907");	//网联
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
        reqBody = req.new REQ_BODY();
        req.setReqSysHead(reqSysHead);
        req.setReqBody(reqBody);
    }

    @Test
    public void payOk() throws Exception {

        reqBody.setOrgnlDlvrgMsgId("2019070100003762");
        reqBody.setOrgnlRcvgMsgId("2019070100000013");
        reqBody.setCompanyName("拉哈哈娱乐股份有限公司");
        reqBody.setUniSocCdtCd("52188802499222");
//        reqBody.setTxpyrIdNb("123456789123456789");
        reqBody.setRslt("WIDT");
        reqBody.setDataResrcDt("2019-04-29");

        List<REQ_50023000207.txpyrInfoArray> txpmtInfArrayList = new ArrayList<REQ_50023000207.txpyrInfoArray>();
        for(int i=0; i<4; i++){
            REQ_50023000207.txpyrInfoArray arraymsg = new REQ_50023000207.txpyrInfoArray();
            arraymsg.setTxAuthCd("TxAuthCd00"+i);
            arraymsg.setTxAuthNm("国税局");
            arraymsg.setTxpyrSts("00"+i);
            txpmtInfArrayList.add(arraymsg);
        }
        reqBody.setTxpyrInfoArrayMsg(txpmtInfArrayList);
        reqBody.setCntt("对核查结果有疑义");
        reqBody.setContactNm("哈哈精");
        reqBody.setContactNb("17702499222");

        logger.debug("模拟发送ESB请求报文");
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