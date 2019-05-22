/**   
* @Title: QR_BocmAcc.java 
* @Package com.fxbank.tpp.bocm.trade.esb 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月9日 上午10:24:56 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.trade.esb;

import java.math.BigDecimal;

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
import com.fxbank.tpp.bocm.dto.esb.REP_30063001301;
import com.fxbank.tpp.bocm.dto.esb.REQ_30063001301;
import com.fxbank.tpp.bocm.model.REP_20102;
import com.fxbank.tpp.bocm.model.REQ_20102;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;
import com.fxbank.tpp.esb.service.IForwardToESBService;

/** 
* @ClassName: QR_BocmAcc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author YePuLiang
* @date 2019年5月9日 上午10:24:56 
*  
*/
@Service("REQ_30063001301")
public class QR_BocmAcc extends TradeBase implements TradeExecutionStrategy{
	
	private static Logger logger = LoggerFactory.getLogger(QR_BocmAcc.class);
	
	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IForwardToBocmService forwardToBocmService;

	@Resource
	private MyJedis myJedis;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		
		
		MyLog myLog = logPool.get();
		REQ_30063001301 reqDto = (REQ_30063001301) dto;
		REQ_30063001301.REQ_BODY reqBody = reqDto.getReqBody();

		REQ_20102 req20102 = new REQ_20102(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());
		super.setBankno(myLog, dto, reqDto.getReqSysHead().getBranchId(), req20102);	//设置报文头中的行号信息
		req20102.setActTyp(reqBody.getAcctTpT());		
		req20102.setActNo(reqBody.getAcctNoT());
		req20102.setTxnTyp(reqBody.getTrnsTpT8());
		req20102.setFeeFlg(reqBody.getRcveWyT());
		req20102.setTxnAmt(Double.parseDouble(reqBody.getTrsrAmtT3()));
		myLog.info(logger, "发送账号信息 查询请求至交行");
		REP_20102 rep20102 = forwardToBocmService.sendToBocm(req20102, REP_20102.class);

		REP_30063001301 rep = new REP_30063001301();
		REP_30063001301.REQ_BODY repBody = rep.getReqBody();
		//姓名
		repBody.setNaT1(rep20102.getActNam());
		//手续费
		repBody.setFeeT3(rep20102.getFee().toString());
		repBody.setHndlPymntFeeT5("0.00");
		//开户行号
		repBody.setPyeeOpnBnkNoT1(rep20102.getActBnk());
		//账户类型
		repBody.setAcctTpT(rep20102.getActTyp());
		return rep;
	}

}
