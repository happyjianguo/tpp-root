package com.fxbank.cap.manager.quartz;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import com.fxbank.cap.manager.entity.PafAccMstReport;
import com.fxbank.cap.manager.service.PafAccMstService;
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
import com.dcfs.esb.ftp.client.FtpClientConfigSet;
import com.dcfs.esb.ftp.client.FtpGet;
import com.dcfs.esb.ftp.server.error.FtpException;
import com.fxbank.cap.esb.model.ses.ESB_REP_30015700901;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30015700901;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.manager.model.PafAcNoInfo;
import com.fxbank.cap.manager.model.PafAcNoList;
import com.fxbank.cap.manager.model.PafCenterInfo;
import com.fxbank.cap.manager.model.PafCenterList;
import com.fxbank.cap.manager.utils.FileUtil;
import com.fxbank.cap.paf.constant.PAF;
import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.model.CLI_REP_DATA;
import com.fxbank.cap.paf.model.CLI_REQ_BDC;
import com.fxbank.cap.paf.model.FIELD;
import com.fxbank.cap.paf.model.FIELDS_LIST_INNER;
import com.fxbank.cap.paf.model.FIELDS_LIST_OUTER;
import com.fxbank.cap.paf.service.IAccountService;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.cip.pub.service.IPublicService;
import com.tienon.util.FileFieldConv;

import redis.clients.jedis.Jedis;

@Configuration
@Component
@EnableScheduling
public class AccMstTracePushTask {
	private static Logger logger = LoggerFactory.getLogger(AccMstTracePushTask.class);

	private static final String JOBNAME = "AccMstTracePush";

	@Reference(version = "1.0.0")
	private IPublicService publicService;

	@Reference(version = "1.0.0")
	private IAccountService accountService;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Resource
    private PafAccMstService pafAccMstService;

	@Resource
	private MyJedis myJedis;

	private final static String BRTEL_PREFIX = "paf_branch.";
	private final static String COMMON_PREFIX = "paf_common.";

	public void exec() throws Exception {
		MyLog myLog = new MyLog();

		String jsonStrCenterList = null;
		String txtPath = null; // 临时文件目录
		try (Jedis jedis = myJedis.connect()) {
			jsonStrCenterList = jedis.get(BRTEL_PREFIX + "PAFCENTER_LIST");
			txtPath = jedis.get(COMMON_PREFIX + "txt_path");
		}
		if (jsonStrCenterList == null || jsonStrCenterList.length() == 0) {
			myLog.error(logger, "公积金中心信息未配置[" + BRTEL_PREFIX + "PAFCENTER_LIST" + "]");
			throw new RuntimeException("公积金中心信息未配置[" + BRTEL_PREFIX + "PAFCENTER_LIST" + "]");
		}
		PafCenterList pafCenterList = JsonUtil.toBean(jsonStrCenterList, PafCenterList.class);
		for (PafCenterInfo pafCenterInfo : pafCenterList.getData()) {
			myLog.info(logger,
					"推送账户变动信息，当前处理公积金中心[" + pafCenterInfo.getPafcNo() + "][" + pafCenterInfo.getPafcName() + "]");
			pushTraceLogByPafc(myLog, pafCenterInfo.getPafcNo(), pafCenterInfo.getDepartCode(), txtPath);
		}

	}

