package com.fxbank.cap.paf.trade.paf;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.model.ses.ESB_REP_30013000201;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30013000201;
import com.fxbank.cap.paf.dto.SER_REP_BDC;
import com.fxbank.cap.paf.dto.SER_REQ_DATA;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName: ActivedBalanceQryTrade
 * @Description: 活期账户实时余额查询（BDC209）
 * @author LiuS
 * @date 2018年10月8日 下午5:01:21
 */
@Service("BDC209")
public class ActivedBalanceQryTrade implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(ActivedBalanceQryTrade.class);

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

    private static final String ACCTNO="AcctNo";


    /**
     * @Title: execute
     * @Description: 接收公积金报文并返回内容
     * @param @param dto
     * @param @return
     * @throws
     */
    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        SER_REQ_DATA reqDto = (SER_REQ_DATA) dto;
        SER_REP_BDC repDto = new SER_REP_BDC();

        //返回内容
        String acctNo=null;
        String acctName=null;
        String acctBal=null;
        String acctRestBal=null;
        String acctOverBal=null;
        String acctStatus=null;
        //调用核心
        ESB_REP_30013000201 esb_rep_30013000201=reqEsb(reqDto);
        acctNo=esb_rep_30013000201.getRepBody().getBaseAcctNo();
        acctName=esb_rep_30013000201.getRepBody().getAcctName();

        acctBal=esb_rep_30013000201.getRepBody().getBalance();
        acctRestBal=esb_rep_30013000201.getRepBody().getActualBal();
        acctOverBal="0";//可透支额0
        if(esb_rep_30013000201.getRepBody().getAcctStatus().equals("A")){
            acctStatus="0";
        }else {
            acctStatus="4";
        }
        //账号
        repDto.getBody().getField().add(new FIELD("AcctNo",acctNo));
        //账户户名
        repDto.getBody().getField().add(new FIELD("AcctName",acctName));
        //账户余额
        repDto.getBody().getField().add(new FIELD("AcctBal",acctBal));
        //账户可用余额
        repDto.getBody().getField().add(new FIELD("AcctRestBal",acctRestBal));
        //账户透支额
        repDto.getBody().getField().add(new FIELD("AcctOverBal",acctOverBal));
        //账户状态，0：正常，4：销户
        repDto.getBody().getField().add(new FIELD("AcctStatus",acctStatus));

        myLog.info(logger,"活期账户查询，账号："+reqDto.getBody().get(ACCTNO).toString()+"返回账户状态："+acctStatus);
        return repDto;
    }


    /**
     * @Title: reqEsb
     * @Description: 将公积金报文转换发给esb进行活期账户余额查询，并获得返回结果
     * @param @param SER_REQ_DATA
     * @param @throws SysTradeExecuteException    设定文件
     * @return ESB_REP_30013000201    返回类型
     * @throws
     */
    private ESB_REP_30013000201 reqEsb(SER_REQ_DATA reqDto) throws SysTradeExecuteException {
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

        ESB_REQ_30013000201 esbReq_30013000201 = new ESB_REQ_30013000201(myLog, reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
        ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30013000201.getReqSysHead(),reqDto).setBranchId(txBrno).setUserId(txTel).build();
        esbReq_30013000201.setReqSysHead(reqSysHead);

        ESB_REQ_30013000201.REQ_BODY reqBody_30013000201=esbReq_30013000201.getReqBody();
        //账号
        reqBody_30013000201.setBaseAcctNo(bodyMap.get(ACCTNO).toString());
        //账户类型
        reqBody_30013000201.setAcctType("C");

        ESB_REP_30013000201 esb_rep_30013000201=IForwardToESBService.sendToESB(esbReq_30013000201,reqBody_30013000201,ESB_REP_30013000201.class);

        return esb_rep_30013000201;
    }


}
