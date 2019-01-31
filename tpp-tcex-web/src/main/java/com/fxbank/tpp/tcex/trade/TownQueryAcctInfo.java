package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30013000201;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30013000201;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30013000801;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.tcex.dto.esb.REP_TRK001;
import com.fxbank.tpp.tcex.dto.esb.REQ_TRK001;
import com.fxbank.tpp.tcex.exception.TcexTradeExecuteException;

import redis.clients.jedis.Jedis;

/**
 * 村镇通兑快捷查询账户信息
 * @author liye
 *
 */
@Service("REQ_TRK001")
public class TownQueryAcctInfo extends TradeBase implements TradeExecutionStrategy{
	private static Logger logger = LoggerFactory.getLogger(CityQueryAcctInfo.class);

	@Resource
	private LogPool logPool;
	
	@Resource
	private MyJedis myJedis;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	private final static String COMMON_PREFIX = "tcex_common.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		
		REQ_TRK001 reqDto = (REQ_TRK001)dto;
		String payerAcno = reqDto.getReqBody().getPayerAcno();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try(Jedis jedis = myJedis.connect()){
			txBrno = jedis.get(COMMON_PREFIX+"TXBRNO");
			txTel = jedis.get(COMMON_PREFIX+"TXTEL");
        }
		
		//请求核心获取账户信息
		ESB_REQ_30013000201 esbReq_30013000201 = new ESB_REQ_30013000201(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30013000201.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30013000201.setReqSysHead(reqSysHead);	
		ESB_REQ_30013000201.REQ_BODY reqBody_30013000201 = esbReq_30013000201.getReqBody();
		reqBody_30013000201.setBaseAcctNo(payerAcno);
		reqBody_30013000201.setCcy("CNY");
		
		ESB_REP_30013000201 esbRep_30013000201 = forwardToESBService.sendToESB(esbReq_30013000201, reqBody_30013000201, ESB_REP_30013000201.class);
		
		REP_TRK001 repDto = new REP_TRK001();
		repDto.getRepBody().setPayerAcno(esbRep_30013000201.getRepBody().getBaseAcctNo());
		repDto.getRepBody().setPayerName(esbRep_30013000201.getRepBody().getAcctName());
		repDto.getRepBody().setAcnoSeq(esbRep_30013000201.getRepBody().getAcctSeqNo());
		repDto.getRepBody().setBal(esbRep_30013000201.getRepBody().getBalance());
		
		return repDto;
	}
	
	
}
