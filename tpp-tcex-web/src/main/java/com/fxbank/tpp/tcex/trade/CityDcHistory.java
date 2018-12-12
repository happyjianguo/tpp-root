package com.fxbank.tpp.tcex.trade;

import java.util.ArrayList;
import java.util.List;

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
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.tcex.dto.esb.REP_TS004;
import com.fxbank.tpp.tcex.dto.esb.REQ_TS004;
import com.fxbank.tpp.tcex.model.RcvTraceQueryModel;
import com.fxbank.tpp.tcex.model.SndTraceQueryModel;
import com.fxbank.tpp.tcex.service.IRcvTraceService;
import com.fxbank.tpp.tcex.service.ISndTraceService;


/**
 * @ClassName: TS004
 * @Description: 商行通存村镇业务
 * @author 
 * @date 2018年4月3日 下午3:46:30
 * 
 */
@Service("REQ_TS004")
public class CityDcHistory extends TradeBase implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CityDcHistory.class);


	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IRcvTraceService rcvTraceService;
	
	@Reference(version = "1.0.0")
	private ISndTraceService sndTraceService;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		
		/**
		 * 
		
		*/
		
		//测试用
		REP_TS004 repDto = tst(myLog,dto);
		return repDto;
	}
	private REP_TS004 tst(MyLog myLog,DataTransObject dto) throws SysTradeExecuteException {
		REQ_TS004 reqDto = (REQ_TS004) dto;
		REQ_TS004.REQ_BODY reqBody = reqDto.getReqBody();
		String begDate = reqBody.getBegDate();//起始日期
		String endDate = reqBody.getEndDate();//终止日期
		String minAmt = reqBody.getMinAmt();//最小金额
		String maxAmt = reqBody.getMaxAmt();//最大金额
		String txBrno = reqBody.getTxBrno();//交易机构

		//获取往帐流水
		List<RcvTraceQueryModel> rcvTraceQueryModelList = rcvTraceService.getRcvTrace(myLog, begDate, endDate, minAmt, maxAmt, txBrno);
		
		//获取来帐流水
		List<SndTraceQueryModel> sndTraceQueryModelList = sndTraceService.getSndTrace(myLog, begDate, endDate, minAmt, maxAmt, txBrno);

		REP_TS004 repDto = new REP_TS004();
		REP_TS004.REP_BODY repBody = repDto.getRepBody();
		List<REP_TS004.TMSG> list = new ArrayList<>();
		for(RcvTraceQueryModel rcv : rcvTraceQueryModelList) {
			REP_TS004.TMSG t = repDto.new TMSG();
			t.setPlatDate(rcv.getPlatDate()==null?"":rcv.getPlatDate().toString());
			t.setPlatTraceno(rcv.getPlatTrace()==null?"":rcv.getPlatTrace().toString());
			t.setDcFlag(rcv.getDcFlag());
			t.setChnl(rcv.getSourceType());
			t.setTownBrno(rcv.getTownBranch());
			t.setHostTraceno(rcv.getHostTraceno());
			t.setHostDate(rcv.getHostDate()==null?"":rcv.getHostDate().toString());
			t.setOurState(rcv.getHostState());
			t.setTownState(rcv.getTownState());
			t.setChkState(rcv.getCheckFlag());
			t.setTxAmt(rcv.getTxAmt()==null?"":rcv.getTxAmt().toString());
			t.setTxInd(rcv.getTxInd());
			t.setPayeeAcno(rcv.getPayeeAcno());
			t.setPayeeName(rcv.getPayeeName());
			t.setPayerAcno(rcv.getPayerAcno());
			t.setPayerName(rcv.getPayerName());
			t.setTxTel(rcv.getTxTel());
			t.setChkTel(rcv.getChkTel());
			t.setAuthTel(rcv.getAuthTel());
			t.setPrint(rcv.getPrint());
			t.setInfo(rcv.getInfo());
			list.add(t);
		}
		for(SndTraceQueryModel rcv : sndTraceQueryModelList) {
			REP_TS004.TMSG t = repDto.new TMSG();
			t.setPlatDate(rcv.getPlatDate()==null?"":rcv.getPlatDate().toString());
			t.setPlatTraceno(rcv.getPlatTrace()==null?"":rcv.getPlatTrace().toString());
			t.setDcFlag(rcv.getDcFlag());
			t.setChnl(rcv.getSourceType());
			t.setTownBrno(rcv.getTownBranch());
			t.setHostTraceno(rcv.getHostTraceno());
			t.setHostDate(rcv.getHostDate()==null?"":rcv.getHostDate().toString());
			t.setOurState(rcv.getHostState());
			t.setTownState(rcv.getTownState());
			t.setChkState(rcv.getCheckFlag());
			t.setTxAmt(rcv.getTxAmt()==null?"":rcv.getTxAmt().toString());
			t.setTxInd(rcv.getTxInd());
			t.setPayeeAcno(rcv.getPayeeAcno());
			t.setPayeeName(rcv.getPayeeName());
			t.setPayerAcno(rcv.getPayerAcno());
			t.setPayerName(rcv.getPayerName());
			t.setTxTel(rcv.getTxTel());
			t.setChkTel(rcv.getChkTel());
			t.setAuthTel(rcv.getAuthTel());
			t.setPrint(rcv.getPrint());
			t.setInfo(rcv.getInfo());
			list.add(t);
		}
		repBody.setArrayMsg(list);
		return repDto;
	}

}
