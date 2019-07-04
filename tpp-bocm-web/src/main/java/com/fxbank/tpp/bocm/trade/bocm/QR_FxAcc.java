package com.fxbank.tpp.bocm.trade.bocm;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.bocm.dto.bocm.REP_10102;
import com.fxbank.tpp.bocm.dto.bocm.REQ_10102;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.bocm.service.IBocmSafeService;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30013000201;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30013000201;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import redis.clients.jedis.Jedis;

/** 
* @ClassName: QR_FxAcc 
* @Description: 账户信息查询
* @author YePuLiang
* @date 2019年5月6日 上午10:54:43 
*  
*/
@Service("REQ_10102")
public class QR_FxAcc implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(DP_FxICC.class);
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IBocmRcvTraceService bocmRcvTraceService;
	
	@Reference(version = "1.0.0")
    private IBocmSafeService safeService;
	

	@Resource
	private LogPool logPool;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "bocm.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_10102 req = (REQ_10102) dto;
		REP_10102 rep = new REP_10102();
		
		String sbnkNo = req.getSbnkNo();//发起行行号
		if(sbnkNo.substring(0, 3).equals("313")){
			myLog.info(logger, "交易发起行为本行，启用挡板数据");
			//TODO 获取交行账户信息	 301000000000  挡板交行行号	
			//发起行行号为本行直接走挡板
			rep.setActNo("6288880210000209903");
			rep.setActBnk("301000000000");
			rep.setActNam("挡板用户");
			rep.setActTyp("5");
			rep.setAmtLmt("0");
			rep.setFee(1d);		
			
			return rep;
		}
		ESB_REP_30013000201 esbRep_30013000201 = null;
		try {
			//1.调用核心查询账户信息
			esbRep_30013000201 = hostQuery(req);
		} catch (SysTradeExecuteException e) {
			myLog.error(logger, "交行查询本行卡余额，本行核心查询失败，渠道日期" + req.getSysDate() + "渠道流水号" + req.getSysTraceno());
			BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10009);
			throw e2;
		}		
		String acctStatus = esbRep_30013000201.getRepBody().getAcctStatus();
		myLog.error(logger, "账户状态：" + acctStatus);
		//2.设置返回报文	
		if("A".equals(acctStatus)){		
			//账户号
			rep.setActNo(req.getActNo());
			//账户类型
			rep.setActTyp("2");
			//户名
			rep.setActNam(esbRep_30013000201.getRepBody().getAcctName());
			//开户行
			rep.setActBnk("313229000442");
		}else{
			myLog.error(logger, "交行查询本行卡余额，卡状态异常，渠道日期" + req.getSysDate() + "渠道流水号" + req.getSysTraceno());
			BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10015);
			throw e2;
		}
		return rep;

	}
	
	/** 
	* @Title: hostQuery
	* @Description: 本行核心基本账户信息查询 
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30011000103    返回类型 
	* @throws 
	*/
	private ESB_REP_30013000201 hostQuery(REQ_10102 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "TXBRNO");
			txTel = jedis.get(COMMON_PREFIX + "TXTEL");
		}

		ESB_REQ_30013000201 esbReq_30013000201 = new ESB_REQ_30013000201(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30013000201.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30013000201.setReqSysHead(reqSysHead);

		ESB_REQ_30013000201.REQ_BODY reqBody_30013000201 = esbReq_30013000201.getReqBody();
		reqBody_30013000201.setBaseAcctNo(reqDto.getActNo());
		reqBody_30013000201.setProdType("");//产品类型
		reqBody_30013000201.setCcy(reqDto.getCcyCod());//币种
		reqBody_30013000201.setAcctSeqNo("");//账户 序号

		ESB_REP_30013000201 esbRep_30013000201 = forwardToESBService.sendToESB(esbReq_30013000201, reqBody_30013000201,
				ESB_REP_30013000201.class);
		return esbRep_30013000201;
	}

}
