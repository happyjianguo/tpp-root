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
import com.fxbank.tpp.esb.model.ses.ESB_REP_TS002;
import com.fxbank.tpp.esb.model.ses.ESB_REP_TS004;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30011000103;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_TS002;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_TS004;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.esb.service.IForwardToTownService;
import com.fxbank.tpp.tcex.dto.esb.REP_30041001001;
import com.fxbank.tpp.tcex.dto.esb.REQ_30041001001;
import com.fxbank.tpp.tcex.exception.TcexTradeExecuteException;
import com.fxbank.tpp.tcex.model.SndTraceInitModel;
import com.fxbank.tpp.tcex.model.SndTraceUpdModel;
import com.fxbank.tpp.tcex.service.ISndTraceService;

/**
 * 商行通兑业务
 * 
 * @author liye
 *
 */
@Service("REQ_30041001001")
public class CityExchange implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CityDeposit.class);

	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IForwardToTownService forwardToTownService;

	@Reference(version = "1.0.0")
	private ISndTraceService sndTraceService;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30041001001 reqDto = (REQ_30041001001) dto;
		REP_30041001001 repDto = new REP_30041001001();
		//村镇编号
		String townID = reqDto.getReqBody().getBrnoFlag();
		// 插入流水表
		initRecord(reqDto);
		myLog.info(logger, "商行通兑村镇登记成功，村镇机构"+townID+"付款账号"+reqDto.getReqBody().getPayerAcctNo());
		// 通知村镇记账
		ESB_REP_TS002 esbRep_TS002 = townCharge(reqDto);
		ESB_REP_TS002.REP_BODY esbRepBody_TS002 = esbRep_TS002.getRepBody();
		String townBrno = esbRepBody_TS002.getBrno();
		Integer townDate = Integer.parseInt(esbRepBody_TS002.getTownDate());
		String townTraceNo = esbRepBody_TS002.getTownTraceno();
		String townState = esbRep_TS002.getRepSysHead().getRet().get(0).getRetCode();
		// 更新流水表村镇记账状态
		if ("000000".equals(townState)) {
			updateTownRecord(reqDto, townBrno, townDate, townTraceNo, "1");
			// 核心记账：将金额从头寸中划至指定账户
			// 记账状态码
			String hostCode = null;
			String hostMsg = null;
			// 记账流水号
			String hostSeqno = null;
			// 核心日期
			String hostDate = null;
			ESB_REP_30011000103 esbRep_30011000103 = innerCapCharge(reqDto, townBrno);
			hostCode = esbRep_30011000103.getRepSysHead().getRet().get(0).getRetCode();
		    hostMsg = esbRep_30011000103.getRepSysHead().getRet().get(0).getRetMsg();
			hostSeqno = esbRep_30011000103.getRepBody().getReference();
			hostDate = esbRep_30011000103.getRepSysHead().getRunDate();
			//开户机构
			String acctBranch = esbRep_30011000103.getRepBody().getAcctBranch();
			//记账机构
			String accounting_branch = esbRep_30011000103.getRepBody().getAccountingBranch();
			//记账结果，00-已记账 01-已挂账
			String acctResult = esbRep_30011000103.getRepBody().getAcctResult();
			// 更新流水表核心记账状态
			updateHostRecord(reqDto, Integer.parseInt(hostDate), hostSeqno, "1",hostCode,hostMsg);
			if (!"000000".equals(hostCode)) {
				// 多次查询核心，确认是否是延迟原因

				// 村镇冲正
				ESB_REP_TS004 esbRep_TS004 = townCancel(reqDto, townDate, townTraceNo);
				ESB_REP_TS004.REP_BODY esbRepBody_TS004 = esbRep_TS004.getRepBody();
				String sts = esbRepBody_TS004.getSts();
				// 更新流水表村镇记账状态,村镇冲正返回状态sts 1-成功2-失败
				//村镇记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败
				townState = "6";
				if("1".equals(sts)) {
					townState = "5";
				}
				updateTownRecord(reqDto, townBrno, townDate, townTraceNo, townState);
			}
		} else {
			updateTownRecord(reqDto, townBrno, townDate, townTraceNo, "2");
			TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10004);
			myLog.error(logger, "村镇记账失败", e);
			throw e;
		}
		return repDto;
	}
	/** 
	* @Title: townCancel 
	* @Description: 村镇冲正
	* @param @param reqDto
	* @param @param townDate
	* @param @param townTraceNo
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_TS004    返回类型 
	* @throws 
	*/
	private ESB_REP_TS004 townCancel(REQ_30041001001 reqDto, Integer townDate, String townTraceNo)
			throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		ESB_REQ_TS004 esbReq_TS004 = new ESB_REQ_TS004(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqTS004SysHead = new EsbReqHeaderBuilder(esbReq_TS004.getReqSysHead(), reqDto)
				.setBranchId(reqDto.getReqSysHead().getBranchId()).setUserId(reqDto.getReqSysHead().getUserId())
				.build();
		esbReq_TS004.setReqSysHead(reqTS004SysHead);
		ESB_REQ_TS004.REQ_BODY esbReqBody_TS004 = esbReq_TS004.getReqBody();
		esbReqBody_TS004.setPlatDate(townDate.toString());
		esbReqBody_TS004.setPlatTraceno(townTraceNo);
		ESB_REP_TS004 esbRep_TS004 = forwardToTownService.sendToTown(esbReq_TS004, esbReqBody_TS004,
				ESB_REP_TS004.class);
		return esbRep_TS004;
	}
	/** 
	* @Title: townCharge 
	* @Description: 村镇记账
	* @param @param reqDto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_TS002    返回类型 
	* @throws 
	*/
	private ESB_REP_TS002 townCharge(REQ_30041001001 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		ESB_REQ_TS002 esbReq_TS002 = new ESB_REQ_TS002(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_TS002.getReqSysHead(), reqDto)
				.setBranchId(reqDto.getReqSysHead().getBranchId()).setUserId(reqDto.getReqSysHead().getUserId())
				.build();
		esbReq_TS002.setReqSysHead(reqSysHead);
		ESB_REQ_TS002.REQ_BODY esbReqBody_TS002 = esbReq_TS002.getReqBody();
		REQ_30041001001.REQ_BODY reqBody = reqDto.getReqBody();
		esbReqBody_TS002.setPlatDate(reqDto.getSysDate().toString());
		esbReqBody_TS002.setPlatTraceno(reqDto.getSysTraceno().toString());
		esbReqBody_TS002.setTxAmt(reqBody.getTranAmt());
		esbReqBody_TS002.setPayerName(reqBody.getPayerName());
		esbReqBody_TS002.setPayerAcc(reqBody.getPayerAcctNo());
		esbReqBody_TS002.setPayerPwd(reqBody.getPayPassword());
		esbReqBody_TS002.setIDtype(reqBody.getDocumentType());
		esbReqBody_TS002.setIDno(reqBody.getDocumentID());
		esbReqBody_TS002.setInfo(reqBody.getNarrative());

		ESB_REP_TS002 esbRep_TS002 = forwardToTownService.sendToTown(esbReq_TS002, esbReqBody_TS002,
				ESB_REP_TS002.class);
		return esbRep_TS002;
	}

	/** 
	* @Title: innerCapCharge 
	* @Description: 行内核心记本金账务
	* @param @param reqDto
	* @param @param townBrno
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30011000103    返回类型 
	* @throws 
	*/
	private ESB_REP_30011000103 innerCapCharge(REQ_30041001001 reqDto, String townBrno)
			throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		REQ_30041001001.REQ_BODY reqBody = reqDto.getReqBody();
		//村镇编号
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
		reqBody_30011000103.setBaseAcctNo(reqBody.getPayerAcctNo());
		//村镇机构号
		reqBody_30011000103.setVillageBrnachId(townBrno);
		//村镇标志 1-于洪 2-铁岭 7-彰武 8-阜蒙
		reqBody_30011000103.setVillageFlag(townId);
		// 交易类型
		reqBody_30011000103.setTranType("LV04");
		// 交易币种
		reqBody_30011000103.setTranCcy("CNY");
		// 交易金额
		reqBody_30011000103.setTranAmt(reqBody.getTranAmt());
		// 密码
		reqBody_30011000103.setPassword(reqBody.getPayPassword());
		ESB_REP_30011000103 esbRep_30011000103 = forwardToESBService.sendToESB(esbReq_30011000103, reqBody_30011000103,
				ESB_REP_30011000103.class);
		return esbRep_30011000103;
	}
	/** 
	* @Title: initRecord 
	* @Description: 登记流水表
	* @param @param reqDto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return
	* @throws 
	*/
	private void initRecord(REQ_30041001001 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		REQ_30041001001.REQ_BODY reqBody = reqDto.getReqBody();
		REQ_SYS_HEAD reqSysHead = reqDto.getReqSysHead();

		SndTraceInitModel record = new SndTraceInitModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setSourceType(reqBody.getChannelType());
		record.setTxBranch(reqSysHead.getBranchId());
		// 现转标志 0现金1转账
		record.setTxInd(reqBody.getMfflg());
		// 通存通兑
		record.setDcFlag("1");
		record.setTxAmt(reqBody.getTranAmt());
		if ("1".equals(reqBody.getMfflg())) {
			record.setPayeeAcno(reqBody.getPayeeAcctNo());
			record.setPayeeName(reqBody.getPayeeAcctName());
		}
		record.setPayerAcno(reqBody.getPayerAcctNo());
		record.setPayerName(reqBody.getPayerName());
		record.setHostState("0");
		record.setTownState("0");
		record.setTxTel(reqSysHead.getUserId());
		// record.setChkTel();
		// record.setAuthTel();
		record.setInfo(reqBody.getNarrative());
		sndTraceService.sndTraceInit(record);
	}
	/** 
	* @Title: updateHostRecord 
	* @Description: 行内核心记本金账务
	* @param @param reqDto
	* @param @param hostDate
	* @param @param hostTraceno
	* @param @param hostState
	* @param @throws SysTradeExecuteException    设定文件 
	* @return SndTraceUpdModel    返回类型 
	* @throws 
	*/
	private SndTraceUpdModel updateHostRecord(REQ_30041001001 reqDto, Integer hostDate, String hostTraceno,
			String hostState,String retCode,String retMsg) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		SndTraceUpdModel record = new SndTraceUpdModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setHostDate(hostDate);
		record.setHostState(hostState);
		record.setHostTraceno(hostTraceno);
		record.setRetCode(retCode);
		record.setRetMsg(retMsg);
		sndTraceService.sndTraceUpd(record);
		return record;
	}
	/** 
	* @Title: updateTownRecord 
	* @Description: 行内核心记本金账务
	* @param @param reqDto
	* @param @param townBrno
	* @param @param townDate
	* @param @param townTraceno
	* @param @param townState
	* @param @throws SysTradeExecuteException    设定文件 
	* @return SndTraceUpdModel    返回类型 
	* @throws 
	*/
	private SndTraceUpdModel updateTownRecord(REQ_30041001001 reqDto, String townBrno, Integer townDate,
			String townTraceno, String townState) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		SndTraceUpdModel record = new SndTraceUpdModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setTownBranch(townBrno);
		record.setTownDate(townDate);
		record.setTownState(townState);
		record.setTownTraceno(townTraceno);
		sndTraceService.sndTraceUpd(record);
		return record;
	}
}
