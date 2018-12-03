package com.fxbank.cap.paf.trade.paf;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.fxbank.cap.esb.model.ses.ESB_REP_30013000510;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30013000510;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.paf.dto.SER_REP_BDC;
import com.fxbank.cap.paf.dto.SER_REQ_DATA;
import com.fxbank.cap.paf.model.FIELD;
import com.fxbank.cap.paf.model.FIELDS_LIST_INNER;
import com.fxbank.cap.paf.model.FIELDS_LIST_OUTER;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;

import redis.clients.jedis.Jedis;


/** 
* @ClassName: FixedBalanceQryTrade 
* @Description: 定期余额查询
* @author DuZhenduo
* @date 2018年10月18日 下午2:10:23 
*  
*/
@Service("BDC220")
public class FixedBalanceQryTrade implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(FixedBalanceQryTrade.class);

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
	//账号
	private static final String ACCTNO = "AcctNo";
	//定期类型
	private static final String FIXEDTYPE = "FixedType";
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		SER_REQ_DATA reqDto = (SER_REQ_DATA) dto;
		SER_REP_BDC repDto = new SER_REP_BDC();

		ESB_REP_30013000510 esbRep_30013000510 = fixedAcctQuery(reqDto);
        //循环报文体节点名称
		FIELDS_LIST_OUTER outer = new FIELDS_LIST_OUTER("SUMMARY");

		List<ESB_REP_30013000510.AcctInfo> acctInfoList=esbRep_30013000510.getRepBody().getAcctInfoArray();
		List<FIELDS_LIST_INNER> innerList = new ArrayList<FIELDS_LIST_INNER>();
		for(int i=0;i<acctInfoList.size();i++){
			//定期账户子账号
			FIELDS_LIST_INNER inner = new FIELDS_LIST_INNER(String.valueOf(i));
			//存折册号
			inner.getField().add(new FIELD("BookNo",acctInfoList.get(i).getAcctSeqNo()));
			//本册笔号
			inner.getField().add(new FIELD("BookListNo",""));
			//币种
			inner.getField().add(new FIELD("CurrNo","156"));
			//存款期限
			String term = acctInfoList.get(i).getTerm();
			/**
			 * 存款期限(按天计算，一月计 30 天，一年计 360天，以此类推)
			 * 存款期限类型D-日M-月Y-年
			 */
			String termType = acctInfoList.get(i).getTermType();
			//存期（返回给公积金中心）
			int depositDays = 0;
			String depositPeriod = "";
			try {
				if("D".equals(termType)) {
					depositDays = Integer.parseInt(term);
				}else if("M".equals(termType)) {
					depositDays = Integer.parseInt(term) * 30;
				}else if("Y".equals(termType)) {
					depositDays = Integer.parseInt(term) * 360;
				}
				depositPeriod = String.valueOf(depositDays);
			}catch(Exception e) {
				myLog.error(logger, "定期余额查询存期和存期类型格式不正确，"
						+ "账号"+reqDto.getBody().get(ACCTNO).toString()
						+"册号"+acctInfoList.get(i).getAcctSeqNo());
			}
			inner.getField().add(new FIELD("DepositPeriod",depositPeriod));
			//到期日期
			inner.getField().add(new FIELD("DepositEndDate",acctInfoList.get(i).getMaturityDate()));
			//开户日期
			inner.getField().add(new FIELD("BegDate",acctInfoList.get(i).getAcctOpenDate()));
			//开户金额
			inner.getField().add(new FIELD("BegAmt",acctInfoList.get(i).getOpenBal()));
			//实际金额
			inner.getField().add(new FIELD("DrawAmt",acctInfoList.get(i).getBalance()));
			//结清利息
			inner.getField().add(new FIELD("Interest",acctInfoList.get(i).getInterest()));
			//冻结状态0：正常1：冻结3：部分冻结4：其它
			inner.getField().add(new FIELD("FreezeType",acctInfoList.get(i).getFreezeType()));
			//挂失标志 0：正常1：挂失
			inner.getField().add(new FIELD("LossFlag",acctInfoList.get(i).getLossFlag()));
			//账户状态 0：正常1：销户2：没收3：上缴9：其他
			inner.getField().add(new FIELD("AcctStatus","0"));
			//钞汇鉴别 1：钞2：汇
			inner.getField().add(new FIELD("CurrIden","1"));
			//利率
			inner.getField().add(new FIELD("InterestRate",acctInfoList.get(i).getRealRate()));
			innerList.add(inner);
		}
		//总笔数 此账户下定期存款的笔数
		repDto.getBody().getField().add(new FIELD("Num",String.valueOf(acctInfoList.size())));
		myLog.info(logger, "定期余额查询成功，账号"
		+reqDto.getBody().get(ACCTNO).toString()+"子账户数"
				+String.valueOf(acctInfoList.size()));
		outer.setField_list(innerList);
		repDto.getBody().setField_list(outer);
		return repDto;
	}
	
	/** 
	* @Title: fixedAcctQuery 
	* @Description: 定期账户余额查询 
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30013000510    返回类型 
	* @throws 
	*/
	private ESB_REP_30013000510 fixedAcctQuery(SER_REQ_DATA reqDto) throws SysTradeExecuteException {
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
        //定期账号
		reqBody_30013000510.setBaseAcctNo(bodyMap.get(ACCTNO).toString());
		//币种
		reqBody_30013000510.setCcy("CNY");
		//钞汇鉴别1：钞 2：汇
		reqBody_30013000510.setCurrIden("1");
		//是否对私客户
		reqBody_30013000510.setIsIndividual("N");
		//定期类型0：定期 1：通知
		reqBody_30013000510.setFixedType(bodyMap.get(FIXEDTYPE).toString());

		ESB_REP_30013000510 esb_rep_30013000510=IForwardToESBService.sendToESB(esbReq_30013000510,reqBody_30013000510,ESB_REP_30013000510.class);

		return esb_rep_30013000510;
	}
	
}
