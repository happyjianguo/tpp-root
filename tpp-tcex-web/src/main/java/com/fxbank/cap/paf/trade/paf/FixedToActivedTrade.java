package com.fxbank.cap.paf.trade.paf;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.model.ses.*;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30011001201.Tran;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: FixedToActivedTrade
 * @Description: 定期转活期（BDC212）
 * @author LiuS
 * @date 2018年10月8日 下午5:01:21
 */
@Service("BDC212")
public class FixedToActivedTrade implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(FixedToActivedTrade.class);

    @Reference(version = "1.0.0")
    private com.fxbank.cap.esb.service.IForwardToESBService IForwardToESBService;

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

    //定期账号
    private static final String FIXEDACCTNO="FixedAcctNo";
    //币种
    private static final String CURRNO="CurrNo";
    //支取金额
    private static final String DRAWAMT="DrawAmt";
    //册号
    private static final String BOOKNO="BookNo";
    //活期账户
    private static final String ACTIVEDACCTNO="ActivedAcctNo";




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

        String hostSeqNo=null;

        ESB_REP_30011001201 esb_rep_30011001201=reqEsb(reqDto);
        hostSeqNo=esb_rep_30011001201.getRepBody().getReference();
        //银行主机流水号
        repDto.getBody().getField().add(new FIELD("HostSeqNo",hostSeqNo));
        myLog.info(logger,"定转活，账号："+reqDto.getBody().get(FIXEDACCTNO).toString()+"返回主机流水号："+hostSeqNo);
        return repDto;
    }

    /**
     * @Title: reqEsb
     * @Description: 将公积金报文转换发给esb进行定转活操作，并获得返回结果
     * @param @param SER_REQ_DATA
     * @param @throws SysTradeExecuteException    设定文件
     * @return ESB_REP_30011001201    返回类型
     * @throws
     */
    private ESB_REP_30011001201 reqEsb(SER_REQ_DATA reqDto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        Map<String,Object> headMap = reqDto.getHead();
        Map<String,Object> bodyMap = reqDto.getBody();

        //公积金节点编号
        String txUnitNo = headMap.get(TXUNITNO).toString();
        //交易机构
        String txBrno =null ;
        //柜员号
    	String txTel = null;
    	String prodTypeA = null;
    	String prodTypeB = null;
        try(Jedis jedis = myJedis.connect()){
        	txBrno = jedis.get(BRTEL_PREFIX+txUnitNo+"_TXBRNO");
        	txTel = jedis.get(BRTEL_PREFIX+txUnitNo+"_TXTEL");
        	prodTypeA = jedis.get(BRTEL_PREFIX+txUnitNo+"_PROD_TYPE_A");
        	prodTypeB = jedis.get(BRTEL_PREFIX+txUnitNo+"_PROD_TYPE_B");
        }

        ESB_REQ_30011001201 esbReq_30011001201 = new ESB_REQ_30011001201(myLog, reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
        ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011001201.getReqSysHead(),reqDto).setBranchId(txBrno).setUserId(txTel).build();
        esbReq_30011001201.setReqSysHead(reqSysHead);

        ESB_REQ_30011001201.REQ_BODY reqBody_30011001201=esbReq_30011001201.getReqBody();
        //账号
        reqBody_30011001201.setBaseAcctNo(bodyMap.get(FIXEDACCTNO).toString());
        //账户序号
        reqBody_30011001201.setAcctSeqNo(bodyMap.get(BOOKNO).toString());
        //币种
        reqBody_30011001201.setCcy("CNY");
        //产品类型
        reqBody_30011001201.setProdType(prodTypeA);
        //支取方式
        reqBody_30011001201.setWithdrawalType("S");
        //支取金额
        reqBody_30011001201.setPretermAmt(bodyMap.get(DRAWAMT).toString());

        //先调用esb14000209接口,获得定转活需要的参数
        ESB_REQ_30013002401 esbReq_30013002401=new ESB_REQ_30013002401(myLog,reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
        ESB_REQ_SYS_HEAD reqSysHead0209=new EsbReqHeaderBuilder(esbReq_30013002401.getReqSysHead(),reqDto).setBranchId(txBrno).setUserId(txTel).build();
        esbReq_30013002401.setReqSysHead(reqSysHead0209);
        ESB_REQ_30013002401.REQ_BODY reqBody_30013002401=esbReq_30013002401.getReqBody();
        //账号
        reqBody_30013002401.setBaseAcctNo(bodyMap.get(FIXEDACCTNO).toString());
        //产品类型
        reqBody_30013002401.setProdType(prodTypeB);
        //币种
        reqBody_30013002401.setCcy("CNY");
        //账户序号
        reqBody_30013002401.setAcctSeqNo(bodyMap.get(BOOKNO).toString());
        //交易金额
        reqBody_30013002401.setTranAmt(bodyMap.get(DRAWAMT).toString());
        //调用esb
        ESB_REP_30013002401 esb_rep_30013002401=IForwardToESBService.sendToESB(esbReq_30013002401,reqBody_30013002401,ESB_REP_30013002401.class);

        //继续给主接口赋值
        //净利息
        reqBody_30011001201.setNetInterestAmt(esb_rep_30013002401.getRepBody().getNetInterestAmt());
        //应付利息
        reqBody_30011001201.setPayInterest(esb_rep_30013002401.getRepBody().getPayInterest());
        //已付利息
        reqBody_30011001201.setPastInterest(esb_rep_30013002401.getRepBody().getPastInterest());
        //计提利息调整
        reqBody_30011001201.setAccrIntAdj(esb_rep_30013002401.getRepBody().getIntAdj());
        //
        ESB_REQ_30011001201.Tran tran=new ESB_REQ_30011001201.Tran();
        tran.setTranType("4186");
        double zqje=Double.parseDouble(bodyMap.get(DRAWAMT).toString());
        double jlx=Double.parseDouble(esb_rep_30013002401.getRepBody().getNetInterestAmt());
        double heji=zqje+jlx;
//        DecimalFormat df = new DecimalFormat("#.00");
        //交易金额
        tran.setTranAmt(String.valueOf(heji));
        //对方账号
        tran.setOthBaseAcctNo(bodyMap.get(ACTIVEDACCTNO).toString());
        //对方账户币种
        tran.setOthAcctCcy("CNY");
        //对方账户序号
        tran.setOthAcctSeqNo("0");
        List<Tran> list = new ArrayList<Tran>();
        list.add(tran);
        reqBody_30011001201.setTranArray(list);

        ESB_REP_30011001201 esb_rep_30011001201=IForwardToESBService.sendToESB(esbReq_30011001201,reqBody_30011001201,ESB_REP_30011001201.class);
        return esb_rep_30011001201;
    }
}
