/**   
* @Title: CHK_Bocm.java 
* @Package com.fxbank.tpp.bocm.trade.esb 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月6日 下午5:18:44 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.trade.esb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dcfs.esb.ftp.client.FtpClientConfigSet;
import com.dcfs.esb.ftp.client.FtpGet;
import com.dcfs.esb.ftp.server.error.FtpException;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.bocm.dto.esb.REP_30061800501;
import com.fxbank.tpp.bocm.dto.esb.REQ_30061800501;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmAcctCheckErrModel;
import com.fxbank.tpp.bocm.model.BocmDayCheckLogInitModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceUpdModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceUpdModel;
import com.fxbank.tpp.bocm.model.REP_10103;
import com.fxbank.tpp.bocm.model.REQ_10103;
import com.fxbank.tpp.bocm.service.IBocmAcctCheckErrService;
import com.fxbank.tpp.bocm.service.IBocmDayCheckLogService;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.bocm.service.IBocmSndTraceService;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;
import com.fxbank.tpp.esb.model.ses.ESB_REP_50015000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_50015000101;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import redis.clients.jedis.Jedis;

/** 
* @ClassName: CHK_Bocm 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author YePuLiang
* @date 2019年5月6日 下午5:18:44 
*  
*/
@Service("REQ_30061800501")
public class CHK_Bocm extends TradeBase implements TradeExecutionStrategy {
	
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
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "bocm.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061800501 reqDto = (REQ_30061800501) dto;
		REQ_30061800501.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30061800501 rep = new REP_30061800501();
				
		//对账日期
		String date = reqDto.getReqBody().getStmtDtT2();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try(Jedis jedis = myJedis.connect()){
			txBrno = jedis.get(COMMON_PREFIX+"TXBRNO");
			txTel = jedis.get(COMMON_PREFIX+"TXTEL");
        }
		
		
		myLog.info(logger, "外围与交行对账开始");
		
		/**
		
		acctCheckErrService.delete(date);
		
		
		
		//获取交行对账文件
		myLog.info(logger, "获取交行对账文件");
		REQ_10103 req10103 = null;
		REP_10103 rep10103 = null;
		req10103 = new REQ_10103(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
		super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req10103.getHeader()); // 设置报文头中的行号信息
		req10103.setFilNam("BUPS"+req10103.getHeader().getsBnkNo()+date+".dat");			
		//对账文件长度
		String filLen = "";
		//交易笔数
		String tolCnt = "0";
		//对账文件明细
		String filTxt = "";
		try {
			//获取对账文件
			rep10103 = getBocmCheckFile(req10103);
			filLen = rep10103.getFilLen();
			tolCnt = rep10103.getTolCnt();
			BigDecimal tolAmt = rep10103.getTolAmt();
			filTxt = rep10103.getFilTxt();
			myLog.info(logger, "对账文件:"+filTxt);
		}catch (SysTradeExecuteException e) {
				//异常处理
				BocmTradeExecuteException e1 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013,e.getRspMsg()+"获取交行记账文件失败");
				throw e1;
		}

		//拆分对账文件与渠道对账
		List<ChkFile> list = ChkFile.getChkTraceList(filTxt, Integer.parseInt(filLen));
		for(ChkFile bocmTrace : list){
			String bocmTraceno = bocmTrace.gettLogNo();
			String bocmDate = bocmTrace.gettActDt();
			//交易业务码
			String thdCod = bocmTrace.getThdCod();
			String txnMod = bocmTrace.getTxnMod();
			//通存通兑业务模式 0现金 1转账
			String source = getTraceSrc(thdCod,txnMod);
			if("RCV".equals(source)){
				//根据交行对账数据取渠道来账数据
				BocmRcvTraceQueryModel rcvTraceQueryModel = rcvTraceService.getBocmRcvTraceByKey(myLog, dto.getSysTime(), 
						dto.getSysTraceno(), dto.getSysDate(),Integer.parseInt(bocmDate),bocmTraceno);
				checkBocmRcvLog(myLog, reqDto, rcvTraceQueryModel, bocmTrace,date);
				
			}
			if("SND".equals(source)){
				//根据交行核心对账数据取渠道往账数据
				BocmSndTraceQueryModel sndTraceQueryModel = sndTraceService.getBocmSndTraceByKey(myLog, dto.getSysTime(), 
						dto.getSysTraceno(), dto.getSysDate(),Integer.parseInt(bocmDate),bocmTraceno);
				
				checkBocmSndLog(myLog, reqDto, sndTraceQueryModel, bocmTrace, bocmDate);
			}
			if(source==null){
				BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013,"交行交易码不存在");
				throw e;
			}

		}
		
		myLog.info(logger, "外围与交行对账结束");
		
		
		**/

		
		myLog.info(logger, "核心与外围对账开始");
		
		
		//核对来账  rcvDayCheckLogList(从核心对账文件中获取的记账数据   来账)
		List<BocmDayCheckLogInitModel> rcvDayCheckLogList = getCheckLogList(myLog, date, txBrno, txTel, dto, "I");
		//通过核心记账信息找柜面中来账的流水记录
		checkRcvLog(myLog, dto, rcvDayCheckLogList, date);
		
