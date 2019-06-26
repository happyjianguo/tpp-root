//package com.fxbank.tpp.mivs.trade;
//
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.fxbank.cip.base.common.LogPool;
//import com.fxbank.tpp.esb.service.ISafeService;
//import com.fxbank.tpp.mivs.dto.mivs.MIVS_330_001_01;
//import com.fxbank.tpp.mivs.model.response.MIVS_330_001_01_AbnmlAgtInfoVrfctnInfNtfctn;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import javax.annotation.Resource;
//
///**
// * @Description:
// * @Author: 王鹏
// * @Date: 2019/6/20 8:33
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AbnmlAgtInfoVrfctnInfNtfctnTest {
//    private static Logger logger = LoggerFactory.getLogger(GetIdVrfctnTest.class);
//
//    private static final String URL="http://127.0.0.1:7006/esb/mivs.do";
//
//    @Resource
//    private LogPool logPool;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Reference(version = "1.0.0")
//    private ISafeService passwordService;
//
//    private MIVS_330_001_01 mivs330 ;
//
//    @Before
//    public void init(){
//        mivs330 = new MIVS_330_001_01();
//        mivs330.getHead().setOrigSender("0000"); // TODO 通过机构号查询渠道接口获取（机构号查行号）
//        mivs330.getHead().setOrigReceiver("313131000008");
//        mivs330.getHead().setMesgID(String.format("%08d%08d", modelBase.getSysDate(), modelBase.getSysTraceno()));
//        grpHdr.getInstgPty().setInstgDrctPty("0000");
//        grpHdr.getInstgPty().setInstgPty("000012345678");
//        reqSysHead.setServiceId("500230002");
//        reqSysHead.setSceneId("04");
//        reqSysHead.setSystemId("301907");
//        reqSysHead.setTranMode("ONLINE");
//        reqSysHead.setSourceType("301907");	//网联
////		reqSysHead.setSourceType("302200");	//银联
//        reqSysHead.setBranchId("02002");
//        reqSysHead.setUserId("002241");
//        reqSysHead.setTranDate(String.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date())));
//        reqSysHead.setTranTimestamp(String.valueOf(new SimpleDateFormat("HHmmss").format(new Date())));
//        reqSysHead.setUserLang("CHINESE");
//        reqSysHead.setSeqNo(String.valueOf(Math.abs(new Random().nextInt())));
//        reqSysHead.setSystemId("301907");	//网联
////		reqSysHead.setSystemId("302200");	//银联
//        reqSysHead.setCompany("COMPANY");
//        reqSysHead.setSourceBranchNo("SOURCE_BRANCH_NO");
//        reqSysHead.setDestBranchNo("DEST_BRANCH_NO");
//        reqSysHead.setFilePath("FILE_PATH");
//        reqSysHead.setGloabalSeqNo(reqSysHead.getSeqNo());
//        reqBody = req.new REQ_BODY();
//        req.setReqSysHead(reqSysHead);
//        req.setReqBody(reqBody);
//    }
//
//    @Test
//    public void payOk() throws Exception {
//
//        reqBody.setEntNm("企业名称哈哈哈");
//        reqBody.setUniSocCdtCd("291238750123857");
//        reqBody.setAcctSts("OPEN");
//        reqBody.setChngDt("20190604");
//
//        String macDataStr = JsonUtil.toJson(reqBody);
//        byte[] macBytes = macDataStr.getBytes();
//        reqSysHead.setMacValue(passwordService.calcCITY(logPool.get(), macBytes));
//
//        String reqContent = JsonUtil.toJson(req);
//
//        RequestBuilder request = MockMvcRequestBuilders.post(URL)
//                .contentType(MediaType.APPLICATION_JSON_UTF8).content(reqContent);
//        MvcResult mvcResult = mockMvc.perform(request).andReturn();
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(status, 200);
//        String repContent = mvcResult.getResponse().getContentAsString();
//        REP_50023000203 rep = JsonUtil.toBean(repContent, REP_50023000203.class);
//    }
//}
