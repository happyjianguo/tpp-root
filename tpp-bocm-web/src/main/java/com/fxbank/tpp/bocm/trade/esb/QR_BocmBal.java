package com.fxbank.tpp.bocm.trade.esb;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.bocm.dto.esb.REP_30063800301;
import com.fxbank.tpp.bocm.dto.esb.REQ_30063800301;
import com.fxbank.tpp.bocm.model.REP_10101;
import com.fxbank.tpp.bocm.model.REQ_10101;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("REQ_30063800301")
public class QR_BocmBal extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(QR_BocmBal.class);

	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IForwardToBocmService forwardToBocmService;

	@Resource
	private MyJedis myJedis;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30063800301 reqDto = (REQ_30063800301) dto;
		REQ_30063800301.REQ_BODY reqBody = reqDto.getReqBody();

		REQ_10101 req10101 = new REQ_10101(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());
		super.setBankno(myLog, dto, reqDto.getReqSysHead().getBranchId(), req10101.getHeader());	//设置报文头中的行号信息
		req10101.setActTyp(reqBody.getAcctType());
		req10101.setActNo(reqBody.getCardNo());
		req10101.setPin(super.convPin(reqBody.getPwd()));

		myLog.info(logger, "发送余额查询请求至交行");
		REP_10101 rep10101 = forwardToBocmService.sendToBocm(req10101, REP_10101.class);

		REP_30063800301 rep = new REP_30063800301();
		rep.getReqBody().setAcctName(rep10101.getActNam());
		rep.getReqBody().setBal(rep10101.getActBal().doubleValue());
		return rep;
	}

}
