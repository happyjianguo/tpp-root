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
import com.fxbank.tpp.bocm.dto.esb.REP_QR_CB;
import com.fxbank.tpp.bocm.model.BocmAcctCheckErrModel;
import com.fxbank.tpp.bocm.service.IBocmAcctCheckErrService;

/** 
* @ClassName: QR_CB 
* @Description: 调账查询
* @author YePuLiang
* @date 2019年7月10日 下午4:41:36 
*  
*/
@Service("REQ_QR_CB")
public class QR_CB extends TradeBase implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(QR_Trace.class);
	
	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IBocmAcctCheckErrService acctCheckErrService;
	
	@Resource
	private MyJedis myJedis;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		
		MyLog myLog = logPool.get();
		String begDate = "20190621";
		String endDate = "20190621";
		String payeeAcno="";
		String hostTraceno="";
		String platTraceno="";
		
		List<BocmAcctCheckErrModel> errlist = acctCheckErrService.getListByDate(myLog, dto.getSysTime(), dto.getSysDate(), dto.getSysTraceno(), begDate, endDate);
		logger.info("调账记录:  "+errlist.size());
		for(BocmAcctCheckErrModel model : errlist){
			logger.info("渠道流水 号："+model.getPlatTrace());
		}
		
		REP_QR_CB rep = new REP_QR_CB();
		return rep;
	}
}
