package com.fxbank.cap.paf.trade.paf;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fxbank.cap.esb.model.cnaps2.ESB_REP_30043000102;
import com.fxbank.cap.esb.model.cnaps2.ESB_REP_30043000103;
import com.fxbank.cap.esb.model.cnaps2.ESB_REQ_30043000102;
import com.fxbank.cap.esb.model.cnaps2.ESB_REQ_30043000103;
import com.fxbank.cap.esb.model.ses.ESB_REP_30043000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30043000101;
import com.fxbank.cap.paf.dto.SER_REP_BDC;
import com.fxbank.cap.paf.dto.SER_REQ_DATA;
import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.model.BatchCrdtMasterModel;
import com.fxbank.cap.paf.model.BatchLoanMasterModel;
import com.fxbank.cap.paf.model.FIELD;
import com.fxbank.cap.paf.model.QrySingleCapTradeModel;
import com.fxbank.cap.paf.service.IBatchCrdtService;
import com.fxbank.cap.paf.service.IBatchLoanService;
import com.fxbank.cap.paf.service.ISingleCrdtService;
import com.fxbank.cap.paf.service.ISingleDbitService;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.constant.CIP;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.pub.model.CipTraceLogData;

import com.fxbank.cip.pub.service.IPublicService;

import redis.clients.jedis.Jedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fxbank.cap.esb.service.IForwardToESBService;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * @ClassName: TranResultTrade
 * @Description: 交易结果查询（BDC219）
 * @author LiuS
 * @date 2018年10月8日 下午5:01:21
 */
@Service("BDC219")
public class TranResultTrade implements TradeExecutionStrategy {


    private static Logger logger = LoggerFactory.getLogger(TranResultTrade.class);

    @Reference(version = "1.0.0")
    private IForwardToESBService IForwardToESBService;

    @Reference(version = "1.0.0")
    private IPublicService IPublicService;
    
    @Reference(version = "1.0.0")
	private ISingleDbitService singleDbitService;
    
    @Reference(version = "1.0.0")
	private ISingleCrdtService singleCrdtService;
    
    @Reference(version = "1.0.0")
	private IBatchCrdtService batchCrdtService;
    
    @Reference(version = "1.0.0")
	private IBatchLoanService batchLoanService;

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

    //原交易日期
    private static final String OLDTXDATE="OldTxDate";
    //原交易流水号
    private static final String OLDSEQNO="OldSeqNo";


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
        Map<String,Object> bodyMap = reqDto.getBody();
        
        //返回内容
        String oldTxStatus = "1";
        String hostSeqNo = "";
        String oldBatchNo = "";
        
