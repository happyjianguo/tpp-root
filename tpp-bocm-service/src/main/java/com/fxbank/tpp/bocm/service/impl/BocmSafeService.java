package com.fxbank.tpp.bocm.service.impl;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.service.IBocmSafeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.highsuccess.connPool.api.tssc.HisuTSSCAPI;
import cn.highsuccess.connPool.api.tssc.HisuTSSCAPIResult;

/** 
* @ClassName: BocmSafeService 
* @Description: 加密平台调用实现
* @author YePuLiang
* @date 2019年5月23日 下午3:58:35 
*  
*/
@Service(version = "1.0.0")
@Component
public class BocmSafeService implements IBocmSafeService{

	private static Logger logger = LoggerFactory.getLogger(BocmSafeService.class);
	
	@Resource
	private HisuTSSCAPI hisuTSSCAPI;
	
	@Override
	public String transPinToFX(MyLog myLog, String srcAccount, String dstAccount, String srcPinBlock)
			throws SysTradeExecuteException {
		//加密平台转PIN
		try {
			HisuTSSCAPIResult rzpkPwd = hisuTSSCAPI.transPinBlockFromZPKToZPK("JINP", "COUTER001", "XZAK", 
					"JINP1", "COUTER0012", "XZAK2", srcAccount, dstAccount, srcPinBlock);			
			if (rzpkPwd.getErrCode() < 0) {
				myLog.error(logger, "调用加密平台PIN转加密失败");
				throw new BocmTradeExecuteException(BocmTradeExecuteException.TPP_E_000001);
			}
			return rzpkPwd.getPinBlock();
		} catch (Exception e) {
			throw new BocmTradeExecuteException(BocmTradeExecuteException.TPP_E_000001);
		}
		
	}

	@Override
	public String transPinToJH(MyLog myLog, String srcAccount, String dstAccount, String srcPinBlock)
			throws SysTradeExecuteException {
		//加密平台转PIN
		try {
			HisuTSSCAPIResult rzpkPwd = hisuTSSCAPI.transPinBlockFromZPKToZPK("PINP", "pinpToesb", "RZPK", 
					"JINP", "COUTER001", "XZPK", srcAccount, dstAccount, srcPinBlock);	
			if (rzpkPwd.getErrCode() < 0) {
				myLog.error(logger, "调用加密平台PIN转加密失败");
				throw new BocmTradeExecuteException(BocmTradeExecuteException.TPP_E_000001);
			}
			return rzpkPwd.getPinBlock();
		} catch (Exception e) {
			throw new BocmTradeExecuteException(BocmTradeExecuteException.TPP_E_000001);
		}
	}

	/**
	* @Title: verifyBocmMac 
	* @Description: 验证交行MAC
	* @param @param myLog
	* @param @param dataToMAC
	* @param @param macValue
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws
	 */
	@Override
	public void verifyBocmMac(MyLog myLog, String dataToMAC, String macValue) throws SysTradeExecuteException {
		myLog.info(logger, "校验交行MAC");
		try {
			HisuTSSCAPIResult result = hisuTSSCAPI.verifyDataMACBySpecKey("JINP", "COUTER001", "XZAK",1, dataToMAC, macValue);
			if(result.getErrCode() < 0) {
				myLog.error(logger, "调用加密平台MAC校验失败");
				throw new BocmTradeExecuteException(BocmTradeExecuteException.TPP_E_000004);
			}
		} catch (Exception e) {
			myLog.error(logger, "调用加密平台MAC校验失败", e);
			throw new BocmTradeExecuteException(BocmTradeExecuteException.TPP_E_000004);
		}
	}
	
	//计算交行MAC
	@Override
	public String calcBocmMac(MyLog myLog,String dataToMAC) throws SysTradeExecuteException {
		try {
			HisuTSSCAPIResult result = hisuTSSCAPI.calculateDataMACBySpecKey("JINP", "COUTER001","XZAK", 1, dataToMAC);
			if (result.getErrCode() < 0) {
				myLog.error(logger, "调用加密平台计算MAC失败");
				throw new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000003);
			}
			return result.getMAC();
		} catch (Exception e) {
			myLog.error(logger, "调用加密平台计算MAC失败", e);
			throw new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000003);
		}
	}

	//更新mac密钥
	public void updateMacKey(MyLog myLog, String keyValue, String checkValue) throws SysTradeExecuteException {
		try {
			myLog.error(logger, "更新Mac密钥:  key:【"+keyValue+"】校验码【"+checkValue+"】");
			HisuTSSCAPIResult result = hisuTSSCAPI.tsscStoreKey("JINP", "COUTER001", "XZMK", "JINP","COUTER001", "XZAK", keyValue, checkValue);
			myLog.error(logger, "返回错误码: 【"+result.getErrCode()+"】");
			if (result.getErrCode() < 0) {
				myLog.error(logger, "更新Mac密钥失败");
				throw new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000003);
			}
		} catch (Exception e) {
			myLog.error(logger, "更新Mac密钥失败", e);
			throw new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000003);
		}
	}

	@Override
	public void updatePinKey(MyLog myLog, String keyValue, String checkValue) throws SysTradeExecuteException {
		try {
			myLog.error(logger, "更新Pin密钥:  key:【"+keyValue+"】校验码【"+checkValue+"】");
			HisuTSSCAPIResult result = hisuTSSCAPI.tsscStoreKey("JINP", "COUTER001", "XZMK", "JINP","COUTER001", "XZPK", keyValue, checkValue);
			myLog.error(logger, "返回错误码: 【"+result.getErrCode()+"】");
			if (result.getErrCode() < 0) {
				myLog.error(logger, "更新Pin密钥失败");
				throw new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000003);
			}
		} catch (Exception e) {
			myLog.error(logger, "更新Pin密钥失败", e);
			throw new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000003);
		}
	}

}