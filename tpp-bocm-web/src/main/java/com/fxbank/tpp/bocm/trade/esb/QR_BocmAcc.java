package com.fxbank.tpp.bocm.trade.esb;

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
import com.fxbank.tpp.bocm.dto.esb.REP_30063001301;
import com.fxbank.tpp.bocm.dto.esb.REQ_30063001301;
import com.fxbank.tpp.bocm.model.REP_10102;
import com.fxbank.tpp.bocm.model.REQ_10102;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;
import com.fxbank.tpp.bocm.util.NumberUtil;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30063000103;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30063000103;
import com.fxbank.tpp.esb.service.IForwardToESBService;

/** 
* @ClassName: QR_BocmAcc 
* @Description: 柜面账户信息查询
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

		REQ_10102 req10102 = new REQ_10102(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());
		super.setBankno(myLog, dto, reqDto.getReqSysHead().getBranchId(), req10102);	//设置报文头中的行号信息
		req10102.setActTyp(reqBody.getAcctTpT());		
		req10102.setActNo(reqBody.getAcctNoT());
		//TRNS_TP_T8（交易类型）00 存款  01 取款  02 转出  03 转入
		req10102.setTxnTyp(reqBody.getTrnsTpT8());
		req10102.setFeeFlg(reqBody.getRcveWyT());
		
		//交易金额
		String amt = reqBody.getTrsrAmtT3();
		//交易金额补零
		req10102.setTxnAmt(NumberUtil.addPoint(Double.parseDouble(amt)));
		myLog.info(logger, "发送账号信息 查询请求至交行");
		
		REP_10102 rep10102 = forwardToBocmService.sendToBocm(req10102, REP_10102.class);
		
		

		REP_30063001301 rep = new REP_30063001301();
		REP_30063001301.REQ_BODY repBody = rep.getReqBody();
		//姓名
		repBody.setNaT1(rep10102.getActNam());
		
		String jhFee = NumberUtil.removePointToString(rep10102.getFee());
		//手续费
		repBody.setFeeT3(jhFee);

		repBody.setHndlPymntFeeT5("0");
		//开户行号
		repBody.setPyeeOpnBnkNoT1(rep10102.getActBnk());
		//账户类型
		repBody.setAcctTpT(rep10102.getActTyp());
		return rep;
	}
	
	public ESB_REP_30063000103 queryFee(REQ_30063001301 reqDto,REP_10102 rep20102) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		txTel = reqDto.getReqSysHead().getUserId();
		txBrno = reqDto.getReqSysHead().getBranchId();
		ESB_REQ_30063000103 esbReq_30063000103 = new ESB_REQ_30063000103(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30063000103.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		reqSysHead.setProgramId(reqDto.getReqSysHead().getProgramId());
		esbReq_30063000103.setReqSysHead(reqSysHead);

		ESB_REQ_30063000103.REQ_BODY reqBody_30063000103 = esbReq_30063000103.getReqBody();
		esbReq_30063000103.setReqSysHead(reqSysHead);	
        //DEP-存款WTD-取款TRA-转账
		//00 存款
		//01 取款
		//02 转出
		//03 转入
		String tran_type = reqDto.getReqBody().getTrnsTpT8();
		if("00".equals(tran_type)){
			reqBody_30063000103.setChargeSenece("DEP");
		}
		if("01".equals(tran_type)){
			reqBody_30063000103.setChargeSenece("WTD");
		}
		if("02".equals(tran_type)||"03".equals(tran_type)){
			reqBody_30063000103.setChargeSenece("TRA");
		}		
		reqBody_30063000103.setOthBankCode(rep20102.getActBnk());
		reqBody_30063000103.setTranAmt(reqDto.getReqBody().getTrsrAmtT3());
		reqBody_30063000103.setTranCcy("CNY");

		ESB_REP_30063000103 esbRep_30063000103 = forwardToESBService.sendToESB(esbReq_30063000103, reqBody_30063000103,
				ESB_REP_30063000103.class);
		return esbRep_30063000103;
	}

}
