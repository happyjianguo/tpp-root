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
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30011000103;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30011000103;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.esb.service.IForwardToTownService;
import com.fxbank.tpp.tcex.dto.esb.REP_TR001;
import com.fxbank.tpp.tcex.dto.esb.REQ_TR001;
import com.fxbank.tpp.tcex.model.RcvTraceInitModel;
import com.fxbank.tpp.tcex.model.RcvTraceUpdModel;
import com.fxbank.tpp.tcex.model.TownInfo;
import com.fxbank.tpp.tcex.model.TownList;
import com.fxbank.tpp.tcex.service.IRcvTraceService;

import redis.clients.jedis.Jedis;

/** 
* @ClassName: TownDeposit 
* @Description: 村镇通存商行
* @author DuZhenduo
* @date 2018年12月18日 下午2:50:42 
*  
*/
@Service("REQ_TR001")
public class TownDeposit implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CityDeposit.class);

	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IForwardToTownService forwardToTownService;
	
	@Resource
	private MyJedis myJedis;

	@Reference(version = "1.0.0")
	private IRcvTraceService rcvTraceService;
	
	private final static String COMMON_PREFIX = "tcex_common.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_TR001 reqDto = (REQ_TR001) dto;
		REP_TR001 repDto = new REP_TR001();
		REQ_TR001.REQ_BODY reqBody = reqDto.getReqBody();
		// 插入流水表
		initRecord(reqDto);
		myLog.info(logger, "村镇通存商行登记成功，村镇机构" + reqBody.getBrnoFlag() + "收款账号" + reqDto.getReqBody().getPayeeAcc());
		// 记账状态码
		String hostCode = null;
		String hostMsg = null;
		// 记账流水号
		String hostSeqno = null;
		// 核心日期
		String hostDate = null;
		ESB_REP_30011000103 esbRep_30011000103 = null;
		try {
		  esbRep_30011000103 = hostCharge(reqDto);
		}catch(SysTradeExecuteException e) {
			updateHostRecord(reqDto, "", "", "2",e.getRspCode(),e.getRspMsg(),"");
			throw e;
		}
		hostCode = esbRep_30011000103.getRepSysHead().getRet().get(0).getRetCode();
		hostMsg = esbRep_30011000103.getRepSysHead().getRet().get(0).getRetMsg();
		hostSeqno = esbRep_30011000103.getRepBody().getReference();
		hostDate = esbRep_30011000103.getRepSysHead().getRunDate();
		// 开户机构
		String acctBranch = esbRep_30011000103.getRepBody().getAcctBranch();
		// 记账机构
		String accounting_branch = esbRep_30011000103.getRepBody().getAccountingBranch();
		// 记账结果，00-已记账 01-已挂账
		String acctResult = esbRep_30011000103.getRepBody().getAcctResult();
		// 更新流水表核心记账状态
	    updateHostRecord(reqDto, hostDate, hostSeqno, "1",hostCode,hostMsg,accounting_branch);
	    return repDto;
	}
	/** 
	* @Title: hostCharge 
	* @Description: 核心记账
	* @param reqDto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return  ESB_REP_30011000103  返回类型 
	* @throws 
	*/
	private ESB_REP_30011000103 hostCharge(REQ_TR001 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		REQ_TR001.REQ_BODY reqBody = reqDto.getReqBody();
		// 村镇编号
		String townFlag = reqBody.getBrnoFlag();
		// 交易机构
		String txBrno = reqDto.getReqSysHead().getBranchId();
		// 柜员号
		String txTel = reqDto.getReqSysHead().getUserId();

		ESB_REQ_30011000103 esbReq_30011000103 = new ESB_REQ_30011000103(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000103.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30011000103.setReqSysHead(reqSysHead);

		ESB_REQ_30011000103.REQ_BODY reqBody_30011000103 = esbReq_30011000103.getReqBody();
		// 账号/卡号
		reqBody_30011000103.setBaseAcctNo(reqBody.getPayeeAcc());
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
		reqBody_30011000103.setVillageBrnachId(townBranch);
		// 村镇标志
		//reqBody_30011000103.setVillageFlag(townFlag);
		// 交易类型
		reqBody_30011000103.setTranType("LV01");
		// 交易币种
		reqBody_30011000103.setTranCcy("CNY");
		// 交易金额
		reqBody_30011000103.setTranAmt(reqBody.getTxAmt());
		// 密码
		reqBody_30011000103.setPassword("");
		ESB_REP_30011000103 esbRep_30011000103 = forwardToESBService.sendToESB(esbReq_30011000103, reqBody_30011000103,
				ESB_REP_30011000103.class);
		return esbRep_30011000103;
	}
	/** 
	* @Title: initRecord 
	* @Description: 交易登记
	* @param reqDto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return    返回类型 
	* @throws 
	*/
	private void initRecord(REQ_TR001 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		REQ_TR001.REQ_BODY reqBody = reqDto.getReqBody();
		REQ_SYS_HEAD reqSysHead = reqDto.getReqSysHead();

		RcvTraceInitModel record = new RcvTraceInitModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setSourceType(reqSysHead.getSourceType());
		record.setTxBranch(reqSysHead.getBranchId());
		// 现转标志 0现金1转账
		record.setTxInd("0");
		// 通存通兑
		record.setDcFlag("0");
		record.setTxAmt(reqBody.getTxAmt());
		record.setPayeeAcno(reqBody.getPayeeAcc());
		record.setPayeeName(reqBody.getPayeeName());
		record.setHostState("0");
		record.setTownState("0");
		record.setTxTel(reqSysHead.getUserId());
		record.setTownBranch(reqBody.getBrno());
		record.setTownDate(reqBody.getTownDate());
		record.setTownTraceNo(reqBody.getTownTraceNo());
		record.setTownFlag(reqBody.getBrnoFlag());
		// record.setChkTel();
		// record.setAuthTel();
		record.setInfo(reqBody.getInfo());
		rcvTraceService.rcvTraceInit(record);

	}
	/** 
	* @Title: updateHostRecord 
	* @Description: 更新核心记账状态
	* @param reqDto
	* @param hostDate 核心日期
	* @param hostTraceno 核心流水号
	* @param hostState 核心记账状态
	* @param retCode 核心响应码
	* @param retMsg 核心响应信息
	* @param accounting_branch 核心记账机构
	* @param @throws SysTradeExecuteException    设定文件 
	* @return RcvTraceUpdModel   返回类型 
	* @throws 
	*/
	private RcvTraceUpdModel updateHostRecord(REQ_TR001 reqDto, String hostDate, String hostTraceno,
			String hostState,String retCode,String retMsg,String accounting_branch) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		RcvTraceUpdModel record = new RcvTraceUpdModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		if(!"".equals(hostDate)) {
		record.setHostDate(Integer.parseInt(hostDate));
		}
		record.setHostState(hostState);
		record.setHostTraceno(hostTraceno);
		record.setRetCode(retCode);
		record.setRetMsg(retMsg);
		record.setHostBranch(accounting_branch);
		rcvTraceService.rcvTraceUpd(record);
		return record;
	}
}
