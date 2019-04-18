package com.fxbank.tpp.bocm.trade.esb;

import java.math.BigDecimal;
import javax.annotation.Resource;
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
import com.fxbank.tpp.bocm.dto.esb.REP_30061800101;
import com.fxbank.tpp.bocm.dto.esb.REQ_30061800101;
import com.fxbank.tpp.bocm.dto.esb.REQ_30061800301;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmSndTraceInitModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceUpdModel;
import com.fxbank.tpp.bocm.model.REP_10000;
import com.fxbank.tpp.bocm.model.REP_20000;
import com.fxbank.tpp.bocm.model.REQ_10000;
import com.fxbank.tpp.bocm.model.REQ_20000;
import com.fxbank.tpp.bocm.service.IBocmSndTraceService;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30011000104;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30014000101;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30063000103;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30011000104;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30014000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30063000103;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import redis.clients.jedis.Jedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/** 
* @ClassName: DP_BocmTrsr 
* @Description: 本行卡付款转账 
* @author Duzhenduo
* @date 2019年4月17日 上午9:23:03 
*  
*/
@Service("REQ_30061800101")
public class DP_BocmTra extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(DP_BocmTra.class);

	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IForwardToBocmService forwardToBocmService;

	@Reference(version = "1.0.0")
	private IBocmSndTraceService bocmSndTraceService;

	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "bocm.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061800101 reqDto = (REQ_30061800101) dto;
		REQ_30061800101.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30061800101 rep = new REP_30061800101();
		// 插入流水表
		initRecord(reqDto);
		myLog.info(logger, "本行卡付款转账，登记成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
		// 核心记账
		ESB_REP_30011000104 esbRep_30011000104 = null;
		//核心记账日期
		String hostDate = null;
		//核心记账流水号
		String hostTraceno = null;
		//核心记账返回状态码
		String retCode = null;
		//核心记账返回状态信息
		String retMsg = null;
		try {
			esbRep_30011000104 = hostCharge(reqDto);
			hostDate = esbRep_30011000104.getRepSysHead().getRunDate();
			hostTraceno = esbRep_30011000104.getRepBody().getReference();
			retCode = esbRep_30011000104.getRepSysHead().getRet().get(0).getRetCode();
			retMsg = esbRep_30011000104.getRepSysHead().getRet().get(0).getRetMsg();
		} catch (SysTradeExecuteException e) {
			updateHostRecord(reqDto, "", "", "2", e.getRspCode(), e.getRspMsg());
			myLog.error(logger, "本行卡付款转账，本行核心记账失败，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
			BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002);
			throw e2;
		}
		myLog.info(logger, "本行卡付款转账，本行核心记账成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
		updateHostRecord(reqDto, hostDate, hostTraceno, "1", retCode, retMsg);
		//交行记账流水号
		String bocmTraceNo = null;
		// IC_CARD_FLG_T4判断IC卡磁条卡标志
		if ("0".equals(reqBody.getIcCardFlgT4())) {
			myLog.info(logger, "发送磁条卡转账通存请求至交行");
			REP_10000 rep10000 = null;
			try {
				rep10000 = magCardCharge(reqDto);
				bocmTraceNo = rep10000.getHeader().getrLogNo();
				updateBocmRecord(reqDto,bocmTraceNo,"1");
				myLog.info(logger, "本行卡付款转账，交行磁条卡通存记账成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
			} catch (SysTradeExecuteException e) { // 记账交易参考一下方式处理，查询交易不用
				// 如果不是账务类请求，可以不用分类处理应答码，统一当成失败处理即可
				// 如果交易不关心返回的异常类型，直接可以不捕获，直接省略catch，抛出异常即可
				if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000006) // 生成请求失败
						|| e.getRspCode().equals(SysTradeExecuteException.CIP_E_000007)
						|| e.getRspCode().equals(SysTradeExecuteException.CIP_E_000008)) {
					//交行核心记账报错，本行核心冲正
					updateBocmRecord(reqDto, "", "2");
					myLog.error(logger, "本行卡付款转账，交行磁条卡通存记账失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					//核心记账状态，0-登记，1-成功，2-失败，3-超时，5-冲正成功，6-冲正失败，7-冲正超时
					ESB_REP_30014000101 esbRep_30014000101 = null;
					String hostReversalCode = null;
					String hostReversalMsg = null;
					try {
						esbRep_30014000101 = hostReversal(reqDto,hostTraceno);
						hostReversalCode = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetCode();
						hostReversalMsg = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetMsg();
					}catch(SysTradeExecuteException e1) {
						if("CIP_E_000004".equals(e1.getRspCode())) {
							updateHostRecord(reqDto, "", "", "7", e.getRspCode(), e.getRspMsg());
							myLog.error(logger, "本行卡付款转账，本行核心冲正超时，渠道日期" + reqDto.getSysDate() + 
									"渠道流水号" + reqDto.getSysTraceno(), e1);
							BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(核心冲正超时)");
							throw e2;
						}else {
						updateHostRecord(reqDto, "", "", "6", e.getRspCode(), e.getRspMsg());
						myLog.error(logger, "本行卡付款转账，本行核心冲正失败，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e1);
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(核心冲正失败)");
						throw e2;
						}
					}
					updateHostRecord(reqDto, hostDate, hostTraceno, "5", hostReversalCode, hostReversalMsg);
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(核心冲正成功)");
					myLog.error(logger, "本行卡付款转账，本行核心冲正成功，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(),e2);
					throw e2;
				}else if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000009)) { // 接收交行返回结果超时
					updateBocmRecord(reqDto, "", "3");
					myLog.error(logger, "本行卡付款转账，交行磁条卡通存记账返回结果超时，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					String reBocmTraceNo = null;
					try {
						rep10000 = magCardCharge(reqDto);
						reBocmTraceNo = rep10000.getHeader().getrLogNo();
					    updateBocmRecord(reqDto, reBocmTraceNo, "4");
					    myLog.info(logger, "本行卡付款转账，交行磁条卡通存记账重发成功，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno());
					    return rep;
					}catch(SysTradeExecuteException e1) {
						myLog.error(logger, "本行卡付款转账，交行磁条卡通存记账重发报错，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e1);
						updateBocmRecord(reqDto, "", "4");
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10003);
						throw e2;
					}
				}else if (e.getRspCode().equals("JH6203")) { // 交行返回结果成功，但结果是超时
				       // 确认是否有冲正操作
					updateBocmRecord(reqDto, "", "3");
					myLog.error(logger, "本行卡付款转账，交行磁条卡通存记账返回结果成功，但结果超时，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					String reBocmTraceNo = null;
					try {
						rep10000 = magCardCharge(reqDto);
						reBocmTraceNo = rep10000.getHeader().getrLogNo();
					    updateBocmRecord(reqDto, reBocmTraceNo, "4");
					    myLog.info(logger, "本行卡付款转账，交行磁条卡通存记账重发成功，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno());
					    return rep;
					}catch(SysTradeExecuteException e1) {
						myLog.error(logger, "本行卡付款转账，交行磁条卡通存记账重发报错，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e1);
						updateBocmRecord(reqDto, "", "4");
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10003);
						throw e2;
					}
				} else { // 目标系统应答失败
							// 确认是否有冲正操作
					updateBocmRecord(reqDto, "", "3");
					myLog.error(logger, "本行卡付款转账，交行磁条卡通存记账应答失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					String reBocmTraceNo = null;
					try {
						rep10000 = magCardCharge(reqDto);
						reBocmTraceNo = rep10000.getHeader().getrLogNo();
					    updateBocmRecord(reqDto, reBocmTraceNo, "4");
					    myLog.info(logger, "本行卡付款转账，交行磁条卡通存记账重发成功，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno());
					    return rep;
					}catch(SysTradeExecuteException e1) {
						myLog.error(logger, "本行卡付款转账，交行磁条卡通存记账重发报错，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e1);
						updateBocmRecord(reqDto, "", "4");
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10003);
						throw e2;
					}
				}
			} catch (Exception e) { // 其它未知错误，可以当成超时处理
				// 确认是否有冲正操作
				updateBocmRecord(reqDto, "", "3");
				myLog.error(logger, "本行卡付款转账，交行磁条卡通存记账其它未知错误，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				String reBocmTraceNo = null;
				try {
					rep10000 = magCardCharge(reqDto);
					reBocmTraceNo = rep10000.getHeader().getrLogNo();
				    updateBocmRecord(reqDto, reBocmTraceNo, "4");
				    myLog.info(logger, "本行卡付款转账，交行磁条卡通存记账重发成功，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno());
				    return rep;
				}catch(SysTradeExecuteException e1) {
					myLog.error(logger, "本行卡付款转账，交行磁条卡通存记账重发报错，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					updateBocmRecord(reqDto, "", "4");
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10003);
					throw e2;
				}
			}
		} else {
			myLog.info(logger, "发送IC卡转账通存请求至交行");
			REP_20000 rep20000 = null;
			try {
				rep20000 = iCCardCharge(reqDto);
				bocmTraceNo = rep20000.getHeader().getrLogNo();
				updateBocmRecord(reqDto,bocmTraceNo,"1");
				myLog.info(logger, "本行卡付款转账，交行IC卡通存记账成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
			} catch (SysTradeExecuteException e) { // 记账交易参考一下方式处理，查询交易不用
				// 如果不是账务类请求，可以不用分类处理应答码，统一当成失败处理即可
				// 如果交易不关心返回的异常类型，直接可以不捕获，直接省略catch，抛出异常即可
				if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000006) // 生成请求失败
						|| e.getRspCode().equals(SysTradeExecuteException.CIP_E_000007)
						|| e.getRspCode().equals(SysTradeExecuteException.CIP_E_000008)) {
					//交行核心记账报错，本行核心冲正
					updateBocmRecord(reqDto, "", "2");
					myLog.error(logger, "本行卡付款转账，交行IC卡通存记账失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					//核心记账状态，0-登记，1-成功，2-失败，3-超时，5-冲正成功，6-冲正失败，7-冲正超时
					ESB_REP_30014000101 esbRep_30014000101 = null;
					String hostReversalCode = null;
					String hostReversalMsg = null;
					try {
						esbRep_30014000101 = hostReversal(reqDto,hostTraceno);
						hostReversalCode = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetCode();
						hostReversalMsg = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetMsg();
					}catch(SysTradeExecuteException e1) {
						if("CIP_E_000004".equals(e1.getRspCode())) {
							updateHostRecord(reqDto, "", "", "7", e.getRspCode(), e.getRspMsg());
							myLog.error(logger, "本行卡付款转账，本行核心冲正超时，渠道日期" + reqDto.getSysDate() + 
									"渠道流水号" + reqDto.getSysTraceno(), e1);
							BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(核心冲正超时)");
							throw e2;
						}else {
						updateHostRecord(reqDto, "", "", "6", e.getRspCode(), e.getRspMsg());
						myLog.error(logger, "本行卡付款转账，本行核心冲正失败，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e1);
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(核心冲正失败)");
						throw e2;
						}
					}
					updateHostRecord(reqDto, hostDate, hostTraceno, "5", hostReversalCode, hostReversalMsg);
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(核心冲正成功)");
					myLog.error(logger, "本行卡付款转账，本行核心冲正成功，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(),e2);
					throw e2;
				} else if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000009)) { // 接收交行返回结果超时
					// 确认是否有冲正操作
					updateBocmRecord(reqDto, "", "3");
					myLog.error(logger, "本行卡付款转账，交行IC卡通存记账返回结果超时，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					String reBocmTraceNo = null;
					try {
						rep20000 = iCCardCharge(reqDto);
						reBocmTraceNo = rep20000.getHeader().getrLogNo();
					    updateBocmRecord(reqDto, reBocmTraceNo, "4");
					    myLog.info(logger, "本行卡付款转账，交行IC卡通存记账重发成功，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno());
					    return rep;
					}catch(SysTradeExecuteException e1) {
						myLog.error(logger, "本行卡付款转账，交行IC卡通存记账重发报错，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e);
						updateBocmRecord(reqDto, "", "4");
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10003);
						throw e2;
					}
				} else if (e.getRspCode().equals("JH6203")) { // 交行返回结果成功，但结果是超时
					// 确认是否有冲正操作
					updateBocmRecord(reqDto, "", "3");
					myLog.error(logger, "本行卡付款转账，交行IC卡通存记账返回结果成功，但结果超时，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					String reBocmTraceNo = null;
					try {
						rep20000 = iCCardCharge(reqDto);
						reBocmTraceNo = rep20000.getHeader().getrLogNo();
					    updateBocmRecord(reqDto, reBocmTraceNo, "4");
					    myLog.info(logger, "本行卡付款转账，交行IC卡通存记账重发成功，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno());
					    return rep;
					}catch(SysTradeExecuteException e1) {
						myLog.error(logger, "本行卡付款转账，交行IC卡通存记账重发报错，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e);
						updateBocmRecord(reqDto, "", "4");
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10003);
						throw e2;
					}
				} else { // 目标系统应答失败
							// 确认是否有冲正操作
					updateBocmRecord(reqDto, "", "3");
					myLog.error(logger, "本行卡付款转账，交行IC卡通存记账应答失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					String reBocmTraceNo = null;
					try {
						rep20000 = iCCardCharge(reqDto);
						reBocmTraceNo = rep20000.getHeader().getrLogNo();
					    updateBocmRecord(reqDto, reBocmTraceNo, "4");
					    myLog.info(logger, "本行卡付款转账，交行IC卡通存记账重发成功，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno());
					    return rep;
					}catch(SysTradeExecuteException e1) {
						myLog.error(logger, "本行卡付款转账，交行IC卡通存记账重发报错，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e);
						updateBocmRecord(reqDto, "", "4");
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10003);
						throw e2;
					}
				}
			} catch (Exception e) { // 其它未知错误，可以当成超时处理
				updateBocmRecord(reqDto, "", "3");
				myLog.error(logger, "本行卡付款转账，交行IC卡通存记账其它未知错误，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				String reBocmTraceNo = null;
				try {
					rep20000 = iCCardCharge(reqDto);
					reBocmTraceNo = rep20000.getHeader().getrLogNo();
				    updateBocmRecord(reqDto, reBocmTraceNo, "4");
				    myLog.info(logger, "本行卡付款转账，交行IC卡通存记账重发成功，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno());
				    return rep;
				}catch(SysTradeExecuteException e1) {
					myLog.error(logger, "本行卡付款转账，交行IC卡通存记账重发报错，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					updateBocmRecord(reqDto, "", "4");
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10003);
					throw e2;
				}
			}
		}
		return rep;
	}


	/** 
	* @Title: initRecord 
	* @Description: 登记流水表
	* @param @param reqDto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private void initRecord(REQ_30061800101 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		REQ_30061800101.REQ_BODY reqBody = reqDto.getReqBody();
		REQ_SYS_HEAD reqSysHead = reqDto.getReqSysHead();

		BocmSndTraceInitModel record = new BocmSndTraceInitModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setSourceType(reqSysHead.getSourceType());
		record.setTxBranch(reqSysHead.getBranchId());
		// 通存通兑标志；0通存、1通兑
		record.setDcFlag("0");
		record.setTxAmt(reqBody.getTrsrAmtT3());
		//现转标志；0现金、1转账
		record.setTxInd("1");
		record.setHostState("0");
		record.setBocmState("0");
		record.setTxTel(reqSysHead.getUserId());
		record.setPayerAcno(reqBody.getBnkCardAcctNoT());
		record.setPayerName(reqBody.getBnkCardAcctNaT());
		record.setPayeeAcno(reqBody.getBcmCardAcctNoT());
		record.setPayeeName(reqBody.getBcmCardAcctNaT());
		record.setChkTel(reqSysHead.getApprUserId());
		record.setAuthTel(reqSysHead.getAuthUserId());
		record.setPrint("0");
		record.setCheckFlag("1");
		bocmSndTraceService.sndTraceInit(record);
	}


	/** 
	* @Title: hostCharge 
	* @Description: 本行核心记账 
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30011000103    返回类型 
	* @throws 
	*/
	private ESB_REP_30011000104 hostCharge(REQ_30061800101 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		REQ_30061800101.REQ_BODY reqBody = reqDto.getReqBody();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "txbrno");
			txTel = jedis.get(COMMON_PREFIX + "txtel");
		}

		ESB_REQ_30011000104 esbReq_30011000104 = new ESB_REQ_30011000104(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000104.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30011000104.setReqSysHead(reqSysHead);

		ESB_REQ_30011000104.REQ_BODY reqBody_30011000104 = esbReq_30011000104.getReqBody();
		reqBody_30011000104.setBaseAcctNo(reqBody.getBnkCardAcctNoT());
		reqBody_30011000104.setAcctName(reqBody.getBnkCardAcctNaT());
		reqBody_30011000104.setTranType("JH11");
		reqBody_30011000104.setTranCcy("CNY");
		reqBody_30011000104.setTranAmt(reqBody.getTrsrAmtT3());
		reqBody_30011000104.setWithdrawalType("P");
		reqBody_30011000104.setPassword(reqBody.getPwdT());
		reqBody_30011000104.setOthBaseAcctNo(reqBody.getBcmCardAcctNoT());
		reqBody_30011000104.setOthBaseAcctName(reqBody.getBcmCardAcctNaT());
		reqBody_30011000104.setChannelType("");
		reqBody_30011000104.setChargeMethod(reqBody.getRcveWyT());
		reqBody_30011000104.setSendBankCode(reqBody.getPyrOpnBnkNoT2());
		reqBody_30011000104.setBankCode(reqBody.getPyrOpnBnkNoT2());
		reqBody_30011000104.setOthBankCode(reqBody.getPyeeOpnBnkNoT1());
		reqBody_30011000104.setSettlementDate("");
		reqBody_30011000104.setCollateFlag("Y");

		ESB_REP_30011000104 esbRep_30011000104 = forwardToESBService.sendToESB(esbReq_30011000104, reqBody_30011000104,
				ESB_REP_30011000104.class);
		return esbRep_30011000104;
	}
	
	/** 
	* @Title: updateHostRecord 
	* @Description: 更新核心记账状态 
	* @param @param reqDto
	* @param @param hostDate
	* @param @param hostTraceno
	* @param @param hostState
	* @param @param retCode
	* @param @param retMsg
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return SndTraceUpdModel    返回类型 
	* @throws 
	*/
	private BocmSndTraceUpdModel updateHostRecord(REQ_30061800101 reqDto, String hostDate, String hostTraceno,
			String hostState, String retCode, String retMsg) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		if(!"".equals(hostDate)) {
		record.setHostDate(Integer.parseInt(hostDate));
		}
		record.setHostState(hostState);
		record.setHostTraceno(hostTraceno);
		record.setRetCode(retCode);
		record.setRetMsg(retMsg);
		bocmSndTraceService.sndTraceUpd(record);
		return record;
	}
	
	/** 
	* @Title: updateBocmRecord 
	* @Description: 更新交行记账状态 
	* @param @param reqDto
	* @param @param bocmTraceno
	* @param @param bocmState
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return BocmSndTraceUpdModel    返回类型 
	* @throws 
	*/
	private BocmSndTraceUpdModel updateBocmRecord(REQ_30061800101 reqDto,
			String bocmTraceno, String bocmState) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setBocmState(bocmState);
		record.setBocmTraceno(bocmTraceno);
		bocmSndTraceService.sndTraceUpd(record);
		return record;
	}
	/** 
	* @Title: magCardCharge 
	* @Description: 交行磁条卡通存记账
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return REP_10000    返回类型 
	* @throws 
	*/
	private REP_10000 magCardCharge(REQ_30061800101 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_10000 req10000 = new REQ_10000(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
		super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req10000.getHeader()); // 设置报文头中的行号信息
		REQ_30061800101.REQ_BODY reqBody = reqDto.getReqBody();
		req10000.setTxnAmt(new BigDecimal(reqBody.getTrsrAmtT3()));
		req10000.setFeeFlg(reqBody.getRcveWyT());
		req10000.setFee(new BigDecimal(reqBody.getFeeT3()));
		req10000.setOprFlg(reqBody.getInWyT());
		//业务模式，0 现金1 转账（即实时转账）9 其他
		req10000.setTxnMod("1");
		req10000.setPayBnk(reqBody.getPyrOpnBnkNoT2());
		//付款人账户类型,0 银行账号1 贷记卡2 借记卡3其他
		req10000.setpActTp(reqBody.getPyrAcctTpT());
		req10000.setpActNo(reqBody.getBnkCardAcctNoT());
		req10000.setPayNam(reqBody.getBnkCardAcctNaT());
		req10000.setRecBnk(reqBody.getPyeeOpnBnkNoT1());
		req10000.setrActTp(reqBody.getAcctTpT());
		req10000.setrActNo(reqBody.getBcmCardAcctNoT());
		req10000.setRecNam(reqBody.getBcmCardAcctNaT());
		req10000.setCuIdTp(reqBody.getBcmCardAcctCrtfTpT());
		req10000.setCuIdNo(reqBody.getBcmCardAcctCrtfNoT());
		req10000.setAgIdTp(reqBody.getAgentCrtfT());
		req10000.setAgIdNo(reqBody.getCmsnHldrGlblIdT());
		req10000.setSecMag(reqBody.getScdTrkInfoT2());
		req10000.setThdMag(reqBody.getThrTrkInfoT1());
		req10000.setRemark(reqBody.getNoteT2());
        
		REP_10000 rep_10000 = forwardToBocmService.sendToBocm(req10000, 
				REP_10000.class);
		return rep_10000;
	}
	/** 
	* @Title: iCCardCharge 
	* @Description: 交行IC卡通存记账 
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return REP_20000    返回类型 
	* @throws 
	*/
	private REP_20000 iCCardCharge(REQ_30061800101 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061800101.REQ_BODY reqBody = reqDto.getReqBody();
		REQ_20000 req20000 = new REQ_20000(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
		super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req20000.getHeader()); // 设置报文头中的行号信息
		req20000.setTxnAmt(new BigDecimal(reqBody.getTrsrAmtT3()));
		req20000.setFeeFlg(reqBody.getRcveWyT());
		req20000.setFee(new BigDecimal(reqBody.getFeeT3()));
		req20000.setOprFlg(reqBody.getInWyT());
		//业务模式，0 现金1 转账（即实时转账）9 其他
		req20000.setTxnMod("1");
		req20000.setPayBnk(reqBody.getPyrOpnBnkNoT2());
		//付款人账户类型,0 银行账号1 贷记卡2 借记卡3其他
		req20000.setpActTp(reqBody.getPyrAcctTpT());
		req20000.setpActNo(reqBody.getBnkCardAcctNoT());
		req20000.setPayNam(reqBody.getBnkCardAcctNaT());
		req20000.setRecBnk(reqBody.getPyeeOpnBnkNoT1());
		req20000.setrActTp(reqBody.getAcctTpT());
		req20000.setrActNo(reqBody.getBcmCardAcctNoT());
		req20000.setRecNam(reqBody.getBcmCardAcctNaT());
		req20000.setCuIdTp(reqBody.getBcmCardAcctCrtfTpT());
		req20000.setCuIdNo(reqBody.getBcmCardAcctCrtfNoT());
		req20000.setAgIdTp(reqBody.getAgentCrtfT());
		req20000.setAgIdNo(reqBody.getCmsnHldrGlblIdT());
		req20000.setSeqNo(reqBody.getIcCardSeqNoT1());
		req20000.setaRQC(reqBody.getIcCard91T());
		req20000.setiCAID(reqBody.getIcCard9f09T());
		req20000.setiCOutDate(reqBody.getIcCardAvaiDtT());
		req20000.setiCData(reqBody.getIcCardF55T());
		req20000.setRemark(reqBody.getNoteT2());
        
		REP_20000 rep_20000 = forwardToBocmService.sendToBocm(req20000, 
				REP_20000.class);
		return rep_20000;
	}
	/** 
	* @Title: hostReversal 
	* @Description: 本行核心冲正
	* @param @param reqDto
	* @param @param hostSeqno
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30014000101    返回类型 
	* @throws 
	*/
	private ESB_REP_30014000101 hostReversal(REQ_30061800101 reqDto,String hostSeqno)
			throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "TXBRNO");
			txTel = jedis.get(COMMON_PREFIX + "TXTEL");
		}

		ESB_REQ_30014000101 esbReq_30014000101 = new ESB_REQ_30014000101(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30014000101.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		reqSysHead.setProgramId(reqDto.getReqSysHead().getProgramId());
		esbReq_30014000101.setReqSysHead(reqSysHead);

		ESB_REQ_30014000101.REQ_BODY reqBody_30014000101 = esbReq_30014000101.getReqBody();
		esbReq_30014000101.setReqSysHead(reqSysHead);	

		reqBody_30014000101.setReference(hostSeqno);
		reqBody_30014000101.setReversalReason("");
		reqBody_30014000101.setEventType("");

		ESB_REP_30014000101 esbRep_30014000101 = forwardToESBService.sendToESB(esbReq_30014000101, reqBody_30014000101,
				ESB_REP_30014000101.class);
		return esbRep_30014000101;
	}
	private ESB_REP_30063000103 queryFee(REQ_30061800301 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "TXBRNO");
			txTel = jedis.get(COMMON_PREFIX + "TXTEL");
		}

		ESB_REQ_30063000103 esbReq_30063000103 = new ESB_REQ_30063000103(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30063000103.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		reqSysHead.setProgramId(reqDto.getReqSysHead().getProgramId());
		esbReq_30063000103.setReqSysHead(reqSysHead);

		ESB_REQ_30063000103.REQ_BODY reqBody_30063000103 = esbReq_30063000103.getReqBody();
		esbReq_30063000103.setReqSysHead(reqSysHead);	
        //DEP-存款WTD-取款TRA-转账
		reqBody_30063000103.setChargeSenece("TRA");
		reqBody_30063000103.setOthBankCode(reqDto.getReqBody().getCardNoT3());
		reqBody_30063000103.setTranAmt(reqDto.getReqBody().getDpsAmtT());
		reqBody_30063000103.setTranCcy("CNY");

		ESB_REP_30063000103 esbRep_30063000103 = forwardToESBService.sendToESB(esbReq_30063000103, reqBody_30063000103,
				ESB_REP_30063000103.class);
		return esbRep_30063000103;
	}
}
