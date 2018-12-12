package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_TS002;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_TS002;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.tcex.dto.esb.REP_TS002;
import com.fxbank.tpp.tcex.dto.esb.REQ_TS002;
import com.fxbank.tpp.tcex.model.RcvTraceInitModel;
import com.fxbank.tpp.tcex.service.IRcvTraceService;
/**
 * 商行通兑业务
 * @author liye
 *
 */
@Service("REQ_TS002")
public class CityExchange implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CityDeposit.class);

	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IRcvTraceService rcvTraceService;
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_TS002 reqDto = (REQ_TS002) dto;
		REQ_TS002.REQ_BODY reqBody = reqDto.getReqBody();
		REP_TS002 repDto = new REP_TS002();
		REP_TS002.REP_BODY repBody = repDto.getRepBody();
		
		//插入流水表
		boolean b = true;
		initRecord(reqDto);
		if(b) {
			//通知村镇记账
			String rst = "";
			ESB_REQ_TS002 esbReq_TS002 = new ESB_REQ_TS002(myLog,dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno());
			ESB_REQ_TS002.REQ_BODY esbReqBody_TS002 = esbReq_TS002.getReqBody();
			ESB_REP_TS002 esbRep_TS002 =forwardToESBService.sendToTown(esbReq_TS002, esbReqBody_TS002, ESB_REP_TS002.class);
			ESB_REP_TS002.REP_BODY esbRepBody_TS002 = esbRep_TS002.getRepBody();
            
			rst = esbRep_TS002.getRepSysHead().getRet().get(0).getRetCode();
			//更新流水表村镇记账状态
			
			if(rst.equals("000000")) {
				//核心记账：将金额从头寸中划至指定账户
				rst = "";
				//更新流水表核心记账状态
				if(rst.equals("success")) {
					//结束
				}else  {
					if(rst.equals("timeout")) {
						//多次查询核心，确认是否是延迟原因
						b = true;
					}else {
						//核心记账失败
						b = false;
					}
					
					if(!b) {
						//村镇冲正

						//更新流水表村镇记账状态
					}
						
					
				}
			}else {
				throw new SysTradeExecuteException("1111");
			}
			
			
		}else {
			throw new SysTradeExecuteException("1111");
		}
		
		
		return repDto;
	}
	private void initRecord(REQ_TS002 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		
		REQ_TS002.REQ_BODY reqBody = reqDto.getReqBody();
		REQ_SYS_HEAD reqSysHead = reqDto.getReqSysHead();
		
		RcvTraceInitModel record = new RcvTraceInitModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
		record.setSourceType(reqBody.getChnl());
		record.setTxBranch(reqSysHead.getBranchId());
		//现转标志 0现金1转账
		record.setTxInd(reqBody.getTxInd());
		//通存通兑
		record.setDcFlag("1");
		record.setTxAmt(reqBody.getTxAmt());
		if("1".equals(reqBody.getTxInd())) {
		record.setPayeeAcno(reqBody.getPayerAcc());
		record.setPayeeName(reqBody.getPayerName());
		}
		record.setPayerAcno(reqBody.getPayeeAcc());
		record.setPayerName(reqBody.getPayeeName());
		record.setHostState("0");
		record.setTxTel(reqSysHead.getUserId());
		//record.setChkTel();
		//record.setAuthTel();
		record.setInfo(reqBody.getInfo());
		rcvTraceService.rcvTraceInit(record);
	}
}
