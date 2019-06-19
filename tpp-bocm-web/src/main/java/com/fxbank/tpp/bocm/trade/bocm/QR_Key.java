/**   
* @Title: Apply_Key.java 
* @Package com.fxbank.tpp.bocm.trade.bocm 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月23日 下午3:07:03 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.trade.bocm;

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
import com.fxbank.tpp.bocm.dto.bocm.REP_10104;
import com.fxbank.tpp.bocm.dto.bocm.REQ_10104;
import com.fxbank.tpp.bocm.model.BocmSafeModel;
import com.fxbank.tpp.bocm.service.IBocmSafeService;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import redis.clients.jedis.Jedis;

/** 
* @ClassName: QR_Key 
* @Description: 工作秘钥申请
* @author YePuLiang
* @date 2019年5月23日 下午3:07:03 
*  
*/
@Service("REQ_10104")
public class QR_Key implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(QR_Key.class);
	
	@Reference(version = "1.0.0")
	private IBocmSafeService safeService;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "bocm_common.";
	
	@Resource
	private LogPool logPool;
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_10104 reqDto = (REQ_10104) dto;
		String keyModel = reqDto.getKeyId();
		//请求核心获取账户信息
		BocmSafeModel passwordModel = new BocmSafeModel(logPool.get(), reqDto.getSysDate(), 
				reqDto.getSysTime(),reqDto.getSysTraceno());
		passwordModel.setKeyModel(keyModel.trim());
		passwordModel = safeService.genKey(passwordModel);

		REP_10104 rep = new REP_10104();
		
		rep.setBlkLen(passwordModel.getKeyValue().length());
		//ZMK加密的工作密钥密文，十六进制表示的ASCII码表示   加密密钥
		rep.setBlkVal(passwordModel.getKeyValue());
		rep.setChkLen(passwordModel.getCheckValue().length());
		//工作密钥校验值，十六进制表示的ASCII码表示     报文MAC校验码
		rep.setChkVal(passwordModel.getCheckValue());
		myLog.info(logger, "获取工作密钥更新申请成功，申请密钥类型"+keyModel);
		
		String keyValue = rep.getBlkVal();
		String checkValue = rep.getChkVal();
		
		try(Jedis jedis = myJedis.connect()){
			//把从交行请求的key存储在redis中
			jedis.set(COMMON_PREFIX+"BLKVALUE", keyValue);
			//把从交行请求的校验码存储在redis中
			jedis.set(COMMON_PREFIX+"CHKVALUE", checkValue);
        }
		myLog.info(logger, "密钥定时完毕");
		myLog.info(logger, "新申请的加密密钥："+keyValue);
		myLog.info(logger, "新申请的校验码："+checkValue);
		
		return rep;
	}

}
