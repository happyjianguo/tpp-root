package com.fxbank.tpp.bocm.trade.bocm;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.tpp.esb.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.constant.CIP;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.pub.service.IPublicService;
import com.fxbank.tpp.bocm.dto.bocm.REP_10009;
import com.fxbank.tpp.bocm.dto.bocm.REQ_10009;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmRcvTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceUpdModel;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30014000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30014000101;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import redis.clients.jedis.Jedis;

/** 
* @ClassName: RV_Fx 
* @Description: 交行向本行发起抹账交易
* @author YePuLiang
* @date 2019年5月6日 上午8:09:42 
*  
*/
@Service("REQ_10009")
public class RV_Fx implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(DP_FxICC.class);
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IBocmRcvTraceService bocmRcvTraceService;
	
	@Reference(version = "1.0.0")
	private IPublicService publicService;
	
	@Resource
	private LogPool logPool;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "bocm.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		myLog.info(logger, "交行向本行发起抹账交易");
		REQ_10009 req = (REQ_10009) dto;
		REP_10009 rep = new REP_10009();
		
		String sbnkNo = req.getSbnkNo();//发起行行号
		if(sbnkNo.substring(0, 3).equals("313")){
			myLog.info(logger, "交易发起行为本行，启用挡板数据");
			return rep;
		}

	


		
		String hostReversalCode = null;
		String hostReversalMsg = null;
		
		BocmRcvTraceQueryModel model = queryRcvTrace(req);
		
		if(model==null){
			myLog.error(logger, "交行向本行发起抹账交易，本行渠道无交易记录，渠道日期" + req.getSysDate() + "渠道流水号" + req.getSysTraceno());
			BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_11007);
			throw e2;
		}else{
			if("4".equals(model.getHostState())){
				myLog.info(logger, "交行向本行发起抹账交易，渠道已经抹账成功");
				return rep;
			}
			String settlementDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			if(model.getTxDate()!=Integer.parseInt(settlementDate)) {
				myLog.error(logger, "不能隔日冲正，交易日期" + model.getTxDate() + 
						"渠道流水号" + model.getPlatTrace()+",当前日期"+settlementDate);
				BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10011);
				throw e;
			}
		}
		
		try {
			myLog.info(logger, "通过交行流水查询渠道流水");
			

			//2.核心冲正
			ESB_REP_30014000101 esbRep_30014000101 = null;
			myLog.info(logger, "交行向本行发起抹账交易，向核心发送抹账请求");
			esbRep_30014000101 = hostReversal(req,model);	
			hostReversalCode = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetCode();
			hostReversalMsg = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetMsg();
			//更新流水表
			updateHostRecord(req, model, "4",hostReversalCode, hostReversalMsg);
			myLog.info(logger, "交行向本行发起抹账交易，更新流水表成功：核心流水号【"+model.getHostTraceno()+"】渠道流水号【"+model.getPlatTrace()+"】");
		} catch (SysTradeExecuteException e) {
			if(SysTradeExecuteException.CIP_E_000004.equals(e.getRspCode())||"ESB_E_000052".equals(e.getRspCode())) {
				//FX6203
				updateHostRecord(req, model, "6", e.getRspCode(), e.getRspMsg());
				myLog.error(logger, "交行向本行发起抹账交易，本行核心抹账超时，渠道日期" + req.getSysDate() + "渠道流水号" + req.getSysTraceno());
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_16203,e.getRspMsg()+"(抹账超时)");
				throw e2;
			}else{
				//冲正失败
				updateHostRecord(req, model, "5", e.getRspCode(), e.getRspMsg());
				myLog.error(logger, "交行向本行发起抹账交易，本行核心抹账失败，渠道日期" + req.getSysDate() + "渠道流水号" + req.getSysTraceno());
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10004,e.getRspMsg()+"(抹账失败)");
				throw e2;
			}
		}
		return rep;
	}
	
	private BocmRcvTraceUpdModel updateHostRecord(REQ_10009 reqDto, BocmRcvTraceQueryModel model,
			String hostState, String retCode, String retMsg) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, model.getPlatDate(), model.getPlatTime(),
				model.getPlatTrace());
		record.setHostState(hostState);
		record.setRetCode(retCode);
		record.setRetMsg(retMsg);
		bocmRcvTraceService.rcvTraceUpd(record);
		return record;
	}
	
	/** 
	* @Title: hostReversal 
	* @Description: 本行核心冲正
	* @param @param reqDto
	* @param @param hostSeqno
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30014000101    返回类型 
	* @throws 
	*/
	public ESB_REP_30014000101 hostReversal(DataTransObject dto,BocmRcvTraceQueryModel model)
			throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_10009 reqDto = (REQ_10009)dto;
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "TXBRNO");
			txTel = jedis.get(COMMON_PREFIX + "TXTEL");
		}

		ESB_REQ_30014000101 esbReq_30014000101 = new ESB_REQ_30014000101(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		/** Add by 叶浦亮 At 2019/12/3 15:48 For 不同渠道平台调用核心接口使用不一样的systemID */
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30014000101.getReqSysHead(), reqDto.getSourceType(),reqDto.getSysDate(),reqDto.getSysTime(),reqDto.getSysTraceno())
				.setBranchId(txBrno).setUserId(txTel).build();
		
		reqSysHead.setSourceType(dto.getSourceType());
		
		esbReq_30014000101.setReqSysHead(reqSysHead);

		ESB_REQ_30014000101.REQ_BODY reqBody_30014000101 = esbReq_30014000101.getReqBody();
		esbReq_30014000101.setReqSysHead(reqSysHead);	

		reqBody_30014000101.setChannelSeqNo(CIP.SYSTEM_ID+model.getPlatDate()+String.format("%08d",model.getPlatTrace()));
		reqBody_30014000101.setReversalReason("交行发起抹账请求");
		ESB_REP_30014000101 esbRep_30014000101 = forwardToESBService.sendToESB(esbReq_30014000101, reqBody_30014000101,
				ESB_REP_30014000101.class);
		return esbRep_30014000101;
	}
	
	private BocmRcvTraceQueryModel queryRcvTrace(REQ_10009 req) throws SysTradeExecuteException{
		MyLog myLog = logPool.get();
		BocmRcvTraceQueryModel model = null;
		//交易日期
		int townDate = req.getTtxnDat();
		//原交易流水
		String townTraceno = req.getOlogNo();
		model = bocmRcvTraceService.getConfirmTrace(myLog, townDate, townTraceno);
		return model;
	}
	


}
