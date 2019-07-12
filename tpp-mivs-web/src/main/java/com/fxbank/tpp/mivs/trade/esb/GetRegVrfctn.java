package com.fxbank.tpp.mivs.trade.esb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043003001;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.mivs.dto.esb.REP_50023000208;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000208;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_911_001_02;
import com.fxbank.tpp.mivs.dto.mivs.DTO_BASE;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_325_001_01;
import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsRegVrfctnInfoModel;
import com.fxbank.tpp.mivs.model.request.MIVS_324_001_01;
import com.fxbank.tpp.mivs.model.request.MIVS_324_001_01_GetRegVrfctn;
import com.fxbank.tpp.mivs.model.response.MIVS_325_001_01_RtrRegVrfctn;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.service.IMivsRegVrfctnInfoService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 登记信息联网核查申请
 * @Author: 王鹏
 * @Date: 2019/5/20 15:28
 */
@Service("REQ_50023000208")
public class GetRegVrfctn extends TradeBase implements TradeExecutionStrategy {

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
    private IMivsRegVrfctnInfoService mivsRegVrfctnInfoService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        REQ_50023000208 req = (REQ_50023000208) dto;//接收ESB请求报文
        REQ_50023000208.REQ_BODY reqBody = req.getReqBody();

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

        //拼发送人行322报文
        MIVS_324_001_01 mivs324 = new MIVS_324_001_01(new MyLog(), dto.getSysDate(),dto.getSysTime(), dto.getSysTraceno());
        MIVS_324_001_01_GetRegVrfctn.MsgHdr msgHdr = mivs324.getGetRegVrfctn().getMsgHdr();
        MIVS_324_001_01_GetRegVrfctn.VryDef vryDef = mivs324.getGetRegVrfctn().getVryDef();
        mivs324.getHeader().setOrigSender(bankNumber);
        mivs324.getHeader().setOrigReceiver("0000");
        msgHdr.getInstgPty().setInstgDrctPty(settlementBankNo);
        msgHdr.getInstgPty().setDrctPtyNm(lqtnBnkNmT1);
        msgHdr.getInstgPty().setInstgPty(bankNumber);
        msgHdr.getInstgPty().setPtyNm(bnkNmT);
        msgHdr.getInstdPty().setInstdDrctPty("0000");
        msgHdr.getInstdPty().setInstdPty("0000");
        if(reqBody.getMarketType().equals("ENT")){
            vryDef.getEnt().setEntNm(reqBody.getEntNm());
            vryDef.getEnt().setUniSocCdtCd(reqBody.getUniSocCdtCd());
            vryDef.getEnt().setNmOfLglPrsn(reqBody.getNmOfLglPrsn());
            vryDef.getEnt().setIdOfLglPrsn(reqBody.getIdOfLglPrsn());
        }else if(reqBody.getMarketType().equals("TRA")){
            vryDef.getSlfEplydPpl().setTraNm(reqBody.getTranm());
            vryDef.getSlfEplydPpl().setUniSocCdtCd(reqBody.getUniSocCdtCd());
            vryDef.getSlfEplydPpl().setNm(reqBody.getNm());
            vryDef.getSlfEplydPpl().setId(reqBody.getId());
        }
        vryDef.setAgtNm(reqBody.getAgtNm());
        vryDef.setAgtId(reqBody.getAgtId());
        vryDef.setOpNm(reqBody.getOpNm());

