package com.fxbank.tpp.tcex.common;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fxbank.cip.base.exception.SysTradeExecuteException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@Component
public class ParmGeter {
	
	@Resource
	private JedisSentinelPool jedisPool;
	
	public String getValue(String prefix,String key) throws SysTradeExecuteException{
		String value;
		try (Jedis jedis = jedisPool.getResource()) {
			if (jedis == null) {
				SysTradeExecuteException e = new SysTradeExecuteException(
						SysTradeExecuteException.CIP_E_000001);
				throw e;
			}
			value = jedis.get(prefix+key);
			if (value == null) {
				SysTradeExecuteException e = new SysTradeExecuteException(
						SysTradeExecuteException.CIP_E_000001);
				throw e;
			}
		}
		return value;
	}
}
