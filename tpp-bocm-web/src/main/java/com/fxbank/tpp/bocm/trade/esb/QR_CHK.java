package com.fxbank.tpp.bocm.trade.esb;

import java.util.List;

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
import com.fxbank.tpp.bocm.dto.esb.REP_QR_CHK;
import com.fxbank.tpp.bocm.model.BocmAcctCheckErrModel;
import com.fxbank.tpp.bocm.model.BocmChkStatusModel;
import com.fxbank.tpp.bocm.service.IBocmAcctCheckErrService;
import com.fxbank.tpp.bocm.service.IBocmChkStatusService;

/** 
* @ClassName: QR_CHK 
* @Description: 对账查询
* @author YePuLiang
* @date 2019年7月10日 下午5:03:47 
*  
*/
@Service("REQ_QR_CHK")
public class QR_CHK extends TradeBase implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(QR_CHK.class);
	
	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IBocmChkStatusService chkStatusService;
	
	@Resource
	private MyJedis myJedis;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		
		MyLog myLog = logPool.get();
		String begDate = "20190710";
		String endDate = "20190710";
		String payeeAcno="";
		String hostTraceno="";
		String platTraceno="";
		
		
		
		List<BocmChkStatusModel> errlist = chkStatusService.selectByDate(myLog,begDate, endDate);
		logger.info("对账记录:  "+errlist.size());
		for(BocmChkStatusModel model : errlist){
			logger.info("主机对账状态："+model.getHostStatus());
			logger.info("交行对账状态："+model.getBocmStatus());
			logger.info("平台对账状态："+model.getPlatStatus());
		}
		
		REP_QR_CHK rep = new REP_QR_CHK();
		return rep;
	}
}
