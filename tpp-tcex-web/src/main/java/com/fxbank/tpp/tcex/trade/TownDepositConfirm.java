package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.tcex.dto.esb.REP_TR003;
import com.fxbank.tpp.tcex.dto.esb.REQ_TR003;
import com.fxbank.tpp.tcex.model.RcvTraceQueryModel;
import com.fxbank.tpp.tcex.model.RcvTraceUpdModel;
import com.fxbank.tpp.tcex.service.IRcvTraceService;
import com.fxbank.tpp.tcex.dto.esb.REQ_30043002701;
/**
 * 村镇存款确认业务
 * @author liye
 *
 */
public class TownDepositConfirm implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(CityQueryAcctInfo.class);

	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IRcvTraceService rcvTraceService;
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		
		REQ_TR003 reqDto = (REQ_TR003) dto;
		String brno = reqDto.getReqBody().getBrno();//村镇记账机构
		String townDate = reqDto.getReqBody().getTownDate();//村镇日期
		String townTraceno = reqDto.getReqBody().getTownTraceno();//村镇流水
		String dcflag = reqDto.getReqBody().getDcFlag();//通存通兑标志
		
		REP_TR003 repDto = new REP_TR003();
		
		//自查流水状态
		RcvTraceQueryModel model = rcvTraceService.getConfirmTrace(myLog, brno, townDate, townTraceno, dcflag);
		String state = model.getHostState();
		Integer platDate = model.getPlatDate();
		Integer platTrance = model.getPlatTrace();
		
		repDto.getRepBody().setSts("1");
		repDto.getRepBody().setPlatDate(platDate.toString());
		repDto.getRepBody().setPlatTraceno(platTrance.toString());
		
		//登记超时
		//调用核心接口确认该笔流水是否入账成功，若登记成功直接反馈，若三次查询都失败则调用核心接口重新登记，登记结果渠道不关心，存款确认反馈状态只有成功
		
		//登记失败
		//调用核心记账接口
		String flag="0";
		if(state.equals("3")) {
			int i=0;
			for(;i<3;i++) {
				//调用核心接口确认该笔流水是否入账成功,
				if(true) {
					break;
				}
			}
			if(i==3)flag="1";
		}else if(state.equals("2")){
			flag="1";
			
		}
		
		if(flag.equals("1")) {
			//调用核心记账接口
		}
		 
		RcvTraceUpdModel record = new RcvTraceUpdModel(myLog, platDate, dto.getSysTime(), platTrance);
		record.setHostState("4");
		rcvTraceService.rcvTraceUpd(record);
		
		return repDto;
	}

}
