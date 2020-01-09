/**   
* @Title: DB_BocmTraTest.java 
* @Package com.fxbank.tpp.bocm.trade.esb 
* @Description: 本行卡付款转账测试
* @author YePuLiang
* @date 2019年4月22日 下午4:22:32 
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
import com.fxbank.tpp.esb.service.ISafeService;

/** 
* @ClassName: DB_BocmTraTest 
* @Description:本行卡付款转账测试
* @author YePuLiang
* @date 2019年4月22日 下午4:22:32 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc	
public class DB_BocmTraTest {
	
	private static Logger logger = LoggerFactory.getLogger(DB_BocmCashTest.class);
	
//	private static final String URL="http://57.25.3.165:8001/tcex/city.do";
     private static final String URL="http://127.0.0.1:7006/esb/bocm.do";

	@Autowired
	private MockMvc mockMvc;
	
	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private ISafeService passwordService;
	
	private REQ_30061000701 req ;
	private REQ_SYS_HEAD reqSysHead;
	private REQ_30061000701.REQ_BODY reqBody ;
	
	@Before
	public void init(){
		req = new REQ_30061000701();
		reqSysHead = new REQ_SYS_HEAD();
		reqSysHead.setServiceId("300610007");
		reqSysHead.setSceneId("01");
		reqSysHead.setSystemId("301907");
		reqSysHead.setTranMode("ONLINE");
		reqSysHead.setSourceType("MT");	//网联
//		reqSysHead.setSourceType("302200");	//银联
		
		reqSysHead.setBranchId("01045");
		reqSysHead.setUserId("000373");
//		reqSysHead.setBranchId("01003");
//		reqSysHead.setUserId("000274");
		reqSysHead.setTranDate(String.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date())));
		reqSysHead.setTranTimestamp(String.valueOf(new SimpleDateFormat("HHmmss").format(new Date())));		
		reqSysHead.setUserLang("CHINESE");
		reqSysHead.setSeqNo(String.valueOf(Math.abs(new Random().nextInt())));
		reqSysHead.setSystemId("301907");	//网联
		reqSysHead.setCompany("COMPANY");
		reqSysHead.setSourceBranchNo("PINP|pinpToesb|RZPK|64510637BCD9|");//10137
		reqSysHead.setSourceBranchNo("PINP|pinpToesb|RZPK|000B0027E831|");//10103
		
		reqSysHead.setDestBranchNo("DEST_BRANCH_NO");
		reqSysHead.setFilePath("FILE_PATH");
		reqSysHead.setGloabalSeqNo(reqSysHead.getSeqNo());
		reqSysHead.setProgramId("7J12");
		reqSysHead.setAuthUserId("999");
		reqBody = req.new REQ_BODY(); 
		req.setReqSysHead(reqSysHead);
		req.setReqBody(reqBody);
	}
	
	@Test
	public void payOk() throws Exception {
		logger.info("本行卡付款转账 ");
		
		
		reqBody.setBnkCardUseCcyT("CNY");
		reqBody.setBcmCardUseCcyT("CNY");
		reqBody.setBnkCardAcctNaT("姓名1");
		reqBody.setBcmCardAcctNaT("姓名2");
		reqBody.setAcctBranchT1("123");//开户机构
//		reqBody.setBnkCardAcctNoT("623166001015087593");//本行卡账号
		reqBody.setBnkCardAcctNoT("623166000000575489");//本行卡账号
		
		reqBody.setBcmCardAcctNoT("6222620110037989184");//交行卡账号
//		reqBody.setTrsrAmtT3("1.00");//转账金额
		reqBody.setTrsrAmtT3("50001.00");//转账金额
//		reqBody.setTrsrAmtT3("10.00");//转账金额
		reqBody.setFeeT3("0.00");//手续费
		reqBody.setHndlPymntFeeT5("0.00");//应收手续费
		reqBody.setAcctSqNoT2("123");//本行卡账户序号
		reqBody.setVchrNoT1("210101");//凭证号码
//		reqBody.setBcmCardAcctCrtfNoT("210102");//交行卡账户证件号码
//		reqBody.setCmsnHldrGlblIdT("201101");//代理人证件号码
		reqBody.setPyrAcctTpT("0");//付款人账号类型 
		reqBody.setRcveWyT("0");//收取方式
		reqBody.setAcctTpT("2");//账户类型
		reqBody.setBusiMdT1("1");//业务模式
		reqBody.setInWyT("0");//输入方式
		reqBody.setPwdT("AB08EA6843319330E5569E959F4AD0A6");//密码   147258
//		reqBody.setPwdT("B0711597D760CC9B49DBF27E32049F24");//密码
		
		reqBody.setPyrOpnBnkNoT2("313229000024");//付款人开户行号
		reqBody.setPyeeOpnBnkNoT1("301290050029");//收款人开户行号
		reqBody.setHmFeeT("0.00");//本行手续费
		reqBody.setBcmCardAcctCrtfTpT("15");//交行卡账户证件类型
		reqBody.setIsVchrPswdT("Y");//是否凭密
		reqBody.setHldrGlblIdT("12321");//本行卡证件号码
		reqBody.setIdTpT2("15");//证件类型
//		reqBody.setBrchNoT8("02002");//机构号1
//		reqBody.setBrchNoT9("02003");//机构号2
//		reqBody.setScdTrkInfoT2("623166000003394334=4912567019123456");//二磁道信息
		reqBody.setIcCardFlgT4("2");//IC卡磁条卡标志   0磁条卡  1IC卡
		String macDataStr = JsonUtil.toJson(reqBody);
		byte[] macBytes = macDataStr.getBytes();
		reqSysHead.setMacValue(passwordService.calcCITY(logPool.get(), macBytes));
		
		String reqContent = JSON.toJSONString(req);
		logger.info("本行卡付款转账测试请求");
		RequestBuilder request = MockMvcRequestBuilders.post(URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(reqContent);
		MvcResult mvcResult = mockMvc.perform(request).andReturn();
		logger.info("本行卡付款转账测试请求完毕");
		int status = mvcResult.getResponse().getStatus();
		assertEquals(status, 200);
	}

}