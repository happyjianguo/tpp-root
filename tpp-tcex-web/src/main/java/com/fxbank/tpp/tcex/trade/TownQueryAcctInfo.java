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
import com.fxbank.tpp.tcex.dto.esb.REP_TRK01;
import com.fxbank.tpp.tcex.dto.esb.REQ_TRK01;
import com.fxbank.tpp.tcex.exception.TcexTradeExecuteException;

import redis.clients.jedis.Jedis;

/**
 * 村镇通兑快捷查询账户信息
 * @author liye
 *
 */
@Service("REQ_TRK01")
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
		
		REQ_TRK01 reqDto = (REQ_TRK01)dto;
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
		
		ESB_REP_30013000201 esbRep_30013000201 = forwardToESBService.sendToESB(esbReq_30013000201, reqBody_30013000201, ESB_REP_30013000201.class);
//		String code = esbRep_30013000201.getRepSysHead().getRet().get(0).getRetCode();
//		String msg = esbRep_30013000201.getRepSysHead().getRet().get(0).getRetMsg();
		
		REP_TRK01 repDto = new REP_TRK01();
		repDto.getRepBody().setPayerAcno(esbRep_30013000201.getRepBody().getBaseAcctNo());
		repDto.getRepBody().setPayerName(esbRep_30013000201.getRepBody().getAcctName());
		repDto.getRepBody().setAcnoSeq(esbRep_30013000201.getRepBody().getAcctSeqNo());
		repDto.getRepBody().setBal(esbRep_30013000201.getRepBody().getBalance());
//		if(code.equals("000000")) {
//			logger.info("商行账户信息查询成功：账号【"+payerAcno+"】");
//		}else {
//			System.out.println("商行账户信息查询失败: 错误码【"+code+"】,错误信息【"+msg+"】");
//			myLog.error(logger,"商行账户信息查询失败: 错误码【"+code+"】,错误信息【"+msg+"】");
//			TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10006);
//			throw e;
//		}
		
		return repDto;
	}
	
	
}
