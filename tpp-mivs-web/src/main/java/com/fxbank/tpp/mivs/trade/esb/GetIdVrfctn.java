package com.fxbank.tpp.mivs.trade.esb;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043003001;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.mivs.dto.esb.REP_50023000201;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000201;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_911_001_02;
import com.fxbank.tpp.mivs.dto.mivs.DTO_BASE;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_321_001_01;
import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import com.fxbank.tpp.mivs.model.mivstables.MivsIdVrfctnInfoTable;
import com.fxbank.tpp.mivs.model.request.MIVS_320_001_01;
import com.fxbank.tpp.mivs.model.request.MIVS_320_001_01_GetIdVrfctn;
import com.fxbank.tpp.mivs.model.response.MIVS_321_001_01_RtrIdVrfctn;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.service.IMivsIdvrfctnInfoService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Description: 行内系统发起企业手机号核查
 * @Author: 周勇沩
 * @Date: 2019-04-28 09:54:14
 */
@Service("REQ_50023000201")
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

    @Reference(version = "1.0.0")
    private IMivsIdvrfctnInfoService mivsIdvrfctnService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        REQ_50023000201 req = (REQ_50023000201) dto;
        REQ_50023000201.REQ_BODY reqBody = req.getReqBody();

		// 通过机构号查询渠道接口获取（机构号查行号）
        String branchId = req.getReqSysHead().getBranchId();
        String bankNumber = null, bnkNmT = null, settlementBankNo = null, lqtnBnkNmT1 = null;
		try {
		    //调用二代接口查询二代行号
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

		//拼发送人行320报文
        MIVS_320_001_01 mivs320 = new MIVS_320_001_01(new MyLog(), dto.getSysDate(),dto.getSysTime(), dto.getSysTraceno());
        MIVS_320_001_01_GetIdVrfctn.MsgHdr msgHdr = mivs320.getGetIdVrfctn().getMsgHdr();
        MIVS_320_001_01_GetIdVrfctn.VryDef vryDef = mivs320.getGetIdVrfctn().getVryDef();
		mivs320.getHeader().setOrigSender(bankNumber);
        mivs320.getHeader().setOrigReceiver("0000");
        msgHdr.getInstgPty().setInstgDrctPty(settlementBankNo);
        msgHdr.getInstgPty().setDrctPtyNm(lqtnBnkNmT1);
        msgHdr.getInstgPty().setInstgPty(bankNumber);
        msgHdr.getInstgPty().setPtyNm(bnkNmT);
        vryDef.setMobNb(reqBody.getMobNb());
        vryDef.setNm(reqBody.getNm());
        vryDef.setIdTp(reqBody.getIdTp());
        vryDef.setId(reqBody.getId());
        vryDef.setUniSocCdtCd(reqBody.getUniSocCdtCd());
        vryDef.setOpNm(reqBody.getOpNm());

        //发送人行请求数据落库
        MivsIdVrfctnInfoTable IdVrfctnTableInsertStart =  new MivsIdVrfctnInfoTable();
        myLog.info(logger, "Date = " + req.getSysDate());
        myLog.info(logger, "trace = " + req.getSysTraceno());
        myLog.info(logger, "SystemId = " + req.getReqSysHead().getSystemId());
        myLog.info(logger, "TranDate = " + req.getReqSysHead().getTranDate());
        myLog.info(logger, "SeqNo = " + req.getReqSysHead().getSeqNo());
        myLog.info(logger, "TranTimestamp = " + req.getReqSysHead().getTranTimestamp());
        IdVrfctnTableInsertStart.setPlat_date(req.getSysDate());
        IdVrfctnTableInsertStart.setPlat_trace(req.getSysTraceno());
        IdVrfctnTableInsertStart.setPlat_time(req.getSysTime());
        IdVrfctnTableInsertStart.setSystem_id(req.getReqSysHead().getSystemId());
        IdVrfctnTableInsertStart.setTran_date(req.getReqSysHead().getTranDate());
        IdVrfctnTableInsertStart.setSeq_no(req.getReqSysHead().getSeqNo());
        IdVrfctnTableInsertStart.setTran_time(req.getReqSysHead().getTranTimestamp());
        IdVrfctnTableInsertStart.setUser_id(req.getReqSysHead().getUserId());
        IdVrfctnTableInsertStart.setBranch_id(req.getReqSysHead().getBranchId());
        IdVrfctnTableInsertStart.setMivs_sts("00");
        IdVrfctnTableInsertStart.setMsg_id(msgHdr.getMsgId());
        IdVrfctnTableInsertStart.setCre_dt_tm(msgHdr.getCreDtTm());
        IdVrfctnTableInsertStart.setInstg_drct_pty(settlementBankNo);
        IdVrfctnTableInsertStart.setDrct_pty_nm(lqtnBnkNmT1);
        IdVrfctnTableInsertStart.setInstg_pty(bankNumber);
        IdVrfctnTableInsertStart.setPty_nm(bnkNmT);
        IdVrfctnTableInsertStart.setInstd_drct_pty("0000");
        IdVrfctnTableInsertStart.setInstd_pty("0000");
        IdVrfctnTableInsertStart.setMob_nb(reqBody.getMobNb());
        IdVrfctnTableInsertStart.setNm(reqBody.getNm());
        IdVrfctnTableInsertStart.setId_tp(reqBody.getIdTp());
        IdVrfctnTableInsertStart.setId(reqBody.getId());
        IdVrfctnTableInsertStart.setUni_soc_cdt_cd(reqBody.getUniSocCdtCd());
        IdVrfctnTableInsertStart.setBiz_reg_nb(reqBody.getBizRegNb());
        IdVrfctnTableInsertStart.setOp_nm(reqBody.getOpNm());

        mivsIdvrfctnService.insertStart(IdVrfctnTableInsertStart); //插入数据库业务数据
//        myLog.info(logger, IdVrfctnTableInsertStart.toString());

        mivs320 = (MIVS_320_001_01) pmtsService.sendToPmts(mivs320); // 发送请求，实时等待990

         String channel = "321_"+ mivs320.getGetIdVrfctn().getMsgHdr().getMsgId();  //为同步等待321，组合报文{H:的三要素

        DTO_BASE dtoBase = syncCom.get(myLog, channel, super.queryTimeout911(myLog), TimeUnit.SECONDS);

        //收到人行通讯回执，更新数据库状态
        MivsIdVrfctnInfoTable IdVrfctnTableUpdate = new MivsIdVrfctnInfoTable();
        //更新数据的主键赋值
        IdVrfctnTableUpdate.setPlat_date(req.getSysDate());
        IdVrfctnTableUpdate.setPlat_trace(req.getSysTraceno());

        REP_50023000201 rep = new REP_50023000201();
        if(dtoBase.getHead().getMesgType().equals("ccms.911.001.02")){  //根据911组织应答报文
        	CCMS_911_001_02 ccms911 = (CCMS_911_001_02)dtoBase;
        	MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10002,ccms911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());

        	IdVrfctnTableUpdate.setMivs_sts("02");
        	IdVrfctnTableUpdate.setProc_cd(ccms911.getDscrdMsgNtfctn().getDscrdInf().getPrcCd());
        	IdVrfctnTableUpdate.setRjct_inf(ccms911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());

            throw e;
        }else if(dtoBase.getHead().getMesgType().equals("mivs.321.001.01")){

            MIVS_321_001_01 mivs321 = (MIVS_321_001_01)dtoBase;
            MIVS_321_001_01_RtrIdVrfctn.Rspsn.VrfctnInf vrfctnInf = mivs321.getRtrIdVrfctn().getRspsn().getVrfctnInf();
            MIVS_321_001_01_RtrIdVrfctn.Rspsn.OprlErr oprlErr = mivs321.getRtrIdVrfctn().getRspsn().getOprlErr();

            REP_50023000201.REP_BODY repBody = rep.getRepBody();
            if(oprlErr.getProcSts()!=null) {
            	MivsTradeExecuteException e = new MivsTradeExecuteException(oprlErr.getProcCd(),oprlErr.getRjctinf());

            	IdVrfctnTableUpdate.setMivs_sts("03");
                IdVrfctnTableUpdate.setProc_cd(oprlErr.getProcCd());
                IdVrfctnTableUpdate.setProc_sts(oprlErr.getProcSts());
                IdVrfctnTableUpdate.setRjct_inf(oprlErr.getRjctinf());
                throw e;
            }

            //返回ESB报文
            repBody.setMobNb(vrfctnInf.getMobNb());
            repBody.setRslt(vrfctnInf.getRslt());
            repBody.setMobCrr(vrfctnInf.getMobCrr());
            repBody.setLocMobNb(vrfctnInf.getLocMobNb());
            repBody.setLocNmMobNb(vrfctnInf.getLocNmMobNb());
            repBody.setCdTp(vrfctnInf.getCdTp());
            repBody.setSts(vrfctnInf.getSts());

            //待更新数据库数据
            IdVrfctnTableUpdate.setMivs_sts("04");
            IdVrfctnTableUpdate.setMob_nb(vrfctnInf.getMobNb());
            IdVrfctnTableUpdate.setRslt(vrfctnInf.getRslt());
            IdVrfctnTableUpdate.setMob_crr(vrfctnInf.getMobCrr());
            IdVrfctnTableUpdate.setLoc_mob_nb(vrfctnInf.getLocMobNb());
            IdVrfctnTableUpdate.setLoc_nm_mob_nb(vrfctnInf.getLocNmMobNb());
            IdVrfctnTableUpdate.setCd_tp(vrfctnInf.getCdTp());
            IdVrfctnTableUpdate.setSts(vrfctnInf.getSts());
        }

        //更新业务数据表
        mivsIdvrfctnService.updateSts(IdVrfctnTableUpdate);

        return rep;
    }
}