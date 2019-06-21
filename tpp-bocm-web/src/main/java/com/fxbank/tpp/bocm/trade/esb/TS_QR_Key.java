/**   
* @Title: Apply_Key.java 
* @Package com.fxbank.tpp.bocm.trade.esb 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月23日 下午3:17:16 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.trade.esb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.pub.service.IPublicService;
import com.fxbank.tpp.bocm.dto.esb.REP_TS_10104;
import com.fxbank.tpp.bocm.dto.esb.REQ_TS_10104;
import com.fxbank.tpp.bocm.model.REQ_10104;
import com.fxbank.tpp.bocm.service.IBocmSafeService;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import redis.clients.jedis.Jedis;

/** 
* @ClassName: QR_Key 
* @Description: 工作密钥申请
* @author YePuLiang
* @date 2019年5月23日 下午3:17:16 
*  
*/
@Service("REQ_TS_10104")
public class TS_QR_Key extends TradeBase implements TradeExecutionStrategy{
	
	private static Logger logger = LoggerFactory.getLogger(TS_QR_Key.class);
	
	@Reference(version = "1.0.0")
	private IBocmSafeService safeService;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "bocm_common.";
	
	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToBocmService forwardToBocmService;
	
	@Reference(version = "1.0.0")
	private IPublicService publicService;
	
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_TS_10104 reqDto = (REQ_TS_10104) dto;
		REP_TS_10104 rep = new REP_TS_10104();
		myLog.info(logger, "更新交行密钥");
		
		
		Integer sysDate = publicService.getSysDate("CIP");
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		Date d = null;
		try {
			d = df.parse(sysDate.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
		Calendar cal=Calendar.getInstance();
		cal.setTime(d);
		//TODO  调试期间用当天后期修改
//		cal.add(Calendar.DATE, -1);  //减1天
		
		Integer date = Integer.parseInt(df.format(cal.getTime()));
		Integer sysTime = publicService.getSysTime();
		Integer sysTraceno = publicService.getSysTraceno();
		
		
		//交行总行行号
		String JHNO = "";
		//阜新银行总行行号
		String FXNO = "";
		String KeyId = "";
		try(Jedis jedis = myJedis.connect()){
			//从redis中获取交行总行行号
			JHNO = jedis.get(COMMON_PREFIX+"JHNO");
			FXNO = jedis.get(COMMON_PREFIX+"FXNO");
			KeyId = jedis.get(COMMON_PREFIX+"KeyId");
        }
		
		//1.申请MAC key
		REQ_10104 reqMac10104 = new REQ_10104(myLog, date, sysTime, sysTraceno);
		reqMac10104.setSbnkNo(FXNO);
		reqMac10104.setRbnkNo(FXNO);
		//密钥ID
		reqMac10104.setKeyId(KeyId);
		//密钥类型
		reqMac10104.setKeyTyp(1);
		//密钥长度
		reqMac10104.setKeyLen(16);
		myLog.info(logger, "请求Mac密钥");
		com.fxbank.tpp.bocm.model.REP_10104 repMac10104 = forwardToBocmService.sendToBocm(reqMac10104, com.fxbank.tpp.bocm.model.REP_10104.class);
		
		String macKeyValue = repMac10104.getBlkVal();
		String macCheckValue = repMac10104.getChkVal();
		myLog.info(logger, "Mac密钥密文值【"+macKeyValue+"】密钥校验值【"+macCheckValue+"】");
		//Mac密钥 更新
		safeService.updateMacKey(myLog, macKeyValue, macCheckValue);
		
		
		//1.申请Pin key
		REQ_10104 reqPin10104 = new REQ_10104(myLog, date, sysTime, sysTraceno);
		//密钥ID
		reqPin10104.setKeyId(KeyId);
		//密钥类型
		reqPin10104.setKeyTyp(0);
		//密钥长度
		reqPin10104.setKeyLen(32);
		myLog.info(logger, "请求Pin密钥");
		com.fxbank.tpp.bocm.model.REP_10104 repPin10104 = forwardToBocmService.sendToBocm(reqPin10104, com.fxbank.tpp.bocm.model.REP_10104.class);
		
		String pinKeyValue = repPin10104.getBlkVal();
		String pinCheckValue = repMac10104.getChkVal();
		myLog.info(logger, "Pin密钥密文值【"+pinKeyValue+"】密钥校验值【"+pinCheckValue+"】");
		safeService.updatePinKey(myLog, pinKeyValue, pinCheckValue);
		
		
		return rep;
	}

}