	private void pushTraceLogByPafc(MyLog myLog, String centerNo, String departCode, String localPath) throws SysTradeExecuteException {
		String jsonStrAcList = null;
		try (Jedis jedis = myJedis.connect()) {
			jsonStrAcList = jedis.get(BRTEL_PREFIX + centerNo + "_ACNOLIST");
		}
		if (jsonStrAcList == null || jsonStrAcList.length() == 0) {
			myLog.error(logger, "尚未该公积金中心配置签约账号信息[" + centerNo + "]");
			return;
		}
		PafAcNoList pafAcnoList = JsonUtil.toBean(jsonStrAcList, PafAcNoList.class);
		for (PafAcNoInfo pafAcNoInfo : pafAcnoList.getData()) {
			Integer sysDate = publicService.getSysDate("CIP");
			Integer sysTime = publicService.getSysTime();
			Integer sysTraceno = publicService.getSysTraceno();
			myLog.info(logger,
					"推送账户变动信息，公积金中心[" + centerNo + "][" + pafAcNoInfo.getAcNo() + "][" + pafAcNoInfo.getAcName() + "]");

			// 1、取当前账户最大流水号
			String reference = getMaxRef(myLog, centerNo, pafAcNoInfo.getAcNo(), sysDate);

			// 2、依据当前账户最大流水与核心通讯获取文件
			ESB_REP_30015700901 esb_rep_30015700901 = fetchTraceLogBySingleAcc(myLog, centerNo, pafAcNoInfo.getAcNo(),
					reference, sysDate, sysTime, sysTraceno);
			if (esb_rep_30015700901.getRepBody().getQueryArray().size() != 1) {
				myLog.error(logger, "核心返回数据异常[" + esb_rep_30015700901.getRepBody().getQueryArray().size() + "]");
				throw new RuntimeException("核心返回数据异常[" + esb_rep_30015700901.getRepBody().getQueryArray().size() + "]");
			}
			String refMax = esb_rep_30015700901.getRepBody().getQueryArray().get(0).getRefMax();

			// 3、判断核心返回当前最大流水与上送流水是否相同，如相同，则无数据，不取文件
			if (refMax.equals(reference)) {
				myLog.info(logger, "账号[" + pafAcNoInfo.getAcNo() + "]无流水返回[" + reference + "]");
				continue;
			}

			// 4、从文件传输平台获取核心文件
			String remoteFile = esb_rep_30015700901.getRepSysHead().getFilePath();
			String fileName = "BDC_BAL_NTF" + "SBDC201" + String.format("%08d", sysDate)
					+ String.format("%06d", sysTime) + String.format("%08d", sysTraceno) + ".act";
			String localFile = localPath + File.separator + fileName;
			loadTraceLogFile(myLog, remoteFile, localFile);

			// 5、推送从核心获取的文件，推送至公积金系统
			CLI_REP_DATA pack = pushTraceLogFile(myLog, localFile, fileName, sysDate, sysTime, sysTraceno);
			if (pack.getHead().get("TxStatus").equals("0") && pack.getHead().get("RtnCode").equals("00000")) {
				// 6、成功，则更新最大流水号
				myLog.info(logger, "发送账户签约通知成功[" + pafAcNoInfo.getAcNo() + "][" + refMax + "]");
				setMaxRef(myLog, centerNo, pafAcNoInfo.getAcNo(), refMax);
				//将成功记录插入数据库中
				InsertPafAccMst(myLog,fileName,centerNo,departCode,String.format("%08d", sysDate)
						+ String.format("%06d", sysTime),localFile);
			} else {
				// 7、失败，不更新
				myLog.info(logger, "发送账户签约通知失败[" + pafAcNoInfo.getAcNo() + "]");
			}
		}
	}

