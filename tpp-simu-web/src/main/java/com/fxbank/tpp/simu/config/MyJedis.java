package com.fxbank.tpp.simu.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import javax.annotation.Resource;

@Component
@ConditionalOnProperty(name = "spring.redis.enable", havingValue = "true")
@ConfigurationProperties(prefix="spring.redis")  
public class MyJedis {
	
	private Integer database;
	
	@Resource
	private JedisSentinelPool jedisPool;
	
	public Jedis connect(){
		Jedis jedis = jedisPool.getResource();
		jedis.select(database);
		return jedis;
	}

	public Integer getDatabase() {
		return database;
	}

	public void setDatabase(Integer database) {
		this.database = database;
	}
	
}
