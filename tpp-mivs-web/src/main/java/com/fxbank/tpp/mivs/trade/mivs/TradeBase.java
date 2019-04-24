package com.fxbank.tpp.mivs.trade.mivs;

import javax.annotation.Resource;

import com.fxbank.cip.base.common.MyJedis;

import redis.clients.jedis.Jedis;

public class TradeBase {

	@Resource
    private MyJedis myJedis;

	public void jedisPublish(byte[] key,byte[] value){
		try(Jedis jedis=myJedis.connect()){
            jedis.publish(key,value);
        }
	}
}
