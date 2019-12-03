package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.tpp.esb.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.tcex.ESB_REP_TSK001;
import com.fxbank.tpp.esb.model.tcex.ESB_REQ_TSK001;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.esb.service.IForwardToTownService;
import com.fxbank.tpp.tcex.dto.esb.REP_30042000307;
import com.fxbank.tpp.tcex.dto.esb.REQ_30042000307;

import redis.clients.jedis.Jedis;


/**
 * @ClassName: TSK01
 * @Description: 商行通兑快捷查询村镇账户信息
 * @author 
 * @date 2018年4月3日 下午3:46:30
 * 
 */
@Service("REQ_30042000307")
public class CityQueryAcctInfo extends TradeBase implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CityQueryAcctInfo.class);

	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IForwardToTownService forwardToTownService;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "tcex_common.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		
		REQ_30042000307 reqDto = (REQ_30042000307) dto;
		REQ_30042000307.REQ_BODY reqBody = reqDto.getReqBody();
		String payerAcno = reqBody.getBasrAcctNo();
		String brnoFlag = reqBody.getVillageBrnachFlag();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try(Jedis jedis = myJedis.connect()){
			txBrno = jedis.get(COMMON_PREFIX+"TXBRNO");
			txTel = jedis.get(COMMON_PREFIX+"TXTEL");
        }
				
		//请求村镇账户信息接口，反馈结果写入REP_TSK01
		
		ESB_REQ_TSK001 esbReq_tsk01 = new ESB_REQ_TSK001(myLog, dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno());
		/** Add by 叶浦亮 At 2019/12/3 15:48 For 不同渠道平台调用核心接口使用不一样的systemID */
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_tsk01.getReqSysHead(),reqDto.getSourceType(),reqDto.getSysDate(),reqDto.getSysTime(),reqDto.getSysTraceno()).setBranchId(txBrno).setUserId(txTel).build();
		esbReq_tsk01.setReqSysHead(reqSysHead);
		ESB_REQ_TSK001.REQ_BODY esbReqBody_tsk01 = esbReq_tsk01.getReqBody();
		esbReqBody_tsk01.setPayerAcno(payerAcno);
		esbReqBody_tsk01.setBrnoFlag(brnoFlag);
		ESB_REP_TSK001 esbRep_tsk01 = forwardToTownService.sendToTown(esbReq_tsk01, esbReqBody_tsk01, ESB_REP_TSK001.class);


		REP_30042000307 repDto = new REP_30042000307();
		REP_30042000307.REP_BODY repBody = repDto.getRepBody();
		repBody.setBasrAcctNo(esbRep_tsk01.getRepBody().getPayerAcno());
		repBody.setAcctName(esbRep_tsk01.getRepBody().getPayerName());
		repBody.setAcctSqNoT(esbRep_tsk01.getRepBody().getAcnoSeq());
		repBody.setBalance(esbRep_tsk01.getRepBody().getBal());
		
		
		return repDto;
	}


	

}
