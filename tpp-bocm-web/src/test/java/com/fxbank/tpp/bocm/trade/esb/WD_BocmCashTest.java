/**   
* @Title: WD_BocmCashTest.java 
* @Package com.fxbank.tpp.bocm.trade.esb 
* @Description:交行卡取现金测试
* @author YePuLiang
* @date 2019年4月22日 上午10:55:34 
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
import com.fxbank.tpp.bocm.dto.esb.REQ_30061001001;
import com.fxbank.tpp.bocm.dto.esb.REQ_30061000801.REQ_BODY;
import com.fxbank.tpp.esb.service.ISafeService;

/** 
* @ClassName: WD_BocmCashTest 
* @Description: 交行卡取现金测试
* @author YePuLiang
* @date 2019年4月22日 上午10:55:34 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc	
public class WD_BocmCashTest {
	
	private static Logger logger = LoggerFactory.getLogger(DB_BocmCashTest.class);
	
//	private static final String URL="http://57.25.3.165:8001/tcex/city.do";
     private static final String URL="http://127.0.0.1:7006/esb/bocm.do";
//	private static final String URL="http://57.25.3.166:7006/esb/bocm.do";

	@Autowired
	private MockMvc mockMvc;
	
	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private ISafeService passwordService;
	
	private REQ_30061001001 req ;
	private REQ_SYS_HEAD reqSysHead;
	private REQ_30061001001.REQ_BODY reqBody ;
	
	@Before
	public void init(){
		req = new REQ_30061001001();
		reqSysHead = new REQ_SYS_HEAD();
		reqSysHead.setServiceId("300610010");
		reqSysHead.setSceneId("01");
		reqSysHead.setSystemId("301907");
		reqSysHead.setTranMode("ONLINE");
		reqSysHead.setSourceType("MT");	//网联
//		reqSysHead.setBranchId("01016");
//		reqSysHead.setUserId("000917");
//		reqSysHead.setBranchId("01037");
		reqSysHead.setBranchId("01037");
		reqSysHead.setUserId("000777");
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
		reqSysHead.setProgramId("7J12");
		reqBody = req.new REQ_BODY(); 
		req.setReqSysHead(reqSysHead);
		req.setReqBody(reqBody);
	}
	
	@Test
	public void payOk() throws Exception {
		logger.info("交行卡取现金测试");
		
		reqBody.setCcyT("CNY");
		reqBody.setNmT("地区代码");
		reqBody.setCardNoT3("6222620110037989184");//卡号
		reqBody.setWthrAmtT("100.00");//转账金额
		reqBody.setFeeAmtT3("0.00");//手续费
		reqBody.setBalT("");//余额
		reqBody.setHndlPymntFeeT5("0.00");//应收手续费
		reqBody.setHldrGlblIdT("370126195312267931");//证件号码
		reqBody.setAcctNoTpT("2");//付款人账户类型
		reqBody.setFeeRcveWyT1("0");
		reqBody.setBusTpT13("1");
		reqBody.setCardInWyT("0");
		reqBody.setPwdT("3F1DC6FD968A3C0A4046F7FDDCB7E11B");
		reqBody.setOpnAcctBnkNoT7("301290050029");
		reqBody.setIdTpT2("15");
		
		//ic卡顺序号
		reqBody.setIcCardSeqNoT1("001");
		//ARQC   IC卡发卡行认证
		reqBody.setIcCard91T("1553C75727C9DA42");
		//ic卡
		reqBody.setIcCardF55T("2469F26081553C75727C9DA429F2701809F101307020103A0A010010A0100000000004E783E039F3704E841ABFC9F36020085950500000008009A031907039C01969F02060000000000005F2A0201565A0A6222620110037989184F5F3401019F1302000782027C009F1A0201569F03060000000000009F330320A100");
		//IC卡应用编号
		reqBody.setIcCard9f09T("1553C75727C9DA42");
		
		reqBody.setIcCardAvaiDtT("241231");
		

		reqBody.setIcCardFlgT4("5");//IC卡磁条卡标志   0磁条卡  1IC卡
		String macDataStr = JsonUtil.toJson(reqBody);
		byte[] macBytes = macDataStr.getBytes();
		reqSysHead.setMacValue(passwordService.calcCITY(logPool.get(), macBytes));
		
		String reqContent = JSON.toJSONString(req);
		logger.info("交行卡取现金请求");
		RequestBuilder request = MockMvcRequestBuilders.post(URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(reqContent);
		MvcResult mvcResult = mockMvc.perform(request).andReturn();
		logger.info("交行卡取现金测试请求完毕");
		int status = mvcResult.getResponse().getStatus();
		assertEquals(status, 200);
		String repContent = mvcResult.getResponse().getContentAsString();

	}
}
