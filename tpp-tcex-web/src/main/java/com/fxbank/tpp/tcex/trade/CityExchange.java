package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.constant.CIP;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30011000103;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30011000103;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30043000101;
import com.fxbank.tpp.esb.model.tcex.ESB_REP_TS0012;
import com.fxbank.tpp.esb.model.tcex.ESB_REP_TS0014;
import com.fxbank.tpp.esb.model.tcex.ESB_REQ_TS0012;
import com.fxbank.tpp.esb.model.tcex.ESB_REQ_TS0014;
import com.fxbank.tpp.esb.model.tcex.SafeModel;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.esb.service.IForwardToTownService;
import com.fxbank.tpp.esb.service.ISafeService;
import com.fxbank.tpp.tcex.dto.esb.REP_30041001001;
import com.fxbank.tpp.tcex.dto.esb.REQ_30041001001;
import com.fxbank.tpp.tcex.exception.TcexTradeExecuteException;
import com.fxbank.tpp.tcex.model.SndTraceInitModel;
import com.fxbank.tpp.tcex.model.SndTraceUpdModel;
import com.fxbank.tpp.tcex.service.ISndTraceService;

import redis.clients.jedis.Jedis;

/** 
* @ClassName: CityExchange 
* @Description: 商行通兑村镇
* @author DuZhenduo
* @date 2018年12月18日 下午2:50:42 
*  
*/
@Service("REQ_30041001001")
public class CityExchange implements TradeExecutionStrategy {

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
	
	@Reference(version = "1.0.0")
	private ISafeService passwordService;
	