        //根据原交易日期和原交易流水号查询渠道流水号
        CipTraceLogData cipTraceLogData=new CipTraceLogData();
        cipTraceLogData.setOthDate(Integer.parseInt(bodyMap.get(OLDTXDATE).toString()));
        cipTraceLogData.setOthTraceon(bodyMap.get(OLDSEQNO).toString());
        List<CipTraceLogData> list = IPublicService.selectCipTraceLog(cipTraceLogData);
        if(list.size()==0) {
        	PafTradeExecuteException e = new PafTradeExecuteException(
					PafTradeExecuteException.PAF_E_10021);
			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
			throw e;
        }else if(list.size()==1) {
        	cipTraceLogData = list.get(0);
        	String txCode = cipTraceLogData.getTxCode();
        	switch(txCode) {
        	case "BDC201":
        	case "BDC202":
        		  String reqPack=cipTraceLogData.getReqPack();
                  JSONObject reqObject=JSONObject.parseObject(reqPack);
                  String settleType = reqObject.getJSONObject("body").getString("SettleType");
        	if("1".equals(settleType)) {
        		ESB_REP_30043000101 esb_rep_30043000101=innerTranResult(reqDto,cipTraceLogData);
                
                if(esb_rep_30043000101!=null) {
                    hostSeqNo=esb_rep_30043000101.getRepBody().getReference();
                    String acctResult=esb_rep_30043000101.getRepBody().getAcctResult();
                    if("00".equals(acctResult)) {
                        oldTxStatus = "0";
                    }else if("".equals(acctResult)){
                        oldTxStatus = "1";
                    }else{
                        oldTxStatus = "2";
                    }
                }else{
                    oldTxStatus="1";
                }
        	}else if("2".equals(settleType)||"3".equals(settleType)){
        		if("BDC201".equals(txCode)) {
        			ESB_REP_30043000102 esb_rep_30043000102=outerCrdtTranResult(reqDto,cipTraceLogData);
            		if(esb_rep_30043000102!=null) {
            		hostSeqNo="ENS"+esb_rep_30043000102.getRepBody().getHostDate()+
            				esb_rep_30043000102.getRepBody().getHostTraceNo();
            		//交易状态 1-成功2-失败3-已撤销 D-大额未清算C-小额未清算
            		String acctResult=esb_rep_30043000102.getRepBody().getTranStatus();
            		if("1".equals(acctResult)) {
            			oldTxStatus = "0";
            		}else if("3".equals(acctResult)||"D".equals(acctResult)||"C".equals(acctResult)) {
            			oldTxStatus = "2";
            		}
            		}
        		}else if("BDC202".equals(txCode)) {
        			ESB_REP_30043000103 esb_rep_30043000103=outerDbitTranResult(reqDto,cipTraceLogData);
            		if(esb_rep_30043000103!=null) {
            		//交易状态 1-成功 2-待处理 3-无此记录
            		String acctResult=esb_rep_30043000103.getRepBody().getTranStatus();
            		if("1".equals(acctResult)) {
            			oldTxStatus = "0";
            		}else if("2".equals(acctResult)) {
            			oldTxStatus = "2";
            		}else if("3".equals(acctResult)) {
            			PafTradeExecuteException e = new PafTradeExecuteException(
            					PafTradeExecuteException.PAF_E_10021);
            			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
            			throw e;
            		}
            		}
        		}
        	}
        	break;
        	case "BDC203":
        	case "BDC205":
        		if("000000".equals(cipTraceLogData.getRspCode())) {
        			 String rspPack=cipTraceLogData.getRspPack();
                     JSONObject rspObject=JSONObject.parseObject(rspPack);
             		 JSONArray field=JSONArray.parseArray(rspObject.getJSONObject("body").getString("field"));
                     oldBatchNo = field.getJSONObject(0).getString("value");
                     String txStatus = "";
                     if(oldBatchNo.startsWith("CRDT")) {
                    	 BatchCrdtMasterModel record = new BatchCrdtMasterModel(myLog,Integer.parseInt(oldBatchNo.substring(4, 12)),0,Integer.parseInt(oldBatchNo.substring(12)));
             			 record = batchCrdtService.queryMasterByPk(myLog,record);
             			 if(null == record) {
             				PafTradeExecuteException e = new PafTradeExecuteException(
             						PafTradeExecuteException.PAF_E_10021);
             				myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
             				throw e;
             			 }
             			txStatus = record.getTxStatus();
             			switch(txStatus) {
            			case "0":
            			case "1":
            			case "2":
            			case "3":
            			case "4":
            				oldTxStatus = "2";
            				break;
            			case "5":
            				oldTxStatus = "0";
            				break;
            			case "9":
            				oldTxStatus = "1";
            				break;
            			}
                     }else if(oldBatchNo.startsWith("LOAN")){
                    	 BatchLoanMasterModel record = new BatchLoanMasterModel(myLog,Integer.parseInt(oldBatchNo.substring(4, 12)),0,Integer.parseInt(oldBatchNo.substring(12)));
             			 record = batchLoanService.queryMasterByPk(myLog,record);
             			 if(null == record) {
             				PafTradeExecuteException e = new PafTradeExecuteException(
             						PafTradeExecuteException.PAF_E_10021);
             				myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
             				throw e;
             			 }
             			 txStatus = record.getTxStatus();
             			 switch(txStatus) {
            			 case "0":
            			 case "1":
            			 case "2":
            			 case "3":
            				oldTxStatus = "2";
            				break;
            			 case "4":
            			    oldTxStatus = "0";
            				break;
            			 case "9":
            				oldTxStatus = "1";
            				break;
            			 }
                     }
        		}
        		break;
        	case "BDC211":
        	case "BDC212":
        		if("000000".equals(cipTraceLogData.getRspCode())) {
       			 String rspPack=cipTraceLogData.getRspPack();
                    JSONObject rspObject=JSONObject.parseObject(rspPack);
            		JSONArray field=JSONArray.parseArray(rspObject.getJSONObject("body").getString("field"));
                    for(int i=0,size=field.size();i<size;i++) {
                    	if("HostSeqNo".equals(field.getJSONObject(i).getString("name"))) {
                    		hostSeqNo = field.getJSONObject(i).getString("value");
                    	}
                    }
                    oldTxStatus = "0";
       		}
        		break;
        	}
        }  
        //原交易状态，0：成功、1：失败、2：处理中活状态未知
        repDto.getBody().getField().add(new FIELD("OldTxStatus",oldTxStatus));
        //银行主机流水号
        repDto.getBody().getField().add(new FIELD("HostSeqNo",hostSeqNo));
        //利息银行主机流水号（无）
        repDto.getBody().getField().add(new FIELD("IntHostSeqNo",""));
        //贷款罚息银行主机流水号（无）
        repDto.getBody().getField().add(new FIELD("PenHostSeqNo",""));
        //贷款违约金银行主机流水号（无）
        repDto.getBody().getField().add(new FIELD("FineHostSeqNo",""));
        //原交易批量编号
        repDto.getBody().getField().add(new FIELD("OldBatchNo",oldBatchNo));

