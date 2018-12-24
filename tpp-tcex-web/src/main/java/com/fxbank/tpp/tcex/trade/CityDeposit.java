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
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30011000103;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30014000101;
import com.fxbank.tpp.esb.model.ses.ESB_REP_TS001;
import com.fxbank.tpp.esb.model.ses.ESB_REP_TS003;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30011000103;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30014000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_TS001;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_TS003;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.esb.service.IForwardToTownService;
import com.fxbank.tpp.tcex.dto.esb.REP_30041000901;
import com.fxbank.tpp.tcex.dto.esb.REQ_30041000901;
import com.fxbank.tpp.tcex.exception.TcexTradeExecuteException;
import com.fxbank.tpp.tcex.model.SndTraceInitModel;
import com.fxbank.tpp.tcex.model.SndTraceUpdModel;
import com.fxbank.tpp.tcex.service.ISndTraceService;
/**
 * 商行通存业务
 * @author liye
 *
 */
@Service("REQ_30041000901")
public class CityDeposit implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(CityDeposit.class);

	@Resource
	private LogPool logPool;
	
	@Resource
	private MyJedis myJedis;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IForwardToTownService forwardToTownService;

	@Reference(version = "1.0.0")
	private ISndTraceService sndTraceService;

	private final static String COMMON_PREFIX = "tcex_common.";
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
				
	    MyLog myLog = logPool.get();
		REQ_30041000901 reqDto = (REQ_30041000901) dto;
		REP_30041000901 repDto = new REP_30041000901();
		//插入流水表
		initRecord(reqDto);
		String hostCode = null;
		String hostMsg = null;
		// 记账流水号
		String hostSeqno = null;
		// 核心日期
		String hostDate = null;
		ESB_REP_30011000103 esbRep_30011000103 = null;
		// 核心记账
		try {
			   esbRep_30011000103 = hostCharge(reqDto);
			}catch(SysTradeExecuteException e) {
				updateHostRecord(reqDto, "", "", "2", e.getRspCode(), e.getRspMsg(),"");
				myLog.error(logger, "商行通存业务失败，渠道日期" + dto.getSysDate() + 
						"渠道流水号" + dto.getSysTraceno());
			}
		hostCode = esbRep_30011000103.getRepSysHead().getRet().get(0).getRetCode();
		hostMsg = esbRep_30011000103.getRepSysHead().getRet().get(0).getRetMsg();
		hostSeqno = esbRep_30011000103.getRepBody().getReference();
		hostDate = esbRep_30011000103.getRepSysHead().getRunDate();
		// 开户机构
		String acctBranch = esbRep_30011000103.getRepBody().getAcctBranch();
		// 记账机构
		String accounting_branch = esbRep_30011000103.getRepBody().getAccountingBranch();
		// 更新流水表核心记账状态
		if("000000".equals(hostCode)) {
			updateHostRecord(reqDto, hostDate, hostSeqno, "1", hostCode, hostMsg,accounting_branch);
			// 通知村镇记账
			ESB_REP_TS001 esbRep_TS001 = null;
			try {
			    esbRep_TS001 = townCharge(reqDto);
			}catch(SysTradeExecuteException e) {
				updateTownRecord(reqDto, "", "", "", "2");
				myLog.error(logger, "商行通存村镇村镇记账失败，渠道日期" + dto.getSysDate() + 
						"渠道流水号" + dto.getSysTraceno(), e);
				throw e;
			}
			ESB_REP_TS001.REP_BODY esbRepBody_TS001 = esbRep_TS001.getRepBody();
			String townBranch = esbRepBody_TS001.getBrno();
			String townDate = esbRepBody_TS001.getTownDate();
			String townTraceNo = esbRepBody_TS001.getTownTraceno();
			String townRetCode = esbRep_TS001.getRepSysHead().getRet().get(0).getRetCode();
			//村镇记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败
			if("000000".equals(townRetCode)){
				updateTownRecord(reqDto, townBranch, townDate, townTraceNo, "1");
				myLog.info(logger, "商行通存村镇村镇记账成功，渠道日期" + dto.getSysDate() + "渠道流水号" + dto.getSysTraceno());				
			}
			else if("@@@@".equals(townRetCode)){
				ESB_REP_TS003 esbRep_TS003 = null;
				try {
				    esbRep_TS003 = townTranResult(reqDto);
				}catch(SysTradeExecuteException e) {
					myLog.error(logger, "商行通存村镇存款确认失败，渠道日期" + dto.getSysDate() + 
							"渠道流水号" + dto.getSysTraceno(), e);
					throw e;
				}
				updateTownRecord(reqDto, "", "", "", "4");
				
			}
			else{
				//核心记账状态，0-登记，1-成功，2-失败，3-超时，5-冲正成功，6-冲正失败，7-冲正超时
				ESB_REP_30014000101 esbRep_30014000101 = null;
				try {
					esbRep_30014000101 = hostCancel(reqDto);
				}catch(SysTradeExecuteException e) {
					updateHostRecord(reqDto, hostDate, hostSeqno, "6", hostCode, hostMsg,"");
					myLog.error(logger, "商行通存村镇存款确认失败，渠道日期" + dto.getSysDate() + 
							"渠道流水号" + dto.getSysTraceno(), e);
					throw e;
				}
				updateHostRecord(reqDto, hostDate, hostSeqno, "5", hostCode, hostMsg,"");
				
	
}
			
			
		} else {
			updateHostRecord(reqDto, hostDate, hostSeqno, "2", hostCode, hostMsg,accounting_branch);
			TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10004);
			myLog.error(logger, "商行通兑村镇商行记账失败，渠道日期"+dto.getSysDate()+
					"渠道流水号"+dto.getSysTraceno(), e);
			throw e;
		}
		



		

		
		
		
		return repDto;
	}
	private void initRecord(REQ_30041000901 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		REQ_30041000901.REQ_BODY reqBody = reqDto.getReqBody();
		REQ_SYS_HEAD reqSysHead = reqDto.getReqSysHead();

		SndTraceInitModel record = new SndTraceInitModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setSourceType(reqBody.getChannelType());
		record.setTxBranch(reqSysHead.getBranchId());
		// 通存通兑
		record.setDcFlag("1");
		record.setTxAmt(reqBody.getTranAmt());
		record.setHostState("0");
		record.setTownState("0");
		record.setTxTel(reqSysHead.getUserId());
		record.setTownFlag(reqBody.getBrnoFlag());
		// record.setChkTel();
		// record.setAuthTel();
		record.setInfo(reqBody.getNarrative());
		sndTraceService.sndTraceInit(record);
	}
	
	private ESB_REP_30011000103 hostCharge(REQ_30041000901 reqDto)
			throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		REQ_30041000901.REQ_BODY reqBody = reqDto.getReqBody();
		// 村镇标志
		String townFlag = reqBody.getBrnoFlag();
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
		// 村镇标志 1-于洪 2-铁岭 7-彰武 8-阜蒙
		reqBody_30011000103.setVillageFlag(townFlag);
		// 交易类型
		reqBody_30011000103.setTranType("LV03");
		// 交易币种
		reqBody_30011000103.setTranCcy("CNY");
		// 交易金额
		reqBody_30011000103.setTranAmt(reqBody.getTranAmt());

		ESB_REP_30011000103 esbRep_30011000103 = forwardToESBService.sendToESB(esbReq_30011000103, reqBody_30011000103,
				ESB_REP_30011000103.class);
		return esbRep_30011000103;
	}
	
	private SndTraceUpdModel updateHostRecord(REQ_30041000901 reqDto, String hostDate, String hostTraceno,
			String hostState, String retCode, String retMsg,String accounting_branch) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		SndTraceUpdModel record = new SndTraceUpdModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		if(!"".equals(hostDate)) {
		record.setHostDate(Integer.parseInt(hostDate));
		}
		record.setHostState(hostState);
		record.setHostTraceno(hostTraceno);
		record.setRetCode(retCode);
		record.setRetMsg(retMsg);
		record.setHostBranch(accounting_branch);
		sndTraceService.sndTraceUpd(record);
		return record;
	}
	private SndTraceUpdModel updateTownRecord(REQ_30041000901 reqDto, String townBrno, String townDate,
			String townTraceno, String townState) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		SndTraceUpdModel record = new SndTraceUpdModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setTownBranch(townBrno);
		if(!"".equals(townDate)) {
		record.setTownDate(Integer.parseInt(townDate));
		}
		record.setTownState(townState);
		record.setTownTraceno(townTraceno);
		sndTraceService.sndTraceUpd(record);
		return record;
	}
	private ESB_REP_TS001 townCharge(REQ_30041000901 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		ESB_REQ_TS001 esbReq_TS001 = new ESB_REQ_TS001(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_TS001.getReqSysHead(), reqDto)
				.setBranchId(reqDto.getReqSysHead().getBranchId()).setUserId(reqDto.getReqSysHead().getUserId())
				.build();
		esbReq_TS001.setReqSysHead(reqSysHead);
		ESB_REQ_TS001.REQ_BODY esbReqBody_TS001 = esbReq_TS001.getReqBody();
		REQ_30041000901.REQ_BODY reqBody = reqDto.getReqBody();
		esbReqBody_TS001.setPlatDate(reqDto.getSysDate().toString());
		esbReqBody_TS001.setPlatTraceno(reqDto.getSysTraceno().toString());
		esbReqBody_TS001.setTxAmt(reqBody.getTranAmt());
        esbReqBody_TS001.setInfo(reqBody.getNarrative());
        esbReqBody_TS001.setPayeeAcc(reqBody.getPayeeAcctNo());
        esbReqBody_TS001.setPayeeName(reqBody.getPayeeAcctName());
        esbReqBody_TS001.setBrnoFlag(reqBody.getBrnoFlag());
        
		ESB_REP_TS001 esbRep_TS001 = forwardToTownService.sendToTown(esbReq_TS001, esbReqBody_TS001,
				ESB_REP_TS001.class);
		return esbRep_TS001;
	}
	private ESB_REP_TS003 townTranResult(REQ_30041000901 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		ESB_REQ_TS003 esbReq_TS003 = new ESB_REQ_TS003(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_TS003.getReqSysHead(), reqDto)
				.setBranchId(reqDto.getReqSysHead().getBranchId()).setUserId(reqDto.getReqSysHead().getUserId())
				.build();
		esbReq_TS003.setReqSysHead(reqSysHead);
		ESB_REQ_TS003.REQ_BODY esbReqBody_TS003 = esbReq_TS003.getReqBody();
		REQ_30041000901.REQ_BODY reqBody = reqDto.getReqBody();
		esbReqBody_TS003.setPlatDate(reqDto.getSysDate().toString());
		esbReqBody_TS003.setPlatTraceno(reqDto.getSysTraceno().toString());

        
		ESB_REP_TS003 esbRep_TS003 = forwardToTownService.sendToTown(esbReq_TS003, esbReqBody_TS003,
				ESB_REP_TS003.class);
		return esbRep_TS003;
	}
	private ESB_REP_30014000101 hostCancel(REQ_30041000901 reqDto)
			throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		REQ_30041000901.REQ_BODY reqBody = reqDto.getReqBody();

		// 交易机构
		String txBrno = reqDto.getReqSysHead().getBranchId();
		// 柜员号
		String txTel = reqDto.getReqSysHead().getUserId();

		ESB_REQ_30014000101 esbReq_30014000101 = new ESB_REQ_30014000101(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30014000101.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30014000101.setReqSysHead(reqSysHead);

		ESB_REQ_30014000101.REQ_BODY reqBody_30014000101 = esbReq_30014000101.getReqBody();
		esbReq_30014000101.setReqSysHead(reqSysHead);	

//		reqBody_30014000101.setChannelSeqNo(platTraceno);

		reqBody_30014000101.setReversalReason("村镇【"+txBrno+"】柜面通发起冲正");
		reqBody_30014000101.setEventType("");


		ESB_REP_30014000101 esbRep_30014000101 = forwardToESBService.sendToESB(esbReq_30014000101, reqBody_30014000101,
				ESB_REP_30014000101.class);
		return esbRep_30014000101;
	}


}
