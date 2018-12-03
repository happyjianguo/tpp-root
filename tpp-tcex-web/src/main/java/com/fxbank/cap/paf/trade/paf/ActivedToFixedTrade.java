package com.fxbank.cap.paf.trade.paf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.model.ses.ESB_REP_30012000102;
import com.fxbank.cap.esb.model.ses.ESB_REP_30013000510;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30012000102;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30013000510;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.paf.dto.SER_REP_BDC;
import com.fxbank.cap.paf.dto.SER_REQ_DATA;
import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.model.FIELD;
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
* @ClassName: ActivedToFixedTrade 
* @Description: 活期转定期（BDC211） 
* @author DuZhenduo
* @date 2018年10月8日 下午5:01:21 
*  
*/
@Service("BDC211")
public class ActivedToFixedTrade implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(ActivedToFixedTrade.class);

	@Reference(version = "1.0.0")
	private IForwardToESBService IForwardToESBService;
	
	@Resource
	private MyJedis myJedis;
	
	@Resource
	private LogPool logPool;
	
	private static final String BRTEL_PREFIX = "paf_branch.";
	private final static String COMMON_PREFIX = "paf_common.";
	
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
	//活期账号
	private static final String ACTIVEDACCTNO = "ActivedAcctNo";
	//活期户名
	private static final String ACTIVEDACCTNAME = "ActivedAcctName";
	//定期账号
	private static final String FIXEDACCTNO = "FixedAcctNo";
	//定期户名
	private static final String FIXEDACCTNAME = "FixedAcctName";
	//存期 
	private static final String DEPOSITPERIOD = "DepositPeriod";
	//利率 
	private static final String INTERESTRATE = "InterestRate";
	//交易金额
	private static final String AMT = "Amt";
	//转存方式
	private static final String EXTENDDEPOSITTYPE = "ExtendDepositType";
	//唯一识别码
	private static final String IDCODE = "IDCode";
	//利息转存转入账号
	private static final String PARTEXTENDDEPOSITACCTNO = "PartExtendDepositAcctNo";
	//备注
	private static final String REMARK = "Remark";
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		SER_REQ_DATA reqDto = (SER_REQ_DATA) dto;
		SER_REP_BDC repDto = new SER_REP_BDC();
		//根据活期账户账号查询户名
		ESB_REP_30013000510 activedAccount = queryActivedAccount(reqDto);
		//活期账户名称
		String deAccountName = activedAccount.getRepBody().getAcctName();
		//验证活期户名与账号是否一致
		if(!deAccountName.equals(reqDto.getBody().get(ACTIVEDACCTNAME).toString())) {
		PafTradeExecuteException e = new PafTradeExecuteException(
				PafTradeExecuteException.PAF_E_10012);
				myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
					throw e;
				}
		String depositPeriod = reqDto.getBody().get(DEPOSITPERIOD).toString();
		String termRange = ",0,1,2,3,4,5,";
		if(termRange.indexOf(","+depositPeriod+",")<0) {
			PafTradeExecuteException e = new PafTradeExecuteException(
					PafTradeExecuteException.PAF_E_10014);
					myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
						throw e;
					}
		ESB_REP_30012000102 esbRep_30012000102 = establishFixedAccount(reqDto);
		ESB_REP_30012000102.REP_BODY repBody = esbRep_30012000102.getRepBody();
		myLog.info(logger, "活期转定期成功,活期账号"+reqDto.getBody().get(ACTIVEDACCTNO).toString()+
				"定期账号"+reqDto.getBody().get(FIXEDACCTNO).toString()+
				"定期册号"+repBody.getAcctSeqNo()+"流水号"+repBody.getReference());
		//定期账号
		repDto.getBody().getField().add(new FIELD("FixedAcctNo",repBody.getBaseAcctNo()));
	    //定期户名
		repDto.getBody().getField().add(new FIELD("FixedAcctName",repBody.getAcctName()));
		//定期册号
		repDto.getBody().getField().add(new FIELD("BookNo",repBody.getAcctSeqNo()));
		//定期笔号
		repDto.getBody().getField().add(new FIELD("BookListNo",""));
		//银行主机流水号
		repDto.getBody().getField().add(new FIELD("HostSeqNo",repBody.getReference()));
		
		return repDto;
	}
	
	/** 
	* @Title: registAccount 
	* @Description: 定期子账户开立
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30012000102    返回类型 
	* @throws 
	*/
	private ESB_REP_30012000102 establishFixedAccount(SER_REQ_DATA reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		Map<String,Object> headMap = reqDto.getHead();
		Map<String,Object> bodyMap = reqDto.getBody();
		//公积金节点编号
		String txUnitNo = headMap.get(TXUNITNO).toString();		
		//交易机构
        String txBrno =null ;
        //柜员号
    	String txTel = null;
    	String prodType = null;
        try(Jedis jedis = myJedis.connect()){
        	txBrno = jedis.get(BRTEL_PREFIX+txUnitNo+"_TXBRNO");
        	txTel = jedis.get(BRTEL_PREFIX+txUnitNo+"_TXTEL");
        	prodType = jedis.get(BRTEL_PREFIX+txUnitNo+"_PROD_TYPE_C");
        }
		
		ESB_REQ_30012000102 esbReq_30012000102 = new ESB_REQ_30012000102(myLog, reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30012000102.getReqSysHead(),reqDto).setBranchId(txBrno).setUserId(txTel).build(); 
		esbReq_30012000102.setReqSysHead(reqSysHead);
			
		ESB_REQ_30012000102.REQ_BODY reqBody_30012000102 = esbReq_30012000102.getReqBody();
		//定期账号
		reqBody_30012000102.setBaseAcctNo(bodyMap.get(FIXEDACCTNO).toString());
		//产品类型
		
		reqBody_30012000102.setProdType(prodType);
		//币种
		reqBody_30012000102.setCcy("CNY");
		/**
		 * 公积金中心 0：本金+利息自动转存1：不自动转存2：本金自动转存
		 * 银行核心 O-本息自动转存N-不自动转存W-本金自动转存
		 */
		String paf_autoRenewRollover = bodyMap.get(EXTENDDEPOSITTYPE).toString();
		String autoRenewRollover = "";
		if("0".equals(paf_autoRenewRollover)) {
			autoRenewRollover = "O";
		}else if("1".equals(paf_autoRenewRollover)) {
			autoRenewRollover = "N";
		}else if("2".equals(paf_autoRenewRollover)) {
			autoRenewRollover = "W";
		}
		reqBody_30012000102.setAutoRenewRollover(autoRenewRollover);
		//交易类型
		reqBody_30012000102.setTranType("4186");
		//交易金额
		reqBody_30012000102.setTranAmt(bodyMap.get(AMT).toString());
		/**
		 * 0：三个月、1半年、2：一年、3：两年、4：三年、5：五年
		 * 期限类型D-日M-月Y-年
		 */
		int depositPeriod = Integer.parseInt(bodyMap.get(DEPOSITPERIOD).toString());
		String term = "",termType = "";
		switch(depositPeriod) {
		case 0:
			term = "3";
			termType = "M";
			break;
		case 1:
			term = "6";
			termType = "M";
			break;
		case 2:
			term = "1";
			termType = "Y";
			break;
		case 3:
			term = "2";
			termType = "Y";
			break;
		case 4:
			term = "3";
			termType = "Y";
			break;
		case 5:
			term = "5";
			termType = "Y";
			break;	
		}
		//存期
		reqBody_30012000102.setTerm(term);
		//存期类型
		reqBody_30012000102.setTermType(termType);
		//结算账户
		reqBody_30012000102.setSettleBaseAcctNo(bodyMap.get(PARTEXTENDDEPOSITACCTNO).toString());
		//结算账户序号
		reqBody_30012000102.setSettleAcctSeqNo("0");
		//INT（正常利息),WYINT(逾期利息）,PDUE(提前支取利息)三个数组
		ESB_REQ_30012000102.Int int0 = new ESB_REQ_30012000102.Int();
		ESB_REQ_30012000102.Int int1 = new ESB_REQ_30012000102.Int();
		ESB_REQ_30012000102.Int int2 = new ESB_REQ_30012000102.Int();
		int0.setIntClass("INT");
		int1.setIntClass("WYINT");
		int2.setIntClass("PDUE");
		//唯一识别码
		String IDCode = bodyMap.get(IDCODE).toString();
		List<ESB_REQ_30012000102.Int> intList = new ArrayList<ESB_REQ_30012000102.Int>();
		String interestRate = bodyMap.get(INTERESTRATE).toString();
		if(!"".equals(interestRate)) {
			int0.setAcctFixedRate(interestRate);
			int0.setRealRate(interestRate);
			int1.setAcctFixedRate(interestRate);
			int1.setRealRate(interestRate);
			int2.setAcctFixedRate(interestRate);
			int2.setRealRate(interestRate);
		}
		String int0_acctFixedRate = null;
		String int0_realRate = null;
		String int1_acctFixedRate = null;
		String int1_realRate = null;
		String int2_acctFixedRate = null;
		String int2_realRate = null;
		 try(Jedis jedis = myJedis.connect()){
			 int0_acctFixedRate = jedis.get(BRTEL_PREFIX+txUnitNo+"_"+IDCode+"_INT");
			 int0_realRate = jedis.get(BRTEL_PREFIX+txUnitNo+"_"+IDCode+"_INT");
			 int1_acctFixedRate = jedis.get(BRTEL_PREFIX+txUnitNo+"_"+IDCode+"_WYINT");
			 int1_realRate = jedis.get(BRTEL_PREFIX+txUnitNo+"_"+IDCode+"_WYINT");
			 int2_acctFixedRate = jedis.get(BRTEL_PREFIX+txUnitNo+"_"+IDCode+"_PDUE");
			 int2_realRate = jedis.get(BRTEL_PREFIX+txUnitNo+"_"+IDCode+"_PDUE");
	        }
		if(!"".equals(IDCode)) {
			int0.setAcctFixedRate(int0_acctFixedRate);
			int0.setRealRate(int0_realRate);
			int1.setAcctFixedRate(int1_acctFixedRate);
			int1.setRealRate(int1_realRate);
			int2.setAcctFixedRate(int2_acctFixedRate);
			int2.setRealRate(int2_realRate);
		}
		intList.add(int0);
		intList.add(int1);
		intList.add(int2);
		//利息明细数组
		reqBody_30012000102.setIntArray(intList);
		//活期账号
		reqBody_30012000102.setOthBaseAcctNo(bodyMap.get(ACTIVEDACCTNO).toString());
		//记账渠道类型
		reqBody_30012000102.setChannelType("GJ");
		
		ESB_REP_30012000102 esbRep_30012000102 = IForwardToESBService.sendToESB(esbReq_30012000102,reqBody_30012000102, ESB_REP_30012000102.class);
		return esbRep_30012000102;
	}
	/** 
	* @Title: queryDeAccount 
	* @Description: 验证活期账户账号和户名是否一致
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30013000510    返回类型 
	* @throws 
	*/
	private ESB_REP_30013000510 queryActivedAccount(SER_REQ_DATA reqDto) throws SysTradeExecuteException {
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
        //账号
		reqBody_30013000510.setBaseAcctNo(bodyMap.get(ACTIVEDACCTNO).toString());
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
