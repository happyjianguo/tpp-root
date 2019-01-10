package com.fxbank.tpp.tcex.trade;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.constant.CIP;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30011000103;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30011000103;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30014000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30043000101;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.esb.service.IPasswordService;
import com.fxbank.tpp.tcex.dto.esb.REP_TR003;
import com.fxbank.tpp.tcex.dto.esb.REQ_TR003;
import com.fxbank.tpp.tcex.exception.TcexTradeExecuteException;
import com.fxbank.tpp.tcex.model.RcvTraceQueryModel;
import com.fxbank.tpp.tcex.model.RcvTraceUpdModel;
import com.fxbank.tpp.tcex.model.TownInfo;
import com.fxbank.tpp.tcex.model.TownList;
import com.fxbank.tpp.tcex.service.IRcvTraceService;

import redis.clients.jedis.Jedis;

import com.fxbank.tpp.tcex.dto.esb.REQ_30043002701;
/**
 * 村镇存款确认业务
 * @author liye
 *
 */
@Service("REQ_TR003")
public class TownDepositConfirm implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(CityQueryAcctInfo.class);

	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IRcvTraceService rcvTraceService;

	@Reference(version = "1.0.0")
	private IPasswordService passwordService;
	
	@Resource
	private MyJedis myJedis;

	private final static String COMMON_PREFIX = "tcex_common.";
	
	@Override
//	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
//		MyLog myLog = logPool.get();
//		
//		REQ_TR003 reqDto = (REQ_TR003) dto;
//		String townDate = reqDto.getReqBody().getTownDate();//村镇日期
//		String townTraceno = reqDto.getReqBody().getTownTraceno();//村镇流水
//		// 交易机构
//		String txBrno = reqDto.getReqSysHead().getBranchId();
//		// 柜员号
//		String txTel = reqDto.getReqSysHead().getUserId();
//		
//		//自查流水状态
//		RcvTraceQueryModel model = rcvTraceService.getConfirmTrace(myLog,  townDate, townTraceno);
//		String state = model.getHostState();
//		Integer platDate = model.getPlatDate();
//		Integer platTrance = model.getPlatTrace();
//		String hostTrance = model.getHostTraceno();
//		String townFlag = model.getTownFlag();
//		BigDecimal txAmt = model.getTxAmt();
//		String payeeAcno = model.getPayeeAcno();
//		
//		
//		testHx(myLog,txBrno,txTel,platTrance,platDate,hostTrance,dto,reqDto);
////		System.out.println(txBrno+","+txTel+","+ platTrance+","+ hostTrance+","+ dto+","+ reqDto+","+ townFlag+","+ payeeAcno+","+ txAmt);
////		testCharge(myLog, txBrno, txTel, platTrance, hostTrance, dto, reqDto, townFlag, payeeAcno, txAmt.toString());
//		
//		REP_TR003 repDto = new REP_TR003();
//		repDto.getRepBody().setSts("1");
//		repDto.getRepBody().setPlatDate(platDate.toString());
//		repDto.getRepBody().setPlatTraceno(platTrance.toString());
//		
//		return repDto;
//	}
	
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		
		REQ_TR003 reqDto = (REQ_TR003) dto;
		String townDate = reqDto.getReqBody().getTownDate();//村镇日期
		String townTraceno = reqDto.getReqBody().getTownTraceno();//村镇流水
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try(Jedis jedis = myJedis.connect()){
			txBrno = jedis.get(COMMON_PREFIX+"TXBRNO");
			txTel = jedis.get(COMMON_PREFIX+"TXTEL");
        }
		//自查流水状态
		RcvTraceQueryModel model = rcvTraceService.getConfirmTrace(myLog,  townDate, townTraceno);
		String state = model.getHostState();
		Integer platDate = model.getPlatDate();
		Integer platTrance = model.getPlatTrace();
		String townFlag = model.getTownFlag();
		BigDecimal txAmt = model.getTxAmt();
		String payeeAcno = model.getPayeeAcno();
		
		//登记超时
		//调用核心接口确认该笔流水是否入账成功，若登记成功直接反馈，若三次查询都失败则调用核心接口重新登记，登记结果渠道不关心，存款确认反馈状态只有成功
		//登记失败
		//调用核心记账接口
		String flag="0";
		String hostDate=null,hostTraceno=null,retCode=null,retMsg=null,hostBranch=null;
		if(state.equals("3")) {
			int i=0;
			for(;i<3;i++) {
				//调用核心接口确认该笔流水是否入账成功,
				ESB_REQ_30043000101 esbReq_30043000101 = new ESB_REQ_30043000101(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());
				ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30043000101.getReqSysHead(), reqDto)
						.setBranchId(txBrno).setUserId(txTel).build();
				esbReq_30043000101.setReqSysHead(reqSysHead);	
				ESB_REQ_30043000101.REQ_BODY reqBody_30043000101 = esbReq_30043000101.getReqBody();
				//渠道流水号
				reqBody_30043000101.setChannelSeqNo(CIP.SYSTEM_ID+platDate.toString()+String.format("%08d",platTrance));
//				reqBody_30043000101.setReference(hostTrance);
				reqBody_30043000101.setChannelType("LV");
				
				try {
					ESB_REP_30043000101 esbRep_30043000101 = forwardToESBService.sendToESB(esbReq_30043000101, reqBody_30043000101, ESB_REP_30043000101.class);
					myLog.info(logger,"记账结果查询："+esbRep_30043000101.getRepBody().getAcctResult());
					retCode = esbRep_30043000101.getRepSysHead().getRet().get(0).getRetCode();
					retMsg = esbRep_30043000101.getRepSysHead().getRet().get(0).getRetMsg();
					if(esbRep_30043000101.getRepBody().getAcctResult().equals("00")) {
						hostDate = esbRep_30043000101.getRepBody().getTranDate();
						hostTraceno = esbRep_30043000101.getRepBody().getReference();
						break;
					}
				}catch (Exception e) {
					retCode="error";
					retMsg = "渠道流水号【"+platTrance.toString()+"】单笔记账结果查询失败："+e.getMessage();
					myLog.error(logger,"渠道流水号【"+platTrance.toString()+"】记账结果查询失败："+e.getMessage());
				}
				
			}
			if(i==3)flag="1";
		}else if(state.equals("2")){
			flag="1";
		}else {
			myLog.error(logger,"渠道流水号【"+platTrance.toString()+"】记录的核心状态【"+state+"】不符合存款确认流程状态 ");
//			TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10011);
//			throw e;
		}
		
		if(flag.equals("1")) {
			// 村镇机构号
			String jsonStrTownBranch = null;
			try(Jedis jedis = myJedis.connect()){
				jsonStrTownBranch = jedis.get(COMMON_PREFIX+"TOWN_LIST");
	        }
			if(jsonStrTownBranch==null||jsonStrTownBranch.length()==0){
				logger.error("渠道未配置["+COMMON_PREFIX + "TOWN_LIST"+"]");
				throw new RuntimeException("渠道未配置["+COMMON_PREFIX + "TOWN_LIST"+"]");
			}
			TownList townList = JsonUtil.toBean(jsonStrTownBranch, TownList.class);
	        String townBranch = null;
			for(TownInfo townInfo:townList.getData()){
				if(townInfo.getTownFlag().equals(townFlag)) {
					townBranch = townInfo.getTownBranch();
				}
			}
			
			//调用核心记账接口
			ESB_REQ_30011000103 esbReq_30011000103 = new ESB_REQ_30011000103(myLog, reqDto.getSysDate(),
					reqDto.getSysTime(), reqDto.getSysTraceno());
			ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000103.getReqSysHead(), reqDto)
					.setBranchId(txBrno).setUserId(txTel).build();
			esbReq_30011000103.setReqSysHead(reqSysHead);

			ESB_REQ_30011000103.REQ_BODY reqBody_30011000103 = esbReq_30011000103.getReqBody();
			// 账号/卡号
			reqBody_30011000103.setBaseAcctNo(payeeAcno);
			// 村镇机构号
			reqBody_30011000103.setVillageBrnachId(townBranch);
