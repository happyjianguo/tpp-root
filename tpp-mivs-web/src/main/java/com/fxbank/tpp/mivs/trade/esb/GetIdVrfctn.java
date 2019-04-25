package com.fxbank.tpp.mivs.trade.esb;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043003001;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30043003001;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.mivs.dto.esb.REP_30041000901;
import com.fxbank.tpp.mivs.dto.esb.REQ_30041000901;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_911_001_02;
import com.fxbank.tpp.mivs.dto.mivs.DTO_BASE;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_321_001_01;
import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import com.fxbank.tpp.mivs.model.CCMS_911_001_02_DscrdMsgNtfctn;
import com.fxbank.tpp.mivs.model.MIVS_320_001_01;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("REQ_30041000901")
public class GetIdVrfctn extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(ComConf.class);

    @Resource
    private LogPool logPool;

    @Resource
    private SyncCom syncCom;

    @Reference(version = "1.0.0")
    private IForwardToPmtsService pmtsService;
    
    @Reference(version = "1.0.0")
    private IForwardToESBService forwardToESBService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        REQ_30041000901 req = (REQ_30041000901) dto;
        REQ_30041000901.REQ_BODY reqBody = req.getReqBody();

        MIVS_320_001_01 mivs320 = new MIVS_320_001_01(new MyLog(), 20190909, 12323, 12);
        
		// 通过机构号查询渠道接口获取（机构号查行号）
        String branchId = req.getReqSysHead().getBranchId();
        String bankNumber = null, bnkNmT = null, settlementBankNo = null, lqtnBnkNmT1 = null;
		try {
			ESB_REP_30043003001 esbRep_30043003001 = queryBankno(myLog, dto, branchId);
			// 发起行人行行号
			bankNumber = esbRep_30043003001.getRepBody().getBankNumber();
			// 发起行人行行名
			bnkNmT = esbRep_30043003001.getRepBody().getBnkNmT();
			// 发起行人行清算行号
			settlementBankNo = esbRep_30043003001.getRepBody().getSettlementBankNo();
			// 发起行人行清算行名
			lqtnBnkNmT1 = esbRep_30043003001.getRepBody().getLqtnBnkNmT1();
		} catch (SysTradeExecuteException e) {
			myLog.error(logger, "通过本行机构号查询人行行号失败，机构号：" + branchId,e);
			throw e;
		}
		myLog.info(logger, "通过本行机构号查询人行行号成功，机构号：" + branchId + "，人行行号：" + bankNumber);
		
		//发起行行号
        mivs320.getHeader().setOrigSender(bankNumber);  
        mivs320.getHeader().setOrigReceiver("0000");
        mivs320.getGetIdVrfctn().getMsgHdr().getInstgPty().setInstgDrctPty(settlementBankNo);
        mivs320.getGetIdVrfctn().getMsgHdr().getInstgPty().setDrctPtyNm(lqtnBnkNmT1);
        mivs320.getGetIdVrfctn().getMsgHdr().getInstgPty().setInstgPty(bankNumber);
        mivs320.getGetIdVrfctn().getMsgHdr().getInstgPty().setPtyNm(bnkNmT);

        mivs320.getGetIdVrfctn().getVryDef().setMobNb(reqBody.getMobNb());
        mivs320.getGetIdVrfctn().getVryDef().setNm(reqBody.getNm());
        mivs320.getGetIdVrfctn().getVryDef().setIdTp(reqBody.getIdTp());
        mivs320.getGetIdVrfctn().getVryDef().setId(reqBody.getId());
        mivs320.getGetIdVrfctn().getVryDef().setUniSocCdtCd(reqBody.getUniSocCdtCd());
        mivs320.getGetIdVrfctn().getVryDef().setOpNm(reqBody.getOpNm());

        mivs320 = (MIVS_320_001_01) pmtsService.sendToPmts(mivs320); // 发送请求，实时等待990

        String msgid= mivs320.getGetIdVrfctn().getMsgHdr().getMsgId();    //为同步等待321，组合三要素
        String channel = "321_"+msgid;
        DTO_BASE dtoBase = syncCom.get(myLog, channel, 60, TimeUnit.SECONDS);

        REP_30041000901 rep = new REP_30041000901();
        if(dtoBase.getHead().getMesgType().equals("ccms.911.001.02")){  //根据911组织应答报文
        	CCMS_911_001_02 ccmc911 = (CCMS_911_001_02)dtoBase;
        	MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10002,
        			MivsTradeExecuteException.TCEXERRCODECONV.get(MivsTradeExecuteException.MIVS_E_10002)
        			+"("+ccmc911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf()+")");
            throw e;
        }else if(dtoBase.getHead().getMesgType().equals("mivs.321.001.01")){
            MIVS_321_001_01 mivs321 = (MIVS_321_001_01)dtoBase;
            REP_30041000901.REP_BODY repBody = rep.getRepBody();
            if(mivs321.getRtrIdVrfctn().getRspsn().getOprlErr().getProcCd()!=null) {
            	MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10002,
            			MivsTradeExecuteException.TCEXERRCODECONV.get(MivsTradeExecuteException.MIVS_E_10002)
            			+"("+mivs321.getRtrIdVrfctn().getRspsn().getOprlErr().getRjctinf()+")");
                throw e;
            }
            repBody.setMobNb(mivs321.getRtrIdVrfctn().getRspsn().getVrfctnInf().getMobNb());
            repBody.setRslt(mivs321.getRtrIdVrfctn().getRspsn().getVrfctnInf().getRslt());
            repBody.setMobCrr(mivs321.getRtrIdVrfctn().getRspsn().getVrfctnInf().getMobCrr());
            repBody.setLocMobNb(mivs321.getRtrIdVrfctn().getRspsn().getVrfctnInf().getLocMobNb());
            repBody.setLocNmMobNb(mivs321.getRtrIdVrfctn().getRspsn().getVrfctnInf().getLocNmMobNb());
            repBody.setCdTp(mivs321.getRtrIdVrfctn().getRspsn().getVrfctnInf().getCdTp());
            repBody.setSts(mivs321.getRtrIdVrfctn().getRspsn().getVrfctnInf().getSts());
        }

        return rep;
    }
}