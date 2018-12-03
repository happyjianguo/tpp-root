package com.fxbank.cap.manager.quartz;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.model.ses.ESB_REP_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REP_30013000510;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30013000510;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.manager.utils.DivideUtil;
import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.model.BatchCrdtMasterModel;
import com.fxbank.cap.paf.model.BatchCrdtUpdMasterModel;
import com.fxbank.cap.paf.service.IBatchCrdtService;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.pub.service.IPublicService;

import redis.clients.jedis.Jedis;

@Configuration
@Component
@EnableScheduling
public class BatchCrdtTask {
	private static Logger logger = LoggerFactory.getLogger(BatchCrdtTask.class);

	private static final String JOBNAME = "BatchCrdt";
	private static final Integer SLOT = 10; // 默认线程数量
	private CountDownLatch latch;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IBatchCrdtService batchCrdtService;

	@Reference(version = "1.0.0")
	private IPublicService publicService;

	@Resource
	private MyJedis myJedis;

	@Resource
	private LogPool logPool;

	private final static String COMMON_PREFIX = "paf_common.";
	private static final String BRTEL_PREFIX = "paf_branch.";

	public void exec() {
		MyLog myLog = new MyLog();
		myLog.info(logger, "开始进行批量付款处理");
		long startTime = System.currentTimeMillis();
		List<BatchCrdtMasterModel> list;
		try {
			list = batchCrdtService.queryMasterByTxStatus(myLog, "0,1,2,4");
		} catch (SysTradeExecuteException e2) {
			myLog.error(logger, "查询批量付款主表数据失败");
			throw new RuntimeException("查询批量付款主表数据失败");
		}

		// 取redis标志，如果未N，直接退出
		try (Jedis jedis = myJedis.connect()) {
			if ("N".equals(jedis.get(COMMON_PREFIX + "batch_process_flag"))) {
				myLog.info(logger, "批量付款处理结束");
				return;
			}
		}

		if (list == null || list.size() == 0) {
			myLog.info(logger, "没有待处理批量数据");
			return;
		}

		String txtPath = null; // 临时文件目录
		try (Jedis jedis = myJedis.connect()) {
			txtPath = jedis.get(COMMON_PREFIX + "txt_path");
		}

		for (BatchCrdtMasterModel master : list) {
			BatchCrdtUpdMasterModel record = new BatchCrdtUpdMasterModel();
			BatchCrdtMasterModel detailSum = null;
			record.setPlatDate(master.getSysDate());
			record.setPlatTrace(master.getSysTraceno());
			String batchNo = master.getBatchNo();
			try {
				master.setRemoteFilePath("/cip/");
				master.setLocalFilePath(txtPath);
				master.setFileType(master.getFileType());
				if ("0".equals(master.getTxStatus())) {
					record.setOldStatus("0");
					// 根据付款账户账号查询户名
					ESB_REP_30013000510 deAccount = null;
					try {
						deAccount = queryDeAccount(myLog, master);
						// 付款账户名称
						String deAccountName = deAccount.getRepBody().getAcctName();
						// 验证付款户名与账号是否一致
						if (!deAccountName.equals(master.getDeAcctname())) {
							PafTradeExecuteException e = new PafTradeExecuteException(
									PafTradeExecuteException.PAF_E_10007);
							myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg() + "批量编号" + batchNo);
							record.setTxStatus("9");
							record.setTxMsg("付款户名与账号不一致");
							batchCrdtService.updateMaster(record);
							return;
						}
					} catch (Exception e) {
						myLog.error(logger, "批量交易验证付款账户失败，批量编号" + batchNo);
						record.setTxStatus("9");
						record.setTxMsg("验证付款账户失败");
						batchCrdtService.updateMaster(record);
						return;
					}
					try {
						batchCrdtService.addBatchDetail(myLog, logger, master);
						myLog.info(logger, "批量付款明细登记成功，批量编号" + batchNo);
					} catch (Exception e) {
						record.setTxStatus("9");
						record.setTxMsg("登记失败");
						batchCrdtService.updateMaster(record);
						return;
					}

					detailSum = batchCrdtService.queryDetailsAmtSum(myLog, batchNo);
					if (detailSum.getTotalNum().compareTo(master.getTotalNum()) != 0) {
						PafTradeExecuteException e = new PafTradeExecuteException(PafTradeExecuteException.PAF_E_10018);
						myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg() + "批量编号" + batchNo);
						record.setTxStatus("9");
						record.setTxMsg(e.getRspMsg());
						batchCrdtService.updateMaster(record);
						return;
					}

					if (detailSum.getTotalAmt().compareTo(master.getTotalAmt()) != 0) {
						PafTradeExecuteException e = new PafTradeExecuteException(PafTradeExecuteException.PAF_E_10019);
						myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg() + "批量编号" + batchNo);
						record.setTxStatus("9");
						record.setTxMsg(e.getRspMsg());
						batchCrdtService.updateMaster(record);
						return;
					}

					// 0、修改批量主表状态
					record.setTxStatus("1");
					record.setTxMsg("批量付款登记成功");
					batchCrdtService.updateMaster(record);
					master.setTxStatus("1");
				}

