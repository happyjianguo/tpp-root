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
import com.fxbank.tpp.bocm.dto.esb.REP_30063001303;
import com.fxbank.tpp.bocm.dto.esb.REQ_30063001303;
import com.fxbank.tpp.bocm.model.BocmAcctCheckErrModel;
import com.fxbank.tpp.bocm.service.IBocmAcctCheckErrService;

/** 
* @ClassName: QR_CB 
* @Description: 调账明细查询
* @author YePuLiang
* @date 2019年7月10日 下午4:41:36 
*  
*/
@Service("REQ_30063001303")
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
		
		REQ_30063001303 reqDto = (REQ_30063001303) dto;
		REQ_30063001303.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30063001303 rep = new REP_30063001303();
		
		String begDate = reqBody.getQueStrDtT();
		String endDate = reqBody.getQueEndDtT();

		
		List<BocmAcctCheckErrModel> errlist = acctCheckErrService.getListByDate(myLog, dto.getSysTime(), dto.getSysDate(), dto.getSysTraceno(), begDate, endDate);
		logger.info("调账记录:  "+errlist.size());
		for(BocmAcctCheckErrModel model : errlist){
			logger.info("渠道流水 号："+model.getPlatTrace());
		}
		
		REP_30063001303.REP_BODY body = rep.getRepBody();
		List<REP_30063001303.Trade> list = new ArrayList<REP_30063001303.Trade>();
		//总金额
		BigDecimal total = new BigDecimal("0.00");
		for(BocmAcctCheckErrModel model : errlist){
			logger.info("渠道流水 号："+model.getPlatTrace());
			if(model.getTxAmt()!=null){
				total = total.add(model.getTxAmt());
			}
			REP_30063001303.Trade trace = transRepTrace(model);
			list.add(trace);
		}		
		body.setTolAmt(total.toString());
		body.setTradeList(list);
		return rep;
	}
	
	private REP_30063001303.Trade transRepTrace(BocmAcctCheckErrModel model){
		REP_30063001303.Trade trace = new REP_30063001303.Trade();
		trace.setTxCode(model.getTxCode());
		String source = model.getTxSource();
		if("MT".equals(source)){
			trace.setSource("我行发起");
		}
		if("BU".equals(source)){
			trace.setSource("交行发起");
		}
		trace.setPlatDate(model.getPlatDate()+"");
		trace.setPlatTrace(model.getPlatTrace()+"");
		if(model.getHostDate()!=null){
			trace.setHostDate(model.getHostDate()+"");
		}
		trace.setHostTrace(model.getHostTraceno());
		if(model.getTxDate()!=null){
			trace.setTxDate(model.getTxDate()+"");
		}
		trace.setSndBank(model.getSndBankno());
		trace.setTxBranch(model.getTxBranch());
		trace.setTxTel(model.getTxTel());
		if(model.getTxInd()!=null){
			String txInd = model.getTxInd();
			if(txInd.equals("0")){
				trace.setTxMod("现金");
			}
			if(txInd.equals("1")){
				trace.setTxMod("转账");
			}
		}
		if(model.getTxAmt()!=null){
			trace.setTxAmt(model.getTxAmt().toString());
		}		
		if(model.getProxyFee()!=null){
			trace.setProxyFee(model.getProxyFee().toString());
		}
		trace.setProxyFlag(model.getProxyFlag());

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
			trace.setHostFlag("记账失败");
		}
		if("4".equals(hostState)){
			trace.setHostState("冲正成功");
		}
		if("5".equals(hostState)){
			trace.setHostState("冲正失败");
		}
		if("6".equals(hostState)){
			trace.setHostState("冲正超时");
			trace.setHostFlag("记账失败");
		}
		String bocmState = model.getBocmState();
		if("0".equals(bocmState)){
			trace.setBocmState("登记");
		}
		if("1".equals(bocmState)){
			trace.setBocmState("成功");
		}
		if("2".equals(bocmState)){
			trace.setBocmState("失败");
		}
		if("3".equals(bocmState)){
			trace.setBocmState("超时");
		}
		if("4".equals(bocmState)){
			trace.setBocmState("冲正成功");
		}
		if("5".equals(bocmState)){
			trace.setBocmState("冲正失败");
		}
		if("6".equals(bocmState)){
			trace.setBocmState("冲正超时");
		}
		String bocmFlag = model.getBocmFlag();
		String hostFlag = model.getHostFlag();
		if("0".equals(bocmFlag)){
			trace.setBocmFlag("未对账");
		}else if("1".equals(bocmFlag)){
			trace.setBocmFlag("已对账");
		}else if("2".equals(bocmFlag)){
			trace.setBocmFlag("交行多");
		}
		if("0".equals(hostFlag)){
			trace.setHostFlag("未对账");
		}else if("1".equals(hostFlag)){
			trace.setHostFlag("已对账");
		}else if("2".equals(hostFlag)){
			trace.setHostFlag("核心多");
		}else if("3".equals(hostFlag)){
			trace.setHostFlag("核心少");
		}
//		trace.setBocmFlag("");
//		trace.setHostFlag("");
		trace.setCheckFlag(model.getCheckFlag());
		trace.setMsg(model.getMsg());
		return trace;
	}
}