		//核对往帐 SndDayCheckLogList(从核心对账文件中获取的记账数据  往账)
		List<BocmDayCheckLogInitModel> SndDayCheckLogList = getCheckLogList(myLog, date, txBrno, txTel, dto, "O");
		//通过核心记账信息找柜面中往账的流水记录
		checkSndLog(myLog, dto, SndDayCheckLogList, date);
		
		myLog.info(logger, "核心与外围对账结束");
		
		myLog.info(logger, "外围与核心对账开始");
		//获取未对账的来账信息
		List<BocmRcvTraceQueryModel> rcvTraceList = rcvTraceService.getCheckRcvTrace(myLog,dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno(), date);
		
				//失败、超时、存款确认、冲功？
				//获取到的数据为渠道多出来的流水记录
				for(BocmRcvTraceQueryModel model : rcvTraceList) {
					BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, model.getPlatDate(), model.getPlatTime(), model.getPlatTrace());
					record.setCheckFlag("4");
					rcvTraceService.rcvTraceUpd(record);
					
					if(model.getHostState().equals("1")) {
						myLog.error(logger,"柜面通【"+date+"】对账失败: 多出来账记录，渠道流水号【"+model.getPlatTrace()+"】，核心状态【"+model.getHostState()+"】，通存通兑标志【"+model.getDcFlag()+"】");
						BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
						throw e;
					}else {
						myLog.info(logger, "渠道多出来账数据，渠道日期【"+model.getPlatDate()+"】，渠道流水【"+model.getPlatTrace()+"】，核心状态【"+model.getHostState()+"】，通存通兑标志【"+model.getDcFlag()+"】");
					}
				}
				
				//获取未对账的往帐信息
				List<BocmSndTraceQueryModel> sndTraceList = sndTraceService.getCheckSndTrace(myLog,dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno(), date);
				for(BocmSndTraceQueryModel model:sndTraceList) {
					BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, model.getPlatDate(), model.getPlatTime(), model.getPlatTrace());
					record.setCheckFlag("4");
					sndTraceService.sndTraceUpd(record);
					
					if(model.getHostState().equals("1")) {
						myLog.error(logger,"柜面通【"+date+"】对账失败: 多出往账记录，渠道流水号【"+model.getPlatTrace()+"】，核心状态【"+model.getHostState()+"】，通存通兑标志【"+model.getDcFlag()+"】");
						BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
						throw e;
					}else {
						myLog.info(logger, "渠道多出往账数据，渠道日期【"+model.getPlatDate()+"】，渠道流水【"+model.getPlatTrace()+"】，核心状态【"+model.getHostState()+"】，通存通兑标志【"+model.getDcFlag()+"】");
					}
				}
				
				myLog.info(logger, "外围与核心对账结束");
				

			
				
				
		
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
	
	private REP_10103 getBocmCheckFile(REQ_10103 req10103) throws SysTradeExecuteException{
		
		MyLog myLog = logPool.get();
		myLog.info(logger, "向交行发送发送磁条卡现金通存请求报文");
		REP_10103 rep_10103 = forwardToBocmService.sendToBocm(req10103, 
				REP_10103.class);
		return rep_10103;
	}
	

	
	private void checkRcvLog(MyLog myLog,DataTransObject dto,List<BocmDayCheckLogInitModel> rcvDayCheckLogList,String date) throws SysTradeExecuteException {
		for(BocmDayCheckLogInitModel model:rcvDayCheckLogList) {
			//根据核心对账数据取渠道来账数据
			BocmRcvTraceQueryModel rcvTraceQueryModel = rcvTraceService.getRcvTraceByKey(myLog, dto.getSysTime(), dto.getSysTraceno(), dto.getSysDate(),model.getSettleDate(),model.getPlatTrace());
			
			
			
			//若渠道缺少数据则报错
			if(rcvTraceQueryModel == null) {
				BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, model.getSettleDate(), dto.getSysTime(), model.getPlatTrace());
				aceModel.setPlatDate(model.getSettleDate());
				aceModel.setPlatTrace(model.getPlatTrace());
				aceModel.setPreHostState("");
				aceModel.setReHostState("1");
				aceModel.setDcFlag("");
				aceModel.setCheckFlag("3");
				aceModel.setDirection("I");
				aceModel.setTxAmt(model.getTxAmt());
				aceModel.setMsg("渠道补充来账数据，渠道日期【"+model.getSettleDate()+"】，渠道流水【"+model.getPlatTrace()+"】");
				acctCheckErrService.insert(aceModel);
			
				myLog.error(logger, "柜面通【"+date+"】来帐对账失败,渠道数据丢失: 核心流水号【"+model.getHostTraceno()+"】核心日期为【"+model.getSysDate()+"】");
				BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
				throw e;
			}else {
				//渠道记录的核心状态
				String hostState = rcvTraceQueryModel.getHostState(); 
				if(hostState.equals("1")) {
					//核心与渠道状态一致  更新对账状态
					BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(), rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
					record.setCheckFlag("2");
					rcvTraceService.rcvTraceUpd(record);
				}else if(hostState.equals("0")||hostState.equals("3")||hostState.equals("5")||hostState.equals("6")) {
					//核心记账成功，渠道状态为超时、冲正超时、冲正失败，修改渠道状态为成功
					BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(), rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
					record.setHostState("1");
					record.setCheckFlag("2");
					rcvTraceService.rcvTraceUpd(record);
					myLog.info(logger,"渠道调整来账数据核心状态，渠道日期【"+rcvTraceQueryModel.getPlatDate()+"】，渠道流水【"+rcvTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+rcvTraceQueryModel.getDcFlag()+"】");

					BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, model.getSettleDate(), dto.getSysTime(), model.getPlatTrace());
					aceModel.setPlatDate(model.getSettleDate());
					aceModel.setPlatTrace(model.getPlatTrace());
					aceModel.setPreHostState(rcvTraceQueryModel.getHostState());
					aceModel.setReHostState("1");
					aceModel.setDcFlag(rcvTraceQueryModel.getDcFlag());
					aceModel.setCheckFlag("2");
					aceModel.setDirection("I");
					aceModel.setTxAmt(rcvTraceQueryModel.getTxAmt());
					aceModel.setPayeeAcno(rcvTraceQueryModel.getPayeeAcno());
					aceModel.setPayeeName(rcvTraceQueryModel.getPayeeName());
					aceModel.setPayerAcno(rcvTraceQueryModel.getPayerAcno());
					aceModel.setPayerName(rcvTraceQueryModel.getPayerName());
					aceModel.setMsg("渠道调整来账数据核心状态，渠道日期【"+rcvTraceQueryModel.getPlatDate()+"】，渠道流水【"+rcvTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+rcvTraceQueryModel.getDcFlag()+"】");
					acctCheckErrService.insert(aceModel);
				}else {
					////核心记账成功，渠道状态为2.失败、4.冲正成功 则对账失败
					myLog.error(logger, "柜面通【"+date+"】对账失败: 渠道流水号【"+rcvTraceQueryModel.getPlatTrace()+"】记录核心状态为【"+rcvTraceQueryModel.getHostState()+"】,与核心记账状态不符");
					BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
					throw e;
				}
			}
		}
	}
	
	private List<BocmDayCheckLogInitModel> getCheckLogList(MyLog myLog, String date, String txBrno, String txTel,DataTransObject dto,String direction) throws SysTradeExecuteException{
		//请求核心接口，获取来账文件
		String localFile = getEsbCheckFile(myLog,date,txBrno,txTel,dto,direction);
		
		//对账文件入库
		InitCheckLog(localFile,myLog, dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno(),direction);
		
		//取对账文件数据
		List<BocmDayCheckLogInitModel> dayCheckLogList = dayCheckLogService.getDayCheckLog(myLog, dto.getSysTime(), dto.getSysTraceno(), dto.getSysDate());
			
		return 	dayCheckLogList;
	}
	
	private void InitCheckLog(String localFile, MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno,String direction) throws SysTradeExecuteException {
		BufferedReader br = null;
		myLog.info(logger, "账户变动信息入库开始");
		try {
			dayCheckLogService.delete();
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(localFile)),"UTF-8"));
			String lineTxt=null;
			while ((lineTxt = br.readLine()) != null) {
				lineTxt += "*|";
				String[] array = lineTxt.split("\\|");
                if(array.length<11){
                    myLog.info(logger,"文件【"+localFile+"】内容缺失");
                    continue;
                }

                BocmDayCheckLogInitModel model = new BocmDayCheckLogInitModel(myLog, sysDate,sysTime,sysTraceno);
                model.setPlatDate(sysDate); //渠道日期
                model.setPlatTrace(Integer.parseInt(array[0].substring(14))); //渠道流水
                model.setSettleDate(Integer.parseInt(array[1]));//清算日期
                model.setSettleBranch(array[2]); //清算机构
                model.setHostDate(Integer.parseInt(array[3])); //核心交易日期
                model.setHostTraceno(array[4]); //核心流水号
                model.setCcy(array[5]); //交易币种
                BigDecimal bg = new BigDecimal(array[6]==null?"0":array[6]);
                model.setTxAmt(bg); //交易金额
                model.setAccountno(array[7]); //交易账户 
                model.setReversal(array[8]); //冲正标志
                model.setTxStatus(array[9]); //交易状态
                model.setDirection(direction); //来往账标识
                
        		dayCheckLogService.dayCheckLogInit(model);
			}

		} catch (Exception e) {
            myLog.error(logger, "文件【"+localFile+"】插入失败", e);
            throw new RuntimeException("文件【"+localFile+"】插入失败");
		} finally {
			if(null != br) {
				try {
					br.close();
				} catch (IOException e) {
					myLog.error(logger, "文件流关闭失败", e);
				}
			}
			myLog.info(logger, "核心对账信息入库结束");
		}
		
		
	}
	
	private String getEsbCheckFile(MyLog myLog, String date, String txBrno, String txTel,DataTransObject dto,String direction) throws SysTradeExecuteException {
		ESB_REQ_50015000101 esbReq_50015000101 = new ESB_REQ_50015000101(myLog, dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_50015000101.getReqSysHead(),dto).setBranchId(txBrno).setUserId(txTel).setSourceType("LV").build();
		esbReq_50015000101.setReqSysHead(reqSysHead);
		ESB_REQ_50015000101.REQ_BODY esbReqBody_50015000101 = esbReq_50015000101.getReqBody();
		esbReqBody_50015000101.setChannelType("LV");
		esbReqBody_50015000101.setStartDate(date);
		esbReqBody_50015000101.setEndDate(date);
		esbReqBody_50015000101.setDirection(direction);
		
		
		ESB_REP_50015000101 esbRep_50015000101 = forwardToESBService.sendToESB(esbReq_50015000101, esbReqBody_50015000101, ESB_REP_50015000101.class);
		String remoteFile = esbRep_50015000101.getRepSysHead().getFilePath();
		String fileName = esbRep_50015000101.getRepBody().getFileName();
		String localPath="";
		try (Jedis jedis = myJedis.connect()) {
			localPath = jedis.get(COMMON_PREFIX+"txt_path");
		}
		loadTraceLogFile(myLog, remoteFile, localPath+File.separator+direction+"_"+fileName);
		return localPath+File.separator+direction+"_"+fileName;
	}
	
	/**
	 * @Title: loadFile @Description: 从文件传输平台下载文件 @param @param
	 * myLog @param @param remoteFile 文件传输平台路径+文件名 @param @param localFile
	 * 文件本地路径+文件名 @param @throws PafTradeExecuteException 设定文件 @return void
	 * 返回类型 @throws
	 */
	private void loadTraceLogFile(MyLog myLog, String remoteFile, String localFile) throws SysTradeExecuteException {
		FtpClientConfigSet configSet = new FtpClientConfigSet();
		FtpGet ftpGet = null;
		try {
			ftpGet = new FtpGet(remoteFile, localFile, configSet);
			boolean result = ftpGet.doGetFile();
			if (!result) {
				myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载失败！");
				BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10012);
				throw e;
			}
			myLog.info(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载成功！");
		} catch (Exception e) {
			myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载失败！", e);
			BocmTradeExecuteException e1 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10012,
					e.getMessage());
			throw e1;
		} finally {
			if (ftpGet != null) {
				try {
					ftpGet.close();
				} catch (FtpException e) {
					myLog.error(logger, "文件传输关闭连接失败！", e);
				}
			}
		}
	}
	

	
	private void checkSndLog(MyLog myLog, DataTransObject dto, List<BocmDayCheckLogInitModel> sndDayCheckLogList,
			String date) throws SysTradeExecuteException {
		for(BocmDayCheckLogInitModel model:sndDayCheckLogList) {
			//根据核心对账数据取渠道往账数据
			BocmSndTraceQueryModel sndTraceQueryModel = sndTraceService.getSndTraceByKey(myLog, dto.getSysTime(), dto.getSysTraceno(), dto.getSysDate(),model.getSettleDate(),model.getPlatTrace());
			//若渠道缺少数据则报错
			if(sndTraceQueryModel == null) {
				BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, model.getSettleDate(), dto.getSysTime(), model.getPlatTrace());
				aceModel.setPlatDate(model.getSettleDate());
				aceModel.setPlatTrace(model.getPlatTrace());
				aceModel.setPreHostState("");
				aceModel.setReHostState("1");
				aceModel.setDcFlag("");
				aceModel.setCheckFlag("3");
				aceModel.setDirection("O");
				aceModel.setMsg("渠道补充往账数据，渠道日期【"+model.getSettleDate()+"】，渠道流水【"+model.getPlatTrace()+"】");
				acctCheckErrService.insert(aceModel);
				
				myLog.error(logger, "柜面通【"+date+"】往帐对账失败,渠道数据丢失: 核心流水号【"+model.getHostTraceno()+"】核心日期为【"+model.getSysDate()+"】");
				BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
				throw e;
			}else {
				String dcFlag = sndTraceQueryModel.getDcFlag();//通存通兑标志
				String hostState = sndTraceQueryModel.getHostState(); //渠道记录的核心状态
				
				if(dcFlag.equals("0")) {
					//通存
					if(hostState.equals("1")) {
						//核心与渠道状态一致
						BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
						record.setCheckFlag("2");
						sndTraceService.sndTraceUpd(record);
					}else if(hostState.equals("0")||hostState.equals("3")||hostState.equals("5")||hostState.equals("6")) {
						//核心记账成功，渠道状态为超时、存款确认、冲正超时、冲正失败，修改渠道状态为成功
						BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
						record.setHostState("1");
						record.setCheckFlag("2");
						sndTraceService.sndTraceUpd(record);
						myLog.info(logger,"渠道调整往账数据核心状态，渠道日期【"+sndTraceQueryModel.getPlatDate()+"】，渠道流水【"+sndTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+dcFlag+"】");
					
						BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, model.getSettleDate(), dto.getSysTime(), model.getPlatTrace());
						aceModel.setPlatDate(model.getSettleDate());
						aceModel.setPlatTrace(model.getPlatTrace());
						aceModel.setPreHostState(hostState);
						aceModel.setReHostState("1");
						aceModel.setDcFlag(dcFlag);
						aceModel.setCheckFlag("2");
						aceModel.setDirection("O");
						aceModel.setTxAmt(sndTraceQueryModel.getTxAmt());
						aceModel.setPayeeAcno(sndTraceQueryModel.getPayeeAcno());
						aceModel.setPayeeName(sndTraceQueryModel.getPayeeName());
						aceModel.setPayerAcno(sndTraceQueryModel.getPayerAcno());
						aceModel.setPayerName(sndTraceQueryModel.getPayerName());
						aceModel.setMsg("渠道调整往账数据核心状态，渠道日期【"+sndTraceQueryModel.getPlatDate()+"】，渠道流水【"+sndTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+dcFlag+"】");
						acctCheckErrService.insert(aceModel);
					}else {
						myLog.error(logger, "柜面通【"+date+"】往帐对账失败: 渠道流水号【"+sndTraceQueryModel.getPlatTrace()+"】记录核心状态为【"+sndTraceQueryModel.getHostState()+"】,与核心记账状态不符");
						BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
						throw e;
					}
				}else if(dcFlag.equals("1")) {
					//通兑
					if(hostState.equals("1")) {
						//核心与渠道状态一致
						BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
						record.setCheckFlag("2");
						sndTraceService.sndTraceUpd(record);
					}else if(hostState.equals("0")||hostState.equals("3")||hostState.equals("5")||hostState.equals("6")) {
						//核心记账成功，渠道状态为超时、存款确认、冲正超时、冲正失败，修改渠道状态为成功
						BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
						record.setHostState("1");
						record.setCheckFlag("2");
						sndTraceService.sndTraceUpd(record);
						myLog.info(logger,"渠道调整往账数据核心状态，渠道日期【"+sndTraceQueryModel.getPlatDate()+"】，渠道流水【"+sndTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+dcFlag+"】");
					
						BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, model.getSettleDate(), dto.getSysTime(), model.getPlatTrace());
						aceModel.setPlatDate(model.getSettleDate());
						aceModel.setPlatTrace(model.getPlatTrace());
						aceModel.setPreHostState(hostState);
						aceModel.setReHostState("1");
						aceModel.setDcFlag(dcFlag);
						aceModel.setCheckFlag("2");
						aceModel.setDirection("O");
						aceModel.setTxAmt(sndTraceQueryModel.getTxAmt());
						aceModel.setPayeeAcno(sndTraceQueryModel.getPayeeAcno());
						aceModel.setPayeeName(sndTraceQueryModel.getPayeeName());
						aceModel.setPayerAcno(sndTraceQueryModel.getPayerAcno());
						aceModel.setPayerName(sndTraceQueryModel.getPayerName());
						aceModel.setMsg("渠道调整往账数据核心状态，渠道日期【"+sndTraceQueryModel.getPlatDate()+"】，渠道流水【"+sndTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+dcFlag+"】");
						acctCheckErrService.insert(aceModel);
					}else {
						myLog.error(logger, "柜面通【"+date+"】往帐对账失败: 渠道流水号【"+sndTraceQueryModel.getPlatTrace()+"】记录核心状态为【"+sndTraceQueryModel.getHostState()+"】,与核心记账状态不符");
						BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
						throw e;
					}
				}else {
					myLog.error(logger,"柜面通【"+date+"】往帐对账失败: 渠道流水号【"+sndTraceQueryModel.getPlatTrace()+"】通存通兑标志状态异常:【"+sndTraceQueryModel.getDcFlag()+"】");
					BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
					throw e;
				}
			}
		}
	}
}