//			// 村镇标志 1-于洪 2-铁岭 7-彰武 8-阜蒙
//			reqBody_30011000103.setVillageFlag(townFlag);
			// 交易类型
			reqBody_30011000103.setTranType("LV01");
			// 交易币种
			reqBody_30011000103.setTranCcy("CNY");
			// 交易金额
			reqBody_30011000103.setTranAmt(txAmt.toString());
			
			try {
				ESB_REP_30011000103 esbRep_30011000103 = forwardToESBService.sendToESB(esbReq_30011000103, reqBody_30011000103,
						ESB_REP_30011000103.class);
				retCode = esbRep_30011000103.getRepSysHead().getRet().get(0).getRetCode();
				retMsg = esbRep_30011000103.getRepSysHead().getRet().get(0).getRetMsg();
				hostTraceno = esbRep_30011000103.getRepBody().getReference();
				hostDate = esbRep_30011000103.getRepSysHead().getRunDate();
				hostBranch = esbRep_30011000103.getRepBody().getAccountingBranch();
			}catch (Exception e) {
				retCode="error";
				retMsg = "渠道流水号【"+platTrance.toString()+"】统一记账失败："+e.getMessage();
				myLog.error(logger,"渠道流水号【"+platTrance.toString()+"】统一记账失败："+e.getMessage());
			}
		}
		 
		RcvTraceUpdModel record = new RcvTraceUpdModel(myLog, platDate, dto.getSysTime(), platTrance);
		record.setHostState("4");
		if(null != hostDate && !"".equals(hostDate))
			record.setHostDate(Integer.parseInt(hostDate));
		record.setHostTraceno(hostTraceno);
		record.setRetCode(retCode);
		record.setRetMsg(retMsg);
		record.setHostBranch(hostBranch);
		rcvTraceService.rcvTraceUpd(record);

		REP_TR003 repDto = new REP_TR003();
		repDto.getRepBody().setSts("1");
		repDto.getRepBody().setPlatDate(platDate.toString());
		repDto.getRepBody().setPlatTraceno(platTrance.toString());
		
		return repDto;
	}

	private void testHx(MyLog myLog, String txBrno, String txTel, Integer platTrance, Integer platDate, String hostTrance, DataTransObject dto, REQ_TR003 reqDto) throws SysTradeExecuteException {
		//调用核心接口确认该笔流水是否入账成功,
		ESB_REQ_30043000101 esbReq_30043000101 = new ESB_REQ_30043000101(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30043000101.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30043000101.setReqSysHead(reqSysHead);	
		ESB_REQ_30043000101.REQ_BODY reqBody_30043000101 = esbReq_30043000101.getReqBody();
		String pt = String.format("%08d",platTrance);
		reqBody_30043000101.setChannelSeqNo(CIP.SYSTEM_ID+platDate.toString()+pt);
//		reqBody_30043000101.setReference(hostTrance);
		reqBody_30043000101.setChannelType("LV");
		try {
			ESB_REP_30043000101 esbRep_30043000101 = forwardToESBService.sendToESB(esbReq_30043000101, reqBody_30043000101, ESB_REP_30043000101.class);
			myLog.info(logger,"存款确认结果："+esbRep_30043000101.getRepBody().getAcctResult());
			esbRep_30043000101.getRepBody().getChannelSeqNo();
			esbRep_30043000101.getRepBody().getTranDate();
		}catch (Exception e) {
			myLog.error(logger,"存款确认：",e);
		}
		
		

//		retCode = esbRep_30011000103.getRepSysHead().getRet().get(0).getRetCode();
//		retMsg = esbRep_30011000103.getRepSysHead().getRet().get(0).getRetMsg();
//		hostTraceno = esbRep_30011000103.getRepBody().getReference();
//		hostDate = esbRep_30011000103.getRepSysHead().getRunDate();
//		hostBranch = esbRep_30011000103.getRepBody().getAccountingBranch();
		
	}
	
	private void testCharge(MyLog myLog, String txBrno, String txTel, Integer platTrance, String hostTrance, DataTransObject dto, REQ_TR003 reqDto,String townFlag,String payeeAcno,String txAmt) throws SysTradeExecuteException {
		String jsonStrTownBranch = null;
		try(Jedis jedis = myJedis.connect()){
			jsonStrTownBranch = jedis.get(COMMON_PREFIX+"TOWN_LIST");
        }
		if(jsonStrTownBranch==null||jsonStrTownBranch.length()==0){
			logger.error("渠道未配置["+COMMON_PREFIX + "TOWN_LIST"+"]");
			throw new RuntimeException("渠道未配置["+COMMON_PREFIX + "TOWN_LIST"+"]");
		}
		TownList townList = JsonUtil.toBean(jsonStrTownBranch, TownList.class);
        String townBranch = null;
		for(TownInfo townInfo:townList.getData()){
			if(townInfo.getTownFlag().equals(townFlag)) {
				townBranch = townInfo.getTownBranch();
			}
		}
		
		ESB_REQ_30011000103 esbReq_30011000103 = new ESB_REQ_30011000103(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000103.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30011000103.setReqSysHead(reqSysHead);

		ESB_REQ_30011000103.REQ_BODY reqBody_30011000103 = esbReq_30011000103.getReqBody();
		// 账号/卡号
		reqBody_30011000103.setBaseAcctNo(payeeAcno);
		// 村镇机构号
		reqBody_30011000103.setVillageBrnachId(townBranch);
	//// 村镇标志 1-于洪 2-铁岭 7-彰武 8-阜蒙
	//reqBody_30011000103.setVillageFlag(townFlag);
	// 交易类型
		reqBody_30011000103.setTranType("LV01");
		// 交易币种
		reqBody_30011000103.setTranCcy("CNY");
		// 交易金额
		reqBody_30011000103.setTranAmt(txAmt);
		reqBody_30011000103.setNewCoreFlag("N");

		try {
			ESB_REP_30011000103 esbRep_30011000103 = forwardToESBService.sendToESB(esbReq_30011000103, reqBody_30011000103,
					ESB_REP_30011000103.class);
			myLog.info(logger,"存款确认记账成功");
		}catch (Exception e) {
			myLog.error(logger,"存款确认记账失败：",e);
		}
		
	}
	
	

}
