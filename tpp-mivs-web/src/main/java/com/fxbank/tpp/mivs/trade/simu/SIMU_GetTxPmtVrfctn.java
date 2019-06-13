package com.fxbank.tpp.mivs.trade.simu;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_322_001_01;
import com.fxbank.tpp.mivs.model.CCMS_990_001_02;
import com.fxbank.tpp.mivs.model.response.MIVS_323_001_01_RtrTxPmtVrfctn;
import com.fxbank.tpp.mivs.model.sim.MIVS_323_001_01;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 模拟用
 * @Author: 周勇沩
 * @Date: 2019-04-23 22:25:09
 */
@Service("MIVS_322_001_01")
public class SIMU_GetTxPmtVrfctn implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(ComConf.class);

	@Resource
	private LogPool logPool;

	@Resource
	private SyncCom syncCom;

	@Reference(version = "1.0.0")
	private IForwardToPmtsService pmtsService;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		// 1、接收322请求
		MIVS_322_001_01 mivs322 = (MIVS_322_001_01) dto;
		// 2、根据322内容模拟返回990
		CCMS_990_001_02 mivs = new CCMS_990_001_02(new MyLog(), dto.getSysDate(), dto.getSysTime(), 12);
		mivs.getHeader().setOrigSender("313131000008"); // TODO 通过机构号查询渠道接口获取（机构号查行号）
		mivs.getHeader().setOrigReceiver("0000");
		mivs.getHeader().setMesgID(mivs322.getHead().getMesgID());
		mivs.getComConf().getConfInf().setMT("MT");
		mivs.getComConf().getConfInf().setMsgId(mivs322.getHead().getMesgID());
		mivs.getComConf().getConfInf().setMsgPrcCd("PM1I0000");
		mivs.getComConf().getConfInf().setMsgRefId("msgRefId");
		mivs.getComConf().getConfInf().setOrigSndDt("20190909");
		mivs.getComConf().getConfInf().setOrigSndr("origSndr");

		try {
			pmtsService.sendToPmtsNoWait(mivs);
		} catch (SysTradeExecuteException e) {
			e.printStackTrace();
		}

		// 3、根据322内容模拟返回323
		MIVS_323_001_01 mivs323 = new MIVS_323_001_01(new MyLog(), dto.getSysDate(), dto.getSysTime(), 13);
//		//编值
//		ArrayList al = new ArrayList();
//		al.add("TxAuthCd001");
		//赋循环数据
		List<MIVS_323_001_01_RtrTxPmtVrfctn.Rspsn.VrfctnInf.TxpmtInf> txpmtInf = new ArrayList<MIVS_323_001_01_RtrTxPmtVrfctn.Rspsn.VrfctnInf.TxpmtInf>();
		for (int i=0 ; i<4; i++) {
			MIVS_323_001_01_RtrTxPmtVrfctn.Rspsn.VrfctnInf.TxpmtInf arraymsg = new MIVS_323_001_01_RtrTxPmtVrfctn.Rspsn.VrfctnInf.TxpmtInf();
			arraymsg.setTxAuthCd("TxAuthCd00"+i);
			arraymsg.setTxAuthNm("国税局");
			arraymsg.setTxpySts("00"+i);
//			myLog.info(logger, "i=" + i);
			txpmtInf.add(arraymsg);
		}
//		myLog.info(logger,"循环列表内容为：" + txpmtInf);

		mivs323.getHeader().setOrigSender("313131000008");
		mivs323.getHeader().setOrigReceiver("0000");
		mivs323.getHeader().setMesgID(mivs323.getHeader().getMesgID());
		mivs323.getRtrTxPmtVrfctn().getMsgHdr().getInstgPty().setInstgDrctPty("0000");
		mivs323.getRtrTxPmtVrfctn().getMsgHdr().getInstgPty().setInstgPty("00000001200197");
        mivs323.getRtrTxPmtVrfctn().getMsgHdr().setMsgId(mivs323.getRtrTxPmtVrfctn().getMsgHdr().getMsgId());
        mivs323.getRtrTxPmtVrfctn().getOrgnlBizQry().setMsgId(mivs322.getHead().getMesgID());
		mivs323.getRtrTxPmtVrfctn().getOrgnlBizQry().getInstgPty().setInstgDrctPty(mivs322.getTxPmtVrfctn().getMsgHdr().getInstgPty().getInstgDrctPty());
		mivs323.getRtrTxPmtVrfctn().getOrgnlBizQry().getInstgPty().setInstgPty(mivs322.getTxPmtVrfctn().getMsgHdr().getInstgPty().getInstgPty());

		mivs323.getRtrTxPmtVrfctn().getRspsn().getVrfctnInf().setRslt("MCHD");
		mivs323.getRtrTxPmtVrfctn().getRspsn().getVrfctnInf().setDataResrcDt("2019-04-29");
		if(txpmtInf !=null && !txpmtInf.isEmpty()){
			mivs323.getRtrTxPmtVrfctn().getRspsn().getVrfctnInf().setTxpmtInf(txpmtInf);
		}

		pmtsService.sendToPmtsNoWait(mivs323);
		
		/**
		//3、根据322内容模拟返回911
		CCMS_911_001_02 ccms911 = new CCMS_911_001_02(new MyLog(), 20190909, 122321, 13);
		
		ccms911.getHeader().setOrigSender("313131000008"); // TODO 通过机构号查询渠道接口获取（机构号查行号）
		ccms911.getHeader().setOrigReceiver("0000");
		ccms911.getHeader().setMesgID(mivs322.getHead().getMesgID());
		ccms911.getDscrdMsgNtfctn().getDscrdInf().setMT("mivs.321.001.01");
		ccms911.getDscrdMsgNtfctn().getDscrdInf().setMsgId(mivs322.getGetIdVrfctn().getMsgHdr().getMsgId());
		ccms911.getDscrdMsgNtfctn().getDscrdInf().setPrcCd("O1106");
		ccms911.getDscrdMsgNtfctn().getDscrdInf().setRjctInf("原报文类型非法");
		ccms911.getDscrdMsgNtfctn().getDscrdInf().setMsgRefId("msgRefId");
		ccms911.getDscrdMsgNtfctn().getDscrdInf().setOrigSndDt("20190909");
		ccms911.getDscrdMsgNtfctn().getDscrdInf().setOrigSndr("origSndr");
		pmtsService.sendToPmtsNoWait(ccms911);
		**/
		return mivs322;
	}
}