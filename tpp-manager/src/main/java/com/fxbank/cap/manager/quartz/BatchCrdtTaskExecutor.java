package com.fxbank.cap.manager.quartz;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fxbank.cap.esb.model.cnaps2.ESB_REP_30041000406;
import com.fxbank.cap.esb.model.cnaps2.ESB_REQ_30041000406;
import com.fxbank.cap.esb.model.ses.ESB_REP_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REP_30013000510;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30013000510;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.model.BatchCrdtDetailModel;
import com.fxbank.cap.paf.model.BatchCrdtMasterModel;
import com.fxbank.cap.paf.model.BatchCrdtUpdDetailModel;
import com.fxbank.cap.paf.service.IBatchCrdtService;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.pub.service.IPublicService;

import redis.clients.jedis.Jedis;

public class BatchCrdtTaskExecutor implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(BatchCrdtTaskExecutor.class);

	private MyLog myLog;
	private Integer num; // 当前线程标志
	private CountDownLatch latch;
	private List<String> dataIndex;
	private IForwardToESBService forwardToESBService;
	private BatchCrdtMasterModel masterModel;

	private IPublicService publicService;
	private IBatchCrdtService batchCrdtService;

	private MyJedis myJedis;

	private static final String BRTEL_PREFIX = "paf_branch.";

	public BatchCrdtTaskExecutor(MyLog myLog, Integer num, CountDownLatch latch, List<String> dataIndex,
			IForwardToESBService forwardToESBService, IBatchCrdtService batchCrdtService, IPublicService publicService,
			MyJedis myJedis, BatchCrdtMasterModel masterModel) {
		this.myLog = myLog;
		this.num = num;
		this.latch = latch;
		this.dataIndex = dataIndex;
		this.batchCrdtService = batchCrdtService;
		this.forwardToESBService = forwardToESBService;
		this.masterModel = masterModel;
		this.myJedis = myJedis;
		this.publicService = publicService;
	}

	@Override
	public void run() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (String str : dataIndex) {
				sb.append(str + ",");
				BatchCrdtUpdDetailModel updModel = new BatchCrdtUpdDetailModel();
				updModel.setBatchNo(masterModel.getBatchNo());
				updModel.setSeqNo(str);
				try {
					// 1、查询附表，获得记账信息
					BatchCrdtDetailModel selections = new BatchCrdtDetailModel();
					selections.setBatchNo(masterModel.getBatchNo());
					selections.setSeqNo(str);
					BatchCrdtDetailModel record = batchCrdtService.queryDetailByPk(selections);
					if ("0".equals(record.getTxStatus())) {
						BatchCrdtUpdDetailModel deModel = new BatchCrdtUpdDetailModel();
						deModel.setBatchNo(masterModel.getBatchNo());
						deModel.setSeqNo(str);
						deModel.setOldStatus("0");
						deModel.setTxStatus("1");
						batchCrdtService.updateDetail(deModel);
						record.setTxStatus("1");
					} else {
						myLog.info(logger, "批量交易单笔记账已处理，批量编号" + masterModel.getBatchNo() + "序号" + str);
						continue;
					}
					// 2、核心记账
					if ("1".equals(record.getTxStatus())) {
						String hostSeqNo = "", hostRspCode = "", hostRspMsg = "";
						if ("1".equals(masterModel.getFileType())) {
							updModel.setOldStatus("1");
							// 根据收款账户账号查询户名
							try {
								ESB_REP_30013000510 crAccount = queryCrAccount(record);
								// 收款户名
								String crAccountName = crAccount.getRepBody().getAcctName();
								// 验证收款户名与账号是否一致
								if (!crAccountName.equals(record.getCrAcctName())) {
									PafTradeExecuteException e = new PafTradeExecuteException(
											PafTradeExecuteException.PAF_E_10008);
									myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg() + "批量编号"
											+ masterModel.getBatchNo() + "序号" + str, e);
									updModel.setBatchNo(masterModel.getBatchNo());
									updModel.setSeqNo(str);
									updModel.setTxStatus("3");
									batchCrdtService.updateDetail(updModel);
									continue;
								}
							} catch (Exception e) {
								myLog.error(logger, "批量交易单笔记账验证收款账户失败，批量编号" + masterModel.getBatchNo() + "序号" + str);
								updModel.setBatchNo(masterModel.getBatchNo());
								updModel.setSeqNo(str);
								updModel.setTxStatus("3");
								batchCrdtService.updateDetail(updModel);
								continue;
							}
							ESB_REP_30011000101 esbRep_30011000101 = null;
							try {
								esbRep_30011000101 = innerCapCharge(record);
							} catch (Exception e) {
								myLog.error(logger, "批量交易单笔记账失败，批量编号" + masterModel.getBatchNo() + "序号" + str, e);
								updModel.setBatchNo(masterModel.getBatchNo());
								updModel.setSeqNo(str);
								updModel.setTxStatus("3");
								batchCrdtService.updateDetail(updModel);
								continue;
							}
							myLog.info(logger, "批量付款单笔同行付款成功，批量编号" + masterModel.getBatchNo() + "序号" + str);
							hostSeqNo = esbRep_30011000101.getRepBody().getReference();
							hostRspCode = esbRep_30011000101.getRepSysHead().getRet().get(0).getRetCode();
							hostRspMsg = esbRep_30011000101.getRepSysHead().getRet().get(0).getRetMsg();
						} else {
							ESB_REP_30041000406 esbRep_30041000406 = null;
							try {
								esbRep_30041000406 = outerCapCharge(record);
							} catch (Exception e) {
								myLog.error(logger, "批量交易单笔记账失败，批量编号" + masterModel.getBatchNo() + "序号" + str);
								updModel.setBatchNo(masterModel.getBatchNo());
								updModel.setSeqNo(str);
								updModel.setTxStatus("3");
								batchCrdtService.updateDetail(updModel);
								continue;
							}
							myLog.info(logger, "批量付款跨行付款成功，批量编号" + masterModel.getBatchNo() + "序号" + str);
							hostSeqNo = "ENS"+esbRep_30041000406.getRepBody().getHostDate()
							+esbRep_30041000406.getRepBody().getHostTraceNo();
							hostRspCode = esbRep_30041000406.getRepSysHead().getRet().get(0).getRetCode();
							hostRspMsg = esbRep_30041000406.getRepSysHead().getRet().get(0).getRetMsg();
						}
						updModel.setTxStatus("2");
						updModel.setHostSeqNo(hostSeqNo);
						updModel.setHostRspCode(hostRspCode);
						updModel.setHostRspMsg(hostRspMsg);
						batchCrdtService.updateDetail(updModel);
					}
				} catch (SysTradeExecuteException e) {
					updModel.setBatchNo(masterModel.getBatchNo());
					updModel.setSeqNo(str);
					updModel.setTxStatus("3");
					try {
						batchCrdtService.updateDetail(updModel);
						myLog.error(logger, "批量处理单笔付款异常，批量编号" + masterModel.getBatchNo() + "序号" + str, e);
					} catch (SysTradeExecuteException e1) {
						myLog.error(logger, "批量处理单笔付款异常保存失败，批量编号" + masterModel.getBatchNo() + "序号" + str, e1);
						throw new RuntimeException("批量处理单笔付款异常保存失败");
					}
				}

			}
			sb.append("]");
			myLog.info(logger, "线程[" + num + "]处理范围[" + sb.toString() + "]");

		} finally {
			myLog.info(logger, "线程[" + num + "]处理完成，释放锁");
			this.latch.countDown();
		}

	}

	private ESB_REP_30011000101 innerCapCharge(BatchCrdtDetailModel record) throws SysTradeExecuteException {

		String txUnitNo = masterModel.getSndUnitNo(); // 公积金节点编号
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		// 过渡账户
		String inacno = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(BRTEL_PREFIX + txUnitNo + "_TXBRNO");
			txTel = jedis.get(BRTEL_PREFIX + txUnitNo + "_TXTEL");
			inacno = jedis.get(BRTEL_PREFIX + txUnitNo + "_CRDT_INACNO");
		}

		ESB_REQ_30011000101 esbReq_30011000101 = new ESB_REQ_30011000101(myLog, masterModel.getSysDate(),
				masterModel.getSysTime(), masterModel.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000101.getReqSysHead(), getReqDto())
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30011000101.setReqSysHead(reqSysHead);

		ESB_REQ_30011000101.REQ_BODY reqBody_30011000101 = esbReq_30011000101.getReqBody();
		// 账号/卡号
		reqBody_30011000101.setBaseAcctNo(inacno);
		//账户名称
		reqBody_30011000101.setAcctName(masterModel.getDeAcctname());
		// 交易类型
		reqBody_30011000101.setTranType("GJ03");
		// 交易币种
		reqBody_30011000101.setTranCcy("CNY");
		// 交易金额
		reqBody_30011000101.setTranAmt(record.getCapAmt().toString());
		// 对方账号/卡号
		reqBody_30011000101.setOthBaseAcctNo(record.getCrAcctNo());
		// 对方户名
		reqBody_30011000101.setOthBaseAcctName(record.getCrAcctName());
		// 摘要
		reqBody_30011000101.setNarrative(record.getSummary());
		// 记账渠道类型
		reqBody_30011000101.setChannelType("GJ");
		// 清算日期
		reqBody_30011000101.setSettlementDate(String.valueOf(masterModel.getSysDate()));
		// 对账标识,Y-参与对账;N-不参与对账
		reqBody_30011000101.setCollateFlag("Y");
		ESB_REP_30011000101 esbRep_30011000101 = forwardToESBService.sendToESB(esbReq_30011000101, reqBody_30011000101,
				ESB_REP_30011000101.class);
		return esbRep_30011000101;
	}

	/**
	 * @Title: outerCapCharge @Description: 二代跨行支付记本金账务 @param @param
	 *         dto @param @return @param @throws SysTradeExecuteException
	 *         设定文件 @return ESB_REP_30011000101 返回类型 @throws
	 */
	private ESB_REP_30041000406 outerCapCharge(BatchCrdtDetailModel record) throws SysTradeExecuteException {
		// 公积金节点编号
		String txUnitNo = masterModel.getSndUnitNo();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		String inacno = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(BRTEL_PREFIX + txUnitNo + "_TXBRNO");
			txTel = jedis.get(BRTEL_PREFIX + txUnitNo + "_TXTEL");
			inacno = jedis.get(BRTEL_PREFIX + txUnitNo + "_CRDT_INACNO");
		}
		ESB_REQ_30041000406 esbReq_30041000406 = new ESB_REQ_30041000406(myLog, masterModel.getSysDate(),
				masterModel.getSysTime(), masterModel.getSysTraceno());

		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30041000406.getReqSysHead(), getReqDto())
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30041000406.setReqSysHead(reqSysHead);

		ESB_REQ_30041000406.REQ_BODY reqBody_30041000406 = esbReq_30041000406.getReqBody();
		// 到账方式1-实时2-普通延时3-次日延时
		reqBody_30041000406.setTranMethod("2");
		// 来源系统
		reqBody_30041000406.setSourceSys("CAP");
		// 付款人账号
		reqBody_30041000406.setPayerAcctNo(inacno);
		// 付款人名称
		reqBody_30041000406.setPayerName(masterModel.getDeAcctname());
		// 付款人地址
		reqBody_30041000406.setPayerAddr(masterModel.getDeAcctname());
		// 金额
		reqBody_30041000406.setAmt(record.getCapAmt().toString());
		// 收款人账号
		reqBody_30041000406.setPayeeAcctNo(record.getCrAcctNo());
		// 收款人名称
		reqBody_30041000406.setPayeeName(record.getCrAcctName());
		// 收款人地址
		reqBody_30041000406.setPayeeAddr(record.getCrAddr());
		// 根据结算模式判断系统类型
		String sysTpT = "BEPS";
		// 系统类型
		reqBody_30041000406.setSysTpT(sysTpT);
		// 接收行行号
		reqBody_30041000406.setRcvBankNo(record.getCrChgNo());
		// 附言
		reqBody_30041000406.setPostScript(record.getSummary());
		// 支取方式
		reqBody_30041000406.setWithDrawalType("W");
		// 付款账户类型0-内部账 1-对私 2-对公
		reqBody_30041000406.setActualPayerAcctTp("0");
		// 费用金额
		reqBody_30041000406.setFeeAmt("0");
		// 费用类型
		reqBody_30041000406.setFeeType("0");

		ESB_REP_30041000406 esbRep_30041000406 = forwardToESBService.sendToESB(esbReq_30041000406, reqBody_30041000406,
				ESB_REP_30041000406.class);
		return esbRep_30041000406;
	}

	/**
	 * @Title: queryCrAccount @Description: 验证收款账户账号与户名是否一致 @param @param
	 *         reqDto @param @return @param @throws SysTradeExecuteException
	 *         设定文件 @return ESB_REP_30013000510 返回类型 @throws
	 */
	private ESB_REP_30013000510 queryCrAccount(BatchCrdtDetailModel record) throws SysTradeExecuteException {
		// 公积金节点编号
		String txUnitNo = masterModel.getSndUnitNo();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(BRTEL_PREFIX + txUnitNo + "_TXBRNO");
			txTel = jedis.get(BRTEL_PREFIX + txUnitNo + "_TXTEL");
		}

		ESB_REQ_30013000510 esbReq_30013000510 = new ESB_REQ_30013000510(myLog, masterModel.getSysDate(),
				masterModel.getSysTime(), masterModel.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30013000510.getReqSysHead(), getReqDto())
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30013000510.setReqSysHead(reqSysHead);

		ESB_REQ_30013000510.REQ_BODY reqBody_30013000510 = esbReq_30013000510.getReqBody();
		// 收款账号
		reqBody_30013000510.setBaseAcctNo(record.getCrAcctNo());
		// 币种
		reqBody_30013000510.setCcy("CNY");
		// 钞汇鉴别1：钞 2：汇
		reqBody_30013000510.setCurrIden("1");
		// 是否对私客户
		reqBody_30013000510.setIsIndividual("N");

		ESB_REP_30013000510 esb_rep_30013000510 = forwardToESBService.sendToESB(esbReq_30013000510, reqBody_30013000510,
				ESB_REP_30013000510.class);

		return esb_rep_30013000510;
	}

	private DataTransObject getReqDto() {
		DataTransObject reqDto = new DataTransObject();
		Integer sysDate = publicService.getSysDate("CIP");
		Integer sysTime = publicService.getSysTime();
		Integer sysTraceno = publicService.getSysTraceno();
		reqDto.setSourceType("GJ");
		reqDto.setSysDate(sysDate);
		reqDto.setSysTime(sysTime);
		reqDto.setSysTraceno(sysTraceno);
		return reqDto;
	}
}
