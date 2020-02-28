package com.fxbank.tpp.beps.trade.utils;

import com.fxbank.tpp.beps.pmts.CCMS_900_001_02;
import com.fxbank.tpp.beps.pmts.CCMS_911_001_02;
import com.fxbank.tpp.beps.pmts.CCMS_990_001_02;
import com.fxbank.tpp.beps.utils.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;

/** 
 * @author 叶浦亮
 * @Description: CCMS返回模拟报文测试
 * @date 2020年2月28日 下午2:21:46 
 */
public class CcmsRepTest {
	
	private static Logger logger = LoggerFactory.getLogger(CcmsRepTest.class);
	
	public static void main(String[] args) throws IntrospectionException {
		CCMS_900_001_02 ccms900 = new CCMS_900_001_02(null,0,0,0);
        BeanUtil.fillBean(ccms900);
        logger.info("900返回报文");
        logger.info(BeanUtil.objectToXml(ccms900));
        
        CCMS_911_001_02 ccms911 = new CCMS_911_001_02(null,0,0,0);
        BeanUtil.fillBean(ccms911);
        logger.info("911返回报文");
        logger.info(BeanUtil.objectToXml(ccms911));
		
        CCMS_990_001_02 ccms990 = new CCMS_990_001_02(null,0,0,0);
        BeanUtil.fillBean(ccms990);
        logger.info("990返回报文");
        logger.info(BeanUtil.objectToXml(ccms990));
      

	}
}
