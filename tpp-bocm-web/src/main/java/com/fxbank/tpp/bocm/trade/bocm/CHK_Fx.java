package com.fxbank.tpp.bocm.trade.bocm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.bocm.dto.bocm.REP_10103;
import com.fxbank.tpp.bocm.dto.bocm.REQ_10103;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmChkStatusModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceQueryModel;
import com.fxbank.tpp.bocm.service.IBocmAcctCheckErrService;
import com.fxbank.tpp.bocm.service.IBocmChkStatusService;
import com.fxbank.tpp.bocm.service.IBocmDayCheckLogService;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.bocm.service.IBocmSndTraceService;
import com.fxbank.tpp.bocm.util.NumberUtil;
import com.fxbank.tpp.esb.service.IForwardToESBService;

/** 
* @ClassName: CHK_Fx 
* @Description: 取对账文件
* @author YePuLiang
* @date 2019年5月6日 上午10:02:07 
*  
*/
@Service("REQ_10103")
public class CHK_Fx implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(CHK_Fx.class);
	
	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IBocmDayCheckLogService dayCheckLogService;
	
	@Reference(version = "1.0.0")
	private IBocmSndTraceService sndTraceService;
	
	@Reference(version = "1.0.0")
	private IBocmRcvTraceService rcvTraceService;
	
	@Reference(version = "1.0.0")
	private IBocmAcctCheckErrService acctCheckErrService;
	
	@Reference(version = "1.0.0")
	private IBocmChkStatusService chkStatusService;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
			
		MyLog myLog = logPool.get();
		REQ_10103 req = (REQ_10103) dto;
	
		//发起行行号
		String sbnkNo = req.getSbnkNo();
		if(sbnkNo.substring(0, 3).equals("313")){
			myLog.info(logger, "交易发起行为本行，启用挡板数据");
			TS_10103 ts10103 = new TS_10103(logPool, forwardToESBService,dayCheckLogService, 
					sndTraceService, rcvTraceService, acctCheckErrService);
			DataTransObject dtoRep = ts10103.execute(dto);
			return dtoRep;
		}
		
		REP_10103 rep = new REP_10103();
		myLog.info(logger, "交行请求我行对账文件");
		
		String fileName = req.getFilNam();
		String date = fileName.substring(16,24);
		
		BocmChkStatusModel chkModel = chkStatusService.selectByDate(date);
		if(chkModel==null||chkModel.getHostStatus()==0){
			//更新对账状态表交行对账状态
			BocmChkStatusModel record = new BocmChkStatusModel();
			record.setTxDate(Integer.parseInt(date));
			record.setPlatStatus(2);
			chkStatusService.chkStatusUpd(record);
			myLog.info(logger, "更新对账状态表信息");	
			
			myLog.error(logger, "渠道未与本行核心对账");
			BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013,"渠道未与核心对账");
			throw e;
		}
		if(chkModel.getHostStatus()==2){
			//更新对账状态表交行对账状态
			BocmChkStatusModel record = new BocmChkStatusModel();
			record.setTxDate(Integer.parseInt(date));
			record.setPlatStatus(2);
			chkStatusService.chkStatusUpd(record);
			myLog.error(logger, "渠道与本行核心对账失败");
			BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013,"渠道与本行核心对账失败");
			throw e;
		}

		int sysTime = req.getSysTime();
		int sysTraceno = req.getSysTraceno();
		int sysDate = req.getSysDate();
		
		
		String rcvCheckFlag2 = rcvTraceService.getTraceNum(date, "2");
		String rcvCheckFlag3 = rcvTraceService.getTraceNum(date, "3");
		int rcvTotal = Integer.parseInt(rcvCheckFlag2)+Integer.parseInt(rcvCheckFlag3);
		myLog.info(logger, "来账对账记录数【已对账】【"+rcvCheckFlag2+"】");
		myLog.info(logger, "来账对账记录数【核心多】【"+rcvCheckFlag3+"】");
		
		String sndCheckFlag2 = sndTraceService.getTraceNum(date, "2");
		String sndCheckFlag3 = sndTraceService.getTraceNum(date, "3");
		int sndTotal = Integer.parseInt(sndCheckFlag2)+Integer.parseInt(sndCheckFlag3);
		
		myLog.info(logger, "往账对账记录数【已对账】【"+sndCheckFlag2+"】");
		myLog.info(logger, "往账对账记录数【核心多】【"+sndCheckFlag3+"】");
		
		int tolCnt = rcvTotal + sndTotal;
		myLog.info(logger, "已对账交易数量【"+tolCnt+"】");
			
		BigDecimal totalAmt = new BigDecimal("0.00");

		//组装来账文件报文
		List<BocmRcvTraceQueryModel> upRcvTraceList = rcvTraceService.getUploadCheckRcvTrace(myLog, sysDate,sysTime,sysTraceno, date);
		List<REP_10103.Detail> tradList = new ArrayList<REP_10103.Detail>();		
		for(BocmRcvTraceQueryModel model :upRcvTraceList){
			myLog.info(logger, "来账确认流水【"+model.getPlatTrace()+"】记账是否以本行为主");
			if(model.getTranType()==null){
				myLog.info(logger, "流水【"+model.getPlatTrace()+"】交易类型为空");
				continue;
			}				
			if(model.getTranType().equals("JH01")&&model.getTxInd().equals("1")){
				//本方交易流水号
				REP_10103.Detail trad = modelToRcvTradDetail(model);
				totalAmt = totalAmt.add(new BigDecimal(trad.getTxnAmt().toString()));
				tradList.add(trad);
				myLog.info(logger, "流水【"+model.getPlatTrace()+"】添加对账流水");
			}
		}
		//组装往账文件报文
		List<BocmSndTraceQueryModel> upSndTraceList = sndTraceService.getUploadCheckSndTrace(myLog, sysDate,sysTime,sysTraceno, date);
		for(BocmSndTraceQueryModel model :upSndTraceList){
			myLog.info(logger, "往账确认流水【"+model.getPlatTrace()+"】记账是否以本行为主");
			if(model.getTranType().equals("JH11")&&model.getTxInd().equals("1")){
				//往账如果是交行卡付款转账，不返回记账，记账结果以交行为主
				continue;
			}
			//本方交易流水号
			REP_10103.Detail trad = modelToSndTradDetail(model);
			totalAmt = totalAmt.add(new BigDecimal(trad.getTxnAmt().toString()));
			tradList.add(trad);
		}	
		myLog.info(logger, "以我行为主交易总金额【"+totalAmt+"】");
		myLog.info(logger, "以我行为主交易数量【"+tradList.size()+"】");
		rep.setFilLen(254*tradList.size());	
		rep.setTolCnt(tradList.size());
		rep.setTolAmt(NumberUtil.addPoint(Double.parseDouble(totalAmt.toString())));
		myLog.info(logger, "返回报文文件长度："+rep.getFilLen());
				
		rep.setFilTxt(tradList);
			
		//更新对账状态表交行对账状态
		BocmChkStatusModel record = new BocmChkStatusModel();
		record.setTxDate(Integer.parseInt(date));
		record.setPlatStatus(1);
		record.setPlatTxCnt(tradList.size());
		record.setPlatTxAmt(new BigDecimal(totalAmt.toString()));
		chkStatusService.chkStatusUpd(record);
		myLog.info(logger, "更新对账状态表信息");		


		return rep;
	}
	
	
	private REP_10103.Detail modelToRcvTradDetail(BocmRcvTraceQueryModel model){
		REP_10103.Detail chk = new REP_10103.Detail();
		//给交行返回交易流水
		chk.setTlogNo(String.format("%06d%08d", model.getPlatDate()%1000000,model.getPlatTrace()));
		chk.setLogNo(model.getBocmTraceno());
		
		chk.setThdCod(model.getTxCode());
		chk.setBbusTyp("000");
		
		
		//发起行行号  来账本行为联机交易接收行（填发起行人行行号）
		//联机交易发起方出文件时指发起方网点，
		//联机交易接收方出文件时指发起方总行行号。
		chk.setSbnkNo(model.getSndBankno());
		//接收行行号  联机交易接收方总行行号。
		chk.setRbnkNo(model.getRcvBankno());
		chk.setTactDt(model.getPlatDate()+"");
		chk.setTxnAmt(Double.parseDouble(model.getTxAmt().toString()));
		//手续费收取方式
		chk.setFeeFlg(model.getFeeFlag());
		//开户行手续费
		if(model.getFee()!=null){
			chk.setFee(Double.parseDouble(model.getFee().toString()));
		}
		//通存通兑业务模式 0 现金 1 转账
		chk.setTxnMod(model.getTxInd());
		//付款人开户行
		chk.setPayBnk(model.getPayerBank());
		//付款人账户类型
		chk.setPactTp(model.getPayerActtp());
		//付款人账号
		chk.setPactNo(model.getPayerAcno());
		//付款人名称
		chk.setPayNam(model.getPayerName());
		//收款人开户行
		chk.setRecBnk(model.getPayeeBank());
		//收款人账户类型
		chk.setRactTp(model.getPayeeActtp());
		//收款人账号
		chk.setRactNo(model.getPayeeAcno());
		//收款人名称
		chk.setRecNam(model.getPayeeName());
		//交易状态
		if("1".equals(model.getHostState())){
			chk.setTxnSts("S");	
		}else{
			chk.setTxnSts("F");	
		}
		
		return chk;
	}
	
	/**
	* @Title: modelToSndTradDetail 
	* @Description: 往账组装单笔交易报文 
	* @param @param model
	* @param @return    设定文件 
	* @return REP_10103.Detail    返回类型 
	* @throws
	 */
	private REP_10103.Detail modelToSndTradDetail(BocmSndTraceQueryModel model){
		REP_10103.Detail chk = new REP_10103.Detail();
		
		//本方交易流水号  TlogNo长度不足前补‘0’，例如农商行生成对账文件给交行，本方交易流水号填农商行流水号，对方交易流水号填交行流水号（如果存在），发起方流水号必输		
		//对方交易流水号 logNo 长度不足前补‘0’，发起方流水号必输
		//给交行返回交易流水
		chk.setTlogNo(String.format("%06d%08d", model.getSysDate()%1000000,model.getSysTraceno()));
		chk.setLogNo(model.getBocmTraceno());
		
		chk.setThdCod(model.getTxCode());
		chk.setBbusTyp("000");		
		
		//发起行行号  往账本行为联机交易发起行（填发起行人行行号）
		//联机交易发起方出文件时指发起方网点，
		//联机交易接收方出文件时指发起方总行行号。
		chk.setSbnkNo(model.getSndBankno());
		//接收行行号  联机交易接收方总行行号。(往账交易，接收方为交行)
		chk.setRbnkNo(model.getRcvBankno());
		chk.setTactDt(model.getPlatDate()+"");
		chk.setTxnAmt(Double.parseDouble(model.getTxAmt().toString()));
		//手续费收取方式
		chk.setFeeFlg(model.getFeeFlag());
		//开户行手续费
		if(model.getFee()!=null){
			chk.setFee(Double.parseDouble(model.getFee().toString()));
		}
		//通存通兑业务模式 0 现金 1 转账
		chk.setTxnMod(model.getTxInd());
		//付款人开户行
		chk.setPayBnk(model.getPayerBank());
		//付款人账户类型
		chk.setPactTp(model.getPayerActtp());
		//付款人账号
		chk.setPactNo(model.getPayerAcno());
		//付款人名称
		chk.setPayNam(model.getPayerName());
		//收款人开户行
		chk.setRecBnk(model.getPayeeBank());
		//收款人账户类型
		chk.setRactTp(model.getPayeeActtp());
		//收款人账号
		chk.setRactNo(model.getPayeeAcno());
		//收款人名称
		chk.setRecNam(model.getPayeeName());
		//交易状态
		if("1".equals(model.getHostState())){
			chk.setTxnSts("S");	
		}else{
			chk.setTxnSts("F");	
		}
		
		return chk;
	}
}
