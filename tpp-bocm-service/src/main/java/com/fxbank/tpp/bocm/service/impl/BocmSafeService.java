/**   
* @Title: BocmSafeService.java 
* @Package com.fxbank.tpp.bocm.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月23日 下午3:58:35 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.common.BOCM;
import com.fxbank.tpp.bocm.common.ESB;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmSafeModel;
import com.fxbank.tpp.bocm.service.IBocmSafeService;

import cn.highsuccess.connPool.api.tssc.HisuTSSCAPI;
import cn.highsuccess.connPool.api.tssc.HisuTSSCAPIResult;
import redis.clients.jedis.Jedis;

/** 
* @ClassName: BocmSafeService 
* @Description: 加密平台调用实现
* @author YePuLiang
* @date 2019年5月23日 下午3:58:35 
*  
*/
@Service(version = "1.0.0")
public class BocmSafeService implements IBocmSafeService{

	private static Logger logger = LoggerFactory.getLogger(BocmSafeService.class);
	
	private final static String COMMON_PREFIX = "bocm_common.";

	@Resource
	private MyJedis myJedis;
	
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
//			myLog.error(logger, "调用加密平台PIN转加密失败", e);
			throw new BocmTradeExecuteException(BocmTradeExecuteException.TPP_E_000001);
		}
		
	}

	@Override
	public String transPinToJH(MyLog myLog, String srcAccount, String dstAccount, String srcPinBlock)
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
//			myLog.error(logger, "调用加密平台PIN转加密失败", e);
			throw new BocmTradeExecuteException(BocmTradeExecuteException.TPP_E_000001);
		}
	}

	/** 
	* @Title: encryptPwd 
	* @Description: 使用ZPK加密明文PIN
	* @param @param model
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public BocmSafeModel encryptPwd(BocmSafeModel model) throws SysTradeExecuteException {
		MyLog myLog = model.getMylog();
		String password = model.getPassword();
		String acctNo = model.getAcctNo();
		String deginId = "";
		String nodeId = "";
		String keyModelId = "";
		String encryptParameter = "";
		try (Jedis jedis = myJedis.connect()) {
			encryptParameter = jedis.get(COMMON_PREFIX + "HOST");
			String[] temp = encryptParameter.split("\\|");
			deginId = temp[0];
			nodeId = temp[1];
			keyModelId = temp[2];
		}
		try {
			HisuTSSCAPIResult rzpkPwd = hisuTSSCAPI.calculateCipherPinByZPK8008(deginId, 
					nodeId, keyModelId,acctNo,password);
			if (rzpkPwd.getErrCode() < 0) {
				myLog.error(logger, "调用加密平台ZPK加密明文PIN失败");
				throw new BocmTradeExecuteException(BocmTradeExecuteException.TPP_E_000002);
			}
			model.setPassword(rzpkPwd.getPinBlock());
		} catch (Exception e) {
			myLog.error(logger, "调用加密平台ZPK加密明文PIN失败", e);
			throw new BocmTradeExecuteException(BocmTradeExecuteException.TPP_E_000002);
		}
		return model;
	}

	/** 
	* @Title: genKey 
	* @Description: 工作密钥更新申请
	* @param @param model
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public BocmSafeModel genKey(BocmSafeModel model) throws SysTradeExecuteException {
		MyLog myLog = model.getMylog();
		String keyModel = model.getKeyModel();
		String deginId = "";
		String nodeId = "";
		String keyModelId = "";
		String deginId1 = "";
		String nodeId1 = "";
		String keyModelId1 = "";
		String encryptParameter = "";
		try (Jedis jedis = myJedis.connect()) {
			encryptParameter = jedis.get(COMMON_PREFIX + keyModel);
			String[] temp = encryptParameter.split("\\|");
			deginId = temp[0];
			nodeId = temp[1];
			keyModelId = temp[2];
			deginId1 = temp[3];
			nodeId1 = temp[4];
			keyModelId1 = temp[5];
		}
		try {
			HisuTSSCAPIResult rzpkPwd = hisuTSSCAPI.tsscGenKey(deginId, nodeId, keyModelId, 
					deginId1, nodeId1, keyModelId1);
			if (rzpkPwd.getErrCode() < 0) {
				myLog.error(logger, "调用加密平台工作密钥更新失败");
				throw new BocmTradeExecuteException(BocmTradeExecuteException.TPP_E_000003);
			}
			model.setKeyValue(rzpkPwd.getKeyValue());
			model.setCheckValue(rzpkPwd.getCheckValue());
		} catch (Exception e) {
			myLog.error(logger, "调用加密平台工作密钥更新失败", e);
			throw new BocmTradeExecuteException(BocmTradeExecuteException.TPP_E_000003);
		}
		return model;
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
//			String[] macParameter = macValue.split("\\|");
//			String deginId = macParameter[0];
//			String nodeId = macParameter[1];
//			String keyModelId = macParameter[2];
//			String mac = macParameter[3];
//			if(!BOCM.macDeginId.equals(deginId)) {
//				myLog.error(logger, "调用加密平台MAC校验失败");
//				throw new BocmTradeExecuteException(BocmTradeExecuteException.TPP_E_000004);
//			}
//			if(!BOCM.macNodeId.equals(nodeId)) {
//				myLog.error(logger, "调用加密平台MAC校验失败");
//				throw new BocmTradeExecuteException(BocmTradeExecuteException.TPP_E_000004);
//			}
//			if(!BOCM.macKeyModelId.equals(keyModelId)) {
//				myLog.error(logger, "调用加密平台MAC校验失败");
//				throw new BocmTradeExecuteException(BocmTradeExecuteException.TPP_E_000004);
//			}
//			HisuTSSCAPIResult result = hisuTSSCAPI.verifyHashDataMACBySpecKeyBytes(BOCM.macDeginId, BOCM.macNodeId,
//					BOCM.macKeyModelId,1,2,dataToMAC,dataToMAC.length,mac);
			
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
	
	@Override
	public void verifyCityMac(MyLog myLog, byte[] dataToMAC,String macValue) throws SysTradeExecuteException {
		try {
			String[] macParameter = macValue.split("\\|");
			String deginId = macParameter[0];
			String nodeId = macParameter[1];
			String keyModelId = macParameter[2];
			String mac = macParameter[3];
			if(!ESB.macDeginId.equals(deginId)) {
				myLog.error(logger, "调用加密平台MAC校验失败");
				throw new BocmTradeExecuteException(BocmTradeExecuteException.TPP_E_000004);
			}
			if(!ESB.macNodeId.equals(nodeId)) {
				myLog.error(logger, "调用加密平台MAC校验失败");
				throw new BocmTradeExecuteException(BocmTradeExecuteException.TPP_E_000004);
			}
			if(!ESB.macKeyModelId.equals(keyModelId)) {
				myLog.error(logger, "调用加密平台MAC校验失败");
				throw new BocmTradeExecuteException(BocmTradeExecuteException.TPP_E_000004);
			}
			HisuTSSCAPIResult result = hisuTSSCAPI.verifyHashDataMACBySpecKeyBytes(ESB.macDeginId, ESB.macNodeId,
					ESB.macKeyModelId,1,2,dataToMAC,dataToMAC.length,mac);
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
	public String calcBocm(MyLog myLog,String dataToMAC) throws SysTradeExecuteException {
		try {
//			HisuTSSCAPIResult result = hisuTSSCAPI.calculateHashDataMACBySpecKeyBytes(BOCM.macDeginId, BOCM.macNodeId,
//					BOCM.macKeyModelId, 1, 2, dataToMAC, dataToMAC.length);
			
			HisuTSSCAPIResult result = hisuTSSCAPI.calculateDataMACBySpecKey("JINP", "COUTER001","XZAK", 1, dataToMAC);
//			hisuTSSCAPI.calculateDataMACBySpecKey(designID, nodeID, keyModelID, keyTypeFlag, dataToMAC)
//			hisuTSSCAPI.
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
	public String calcCITY(MyLog myLog,byte[] dataToMAC) throws SysTradeExecuteException {
		try {
			HisuTSSCAPIResult result = hisuTSSCAPI.calculateHashDataMACBySpecKeyBytes(ESB.macDeginId, ESB.macNodeId,
					ESB.macKeyModelId, 1, 2, dataToMAC, dataToMAC.length);
			if (result.getErrCode() < 0) {
				myLog.error(logger, "调用加密平台计算MAC失败");
				throw new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000003);
			}
			return ESB.macDeginId+"|"+ESB.macNodeId+"|"+ESB.macKeyModelId+"|"+result.getMAC()+"|";
		} catch (Exception e) {
			myLog.error(logger, "调用加密平台计算MAC失败", e);
			throw new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000003);
		}
	}

}
