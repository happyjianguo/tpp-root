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
import com.fxbank.tpp.bocm.dto.esb.REP_30061800201;
import com.fxbank.tpp.bocm.dto.esb.REQ_30061800201;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmSndTraceInitModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceUpdModel;
import com.fxbank.tpp.bocm.model.REP_10001;
import com.fxbank.tpp.bocm.model.REP_10009;
import com.fxbank.tpp.bocm.model.REP_20001;
import com.fxbank.tpp.bocm.model.REQ_10001;
import com.fxbank.tpp.bocm.model.REQ_10009;
import com.fxbank.tpp.bocm.model.REQ_20001;
import com.fxbank.tpp.bocm.service.IBocmSndTraceService;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30011000104;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30011000104;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import redis.clients.jedis.Jedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/** 
* @ClassName: WD_BocmTrsr 
* @Description: 交行卡付款转账
* @author Duzhenduo
* @date 2019年4月17日 上午10:38:50 
*  
*/
@Service("REQ_30061800201")
public class WD_BocmTra extends TradeBase implements TradeExecutionStrategy {
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
		REQ_30061800201 reqDto = (REQ_30061800201) dto;
		REQ_30061800201.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30061800201 rep = new REP_30061800201();
		//1. 插入流水表
		initRecord(reqDto);
		myLog.info(logger, "交行卡付款转账，登记成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
		//2. 交行记账   
		//交行记账流水号
		String bocmTraceNo = null;
		//原发起方交易流水
		String oLogNo = null;
		//原交易代码
		String oTxnCd = null;
		// IC_CARD_FLG_T4判断IC卡磁条卡标志   IC卡和磁条卡走不同的交行接口
		if ("0".equals(reqBody.getIcCardFlgT4())) {
			myLog.info(logger, "发送磁条卡转账通兑请求至交行");
			REQ_10001 req10001 = null;
			REP_10001 rep10001 = null;
			try {
				req10001 = new REQ_10001(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
				super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req10001.getHeader()); // 设置报文头中的行号信息
				oLogNo = req10001.getHeader().getsLogNo();
				oTxnCd = "10001";
				rep10001 = magCardCharge(req10001,reqDto);
				bocmTraceNo = rep10001.getHeader().getrLogNo();
				//3.更新流水表交行记账状态
				updateBocmRecord(reqDto,bocmTraceNo,"1");
				myLog.info(logger, "交行卡付款转账，交行磁条卡通兑记账成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
			    rep.getRepBody().setBalance3T(rep10001.getActBal().toString());
			    rep.getRepBody().setFeeT3(rep10001.getFee().toString());
			} catch (SysTradeExecuteException e) { // 记账交易参考一下方式处理，查询交易不用
				// 如果不是账务类请求，可以不用分类处理应答码，统一当成失败处理即可
				// 如果交易不关心返回的异常类型，直接可以不捕获，直接省略catch，抛出异常即可
				if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000006) // 生成请求失败
						|| e.getRspCode().equals(SysTradeExecuteException.CIP_E_000007)
						|| e.getRspCode().equals(SysTradeExecuteException.CIP_E_000008)) {
					//交行核心记账报错，本行核心冲正
					updateBocmRecord(reqDto, "", "2");
					myLog.error(logger, "交行卡付款转账，交行磁条卡通兑记账失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002);
					throw e2;
				}else if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000009)) { // 接收交行返回结果超时
					updateBocmRecord(reqDto, "", "3");
					myLog.error(logger, "交行卡付款转账，交行磁条卡通兑记账返回结果超时，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					try {
						bocmReversal(reqDto,oLogNo,oTxnCd);
					    updateBocmRecord(reqDto, "", "5");
					    myLog.info(logger, "交行卡付款转账，交行磁条卡通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno());
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(交行抹账成功)");
						throw e2;
					}catch(SysTradeExecuteException e1) {
						myLog.error(logger, "交行卡付款转账，交行磁条卡通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e1);
						updateBocmRecord(reqDto, "", "6");
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(交行抹账失败)");
						throw e2;
					}
				}else if (e.getRspCode().equals("JH6203")) { // 交行返回结果成功，但结果是超时
				       // 确认是否有冲正操作
					updateBocmRecord(reqDto, "", "3");
					myLog.error(logger, "交行卡付款转账，交行磁条卡通兑记账返回结果成功，但结果超时，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					try {
						bocmReversal(reqDto,oLogNo,oTxnCd);
					    updateBocmRecord(reqDto, "", "5");
					    myLog.info(logger, "交行卡付款转账，交行磁条卡通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno());
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(交行抹账成功)");
						throw e2;
					}catch(SysTradeExecuteException e1) {
						myLog.error(logger, "交行卡付款转账，交行磁条卡通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e1);
						updateBocmRecord(reqDto, "", "6");
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(交行抹账失败)");
						throw e2;
					}
				} else { // 目标系统应答失败
							// 确认是否有冲正操作
					updateBocmRecord(reqDto, "", "3");
					myLog.error(logger, "交行卡付款转账，交行磁条卡通兑记账应答失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					try {
						bocmReversal(reqDto,oLogNo,oTxnCd);
					    updateBocmRecord(reqDto, "", "5");
					    myLog.info(logger, "交行卡付款转账，交行磁条卡通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno());
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(交行抹账成功)");
						throw e2;
					}catch(SysTradeExecuteException e1) {
						myLog.error(logger, "交行卡付款转账，交行磁条卡通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e1);
						updateBocmRecord(reqDto, "", "6");
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(交行抹账失败)");
						throw e2;
					}
				}
			} catch (Exception e) { // 其它未知错误，可以当成超时处理
				// 确认是否有冲正操作
				updateBocmRecord(reqDto, "", "3");
				myLog.error(logger, "交行卡付款转账，交行磁条卡通兑记账其它未知错误，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				try {
					bocmReversal(reqDto,oLogNo,oTxnCd);
				    updateBocmRecord(reqDto, "", "5");
				    myLog.info(logger, "交行卡付款转账，交行磁条卡通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno());
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"其它未知错误(交行抹账成功)");
					throw e2;
				}catch(SysTradeExecuteException e1) {
					myLog.error(logger, "交行卡付款转账，交行磁条卡通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e1);
					updateBocmRecord(reqDto, "", "6");
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"其它未知错误(交行抹账失败)");
					throw e2;
				}
			}
		} else {
			myLog.info(logger, "发送IC卡转账通兑请求至交行");
			REQ_20001 req20001 = null;
			try {
				req20001 = new REQ_20001(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
				super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req20001.getHeader()); // 设置报文头中的行号信息
                oLogNo = req20001.getHeader().getsLogNo();
                oTxnCd = "20001";
				REP_20001 rep20001 = iCCardCharge(req20001,reqDto);
				bocmTraceNo = rep20001.getHeader().getrLogNo();
				//3.更新流水表交行记账状态
				updateBocmRecord(reqDto,bocmTraceNo,"1");
				myLog.info(logger, "交行卡付款转账，交行IC卡通兑记账成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
			    rep.getRepBody().setBalance3T(rep20001.getActBal().toString());
			    rep.getRepBody().setFeeT3(rep20001.getFee().toString());
			} catch (SysTradeExecuteException e) { // 记账交易参考一下方式处理，查询交易不用
				// 如果不是账务类请求，可以不用分类处理应答码，统一当成失败处理即可
				// 如果交易不关心返回的异常类型，直接可以不捕获，直接省略catch，抛出异常即可
				if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000006) // 生成请求失败
						|| e.getRspCode().equals(SysTradeExecuteException.CIP_E_000007)
						|| e.getRspCode().equals(SysTradeExecuteException.CIP_E_000008)) {
					//交行核心记账报错，本行核心冲正
					updateBocmRecord(reqDto, "", "2");
					myLog.error(logger, "交行卡付款转账，交行磁条卡通兑记账失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002);
					throw e2;
				} else if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000009)) { // 接收交行返回结果超时
					// 确认是否有冲正操作
					updateBocmRecord(reqDto, "", "3");
					myLog.error(logger, "交行卡付款转账，交行IC卡通兑记账返回结果超时，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					try {
						bocmReversal(reqDto,req20001.getHeader().getsLogNo(),"20001");
					    updateBocmRecord(reqDto, "", "5");
					    myLog.info(logger, "交行卡付款转账，交行磁条卡通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno());
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(交行抹账成功)");
						throw e2;
					}catch(SysTradeExecuteException e1) {
						myLog.error(logger, "交行卡付款转账，交行磁条卡通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e1);
						updateBocmRecord(reqDto, "", "6");
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(交行抹账失败)");
						throw e2;
					}
				} else if (e.getRspCode().equals("JH6203")) { // 交行返回结果成功，但结果是超时
					// 确认是否有冲正操作
					updateBocmRecord(reqDto, "", "3");
					myLog.error(logger, "交行卡付款转账，交行IC卡通兑记账返回结果成功，但结果超时，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					try {
						bocmReversal(reqDto,req20001.getHeader().getsLogNo(),"20001");
					    updateBocmRecord(reqDto, "", "5");
					    myLog.info(logger, "交行卡付款转账，交行磁条卡通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno());
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(交行抹账成功)");
						throw e2;
					}catch(SysTradeExecuteException e1) {
						myLog.error(logger, "交行卡付款转账，交行磁条卡通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e1);
						updateBocmRecord(reqDto, "", "6");
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(交行抹账失败)");
						throw e2;
					}
				} else { // 目标系统应答失败
							// 确认是否有冲正操作
					updateBocmRecord(reqDto, "", "3");
					myLog.error(logger, "交行卡付款转账，交行IC卡通兑记账应答失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e);
					try {
						bocmReversal(reqDto,req20001.getHeader().getsLogNo(),"20001");
					    updateBocmRecord(reqDto, "", "5");
					    myLog.info(logger, "交行卡付款转账，交行磁条卡通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno());
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(交行抹账成功)");
						throw e2;
					}catch(SysTradeExecuteException e1) {
						myLog.error(logger, "交行卡付款转账，交行磁条卡通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e1);
						updateBocmRecord(reqDto, "", "6");
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(交行抹账失败)");
						throw e2;
					}
				}
			} catch (Exception e) { // 其它未知错误，可以当成超时处理
				updateBocmRecord(reqDto, "", "3");
				myLog.error(logger, "交行卡付款转账，交行IC卡通兑记账其它未知错误，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				try {
					bocmReversal(reqDto,req20001.getHeader().getsLogNo(),"20001");
				    updateBocmRecord(reqDto, "", "5");
				    myLog.info(logger, "交行卡付款转账，交行磁条卡通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno());
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"其它未知错误(交行抹账成功)");
					throw e2;
				}catch(SysTradeExecuteException e1) {
					myLog.error(logger, "交行卡付款转账，交行磁条卡通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e1);
					updateBocmRecord(reqDto, "", "6");
					BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"其它未知错误(交行抹账失败)");
					throw e2;
				}
			}
		}		
		//4. 核心记账
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
			//核心记账
			esbRep_30011000104 = hostCharge(reqDto);
			hostDate = esbRep_30011000104.getRepSysHead().getRunDate();
			hostTraceno = esbRep_30011000104.getRepBody().getReference();
			retCode = esbRep_30011000104.getRepSysHead().getRet().get(0).getRetCode();
			retMsg = esbRep_30011000104.getRepSysHead().getRet().get(0).getRetMsg();
		} catch (SysTradeExecuteException e) {
			updateHostRecord(reqDto, "", "", "2", e.getRspCode(), e.getRspMsg());
			myLog.error(logger, "交行卡付款转账，本行核心记账失败，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
			try {
				bocmReversal(reqDto,oLogNo,oTxnCd);
			    updateBocmRecord(reqDto, "", "5");
			    myLog.info(logger, "交行卡付款转账，交行磁条卡通兑记账抹账成功，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno());
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(交行抹账成功)");
				throw e2;
			}catch(SysTradeExecuteException e1) {
				myLog.error(logger, "交行卡付款转账，交行磁条卡通兑记账抹账失败，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e1);
				updateBocmRecord(reqDto, "", "6");
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,e.getRspMsg()+"(交行抹账失败)");
				throw e2;
			}
			
		}
		myLog.info(logger, "交行卡付款转账，本行核心记账成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
		//5. 核心记账成功，更新流水表核心记账状态
		updateHostRecord(reqDto, hostDate, hostTraceno, "1", retCode, retMsg);

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
	private void initRecord(REQ_30061800201 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		REQ_30061800201.REQ_BODY reqBody = reqDto.getReqBody();
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
		record.setPayerAcno(reqBody.getPyrAcctNoT2());
		record.setPayerName(reqBody.getPyrNaT());
		record.setPayeeAcno(reqBody.getRcptPrAcctNoT2());
		record.setPayeeName(reqBody.getRcptPrNmT7());
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
	private ESB_REP_30011000104 hostCharge(REQ_30061800201 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		REQ_30061800201.REQ_BODY reqBody = reqDto.getReqBody();
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
		reqBody_30011000104.setBaseAcctNo(reqBody.getRcptPrAcctNoT2());
		reqBody_30011000104.setAcctName(reqBody.getRcptPrNmT7());
		reqBody_30011000104.setTranType("JH10");
		reqBody_30011000104.setTranCcy("CNY");
		reqBody_30011000104.setTranAmt(reqBody.getTrsrAmtT3());
		reqBody_30011000104.setWithdrawalType("P");
		reqBody_30011000104.setPassword(reqBody.getPwdT());
		reqBody_30011000104.setOthBaseAcctNo(reqBody.getPyrAcctNoT2());
		reqBody_30011000104.setOthBaseAcctName(reqBody.getPyrNaT());
		reqBody_30011000104.setChannelType("");
		reqBody_30011000104.setSendBankCode(reqBody.getPyOpnBrNoT());
		reqBody_30011000104.setBankCode(reqBody.getPyrOpnBnkNoT2());
		reqBody_30011000104.setOthBankCode(reqBody.getPyrOpnBnkNoT2());
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
	private BocmSndTraceUpdModel updateHostRecord(REQ_30061800201 reqDto, String hostDate, String hostTraceno,
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
	private BocmSndTraceUpdModel updateBocmRecord(REQ_30061800201 reqDto,
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
	* @Description: 交行磁条卡通兑记账
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return REP_10001    返回类型 
	* @throws 
	*/
	private REP_10001 magCardCharge(REQ_10001 req10001,REQ_30061800201 reqDto) throws SysTradeExecuteException {
		REQ_30061800201.REQ_BODY reqBody = reqDto.getReqBody();
		req10001.setTxnAmt(new BigDecimal(reqBody.getTrsrAmtT3()));
		req10001.setOprFlg(reqBody.getCardInWyT());
		//业务模式，0 现金1 转账（即实时转账）9 其他
		req10001.setTxnMod("1");
		req10001.setPayBnk(reqBody.getPyrOpnBnkNoT2());
		//付款人账户类型,0 银行账号1 贷记卡2 借记卡3其他
		req10001.setpActTp(reqBody.getPyrAcctTpT());
		req10001.setpActNo(reqBody.getPyrAcctNoT2());
		req10001.setPayNam(reqBody.getPyrNaT());
		req10001.setRecBnk(reqBody.getPyeeOpnBnkNoT6());
		req10001.setrActTp(reqBody.getPyAcctTpT());
		req10001.setrActNo(reqBody.getRcptPrAcctNoT2());
		req10001.setRecNam(reqBody.getRcptPrNmT7());
		req10001.setCuIdTp(reqBody.getIdTpT2());
		req10001.setCuIdNo(reqBody.getHldrGlblIdT());
		req10001.setAgIdTp(reqBody.getAgentCrtfT());
		req10001.setAgIdNo(reqBody.getCmsnHldrGlblIdT());
		req10001.setSecMag(reqBody.getScdTrkT());
		req10001.setThdMag(reqBody.getThrTrkInfoT1());
		req10001.setRemark(reqBody.getNoteT2());
        
		REP_10001 rep_10001 = forwardToBocmService.sendToBocm(req10001, 
				REP_10001.class);
		return rep_10001;
	}
	/** 
	* @Title: iCCardCharge 
	* @Description: 交行IC卡通兑记账 
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return REP_20001    返回类型 
	* @throws 
	*/
	private REP_20001 iCCardCharge(REQ_20001 req20001,REQ_30061800201 reqDto) throws SysTradeExecuteException {
		REQ_30061800201.REQ_BODY reqBody = reqDto.getReqBody();
		req20001.setTxnAmt(new BigDecimal(reqBody.getTrsrAmtT3()));
		req20001.setPin(reqBody.getPwdT());
		req20001.setOprFlg(reqBody.getCardInWyT());
		//业务模式，0 现金1 转账（即实时转账）2 普通转账（2小时后）3 隔日转账9 其他
		req20001.setTxnMod("1");
		req20001.setPayBnk(reqBody.getPyrOpnBnkNoT2());
		//付款人账户类型,0 银行账号1 贷记卡2 借记卡3其他
		req20001.setpActTp(reqBody.getPyrAcctTpT());
		req20001.setpActNo(reqBody.getPyrAcctNoT2());
		req20001.setPayNam(reqBody.getPyrNaT());
		req20001.setRecBnk(reqBody.getPyeeOpnBnkNoT6());
		req20001.setrActTp(reqBody.getPyAcctTpT());
		req20001.setrActNo(reqBody.getRcptPrAcctNoT2());
		req20001.setRecNam(reqBody.getRcptPrNmT7());
		req20001.setCuIdTp(reqBody.getIdTpT2());
		req20001.setCuIdNo(reqBody.getHldrGlblIdT());
		req20001.setAgIdTp(reqBody.getAgentCrtfT());
		req20001.setAgIdNo(reqBody.getCmsnHldrGlblIdT());
		req20001.setSeqNo(reqBody.getIcCardSeqNoT1());
		req20001.setaRQC(reqBody.getIcCard91T());
		req20001.setiCAID(reqBody.getIcCard9f09T());
		req20001.setiCOutDate(reqBody.getIcCardAvaiDtT());
		req20001.setiCData(reqBody.getIcCardF55T());
		req20001.setRemark(reqBody.getNoteT2());
        
		REP_20001 rep_20001 = forwardToBocmService.sendToBocm(req20001, 
				REP_20001.class);
		return rep_20001;
	}

	/** 
	* @Title: bocmReversal 
	* @Description: 交行个人储蓄抹帐业务
	* @param @param reqDto
	* @param @param oLogNo
	* @param @param oTxnCd
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return REP_10009    返回类型 
	* @throws 
	*/
	private REP_10009 bocmReversal(REQ_30061800201 reqDto,String oLogNo,String oTxnCd)
			throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061800201.REQ_BODY reqBody = reqDto.getReqBody();
		REQ_10009 req10009 = new REQ_10009(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
		super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req10009.getHeader()); // 设置报文头中的行号信息
		req10009.setOlogNo(oLogNo);
		req10009.setOtxnCd(oTxnCd);
		req10009.setTxnAmt(new BigDecimal(reqBody.getTrsrAmtT3()));
		//业务模式，0 现金1 转账（即实时转账）2 普通转账（2小时后）3 隔日转账9 其他
		req10009.setTxnMod("1");
		req10009.setPayBnk(reqBody.getPyrOpnBnkNoT2());
		//付款人账户类型,0 银行账号1 贷记卡2 借记卡3其他
		req10009.setpActTp(reqBody.getPyrAcctTpT());
		req10009.setpActNo(reqBody.getPyrAcctNoT2());
		req10009.setPayNam(reqBody.getPyrNaT());
		req10009.setRecBnk(reqBody.getPyeeOpnBnkNoT6());
		req10009.setrActTp(reqBody.getPyAcctTpT());
		req10009.setrActNo(reqBody.getRcptPrAcctNoT2());
		req10009.setRecNam(reqBody.getRcptPrNmT7());
		req10009.setCuIdTp(reqBody.getIdTpT2());
		req10009.setCuIdNo(reqBody.getHldrGlblIdT());
		req10009.setAgIdTp(reqBody.getAgentCrtfT());
		req10009.setAgIdNo(reqBody.getCmsnHldrGlblIdT());
		req10009.setRemark(reqBody.getNoteT2());
        
		REP_10009 rep_10009 = forwardToBocmService.sendToBocm(req10009, 
				REP_10009.class);
		return rep_10009;
	}
}

