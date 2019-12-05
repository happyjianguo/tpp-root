package com.fxbank.tpp.tcex.trade;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.tpp.esb.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.tcex.ESB_REP_TS0012;
import com.fxbank.tpp.esb.model.tcex.ESB_REP_TS0013;
import com.fxbank.tpp.esb.model.tcex.ESB_REQ_TS0012;
import com.fxbank.tpp.esb.model.tcex.ESB_REQ_TS0013;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.esb.service.IForwardToTownService;
import com.fxbank.tpp.tcex.dto.esb.REP_TS0013;
import com.fxbank.tpp.tcex.dto.esb.REQ_30041000901;
import com.fxbank.tpp.tcex.dto.esb.REQ_TS0013;
import com.fxbank.tpp.tcex.model.RcvTraceInitModel;
import com.fxbank.tpp.tcex.service.IRcvTraceService;

/** 
* @ClassName: TS0013 
* @Description: 模拟村镇存款确认
* @author Duzhenduo
* @date 2019年1月30日 下午2:14:57 
*  
*/
@Service("REQ_TS0013")
public class TS0013 implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CityDeposit.class);

	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IForwardToTownService forwardToTownService;
	
	static SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		/**
		REP_TS003 repDto = new REP_TS003();
		repDto.getRepBody().setTownDate(sdf1.format(new Date()));
		repDto.getRepBody().setTownTraceNo(UUID.randomUUID().toString().replace("-", "").substring(0, 15));
		repDto.getRepBody().setSts("1");
		**/
		REQ_TS0013 reqDto = (REQ_TS0013) dto;
		REP_TS0013 repDto = new REP_TS0013();
		ESB_REQ_TS0013 esbReq_TS003 = new ESB_REQ_TS0013(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		/** Add by 叶浦亮 At 2019/12/3 15:48 For 不同渠道平台调用核心接口使用不一样的systemID */
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_TS003.getReqSysHead(), reqDto.getSourceType(),reqDto.getSysDate(),reqDto.getSysTime(),reqDto.getSysTraceno())
				.setBranchId(reqDto.getReqSysHead().getBranchId()).setUserId(reqDto.getReqSysHead().getUserId())
				.build();
		esbReq_TS003.setReqSysHead(reqSysHead);
		ESB_REQ_TS0013.REQ_BODY esbReqBody_TS003 = esbReq_TS003.getReqBody();
		esbReqBody_TS003.setPlatDate(reqDto.getReqBody().getPlatDate().toString());
		esbReqBody_TS003.setPlatTraceno(reqDto.getReqBody().getPlatTraceno().toString());

        
		ESB_REP_TS0013 esbRep_TS003 = forwardToTownService.sendToTown(esbReq_TS003, esbReqBody_TS003,
				ESB_REP_TS0013.class);
		repDto.getRepBody().setTownDate(esbRep_TS003.getRepBody().getTownDate());
		repDto.getRepBody().setTownTraceNo(esbRep_TS003.getRepBody().getTownTraceNo());
		repDto.getRepBody().setSts(esbRep_TS003.getRepBody().getSts());
		return repDto;
	}
}
