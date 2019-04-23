/**   
* @Title: DB_BocmCashTest.java 
* @Package com.fxbank.tpp.bocm.trade.esb 
* @Description: 交行卡存现金测试
* @author YePuLiang
* @date 2019年4月22日 上午8:08:11 
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
import com.fxbank.tpp.bocm.dto.esb.REP_30061800301;
import com.fxbank.tpp.bocm.dto.esb.REQ_30061800301;
import com.fxbank.tpp.esb.service.ISafeService;


/** 
* @ClassName: DB_BocmCashTest 
* @Description: 交行卡存现金测试
* @author YePuLiang
* @date 2019年4月22日 上午8:08:11 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc	
public class DB_BocmCashTest {
	
	private static Logger logger = LoggerFactory.getLogger(DB_BocmCashTest.class);
	
//	private static final String URL="http://57.25.3.165:8001/tcex/city.do";
     private static final String URL="http://127.0.0.1:7006/esb/bocm.do";

	@Autowired
	private MockMvc mockMvc;
	
	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private ISafeService passwordService;
	
	private REQ_30061800301 req ;
	private REQ_SYS_HEAD reqSysHead;
	private REQ_30061800301.REQ_BODY reqBody ;
	
	@Before
	public void init(){
		req = new REQ_30061800301();
		reqSysHead = new REQ_SYS_HEAD();
		reqSysHead.setServiceId("300618003");
		reqSysHead.setSceneId("01");
		reqSysHead.setSystemId("301907");
		reqSysHead.setTranMode("ONLINE");
		reqSysHead.setSourceType("BOCM");	//网联
//		reqSysHead.setSourceType("302200");	//银联
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
		reqSysHead.setAuthUserId("999");
		reqBody = req.new REQ_BODY(); 
		req.setReqSysHead(reqSysHead);
		req.setReqBody(reqBody);
	}
	
	@Test
	public void payOk() throws Exception {
		logger.info("交行卡存现金测试");
		
		reqBody.setCcyT("CNY");
		reqBody.setNaT1("姓名");//姓名
		reqBody.setCardNoT3("卡号");//卡号
		reqBody.setDpsAmtT("100.00");//存款金额
		reqBody.setFeeT3("0.00");//手续费
		reqBody.setAcctBalT3("1000.00");//账户余额
		reqBody.setHndlPymntFeeT5("0.00");//应收手续费
		reqBody.setHldrGlblIdT("201101");//身份证号
		reqBody.setAgentCrtfNoT("201101");//代理人身份证
		reqBody.setRcveWyT("0");//收取方式  0 账户内扣  1 现金外收
		
		//账户类型  0 银行账号 1 贷记卡 2 借记卡 3其他 通存业务模式为1时必需
		reqBody.setAcctTpT("0");
		reqBody.setBusiMdT1("0");//业务模式
		reqBody.setRdCardWyT("0");//存款时必输  0刷卡 1手工输入
		reqBody.setOpnAcctBnkNoT8("88291991");//开户行号
		//15	居民身份证        
		reqBody.setIdTpT2("15");
		reqBody.setScdTrkInfoT2("6222600530011742438=4912120343981195");//二磁道信息
		reqBody.setIcCardFlgT4("0");//IC卡磁条卡标志   0磁条卡  1IC卡
		String macDataStr = JsonUtil.toJson(reqBody);
		byte[] macBytes = macDataStr.getBytes();
		reqSysHead.setMacValue(passwordService.calcCITY(logPool.get(), macBytes));
		
		String reqContent = JSON.toJSONString(req);
		logger.info("交行卡存现金测试请求");
		RequestBuilder request = MockMvcRequestBuilders.post(URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(reqContent);
		MvcResult mvcResult = mockMvc.perform(request).andReturn();
		logger.info("交行卡存现金测试请求完毕");
		int status = mvcResult.getResponse().getStatus();
		assertEquals(status, 200);
		String repContent = mvcResult.getResponse().getContentAsString();
		REP_30061800301 rep = JsonUtil.toBean(repContent, REP_30061800301.class);
		System.out.println(rep);
	}

}
