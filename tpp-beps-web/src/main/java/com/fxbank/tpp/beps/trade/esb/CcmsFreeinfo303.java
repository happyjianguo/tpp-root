package com.fxbank.tpp.beps.trade.esb;

import java.util.concurrent.TimeUnit;

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
import com.fxbank.tpp.beps.dto.beps.CCMS_900_001_02;
import com.fxbank.tpp.beps.dto.beps.CCMS_911_001_02;
import com.fxbank.tpp.beps.dto.beps.DTO_BASE;
import com.fxbank.tpp.beps.dto.esb.REP_30042801901;
import com.fxbank.tpp.beps.dto.esb.REQ_30042801901;
import com.fxbank.tpp.beps.exception.BepsTradeExecuteException;
import com.fxbank.tpp.beps.model.CCMS_900_001_02_CmonConf;
import com.fxbank.tpp.beps.model.request.CCMS_303_001_02;
import com.fxbank.tpp.beps.model.request.CCMS_303_001_02_FreeFrmt;
import com.fxbank.tpp.beps.service.IForwardToPmtsService;
import com.fxbank.tpp.beps.sync.SyncCom;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043003001;

@Service("REQ_30042801901")
public class CcmsFreeinfo303 extends TradeBase implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CcmsFreeinfo303.class);

	@Resource
	private LogPool logPool;

	@Resource
	private SyncCom syncCom;

	@Reference(version = "1.0.0")
	private IForwardToPmtsService pmtsService;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		// TODO Auto-generated method stub
		MyLog myLog = logPool.get();

		REQ_30042801901 req_30042801901 = (REQ_30042801901) dto;
		REQ_30042801901.REQ_BODY reqBody = req_30042801901.getReqBody();

		// 通过机构号查询渠道接口获取（机构号查行号）
		String branchId = req_30042801901.getReqSysHead().getBranchId();
		ESB_REP_30043003001 esbRep_30043003001 = null;
		try {
			// 调用二代接口查询二代行号
			esbRep_30043003001 = queryBankno(myLog, dto, branchId);
		} catch (SysTradeExecuteException e) {
			myLog.error(logger, "通过本行机构号查询人行行号失败，机构号：" + branchId, e);
			throw e;
		}

		// 组303往账发送报文
		CCMS_303_001_02 ccms303 = new CCMS_303_001_02(new MyLog(), dto.getSysDate(), dto.getSysTime(),
				dto.getSysTraceno());
		CCMS_303_001_02_FreeFrmt.GrpHdr GrpHdr = ccms303.getFreeFrmt().getGrpHdr();
		CCMS_303_001_02_FreeFrmt.FreeFrmtInf freeFrmtInf = ccms303.getFreeFrmt().getFreeFrmtInf();
		freeFrmtInf.setMsgCntt(reqBody.getInfoCntT1());
		GrpHdr.setSysCd("HVPS");
		GrpHdr.setRmk("HVPS_FREEMSG");
		GrpHdr.getInstgPty().setInstgDrctPty(esbRep_30043003001.getRepBody().getSettlementBankNo());
		GrpHdr.getInstgPty().setInstgPty(esbRep_30043003001.getRepBody().getBankNumber());
		GrpHdr.getInstdPty().setInstdDrctPty("0000");
		GrpHdr.getInstdPty().setInstdPty("0000");
		ccms303.getHeader().setOrigSender(esbRep_30043003001.getRepBody().getBankNumber());
		ccms303.getHeader().setOrigReceiver("0000");;


		// 登记数据库

		ccms303 = (CCMS_303_001_02) pmtsService.sendToPmts(ccms303); // 发送请求，实时等待990

		String channel = "900_" + ccms303.getFreeFrmt().getGrpHdr().getMsgId(); // 为同步等待303回执报文，组合报文{H:的三要素
		myLog.info(logger, "303报文发送通道编号=[" + channel);
		DTO_BASE dtoBase = syncCom.get(myLog, channel, super.queryTimeout911(myLog), TimeUnit.SECONDS);

		// 定义更新数据库的对象

		REP_30042801901 rep_30042801901 = new REP_30042801901();
		if (dtoBase.getHead().getMesgType().equals("ccms.911.001.02")) { // 根据911组织应答报文
			CCMS_911_001_02 ccms911 = (CCMS_911_001_02) dtoBase;
			BepsTradeExecuteException e = new BepsTradeExecuteException(BepsTradeExecuteException.BEPS_E_10002,
					ccms911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());

			// 根据人行返回报文更新数据库状态

			throw e;
		} else if (dtoBase.getHead().getMesgType().equals("ccms.900.001.02")) {// 通用处理确认报文

			CCMS_900_001_02 ccms900 = (CCMS_900_001_02) dtoBase;
			CCMS_900_001_02_CmonConf.CmonConfInf cmonConfInf = ccms900.getCmonConf().getCmonConfInf();

			// 客户端返回报文
			REP_30042801901.REP_BODY repBody = rep_30042801901.getRepBody();
			repBody.setChnlDtT2(dto.getSysDate().toString());
			repBody.setChnlSrlNoT2(dto.getSysTraceno().toString());

		}
//更新数据库语句
		return rep_30042801901;
	}

}
