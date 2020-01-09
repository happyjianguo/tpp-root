package com.fxbank.tpp.bocm.trade.esb;

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
import com.fxbank.tpp.bocm.dto.esb.REP_30063001201;
import com.fxbank.tpp.bocm.dto.esb.REQ_30063001201;
import com.fxbank.tpp.bocm.model.REP_10101;
import com.fxbank.tpp.bocm.model.REQ_10101;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;
import com.fxbank.tpp.bocm.util.NumberUtil;
import com.fxbank.tpp.esb.service.IForwardToESBService;

/**
 * @Description: 本行查询交行卡余额
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:15:42
 */
@Service("REQ_30063001201")
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
		myLog.info(logger, "余额查询请求至交行");
		REQ_30063001201 reqDto = (REQ_30063001201) dto;
		REQ_30063001201.REQ_BODY reqBody = reqDto.getReqBody();

		REQ_10101 req10101 = new REQ_10101(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());
		super.setBankno(myLog, dto, reqDto.getReqSysHead().getBranchId(), req10101);	//设置报文头中的行号信息
		req10101.setActTyp(reqBody.getAcctType());
		req10101.setActNo(reqBody.getCardNo());
		req10101.setPin(super.convPin(dto,reqBody.getCardNo(),reqBody.getPwd()));

		myLog.info(logger, "发送余额查询请求至交行");
		
		REP_10101 rep10101 = forwardToBocmService.sendToBocm(req10101, REP_10101.class);

		REP_30063001201 rep = new REP_30063001201();
		rep.getReqBody().setAcctName(rep10101.getActNam());
		//从交行接口获取账户余额，金额处理，去除 交行接口补零
		double amt = NumberUtil.removePoint(rep10101.getActBal());
		rep.getReqBody().setBal(amt);
		return rep;
	}

}