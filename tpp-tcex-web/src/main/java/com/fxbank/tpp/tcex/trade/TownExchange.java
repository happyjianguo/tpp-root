package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30011000103;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30011000103;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.esb.service.IForwardToTownService;
import com.fxbank.tpp.tcex.dto.esb.REP_TR002;
import com.fxbank.tpp.tcex.dto.esb.REQ_TR002;
import com.fxbank.tpp.tcex.model.RcvTraceInitModel;
import com.fxbank.tpp.tcex.model.RcvTraceUpdModel;
import com.fxbank.tpp.tcex.service.IRcvTraceService;

/**
 * 村镇通兑业务
 * 
 * @author liye
 *
 */
@Service("REQ_TR002")
public class TownExchange implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CityDeposit.class);

	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IForwardToTownService forwardToTownService;

	@Reference(version = "1.0.0")
	private IRcvTraceService rcvTraceService;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_TR002 reqDto = (REQ_TR002) dto;
		REP_TR002 repDto = new REP_TR002();
		// 插入流水表
		initRecord(reqDto);
		// 记账状态码
		String hostCode = null;
		String hostMsg = null;
		// 记账流水号
		String hostSeqno = null;
		// 核心日期
		String hostDate = null;
		ESB_REP_30011000103 esbRep_30011000103 = innerCapCharge(reqDto);
		hostCode = esbRep_30011000103.getRepSysHead().getRet().get(0).getRetCode();
		hostMsg = esbRep_30011000103.getRepSysHead().getRet().get(0).getRetMsg();
		hostSeqno = esbRep_30011000103.getRepBody().getReference();
		hostDate = esbRep_30011000103.getRepSysHead().getRunDate();
		// 开户机构
		String acctBranch = esbRep_30011000103.getRepBody().getAcctBranch();
		// 记账机构
		String accounting_branch = esbRep_30011000103.getRepBody().getAccountingBranch();
		// 记账结果，00-已记账 01-已挂账
		String acctResult = esbRep_30011000103.getRepBody().getAcctResult();
		// 更新流水表核心记账状态
	    updateHostRecord(reqDto, Integer.parseInt(hostDate), hostSeqno, "1",hostCode,hostMsg);
		return repDto;
	}

	private ESB_REP_30011000103 innerCapCharge(REQ_TR002 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		REQ_TR002.REQ_BODY reqBody = reqDto.getReqBody();
		// 村镇编号
		String townId = reqBody.getBrnoFlag();
		// 交易机构
		String txBrno = reqDto.getReqSysHead().getBranchId();
		// 柜员号
		String txTel = reqDto.getReqSysHead().getUserId();

		ESB_REQ_30011000103 esbReq_30011000103 = new ESB_REQ_30011000103(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000103.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30011000103.setReqSysHead(reqSysHead);

		ESB_REQ_30011000103.REQ_BODY reqBody_30011000103 = esbReq_30011000103.getReqBody();
		// 账号/卡号
		reqBody_30011000103.setBaseAcctNo(reqBody.getPayerAcc());
		// 村镇机构号
		reqBody_30011000103.setVillageBrnachId(reqBody.getBrno());
		// 村镇标志
		reqBody_30011000103.setVillageFlag("");
		// 交易类型
		reqBody_30011000103.setTranType("LV02");
		// 交易币种
		reqBody_30011000103.setTranCcy("CNY");
		// 交易金额
		reqBody_30011000103.setTranAmt(reqBody.getTxAmt());
		// 密码
		reqBody_30011000103.setPassword(reqBody.getPayerPwd());
		ESB_REP_30011000103 esbRep_30011000103 = forwardToESBService.sendToESB(esbReq_30011000103, reqBody_30011000103,
				ESB_REP_30011000103.class);
		return esbRep_30011000103;
	}

	private void initRecord(REQ_TR002 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		REQ_TR002.REQ_BODY reqBody = reqDto.getReqBody();
		REQ_SYS_HEAD reqSysHead = reqDto.getReqSysHead();

		RcvTraceInitModel record = new RcvTraceInitModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setSourceType(reqSysHead.getSourceType());
		record.setTxBranch(reqSysHead.getBranchId());
		// 现转标志 0现金1转账
		record.setTxInd("0");
		// 通存通兑
		record.setDcFlag("1");
		record.setTxAmt(reqBody.getTxAmt());
		record.setPayerAcno(reqBody.getPayerAcc());
		record.setPayerName(reqBody.getPayerName());
		record.setHostState("0");
		record.setTownState("0");
		record.setTxTel(reqSysHead.getUserId());
		record.setTownBranch(reqBody.getBrno());
		record.setTownDate(reqBody.getTownDate());
		record.setTownTraceNo(reqBody.getTownTraceNo());
		// record.setChkTel();
		// record.setAuthTel();
		record.setInfo(reqBody.getInfo());
		rcvTraceService.rcvTraceInit(record);

	}
	private RcvTraceUpdModel updateHostRecord(REQ_TR002 reqDto, Integer hostDate, String hostTraceno,
			String hostState,String retCode,String retMsg) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		RcvTraceUpdModel record = new RcvTraceUpdModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setHostDate(hostDate);
		record.setHostState(hostState);
		record.setHostTraceno(hostTraceno);
		record.setRetCode(retCode);
		record.setRetMsg(retMsg);
		rcvTraceService.rcvTraceUpd(record);
		return record;
	}
}
