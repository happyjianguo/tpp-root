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
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmAcctCheckErrModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceUpdModel;
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
	
	private final static String COMMON_PREFIX = "bocm_common.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		
		MyLog myLog = logPool.get();
		REQ_30061800501 reqDto = (REQ_30061800501) dto;
		REQ_30061800501.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30061800501 rep = new REP_30061800501();

		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try(Jedis jedis = myJedis.connect()){
			txBrno = jedis.get(COMMON_PREFIX+"TXBRNO");
			txTel = jedis.get(COMMON_PREFIX+"TXTEL");
        }
				
		myLog.info(logger, "外围与交行对账定时任务开始");
		
		Integer sysDate = publicService.getSysDate("CIP");
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		Date d = null;
		try {
			d = df.parse(sysDate.toString());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}     
		Calendar cal=Calendar.getInstance();
		cal.setTime(d);
		//TODO  调试期间用当天后期修改
//		cal.add(Calendar.DATE, -1);  //减1天
		
		Integer date = Integer.parseInt(df.format(cal.getTime()));
		Integer sysTime = publicService.getSysTime();
		Integer sysTraceno = publicService.getSysTraceno();
		
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
		
		//对账文件长度
		int filLen = 0;
		//交易笔数
		int tolCnt = 0;
		//对账文件明细
		String filTxt = "";

		//获取对账文件
		filLen = rep10103.getFilLen();
		tolCnt = rep10103.getTolCnt();
		Double tolAmt = rep10103.getTolAmt();
		List<REP_10103_FEE.Detail> tradList = rep10103.getFilTxt();
		
		int snd = 0;
		int rcv = 0;
		//拆分对账文件与渠道对账		
		for(REP_10103_FEE.Detail bocmTrace : tradList){
			
			//获取交行交易流水号
			String bocmTraceno = bocmTrace.getTlogNo();
			//交易日期
			String bocmDate = bocmTrace.getTactDt();
			if(bocmDate==null){
				myLog.error(logger, "外围与交行对账,柜面通对账失败,交行流水号【"+bocmTraceno+"】记账日期为空");
				BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
				throw e;
			}

			
			//交易业务码
			String thdCod = bocmTrace.getThdCod();
			//通存通兑业务模式 0现金 1转账
			String txnMod = bocmTrace.getTxnMod();

			//交易发起行行号
			String SbnkNo = bocmTrace.getSbnkNo();
			
			myLog.info(logger, "外围与交行对账,交行流水号【"+bocmTraceno+"】发起行行号【"+SbnkNo+"】交易代码【"+thdCod+"】业务模式【"+txnMod+"】");
			
			//判断交易发起方人行行号，如果为本行行号说明本条对账文件对应的我方往账记录
			if(FXNO.equals(SbnkNo)){
				//根据交行核心对账数据取渠道往账数据
				BocmSndTraceQueryModel sndTraceQueryModel = sndTraceService.getBocmSndTraceByKey(myLog, sysTime, 
						sysTraceno, sysDate,Integer.parseInt(bocmDate),bocmTraceno);		
				//若渠道缺少数据则报错
				if(sndTraceQueryModel == null) {
					int platTraceno = Integer.parseInt(bocmTrace.getLogNo().substring(6));
					BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, sysDate, sysTime, platTraceno);
					aceModel.setPlatDate(sysDate);
					aceModel.setPlatTrace(platTraceno);
					aceModel.setPreHostState("");
					aceModel.setReHostState("1");
					aceModel.setDcFlag("");
					aceModel.setCheckFlag("3");
					aceModel.setDirection("O");
					aceModel.setTxAmt(new BigDecimal(bocmTrace.getTxnAmt()));
					aceModel.setMsg("渠道补充往账数据，渠道日期【"+sysDate+"】，渠道流水【"+platTraceno+"】");
					acctCheckErrService.insert(aceModel);						
					myLog.error(logger, "柜面通【"+date+"】往帐对账失败,渠道数据丢失: 交行流水号【"+bocmTraceno+"】交行记账日期为【"+sysDate+"】");
					BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
					throw e;
				}else{
					checkBocmSndLog(myLog, sysDate, sysTime , sndTraceQueryModel, bocmTrace, bocmDate);
					snd++;
				}	
			}else if(JHNO.equals(SbnkNo)){
				//判断交易发起方人行行号，如果不是本行行号说明本条对账文件对应的我方来账记录
				//根据交行对账数据取渠道来账数据
				BocmRcvTraceQueryModel rcvTraceQueryModel = rcvTraceService.getBocmRcvTraceByKey(myLog, sysTime, 
						sysTraceno, sysDate,Integer.parseInt(bocmDate),bocmTraceno);				
				//若渠道缺少数据则报错
				if(rcvTraceQueryModel == null) {
					int platTraceno = Integer.parseInt(bocmTrace.getLogNo().substring(6));
					BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, sysDate, sysTime, platTraceno);
					aceModel.setPlatDate(sysDate);
					aceModel.setPlatTrace(platTraceno);
					aceModel.setPreHostState("");
					aceModel.setReHostState("1");
					aceModel.setDcFlag("");
					aceModel.setCheckFlag("3");
					aceModel.setDirection("I");
					aceModel.setTxAmt(new BigDecimal(bocmTrace.getTxnAmt()));
					aceModel.setMsg("渠道补充来账数据，渠道日期【"+sysDate+"】，渠道流水【"+platTraceno+"】");
					acctCheckErrService.insert(aceModel);		
					myLog.error(logger, "柜面通来帐对账失败,渠道数据丢失: 交行流水号【"+bocmTraceno+"】核心日期为【"+sysDate+"】渠道流水【"+bocmTrace.getLogNo()+"】");
					BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
					throw e;
				}else{
					checkBocmRcvLog(myLog, sysDate, sysTime , rcvTraceQueryModel, bocmTrace, bocmDate);
					rcv++;
				}			
			}
		}
		myLog.info(logger, "外围与交行对账往账记录：【"+snd+"】");
		myLog.info(logger, "外围与交行对账来账记录：【"+rcv+"】");
		
		myLog.info(logger, "外围与交行对账结束");
		
		
//		int sndTotal = Integer.parseInt(sndCheckFlag2)+Integer.parseInt(sndCheckFlag3)+Integer.parseInt(sndCheckFlag4);
//		String s = "柜面通【"+date+"】对账统计：来账共【"+rcvTotal+"】笔，其中已对账【"+rcvCheckFlag2+"】笔，核心多出【"+rcvCheckFlag3+"】笔，渠道多出【"+rcvCheckFlag4+"】笔;"
//				+ "往账共【"+sndTotal+"】笔，其中已对账【"+sndCheckFlag2+"】笔，核心多出【"+sndCheckFlag3+"】笔，渠道多出【"+sndCheckFlag4+"】笔";

		myLog.info(logger, "外围与交行对账成功");
		
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
