package com.fxbank.tpp.simu.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@ConditionalOnProperty(name = "spring.redis.enable", havingValue = "true")
@ConfigurationProperties(prefix="spring.redis")  
public class RedisConfig {

	private String master;
	private String nodes;
	private String password;
	private Integer max_active;
	private Integer max_idle;
	private Integer max_wait;
	private Integer timeout;
	private Boolean test_on_borrow;
	
	@Bean
	@Deprecated
	public JedisSentinelPool getRedisPool(){
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(this.max_active);
		config.setMaxIdle(this.max_idle);
		config.setMaxWaitMillis(this.max_wait);
		config.setTestOnBorrow(this.test_on_borrow);
	    Set<String> sentinels = new HashSet<String>(Arrays.asList(nodes.split(","))); 
		return new JedisSentinelPool(master, sentinels,config,this.password);
	}
	
	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getNodes() {
		return nodes;
	}

	public void setNodes(String nodes) {
		this.nodes = nodes;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getMax_active() {
		return max_active;
	}

	public void setMax_active(Integer max_active) {
		this.max_active = max_active;
	}

	public Integer getMax_idle() {
		return max_idle;
	}

	public void setMax_idle(Integer max_idle) {
		this.max_idle = max_idle;
	}

	public Integer getMax_wait() {
		return max_wait;
	}

	public void setMax_wait(Integer max_wait) {
		this.max_wait = max_wait;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Boolean getTest_on_borrow() {
		return test_on_borrow;
	}

	public void setTest_on_borrow(Boolean test_on_borrow) {
		this.test_on_borrow = test_on_borrow;
	}
	
}