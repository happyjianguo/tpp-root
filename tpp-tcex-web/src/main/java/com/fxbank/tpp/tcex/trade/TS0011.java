package com.fxbank.tpp.tcex.trade;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.tcex.dto.esb.REP_TS0011;
/**
 * 商行通存业务
 * @author liye
 *
 */
@Service("REQ_TS0011")
public class TS0011 implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CityDeposit.class);

	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	static SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REP_TS0011 repDto = new REP_TS0011();
		/**
		List<REP_RET> list = new ArrayList<REP_RET>();
		REP_RET ret = new REP_RET();
		ret.setRetCode("@@@@");
		ret.setRetMsg("@@@@");
		list.add(ret);
		repDto.getRepSysHead().setRet(list);
		repDto.getRepSysHead().setRetStatus("F");
		
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			**/
		repDto.getRepBody().setBrno("10001");
		repDto.getRepBody().setTownDate(sdf1.format(new Date()));
		repDto.getRepBody().setTownTraceno(UUID.randomUUID().toString().replace("-", "").substring(0, 15));
		repDto.getRepBody().setBackTal("10000.00");
		return repDto;
	}
}
