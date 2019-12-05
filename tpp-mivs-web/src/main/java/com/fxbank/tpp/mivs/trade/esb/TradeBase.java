package com.fxbank.tpp.mivs.trade.esb;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.tpp.esb.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043003001;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30043003001;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;

/**
 * @Description: 行内系统发起交易基类
 * @Author: 周勇沩
 * @Date: 2019-04-28 09:54:37
 */
public class TradeBase {
	private static Logger logger = LoggerFactory.getLogger(TradeBase.class);

	private static final String TIMEOUT_911 = "mivs.timeout_911";

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Resource
	private MyJedis myJedis;

	/**
	 * @Title: queryBankno @Description: 通过机构号查询渠道接口获取（机构号查行号） @param @param
	 *         myLog @param @param dto @param @param branchId
	 *         机构号 @param @return @param @throws SysTradeExecuteException
	 *         设定文件 @return ESB_REP_30043003001 返回类型 @throws
	 */
	public ESB_REP_30043003001 queryBankno(MyLog myLog, DataTransObject dto, String branchId)
			throws SysTradeExecuteException {
		if (branchId == null) {
			myLog.error(logger, "发起机构号不能为空");
			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
			throw e;
		}
		ESB_REQ_30043003001 esbReq_30043003001 = new ESB_REQ_30043003001(myLog, dto.getSysDate(), dto.getSysTime(),
				dto.getSysTraceno());
		/** Add by 叶浦亮 At 2019/12/3 15:48 For 不同渠道平台调用核心接口使用不一样的systemID */
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30043003001.getReqSysHead(), dto.getSourceType(), dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno()).build();
		reqSysHead.setBranchId(branchId);
		esbReq_30043003001.setReqSysHead(reqSysHead);
		ESB_REQ_30043003001.REQ_BODY reqBody_30043003001 = esbReq_30043003001.getReqBody();
		reqBody_30043003001.setBrchNoT4(branchId);
		ESB_REP_30043003001 esbRep_30043003001 = forwardToESBService.sendToESB(esbReq_30043003001, reqBody_30043003001,
				ESB_REP_30043003001.class);
		return esbRep_30043003001;
	}

	public Integer queryTimeout911(MyLog myLog) {
		Integer timeout = 0;
		try (Jedis jedis = myJedis.connect()) {
			String stimeout = jedis.get(TIMEOUT_911);
			if (stimeout == null) {
				timeout = 60;
			} else {
				try {
					timeout = Integer.valueOf(stimeout);
				} catch (Exception e) {
					myLog.error(logger, "ccms911报文同步等待超时时间配置异常，取默认值60");
					timeout = 60;
				}
			}
			return timeout;
		}
	}
	/*
	 * public String convPin(String oPin){ String nPin = oPin; //TODO return nPin; }
	 */

//	public getMsgHrd getmsghrd(MyLog myLog, DataTransObject dto)
////		throws SysTradeExecuteException {
////		if (branchId == null) {
////			myLog.error(logger, "发起机构号不能为空");
////			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
////			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
////			throw e;
////		}
//		getMsgHrd msgHrd = new getMsgHrd(new MyLog(), dto.getSysDate(),dto.getSysTime(), dto.getSysTraceno());
//		//发起行行号
//		msgHrd.getHeader().setOrigSender(bankNumber);
//		msgHrd.getHeader().setOrigReceiver("0000");
//		msgHrd.getTxPmtVrfctn().getMsgHdr().getInstgPty().setInstgDrctPty(settlementBankNo);
//		msgHrd.getTxPmtVrfctn().getMsgHdr().getInstgPty().setDrctPtyNm(lqtnBnkNmT1);
//		msgHrd.getTxPmtVrfctn().getMsgHdr().getInstgPty().setInstgPty(bankNumber);
//		msgHrd.getTxPmtVrfctn().getMsgHdr().getInstgPty().setPtyNm(bnkNmT);
//		return esbRep_30043003001;
//	}

	/**
	 * 判断字符串是否为空
	 */
	public String isOrNotNull(String stringNotNull, String msgNotNull, String isOrNotFlag) throws MivsTradeExecuteException {
		if (stringNotNull == null || stringNotNull.equals("") || stringNotNull.equals("null")) {
			if(isOrNotFlag == "Y") {
				MivsTradeExecuteException e = new MivsTradeExecuteException("MIVS_E_10001", msgNotNull + "必填");
				throw e;
			}else{
				return null;
			}
		}
		return stringNotNull;
	}

	/**
	 * ESB日期转换成人行日期
	 */
	public String dateToIsoDate(String idate, String msgNotNull, String isOrNotFlag) throws MivsTradeExecuteException{
		if(isOrNotFlag == "Y" && (idate == null || idate.equals(""))){
			MivsTradeExecuteException e = new MivsTradeExecuteException("MIVS_E_10002",msgNotNull + "必填");
			throw e;
		}else {
			if (idate.length() == 8) {
				StringBuilder sb = new StringBuilder(idate);//构造一个StringBuilder对象
				sb.insert(4, "-");
				sb.insert(7, "-");
				idate = sb.toString();
			}
			return idate;
		}
	}

	/**
	 * 手机号添加86
	 */
	public String moblePhoneAdd(String moblePhone, String isOrNotFlag) throws MivsTradeExecuteException{
		if(isOrNotFlag == "Y" && (moblePhone == null || moblePhone.equals(""))){
			MivsTradeExecuteException e = new MivsTradeExecuteException("MIVS_E_10003","手机号码必填");
			throw e;
		}else {
			String subT = moblePhone.substring(0, 2);
			if (!subT.equals("86")) {
				StringBuilder sb = new StringBuilder(moblePhone);//构造一个StringBuilder对象
				sb.insert(0, "86");
				moblePhone = sb.toString();
			}
			return moblePhone;
		}
	}

	/**
	 * 手机号删除86
	 */
	public String moblePhoneDel(String moblePhone) {
		if(moblePhone != null && !moblePhone.equals("")) {
			String subT = moblePhone.substring(0, 2);
			if (subT.equals("86")) {
				StringBuilder sb = new StringBuilder(moblePhone);//构造一个StringBuilder对象
				sb.delete(0, 2);
				moblePhone = sb.toString();
			}
		}
		return moblePhone;
	}
}
