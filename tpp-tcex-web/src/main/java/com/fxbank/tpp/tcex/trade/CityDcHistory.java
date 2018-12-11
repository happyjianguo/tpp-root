package com.fxbank.tpp.tcex.trade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelPage;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.tcex.dto.esb.REP_TS004;
import com.fxbank.tpp.tcex.dto.esb.REP_TS004.REP_BODY.T;
import com.fxbank.tpp.tcex.dto.esb.REQ_TS004;


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

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_TS004 reqDto = (REQ_TS004) dto;
		REQ_TS004.REQ_BODY reqBody = reqDto.getReqBody();
		REP_TS004 repDto = new REP_TS004();
		REP_TS004.REP_BODY repBody = repDto.getRepBody();
		REP_TS004.REP_BODY.T t = repBody.new T();
		t.setPlatDate("20181206");
		t.setPlatTraceno("45551");
		t.setDcFlag("1");
		t.setChnl("cdf");
		t.setTownBrno("3243");
		t.setHostTraceno("");
		t.setHostDate("");
		t.setOurState("");
		t.setTownState("");
		t.setChkState("");
		t.setTxAmt("");
		t.setTxInd("");
		t.setPayeeAcno("");
		t.setPayeeName("");
		t.setPayerAcno("");
		t.setPayerName("");
		t.setTxTel("");
		t.setChkTel("");
		t.setAuthTel("");
		t.setPrint("");
		t.setInfo("");
		List<REP_TS004.REP_BODY.T> list = repBody.getaT();
		list.add(t);
		return repDto;
	}

	

}
