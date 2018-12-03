package com.fxbank.cap.paf.trade.paf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.model.ses.ESB_REP_30013000305;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30013000305;
import com.fxbank.cap.paf.dto.SER_REP_BDC;
import com.fxbank.cap.paf.dto.SER_REQ_DATA;
import com.fxbank.cap.paf.model.FIELD;
import com.fxbank.cap.paf.model.FIELDS_LIST_INNER;
import com.fxbank.cap.paf.model.FIELDS_LIST_OUTER;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;

import redis.clients.jedis.Jedis;

@Service("BDC210")
public class ActivedDetailQryTrade implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(ActivedDetailQryTrade.class);

    @Reference(version = "1.0.0")
    private com.fxbank.cap.esb.service.IForwardToESBService IForwardToESBService;

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
    //开始记录数
    private static final String BEGINREC = "BeginRec";
    //一次查询最大记录数
    private static final String MAXREC = "MaxRec";
    //币种 
    private static final String CURRNo = "CurrNo";
    //钞汇鉴别 1钞2汇
    private static final String CURRIDEN = "CurrIden";
    //账号类型0：内部账户1：银行账户
    private static final String ACCTTYPE = "AcctType";
    //账号
    private static final String ACCTNO="AcctNo";
    
    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        SER_REQ_DATA reqDto = (SER_REQ_DATA) dto;
        SER_REP_BDC repDto = new SER_REP_BDC();

        //调用核心
        ESB_REP_30013000305 esb_rep_30013000305=queryActivedDetail(reqDto);
      //循环报文体节点名称
      	FIELDS_LIST_OUTER outer = new FIELDS_LIST_OUTER("SUMMARY");
      	List<ESB_REP_30013000305.TranHist> tranHistList = esb_rep_30013000305.getRepBody().getTranHistArray();
      	List<FIELDS_LIST_INNER> innerList = new ArrayList<FIELDS_LIST_INNER>();
        for(int i=0;i<tranHistList.size();i++){
        FIELDS_LIST_INNER inner = new FIELDS_LIST_INNER(String.valueOf(i));
      	inner.getField().add(new FIELD("HostDate",tranHistList.get(i).getTranDate()));
      	inner.getField().add(new FIELD("HostTime",tranHistList.get(i).getTranTime()));
      	inner.getField().add(new FIELD("AcctDetailSerialNo",String.valueOf(i+1)));
      	inner.getField().add(new FIELD("AcctNo",tranHistList.get(i).getBaseAcctNo()));
      	inner.getField().add(new FIELD("AcctName",tranHistList.get(i).getAcctName()));
      	inner.getField().add(new FIELD("CurrNo","156"));
      	inner.getField().add(new FIELD("CurrIden",tranHistList.get(i).getCurrIden()));
      	inner.getField().add(new FIELD("DeAmt",tranHistList.get(i).getDrAmount()));
      	inner.getField().add(new FIELD("CrAmt",tranHistList.get(i).getCrAmount()));
      	inner.getField().add(new FIELD("AcctBal",tranHistList.get(i).getAcctBal()));
      	inner.getField().add(new FIELD("VchNo",tranHistList.get(i).getVoucherNo()));
      	inner.getField().add(new FIELD("VchType",tranHistList.get(i).getDocType()));
      	inner.getField().add(new FIELD("Remark",tranHistList.get(i).getRemark()));
      	inner.getField().add(new FIELD("HostSeqNo",tranHistList.get(i).getReference()));
      	inner.getField().add(new FIELD("Summary",tranHistList.get(i).getTranNote()));
        innerList.add(inner);
      	}
      	myLog.info(logger, "活期账户当日明细查询成功，账号"
      	+reqDto.getBody().get(ACCTNO).toString()+"明细数"
      	+String.valueOf(tranHistList.size()));
      	outer.setField_list(innerList);
      	repDto.getBody().setField_list(outer);        
      	
      	repDto.getHead().getField().add(new FIELD("BkTotNum",esb_rep_30013000305.getRepAppHead().getTotalRows()));
      	repDto.getHead().getField().add(new FIELD("BkRecNum",String.valueOf(tranHistList.size())));
        return repDto;
    }



    private ESB_REP_30013000305 queryActivedDetail(SER_REQ_DATA reqDto) throws SysTradeExecuteException {
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

        ESB_REQ_30013000305 esbReq_30013000305 = new ESB_REQ_30013000305(myLog, reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
        ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30013000305.getReqSysHead(),reqDto).setBranchId(txBrno).setUserId(txTel).build();
        esbReq_30013000305.setReqSysHead(reqSysHead);
        
        ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();
        reqAppHead.setPgupOrPgnd("1");		//一直下翻
        String maxRec = "300";		//默认一页显示300条
        if(headMap.get(MAXREC)!=null&&!"".equals(headMap.get(MAXREC))){
        	maxRec = headMap.get(MAXREC).toString();
        }
        reqAppHead.setTotalNum(maxRec);
        String beginRec = "1";		//默认从第一条开始
        if(headMap.get(BEGINREC)!=null&&!"".equals(headMap.get(BEGINREC))){
        	beginRec = headMap.get(BEGINREC).toString();
        }
        Integer iBeginRec = Integer.valueOf(beginRec)-1<0?0:Integer.valueOf(beginRec)-1;
        reqAppHead.setCurrentNum(String.valueOf(iBeginRec));
        reqAppHead.setTotalFlag("E");		//总是查询汇总
        esbReq_30013000305.setReqAppHead(reqAppHead);

        ESB_REQ_30013000305.REQ_BODY reqBody_30013000305=esbReq_30013000305.getReqBody();
        //账号
        reqBody_30013000305.setBaseAcctNo(bodyMap.get(ACCTNO).toString());
        //
        reqBody_30013000305.setCcy("CNY");
        String currentDate = reqDto.getSysDate().toString();
        reqBody_30013000305.setStartDate(currentDate);
        reqBody_30013000305.setEndDate(currentDate);
        reqBody_30013000305.setCurrIden(bodyMap.get(CURRIDEN).toString());
        //账户类型
        reqBody_30013000305.setAcctType(bodyMap.get(ACCTTYPE).toString());

        ESB_REP_30013000305 esb_rep_30013000305=IForwardToESBService.sendToESB(esbReq_30013000305,reqBody_30013000305,ESB_REP_30013000305.class);

        return esb_rep_30013000305;
    }


}
