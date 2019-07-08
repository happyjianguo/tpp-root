package com.fxbank.tpp.bocm.trade.esb;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.pub.service.IPublicService;
import com.fxbank.tpp.bocm.dto.esb.REP_30061800501;
import com.fxbank.tpp.bocm.dto.esb.REQ_30061800501;
import com.fxbank.tpp.bocm.dto.esb.REQ_TS_CHK_FEE;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmAcctCheckErrModel;
import com.fxbank.tpp.bocm.model.BocmChkStatusModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceUpdModel;
import com.fxbank.tpp.bocm.model.REP_10103;
import com.fxbank.tpp.bocm.model.REP_10103_FEE;
import com.fxbank.tpp.bocm.model.REQ_10103;
import com.fxbank.tpp.bocm.service.IBocmAcctCheckErrService;
import com.fxbank.tpp.bocm.service.IBocmDayCheckLogService;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.bocm.service.IBocmSndTraceService;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import redis.clients.jedis.Jedis;

/** 
* @ClassName: CHK_FEE 
* @Description: 交行手续费对账
* @author YePuLiang
* @date 2019年6月22日 下午3:08:40 
*  
*/
@Service("REQ_TS_CHK_FEE")
public class CHK_FEE extends TradeBase implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(CHK_Bocm.class);
	
	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IForwardToBocmService forwardToBocmService;
	
	@Reference(version = "1.0.0")
	private IBocmDayCheckLogService dayCheckLogService;
	
	@Reference(version = "1.0.0")
	private IBocmSndTraceService sndTraceService;
	
	@Reference(version = "1.0.0")
	private IBocmRcvTraceService rcvTraceService;
	
	@Reference(version = "1.0.0")
	private IBocmAcctCheckErrService acctCheckErrService;
	
	@Reference(version = "1.0.0")
	private IPublicService publicService;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "bocm.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		
		MyLog myLog = logPool.get();
		REQ_TS_CHK_FEE reqDto = (REQ_TS_CHK_FEE) dto;
		REQ_TS_CHK_FEE.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30061800501 rep = new REP_30061800501();
		
		myLog.info(logger, "外围与交行对账定时任务开始");		   
		
		Integer date = reqDto.getSysDate();
		Integer sysTime = reqDto.getSysTime();
		Integer sysTraceno =reqDto.getSysTraceno();
		
		date = Integer.parseInt(reqBody.getStmtDtT2());
		Integer sysDate = date;
		
		//交行总行行号
		String JHNO = "";
		//阜新银行总行行号
		String FXNO = "";
		try(Jedis jedis = myJedis.connect()){
			//从redis中获取交行总行行号
			JHNO = jedis.get(COMMON_PREFIX+"JHNO");
			FXNO = jedis.get(COMMON_PREFIX+"FXNO");
        }
		
		acctCheckErrService.delete(date.toString());
				
		REQ_10103 req10103 = null;
		REP_10103_FEE rep10103 = null;
		
		req10103 = new REQ_10103(myLog, date, sysTime, sysTraceno);
		req10103.setSbnkNo(FXNO);
		req10103.setRbnkNo(FXNO);
		
		req10103.setFilNam("BFEE"+FXNO+date+".dat");	
		//获取交行对账文件
		myLog.info(logger, "外围与交行对账获取交行对账文件");
		//获取交行交易流水信息
		rep10103 = forwardToBocmService.sendToBocm(req10103, 
				REP_10103_FEE.class);		
		return rep;
	}
	
	private String getTraceSrc(String thdCod, String txnMod){
		//来账
		String RCV = "RCV";
		//往账
		String SND = "SND";
		//核对来账
		//磁条卡通存
		if("10000".equals(thdCod)){
			return RCV;
		}
		//IC卡通存
		if("20000".equals(thdCod)){
			return RCV;
		}
		//磁条卡现金通兑
		if("10001".equals(thdCod)&&"0".equals(txnMod)){
			return RCV;
		}
		//IC卡现金通兑
		if("20001".equals(thdCod)&&"0".equals(txnMod)){
			return RCV;
		}
		
		//核对往账
		//磁条卡现金通兑
		if("10001".equals(thdCod)&&"1".equals(txnMod)){
			return SND;
		}
		//IC卡现金通兑
		if("20001".equals(thdCod)&&"1".equals(txnMod)){
			return SND;
		}
		return null;
	}
	
	
	private void checkBocmRcvLog(MyLog myLog,int sysDate,int sysTime,BocmRcvTraceQueryModel rcvTraceQueryModel,
			REP_10103_FEE.Detail bocmTrace,String date) throws SysTradeExecuteException{
		//检查交行记账文件来账记录
		String bocmTraceno = bocmTrace.getTlogNo();
		int platTraceno = Integer.parseInt(bocmTrace.getLogNo().substring(6));
		int platDate = Integer.parseInt(bocmTrace.getTactDt());
		Double txAmt = bocmTrace.getTxnAmt();
		String dcFlag = rcvTraceQueryModel.getDcFlag();//通存通兑标志
		String bocmState = rcvTraceQueryModel.getBocmState(); //渠道记录的交行记账状态
		String hostState = rcvTraceQueryModel.getBocmState(); //渠道记录的核心记账状态
		String txnStatus = bocmTrace.getTxnSts();
		
		if("S".equals(txnStatus)) {
			//交行存款，现金通兑，交行是转出行，记账以交行为主
			if(("10000".equals(rcvTraceQueryModel.getTxCode()))||("20000".equals(rcvTraceQueryModel.getTxCode()))||
			   ("20001".equals(rcvTraceQueryModel.getTxCode())&&"0".equals(rcvTraceQueryModel.getTxInd()))||
			   ("10001".equals(rcvTraceQueryModel.getTxCode())&&"0".equals(rcvTraceQueryModel.getTxInd()))){					
				//交易结果以交行为准，更新核心记账状态为失败
				if(hostState.equals("1")){
					BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(), rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
					record.setCheckFlag("2");
					record.setHostState("2");
					sndTraceService.sndTraceUpd(record);
					myLog.info(logger,"更新渠道对账状态，渠道日期【"+rcvTraceQueryModel.getPlatDate()+"】，渠道流水【"+rcvTraceQueryModel.getPlatTrace()+"】");
				}else{
					BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(), rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
					record.setCheckFlag("2");
					record.setHostState("2");
					sndTraceService.sndTraceUpd(record);
					myLog.info(logger,"渠道更新来账数据已交行为主记账状态，渠道日期【"+rcvTraceQueryModel.getPlatDate()+"】，渠道流水【"+rcvTraceQueryModel.getPlatTrace()+"】，"
							+ "交行记账调整前状态【"+bocmState+"】，调整后状态【1】，核心记账调整前状态【"+hostState+"】，调整后状态【1】");
				
					BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, sysDate, sysTime, platTraceno);
					aceModel.setPlatDate(sysDate);
					aceModel.setPlatTrace(platTraceno);
					aceModel.setPreHostState(rcvTraceQueryModel.getHostState());
					aceModel.setReHostState("1");
					aceModel.setDcFlag("");
					aceModel.setCheckFlag("2");
					aceModel.setDirection("O");
					aceModel.setMsg("交行记账成功，核心补充记账数据，渠道日期【"+sysDate+"】，渠道流水【"+platTraceno+"】");
					acctCheckErrService.insert(aceModel);
				}
				
			}
		}else if("F".equals(txnStatus)) {				
			//交行卡付款转账（磁条卡和IC卡）  通兑（交行转出行）交易结果以交行为准
			if(("10001".equals(rcvTraceQueryModel.getTxCode())&&"1".equals(rcvTraceQueryModel.getTxInd()))
					||("20001".equals(rcvTraceQueryModel.getTxCode())&&"1".equals(rcvTraceQueryModel.getTxInd()))){					
				//交易结果以交行为准，更新核心记账状态为失败
				if(hostState.equals("1")){
					BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(), rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
					record.setCheckFlag("2");
					record.setHostState("2");
					sndTraceService.sndTraceUpd(record);
					myLog.info(logger,"更新渠道对账状态，渠道日期【"+rcvTraceQueryModel.getPlatDate()+"】，渠道流水【"+rcvTraceQueryModel.getPlatTrace()+"】");
				
					BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, sysDate, sysTime, platTraceno);
					aceModel.setPlatDate(sysDate);
					aceModel.setPlatTrace(platTraceno);
					aceModel.setPreHostState(rcvTraceQueryModel.getHostState());
					aceModel.setReHostState("2");
					aceModel.setDcFlag("");
					aceModel.setCheckFlag("2");
					aceModel.setDirection("O");
					aceModel.setMsg("交行记账失败，冲正核心记账数据，渠道日期【"+sysDate+"】，渠道流水【"+platTraceno+"】");
					acctCheckErrService.insert(aceModel);
				
				}else{
					BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(), rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
					record.setCheckFlag("2");
					record.setHostState("2");
					sndTraceService.sndTraceUpd(record);
					myLog.info(logger,"渠道更新来账数据已交行为主记账状态，渠道日期【"+rcvTraceQueryModel.getPlatDate()+"】，渠道流水【"+rcvTraceQueryModel.getPlatTrace()+"】，"
							+ "交行记账调整前状态【"+bocmState+"】，调整后状态【1】，核心记账调整前状态【"+hostState+"】，调整后状态【1】");
				}
				
			}	
		}	
	}
	
	private void checkBocmSndLog(MyLog myLog,int sysDate,int sysTime,BocmSndTraceQueryModel sndTraceQueryModel,
			REP_10103_FEE.Detail bocmTrace,String date) throws SysTradeExecuteException {
		
		String bocmTraceno = bocmTrace.getTlogNo();
		int platTraceno = Integer.parseInt(bocmTrace.getLogNo().substring(6));
		int platDate = Integer.parseInt(bocmTrace.getTactDt());
		Double txAmt = bocmTrace.getTxnAmt();
		
		
		String bocmState = sndTraceQueryModel.getBocmState(); //渠道记录的交行记账状态
		String hostState = sndTraceQueryModel.getBocmState(); //渠道记录的核心记账状态
		String txnStatus = bocmTrace.getTxnSts();
		
		if("S".equals(txnStatus)) {
			//交行卡付款转账（磁条卡和IC卡）  通兑（交行转出行）交易结果以交行为准
			if(("10001".equals(sndTraceQueryModel.getTxCode())&&"1".equals(sndTraceQueryModel.getTxInd()))
					||("20001".equals(sndTraceQueryModel.getTxCode())&&"1".equals(sndTraceQueryModel.getTxInd()))){									
				//交易结果以交行为准，更新核心记账状态为失败
				if(hostState.equals("1")){
					BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
					record.setCheckFlag("2");
					record.setHostState("2");
					sndTraceService.sndTraceUpd(record);
					myLog.info(logger,"更新渠道对账状态，渠道日期【"+sndTraceQueryModel.getPlatDate()+"】，渠道流水【"+sndTraceQueryModel.getPlatTrace()+"】");
				}else{
					BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
					record.setCheckFlag("2");
					record.setHostState("2");
					sndTraceService.sndTraceUpd(record);
					myLog.info(logger,"渠道更新往账数据已交行为主记账状态，渠道日期【"+sndTraceQueryModel.getPlatDate()+"】，渠道流水【"+sndTraceQueryModel.getPlatTrace()+"】，"
							+ "交行记账调整前状态【"+bocmState+"】，调整后状态【1】，核心记账调整前状态【"+hostState+"】，调整后状态【1】");
				
					BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, sysDate, sysTime, platTraceno);
					aceModel.setPlatDate(sysDate);
					aceModel.setPlatTrace(platTraceno);
					aceModel.setPreHostState(sndTraceQueryModel.getHostState());
					aceModel.setReHostState("1");
					aceModel.setDcFlag("");
					aceModel.setCheckFlag("2");
					aceModel.setDirection("O");
					aceModel.setMsg("交行记账成功，核心补充记账数据，渠道日期【"+sysDate+"】，渠道流水【"+platTraceno+"】");
					acctCheckErrService.insert(aceModel);
				}
				
			}
		}else if("F".equals(txnStatus)) {				
			//交行卡付款转账（磁条卡和IC卡）  通兑（交行转出行）交易结果以交行为准
			if(("10001".equals(sndTraceQueryModel.getTxCode())&&"1".equals(sndTraceQueryModel.getTxInd()))
					||("20001".equals(sndTraceQueryModel.getTxCode())&&"1".equals(sndTraceQueryModel.getTxInd()))){					
				//交易结果以交行为准，更新核心记账状态为失败
				if(hostState.equals("1")){
					BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
					record.setCheckFlag("2");
					record.setHostState("2");
					sndTraceService.sndTraceUpd(record);
					myLog.info(logger,"更新渠道对账状态，渠道日期【"+sndTraceQueryModel.getPlatDate()+"】，渠道流水【"+sndTraceQueryModel.getPlatTrace()+"】");
				
					BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, sysDate, sysTime, platTraceno);
					aceModel.setPlatDate(sysDate);
					aceModel.setPlatTrace(platTraceno);
					aceModel.setPreHostState(sndTraceQueryModel.getHostState());
					aceModel.setReHostState("2");
					aceModel.setDcFlag("");
					aceModel.setCheckFlag("2");
					aceModel.setDirection("O");
					aceModel.setMsg("交行记账失败，冲正核心记账数据，渠道日期【"+sysDate+"】，渠道流水【"+platTraceno+"】");
					acctCheckErrService.insert(aceModel);
				
				}else{
					BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
					record.setCheckFlag("2");
					record.setHostState("2");
					sndTraceService.sndTraceUpd(record);
					myLog.info(logger,"渠道更新往账数据已交行为主记账状态，渠道日期【"+sndTraceQueryModel.getPlatDate()+"】，渠道流水【"+sndTraceQueryModel.getPlatTrace()+"】，"
							+ "交行记账调整前状态【"+bocmState+"】，调整后状态【1】，核心记账调整前状态【"+hostState+"】，调整后状态【1】");
				}
				
			}	
		}
	}

}
