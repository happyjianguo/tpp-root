package com.fxbank.cap.manager.quartz;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fxbank.cap.esb.model.cnaps2.ESB_REP_30041000801;
import com.fxbank.cap.esb.model.cnaps2.ESB_REQ_30041000801;
import com.fxbank.cap.esb.model.ses.ESB_REP_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REP_30013000201;
import com.fxbank.cap.esb.model.ses.ESB_REP_30013000510;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30013000201;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30013000510;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.model.BatchLoanDetailModel;
import com.fxbank.cap.paf.model.BatchLoanMasterModel;
import com.fxbank.cap.paf.model.BatchLoanUpdDetailModel;
import com.fxbank.cap.paf.service.IBatchLoanService;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.pub.service.IPublicService;

import redis.clients.jedis.Jedis;

public class BatchLoanTaskExecutor implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(BatchLoanTaskExecutor.class);

	private MyLog myLog;
	private Integer num; // 当前线程标志
	private CountDownLatch latch;
	private List<String> dataIndex;
	private IForwardToESBService forwardToESBService;
	private BatchLoanMasterModel masterModel;

	private IPublicService publicService;
	private IBatchLoanService batchLoanService;

	private MyJedis myJedis;

	private static final String BRTEL_PREFIX = "paf_branch.";
	private final static String COMMON_PREFIX = "paf_common.";

	public BatchLoanTaskExecutor(MyLog myLog, Integer num, CountDownLatch latch, List<String> dataIndex,
			IForwardToESBService forwardToESBService, IBatchLoanService batchLoanService, IPublicService publicService,
			MyJedis myJedis, BatchLoanMasterModel masterModel) {
		this.myLog = myLog;
		this.num = num;
		this.latch = latch;
		this.dataIndex = dataIndex;
		this.batchLoanService = batchLoanService;
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
				BatchLoanUpdDetailModel updModel = new BatchLoanUpdDetailModel();
				updModel.setBatchNo(masterModel.getBatchNo());
				updModel.setSeqNo(str);
				try {
					// 1、查询附表，获得记账信息
					BatchLoanDetailModel selections = new BatchLoanDetailModel();
					selections.setBatchNo(masterModel.getBatchNo());
					selections.setSeqNo(str);
					BatchLoanDetailModel record = batchLoanService.queryDetailByPk(selections);
					if ("0".equals(record.getTxStatus())) {
						BatchLoanUpdDetailModel deModel = new BatchLoanUpdDetailModel();
						deModel.setBatchNo(masterModel.getBatchNo());
						deModel.setSeqNo(str);
						deModel.setOldStatus("0");
						deModel.setTxStatus("1");
						batchLoanService.updateDetail(deModel);
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
							ESB_REP_30013000510 deAccount = null;
							// 根据收款账户账号查询户名
							try {
								 deAccount = queryDeAccount(record);
								// 付款户名
								String deAccountName = deAccount.getRepBody().getAcctName();
								// 验证付款户名与账号是否一致
								if (!deAccountName.equals(record.getDeAcctName())) {
									PafTradeExecuteException e = new PafTradeExecuteException(
											PafTradeExecuteException.PAF_E_10008);
									myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg() + "批量编号"
											+ masterModel.getBatchNo() + "序号" + str, e);
									updModel.setBatchNo(masterModel.getBatchNo());
									updModel.setSeqNo(str);
									updModel.setTxStatus("3");
									batchLoanService.updateDetail(updModel);
									continue;
								}
							} catch (Exception e) {
								myLog.error(logger, "批量交易单笔记账验证收款账户失败，批量编号" + masterModel.getBatchNo() + "序号" + str);
								updModel.setBatchNo(masterModel.getBatchNo());
								updModel.setSeqNo(str);
								updModel.setTxStatus("3");
								batchLoanService.updateDetail(updModel);
								continue;
							}
							//判断足额标志,0：足额1：非足额
							String enghFlag = record.getEnghFlag();
							ESB_REP_30011000101 esbRep_30011000101 = null;
							if("1".equals(enghFlag)) {
							//非足额时，先查询可用余额
								ESB_REP_30013000201 esbRep30013000201 = queryBalance(record);
								BigDecimal suAmt = new BigDecimal(esbRep30013000201.getRepBody().getActualBal());
								if(suAmt.compareTo(record.getTxAmt()) > 0) {
									suAmt = record.getTxAmt();
								}
								record.setSuAmt(suAmt);
								updModel.setBatchNo(masterModel.getBatchNo());
								updModel.setSeqNo(str);
								updModel.setSuAmt(suAmt);
								batchLoanService.updateDetail(updModel);
							}else {
								record.setSuAmt(record.getTxAmt());
								updModel.setBatchNo(masterModel.getBatchNo());
								updModel.setSeqNo(str);
								updModel.setSuAmt(record.getTxAmt());
								batchLoanService.updateDetail(updModel);
							}
							try {
								esbRep_30011000101 = innerCapCharge(record);
							} catch (Exception e) {
								myLog.error(logger, "批量交易单笔记账失败，批量编号" + masterModel.getBatchNo() + "序号" + str, e);
								updModel.setBatchNo(masterModel.getBatchNo());
								updModel.setSeqNo(str);
								updModel.setTxStatus("3");
								batchLoanService.updateDetail(updModel);
								continue;
							}
							myLog.info(logger, "批量付款单笔同行付款成功，批量编号" + masterModel.getBatchNo() + "序号" + str);
							hostSeqNo = esbRep_30011000101.getRepBody().getReference();
							hostRspCode = esbRep_30011000101.getRepSysHead().getRet().get(0).getRetCode();
							hostRspMsg = esbRep_30011000101.getRepSysHead().getRet().get(0).getRetMsg();
						} else {
							try {
								ESB_REP_30041000801 esbRep_30041000801 = outerCapCharge(record);
							} catch (Exception e) {
								myLog.error(logger, "批量交易单笔记账失败，批量编号" + masterModel.getBatchNo() + "序号" + str);
								updModel.setBatchNo(masterModel.getBatchNo());
								updModel.setSeqNo(str);
								updModel.setTxStatus("3");
								batchLoanService.updateDetail(updModel);
								continue;
							}
							myLog.info(logger, "批量付款跨行付款成功，批量编号" + masterModel.getBatchNo() + "序号" + str);
						}
						updModel.setTxStatus("2");
						updModel.setHostSeqNo(hostSeqNo);
						updModel.setHostRspCode(hostRspCode);
						updModel.setHostRspMsg(hostRspMsg);
						batchLoanService.updateDetail(updModel);
					}
				} catch (SysTradeExecuteException e) {
					updModel.setBatchNo(masterModel.getBatchNo());
					updModel.setSeqNo(str);
					updModel.setTxStatus("3");
					try {
						batchLoanService.updateDetail(updModel);
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

	private ESB_REP_30011000101 innerCapCharge(BatchLoanDetailModel record) throws SysTradeExecuteException {

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
			inacno = jedis.get(BRTEL_PREFIX + txUnitNo + "_LOAN_INACNO");
		}

		ESB_REQ_30011000101 esbReq_30011000101 = new ESB_REQ_30011000101(myLog, masterModel.getSysDate(),
				masterModel.getSysTime(), masterModel.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000101.getReqSysHead(), getReqDto())
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30011000101.setReqSysHead(reqSysHead);

		ESB_REQ_30011000101.REQ_BODY reqBody_30011000101 = esbReq_30011000101.getReqBody();
		// 账号/卡号
		reqBody_30011000101.setBaseAcctNo(record.getDeAcctNo());
		//账户名称
		reqBody_30011000101.setAcctName(record.getDeAcctName());
		// 交易类型
		reqBody_30011000101.setTranType("GJ03");
		// 交易币种
		reqBody_30011000101.setTranCcy("CNY");
		// 交易金额
		reqBody_30011000101.setTranAmt(record.getSuAmt().toString());
		// 对方账号/卡号
		reqBody_30011000101.setOthBaseAcctNo(inacno);
		// 对方户名
		reqBody_30011000101.setOthBaseAcctName(masterModel.getCrAcctname());
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
	private ESB_REP_30041000801 outerCapCharge(BatchLoanDetailModel record) throws SysTradeExecuteException {
		//公积金节点编号
		String txUnitNo = masterModel.getSndUnitNo();
		//交易机构
        String txBrno =null ;
        //柜员号
    	String txTel = null;
    	// 过渡账户
    	String inacno = null;
        try(Jedis jedis = myJedis.connect()){
        	txBrno = jedis.get(BRTEL_PREFIX+txUnitNo+"_TXBRNO");
        	txTel = jedis.get(BRTEL_PREFIX+txUnitNo+"_TXTEL");
        	inacno = jedis.get(BRTEL_PREFIX + txUnitNo + "_LOAN_INACNO");
        }
		
		ESB_REQ_30041000801 esbReq_30041000801 = new ESB_REQ_30041000801(myLog, masterModel.getSysDate(),
				masterModel.getSysTime(), masterModel.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30041000801.getReqSysHead(), getReqDto())
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30041000801.setReqSysHead(reqSysHead);
			
		ESB_REQ_30041000801.REQ_BODY reqBody_30041000801 = esbReq_30041000801.getReqBody();
		//业务种类
		reqBody_30041000801.setBusVrT3("09001");
		//付款人名称
		reqBody_30041000801.setPyrNmT(record.getDeAcctName());
		//付款人地址
		reqBody_30041000801.setPyrAddrT(record.getDeAddr());
		//付款人账号
		reqBody_30041000801.setPyrAcctNoT2(record.getDeAcctNo());
		//收款人账号
		reqBody_30041000801.setRcptPrAcctNoT2(inacno);
		//交易金额
		reqBody_30041000801.setTrnsAmtT2(record.getTxAmt().toString());
		//接收行行号
		reqBody_30041000801.setRcvngBnkNoT6(record.getDeChgNo());
		//业务类型
		reqBody_30041000801.setBusTpT10("B100");
		//收款人名称
		reqBody_30041000801.setRcptPrNmT3("");
		//收款人地址
		reqBody_30041000801.setRcptPrAddrT1("");
		//附言
		reqBody_30041000801.setNoteT2(record.getSummary());
		ESB_REP_30041000801 esbRep_30041000801 = forwardToESBService.sendToESB(esbReq_30041000801,reqBody_30041000801, ESB_REP_30041000801.class);
		return esbRep_30041000801;
	}

	/**
	 * @Title: queryDeAccount @Description: 验证付款账户账号与户名是否一致 @param @param
	 *         reqDto @param @return @param @throws SysTradeExecuteException
	 *         设定文件 @return ESB_REP_30013000510 返回类型 @throws
	 */
	private ESB_REP_30013000510 queryDeAccount(BatchLoanDetailModel record) throws SysTradeExecuteException {
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

		ESB_REQ_30013000510 esbReq_30013000510 = new ESB_REQ_30013000510(myLog, 
				masterModel.getSysDate(),masterModel.getSysTime(), 
				masterModel.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30013000510.getReqSysHead(), getReqDto())
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30013000510.setReqSysHead(reqSysHead);

		ESB_REQ_30013000510.REQ_BODY reqBody_30013000510 = esbReq_30013000510.getReqBody();
		// 收款账号
		reqBody_30013000510.setBaseAcctNo(record.getDeAcctNo());
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
	/**
     * @Title: queryBalance
     * @Description: 查询账户余额
     * @param @param SER_REQ_DATA
     * @param @throws SysTradeExecuteException    设定文件
     * @return ESB_REP_30013000201    返回类型
     * @throws
     */
    private ESB_REP_30013000201 queryBalance(BatchLoanDetailModel record) throws SysTradeExecuteException {
        //公积金节点编号
        String txUnitNo = masterModel.getSndUnitNo();
        //交易机构
        String txBrno =null ;
        //柜员号
    	String txTel = null;
    	//产品类型
    	String prodType = null;
        try(Jedis jedis = myJedis.connect()){
        	txBrno = jedis.get(BRTEL_PREFIX+txUnitNo+"_TXBRNO");
        	txTel = jedis.get(BRTEL_PREFIX+txUnitNo+"_TXTEL");
        	prodType = jedis.get(BRTEL_PREFIX+txUnitNo+"_PROD_TYPE_D");
        }

        ESB_REQ_30013000201 esbReq_30013000201 = new ESB_REQ_30013000201(myLog, 
        		masterModel.getSysDate(),masterModel.getSysTime(), 
				masterModel.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30013000201.getReqSysHead(), getReqDto())
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30013000201.setReqSysHead(reqSysHead);

        ESB_REQ_30013000201.REQ_BODY reqBody_30013000201=esbReq_30013000201.getReqBody();
        //账号
        reqBody_30013000201.setBaseAcctNo(record.getDeAcctNo());
        //产品类型
        reqBody_30013000201.setProdType(prodType);
        //账户序号
        reqBody_30013000201.setAcctSeqNo("1");
        //币种
        reqBody_30013000201.setCcy("CNY");
        
        ESB_REP_30013000201 esb_rep_30013000201=forwardToESBService.sendToESB(esbReq_30013000201,reqBody_30013000201,ESB_REP_30013000201.class);

        return esb_rep_30013000201;
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
