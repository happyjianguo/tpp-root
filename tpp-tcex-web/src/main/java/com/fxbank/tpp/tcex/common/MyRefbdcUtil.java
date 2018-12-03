package com.fxbank.tpp.tcex.common;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.tpp.tcex.constant.TCEX;
import com.tienon.util.RefbdcUtil;

import redis.clients.jedis.Jedis;

@Component
public class MyRefbdcUtil {
	private static Logger logger = LoggerFactory.getLogger(MyRefbdcUtil.class);
	
	private static final String COMMON_PREFIX = "paf_common.";
	
	@Resource
	private MyJedis myJedis;
	
	public void saveSessionKey(String encryptSessionKey) throws Exception{
		try (Jedis jedis = myJedis.connect()) {
			jedis.set(TCEX.SESSIONKEY, encryptSessionKey);
		}
	}
	
	/** 
	* @Title: encrypt 
	* @Description: 加密
	* @param @param msg
	* @param @param key
	* @param @return
	* @param @throws Exception    设定文件 
	* @return byte[]    返回类型 
	* @throws 
	*/
	public byte[] encrypt(byte[] msg) throws Exception{
		byte[] sessionKey = null;
		try (Jedis jedis = myJedis.connect()) {
			String encryptSessionKey = jedis.get(TCEX.SESSIONKEY);
			String absolutePath = jedis.get(COMMON_PREFIX+"key_path");
			sessionKey = RefbdcUtil.decryptSessionKey(absolutePath, encryptSessionKey);
			if(sessionKey==null){
				throw new RuntimeException("秘钥明文转换失败["+absolutePath+"]["+encryptSessionKey+"]");
			}
			logger.info("秘钥明文转换成功");
		}
		return RefbdcUtil.encryptMsg(msg, sessionKey);
	}
	
	/** 
	* @Title: decrypt 
	* @Description: 解密
	* @param @param msg
	* @param @param key
	* @param @return
	* @param @throws Exception    设定文件 
	* @return byte[]    返回类型 
	* @throws 
	*/
	public byte[] decrypt(byte[] msg) throws Exception{
		byte[] sessionKey = null;
		try (Jedis jedis = myJedis.connect()) {
			String encryptSessionKey = jedis.get(TCEX.SESSIONKEY);
			String absolutePath = jedis.get(COMMON_PREFIX+"key_path");
			sessionKey = RefbdcUtil.decryptSessionKey(absolutePath, encryptSessionKey);
			if(sessionKey==null){
				throw new RuntimeException("秘钥明文转换失败["+absolutePath+"]["+encryptSessionKey+"]");
			}
			logger.info("秘钥明文转换成功");
		}
		return RefbdcUtil.decryptMsg(msg, sessionKey);
	}
	
	public static void main(String[] args) throws Exception{
		String encryptSessionKey = "18f0d82e01dc930ec02b21ba7d0b51349564dad4897ac83a51f53e132b8abfb1a80cc385bd963e6a96026e8cc698deb2f1d663609d5c2c120a01d611d623f530e96b5f9fea2c0243944198105af5e03ac9b620ae40da50d742f1e8ed6d1736c780865dfa31bd331ee7474c30f3fae012787a3868f914d84d217ee6f7e0b48776";
		byte[] sessionKey = RefbdcUtil.decryptSessionKey("D:\\paf_key", encryptSessionKey);
		System.out.println(sessionKey);
	}
}
