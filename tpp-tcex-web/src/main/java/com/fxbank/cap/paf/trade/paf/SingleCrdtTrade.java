package com.fxbank.cap.paf.trade.paf;


import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.model.cnaps2.ESB_REP_30041000406;
import com.fxbank.cap.esb.model.cnaps2.ESB_REQ_30041000406;
import com.fxbank.cap.esb.model.ses.ESB_REP_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REP_30013000510;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30013000510;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.paf.dto.SER_REP_BDC;
import com.fxbank.cap.paf.dto.SER_REQ_DATA;
import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.model.FIELD;
import com.fxbank.cap.paf.model.SinglCrdtCapUpdModel;
import com.fxbank.cap.paf.model.SinglCrdtInfoModel;
import com.fxbank.cap.paf.service.ISingleCrdtService;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;

import redis.clients.jedis.Jedis;

/** 
* @ClassName: SingleCrdtTrade 
* @Description: 单笔付款
* @author DuZhenduo
* @date 2018年10月18日 下午2:50:42 
*  
*/
@Service("BDC201")
public class SingleCrdtTrade implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(SingleCrdtTrade.class);

	@Reference(version = "1.0.0")
	private ISingleCrdtService singleCrdtService;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService IForwardToESBService;
	
	@Resource
	private MyJedis myJedis;
	
	@Resource
	private LogPool logPool;
	
	private static final String BRTEL_PREFIX = "paf_branch.";
	
	//发送方日期
	private static final String SENDDATE = "SendDate";
	//发送方时间
	private static final String SENDTIME = "SendTime";
	//发送方流水
	private static final String SENDSEQNO = "SendSeqNo";
	//公积金机构号
	private static final String TXUNITNO = "TxUnitNo";
	//发送方节点号
	private static final String SENDNODE = "SendNode";
	//接收方节点号
	private static final String RECEIVENODE = "ReceiveNode";
	//结算系统日期
	private static final String BDCDATE = "BDCDate";
	//结算系统时间
	private static final String BDCTIME = "BDCTime";
	//结算系统流水
	private static final String BDCSEQNO = "BDCSeqNo";
	//银行客户编号
	private static final String CUSTNO = "CustNo";
	//操作员编号
	private static final String OPERNO = "OperNo";
	//币种，156
	private static final String CURRNO = "CurrNo";
	//钞汇鉴别：1、钞；2、汇
	private static final String CURRIDEN = "CurrIden";
	//结算模式：1、同行；2、跨行实时；3、跨行非实时
	private static final String SETTLETYPE = "SettleType";
	//业务类型
	private static final String BUSTYPE = "BusType";
	//付方账号
	private static final String DEACCTNO = "DeAcctNo";
	//付方户名
	private static final String DEACCTNAME = "DeAcctName";
	//付方账户类别：1、对私；2、对公
	private static final String DEACCTCLASS = "DeAcctClass";
	//本金发生额
	private static final String CAPAMT = "CapAmt";
	//付息户账号
	private static final String DEINTACCTNO = "DeIntAcctNo";
	//付息户户名
	private static final String DEINTACCTNAME = "DeIntAcctName";
	//付息户类别:1、对私；2、对公
	private static final String DEINTACCTCLASS = "DeIntAcctClass";
	//利息收方账号
	private static final String DEINTCRACCT = "DeIntCrAcct";
	//利息发生额
	private static final String INTAMT = "IntAmt";
	//收方账号
	private static final String CRACCTNO = "CrAcctNo";
	//收方户名
	private static final String CRACCTNAME = "CrAcctName";
	//收方账户类别:1、对私；2、对公
	private static final String CRACCTCLASS = "CrAcctClass";
	//收方账户行别:0、本行；1、跨行
	private static final String CRBANKCLASS = "CrBankClass";
	//收方行名
	private static final String CRBANKNAME = "CrBankName";
	//收方联行号
	private static final String CRCHGNO = "CrChgNo";
	//交易金额
	private static final String AMT = "Amt";
	//业务明细账号
	private static final String REFACCTNO = "RefAcctNo";
	//业务明细流水号 1
	private static final String REFSEQNO1 = "RefSeqNo1";
	//业务明细流水号 2
	private static final String REFSEQNO2 = "RefSeqNo2";
	//摘要
	private static final String  SUMMARY= "Summary";
	//备注
	private static final String REMARK = "Remark";
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		SER_REQ_DATA reqDto = (SER_REQ_DATA) dto;
		SER_REP_BDC repDto = new SER_REP_BDC();
		//结算模式 1：同行2：跨行实时3：跨行非实时
		String settleType = reqDto.getBody().get(SETTLETYPE).toString();
		//业务类型 仅是账务操作，不控制业务种类
		String busType = reqDto.getBody().get(BUSTYPE).toString();
		//如果利息有值抛出异常
		String intAmt = reqDto.getBody().get(INTAMT).toString();
		if(!"".equals(intAmt) ) {
			PafTradeExecuteException e = new PafTradeExecuteException(
					PafTradeExecuteException.PAF_E_10016);
			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
			throw e;
		}
		//交易登记
		initRecord(reqDto);
		myLog.info(logger, "初始化交易记录成功");
		//根据付款账户账号查询户名
		ESB_REP_30013000510 deAccount = queryDeAccount(reqDto);
		//付款账户名称
		String deAccountName = deAccount.getRepBody().getAcctName();
		//验证付款户名与账号是否一致
		if(!deAccountName.equals(reqDto.getBody().get(DEACCTNAME).toString())) {
			PafTradeExecuteException e = new PafTradeExecuteException(
					PafTradeExecuteException.PAF_E_10007);
			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
			throw e;
		}
		//本金记账状态码
		String capCode = null;
		//本金记账状态信息
		String capMsg = null;
		//本金记账流水号
		String capSeqno = null;
		//银行主机处理状态
		String hostStatus = null;
		//结算模式是本行，直接记录核心账务
		if("1".equals(settleType)) {
			//根据收款账户账号查询户名
			ESB_REP_30013000510 crAccount = queryCrAccount(reqDto);
			//收款户名
			String crAccountName = crAccount.getRepBody().getAcctName();
			 //验证收款户名与账号是否一致
			if(!crAccountName.equals(reqDto.getBody().get(CRACCTNAME).toString())) {
				myLog.error(logger,reqDto.getBody().get(CRACCTNAME).toString()+"|"+crAccountName);
				PafTradeExecuteException e = new PafTradeExecuteException(
						PafTradeExecuteException.PAF_E_10008);
				myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
				throw e;
			}
			try{
				ESB_REP_30011000101 esbRep_30011000101 = innerCapCharge(reqDto);
				capCode = esbRep_30011000101.getRepSysHead().getRet().get(0).getRetCode();
				capMsg = esbRep_30011000101.getRepSysHead().getRet().get(0).getRetCode();
				capSeqno = esbRep_30011000101.getRepSysHead().getReference();
				updateCapRecord(reqDto,"1",capCode,capMsg,capSeqno);				//更新本金记账成功信息
				hostStatus = "0";
				myLog.info(logger, "核心系统记账成功，流水号"+capSeqno);
			}catch(SysTradeExecuteException e){
				myLog.error(logger, "核心系统记账失败,付款号"+
					    reqDto.getBody().get(DEACCTNO).toString()+"收款号"+
					    reqDto.getBody().get(CRACCTNO).toString());
				myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
				PafTradeExecuteException e1 = new PafTradeExecuteException(
						PafTradeExecuteException.PAF_E_10003,e.getRspMsg());
				updateCapRecord(reqDto,"9",e1.getRspCode(),e1.getRspMsg(),"0");		//更新本金记账失败信息
				throw e1;
			}
		}
		else if("2".equals(settleType) || "3".equals(settleType)){		//结算模式是跨行，二代支付系统处理账务
			try{
				ESB_REP_30041000406 esbRep_30041000406 = outerCapCharge(reqDto);
				capCode = esbRep_30041000406.getRepSysHead().getRet().get(0).getRetCode();
				capMsg = esbRep_30041000406.getRepSysHead().getRet().get(0).getRetCode();
				//核心流水号
				capSeqno = "ENS"+esbRep_30041000406.getRepBody().getHostDate()
						+esbRep_30041000406.getRepBody().getHostTraceNo();
				//渠道日期
				String channelDate = esbRep_30041000406.getRepBody().getSystemDate();
				//渠道流水号
				String channelSeqNo = esbRep_30041000406.getRepBody().getSystemReference();
				//更新本金记账成功信息
				updateCapRecord(reqDto,"1",capCode,capMsg,channelDate+","+channelSeqNo);				
				hostStatus = "0";
				myLog.info(logger, "二代支付系统记账成功");
			}catch(SysTradeExecuteException e){
				myLog.error(logger, "二代支付系统记账失败");
				myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
				PafTradeExecuteException e1 = new PafTradeExecuteException(
						PafTradeExecuteException.PAF_E_10003,e.getRspMsg());
				updateCapRecord(reqDto,"9",e1.getRspCode(),e1.getRspMsg(),"0");		//更新本金记账失败信息
				throw e1;
			}
		} else{
			PafTradeExecuteException e = new PafTradeExecuteException(
					PafTradeExecuteException.PAF_E_10002);
			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
			throw e;
		}
		//银行主机处理状态
		repDto.getBody().getField().add(new FIELD("HostStatus",hostStatus));
		//本金银行主机流水号
		repDto.getBody().getField().add(new FIELD("CapHostSeqNo",capSeqno));
		return repDto;
	}
	
	/** 
	* @Title: innerCapCharge 
	* @Description: 行内核心记本金账务
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30011000101    返回类型 
	* @throws 
	*/
	private ESB_REP_30011000101 innerCapCharge(SER_REQ_DATA reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		Map<String,Object> headMap = reqDto.getHead();
		Map<String,Object> bodyMap = reqDto.getBody();
		
		String txUnitNo = headMap.get(TXUNITNO).toString();		//公积金节点编号
		//交易机构
        String txBrno =null ;
        //柜员号
    	String txTel = null;
        try(Jedis jedis = myJedis.connect()){
        	txBrno = jedis.get(BRTEL_PREFIX+txUnitNo+"_TXBRNO");
        	txTel = jedis.get(BRTEL_PREFIX+txUnitNo+"_TXTEL");
        }
		
		ESB_REQ_30011000101 esbReq_30011000101 = new ESB_REQ_30011000101(myLog, reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000101.getReqSysHead(),reqDto).setBranchId(txBrno).setUserId(txTel).build(); 
		esbReq_30011000101.setReqSysHead(reqSysHead);
			
		ESB_REQ_30011000101.REQ_BODY reqBody_30011000101 = esbReq_30011000101.getReqBody();
		//账号/卡号
		reqBody_30011000101.setBaseAcctNo(bodyMap.get(DEACCTNO).toString());
		//账户名称
		reqBody_30011000101.setAcctName(bodyMap.get(DEACCTNAME).toString());
		//交易类型                                                                                                                                
		reqBody_30011000101.setTranType("GJ03");
		//交易币种
		reqBody_30011000101.setTranCcy("CNY");
		//交易金额
		reqBody_30011000101.setTranAmt(bodyMap.get(AMT).toString());
		//对方账号/卡号
		reqBody_30011000101.setOthBaseAcctNo(bodyMap.get(CRACCTNO).toString());
		//对方户名
		reqBody_30011000101.setOthBaseAcctName(bodyMap.get(CRACCTNAME).toString());
		//摘要
		reqBody_30011000101.setNarrative(bodyMap.get(SUMMARY).toString());
		//记账渠道类型
		reqBody_30011000101.setChannelType("GJ");
		//清算日期
		reqBody_30011000101.setSettlementDate(reqDto.getSysDate().toString());
		//对账标识,Y-参与对账;N-不参与对账
		reqBody_30011000101.setCollateFlag("Y");
		ESB_REP_30011000101 esbRep_30011000101 = IForwardToESBService.sendToESB(esbReq_30011000101,reqBody_30011000101, ESB_REP_30011000101.class);
		return esbRep_30011000101;
	}
	/** 
	* @Title: outerCapCharge 
	* @Description: 二代跨行支付记本金账务
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30011000101    返回类型 
	* @throws 
	*/
	private ESB_REP_30041000406 outerCapCharge(SER_REQ_DATA reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		Map<String,Object> headMap = reqDto.getHead();
		Map<String,Object> bodyMap = reqDto.getBody();
		//公积金节点编号
		String txUnitNo = headMap.get(TXUNITNO).toString();		
		//交易机构
        String txBrno =null ;
        //柜员号
    	String txTel = null;
        try(Jedis jedis = myJedis.connect()){
        	txBrno = jedis.get(BRTEL_PREFIX+txUnitNo+"_TXBRNO");
        	txTel = jedis.get(BRTEL_PREFIX+txUnitNo+"_TXTEL");
        }
		
		ESB_REQ_30041000406 esbReq_30041000406 = new ESB_REQ_30041000406(myLog, reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30041000406.getReqSysHead(),reqDto).setBranchId(txBrno).setUserId(txTel).build(); 
		esbReq_30041000406.setReqSysHead(reqSysHead);
			
		ESB_REQ_30041000406.REQ_BODY reqBody_30041000406 = esbReq_30041000406.getReqBody();
		//来源系统
		reqBody_30041000406.setSourceSys("CAP");
		//付款人账号
		reqBody_30041000406.setPayerAcctNo(bodyMap.get(DEACCTNO).toString());
		//付款人名称
		reqBody_30041000406.setPayerName(bodyMap.get(DEACCTNAME).toString());
		//付款人地址
		reqBody_30041000406.setPayerAddr(bodyMap.get(DEACCTNAME).toString());
		//金额
		reqBody_30041000406.setAmt(bodyMap.get(AMT).toString());
		//收款人账号
		reqBody_30041000406.setPayeeAcctNo(bodyMap.get(CRACCTNO).toString());
		//收款人名称
		reqBody_30041000406.setPayeeName(bodyMap.get(CRACCTNAME).toString());
		//收款人地址
		reqBody_30041000406.setPayeeAddr(bodyMap.get(CRACCTNAME).toString());
		//到账方式1-实时2-普通延时3-次日延时
		String tranMethod = "1";
		//根据结算模式判断系统类型
		String sysTpT = "",settleType = bodyMap.get(SETTLETYPE).toString();
        if("2".equals(settleType)) {
        	sysTpT = "HVPS";
        }else if("3".equals(settleType)) {
        	sysTpT = "BEPS";
        	tranMethod = "2";
        }
        reqBody_30041000406.setTranMethod(tranMethod);
        //系统类型
		reqBody_30041000406.setSysTpT(sysTpT);
		//接收行行号
		reqBody_30041000406.setRcvBankNo(bodyMap.get(CRCHGNO).toString());
		//接收行行名
		reqBody_30041000406.setRcvBankName(bodyMap.get(CRBANKNAME).toString());
		//附言
		reqBody_30041000406.setPostScript(bodyMap.get(SUMMARY).toString());
		//支取方式
		reqBody_30041000406.setWithDrawalType("W");
		//付款账户类型0-内部账 1-对私 2-对公
		reqBody_30041000406.setActualPayerAcctTp("0");
		//费用金额
		reqBody_30041000406.setFeeAmt("0");
		//费用类型
		reqBody_30041000406.setFeeType("0");
		
		ESB_REP_30041000406 esbRep_30041000406 = IForwardToESBService.sendToESB(esbReq_30041000406,reqBody_30041000406, ESB_REP_30041000406.class);
		return esbRep_30041000406;
	}
	/** 
	* @Title: initRecord 
	* @Description: 登记单笔付款交易信息
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return SinglCrdtInfoModel 返回类型 
	* @throws 
	*/
	private SinglCrdtInfoModel initRecord(SER_REQ_DATA reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		
		Map<String,Object> headMap = reqDto.getHead();
		Map<String,Object> bodyMap = reqDto.getBody();
		//公积金节点编号
		String txUnitNo = headMap.get(TXUNITNO).toString();	
		//交易机构
        String txBrno =null ;
        //开户机构
    	String upBrno = null;
        try(Jedis jedis = myJedis.connect()){
        	txBrno = jedis.get(BRTEL_PREFIX+txUnitNo+"_TXBRNO");
        	upBrno = jedis.get(BRTEL_PREFIX+txUnitNo+"_UPBRNO");
        }
		SinglCrdtInfoModel record = new SinglCrdtInfoModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
		record.setTx_brno(txBrno);		
		record.setUp_brno(upBrno);	
		record.setSnd_date(headMap.get(SENDDATE).toString()); //发送方日期
		record.setSnd_time(headMap.get(SENDTIME).toString());//'发送方时间' ,
		record.setSnd_seqno(headMap.get(SENDSEQNO).toString());// '发送方流水' ,
		record.setSnd_unitno(txUnitNo);// '公积金机构号' ,
		record.setSnd_node(headMap.get(SENDNODE).toString());//'发送方节点号' ,
		record.setRcv_node(headMap.get(RECEIVENODE).toString());//'接收方节点号' ,
		record.setBdc_date(headMap.get(BDCDATE).toString());// '结算系统日期' ,
		record.setBdc_time(headMap.get(BDCTIME).toString());// '结算系统时间' ,
		record.setBdc_seqno(headMap.get(BDCSEQNO).toString());// '结算系统流水' ,
		record.setCus_no(headMap.get(CUSTNO).toString());// '银行客户编号' ,
		record.setOpr_no(headMap.get(OPERNO).toString());//'操作员编号' ,
		record.setCurr_no(bodyMap.get(CURRNO).toString());//'币种，156' ,
		record.setCurr_iden(bodyMap.get(CURRIDEN).toString());//'钞汇鉴别：1、钞；2、汇' ,
		record.setSettle_type(bodyMap.get(SETTLETYPE).toString());//'结算模式：1、同行；2、跨行实时；3、跨行非实时' ,
		record.setBus_type(bodyMap.get(BUSTYPE).toString());//'业务类型' ,
		record.setDe_acctno(bodyMap.get(DEACCTNO).toString());//'付方账号' ,
		record.setDe_acctname(bodyMap.get(DEACCTNAME).toString());//'付方户名' ,
		record.setDe_acctclass(bodyMap.get(DEACCTCLASS).toString());//'付方账户类别：1、对私；2、对公' ,
		record.setCap_amt(bodyMap.get(CAPAMT).toString());//'本金发生额' ,
		record.setDe_intacctno(bodyMap.get(DEINTACCTNO).toString());//'付息户账号 ',
		record.setDe_intacctname(bodyMap.get(DEINTACCTNAME).toString());//'付息户户名 ',
		record.setDe_intacctclass(bodyMap.get(DEINTACCTCLASS).toString());//'付息户类别:1、对私；2、对公',
		record.setDe_intcracct(bodyMap.get(DEINTCRACCT).toString());//'利息收方账号',
		record.setInt_amt(bodyMap.get(INTAMT).toString());//'利息发生额',
		record.setCr_acctno(bodyMap.get(CRACCTNO).toString());//'收方账号',
		record.setCr_acctname(bodyMap.get(CRACCTNAME).toString());//'收方户名 ',
		record.setCr_acctclass(bodyMap.get(CRACCTCLASS).toString());//'收方账户类别:1、对私；2、对公',
		record.setCr_bankclass(bodyMap.get(CRBANKCLASS).toString());//'收方账户行别:0、本行；1、跨行',
		record.setCr_bankname(bodyMap.get(CRBANKNAME).toString());//'收方行名 ',
		record.setCr_chgno(bodyMap.get(CRCHGNO).toString());//'收方联行号 ',
		record.setAmt(bodyMap.get(AMT).toString());//'交易金额' ,
		record.setRef_acctno(bodyMap.get(REFACCTNO).toString());//'业务明细账号', 
		record.setRef_seqno1(bodyMap.get(REFSEQNO1).toString());//'业务明细流水号 1',
		record.setRef_seqno2(bodyMap.get(REFSEQNO2).toString());//'业务明细流水号 2',
		record.setSummary(bodyMap.get(SUMMARY).toString());//'摘要', 
		record.setRemark(bodyMap.get(REMARK).toString());//'备注'
		
		singleCrdtService.CrdtInfoInit(record);
		return record;
	}
	
	/** 
	* @Title: updateCapRecord 
	* @Description: 更新本金记账信息
	* @param @param reqDto
	* @param @param txStatus 交易状态
	* @param @param capCode 本金记账状态
	* @param @param capMsg 本金记账状态信息
	* @param @param capSeqno 本金记账流水号
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return SinglCrdtCapUpdModel    返回类型 
	* @throws 
	*/
	private SinglCrdtCapUpdModel updateCapRecord(SER_REQ_DATA reqDto,String txStatus,String capCode,String capMsg,String capSeqno) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		SinglCrdtCapUpdModel record = new SinglCrdtCapUpdModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
		record.setTxStatus(txStatus);	
		record.setCapHostcode(capCode);
		record.setCapHostmsg(capMsg);
		record.setCapSeqno(capSeqno);
		singleCrdtService.CrdtInfoHostCapUpd(record);
		return record;
	}
	/** 
	* @Title: queryDeAccount 
	* @Description: 验证付款账户账号和户名是否一致
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30013000510    返回类型 
	* @throws 
	*/
	private ESB_REP_30013000510 queryDeAccount(SER_REQ_DATA reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		Map<String,Object> headMap = reqDto.getHead();
		Map<String,Object> bodyMap = reqDto.getBody();
		//公积金节点编号
		String txUnitNo = headMap.get(TXUNITNO).toString();
		 //交易机构
        String txBrno =null ;
        //柜员号
    	String txTel = null;
        try(Jedis jedis = myJedis.connect()){
        	txBrno = jedis.get(BRTEL_PREFIX+txUnitNo+"_TXBRNO");
        	txTel = jedis.get(BRTEL_PREFIX+txUnitNo+"_TXTEL");
        }

		ESB_REQ_30013000510 esbReq_30013000510 = new ESB_REQ_30013000510(myLog, reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30013000510.getReqSysHead(),reqDto).setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30013000510.setReqSysHead(reqSysHead);

		ESB_REQ_30013000510.REQ_BODY reqBody_30013000510=esbReq_30013000510.getReqBody();
        //付款账号
		reqBody_30013000510.setBaseAcctNo(bodyMap.get(DEACCTNO).toString());
		//币种
		reqBody_30013000510.setCcy("CNY");
		//钞汇鉴别1：钞 2：汇
		reqBody_30013000510.setCurrIden("1");
		//是否对私客户
		reqBody_30013000510.setIsIndividual("N");

		ESB_REP_30013000510 esb_rep_30013000510=IForwardToESBService.sendToESB(esbReq_30013000510,reqBody_30013000510,ESB_REP_30013000510.class);

		return esb_rep_30013000510;
	}
	/** 
	* @Title: queryCrAccount 
	* @Description: 验证收款账户账号与户名是否一致
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30013000510    返回类型 
	* @throws 
	*/
	private ESB_REP_30013000510 queryCrAccount(SER_REQ_DATA reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		Map<String,Object> headMap = reqDto.getHead();
		Map<String,Object> bodyMap = reqDto.getBody();
		//公积金节点编号
		String txUnitNo = headMap.get(TXUNITNO).toString();		
		//交易机构
        String txBrno =null ;
        //柜员号
    	String txTel = null;
        try(Jedis jedis = myJedis.connect()){
        	txBrno = jedis.get(BRTEL_PREFIX+txUnitNo+"_TXBRNO");
        	txTel = jedis.get(BRTEL_PREFIX+txUnitNo+"_TXTEL");
        }

		ESB_REQ_30013000510 esbReq_30013000510 = new ESB_REQ_30013000510(myLog, reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30013000510.getReqSysHead(),reqDto).setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30013000510.setReqSysHead(reqSysHead);

		ESB_REQ_30013000510.REQ_BODY reqBody_30013000510=esbReq_30013000510.getReqBody();
        //收款账号
		reqBody_30013000510.setBaseAcctNo(bodyMap.get(CRACCTNO).toString());
		//币种
		reqBody_30013000510.setCcy("CNY");
		//钞汇鉴别1：钞 2：汇
		reqBody_30013000510.setCurrIden("1");
		//是否对私客户
		reqBody_30013000510.setIsIndividual("N");

		ESB_REP_30013000510 esb_rep_30013000510=IForwardToESBService.sendToESB(esbReq_30013000510,reqBody_30013000510,ESB_REP_30013000510.class);

		return esb_rep_30013000510;
	}
	
}
