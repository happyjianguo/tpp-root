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
import com.fxbank.tpp.mivs.model.mivsmodel.MivsIdVrfctnInfoModel;
import com.fxbank.tpp.mivs.model.request.MIVS_320_001_01;
import com.fxbank.tpp.mivs.model.request.MIVS_320_001_01_GetIdVrfctn;
import com.fxbank.tpp.mivs.model.response.MIVS_321_001_01_RtrIdVrfctn;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.service.IMivsIdVrfctnInfoService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Description: 行内系统发起企业手机号核查
 * 当需要对手机号码进行真实性核查时，发起参与机构组此报文发送到MIVS，MIVS将核查结果使用手机号码联网核查应答报文返回给发起参与机构。
 * @Author: 周勇沩，王鹏
 * @Date: 2019-04-28 09:54:14
 * @Update: 2019-05-26
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
    private IMivsIdVrfctnInfoService mivsIdVrfctnInfoService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        REQ_50023000201 req = (REQ_50023000201) dto;
        REQ_50023000201.REQ_BODY reqBody = req.getReqBody();
        if(reqBody.getBizRegNb() != null && !reqBody.getBizRegNb().equals("") &&
                reqBody.getUniSocCdtCd() != null && !reqBody.getUniSocCdtCd().equals("")){
            MivsTradeExecuteException e = new MivsTradeExecuteException("MIVS_E_00001","统一社会信用代码和工商注册号只能填写其一");
            throw e;
        }

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
		mivs320.getHeader().setOrigSender(settlementBankNo);
        mivs320.getHeader().setOrigReceiver("0000");
        msgHdr.getInstgPty().setInstgDrctPty(settlementBankNo);
        msgHdr.getInstgPty().setDrctPtyNm(lqtnBnkNmT1);
        msgHdr.getInstgPty().setInstgPty(bankNumber);
        msgHdr.getInstgPty().setPtyNm(bnkNmT);
        msgHdr.getInstdPty().setInstdDrctPty("0000");
        msgHdr.getInstdPty().setInstdPty("0000");
        vryDef.setMobNb(moblePhoneAdd(reqBody.getMobNb()));
        vryDef.setNm(reqBody.getNm());
        vryDef.setIdTp(reqBody.getIdTp());
        vryDef.setId(reqBody.getId());
        if(reqBody.getUniSocCdtCd() != null && !reqBody.getUniSocCdtCd().equals("")) {
            vryDef.setUniSocCdtCd(reqBody.getUniSocCdtCd());
        }else if(reqBody.getBizRegNb() != null && !reqBody.getBizRegNb().equals("")){
        vryDef.setBizRegNb(reqBody.getBizRegNb());
        }
        vryDef.setOpNm(reqBody.getOpNm());

        //发送人行请求数据落库
        MivsIdVrfctnInfoModel idVrfctnTableInsert =  new MivsIdVrfctnInfoModel();
        myLog.info(logger, "Date = " + req.getSysDate());
        myLog.info(logger, "trace = " + req.getSysTraceno());
        myLog.info(logger, "SystemId = " + req.getReqSysHead().getSystemId());
        myLog.info(logger, "TranDate = " + req.getReqSysHead().getTranDate());
        myLog.info(logger, "SeqNo = " + req.getReqSysHead().getSeqNo());
        myLog.info(logger, "TranTimestamp = " + req.getReqSysHead().getTranTimestamp());
        idVrfctnTableInsert.setPlat_date(req.getSysDate());
        idVrfctnTableInsert.setPlat_trace(req.getSysTraceno());
        idVrfctnTableInsert.setPlat_time(req.getSysTime());
        idVrfctnTableInsert.setSystem_id(req.getReqSysHead().getSystemId());
        idVrfctnTableInsert.setTran_date(req.getReqSysHead().getTranDate());
        idVrfctnTableInsert.setSeq_no(req.getReqSysHead().getSeqNo());
        idVrfctnTableInsert.setTran_time(req.getReqSysHead().getTranTimestamp());
        idVrfctnTableInsert.setUser_id(req.getReqSysHead().getUserId());
        idVrfctnTableInsert.setBranch_id(req.getReqSysHead().getBranchId());
        idVrfctnTableInsert.setMivs_sts("00");
        idVrfctnTableInsert.setMsg_id(msgHdr.getMsgId());
        idVrfctnTableInsert.setCre_dt_tm(msgHdr.getCreDtTm());
        idVrfctnTableInsert.setInstg_drct_pty(settlementBankNo);
        idVrfctnTableInsert.setDrct_pty_nm(lqtnBnkNmT1);
        idVrfctnTableInsert.setInstg_pty(bankNumber);
        idVrfctnTableInsert.setPty_nm(bnkNmT);
        idVrfctnTableInsert.setInstd_drct_pty(msgHdr.getInstdPty().getInstdDrctPty());
        idVrfctnTableInsert.setInstd_pty(msgHdr.getInstdPty().getInstdPty());
        idVrfctnTableInsert.setMob_nb(reqBody.getMobNb());
        idVrfctnTableInsert.setNm(reqBody.getNm());
        idVrfctnTableInsert.setId_tp(reqBody.getIdTp());
        idVrfctnTableInsert.setId(reqBody.getId());
        idVrfctnTableInsert.setUni_soc_cdt_cd(reqBody.getUniSocCdtCd());
        idVrfctnTableInsert.setBiz_reg_nb(reqBody.getBizRegNb());
        idVrfctnTableInsert.setOp_nm(reqBody.getOpNm());

        mivsIdVrfctnInfoService.insertStart(idVrfctnTableInsert); //插入数据库业务数据
