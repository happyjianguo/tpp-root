package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_TSK01;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_TS002;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_TSK01;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.tcex.dto.esb.REP_30042000307;
import com.fxbank.tpp.tcex.dto.esb.REQ_30042000307;


/**
 * @ClassName: TSK01
 * @Description: 商行通兑快捷查询村镇账户信息
 * @author 
 * @date 2018年4月3日 下午3:46:30
 * 
 */
@Service("REQ_TSK01")
public class CityQueryAcctInfo extends TradeBase implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CityQueryAcctInfo.class);


	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		
		REQ_30042000307 reqDto = (REQ_30042000307) dto;
		REQ_30042000307.REQ_BODY reqBody = reqDto.getReqBody();
		String payerAcno = reqBody.getPayerAcno();
		REP_30042000307 repDto = new REP_30042000307();
		//请求村镇账户信息接口，反馈结果写入REP_TSK01
		
		ESB_REQ_TSK01 esbReq_tsk01 = new ESB_REQ_TSK01(myLog, dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno());
		ESB_REQ_TSK01.REQ_BODY esbReqBody_tsk01 = esbReq_tsk01.getReqBody();
		esbReqBody_tsk01.setPayerAcno(payerAcno);
		ESB_REP_TSK01 esbRep_tsk01 = forwardToESBService.sendToTown(esbReq_tsk01, esbReqBody_tsk01, ESB_REP_TSK01.class);
		if("000000".equals(esbRep_tsk01.getRepSysHead().getRet().get(0).getRetCode())) {
			REP_30042000307.REP_BODY repBody = repDto.getRepBody();
			repBody.setPayerAcno(esbRep_tsk01.getRepBody().getPayerAcno());
			repBody.setPayerName(esbRep_tsk01.getRepBody().getPayerName());
			repBody.setAcnoSeq(esbRep_tsk01.getRepBody().getAcnoSeq());
			repBody.setBal(esbRep_tsk01.getRepBody().getBal());
		}else {
			System.out.println("获取账户【"+payerAcno+"】信息失败: "+esbRep_tsk01.getRepSysHead().getRet().get(0).getRetMsg());
		}
		
		//测试用
//		REP_TSK01 repDto = tstInterface(dto);
		return repDto;
	}

	private REP_30042000307 tstInterface(DataTransObject dto) {
		REQ_30042000307 reqDto = (REQ_30042000307) dto;
		REQ_30042000307.REQ_BODY reqBody = reqDto.getReqBody();
		String payerAcno = reqBody.getPayerAcno();
		
		REP_30042000307 repDto = new REP_30042000307();
		REP_30042000307.REP_BODY repBody = repDto.getRepBody();
		repBody.setPayerAcno(payerAcno);
		repBody.setPayerName("测试");
		repBody.setAcnoSeq("1");
		repBody.setBal("1000.00");
		return repDto;
	}

	

}