	private final static String COMMON_PREFIX = "tcex_common.";
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30041001001 reqDto = (REQ_30041001001) dto;
		REP_30041001001 repDto = new REP_30041001001();
		// 插入流水表
		initRecord(reqDto);
		myLog.info(logger, "商行通兑村镇登记成功，渠道日期" + reqDto.getSysDate() + 
				"渠道流水号" + reqDto.getSysTraceno());
		// 通知村镇记账
		ESB_REP_TS0012 esbRep_TS002 = null;
		String townBranch = null;
		String townDate = null;
		String townTraceNo = null;
		try {
		    esbRep_TS002 = townCharge(reqDto);
		    ESB_REP_TS0012.REP_BODY esbRepBody_TS002 = esbRep_TS002.getRepBody();
			townBranch = esbRepBody_TS002.getBrno();
			townDate = esbRepBody_TS002.getTownDate();
			townTraceNo = esbRepBody_TS002.getTownTraceno();
		}catch(SysTradeExecuteException e) {
			updateTownRecord(reqDto, "", "", "", "2");
			myLog.error(logger, "商行通兑村镇村镇记账失败，渠道日期" + reqDto.getSysDate() + 
					"渠道流水号" + reqDto.getSysTraceno(), e);
			throw e;
		}
		// 更新流水表村镇记账状态
		//村镇记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败
			updateTownRecord(reqDto, townBranch, townDate, townTraceNo, "1");
			myLog.info(logger, "商行通兑村镇村镇记账成功，渠道日期" + reqDto.getSysDate() + 
					"渠道流水号" + reqDto.getSysTraceno());
			// 核心记账：将金额从头寸中划至指定账户
			// 记账状态码
			String hostCode = null;
			String hostMsg = null;
			// 记账流水号
			String hostSeqno = null;
			// 核心日期
			String hostDate = null;
			//记账机构
			String accounting_branch = null;
			ESB_REP_30011000103 esbRep_30011000103 = null;
			try {
			    esbRep_30011000103 = hostCharge(reqDto);
			    hostCode = esbRep_30011000103.getRepSysHead().getRet().get(0).getRetCode();
				hostMsg = esbRep_30011000103.getRepSysHead().getRet().get(0).getRetMsg();
				hostSeqno = esbRep_30011000103.getRepBody().getReference();
				repDto.getRepBody().setHostTraceNo(hostSeqno);
				hostDate = esbRep_30011000103.getRepSysHead().getRunDate();
				accounting_branch = esbRep_30011000103.getRepBody().getAccountingBranch();
			}catch(SysTradeExecuteException e) {
				//商行核心记账失败，村镇冲正
				updateHostRecord(reqDto, "", "", "2", e.getRspCode(), e.getRspMsg(),"");
				myLog.error(logger, "商行通兑村镇核心记账失败，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				// 村镇冲正
				try {	
					 townReversal(reqDto, townDate, townTraceNo);
				}catch(SysTradeExecuteException e1) {
					updateTownRecord(reqDto, "", "", "", "6");
					myLog.error(logger, "商行通兑村镇村镇冲正失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e1);
					TcexTradeExecuteException e2 = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10012,e.getRspMsg()+"(村镇冲正失败)");
					throw e2;
				}
				// 更新流水表村镇记账状态
				// 村镇记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败
				updateTownRecord(reqDto, townBranch, townDate, townTraceNo, "5");
				TcexTradeExecuteException e2 = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10012,e.getRspMsg()+"(村镇冲正成功)");
				myLog.error(logger, "商行通兑村镇村镇冲正成功，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(),e2);
				throw e2;
			}
			// 更新流水表核心记账状态
				repDto.getRepBody().setBalance(esbRep_TS002.getRepBody().getBackTal());
				myLog.info(logger, "商行通兑村镇核心记账成功，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno());
				updateHostRecord(reqDto, hostDate, hostSeqno, "1", hostCode, hostMsg,accounting_branch);
			
		
		return repDto;
	}

	/** 
	* @Title: townReversal 
	* @Description: 村镇撤销
	* @param reqDto
	* @param townDate 村镇日期
	* @param townTraceNo 村镇流水号
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_TS004    返回类型 
	* @throws 
	*/
	private ESB_REP_TS0014 townReversal(REQ_30041001001 reqDto, String townDate, String townTraceNo)
			throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		ESB_REQ_TS0014 esbReq_TS004 = new ESB_REQ_TS0014(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqTS004SysHead = new EsbReqHeaderBuilder(esbReq_TS004.getReqSysHead(), reqDto)
				.setBranchId(reqDto.getReqSysHead().getBranchId()).setUserId(reqDto.getReqSysHead().getUserId())
				.build();
		esbReq_TS004.setReqSysHead(reqTS004SysHead);
		ESB_REQ_TS0014.REQ_BODY esbReqBody_TS004 = esbReq_TS004.getReqBody();
		esbReqBody_TS004.setPlatDate(townDate);
		esbReqBody_TS004.setPlatTraceno(townTraceNo);
		ESB_REP_TS0014 esbRep_TS004 = forwardToTownService.sendToTown(esbReq_TS004, esbReqBody_TS004,
				ESB_REP_TS0014.class);
		return esbRep_TS004;
	}

	/** 
	* @Title: townCharge 
	* @Description: 村镇记账
	* @param reqDto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_TS002    返回类型 
	* @throws 
	*/
	private ESB_REP_TS0012 townCharge(REQ_30041001001 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		ESB_REQ_TS0012 esbReq_TS002 = new ESB_REQ_TS0012(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_TS002.getReqSysHead(), reqDto)
				.setBranchId(reqDto.getReqSysHead().getBranchId()).setUserId(reqDto.getReqSysHead().getUserId())
				.build();
		esbReq_TS002.setReqSysHead(reqSysHead);
		ESB_REQ_TS0012.REQ_BODY esbReqBody_TS002 = esbReq_TS002.getReqBody();
		REQ_30041001001.REQ_BODY reqBody = reqDto.getReqBody();
		esbReqBody_TS002.setBrnoFlag(reqBody.getVillageBrnachFlag());
		esbReqBody_TS002.setPlatDate(reqDto.getSysDate().toString());
		esbReqBody_TS002.setPlatTraceno(reqDto.getSysTraceno().toString());
		esbReqBody_TS002.setTxAmt(reqBody.getTranAmt());
		esbReqBody_TS002.setPayerName(reqBody.getPayerName());
		esbReqBody_TS002.setPayerAcc(reqBody.getPayerAcctNo());
		SafeModel passwordModel = new SafeModel(logPool.get(), reqDto.getSysDate(), 
				reqDto.getSysTime(),reqDto.getSysTraceno());
		passwordModel.setAcctNo(reqBody.getPayerAcctNo());
		passwordModel.setPassword(reqBody.getPassword());
		passwordModel = passwordService.transPin(passwordModel);
		esbReqBody_TS002.setPayerPwd(passwordModel.getPassword());
		esbReqBody_TS002.setIdType(reqBody.getDocumentType());
		esbReqBody_TS002.setIdNo(reqBody.getDocumentID());
		esbReqBody_TS002.setInfo(reqBody.getNarrative());

		ESB_REP_TS0012 esbRep_TS002 = forwardToTownService.sendToTown(esbReq_TS002, esbReqBody_TS002,
				ESB_REP_TS0012.class);
		return esbRep_TS002;
	}

	/** 
	* @Title: hostCharge 
	* @Description: 核心记账
	* @param reqDto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30011000103    返回类型 
	* @throws 
	*/
	private ESB_REP_30011000103 hostCharge(REQ_30041001001 reqDto)
			throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		REQ_30041001001.REQ_BODY reqBody = reqDto.getReqBody();
		// 村镇标志
		String townFlag = reqBody.getVillageBrnachFlag();
		// 交易机构
		String txBrno = reqDto.getReqSysHead().getBranchId();
		// 柜员号
		String txTel = reqDto.getReqSysHead().getUserId();

		ESB_REQ_30011000103 esbReq_30011000103 = new ESB_REQ_30011000103(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000103.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).setSourceType("LV").build();
		esbReq_30011000103.setReqSysHead(reqSysHead);

		ESB_REQ_30011000103.REQ_BODY reqBody_30011000103 = esbReq_30011000103.getReqBody();
		// 账号/卡号
		reqBody_30011000103.setBaseAcctNo(reqBody.getPayerAcctNo());
		// 村镇机构号
		//reqBody_30011000103.setVillageBrnachId();
		// 村镇标志 1-于洪 2-铁岭 7-彰武 8-阜蒙
		reqBody_30011000103.setVillageFlag(townFlag);
		// 交易类型
		reqBody_30011000103.setTranType("LV04");
		// 交易币种
		reqBody_30011000103.setTranCcy("CNY");
		// 交易金额
		reqBody_30011000103.setTranAmt(reqBody.getTranAmt());
		// 密码
		reqBody_30011000103.setPassword(reqBody.getPassword());
		//N新核心O老核心
		reqBody_30011000103.setNewCoreFlag("N");
		ESB_REP_30011000103 esbRep_30011000103 = forwardToESBService.sendToESB(esbReq_30011000103, reqBody_30011000103,
				ESB_REP_30011000103.class);
		return esbRep_30011000103;
	}

