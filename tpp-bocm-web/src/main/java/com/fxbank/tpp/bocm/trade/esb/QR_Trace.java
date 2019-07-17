package com.fxbank.tpp.bocm.trade.esb;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.fxbank.tpp.bocm.dto.esb.REP_30063001302;
import com.fxbank.tpp.bocm.dto.esb.REQ_30063001302;
import com.fxbank.tpp.bocm.model.BocmSndTraceQueryModel;
import com.fxbank.tpp.bocm.service.IBocmSndTraceService;


/** 
* @ClassName: QR_Trace 
* @Description: 交易流水查询
* @author YePuLiang
* @date 2019年7月10日 下午3:00:41 
*  
*/
@Service("REQ_30063001302")
public class QR_Trace extends TradeBase implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(QR_Trace.class);
	
	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IBocmSndTraceService sndTraceService;
	
	@Resource
	private MyJedis myJedis;
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {		
		MyLog myLog = logPool.get();
		
		REQ_30063001302 reqDto = (REQ_30063001302) dto;
		REQ_30063001302.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30063001302 rep = new REP_30063001302();
		
		String begDate = reqBody.getPltfrmBgnDtT1();
		String endDate = reqBody.getPltfrmEndDtT();
		String begTrace= reqBody.getPltfrmFrmSeqT();
		String endTrace= reqBody.getPltfrmEndSeqT1();
		String txAmt= reqBody.getTrnsAmtT3();
		String hostStatus= reqBody.getIntbnkCnstStsT6();
		String branchId = reqDto.getReqSysHead().getBranchId();
		
		logger.info("业务流水查询:行内处理状态："+hostStatus+" 起始日期："+begDate+" 结束日期："+endDate);
		logger.info("业务流水查询:交易金额："+txAmt+" 平台起始流水："+begTrace+" 平台结束流水："+endTrace);
		List<BocmSndTraceQueryModel> sndlist = sndTraceService.getSndTrace(myLog, begDate, endDate, begTrace, 
				endTrace, txAmt, hostStatus, branchId);
		logger.info("往账记录:  "+sndlist.size());
		
		REP_30063001302.REP_BODY body = rep.getRepBody();
		List<REP_30063001302.Trade> list = new ArrayList<REP_30063001302.Trade>();
		//总金额
		BigDecimal total = new BigDecimal("0.00");
		for(BocmSndTraceQueryModel model : sndlist){
			logger.info("渠道流水 号："+model.getPlatTrace());
			total = total.add(model.getTxAmt());
			REP_30063001302.Trade trace = transRepTrace(model);
			list.add(trace);
		}		
		body.setTolCnt(sndlist.size()+"");
		body.setTolAmt(total.toString());
		body.setTradeList(list);
		return rep;
	}
	

	
	private REP_30063001302.Trade transRepTrace(BocmSndTraceQueryModel model){
		REP_30063001302.Trade trace = new REP_30063001302.Trade();
		trace.setTxCode(model.getTxCode());
		trace.setSource(model.getSourceType());
		trace.setPlatDate(model.getPlatDate()+"");
		trace.setPlatTrace(model.getPlatTrace()+"");
		if(model.getHostDate()!=null){
			trace.setHostDate(model.getHostDate()+"");
		}
		if(model.getHostTraceno()!=null){
			trace.setHostTrace(model.getHostTraceno()+"");
		}
		trace.setTxDate(model.getTxDate()+"");
		trace.setSndBank(model.getSndBankno());
		trace.setTxBranch(model.getTxBranch());
		trace.setTxTel(model.getTxTel());
		if(model.getTxInd().equals("0")){
			trace.setTxMod("现金");
		}
		if(model.getTxInd().equals("1")){
			trace.setTxMod("转账");
		}
		trace.setTxAmt(model.getTxAmt()+"");
		if(model.getProxy_fee()!=null){
			trace.setProxyFee(model.getProxy_fee().toString());
		}
		trace.setProxyFlag(model.getProxy_flag());
		if(model.getFee()!=null){
			trace.setFee(model.getFee().toString());
		}
		trace.setPayerBank(model.getPayerBank());
		trace.setPayerAcno(model.getPayerAcno());
		trace.setPayerName(model.getPayerName());
		trace.setPayeeBank(model.getPayeeBank());
		trace.setPayeeAcno(model.getPayeeAcno());
		trace.setPayeeName(model.getPayeeName());
		String hostState = model.getHostState();
		if("0".equals(hostState)){
			trace.setHostState("登记");
		}
		if("1".equals(hostState)){
			trace.setHostState("成功");
		}
		if("2".equals(hostState)){
			trace.setHostState("失败");
		}
		if("3".equals(hostState)){
			trace.setHostState("超时");
		}
		if("4".equals(hostState)){
			trace.setHostState("冲正成功");
		}
		if("5".equals(hostState)){
			trace.setHostState("冲正失败");
		}
		if("6".equals(hostState)){
			trace.setHostState("冲正超时");
		}
		String checkFlag = model.getCheckFlag();
		if("1".equals(checkFlag)){
			trace.setCheckFlag("未对账");
		}
		if("2".equals(checkFlag)){
			trace.setCheckFlag("对账成功");
		}
		if("3".equals(checkFlag)){
			trace.setCheckFlag("核心多账");
		}
		if("4".equals(checkFlag)){
			trace.setCheckFlag("渠道多账");
		}
		if("5".equals(checkFlag)){
			trace.setCheckFlag("交行记账失败本行记账成功");
		}
		return trace;
	}
}