        //发送人行请求报文落地
        MivsRegVrfctnInfoModel regvrfctnInfoTableInsert = new MivsRegVrfctnInfoModel();
        myLog.info(logger, "Date = " + req.getSysDate());
        myLog.info(logger, "trace = " + req.getSysTraceno());
        myLog.info(logger, "SystemId = " + req.getReqSysHead().getSystemId());
        myLog.info(logger, "TranDate = " + req.getReqSysHead().getTranDate());
        myLog.info(logger, "SeqNo = " + req.getReqSysHead().getSeqNo());
        myLog.info(logger, "TranTimestamp = " + req.getReqSysHead().getTranTimestamp());
        regvrfctnInfoTableInsert.setPlat_date(req.getSysDate());
        regvrfctnInfoTableInsert.setPlat_trace(req.getSysTraceno());
        regvrfctnInfoTableInsert.setPlat_time(req.getSysTime());
        regvrfctnInfoTableInsert.setSystem_id(req.getReqSysHead().getSystemId());
        regvrfctnInfoTableInsert.setTran_date(req.getReqSysHead().getTranDate());
        regvrfctnInfoTableInsert.setSeq_no(req.getReqSysHead().getSeqNo());
        regvrfctnInfoTableInsert.setTran_time(req.getReqSysHead().getTranTimestamp());
        regvrfctnInfoTableInsert.setUser_id(req.getReqSysHead().getUserId());
        regvrfctnInfoTableInsert.setBranch_id(req.getReqSysHead().getBranchId());
        regvrfctnInfoTableInsert.setMivs_sts("00");
        regvrfctnInfoTableInsert.setMsg_id(msgHdr.getMsgId());
        regvrfctnInfoTableInsert.setCre_dt_tm(msgHdr.getCreDtTm());
        regvrfctnInfoTableInsert.setInstg_drct_pty(settlementBankNo);
        regvrfctnInfoTableInsert.setDrct_pty_nm(lqtnBnkNmT1);
        regvrfctnInfoTableInsert.setInstg_pty(bankNumber);
        regvrfctnInfoTableInsert.setPty_nm(bnkNmT);
        regvrfctnInfoTableInsert.setInstd_drct_pty(msgHdr.getInstdPty().getInstdDrctPty());
        regvrfctnInfoTableInsert.setInstd_pty(msgHdr.getInstdPty().getInstdPty());
        regvrfctnInfoTableInsert.setMarket_type(reqBody.getMarketType());
        regvrfctnInfoTableInsert.setEnt_nm(reqBody.getEntNm());
        regvrfctnInfoTableInsert.setUni_soc_cdt_cd(reqBody.getUniSocCdtCd());
        regvrfctnInfoTableInsert.setNm_of_lgl_prsn(reqBody.getNmOfLglPrsn());
        regvrfctnInfoTableInsert.setId_of_lgl_prsn(reqBody.getIdOfLglPrsn());
        regvrfctnInfoTableInsert.setTra_nm(reqBody.getTranm());
        regvrfctnInfoTableInsert.setNm(reqBody.getNm());
        regvrfctnInfoTableInsert.setId(reqBody.getId());
        regvrfctnInfoTableInsert.setAgt_nm(reqBody.getAgtNm());
        regvrfctnInfoTableInsert.setAgt_id(reqBody.getAgtId());
        regvrfctnInfoTableInsert.setOp_nm(reqBody.getOpNm());

        myLog.info(logger, "插入的表数据为regvrfctnInfoTableInsert:" + regvrfctnInfoTableInsert.toString());
        mivsRegVrfctnInfoService.insertMaster(regvrfctnInfoTableInsert); //插入数据库业务数据

        mivs324 = (MIVS_324_001_01) pmtsService.sendToPmts(mivs324); // 发送请求，实时等待990

        //为同步等待325，组合三要素
//        String channel = "325_" + mivs324.getHeader().getMesgID() + mivs324.getHeader().getOrigSendDate() + mivs324.getHeader().getMesgID();
        String channel = "325_" + mivs324.getHeader().getMesgID();
        myLog.info(logger, "324报文同步通道编号=[" + channel + "]");
        DTO_BASE dtoBase = syncCom.get(myLog, channel, super.queryTimeout911(myLog), TimeUnit.SECONDS);

        REP_50023000208 rep = new REP_50023000208();
        REP_50023000208.REP_BODY repBody = rep.getRepBody();

