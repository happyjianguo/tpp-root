/**   
* @Title: ForwardToFRMSService.java 
* @Package com.fxbank.tpp.frms.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年8月23日 下午4:22:00 
* @version V1.0   
*/
package com.fxbank.tpp.frms.service.impl;

import java.net.SocketTimeoutException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.common.HttpService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.tpp.frms.model.REQ_FRMS;
import com.fxbank.tpp.frms.service.IForwardToFRMSService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

/** 
* @ClassName: ForwardToFRMSService 
* @Description: 风控系统接口实现
* @author YePuLiang
* @date 2019年8月23日 下午4:22:00 
*  
*/
@Service(version = "1.0.0")
public class ForwardToFRMSService  implements IForwardToFRMSService {

	private static Logger logger = LoggerFactory.getLogger(ForwardToFRMSService.class);
	
	private static final String serviceKey = "bocm.frms_url";
	
	@Resource
	JedisSentinelPool jedisPool;
	
	@Resource
	HttpService httpService;
	

	@Override
	public <T> T sendToFRMS(REQ_FRMS request,Class<T> clazz) throws SysTradeExecuteException {
		String url ;
		try(Jedis jedis=jedisPool.getResource()){
			url = jedis.get(serviceKey);
			url = "http://57.25.2.97:9180/order";
		}
		logger.debug("风险监控服务地址：" + url);
		if (url == null) {
			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000001,"风险监控平台服务地址未配置");
			logger.debug(e.getRspCode() + " | " + e.getRspMsg());
			throw e;
		}
		
		String jsonReq = JsonUtil.toJson(request);
		jsonReq = "["+jsonReq+"]";
		
		logger.debug("发送请求至风险监控平台：" + jsonReq);
		
		String result=null;
		try {
			result = httpService.doJsonPost(url,jsonReq);
		} catch (SocketTimeoutException e) {
			logger.error(e.toString());
			SysTradeExecuteException e1 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000004,"接收风险监控平台应答超时");
			logger.error(e1.getRspCode() + " | " + e1.getRspMsg());
			throw e1;
		} catch(Exception e){
			logger.error(e.toString());
			SysTradeExecuteException e1 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000005,"调用风险监控平台服务异常");
			logger.error(e1.getRspCode() + " | " + e1.getRspMsg());
			throw e1;
		}
		logger.debug("接收风险监控应答：" + result);
		result = result.substring(1, result.length()-1);
		result = result.replaceAll("@type", "unusetype");
		T resultModel = JsonUtil.toBean(result, clazz);
		if(resultModel==null){
			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
			logger.error(e.getRspCode() + " | " + e.getRspMsg());
			throw e;
		}
		
		return resultModel;
	}
}
