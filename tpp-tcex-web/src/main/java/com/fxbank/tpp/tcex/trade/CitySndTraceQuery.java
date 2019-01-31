package com.fxbank.tpp.tcex.trade;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.tcex.dto.esb.REP_30043002701;
import com.fxbank.tpp.tcex.dto.esb.REP_30043002701.TMSGS;
import com.fxbank.tpp.tcex.dto.esb.REQ_30043002701;
import com.fxbank.tpp.tcex.model.SndTraceQueryModel;
import com.fxbank.tpp.tcex.service.ISndTraceService;

import redis.clients.jedis.Jedis;


/**
 * @ClassName: 30043002701
 * @Description: 村镇柜面通往账查询
 * @author 
 * @date 2018年4月3日 下午3:46:30
 * 
 */
@Service("REQ_30043002701")
public class CitySndTraceQuery extends TradeBase implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CitySndTraceQuery.class);

	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private ISndTraceService sndTraceService;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "tcex_common.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		REQ_30043002701 reqDto = (REQ_30043002701) dto;
		REQ_30043002701.REQ_BODY reqBody = reqDto.getReqBody();
		String begDate = reqBody.getStartDate();//起始日期
		String endDate = reqBody.getEndDate();//终止日期
		String minAmt = reqBody.getMinAmt();//最小金额
		String maxAmt = reqBody.getMaxAmt();//最大金额
		String brnoFlag = reqBody.getBrnoFlag();//村镇机构
		
		//获取往帐流水
		List<SndTraceQueryModel> sndTraceQueryModelList = sndTraceService.getSndTrace(myLog, begDate, endDate, minAmt, maxAmt, brnoFlag);

		REP_30043002701 repDto = new REP_30043002701();
		REP_30043002701.REP_BODY repBody = repDto.getRepBody();
		List<REP_30043002701.TMSGS> list = new ArrayList<>();
		for(SndTraceQueryModel rcv : sndTraceQueryModelList) {
			REP_30043002701.TMSGS t = new TMSGS();
			t.setSystemDate(rcv.getPlatDate()==null?"":rcv.getPlatDate().toString());
			t.setSystemReference(rcv.getPlatTrace()==null?"":rcv.getPlatTrace().toString());
			t.setDepDraInd(getParaValue("dc_flag",rcv.getDcFlag()));
			t.setChannelType(rcv.getSourceType());
			t.setVillageBrnachId(rcv.getTownBranch());
			t.setHostSeqNo(rcv.getHostTraceno());
			t.setHostDt(rcv.getHostDate()==null?"":rcv.getHostDate().toString());
			t.setCbsTranSts(getParaValue("host_state",rcv.getHostState()));
			t.setVillageTranSts(getParaValue("town_state",rcv.getTownState()));
			t.setCollateSts(getParaValue("check_flag",rcv.getCheckFlag()));
			t.setTranAmt(rcv.getTxAmt()==null?"":rcv.getTxAmt().toString());
			t.setMfflg(getParaValue("tx_ind",rcv.getTxInd()));
			t.setPayeeAcctNo(rcv.getPayeeAcno());
			t.setPayeeAcctName(rcv.getPayeeName());
			t.setPayerAcctNo(rcv.getPayerAcno());
			t.setPayerName(rcv.getPayerName());
			t.setOfficerId(rcv.getTxTel());
			t.setApprUserId(rcv.getChkTel());
			t.setAuthUserId(rcv.getAuthTel());
			t.setPrintCount(rcv.getPrint());
			t.setNarrative(rcv.getInfo());
			list.add(t);
		}
		repBody.setArrayMsg(list);
		return repDto;
	
	}
	
	private String getParaValue(String rkey,String para) {
		String dcFlag="";
		try(Jedis jedis = myJedis.connect()){
			dcFlag = jedis.get(COMMON_PREFIX+rkey);
        }
		JSONObject json= JSONObject.parseObject(dcFlag);
		return json.getString(para);
	}

}
