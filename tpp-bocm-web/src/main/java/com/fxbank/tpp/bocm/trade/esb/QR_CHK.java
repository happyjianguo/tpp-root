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
import com.fxbank.tpp.bocm.dto.esb.REP_30063001303;
import com.fxbank.tpp.bocm.dto.esb.REP_30063001304;
import com.fxbank.tpp.bocm.dto.esb.REQ_30063001304;
import com.fxbank.tpp.bocm.model.BocmAcctCheckErrModel;
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
		String platState = reqBody.getStmtStsT();

		
		
		
		List<BocmChkStatusModel> errlist = chkStatusService.selectByDate(myLog,begDate, endDate,platState);
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
		chk.setTxDate(model.getChkDate()+"");
		chk.setChkBranch("00001");
		chk.setChkTel("002264");
		int hostStatus = model.getHostStatus();
		int bocmStatus = model.getBocmStatus();
		int platStatus = model.getPlatStatus();
		StringBuffer chkMsg = new StringBuffer();
		if(1==hostStatus){
			chkMsg.append("与核心对账成功");
		}
		if(1==bocmStatus){
			chkMsg.append(",与交行对账成功");
		}
		if(1==platStatus){
			chkMsg.append(",交行与平台对账成功");
		}
		if(chkMsg.toString().length()==0){
			chkMsg.append("未对账");
		}
		chk.setChkState(chkMsg.toString());
		return chk;
	}
}