        //收到人行通讯回执，准备更新数据库状态
        MivsRegVrfctnInfoModel regVrfctnInfoTableUpdate = new MivsRegVrfctnInfoModel();
        //更新数据的主键赋值
        regVrfctnInfoTableUpdate.setPlat_date(req.getSysDate());
        regVrfctnInfoTableUpdate.setPlat_trace(req.getSysTraceno());
        if(dtoBase.getHead().getMesgType().equals("ccms.911.001.02")){  //根据911组织应答报文
            CCMS_911_001_02 ccms911 = (CCMS_911_001_02)dtoBase;
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10002,ccms911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());
            //根据人行返回报文更新数据库状态
            regVrfctnInfoTableUpdate.setMivs_sts("02");
            regVrfctnInfoTableUpdate.setProc_cd(ccms911.getDscrdMsgNtfctn().getDscrdInf().getPrcCd());
            regVrfctnInfoTableUpdate.setRjct_inf(ccms911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());
            throw e;
        }else if(dtoBase.getHead().getMesgType().equals("mivs.325.001.01")){
            MIVS_325_001_01 mivs325 = (MIVS_325_001_01)dtoBase;
            MIVS_325_001_01_RtrRegVrfctn.OrgnlBizQry orgnlBizQry = mivs325.getRtrRegVrfctn().getOrgnlBizQry();
            MIVS_325_001_01_RtrRegVrfctn.Rspsn.OprlErr oprlErr = mivs325.getRtrRegVrfctn().getRspsn().getOprlErr();
            if(oprlErr.getProcSts()!=null) {
                MivsTradeExecuteException e = new MivsTradeExecuteException(mivs325.getRtrRegVrfctn().getRspsn().getOprlErr().getProcCd(),mivs325.getRtrRegVrfctn().getRspsn().getOprlErr().getRjctinf());
                //更新数据库状态
                regVrfctnInfoTableUpdate.setMivs_sts("03");
                regVrfctnInfoTableUpdate.setProc_cd(oprlErr.getProcCd());
                regVrfctnInfoTableUpdate.setProc_sts(oprlErr.getProcSts());
                regVrfctnInfoTableUpdate.setRjct_inf(oprlErr.getRjctinf());
                throw e;
            }
            //附ESB应答报文
            repBody.setRslt(mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getRslt());
            repBody.setDataResrcD(mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getDataResrcDt());
            //查询数据库主表条数数据+附表内容数据，以325应答报文的“原报文标识号，原发起机构”为查询条件
            MivsRegVrfctnInfoModel infoModel = new MivsRegVrfctnInfoModel();
            infoModel.setOrig_dlv_msgid(orgnlBizQry.getMsgId());
            infoModel.setOrig_instg_pty(orgnlBizQry.getInstgPty().getInstgPty());
            infoModel.setDetail_flag("YES");
            infoModel = mivsRegVrfctnInfoService.selectMasterAndAttached(infoModel);
            myLog.debug(logger, "###infoModel :" + infoModel.toString());
            //赋BasInfo数据
            List<REP_50023000208.BasInfoEnt> basInfoEntArrayMsg = new ArrayList<REP_50023000208.BasInfoEnt>();
            List<REP_50023000208.BasInfOfSlfEplydPpl> basInfOfSlfEplydPplArrayMsg = new ArrayList<REP_50023000208.BasInfOfSlfEplydPpl>();
            if(infoModel.getBasInfo() != null && !infoModel.getBasInfo().isEmpty()) {
                for (MivsRegVrfctnInfoModel.BasInfo Info:infoModel.getBasInfo()) {
                    if(Info.getMarket_type().equals("ENT")) {
                        REP_50023000208.BasInfoEnt basInfoArMsg = new REP_50023000208.BasInfoEnt();
                        basInfoArMsg.setPgNb(Info.getPg_nb());
                        basInfoArMsg.setEntNm(Info.getEnt_nm());
                        basInfoArMsg.setUniSocCdtCd(Info.getUni_soc_cdt_cd());
                        basInfoArMsg.setCoTp(Info.getCo_tp());
                        basInfoArMsg.setDom(Info.getDom());
                        basInfoArMsg.setRegCptl(Info.getReg_cptl());
                        basInfoArMsg.setDtEst(Info.getDt_est());
                        basInfoArMsg.setOpPrdFrom(Info.getOp_prd_from());
                        basInfoArMsg.setOpPrdTo(Info.getOp_prd_to());
                        basInfoArMsg.setRegSts(Info.getReg_sts());
                        basInfoArMsg.setNmOfLglPrsn(Info.getNm_of_lgl_prsn());
                        basInfoArMsg.setRegAuth(Info.getReg_auth());
                        basInfoArMsg.setBizScp(Info.getBiz_scp());
                        basInfoArMsg.setDtAppr(Info.getDt_appr());
                        basInfoEntArrayMsg.add(basInfoArMsg);
                    }else if(Info.getMarket_type().equals("TRA")){
                        REP_50023000208.BasInfOfSlfEplydPpl basInfoArMsg = new REP_50023000208.BasInfOfSlfEplydPpl();
                        basInfoArMsg.setPgNb(Info.getPg_nb());
                        basInfoArMsg.setTraNm(Info.getTra_nm());
                        basInfoArMsg.setUniSocCdtCd(Info.getUni_soc_cdt_cd());
                        basInfoArMsg.setCoTp(Info.getCo_tp());
                        basInfoArMsg.setOpLoc(Info.getOp_loc());
                        basInfoArMsg.setFdAmt(Info.getFd_amt());
                        basInfoArMsg.setDtReg(Info.getDt_reg());
                        basInfoArMsg.setRegSts(Info.getReg_sts());
                        basInfoArMsg.setNm(Info.getNm());
                        basInfoArMsg.setRegAuth(Info.getReg_auth());
                        basInfoArMsg.setBizScp(Info.getBiz_scp());
                        basInfoArMsg.setDtAppr(Info.getDt_appr());
                        basInfOfSlfEplydPplArrayMsg.add(basInfoArMsg);
                    }
                }
                //插入ESB应答报文BasInfoList
                myLog.debug(logger, "repBody.setBasInfoEntList = " + basInfoEntArrayMsg.toString());
                repBody.setBasInfoEntList(basInfoEntArrayMsg);
                myLog.debug(logger, "repBody.setBasInfOfSlfEplydPplList = " + basInfOfSlfEplydPplArrayMsg.toString());
                repBody.setBasInfOfSlfEplydPplList(basInfOfSlfEplydPplArrayMsg);
            }
            //赋CoShrhdrFndInfo数据
            List<REP_50023000208.CoShrhdrFndInfo> coShrhdrFndInfoArrayMsg = new ArrayList<REP_50023000208.CoShrhdrFndInfo>();
            if(infoModel.getCoShrhdrFndInfo() != null && !infoModel.getCoShrhdrFndInfo().isEmpty()) {
                for (MivsRegVrfctnInfoModel.CoShrhdrFndInfo Info:infoModel.getCoShrhdrFndInfo()) {
                    REP_50023000208.CoShrhdrFndInfo coShrhdrFndInfoMsg = new REP_50023000208.CoShrhdrFndInfo();
                    coShrhdrFndInfoMsg.setPgNb(Info.getPg_nb());
                    coShrhdrFndInfoMsg.setCoShrhdrfndInfoNb(Info.getCo_shrhdrfnd_info_nb());
                    coShrhdrFndInfoMsg.setInvtrNm(Info.getInvtr_nm());
                    coShrhdrFndInfoMsg.setInvtrId(Info.getInvtr_id());
                    coShrhdrFndInfoMsg.setSubscrCptlConAmt(Info.getSubscr_cptl_con_amt());
                    coShrhdrFndInfoMsg.setActlCptlConAmt(Info.getActl_cptl_con_amt());
                    coShrhdrFndInfoMsg.setSubscrCptlConFm(Info.getSubscr_cptl_con_fm());
                    coShrhdrFndInfoMsg.setSubscrCptlConDt(Info.getSubscr_cptl_con_dt());
                    coShrhdrFndInfoArrayMsg.add(coShrhdrFndInfoMsg);
                }
                //插入ESB应答报文CoShrhdrFndInfo
                myLog.debug(logger, "repBody.coShrhdrFndInfoArrayMsg = " + coShrhdrFndInfoArrayMsg.toString());
                repBody.setCoShrhdrFndInfoList(coShrhdrFndInfoArrayMsg);
            }
            //赋DirSupSrMgrInfo数据
            List<REP_50023000208.DirSupSrMgrInfo> dirSupSrMgrInfoArrayMsg = new ArrayList<REP_50023000208.DirSupSrMgrInfo>();
            if(infoModel.getDirSupSrMgrInfo() != null && !infoModel.getDirSupSrMgrInfo().isEmpty()) {
                for (MivsRegVrfctnInfoModel.DirSupSrMgrInfo Info:infoModel.getDirSupSrMgrInfo()) {
                    REP_50023000208.DirSupSrMgrInfo dirSupSrMgrInfoMsg = new REP_50023000208.DirSupSrMgrInfo();
                    dirSupSrMgrInfoMsg.setPgNb(Info.getPg_nb());
                    dirSupSrMgrInfoMsg.setDirSupSrMgrInfoNb(Info.getDir_supsrsgr_info_nb());
                    dirSupSrMgrInfoMsg.setNm(Info.getNm());
                    dirSupSrMgrInfoMsg.setPosn(Info.getPosn());
                    dirSupSrMgrInfoArrayMsg.add(dirSupSrMgrInfoMsg);
                }
                //插入ESB应答报文DirSupSrMgrInfoList
                myLog.debug(logger, "repBody.dirSupSrMgrInfoArrayMsg = " + dirSupSrMgrInfoArrayMsg.toString());
                repBody.setDirSupSrMgrInfoList(dirSupSrMgrInfoArrayMsg);
            }
            //赋ChngInfo数据
            List<REP_50023000208.ChngInfo> chngInfoArrayMsg = new ArrayList<REP_50023000208.ChngInfo>();
            if(infoModel.getChngInfo() != null && !infoModel.getChngInfo().isEmpty()) {
                for (MivsRegVrfctnInfoModel.ChngInfo Info:infoModel.getChngInfo()) {
                    REP_50023000208.ChngInfo chngInfoMsg = new REP_50023000208.ChngInfo();
                    chngInfoMsg.setPgNb(Info.getPg_nb());
                    chngInfoMsg.setChngInfoNb(Info.getChng_info_nb());
                    chngInfoMsg.setChngItm(Info.getChng_itm());
                    chngInfoMsg.setBfChng(Info.getBf_chng());
                    chngInfoMsg.setAftChng(Info.getAft_chng());
                    chngInfoMsg.setDtOfChng(Info.getDt_of_chng());
                    chngInfoArrayMsg.add(chngInfoMsg);
                }
                //插入ESB应答报文chngInfoArrayMsg
                myLog.debug(logger, "repBody.chngInfoArrayMsg = " + chngInfoArrayMsg.toString());
                repBody.setChngInfoList(chngInfoArrayMsg);
            }
            //赋AbnmlBizInfo数据
            List<REP_50023000208.AbnmlBizInfo> abnInfoArrayMsg = new ArrayList<REP_50023000208.AbnmlBizInfo>();
            if(infoModel.getAbnmlBizInfo() != null && !infoModel.getAbnmlBizInfo().isEmpty()) {
                for (MivsRegVrfctnInfoModel.AbnmlBizInfo Info:infoModel.getAbnmlBizInfo()) {
                    REP_50023000208.AbnmlBizInfo abnInfoMsg = new REP_50023000208.AbnmlBizInfo();
                    abnInfoMsg.setPgNb(Info.getPg_nb());
                    abnInfoMsg.setAbnInfoNb(Info.getAbn_info_nb());
                    abnInfoMsg.setAbnmlCause(Info.getAbnml_cause());
                    abnInfoMsg.setAbnmlDate(Info.getAbnml_date());
                    abnInfoMsg.setAbnmlCauseDcsnAuth(Info.getAbnml_cause_dcsn_auth());
                    abnInfoMsg.setRmvCause(Info.getRmv_cause());
                    abnInfoMsg.setRmvDate(Info.getRmv_date());
                    abnInfoMsg.setRmvCauseDcsnAuth(Info.getRmv_cause_dcsn_auth());
                    abnInfoArrayMsg.add(abnInfoMsg);
                }
                //插入ESB应答报文abnInfoArrayMsg
                myLog.debug(logger, "repBody.abnInfoArrayMsg = " + abnInfoArrayMsg.toString());
                repBody.setAbnmlBizInfoList(abnInfoArrayMsg);
            }
            //赋IllDscrtInfo数据
            List<REP_50023000208.IllDscrtInfo> illInfoArrayMsg = new ArrayList<REP_50023000208.IllDscrtInfo>();
            if(infoModel.getIllDscrtInfo() != null && !infoModel.getIllDscrtInfo().isEmpty()) {
                for (MivsRegVrfctnInfoModel.IllDscrtInfo Info:infoModel.getIllDscrtInfo()) {
                    REP_50023000208.IllDscrtInfo illInfoMsg = new REP_50023000208.IllDscrtInfo();
                    illInfoMsg.setPgNb(Info.getPg_nb());
                    illInfoMsg.setIllInfoNb(Info.getIll_info_nb());
                    illInfoMsg.setIllDscrtCause(Info.getIll_dscrt_cause());
                    illInfoMsg.setAbnmlDate(Info.getAbnml_date());
                    illInfoMsg.setAbnmlCauseDcsnAuth(Info.getAbnml_cause_dcsn_auth());
                    illInfoMsg.setRmvCause(Info.getRmv_cause());
                    illInfoMsg.setRmvDate(Info.getRmv_date());
                    illInfoMsg.setRmvCauseDcsnAuth(Info.getRmv_cause_dcsn_auth());
                    illInfoArrayMsg.add(illInfoMsg);
                }
                //插入ESB应答报文illInfoArrayMsg
                myLog.debug(logger, "repBody.illInfoArrayMsg = " + illInfoArrayMsg.toString());
                repBody.setIllDscrtInfoList(illInfoArrayMsg);
            }
            //赋LicNull数据
            List<REP_50023000208.LicNull> licNullArrayMsg = new ArrayList<REP_50023000208.LicNull>();
            if(infoModel.getLicInfo() != null && !infoModel.getLicInfo().isEmpty()) {
                for (MivsRegVrfctnInfoModel.LicInfo Info:infoModel.getLicInfo()) {
                    REP_50023000208.LicNull licNullMsg = new REP_50023000208.LicNull();
                    licNullMsg.setPgNb(Info.getPg_nb());
                    licNullMsg.setLicInfoNb(Info.getPlat_time());
                    licNullMsg.setOrgnlOrCp(Info.getOrgnl_or_cp());
                    licNullMsg.setLicNullStmCntt(Info.getLic_null_stm_cntt());
                    licNullMsg.setLicNullStmDt(Info.getLic_null_stm_dt());
                    licNullMsg.setRplSts(Info.getRpl_sts());
                    licNullMsg.setRplDt(Info.getRpl_dt());
                    licNullMsg.setLicCpNb(Info.getLic_cp_nb());
                    licNullArrayMsg.add(licNullMsg);
                }
                //插入ESB应答报文licNullArrayMsg
                myLog.debug(logger, "repBody.licNullArrayMsg = " + licNullArrayMsg.toString());
                repBody.setLicNullList(licNullArrayMsg);
            }
            //更新数据库状态赋值
            regVrfctnInfoTableUpdate.setMivs_sts("04");
            regVrfctnInfoTableUpdate.setDetail_flag("NO");
        }

        myLog.debug(logger, "REP_BODY = " + repBody.toString());

        //更新业务数据表
        mivsRegVrfctnInfoService.uMasterAndiAttached(regVrfctnInfoTableUpdate);

        myLog.info(logger,"Json  " + JsonUtil.toJson(rep));
        return rep;
    }
}
