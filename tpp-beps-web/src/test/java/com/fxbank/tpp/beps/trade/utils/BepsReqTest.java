package com.fxbank.tpp.beps.trade.utils;

import java.beans.IntrospectionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fxbank.tpp.beps.pmts.BEPS_351_001_01;
import com.fxbank.tpp.beps.pmts.BEPS_352_001_01;
import com.fxbank.tpp.beps.pmts.BEPS_353_001_01;

/** 
 * @author 叶浦亮
 * @Description: 351,352,353报文测试类
 * @date 2020年2月28日 下午1:53:13 
 */
public class BepsReqTest {
	
	private static Logger logger = LoggerFactory.getLogger(BepsReqTest.class);
	
	public static void main(String[] args) throws IntrospectionException {
        BEPS_351_001_01 beps351 = new BEPS_351_001_01(null,0,0,0);
        BeanUtil.fillBean(beps351);
        logger.info("351请求报文");
        logger.info(BeanUtil.objectToXml(beps351));
        
        BEPS_352_001_01 beps352 = new BEPS_352_001_01(null,0,0,0);
        BeanUtil.fillBean(beps352);
        logger.info("352请求报文");
        logger.info(BeanUtil.objectToXml(beps352));
        
        BEPS_353_001_01 beps353 = new BEPS_353_001_01(null,0,0,0);
        BeanUtil.fillBean(beps353);
        logger.info("353请求报文");
        logger.info(BeanUtil.objectToXml(beps353));
	}

}
