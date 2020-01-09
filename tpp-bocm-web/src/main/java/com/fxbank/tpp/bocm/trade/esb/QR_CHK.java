package com.fxbank.tpp.bocm.trade.esb;

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
import com.fxbank.tpp.bocm.dto.esb.REP_30063001304;
import com.fxbank.tpp.bocm.dto.esb.REQ_30063001304;
import com.fxbank.tpp.bocm.model.BocmChkStatusModel;
import com.fxbank.tpp.bocm.service.IBocmChkStatusService;

/** 
* @ClassName: QR_CHK 
* @Description: 对账查询
* @author YePuLiang
* @date 2019年7月10日 下午5:03:47 
*  
*/
@Service("REQ_30063001304")
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
		
		
		REQ_30063001304 reqDto = (REQ_30063001304) dto;
		REQ_30063001304.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30063001304 rep = new REP_30063001304();
		
		MyLog myLog = logPool.get();
		String begDate = reqBody.getPltfrmBgnDtT1();
		String endDate = reqBody.getPltfrmEndDtT();
		String state = reqBody.getStmtStsT();
		List<BocmChkStatusModel> errlist = chkStatusService.selectByDate(myLog,begDate, endDate,state);
		List<REP_30063001304.Chk> list = new ArrayList<REP_30063001304.Chk>();
		logger.info("对账记录:  "+errlist.size());
		for(BocmChkStatusModel model : errlist){
			REP_30063001304.Chk chk = transRepTrace(model);
			list.add(chk);
		}
		rep.getRepBody().setChkList(list);
		return rep;
	}
	
	private REP_30063001304.Chk transRepTrace(BocmChkStatusModel model){
		REP_30063001304.Chk chk = new REP_30063001304.Chk();
		chk.setTxDate(model.getTxDate()+"");
		chk.setChkBranch(model.getTxBranch());
		chk.setChkTel(model.getTxTel());
		int hostStatus = model.getHostStatus();
		int bocmStatus = model.getBocmStatus();
		int platStatus = model.getPlatStatus();
		StringBuffer chkMsg = new StringBuffer();
		//判断与核心对账状态
		if(0==hostStatus){
			chkMsg.append("未与核心对账");
		}else if(1==hostStatus){
			chkMsg.append("与核心对账成功");
		}else if(2==hostStatus){
			chkMsg.append("与核心对账失败");
		}
		//判断与交行对账状态
		if(0==bocmStatus){
			chkMsg.append(",未与交行对账");
		}else if(1==bocmStatus){
			chkMsg.append(",与交行对账成功");
		}else if(2==bocmStatus){
			chkMsg.append(",与交行对账失败");
		}
		//交行未与平台对账
		if(0==platStatus){
			chkMsg.append(",交行未与平台对账");
		}else if(1==platStatus){
			chkMsg.append(",交行与平台对账成功");
		}
		chk.setChkState(chkMsg.toString());
		chk.setChkDate(model.getChkDate()+"");
		chk.setChkTime(getTimeFormat(model.getChkTime()+""));
		return chk;
	}
	
	
	private String getTimeFormat(String s){
		if(s.equals("")){
			return s;
		}
		if(s.length()==5){
			s = "0"+s.substring(0, 1)+":"+s.substring(1, 3)+":"+s.substring(3, 5);
		}else{
			s = s.substring(0, 2)+":"+s.substring(2, 4)+":"+s.substring(4, 6);
		}
		return s;
	}
}