				if ("1".equals(master.getTxStatus())) {
					record.setOldStatus("1");
					ESB_REP_30011000101 esbRep_30011000101 = null;
					try {
						esbRep_30011000101 = innerCapCharge(master, "1", myLog);
						myLog.info(logger, "核心转出总金额成功,批量编号" + batchNo);
					} catch (Exception e) {
						record.setTxStatus("9");
						record.setTxMsg("核心转出总金额失败");
						batchCrdtService.updateMaster(record);
						myLog.error(logger, "核心转出总金额失败", e);
						return;
					}
					record.setSuccHostSeqNo(esbRep_30011000101.getRepBody().getReference());
					record.setSuccHostRspCode(esbRep_30011000101.getRepSysHead().getRet().get(0).getRetCode());
					record.setSuccHostRspMsg(esbRep_30011000101.getRepSysHead().getRet().get(0).getRetMsg());
					record.setTxStatus("2");
					record.setTxMsg("核心转出总金额成功");
					batchCrdtService.updateMaster(record);
					master.setTxStatus("2");
				}

				if ("2".equals(master.getTxStatus())) {
					record.setOldStatus("2");
					// 1、获得当前批量数据批次序号索引List
					List<String> arr = batchCrdtService.querySeqNosByBatchNo(batchNo);
					int sum = arr.size();
					myLog.info(logger, "当前批量待处理总数量[" + sum + "]");
					if (sum == 0) {
						myLog.info(logger, "批量付款处理退出");
						return;
					}

					// 从redis取SLOT，如果未配置，默认取10
					Integer slot = null;
					try (Jedis jedis = myJedis.connect()) {
						if (null != jedis.get(COMMON_PREFIX + "batch_slot")) {
							slot = Integer.parseInt(jedis.get(COMMON_PREFIX + "batch_slot"));
						}
					}
					ExecutorService executor = Executors.newFixedThreadPool(null == slot ? SLOT : slot);

					// 2、触发批量进程
					DivideUtil divideUtil = new DivideUtil(myLog, sum, SLOT);
					List<DivideUtil.Element> elements = divideUtil.divide();
					latch = new CountDownLatch(elements.size());
					myLog.info(logger, "当前批量触发线程数量[" + elements.size() + "]");
					int n = 1;

					try {
						record.setTxStatus("3");
						record.setTxMsg("批量付款处理中");
						batchCrdtService.updateMaster(record);
						myLog.info(logger, "当前批量等待线程处理完成");

						for (DivideUtil.Element e : elements) {
							executor.execute(
									new BatchCrdtTaskExecutor(myLog, n, latch, arr.subList(e.getBeg(), e.getEnd()),
											forwardToESBService, batchCrdtService, publicService, myJedis, master));
							myLog.info(logger, "当前批量触发第[" + n + "]个线程");
							n++;
						}
						// 3、等待批量进程处理完成
						latch.await();
						record.setOldStatus("3");
						record.setTxStatus("4");
						record.setTxMsg("处理完成");
						batchCrdtService.updateMaster(record);
						master.setTxStatus("4");
					}catch (Exception e) {
						record.setTxStatus("3");
						record.setTxMsg("触发批量异常");
						batchCrdtService.updateMaster(record);
						myLog.error(logger, "触发批量异常：" , e);
						return;
					} finally {
						executor.shutdown();
					}
				}

