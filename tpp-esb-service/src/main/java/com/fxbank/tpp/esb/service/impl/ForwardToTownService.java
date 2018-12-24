package com.fxbank.tpp.esb.service.impl;

import java.net.SocketTimeoutException;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.common.HttpService;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_BASE;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.tpp.esb.common.TOWN;
import com.fxbank.tpp.esb.service.IForwardToTownService;
import redis.clients.jedis.Jedis;

@Service(version = "1.0.0")
public class ForwardToTownService implements IForwardToTownService {

	private static Logger logger = LoggerFactory.getLogger(ForwardToTownService.class);
	
	private static final String serviceKey = "tcex_common.esb_town_url";
	
	@Resource
	private MyJedis myJedis;
	
	@Resource
	private HttpService httpService;
	
	@Autowired
    private MacService macService;

	@Override
	public <T> T sendToTown(ESB_BASE esbModel,Object macData,Class<T> clazz) throws SysTradeExecuteException {
		String url ;
		try(Jedis jedis=myJedis.connect()){
			url = jedis.get(serviceKey);
		}
		logger.info("Town服务地址：" + url);
		if (url == null) {
			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000001);
			logger.debug(e.getRspCode() + " | " + e.getRspMsg());
			throw e;
		}
		
		String jsonReq = JsonUtil.toJson(esbModel);
		
		if(esbModel.isMacEnable()){	//需要生成MAC
			String macDataStr = JsonUtil.toJson(macData);
			byte[] macBytes = macDataStr.getBytes();
			String mac = macService.calc(esbModel.getMylog(),macBytes);
			jsonReq = jsonReq.replaceFirst(TOWN.macPlaceHolder, mac);
		}
		logger.info("发送请求至Town：" + jsonReq);
		
		String result=null;
		try {
//			url="http://57.25.3.164:8000/cip/esb/unionsign.do";
//			jsonReq="{\"APP_HEAD\":{\"CURRENT_NUM\":\"0\",\"PAGE_END\":\"0\",\"PAGE_START\":\"0\",\"PGUP_OR_PGDN\":\"0\",\"TOTAL_NUM\":\"-1\"},\"BODY\":{\"AGREEMENT_TYPE\":\"1\",\"PRO_BELONG_BRANCH\":\"Z2004944000010\",\"PRO_INITR_BRANCH\":\"313229\",\"PRO_RCV_BRANCH\":\"313228\",\"ACCT_TYPE\":\"1\",\"ACCT_NO\":\"622126010004152400\",\"ACCT_NAME\":\"张志宏\",\"DOCUMENT_TYPE\":\"1\",\"DOCUMENT_ID\":\"210381198302252714\",\"OBLIGATE_PHONE\":\"13809872813\",},\"LOCAL_HEAD\":{},\"SYS_HEAD\":{\"APPR_FLAG\":\"1\",\"APPR_USER_ID\":\"1100001002\",\"AUTH_FLAG\":\"N\",\"AUTH_USER_ID\":\"1100001002\",\"BRANCH_ID\":\"8888\",\"CNSM_SYS_SVRID\":\"abc\",\"COMPANY\":\"abc\",\"DEST_BRANCH_NO\":\"02001\",\"EXTEND_ARRAY\":[\"\"],\"EXTEND_FIELD\":\"abc\",\"FILE_PATH\":\"abc\",\"GLOBAL_SEQ_NO\":\"4012002018032909433201\",\"MAC_ORG_ID\":\"abc\",\"MAC_VALUE\":\"UCA|ucaToesb|RZAK|BD8CA9773B808028F403964C1264521E\",\"PROGRAM_ID\":\"3112\",\"SCENE_ID\":\"01\",\"SEQ_NO\":\"000220130707\",\"SERVICE_ID\":\"300120020\",\"SOURCE_BRANCH_NO\":\"000001\",\"SOURCE_TYPE\":\"MT\",\"SRC_SYS_SVRID\":\"abc\",\"SYSTEM_ID\":\"301907\",\"THREAD_NO\":\"abc\",\"TRAN_DATE\":\"20180329\",\"TRAN_MODE\":\"ONLINE\",\"TRAN_TIMESTAMP\":\"0943320120\",\"USER_ID\":\"0001\",\"USER_LANG\":\"CHINESE\",\"VERSION\":\"abc\",\"globalSeqNo\":\"4012002018032909433201\"}}";
//			logger.info("ESB服务地址：" + url);
//			logger.info("发送请求至ESB：" + jsonReq);
			result = httpService.doJsonPost(url,jsonReq);
		} catch (SocketTimeoutException e) {
			logger.error(e.toString());
			SysTradeExecuteException e1 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000004);
			logger.error(e1.getRspCode() + " | " + e1.getRspMsg());
			throw e1;
		} catch(Exception e){
			logger.error(e.toString());
			SysTradeExecuteException e1 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000005);
			logger.error(e1.getRspCode() + " | " + e1.getRspMsg());
			throw e1;
		}