//        myLog.info(logger, idVrfctnTableInsert.toString());

        myLog.info(logger, "发送320报文为：" + mivs320.toString());
        mivs320 = (MIVS_320_001_01) pmtsService.sendToPmts(mivs320); // 发送请求，实时等待990

        String channel = "321_"+ mivs320.getHeader().getMesgID();  //为同步等待321，组合报文{H:的三要素
        myLog.info(logger,"320报文发送通道编号=[" + channel);
        DTO_BASE dtoBase = syncCom.get(myLog, channel, super.queryTimeout911(myLog), TimeUnit.SECONDS);

        //收到人行通讯回执，更新数据库状态
        MivsIdVrfctnInfoModel idVrfctnTableUpdate = new MivsIdVrfctnInfoModel();
        //更新数据的主键赋值
        idVrfctnTableUpdate.setPlat_date(req.getSysDate());
        idVrfctnTableUpdate.setPlat_trace(req.getSysTraceno());

        REP_50023000201 rep = new REP_50023000201();
        if(dtoBase.getHead().getMesgType().equals("ccms.911.001.02")){  //根据911组织应答报文
        	CCMS_911_001_02 ccms911 = (CCMS_911_001_02)dtoBase;
        	MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10002,ccms911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());

            //根据人行返回报文更新数据库状态
        	idVrfctnTableUpdate.setMivs_sts("02");
        	idVrfctnTableUpdate.setProc_cd(ccms911.getDscrdMsgNtfctn().getDscrdInf().getPrcCd());
        	idVrfctnTableUpdate.setRjct_inf(ccms911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());
            //更新业务数据表
            mivsIdVrfctnInfoService.updateSts(idVrfctnTableUpdate);

            throw e;
        }else if(dtoBase.getHead().getMesgType().equals("mivs.321.001.01")){

            MIVS_321_001_01 mivs321 = (MIVS_321_001_01)dtoBase;
            MIVS_321_001_01_RtrIdVrfctn.Rspsn.VrfctnInf vrfctnInf = mivs321.getRtrIdVrfctn().getRspsn().getVrfctnInf();
            MIVS_321_001_01_RtrIdVrfctn.Rspsn.OprlErr oprlErr = mivs321.getRtrIdVrfctn().getRspsn().getOprlErr();

            REP_50023000201.REP_BODY repBody = rep.getRepBody();
            if(oprlErr.getProcSts()!=null) {
            	MivsTradeExecuteException e = new MivsTradeExecuteException(oprlErr.getProcCd(),oprlErr.getRjctInf());

            	idVrfctnTableUpdate.setMivs_sts("03");
                idVrfctnTableUpdate.setProc_cd(oprlErr.getProcCd());
                idVrfctnTableUpdate.setProc_sts(oprlErr.getProcSts());
                idVrfctnTableUpdate.setRjct_inf(oprlErr.getRjctInf());

                //更新业务数据表
                mivsIdVrfctnInfoService.updateSts(idVrfctnTableUpdate);
                throw e;
            }

            //返回ESB报文
            repBody.setMobNb(vrfctnInf.getMobNb());
            repBody.setRslt(vrfctnInf.getRslt());
            repBody.setMobCrr(vrfctnInf.getMobCrr());
            repBody.setLocMobNb(moblePhoneDel(vrfctnInf.getLocMobNb()));
            repBody.setLocNmMobNb(vrfctnInf.getLocNmMobNb());
            repBody.setCdTp(vrfctnInf.getCdTp());
            repBody.setSts(vrfctnInf.getSts());

            //待更新数据库数据
            idVrfctnTableUpdate.setMivs_sts("04");
            idVrfctnTableUpdate.setRcv_msg_id(mivs321.getRtrIdVrfctn().getMsgHdr().getMsgId());
            idVrfctnTableUpdate.setRcv_cre_dt_tm(mivs321.getRtrIdVrfctn().getMsgHdr().getCreDtTm());
            idVrfctnTableUpdate.setMob_nb(moblePhoneDel(vrfctnInf.getMobNb()));
            idVrfctnTableUpdate.setRslt(vrfctnInf.getRslt());
            idVrfctnTableUpdate.setMob_crr(vrfctnInf.getMobCrr());
            idVrfctnTableUpdate.setLoc_mob_nb(vrfctnInf.getLocMobNb());
            idVrfctnTableUpdate.setLoc_nm_mob_nb(vrfctnInf.getLocNmMobNb());
            idVrfctnTableUpdate.setCd_tp(vrfctnInf.getCdTp());
            idVrfctnTableUpdate.setSts(vrfctnInf.getSts());
        }

        //更新业务数据表
        mivsIdVrfctnInfoService.updateSts(idVrfctnTableUpdate);

        return rep;
    }
}