	/** 
	* @Title: initRecord 
	* @Description: 交易登记
	* @param reqDto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return    返回类型 
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
		record.setTxInd("0");
		// 通存通兑
		record.setDcFlag("1");
		record.setTxAmt(reqBody.getTranAmt());
		record.setPayerAcno(reqBody.getPayerAcctNo());
		record.setPayerName(reqBody.getPayerName());
		record.setHostState("0");
		record.setTownState("0");
		record.setTxTel(reqSysHead.getUserId());
		record.setTownFlag(reqBody.getVillageBrnachFlag());
		record.setChkTel(reqSysHead.getApprUserId());
		record.setAuthTel(reqSysHead.getAuthUserId());
		record.setPrint("0");
		record.setCheckFlag("1");
		record.setInfo(reqBody.getNarrative());
		sndTraceService.sndTraceInit(record);
	}

	/** 
	* @Title: updateHostRecord 
	* @Description: 更新核心记账状态
	* @param reqDto
	* @param hostDate 核心日期
	* @param hostTraceno 核心流水号
	* @param hostState 核心记账状态
	* @param retCode 核心响应码
	* @param retMsg 核心响应信息
	* @param accounting_branch 核心记账机构
	* @param @throws SysTradeExecuteException    设定文件 
	* @return SndTraceUpdModel  返回类型 
	* @throws 
	*/
	private SndTraceUpdModel updateHostRecord(REQ_30041001001 reqDto, String hostDate, String hostTraceno,
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

	/** 
	* @Title: updateTownRecord 
	* @Description: 更新村镇记账状态
	* @param reqDto
	* @param townBrno 村镇记账机构
	* @param townDate 村镇日期
	* @param townTraceno 村镇流水号
	* @param townState 村镇记账状态
	* @param @throws SysTradeExecuteException    设定文件 
	* @return SndTraceUpdModel  返回类型 
	* @throws 
	*/
	private SndTraceUpdModel updateTownRecord(REQ_30041001001 reqDto, String townBrno, String townDate,
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
	/** 
	* @Title: hostTranResult 
	* @Description: 核心交易结果查询
	* @param reqDto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30043000101  返回类型 
	* @throws 
	*/
	private ESB_REP_30043000101 hostTranResult(REQ_30041001001 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		ESB_REQ_30043000101 esbReq_30043000101 = new ESB_REQ_30043000101(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30043000101.getReqSysHead(), reqDto)
				.setBranchId(reqDto.getReqSysHead().getBranchId()).setUserId(reqDto.getReqSysHead().getUserId())
				.build();
		esbReq_30043000101.setReqSysHead(reqSysHead);

		ESB_REQ_30043000101.REQ_BODY reqBody_30043000101 = esbReq_30043000101.getReqBody();
		// 记账渠道类型
		reqBody_30043000101.setChannelType("GJ");

		String platTrace = String.format("%08d", reqDto.getSysTraceno());// 左补零
		// 渠道流水号
		reqBody_30043000101.setChannelSeqNo(CIP.SYSTEM_ID + reqDto.getSysDate() + platTrace);
		ESB_REP_30043000101 esb_rep_30043000101 = null;
		try {
			// 如果第一次查询没查到内容再查询一次
			esb_rep_30043000101 = forwardToESBService.sendToESB(esbReq_30043000101, reqBody_30043000101,
					ESB_REP_30043000101.class);
		} catch (SysTradeExecuteException e) {
			if (e.getRspCode().equals("RB4029")) {
				try {
					esb_rep_30043000101 = forwardToESBService.sendToESB(esbReq_30043000101, reqBody_30043000101,
							ESB_REP_30043000101.class);
				} catch (SysTradeExecuteException e1) {
					if (e1.getRspCode().equals("RB4029")) {
						try {
							esb_rep_30043000101 = forwardToESBService.sendToESB(esbReq_30043000101, reqBody_30043000101,
									ESB_REP_30043000101.class);
						} catch (SysTradeExecuteException e2) {
							if (e2.getRspCode().equals("RB4029")) {
								return esb_rep_30043000101;
							} else {
								logger.error(e2.getRspCode() + " | " + e2.getRspMsg());
								throw e2;
							}

						}
					} else {
						logger.error(e1.getRspCode() + " | " + e1.getRspMsg());
						throw e1;
					}
				}
			} else {
				logger.error(e.getRspCode() + " | " + e.getRspMsg());
				throw e;
			}
		}

		return esb_rep_30043000101;
	}
}
