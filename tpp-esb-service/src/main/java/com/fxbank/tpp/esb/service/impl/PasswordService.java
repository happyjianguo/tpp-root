package com.fxbank.tpp.esb.service.impl;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.esb.exception.ESBTradeExecuteException;
import com.fxbank.tpp.esb.model.ses.PasswordModel;
import com.fxbank.tpp.esb.service.IPasswordService;

import cn.highsuccess.connPool.api.tssc.HisuTSSCAPI;
import cn.highsuccess.connPool.api.tssc.HisuTSSCAPIResult;
import redis.clients.jedis.Jedis;

@Service(version = "1.0.0")
public class PasswordService implements IPasswordService {

	private static Logger logger = LoggerFactory.getLogger(PasswordService.class);
	private final static String COMMON_PREFIX = "tcex_common.";

	@Resource
	private MyJedis myJedis;
	
	@Resource
	private HisuTSSCAPI hisuTSSCAPI;
	
	@Override
	public PasswordModel transPin(PasswordModel model) throws SysTradeExecuteException {
		MyLog myLog = model.getMylog();
		String password = model.getPassword();
		String acctNo = model.getAcctNo();
		String deginId = "";
		String nodeId = "";
		String keyModelId = "";
		String deginId1 = "";
		String nodeId1 = "";
		String keyModelId1 = "";
		String encryptParameter = "";
		String sPINKey = model.getsPINKey();
		try (Jedis jedis = myJedis.connect()) {
			encryptParameter = jedis.get(COMMON_PREFIX + model.getSourceType());
			String[] temp = encryptParameter.split("\\|");
			deginId = temp[0];
			nodeId = temp[1];
			keyModelId = temp[2];
			encryptParameter = jedis.get(COMMON_PREFIX + "HOST");
			temp = encryptParameter.split("\\|");
			deginId1 = temp[0];
			nodeId1 = temp[1];
			keyModelId1 = temp[2];
		}
		//加密平台转PIN
		try {
			HisuTSSCAPIResult rzpkPwd = hisuTSSCAPI.transPinBlockFromZPKToZPK(deginId, nodeId, keyModelId, deginId1,
					nodeId1, keyModelId1, sPINKey, acctNo, password);
			if (rzpkPwd.getErrCode() < 0) {
				myLog.error(logger, "调用加密平台加密失败");
				throw new ESBTradeExecuteException(ESBTradeExecuteException.ESB_E_000001);
			}
			password = rzpkPwd.getPinBlock();
		} catch (Exception e) {
			myLog.error(logger, "调用加密平台加密失败", e);
			throw new ESBTradeExecuteException(ESBTradeExecuteException.ESB_E_000001);
		}
		model.setPassword(password);
		return model;
	}

	/** 
	* @Title: encryptPwd 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param model
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public String encryptPwd(MyLog myLog,String pwd) throws SysTradeExecuteException {
		String password = null;
		byte[] arbyte = pwd.getBytes();
		try {
			HisuTSSCAPIResult rzpkPwd = hisuTSSCAPI.encryptDataBySpecKeyBytes("CZBK", "czbkTotass", "RZPK",3, 0, null, arbyte, arbyte.length);
			if (rzpkPwd.getErrCode() < 0) {
				myLog.error(logger, "调用加密平台加密失败");
				throw new ESBTradeExecuteException(ESBTradeExecuteException.ESB_E_000001);
			}
			pwd = rzpkPwd.getCipherText();
		} catch (Exception e) {
			myLog.error(logger, "调用加密平台加密失败", e);
			throw new ESBTradeExecuteException(ESBTradeExecuteException.ESB_E_000001);
		}
		return password;
	}
}
