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
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30013000801;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30013000801;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.tcex.dto.esb.REP_TR005;
import com.fxbank.tpp.tcex.dto.esb.REQ_TR005;
import com.fxbank.tpp.tcex.exception.TcexTradeExecuteException;
import com.fxbank.tpp.tcex.model.TownInfo;
import com.fxbank.tpp.tcex.model.TownList;

import redis.clients.jedis.Jedis;

/**
 * 村镇头寸查询
 * @author liye
 *
 */
@Service("REQ_TR005")
public class TownCashQuery implements TradeExecutionStrategy{
	private static Logger logger = LoggerFactory.getLogger(CityDcHistory.class);


	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Resource
	private MyJedis myJedis;

	private final static String COMMON_PREFIX = "tcex_common.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_TR005 reqDto = (REQ_TR005) dto;
		String brno = reqDto.getReqSysHead().getBranchId();
		// 交易机构
		String txBrno = reqDto.getReqSysHead().getBranchId();
		// 柜员号
		String txTel = reqDto.getReqSysHead().getUserId();
		
		// 村镇机构号
		String jsonStrTownBranch = null;
		try(Jedis jedis = myJedis.connect()){
			jsonStrTownBranch = jedis.get(COMMON_PREFIX+"TOWN_LIST");
        }
		if(jsonStrTownBranch==null||jsonStrTownBranch.length()==0){
			logger.error("渠道未配置["+COMMON_PREFIX + "TOWN_LIST"+"]");
			throw new RuntimeException("渠道未配置["+COMMON_PREFIX + "TOWN_LIST"+"]");
		}
		TownList townList = JsonUtil.toBean(jsonStrTownBranch, TownList.class);
        String townBranch = null;
		for(TownInfo townInfo:townList.getData()){
			if(townInfo.getTownFlag().equals(brno)) {
				townBranch = townInfo.getTownBranch();
			}
		}
				
		//调用核心接口查询头寸余额
		ESB_REQ_30013000801 esbReq_30013000801 = new ESB_REQ_30013000801(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30013000801.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30013000801.setReqSysHead(reqSysHead);	
		ESB_REQ_30013000801.REQ_BODY reqBody_30013000801 = esbReq_30013000801.getReqBody();
		reqBody_30013000801.setVillageBrnachId(townBranch);
		
		ESB_REP_30013000801 esbRep_30013000801 = forwardToESBService.sendToESB(esbReq_30013000801, reqBody_30013000801, ESB_REP_30013000801.class);

		REP_TR005 repDto = new REP_TR005();
		repDto.getRepBody().setBal(esbRep_30013000801.getRepBody().getBalance());
		
//		String code = esbRep_30013000801.getRepSysHead().getRet().get(0).getRetCode();
//		String msg = esbRep_30013000801.getRepSysHead().getRet().get(0).getRetMsg();
//		if(code.equals("000000")) {
//			logger.info("头寸查询成功：机构码【"+townBranch+"】，余额【"+esbRep_30013000801.getRepBody().getBalance()+"】");
//		}else {
//			System.out.println("头寸查询失败: 错误码【"+code+"】,错误信息【"+msg+"】");
//			myLog.error(logger,"头寸查询失败: 错误码【"+code+"】,错误信息【"+msg+"】");
//			TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10005);
//			throw e;
//		}
		
		return repDto;
	}

}