				if ("4".equals(master.getTxStatus())) {
					record.setOldStatus("4");
					// 4、检查批量进程处理结果，核对附表所有记录是否都处理完成
					detailSum = batchCrdtService.queryDetailsAmtSum(myLog, batchNo);
					BigDecimal succAmt = new BigDecimal(0), failAmt = new BigDecimal(0);
					if (null != detailSum.getSuccAmt()) {
						succAmt = detailSum.getSuccAmt();
					}
					if (null != detailSum.getFailAmt()) {
						failAmt = detailSum.getFailAmt();
					}
					record.setSuccAmt(succAmt);
					record.setSuccNum(detailSum.getSuccNum());
					record.setFailAmt(failAmt);
					record.setFailNum(detailSum.getFailNum());
					batchCrdtService.updateMaster(record);
					if (succAmt.compareTo(master.getCapAmt()) != 0
							|| detailSum.getSuccNum().compareTo(master.getTotalNum()) != 0) {
						master.setFailAmt(detailSum.getFailAmt());
						master.setFailNum(detailSum.getFailNum());
						ESB_REP_30011000101 esbRep_30011000101 = null;
						try {
							esbRep_30011000101 = innerCapCharge(master, "2", myLog);
						} catch (Exception e) {
							record.setTxStatus("3");
							record.setTxMsg("核心回退失败金额异常");
							batchCrdtService.updateMaster(record);
							myLog.error(logger, "核心回退失败金额异常，批量编号：" + batchNo, e);
							return;
						}
						myLog.info(logger, "核心回退失败金额成功,批量编号" + batchNo);
						record.setRollHostSeqNo(esbRep_30011000101.getRepBody().getReference());
						record.setRollHostRspCode(esbRep_30011000101.getRepSysHead().getRet().get(0).getRetCode());
						record.setRollHostRspMsg(esbRep_30011000101.getRepSysHead().getRet().get(0).getRetMsg());
						batchCrdtService.updateMaster(record);
					}
					record.setTxStatus("5");
					record.setTxMsg("批量付款处理完成，成功"+detailSum.getSuccNum()+"笔，失败"+detailSum.getFailNum()+"笔");
					myLog.info(logger, "批量付款处理完成，成功"+detailSum.getSuccNum()+"笔，失败"+detailSum.getFailNum()+"笔");
					batchCrdtService.updateMaster(record);
				}
				long endTime = System.currentTimeMillis();
				float excTime = (float) (endTime - startTime) / 1000;
				myLog.info(logger, "批量付款处理完成，批量编号：" + batchNo + "执行时间" + excTime + "s");
			} catch (Exception e) {
				record.setTxStatus("3");
				record.setTxMsg("处理异常");
				myLog.error(logger, "批量付款处理异常，批量编号：" + batchNo, e);
				try {
					batchCrdtService.updateMaster(record);
				} catch (SysTradeExecuteException e1) {
					myLog.error(logger, "保存批量付款主表处理异常失败，批量编号：" + batchNo, e);
					throw new RuntimeException("保存批量付款主表处理异常失败，批量编号：" + batchNo);
				}
			}
		}

	}

	@Bean(name = "batchCrdtJobDetail")
	public MethodInvokingJobDetailFactoryBean accMstTracePushJobDetail(BatchCrdtTask task) {// ScheduleTask为需要执行的任务
		MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
		jobDetail.setConcurrent(false);
		jobDetail.setName(JOBNAME);
		jobDetail.setGroup(QuartzJobConfigration.JOBGROUP);
		jobDetail.setTargetObject(task);
		jobDetail.setTargetMethod(QuartzJobConfigration.METHODNAME);
		return jobDetail;
	}

	@Bean(name = "batchCrdtJobTrigger")
	public CronTriggerFactoryBean accMstTracePushJobTrigger(
			@Qualifier("batchCrdtJobDetail") MethodInvokingJobDetailFactoryBean jobDetail) {
		CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
		tigger.setJobDetail(jobDetail.getObject());
		String exp = null;
		try (Jedis jedis = myJedis.connect()) {
			exp = jedis.get(QuartzJobConfigration.PAF_CRON + JOBNAME);
		}
		if (exp == null) {
			exp = "0 0/1 * * * ?";
		}
		logger.info("任务[" + JOBNAME + "]启动[" + exp + "]");
		tigger.setCronExpression(exp);
		tigger.setName(JOBNAME);
		return tigger;
	}

	@Bean(name = "batchCrdtScheduler")
	public SchedulerFactoryBean schedulerFactory(@Qualifier("batchCrdtJobTrigger") Trigger cronJobTrigger) {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setOverwriteExistingJobs(true);
		bean.setStartupDelay(5);
		bean.setTriggers(cronJobTrigger);
		return bean;
	}

	/**
	 * @Title: queryDeAccount @Description: 验证付款账户账号和户名是否一致 @param @param
	 *         reqDto @param @return @param @throws SysTradeExecuteException
	 *         设定文件 @return ESB_REP_30013000510 返回类型 @throws
	 */
	private ESB_REP_30013000510 queryDeAccount(MyLog myLog, BatchCrdtMasterModel master)
			throws SysTradeExecuteException {
		// 公积金节点编号
		String txUnitNo = master.getSndUnitNo();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(BRTEL_PREFIX + txUnitNo + "_TXBRNO");
			txTel = jedis.get(BRTEL_PREFIX + txUnitNo + "_TXTEL");
		}

		ESB_REQ_30013000510 esbReq_30013000510 = new ESB_REQ_30013000510(myLog, master.getSysDate(),
				master.getSysTime(), master.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30013000510.getReqSysHead(), getReqDto())
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30013000510.setReqSysHead(reqSysHead);

		ESB_REQ_30013000510.REQ_BODY reqBody_30013000510 = esbReq_30013000510.getReqBody();
		// 付款账号
		reqBody_30013000510.setBaseAcctNo(master.getDeAcctno());
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

	private ESB_REP_30011000101 innerCapCharge(BatchCrdtMasterModel master, String type, MyLog myLog)
			throws SysTradeExecuteException {
		String inacno = null;
		String txUnitNo = master.getSndUnitNo(); // 公积金节点编号
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			inacno = jedis.get(BRTEL_PREFIX + txUnitNo + "_CRDT_INACNO");
			txBrno = jedis.get(BRTEL_PREFIX + txUnitNo + "_TXBRNO");
			txTel = jedis.get(BRTEL_PREFIX + txUnitNo + "_TXTEL");
		}

		ESB_REQ_30011000101 esbReq_30011000101 = new ESB_REQ_30011000101(myLog, master.getSysDate(),
				master.getSysTime(), master.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000101.getReqSysHead(), getReqDto())
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30011000101.setReqSysHead(reqSysHead);

		ESB_REQ_30011000101.REQ_BODY reqBody_30011000101 = esbReq_30011000101.getReqBody();
		// 交易类型
		reqBody_30011000101.setTranType("GJ03");
		// 交易币种
		reqBody_30011000101.setTranCcy("CNY");
		// type交易类型，1付款账户全部交易金额转给过渡账户、2过渡账户把失败的交易金额退回付款账户
		if ("1".equals(type)) {
			// 账号/卡号
			reqBody_30011000101.setBaseAcctNo(master.getDeAcctno());
			// 账户名称
			reqBody_30011000101.setAcctName(master.getDeAcctname());
			// 交易金额
			reqBody_30011000101.setTranAmt(master.getCapAmt().toString());
			// 对方账号/卡号
			reqBody_30011000101.setOthBaseAcctNo(inacno);
			// 摘要
			reqBody_30011000101.setNarrative("付款账户全部付款给过渡账户");
		} else if ("2".equals(type)) {
			// 账号/卡号
			reqBody_30011000101.setBaseAcctNo(inacno);
			// 交易金额
			reqBody_30011000101.setTranAmt(master.getFailAmt().toString());
			// 对方账号/卡号
			reqBody_30011000101.setOthBaseAcctNo(master.getDeAcctno());
			// 对方户名
			reqBody_30011000101.setOthBaseAcctName(master.getDeAcctname());
			// 摘要
			reqBody_30011000101.setNarrative("过渡账户将失败交易金额退回付款账户");
		}
		// 记账渠道类型
		reqBody_30011000101.setChannelType("GJ");
		// 清算日期
		reqBody_30011000101.setSettlementDate(String.valueOf(master.getSysDate()));
		// 对账标识,Y-参与对账;N-不参与对账
		reqBody_30011000101.setCollateFlag("Y");
		ESB_REP_30011000101 esbRep_30011000101 = forwardToESBService.sendToESB(esbReq_30011000101, reqBody_30011000101,
				ESB_REP_30011000101.class);
		return esbRep_30011000101;
	}
}
