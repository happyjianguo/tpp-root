package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.tcex.dto.esb.REP_TR004;
import com.fxbank.tpp.tcex.dto.esb.REQ_TR004;
import com.fxbank.tpp.tcex.model.RcvTraceInitModel;
import com.fxbank.tpp.tcex.model.RcvTraceUpdModel;
import com.fxbank.tpp.tcex.service.IRcvTraceService;
/**
 * 村镇冲正业务
 * @author liye
 *
 */
@Service("REQ_TR004")
public class TownReversal implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(CityDeposit.class);


	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IRcvTraceService rcvTraceService;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		
		REQ_TR004 reqDto = (REQ_TR004) dto;
		String platDate = reqDto.getReqBody().getPlatDate();
		String platTraceno = reqDto.getReqBody().getPlatTraceno();

		//调用核心冲正接口
		String hostState = "333";
		
		RcvTraceUpdModel record = new RcvTraceUpdModel(myLog, Integer.parseInt(platDate), reqDto.getSysTime(),Integer.parseInt(platTraceno));
//		record.setPlatDate(Integer.parseInt(platDate));
//		record.setPlatTrace(Integer.parseInt(platTraceno));
		record.setHostState(hostState);
		rcvTraceService.rcvTraceUpd(record);
		
		REP_TR004 repDto = new REP_TR004();
		repDto.getRepBody().setSts(hostState);

		return repDto;
	}
	
//	村镇发来张
//    渠道发往帐
//	private void initRecord(MyLog myLog,REQ_TR004 reqDto) throws SysTradeExecuteException {
//		
//		REQ_TR004.REQ_BODY reqBody = reqDto.getReqBody();
//		REQ_SYS_HEAD reqSysHead = reqDto.getReqSysHead();
//		
//		RcvTraceInitModel record = new RcvTraceInitModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
//		record.setSourceType(reqBody.getChnl());
//		record.setTxBranch(reqSysHead.getBranchId());
//		//现转标志 0现金1转账
//		record.setTxInd(reqBody.getTxInd());
//		//通存通兑
//		record.setDcFlag("1");
//		record.setTxAmt(reqBody.getTxAmt());
//		if("1".equals(reqBody.getTxInd())) {
//		record.setPayeeAcno(reqBody.getPayerAcc());
//		record.setPayeeName(reqBody.getPayerName());
//		}
//		record.setPayerAcno(reqBody.getPayeeAcc());
//		record.setPayerName(reqBody.getPayeeName());
//		record.setHostState("0");
//		record.setTxTel(reqSysHead.getUserId());
//		//record.setChkTel();
//		//record.setAuthTel();
//		record.setInfo(reqBody.getInfo());
//		rcvTraceService.rcvTraceInit(record);
//	}

}
