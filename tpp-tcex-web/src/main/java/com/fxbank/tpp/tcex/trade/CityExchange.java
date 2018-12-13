package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30011000101;
import com.fxbank.tpp.esb.model.ses.ESB_REP_TS002;
import com.fxbank.tpp.esb.model.ses.ESB_REP_TS004;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30011000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_TS002;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_TS004;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.esb.service.IForwardToTownService;
import com.fxbank.tpp.tcex.dto.esb.REP_30041001001;
import com.fxbank.tpp.tcex.dto.esb.REQ_30041001001;
import com.fxbank.tpp.tcex.model.RcvTraceInitModel;
import com.fxbank.tpp.tcex.model.RcvTraceUpdModel;
import com.fxbank.tpp.tcex.service.IRcvTraceService;

import redis.clients.jedis.Jedis;
/**
 * 商行通兑业务
 * @author liye
 *
 */
@Service("REQ_30041001001")
public class CityExchange implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CityDeposit.class);

	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IForwardToTownService forwardToTownService;
	
	@Reference(version = "1.0.0")
	private IRcvTraceService rcvTraceService;
	
	@Resource
	private MyJedis myJedis;
	
	private static final String BRTEL_PREFIX = "tcex_branch.";
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30041001001 reqDto = (REQ_30041001001) dto;
		REQ_30041001001.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30041001001 repDto = new REP_30041001001();
		//插入流水表
		boolean b = true;
		initRecord(reqDto);
		if(b) {
			//通知村镇记账
			ESB_REQ_TS002 esbReq_TS002 = new ESB_REQ_TS002(myLog,dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno());
			ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_TS002.getReqSysHead(),reqDto).setBranchId("22").setUserId("33").build();
			esbReq_TS002.setReqSysHead(reqSysHead);
			ESB_REQ_TS002.REQ_BODY esbReqBody_TS002 = esbReq_TS002.getReqBody();
			esbReqBody_TS002.setPlatDate(reqDto.getSysDate().toString());
			esbReqBody_TS002.setPlatTraceno(reqDto.getSysTraceno().toString());
			esbReqBody_TS002.setTxAmt(reqBody.getTranAmt());
			esbReqBody_TS002.setPayerName(reqBody.getPayerName());
			esbReqBody_TS002.setPayerAcc(reqBody.getPayerAcctNo());
			esbReqBody_TS002.setPayerPwd(reqBody.getPayPassword());
			esbReqBody_TS002.setIDtype(reqBody.getDocumentType());
			esbReqBody_TS002.setIDno(reqBody.getDocumentID());
			esbReqBody_TS002.setInfo(reqBody.getNarrative());
			
			ESB_REP_TS002 esbRep_TS002 =forwardToTownService.sendToTown(esbReq_TS002, esbReqBody_TS002, ESB_REP_TS002.class);
			ESB_REP_TS002.REP_BODY esbRepBody_TS002 = esbRep_TS002.getRepBody();
			String townBrno = esbRepBody_TS002.getBrno();
			Integer townDate = Integer.parseInt(esbRepBody_TS002.getTownDate());
			String townTraceNo = esbRepBody_TS002.getTownTraceno();
			String townState = esbRep_TS002.getRepSysHead().getRet().get(0).getRetCode();
			updateTownRecord(reqDto,townBrno, townDate, townTraceNo, townState);
			//更新流水表村镇记账状态
			if("000000".equals(townState)) {
				//核心记账：将金额从头寸中划至指定账户
				//本金记账状态码
				String hostCode = null;
				//本金记账流水号
				String hostSeqno = null;
				//核心日期
				String hostDate = null;
				ESB_REP_30011000101 esbRep_30011000101 = innerCapCharge(reqDto,townBrno);
				hostCode = esbRep_30011000101.getRepSysHead().getRet().get(0).getRetCode();
				hostSeqno = esbRep_30011000101.getRepSysHead().getReference();
				hostDate = esbRep_30011000101.getRepSysHead().getRunDate();
				//更新流水表核心记账状态
				updateHostRecord(reqDto,Integer.parseInt(hostDate),hostSeqno,hostCode);
				
					if("000000".equals(hostCode)) {
						//多次查询核心，确认是否是延迟原因
						b = true;
					}else {
						//核心记账失败
						b = false;
					}
					
					if(!b) {
						//村镇冲正
						ESB_REQ_TS004 esbReq_TS004 = new ESB_REQ_TS004(myLog,dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno());
						ESB_REQ_SYS_HEAD reqTS004SysHead = new EsbReqHeaderBuilder(esbReq_TS004.getReqSysHead(),reqDto).setBranchId("22").setUserId("33").build();
						esbReq_TS004.setReqSysHead(reqTS004SysHead);
						ESB_REQ_TS004.REQ_BODY esbReqBody_TS004 = esbReq_TS004.getReqBody();
						esbReqBody_TS004.setPlatDate(townDate.toString());
						esbReqBody_TS004.setPlatTraceno(townTraceNo);
						ESB_REP_TS004 esbRep_TS004 = forwardToTownService.sendToTown(esbReq_TS004, esbReqBody_TS004, ESB_REP_TS004.class);
						ESB_REP_TS004.REP_BODY esbRepBody_TS004 = esbRep_TS004.getRepBody();
						String sts = esbRepBody_TS004.getSts();
						//更新流水表村镇记账状态
						updateTownRecord(reqDto,townBrno,townDate,townTraceNo,sts);
					}
			}else {
				throw new SysTradeExecuteException("1111");
			}
			
			
		}else {
			throw new SysTradeExecuteException("1111");
		}
		
		
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
	private ESB_REP_30011000101 innerCapCharge(REQ_30041001001 reqDto,String townBrno) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		
		REQ_30041001001.REQ_BODY reqBody = reqDto.getReqBody();
		//交易机构
        String txBrno = reqDto.getReqSysHead().getBranchId() ;
        //柜员号
    	String txTel = reqDto.getReqSysHead().getUserId();
        //头寸
        String inAcno = null;
    	 try(Jedis jedis = myJedis.connect()){
    		inAcno = jedis.get(BRTEL_PREFIX + townBrno + "_INACNO");
         }
    	
		ESB_REQ_30011000101 esbReq_30011000101 = new ESB_REQ_30011000101(myLog, 
				reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(
				esbReq_30011000101.getReqSysHead(),reqDto).setBranchId(txBrno).
				setUserId(txTel).build(); 
		esbReq_30011000101.setReqSysHead(reqSysHead);
			
		ESB_REQ_30011000101.REQ_BODY reqBody_30011000101 = esbReq_30011000101.getReqBody();
		//账号/卡号
		reqBody_30011000101.setBaseAcctNo(inAcno);
		//交易类型                                                                                                                                
		reqBody_30011000101.setTranType("GJ03");
		//交易币种
		reqBody_30011000101.setTranCcy("CNY");
		//交易金额
		reqBody_30011000101.setTranAmt(reqBody.getTranAmt());
		//摘要
		reqBody_30011000101.setNarrative(reqBody.getNarrative());
		//记账渠道类型
		reqBody_30011000101.setChannelType(reqBody.getChannelType());
		//清算日期
		reqBody_30011000101.setSettlementDate(reqDto.getSysDate().toString());
		//对账标识,Y-参与对账;N-不参与对账
		reqBody_30011000101.setCollateFlag("Y");
		ESB_REP_30011000101 esbRep_30011000101 = forwardToESBService.sendToESB(esbReq_30011000101,reqBody_30011000101, ESB_REP_30011000101.class);
		return esbRep_30011000101;
	}
	private void initRecord(REQ_30041001001 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		
		REQ_30041001001.REQ_BODY reqBody = reqDto.getReqBody();
		REQ_SYS_HEAD reqSysHead = reqDto.getReqSysHead();
		
		RcvTraceInitModel record = new RcvTraceInitModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
		record.setSourceType(reqBody.getChannelType());
		record.setTxBranch(reqSysHead.getBranchId());
		//现转标志 0现金1转账
		record.setTxInd(reqBody.getMfflg());
		//通存通兑
		record.setDcFlag("1");
		record.setTxAmt(reqBody.getTranAmt());
		if("1".equals(reqBody.getMfflg())) {
		record.setPayeeAcno(reqBody.getPayeeAcctNo());
		record.setPayeeName(reqBody.getPayeeAcctName());
		}
		record.setPayerAcno(reqBody.getPayerAcctNo());
		record.setPayerName(reqBody.getPayerName());
		record.setHostState("0");
		record.setTxTel(reqSysHead.getUserId());
		//record.setChkTel();
		//record.setAuthTel();
		record.setInfo(reqBody.getNarrative());
		rcvTraceService.rcvTraceInit(record);
	}
	private RcvTraceUpdModel updateHostRecord(REQ_30041001001 reqDto,Integer hostDate,String hostTraceno,String hostState) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		RcvTraceUpdModel record = new RcvTraceUpdModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
		record.setHostDate(hostDate);	
		record.setHostState(hostState);
		record.setHostTraceno(hostTraceno);
		rcvTraceService.rcvTraceUpd(record);
		return record;
	}
	private RcvTraceUpdModel updateTownRecord(REQ_30041001001 reqDto,String townBrno,Integer townDate,String townTraceno,String townState) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		RcvTraceUpdModel record = new RcvTraceUpdModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
		record.setTownBranch(townBrno);
		record.setTownDate(townDate);	
		record.setTownState(townState);
		record.setTownTraceno(townTraceno);
		rcvTraceService.rcvTraceUpd(record);
		return record;
	}
}