        myLog.info(logger,"交易结果查询成功，原交易流水号："+reqDto.getBody().get(OLDSEQNO).toString()+",原交易状态："+oldTxStatus);
        return repDto;
    }


    /**
     * @Title: innerTranResult
     * @Description: 同行单笔记账交易结果查询
     * @param @param SER_REQ_DATA
     * @param @throws SysTradeExecuteException    设定文件
     * @return ESB_REP_30043000101    返回类型
     * @throws
     */
    private ESB_REP_30043000101 innerTranResult(SER_REQ_DATA reqDto,CipTraceLogData cipTraceLogData) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        Map<String,Object> headMap = reqDto.getHead();

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

        ESB_REQ_30043000101 esbReq_30043000101 = new ESB_REQ_30043000101(myLog, reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
        ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30043000101.getReqSysHead(),reqDto).setBranchId(txBrno).setUserId(txTel).build();
        esbReq_30043000101.setReqSysHead(reqSysHead);

        ESB_REQ_30043000101.REQ_BODY reqBody_30043000101=esbReq_30043000101.getReqBody();
        //记账渠道类型
        reqBody_30043000101.setChannelType("GJ");
       
            String platTrace=String.format("%08d",cipTraceLogData.getPlatTrace());//左补零
            //渠道流水号
            reqBody_30043000101.setChannelSeqNo(CIP.SYSTEM_ID+cipTraceLogData.getPlatDate()+platTrace);
            ESB_REP_30043000101 esb_rep_30043000101=null;
            try {
                //如果第一次查询没查到内容再查询一次
                esb_rep_30043000101 = IForwardToESBService.sendToESB(esbReq_30043000101, reqBody_30043000101, ESB_REP_30043000101.class);
            }catch (SysTradeExecuteException e){
                if(e.getRspCode().equals("RB4029")){
                	try {
                        esb_rep_30043000101 = IForwardToESBService.sendToESB(esbReq_30043000101, reqBody_30043000101, ESB_REP_30043000101.class);
                	}catch(SysTradeExecuteException e1) {
                		if(e1.getRspCode().equals("RB4029")){
                			return esb_rep_30043000101;
                		}else {
                			logger.error(e1.getRspCode() + " | " + e1.getRspMsg());
                			throw e1;
                		}
                	}
                }else{
                    logger.error(e.getRspCode() + " | " + e.getRspMsg());
                    throw e;
                }
            }

        
        return esb_rep_30043000101;
    }
    /** 
    * @Title: outerCrdtTranResult 
    * @Description:跨行付款交易结果查询
    * @param @param reqDto
    * @param @param cipTraceLogData
    * @param @return
    * @param @throws SysTradeExecuteException    设定文件 
    * @return ESB_REP_30043000102    返回类型 
    * @throws 
    */
    private ESB_REP_30043000102 outerCrdtTranResult(SER_REQ_DATA reqDto,CipTraceLogData cipTraceLogData) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        Map<String,Object> headMap = reqDto.getHead();
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

        ESB_REQ_30043000102 esbReq_30043000102 = new ESB_REQ_30043000102(myLog, reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
        ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30043000102.getReqSysHead(),reqDto).setBranchId(txBrno).setUserId(txTel).build();
        esbReq_30043000102.setReqSysHead(reqSysHead);

        ESB_REQ_30043000102.REQ_BODY reqBody_30043000102=esbReq_30043000102.getReqBody();
        
        ESB_REP_30043000102 esb_rep_30043000102=null;
        //公积金中心发送的请求报文
        String reqPack=cipTraceLogData.getReqPack();
        JSONObject reqObject=JSONObject.parseObject(reqPack);
        //结算模式
        String settleType = reqObject.getJSONObject("body").getString("SettleType");
        //渠道日期
        String channelDate = "";
        //渠道流水号
        String chanelSeqNo = "";
        //渠道系统ID
        String sysId = "";
        //渠道日期和渠道流水号
        String capSeqNo = "";
        //渠道日期和渠道流水号
        String[] result = new String[2];
        QrySingleCapTradeModel model = new QrySingleCapTradeModel();
        model.setPlatDate(cipTraceLogData.getPlatDate());
        model.setPlatTrace(cipTraceLogData.getPlatTrace());
        model = singleCrdtService.queryCrdtInfoByPk(model);
        //渠道日期和渠道流水号
        capSeqNo = model.getCapSeqNo();
   	    result = capSeqNo.split(",");
   	    channelDate = result[0];
    	chanelSeqNo = result[1];
    	//settleType等于2：跨行实时，渠道系统ID传HVPS
    	//settleType等于3：跨行非实时，渠道系统ID传BEPS
    	if("2".equals(settleType)) {
         	sysId = "HVPS";
        }else if("3".equals(settleType)) {
         	sysId = "BEPS";
        }
        //渠道日期
        reqBody_30043000102.setOrigChannelDate(channelDate);
        //渠道流水号
        reqBody_30043000102.setOrigChannelSeqNo(chanelSeqNo);
        //渠道系统ID
        reqBody_30043000102.setOrigSysId(sysId);
        try {
        //如果第一次查询没查到内容再查询一次
        esb_rep_30043000102 = IForwardToESBService.sendToESB(esbReq_30043000102, reqBody_30043000102, ESB_REP_30043000102.class);
        }catch (SysTradeExecuteException e){
        	  if(e.getRspCode().equals("CN00")){
              	try {
              		esb_rep_30043000102 = IForwardToESBService.sendToESB(esbReq_30043000102, reqBody_30043000102, ESB_REP_30043000102.class);
              	}catch(SysTradeExecuteException e1) {
              		if(e1.getRspCode().equals("CN00")){
              			return esb_rep_30043000102;
              		}else {
              			logger.error(e1.getRspCode() + " | " + e1.getRspMsg());
              			throw e1;
              		}
              	}
              }else{
                  logger.error(e.getRspCode() + " | " + e.getRspMsg());
                  throw e;
              } 
        }

        
        return esb_rep_30043000102;
    }
    /** 
     * @Title: outerDbitTranResult 
     * @Description:跨行收款交易结果查询
     * @param @param reqDto
     * @param @param cipTraceLogData
     * @param @return
     * @param @throws SysTradeExecuteException    设定文件 
     * @return ESB_REP_30043000103    返回类型 
     * @throws 
     */
    private ESB_REP_30043000103 outerDbitTranResult(SER_REQ_DATA reqDto,CipTraceLogData cipTraceLogData) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        Map<String,Object> headMap = reqDto.getHead();
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

        ESB_REQ_30043000103 esbReq_30043000103 = new ESB_REQ_30043000103(myLog, reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
        ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30043000103.getReqSysHead(),reqDto).setBranchId(txBrno).setUserId(txTel).build();
        esbReq_30043000103.setReqSysHead(reqSysHead);

        ESB_REQ_30043000103.REQ_BODY reqBody_30043000103=esbReq_30043000103.getReqBody();
        
        ESB_REP_30043000103 esb_rep_30043000103=null;
        //公积金中心发送的请求报文
        String reqPack=cipTraceLogData.getReqPack();
        JSONObject reqObject=JSONObject.parseObject(reqPack);
        //渠道日期
        String channelDate = "";
        //渠道流水号
        String chanelSeqNo = "";
        //渠道系统ID
        String sysId = "";
        //渠道日期和渠道流水号
        String capSeqNo = "";
        //渠道日期和渠道流水号
        String[] result = new String[2];
        QrySingleCapTradeModel model = new QrySingleCapTradeModel();
        model.setPlatDate(cipTraceLogData.getPlatDate());
        model.setPlatTrace(cipTraceLogData.getPlatTrace());
        model = singleDbitService.queryDbitInfoByPk(model);
        //渠道日期和渠道流水号
        capSeqNo = model.getCapSeqNo();
   	    result = capSeqNo.split(",");
   	    channelDate = result[0];
    	chanelSeqNo = result[1];
        //渠道日期
        reqBody_30043000103.setOrigChannelDate(channelDate);
        //渠道流水号
        reqBody_30043000103.setOrigChannelSeqNo(chanelSeqNo);
        //渠道系统ID
        try {
        //如果第一次查询没查到内容再查询一次
        esb_rep_30043000103 = IForwardToESBService.sendToESB(esbReq_30043000103, reqBody_30043000103, ESB_REP_30043000103.class);
        }catch (SysTradeExecuteException e){
        	  if(e.getRspCode().equals("CN00")){
              	try {
              		esb_rep_30043000103 = IForwardToESBService.sendToESB(esbReq_30043000103, reqBody_30043000103, ESB_REP_30043000103.class);
              	}catch(SysTradeExecuteException e1) {
              		if(e1.getRspCode().equals("CN00")){
              			return esb_rep_30043000103;
              		}else {
              			logger.error(e1.getRspCode() + " | " + e1.getRspMsg());
              			throw e1;
              		}
              	}
              }else{
                  logger.error(e.getRspCode() + " | " + e.getRspMsg());
                  throw e;
              } 
        }

        
        return esb_rep_30043000103;
    }
}