//		result="{\"SYS_HEAD\":{\"RET_STATUS\":\"S\",\"RET\":[{\"RET_CODE\":\"000000\",\"RET_MSG\":\"000000 SUCCESS\"}],\"SERVICE_ID\":\"300130002\",\"SCENE_ID\":\"01\",\"SOURCE_TYPE\":\"GJ\",\"PRVD_SYS_ID\":\"\",\"RUN_DATE\":\"20181011\",\"TRAN_DATE\":\"20181101\",\"TRAN_TIMESTAMP\":\"111804037\",\"SEQ_NO\":\"1002002018110100065941\",\"REFERENCE\":\"ENS2018101118374834\",\"SOURCE_BRANCH_NO\":\"\",\"DEST_BRANCH_NO\":\"\",\"FILE_PATH\":\"\",\"MAC_VALUE\":\"\",\"THREAD_NO\":\"\",\"COST_TIME\":\"\",\"EXTEND_ARRAY\":[{}],\"EXTEND_FIELD\":\"\",\"MAC_ORG_ID\":\"\",\"PRVD_SYS_SVRID\":\"\",\"TRAN_MODE\":\"\",\"BRANCH_ID\":\"02002\",\"USER_ID\":\"907004\",\"USER_LANG\":\"\",\"SYSTEM_ID\":\"100200\",\"COMPANY\":\"\",\"PROGRAM_ID\":\"\",\"AUTH_USER_ID\":\"\",\"AUTH_FLAG\":\"\",\"APPR_USER_ID\":\"\",\"APPR_FLAG\":\"\",\"VERSION\":\"\",\"CNSM_SYS_SVRID\":\"\",\"SRC_SYS_SVRID\":\"\"},\"APP_HEAD\":{\"PGUP_OR_PGDN\":\"\",\"TOTAL_NUM\":\"\",\"CURRENT_NUM\":\"\",\"TOTAL_ROWS\":\"\",\"TOTAL_FLAG\":\"\"},\"BODY\":{\"INTERNAL_KEY\":\"1958964\",\"BASE_ACCT_NO\":\"34128070020000004\",\"PROD_TYPE\":\"34128\",\"PROD_TYPE_DESC\":\"\",\"ACCT_NAME\":\"测试\",\"ACCT_OPEN_DATE\":\"20180120\",\"ACCT_STATUS\":\"A\",\"ACCT_STATUS_PREV\":\"A\",\"ACCT_STATUS_UPD_DATE\":\"20180120\",\"ACCOUNTING_STATUS\":\"ZHC\",\"ACCOUNTING_STATUS_PREV\":\"ZHC\",\"ACCOUNT_STATUS_UPD_DATE\":\"20180120\",\"ACCT_STATUS_DESC\":\"\",\"BRANCH\":\"07002\",\"CCY\":\"CNY\",\"CLIENT_NO\":\"049990700299\",\"CLIENT_TYPE\":\"04\",\"CLIENT_SHORT\":\"抚顺分行营业部\",\"CLIENT_NAME\":\"\",\"CH_CLIENT_NAME\":\"阜新银行股份有限公司抚顺分行营业部\",\"DOCUMENT_TYPE\":\"232\",\"DOCUMENT_TYPE_DESC\":\"\",\"DOCUMENT_ID\":\"07002\",\"ACCT_EXEC\":\"\",\"ACCT_EXEC_NAME\":\"\",\"ALT_ACCT_NAME\":\"\",\"CATEGORY_TYPE\":\"\",\"INTERNAL_IND\":\"\",\"CLIENT_IND_DESC\":\"\",\"OWNERSHIP_TYPE\":\"\",\"OWNERSHIP_TYPE_DESC\":\"\",\"PROFIT_CENTRE\":\"99\",\"USER_ID\":\"SJYZ\",\"LAST_CHANGE_DATE\":\"20181011\",\"LAST_CHANGE_USER_ID\":\"\",\"APPROVAL_STATUS\":\"\",\"APPROVAL_STATUS_DESC\":\"\",\"TERMINAL_ID\":\"\",\"APPR_USER_ID\":\"\",\"CLIENT_STATUS\":\"1\",\"ADDRESS\":\"\",\"CLASS_LEVEL\":\"\",\"ACCT_TYPE\":\"C\",\"ACCT_TYPE_DESC\":\"\",\"ACCT_DESC\":\"其他清算应付款\",\"BALANCE\":\"10082.07\",\"CCY_DESC\":\"\",\"OSA_FLAG\":\"I\",\"REGION_FLAG\":\"\",\"ACCT_NATURE\":\"\",\"ACCT_NATURE_DESC\":\"\",\"OPEN_TRAN_DATE\":\"20180120\",\"MULTI_BAL_TYPE\":\"\",\"LEAD_ACCT_FLAG\":\"Y\",\"TERM\":\"\",\"TERM_TYPE\":\"\",\"ALL_DEP_IND\":\"\",\"ISS_COUNTRY\":\"CHN\",\"ALL_DRA_IND\":\"\",\"HOME_BRANCH\":\"07002\",\"REASON_CODE\":\"\",\"REAL_RATE\":\"\",\"DAY_NUM\":\"264\",\"MATURITY_DATE\":\"\",\"ACTUAL_BAL\":\"10082.07\",\"DOC_TYPE\":\"\",\"VOUCHER_NO\":\"\",\"WITHDRAWAL_TYPE\":\"W\",\"PREFIX\":\"\",\"ACTUAL_RATE\":\"\",\"FLOAT_RATE\":\"\",\"INT_TYPE\":\"\",\"TAX_POSTED\":\"\",\"TAX_ACCRUED\":\"\",\"INT_POSTED\":\"\",\"INT_ACCRUED\":\"\",\"INT_ADJ\":\"\",\"NET_INTEREST_AMT\":\"\",\"FIXED_CALL\":\"\",\"ACCT_SEQ_NO\":\"0\",\"APPLY_BRANCH\":\"07002\",\"AUTO_RENEW_ROLLOVER\":\"\",\"INT_CLASS\":\"\",\"ACCT_STOP_PAY\":\"N\",\"RES_FLAG\":\"\",\"IS_INDIVIDUAL\":\"N\",\"SETTLE_ARRAY\":[{\"SETTLE_BRANCH\":\"\",\"SETTLE_CLIENT\":\"\",\"SETTLE_ACCT_CLASS\":\"\",\"SETTLE_METHOD\":\"\",\"PAY_REC_IND\":\"\",\"AMT_TYPE\":\"\",\"SETTLE_ACCT_INTERNAL_KEY\":\"\",\"SETTLE_BASE_ACCT_NO\":\"\",\"SETTLE_PROD_TYPE\":\"\",\"SETTLE_ACCT_CCY\":\"\",\"SETTLE_ACCT_SEQ_NO\":\"\",\"SETTLE_CCY\":\"\",\"SETTLE_AMT\":\"\",\"SETTLE_XRATE\":\"\",\"SETTLE_XRATE_ID\":\"\",\"AUTO_BLOCKING\":\"\",\"SORT_PRIORITY\":\"\",\"SETTLE_WEIGHT\":\"\",\"TRUSTED_PAY_NO\":\"\",\"SETTLE_NO\":\"\",\"SETTLE_BANK_FLAG\":\"\",\"SETTLE_ACCT_NAME\":\"\",\"SETTLE_BANK_NAME\":\"\",\"SETTLE_MOBILE_PHONE\":\"\"}],\"CONTACT_ARRAY\":[{\"LINKMAN_TYPE\":\"\",\"LINKMAN_NAME\":\"\",\"DOCUMENT_TYPE\":\"\",\"DOCUMENT_ID\":\"\",\"PHONE_NO1\":\"\",\"PHONE_NO2\":\"\"}],\"ACCT_DUE_DATE\":\"\",\"ACCT_CLASS\":\"\",\"GL_TYPE\":\"I\",\"AUTO_DEP\":\"N\",\"PARTIAL_RENEW_ROLL\":\"\",\"AMOUNT\":\"\",\"DEP_BASE_ACCT_NO\":\"\",\"DOC_CLASS\":\"\",\"ACCT_PROOF_STATUS\":\"\",\"CARD_STATUS\":\"\",\"APP_FLAG\":\"\",\"FTA_FLAG\":\"N\",\"JUDICIAL_FREEZE_FLAG\":\"N\",\"DEAL_DAY\":\"\",\"NEXT_DEAL_DATE\":\"\",\"AUP_BASE_ACCT_NO\":\"\",\"AMT_TYPE\":\"BAL\",\"LOST_STATUS\":\"\",\"PREV_DAY_BALANCE\":\"\",\"BAL_TYPE\":\"CA\",\"BRANCH_NAME\":\"\",\"BANK_CODE\":\"\"}}";
		logger.info("接收应答从Town：" + result);
		
		ESB_REP_BASE repBase = JsonUtil.toBean(result, ESB_REP_BASE.class);
		if(repBase==null){
			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
			logger.error(e.getRspCode() + " | " + e.getRspMsg());
			throw e;
		}
		else{
			if(!repBase.getRepSysHead().getRetStatus().equals("S")){	//ESB返回失败
				String rspCode=repBase.getRepSysHead().getRet().get(0).getRetCode();
				String rspMsg=repBase.getRepSysHead().getRet().get(0).getRetMsg();
				SysTradeExecuteException e = new SysTradeExecuteException(rspCode,rspMsg);
				logger.error(e.getRspCode() + " | " + e.getRspMsg());
				throw e;
			}
		}
		
		T resultModel = JsonUtil.toBean(result, clazz);
		
		return resultModel;
	}
}
