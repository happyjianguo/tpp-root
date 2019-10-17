package com.fxbank.tpp.bocm.trade.esb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.bocm.dto.esb.REP_30063001201;
import com.fxbank.tpp.bocm.dto.esb.REQ_30063001201;
import com.fxbank.tpp.bocm.model.REP_10101;
import com.fxbank.tpp.bocm.model.REQ_10101;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;

/**
 * @Description: 通讯返回码判断例子程序，实际运行不用
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:14:17
 */
public class TemplateTrade implements TradeExecutionStrategy {
    private static Logger logger = LoggerFactory.getLogger(TemplateTrade.class);

	private LogPool logPool;

	private IForwardToBocmService forwardToBocmService;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30063001201 req = (REQ_30063001201) dto;
		REQ_30063001201.REQ_BODY reqBody = req.getReqBody();

		REQ_10101 req10101 = new REQ_10101(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());
		req10101.setActTyp(reqBody.getAcctType());
		req10101.setActNo(reqBody.getCardNo());
		req10101.setPin(reqBody.getPwd());

		REP_10101 rep10101 = null;
		try {
			rep10101 = forwardToBocmService.sendToBocm(req10101, REP_10101.class);
		}catch(SysTradeExecuteException e){		//记账交易参考一下方式处理，查询交易不用
			//如果不是账务类请求，可以不用分类处理应答码，统一当成失败处理即可
			//如果交易不关心返回的异常类型，直接可以不捕获，直接省略catch，抛出异常即可
			if(e.getRspCode().equals(SysTradeExecuteException.CIP_E_000006)	//生成请求失败
					||e.getRspCode().equals(SysTradeExecuteException.CIP_E_000007)
					||e.getRspCode().equals(SysTradeExecuteException.CIP_E_000008)){	
				//确认是否有冲正操作
			}else if(e.getRspCode().equals(SysTradeExecuteException.CIP_E_000009)){ //接收交行返回结果超时
				//确认是否有冲正操作
			}else if(e.getRspCode().equals("JH6203")){ //交行返回结果成功，但结果是超时
				//确认是否有冲正操作
			}else{ //目标系统应答失败
				//确认是否有冲正操作
			}
			throw e;
		}catch(Exception e){	//其它未知错误，可以当成超时处理
			myLog.error(logger,"其它未知错误，可以当成超时处理");
			//确认是否有冲正操作
			throw e;
		}

		REP_30063001201 rep = new REP_30063001201();
		rep.getReqBody().setAcctName(rep10101.getActNam());
		rep.getReqBody().setBal(rep10101.getActBal().doubleValue());
		return rep;
	}

}