	/**
	 *
	 * @param myLog myLog
	 * @param fileName 文件名
	 * @param centerNo 公积金中心编号
	 * @param departCode 部门编码
	 * @param reportTime 上报时间(YYYYMMDDHHMMSS)
	 * @param localFile 本地文件路径
	 */
	private void InsertPafAccMst(MyLog myLog,String fileName, String centerNo, String departCode, String reportTime, String localFile) {
		BufferedReader br = null;
		myLog.info(logger, "账户变动信息入库开始");
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(localFile)),"UTF-8"));
			String lineTxt=null;
			while ((lineTxt = br.readLine()) != null) {
				lineTxt += "*|";
				String[] array = lineTxt.split("\\|");
                if(array.length<24){
                    myLog.info(logger,"文件【"+localFile+"】内容缺失");
                    continue;
                }

                PafAccMstReport pafAccMstReport = new PafAccMstReport();
                pafAccMstReport.setFileName(fileName);
                pafAccMstReport.setCenterNo(centerNo);
                pafAccMstReport.setDepartCode(departCode);
                pafAccMstReport.setReportTime(reportTime);
                pafAccMstReport.setNum(array[0]);  //序号
                pafAccMstReport.setAcctNo(array[1]);  //帐号
                pafAccMstReport.setReference(array[2]); //银行主机流水号
                pafAccMstReport.setTranCode(array[3]); //交易代码
                pafAccMstReport.setOthAcctNo(array[4]); //交易对手账号
                pafAccMstReport.setOthAcctName(array[5]); //交易对手户名
                pafAccMstReport.setTranAmt((array[6]==null||"".equals(array[6]))?new BigDecimal("0.00"): new BigDecimal(array[6])); //金额
                pafAccMstReport.setTranDate(array[7]); //交易日期
                pafAccMstReport.setTranTime(array[8]); //交易时间
                pafAccMstReport.setAvailableAmt((array[9]==null||"".equals(array[9]))?new BigDecimal("0.00"): new BigDecimal(array[9])); //可用金额
                pafAccMstReport.setBranch(array[10]); //开户机构号
                pafAccMstReport.setRemark(array[11]); //备注
                pafAccMstReport.setCcy(array[12]); //币别
                pafAccMstReport.setAmtType(array[13]); //钞汇鉴别
                pafAccMstReport.setBalance((array[14]==null||"".equals(array[14]))?new BigDecimal("0.00"): new BigDecimal(array[14])); //余额
                pafAccMstReport.setOdtBalance((array[15]==null||"".equals(array[15]))?new BigDecimal("0.00"): new BigDecimal(array[15])); //可透支余额
                pafAccMstReport.setDocType(array[16]); //凭证种类
                pafAccMstReport.setVoucherNo(array[17]); //凭证号码
                pafAccMstReport.setOthBranch(array[18]); //交易对手行号
                pafAccMstReport.setNarrative(array[19]); //摘要
                pafAccMstReport.setReversak(array[20]); //冲正标识
                pafAccMstReport.setSerialNum(array[21]); //笔号
                pafAccMstReport.setVolumeNum(array[22]); //册号

                pafAccMstService.save(pafAccMstReport);
			}

		} catch (Exception e) {
            myLog.error(logger, "文件【"+localFile+"】插入失败", e);
            throw new RuntimeException("文件【"+localFile+"】插入失败");
		} finally {
			if(null != br) {
				try {
					br.close();
				} catch (IOException e) {
					myLog.error(logger, "文件流关闭失败", e);
				}
			}
			myLog.info(logger, "账户变动信息入库结束");
		}
	}

	/**
	 * @Title: getMaxRef @Description: 获取账户最大流水号 @param @param
	 * myLog @param @param centerNo @param @param acno @param @param
	 * sysDate @param @return 设定文件 @return String 返回类型 @throws
	 */
	private String getMaxRef(MyLog myLog, String centerNo, String acno, Integer sysDate) {
		String reference = null;
		try (Jedis jedis = myJedis.connect()) {
			reference = jedis.get(BRTEL_PREFIX + centerNo + "_" + acno);
		}
		if (reference == null) {
			reference = "ENS" + String.format("%08d", sysDate) + String.format("%08d", 0);
			myLog.error(logger, "未配置账号[" + acno + "]最大流水号，取当日默认流水[" + reference + "]");
		} else {
			myLog.info(logger, "账号[" + acno + "]最大流水号[" + reference + "]");
		}
		return reference;
	}

	/**
	 * @Title: setMaxRef @Description: 设置账户最大流水号 @param @param
	 * myLog @param @param centerNo @param @param acno @param @param reference
	 * 设定文件 @return void 返回类型 @throws
	 */
	private void setMaxRef(MyLog myLog, String centerNo, String acno, String reference) {
		try (Jedis jedis = myJedis.connect()) {
			jedis.set(BRTEL_PREFIX + centerNo + "_" + acno, reference);
			myLog.info(logger, "更新最大流水成功[" + acno + "][" + reference + "]");
		}
	}

	/**
	 * @Title: pushTraceLogFile @Description: 推送账户变更文件至公积金系统 @param @param
	 * myLog @param @param localFile @param @param fileName @param @param
	 * sysDate @param @param sysTime @param @param
	 * sysTraceno @param @return @param @throws PafTradeExecuteException
	 * 设定文件 @return CLI_REP_DATA 返回类型 @throws
	 */
	private CLI_REP_DATA pushTraceLogFile(MyLog myLog, String localFile, String fileName, Integer sysDate,
			Integer sysTime, Integer sysTraceno) throws PafTradeExecuteException {
		CLI_REQ_BDC reqData = new CLI_REQ_BDC(myLog, sysDate, sysTime, sysTraceno);
		FIELDS_LIST_OUTER outer = new FIELDS_LIST_OUTER("FILE_LIST");
		List<FIELDS_LIST_INNER> innerList = new ArrayList<FIELDS_LIST_INNER>();
		FIELDS_LIST_INNER inner0 = new FIELDS_LIST_INNER("0");
		innerList.add(inner0);
		inner0.getField().add(new FIELD("NAME", fileName));
		String bcdString = null;
		try {
			String fileBuf = FileUtil.readToString(localFile, "UTF-8");
			myLog.info(logger, "发送的账户信息[" + fileBuf + "]");
			bcdString = FileFieldConv.fieldASCtoBCD(fileBuf, PAF.ENCODING);
		} catch (IOException e) {
			myLog.error(logger, "转换文件异常", e);
			throw new RuntimeException("转换文件异常", e);
		}
		inner0.getField().add(new FIELD("DATA", bcdString));
		outer.setField_list(innerList);
		reqData.getBody().setField_list(outer);
		CLI_REP_DATA pack = null;
		try {
			pack = accountService.notify(reqData, "SBDC201");
		} catch (JAXBException e) {
			myLog.error(logger, "发送账户通知异常", e);
			throw new RuntimeException("发送账户通知异常", e);
		}

		return pack;
	}

	/**
	 * @Title: fetchTraceLogBySingleAcc @Description:
	 * 单账号查询获取账户交易流水 @param @param myLog @param @param centerNo @param @param
	 * accNo @param @param reference @param @param sysDate @param @param
	 * sysTime @param @param sysTraceno @param @return @param @throws
	 * SysTradeExecuteException 设定文件 @return ESB_REP_30015700901 返回类型 @throws
	 */
	private ESB_REP_30015700901 fetchTraceLogBySingleAcc(MyLog myLog, String centerNo, String accNo, String reference,
			Integer sysDate, Integer sysTime, Integer sysTraceno) throws SysTradeExecuteException {
		String txBrno = null;
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(BRTEL_PREFIX + centerNo + "_TXBRNO");
			txTel = jedis.get(BRTEL_PREFIX + centerNo + "_TXTEL");
		}
		ESB_REQ_30015700901 esbReq_30015700901 = new ESB_REQ_30015700901(myLog, sysDate, sysTime, sysTraceno);
		DataTransObject reqDto = new DataTransObject();
		reqDto.setSourceType("GJ");
		reqDto.setSysDate(sysDate);
		reqDto.setSysTime(sysTime);
		reqDto.setSysTraceno(sysTraceno);
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30015700901.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30015700901.setReqSysHead(reqSysHead);
		List<ESB_REQ_30015700901.QUERY> queryArray = new ArrayList<>();
		ESB_REQ_30015700901.QUERY query = new ESB_REQ_30015700901.QUERY();
		query.setAcctNo(accNo);
		query.setReference(reference);
		queryArray.add(query);
		ESB_REQ_30015700901.REQ_BODY reqBody_30015700901 = esbReq_30015700901.getReqBody();
		reqBody_30015700901.setQueryArray(queryArray);
		ESB_REP_30015700901 esb_rep_30015700901 = forwardToESBService.sendToESB(esbReq_30015700901, reqBody_30015700901,
				ESB_REP_30015700901.class);
		myLog.info(logger, "从核心获取文件通讯成功");
		return esb_rep_30015700901;
	}

	/**
	 * @Title: loadFile @Description: 从文件传输平台下载文件 @param @param
	 * myLog @param @param remoteFile 文件传输平台路径+文件名 @param @param localFile
	 * 文件本地路径+文件名 @param @throws PafTradeExecuteException 设定文件 @return void
	 * 返回类型 @throws
	 */
	private void loadTraceLogFile(MyLog myLog, String remoteFile, String localFile) throws PafTradeExecuteException {
		FtpClientConfigSet configSet = new FtpClientConfigSet();
		FtpGet ftpGet = null;
		try {
			ftpGet = new FtpGet(remoteFile, localFile, configSet);
			boolean result = ftpGet.doGetFile();
			if (!result) {
				myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载失败！");
				PafTradeExecuteException e = new PafTradeExecuteException(PafTradeExecuteException.PAF_E_10009);
				throw e;
			}
			myLog.info(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载成功！");
		} catch (Exception e) {
			myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载失败！", e);
			PafTradeExecuteException e1 = new PafTradeExecuteException(PafTradeExecuteException.PAF_E_10009,
					e.getMessage());
			throw e1;
		} finally {
			if (ftpGet != null) {
				try {
					ftpGet.close();
				} catch (FtpException e) {
					myLog.error(logger, "文件传输关闭连接失败！", e);
				}
			}
		}
	}

	@Bean(name = "accMstTracePushJobDetail")
	public MethodInvokingJobDetailFactoryBean accMstTracePushJobDetail(AccMstTracePushTask task) {// ScheduleTask为需要执行的任务
		MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
		jobDetail.setConcurrent(false);
		jobDetail.setName(JOBNAME);
		jobDetail.setGroup(QuartzJobConfigration.JOBGROUP);
		jobDetail.setTargetObject(task);
		jobDetail.setTargetMethod(QuartzJobConfigration.METHODNAME);
		return jobDetail;
	}

	@Bean(name = "accMstTracePushJobTrigger")
	public CronTriggerFactoryBean accMstTracePushJobTrigger(
			@Qualifier("accMstTracePushJobDetail") MethodInvokingJobDetailFactoryBean jobDetail) {
		CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
		tigger.setJobDetail(jobDetail.getObject());
		String exp = null;
		try (Jedis jedis = myJedis.connect()) {
			exp = jedis.get(QuartzJobConfigration.PAF_CRON + JOBNAME);
		}
		if (exp == null) {
			exp = "0 0/2 * * * ?";
		}
		logger.info("任务[" + JOBNAME + "]启动[" + exp + "]");
		tigger.setCronExpression(exp);
		tigger.setName(JOBNAME);
		return tigger;
	}

	@Bean(name = "accMstTracePushScheduler")
	public SchedulerFactoryBean schedulerFactory(@Qualifier("accMstTracePushJobTrigger") Trigger cronJobTrigger) {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setOverwriteExistingJobs(true);
		bean.setStartupDelay(5);
		bean.setTriggers(cronJobTrigger);
		return bean;
	}
}
