package com.fxbank.tpp.mivs.trade.mivs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_325_001_01;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsRegVrfctnInfoModel;
import com.fxbank.tpp.mivs.model.response.MIVS_325_001_01_RtrRegVrfctn;
import com.fxbank.tpp.mivs.service.IMivsRegVrfctnInfoService;
import com.fxbank.tpp.mivs.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 登记信息联网核查申请应答
 * @Author: 王鹏
 * @Date: 2019/5/20 16:28
 */
@Service("MIVS_325_001_01")
public class RtrRegVrfctn extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(RtrRegVrfctn.class);

    @Resource
    private MyJedis myJedis;

    @Resource
    private LogPool logPool;

    @Reference(version = "1.0.0")
    private IMivsRegVrfctnInfoService mivsRegvrfctnInfoService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        MIVS_325_001_01 mivs325 = (MIVS_325_001_01) dto;
        MIVS_325_001_01_RtrRegVrfctn.MsgHdr msgHdr = mivs325.getRtrRegVrfctn().getMsgHdr();
        MIVS_325_001_01_RtrRegVrfctn.MsgPgntn msgPgntn = mivs325.getRtrRegVrfctn().getMsgPgntn();
        MIVS_325_001_01_RtrRegVrfctn.OrgnlBizQry orgnlBizQry = mivs325.getRtrRegVrfctn().getOrgnlBizQry();
        MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf vrfctnInf = mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf();
        MIVS_325_001_01_RtrRegVrfctn.Rspsn.OprlErr oprlErr = mivs325.getRtrRegVrfctn().getRspsn().getOprlErr();
        myLog.info(logger, "收到登记信息联网核查应答报文,进行同步处理");
        byte[] b325 = SerializeUtil.serialize(mivs325);
//        String channel = "325_" + mivs325.getHead().getOrigSender() + mivs325.getHead().getOrigSendDate() + mivs325.getHead().getMesgID();   //TODO 拼接原报文三要素
        String channel = "325_" + orgnlBizQry.getMsgId();   //TODO 拼接原报文三要素
        myLog.info(logger, "325报文同步通道编号=[" + channel + "]");

        if(oprlErr.getProcSts() != null){
            super.jedisPublish(myLog,channel.getBytes(), b325);
            myLog.info(logger, "发布至redis成功");
        }else{
            //查询主表数据
            MivsRegVrfctnInfoModel regvrfctnInfoTablSelectMaster = new MivsRegVrfctnInfoModel();
            regvrfctnInfoTablSelectMaster.setOrig_dlv_msgid(orgnlBizQry.getMsgId());
            regvrfctnInfoTablSelectMaster.setOrig_instg_pty(orgnlBizQry.getInstgPty().getInstgPty());
            regvrfctnInfoTablSelectMaster.setDetail_flag("NO");
            regvrfctnInfoTablSelectMaster = mivsRegvrfctnInfoService.selectMasterAndAttached(regvrfctnInfoTablSelectMaster);
            myLog.info(logger, "查询主表数据查询结果为：" + regvrfctnInfoTablSelectMaster.toString());
            //收到人行通讯回执，准备更新数据库状态，插入附表数据，在同一个事务进行
            myLog.info(logger, "收到人行通讯回执，准备更新数据库状态，插入附表数据，在同一个事务进行");
            MivsRegVrfctnInfoModel regvrfctnInfoUmasAndIatt = new MivsRegVrfctnInfoModel();
            //更新数据的主键赋值
            regvrfctnInfoUmasAndIatt.setPlat_date(regvrfctnInfoTablSelectMaster.getPlat_date());
            regvrfctnInfoUmasAndIatt.setPlat_trace(regvrfctnInfoTablSelectMaster.getPlat_trace());
            //主表应答信息赋值
            regvrfctnInfoUmasAndIatt.setMivs_sts("05");
            regvrfctnInfoUmasAndIatt.setRcv_msg_id(mivs325.getRtrRegVrfctn().getMsgHdr().getMsgId());
            regvrfctnInfoUmasAndIatt.setRcv_cre_dt_tm(mivs325.getRtrRegVrfctn().getMsgHdr().getCreDtTm());
            regvrfctnInfoUmasAndIatt.setPg_nb(mivs325.getRtrRegVrfctn().getMsgPgntn().getPgNb());
            regvrfctnInfoUmasAndIatt.setLast_pg_ind(msgPgntn.getLastPgInd());
            regvrfctnInfoUmasAndIatt.setRslt(vrfctnInf.getRslt());
            regvrfctnInfoUmasAndIatt.setData_resrc_dt(vrfctnInf.getDataResrcDt());
            regvrfctnInfoUmasAndIatt.setDetail_flag("YES");
            /*数据库中附表数据赋值开始*/
            //取循环数据BasInfo附表数据
            List<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.BasInfOfEnt> basInfoOfEntList = mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getRegInf().getBasInfOfEnt();
            if(basInfoOfEntList != null && !basInfoOfEntList.isEmpty()) {
                myLog.info(logger, "*** basInfoOfEntList的值为:" + basInfoOfEntList.toString());
                //赋循环数据BasInfo附表数据
                List<MivsRegVrfctnInfoModel.BasInfo> basInfoArrayMsg = new ArrayList<MivsRegVrfctnInfoModel.BasInfo>();
                myLog.info(logger,"照面信息的条数 = " + regvrfctnInfoTablSelectMaster.getBas_info_cnt());
                int basInfoCnt;
                if(regvrfctnInfoTablSelectMaster.getBas_info_cnt() == null ){
                    basInfoCnt = 0;
                }else{
                    basInfoCnt = regvrfctnInfoTablSelectMaster.getBas_info_cnt();
                }
                int basInfoNb = 0;
                for (MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.BasInfOfEnt Info:basInfoOfEntList) {
                    MivsRegVrfctnInfoModel.BasInfo basInfoMsg = new MivsRegVrfctnInfoModel.BasInfo();
                    basInfoMsg.setPlat_date(mivs325.getSysDate());
                    basInfoMsg.setPlat_trace(mivs325.getSysTraceno());
                    basInfoMsg.setPlat_time(mivs325.getSysTime());
                    basInfoMsg.setInstg_pty(msgHdr.getInstgPty().getInstgPty());
                    basInfoMsg.setMsg_id(msgHdr.getMsgId());
                    basInfoMsg.setCre_dt_tm(msgHdr.getCreDtTm());
                    basInfoMsg.setOrig_msg_id(orgnlBizQry.getMsgId());
                    basInfoMsg.setOrig_instg_drct_pty(orgnlBizQry.getInstgPty().getInstgDrctPty());
                    basInfoMsg.setOrig_instg_pty(orgnlBizQry.getInstgPty().getInstgPty());
                    basInfoMsg.setPg_nb(mivs325.getRtrRegVrfctn().getMsgPgntn().getPgNb());
                    basInfoMsg.setBas_info_nb(++basInfoNb);
                    basInfoMsg.setMarket_type("ENT");
                    basInfoMsg.setEnt_nm(Info.getEntNm());
                    basInfoMsg.setUni_soc_cdt_cd(Info.getUniSocCdtCd());
                    basInfoMsg.setCo_tp(Info.getCoTp());
                    basInfoMsg.setDom(Info.getDom());
                    basInfoMsg.setReg_cptl(Info.getRegCptl());
                    basInfoMsg.setDt_est(Info.getDtEst());
                    basInfoMsg.setOp_prd_from(Info.getOpPrdFrom());
                    basInfoMsg.setOp_prd_to(Info.getOpPrdTo());
                    basInfoMsg.setReg_sts(Info.getRegSts());
                    basInfoMsg.setNm_of_lgl_prsn(Info.getNmOfLglPrsn());
                    basInfoMsg.setReg_auth(Info.getRegAuth());
                    basInfoMsg.setBiz_scp(Info.getBizScp());
                    basInfoMsg.setDt_appr(Info.getDtAppr());
                    ++basInfoCnt;
                    basInfoArrayMsg.add(basInfoMsg);
                }
                regvrfctnInfoUmasAndIatt.setBasInfo(basInfoArrayMsg);
                regvrfctnInfoUmasAndIatt.setBas_info_cnt(basInfoCnt);
            }

            List<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.BasInfOfSlfEplydPpl> basInfOfSlfEplydPplList = mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getRegInf().getBasInfOfSlfEplydPpl();
            if(basInfOfSlfEplydPplList != null && !basInfOfSlfEplydPplList.isEmpty()) {
                myLog.info(logger, "*** basInfOfSlfEplydPplList的值为:" + basInfOfSlfEplydPplList.toString());
                //赋循环数据BasInfo附表数据
                List<MivsRegVrfctnInfoModel.BasInfo> basInfoArrayMsg = new ArrayList<MivsRegVrfctnInfoModel.BasInfo>();
                myLog.info(logger,"照面信息的条数 = " + regvrfctnInfoTablSelectMaster.getBas_info_cnt());
                int basInfoCnt;
                if(regvrfctnInfoTablSelectMaster.getBas_info_cnt() == null ){
                    basInfoCnt = 0;
                }else{
                    basInfoCnt = regvrfctnInfoTablSelectMaster.getBas_info_cnt();
                }
                int basInfoNb = 0;
                for (MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.BasInfOfSlfEplydPpl Info:basInfOfSlfEplydPplList) {
                    MivsRegVrfctnInfoModel.BasInfo basInfoMsg = new MivsRegVrfctnInfoModel.BasInfo();
                    basInfoMsg.setPlat_date(mivs325.getSysDate());
                    basInfoMsg.setPlat_trace(mivs325.getSysTraceno());
                    basInfoMsg.setPlat_time(mivs325.getSysTime());
                    basInfoMsg.setInstg_pty(msgHdr.getInstgPty().getInstgPty());
                    basInfoMsg.setMsg_id(msgHdr.getMsgId());
                    basInfoMsg.setCre_dt_tm(msgHdr.getCreDtTm());
                    basInfoMsg.setOrig_msg_id(orgnlBizQry.getMsgId());
                    basInfoMsg.setOrig_instg_drct_pty(orgnlBizQry.getInstgPty().getInstgDrctPty());
                    basInfoMsg.setOrig_instg_pty(orgnlBizQry.getInstgPty().getInstgPty());
                    basInfoMsg.setPg_nb(mivs325.getRtrRegVrfctn().getMsgPgntn().getPgNb());
                    basInfoMsg.setBas_info_nb(++basInfoNb);
                    basInfoMsg.setMarket_type("TRA");
                    basInfoMsg.setTra_nm(Info.getTraNm());
                    basInfoMsg.setUni_soc_cdt_cd(Info.getUniSocCdtCd());
                    basInfoMsg.setCo_tp(Info.getCoTp());
                    basInfoMsg.setOp_loc(Info.getOpLoc());
                    basInfoMsg.setFd_amt(Info.getFdAmt());
                    basInfoMsg.setDt_reg(Info.getDtReg());
                    basInfoMsg.setNm(Info.getNm());
                    basInfoMsg.setReg_sts(Info.getRegSts());;
                    basInfoMsg.setReg_auth(Info.getRegAuth());
                    basInfoMsg.setBiz_scp(Info.getBizScp());
                    basInfoMsg.setDt_appr(Info.getDtAppr());
                    ++basInfoCnt;
                    basInfoArrayMsg.add(basInfoMsg);
                }
                regvrfctnInfoUmasAndIatt.setBasInfo(basInfoArrayMsg);
                regvrfctnInfoUmasAndIatt.setBas_info_cnt(basInfoCnt);
            }

            //取循环数据CoShrhdrFndInfo附表数据
            List<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.CoShrhdrFndInfo> coShrhdrFndInfoList = mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getRegInf().getCoShrhdrFndInfo();
            if(coShrhdrFndInfoList != null && !coShrhdrFndInfoList.isEmpty()) {
                myLog.info(logger, "*** coShrhdrFndInfoList的值为:" + coShrhdrFndInfoList.toString());
                //赋循环数据CoShrhdrFndInfo附表数据
                List<MivsRegVrfctnInfoModel.CoShrhdrFndInfo> coShrhdrFndInfosArrayMsg = new ArrayList<MivsRegVrfctnInfoModel.CoShrhdrFndInfo>();
                myLog.info(logger,"企业股东及出资信息的条数 = " + regvrfctnInfoTablSelectMaster.getCo_shrhdrfnd_info_cnt());
                int coShrhdrFndInfoCnt;
                if(regvrfctnInfoTablSelectMaster.getCo_shrhdrfnd_info_cnt() == null ){
                    coShrhdrFndInfoCnt = 0;
                }else{
                    coShrhdrFndInfoCnt = regvrfctnInfoTablSelectMaster.getCo_shrhdrfnd_info_cnt();
                }
                int coShrhdrFndInfoNb = 0;
                for (MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.CoShrhdrFndInfo Info:coShrhdrFndInfoList) {
                    MivsRegVrfctnInfoModel.CoShrhdrFndInfo coShrhdrFndInfoMsg = new MivsRegVrfctnInfoModel.CoShrhdrFndInfo();
                    coShrhdrFndInfoMsg.setPlat_date(mivs325.getSysDate());
                    coShrhdrFndInfoMsg.setPlat_trace(mivs325.getSysTraceno());
                    coShrhdrFndInfoMsg.setPlat_time(mivs325.getSysTime());
                    coShrhdrFndInfoMsg.setInstg_pty(msgHdr.getInstgPty().getInstgPty());
                    coShrhdrFndInfoMsg.setMsg_id(msgHdr.getMsgId());
                    coShrhdrFndInfoMsg.setCre_dt_tm(msgHdr.getCreDtTm());
                    coShrhdrFndInfoMsg.setOrig_msg_id(orgnlBizQry.getMsgId());
                    coShrhdrFndInfoMsg.setOrig_instg_drct_pty(orgnlBizQry.getInstgPty().getInstgDrctPty());
                    coShrhdrFndInfoMsg.setOrig_instg_pty(orgnlBizQry.getInstgPty().getInstgPty());
                    coShrhdrFndInfoMsg.setPg_nb(mivs325.getRtrRegVrfctn().getMsgPgntn().getPgNb());
                    coShrhdrFndInfoMsg.setCo_shrhdrfnd_info_nb(++coShrhdrFndInfoNb);
                    coShrhdrFndInfoMsg.setNatl_prsn_flag(Info.getNatlPrsnFlag());
                    coShrhdrFndInfoMsg.setInvtr_nm(Info.getInvtrNm());
                    coShrhdrFndInfoMsg.setInvtr_id(Info.getInvtrId());
                    coShrhdrFndInfoMsg.setSubscr_cptl_con_amt(Info.getSubscrCptlConAmt());
                    coShrhdrFndInfoMsg.setActl_cptl_con_amt(Info.getActlCptlConAmt());
                    coShrhdrFndInfoMsg.setSubscr_cptl_con_fm(Info.getSubscrCptlConFm());
                    coShrhdrFndInfoMsg.setSubscr_cptl_con_dt(Info.getSubscrCptlConDt());
                    ++coShrhdrFndInfoCnt;
                    coShrhdrFndInfosArrayMsg.add(coShrhdrFndInfoMsg);
                }
                regvrfctnInfoUmasAndIatt.setCoShrhdrFndInfo(coShrhdrFndInfosArrayMsg);
                regvrfctnInfoUmasAndIatt.setCo_shrhdrfnd_info_cnt(coShrhdrFndInfoCnt);
            }

            //取循环数据DirSupSrMgrInfo附表数据
            List<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.DirSupSrMgrInfo> dirSupSrMgrInfoList = mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getRegInf().getDirSupSrMgrInfo();
            if(dirSupSrMgrInfoList != null && !dirSupSrMgrInfoList.isEmpty()) {
                myLog.info(logger, "*** dirSupSrMgrInfoList的值为:" + dirSupSrMgrInfoList.toString());
                //赋循环数据DirSupSrMgrInfo附表数据
                List<MivsRegVrfctnInfoModel.DirSupSrMgrInfo> dirSupSrMgrInfosArrayMsg = new ArrayList<MivsRegVrfctnInfoModel.DirSupSrMgrInfo>();
                myLog.info(logger,"董事监事及高管信息的条数 = " + regvrfctnInfoTablSelectMaster.getDir_supsrsgr_info_cnt());
                int dirSupSrMgrInfoCnt;
                if(regvrfctnInfoTablSelectMaster.getDir_supsrsgr_info_cnt() == null ){
                    dirSupSrMgrInfoCnt = 0;
                }else{
                    dirSupSrMgrInfoCnt = regvrfctnInfoTablSelectMaster.getDir_supsrsgr_info_cnt();
                }
                int dirSupSrMgrInfoNb = 0;
                for (MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.DirSupSrMgrInfo Info:dirSupSrMgrInfoList) {
                    MivsRegVrfctnInfoModel.DirSupSrMgrInfo dirSupSrMgrInfoMsg = new MivsRegVrfctnInfoModel.DirSupSrMgrInfo();
                    dirSupSrMgrInfoMsg.setPlat_date(mivs325.getSysDate());
                    dirSupSrMgrInfoMsg.setPlat_trace(mivs325.getSysTraceno());
                    dirSupSrMgrInfoMsg.setPlat_time(mivs325.getSysTime());
                    dirSupSrMgrInfoMsg.setInstg_pty(msgHdr.getInstgPty().getInstgPty());
                    dirSupSrMgrInfoMsg.setMsg_id(msgHdr.getMsgId());
                    dirSupSrMgrInfoMsg.setCre_dt_tm(msgHdr.getCreDtTm());
                    dirSupSrMgrInfoMsg.setOrig_msg_id(orgnlBizQry.getMsgId());
                    dirSupSrMgrInfoMsg.setOrig_instg_drct_pty(orgnlBizQry.getInstgPty().getInstgDrctPty());
                    dirSupSrMgrInfoMsg.setOrig_instg_pty(orgnlBizQry.getInstgPty().getInstgPty());
                    dirSupSrMgrInfoMsg.setPg_nb(mivs325.getRtrRegVrfctn().getMsgPgntn().getPgNb());
                    dirSupSrMgrInfoMsg.setDir_supsrsgr_info_nb(++dirSupSrMgrInfoNb);
                    dirSupSrMgrInfoMsg.setNm(Info.getNm());
                    dirSupSrMgrInfoMsg.setPosn(Info.getPosn());
                    ++dirSupSrMgrInfoCnt;
                    dirSupSrMgrInfosArrayMsg.add(dirSupSrMgrInfoMsg);
                }
                regvrfctnInfoUmasAndIatt.setDirSupSrMgrInfo(dirSupSrMgrInfosArrayMsg);
                regvrfctnInfoUmasAndIatt.setDir_supsrsgr_info_cnt(dirSupSrMgrInfoCnt);
            }

            //取循环数据ChngInfo附表数据
            List<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.ChngInfo> chngInfoList = mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getRegInf().getChngInfo();
            if(chngInfoList != null && !chngInfoList.isEmpty()) {
                myLog.info(logger, "*** chngInfoList的值为:" + chngInfoList.toString());
                //赋循环数据ChngInfo附表数据
                List<MivsRegVrfctnInfoModel.ChngInfo> chngInfoArrayMsg = new ArrayList<MivsRegVrfctnInfoModel.ChngInfo>();
                myLog.info(logger,"变更信息的条数 = " + regvrfctnInfoTablSelectMaster.getChng_info_cnt());
                int chgInfoCnt;
                if(regvrfctnInfoTablSelectMaster.getChng_info_cnt() == null ){
                    chgInfoCnt = 0;
                }else{
                    chgInfoCnt = regvrfctnInfoTablSelectMaster.getChng_info_cnt();
                }
                int chgInfoNb = 0;
                for (MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.ChngInfo Info:chngInfoList) {
                    MivsRegVrfctnInfoModel.ChngInfo chngInfoMsg = new MivsRegVrfctnInfoModel.ChngInfo();
                    chngInfoMsg.setPlat_date(mivs325.getSysDate());
                    chngInfoMsg.setPlat_trace(mivs325.getSysTraceno());
                    chngInfoMsg.setPlat_time(mivs325.getSysTime());
                    chngInfoMsg.setInstg_pty(msgHdr.getInstgPty().getInstgPty());
                    chngInfoMsg.setMsg_id(msgHdr.getMsgId());
                    chngInfoMsg.setCre_dt_tm(msgHdr.getCreDtTm());
                    chngInfoMsg.setOrig_msg_id(orgnlBizQry.getMsgId());
                    chngInfoMsg.setOrig_instg_drct_pty(orgnlBizQry.getInstgPty().getInstgDrctPty());
                    chngInfoMsg.setOrig_instg_pty(orgnlBizQry.getInstgPty().getInstgPty());
                    chngInfoMsg.setPg_nb(mivs325.getRtrRegVrfctn().getMsgPgntn().getPgNb());
                    chngInfoMsg.setChng_info_nb(++chgInfoNb);
                    chngInfoMsg.setChng_itm(Info.getChngItm());
                    chngInfoMsg.setAft_chng(Info.getAftChng());
                    chngInfoMsg.setBf_chng(Info.getBfChng());
                    chngInfoMsg.setDt_of_chng(Info.getDtOfChng());
                    ++chgInfoCnt;
                    chngInfoArrayMsg.add(chngInfoMsg);
                }
                regvrfctnInfoUmasAndIatt.setChngInfo(chngInfoArrayMsg);
                regvrfctnInfoUmasAndIatt.setChng_info_cnt(chgInfoCnt);
            }

            //取循环数据AbnmlBizInfo附表数据
            List<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.AbnmlBizInfo> abnmlBizInfoList = mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getRegInf().getAbnmlBizInfo();
            if(abnmlBizInfoList != null && !abnmlBizInfoList.isEmpty()) {
                myLog.info(logger, "*** abnmlBizInfoList的值为:" + abnmlBizInfoList.toString());
                //赋循环数据AbnmlBizInfo附表数据
                List<MivsRegVrfctnInfoModel.AbnmlBizInfo> abnInfoArrayMsg = new ArrayList<MivsRegVrfctnInfoModel.AbnmlBizInfo>();
                myLog.info(logger,"异常经营信息的条数 = " + regvrfctnInfoTablSelectMaster.getAbnml_biz_info_cnt());
                int abnInfoCnt;
                if(regvrfctnInfoTablSelectMaster.getAbnml_biz_info_cnt() == null ){
                    abnInfoCnt = 0;
                }else{
                    abnInfoCnt = regvrfctnInfoTablSelectMaster.getAbnml_biz_info_cnt();
                }
                int abnInfoNb = 0;
                for (MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.AbnmlBizInfo Info:abnmlBizInfoList) {
                    MivsRegVrfctnInfoModel.AbnmlBizInfo abnInfoMsg = new MivsRegVrfctnInfoModel.AbnmlBizInfo();
                    abnInfoMsg.setPlat_date(mivs325.getSysDate());
                    abnInfoMsg.setPlat_trace(mivs325.getSysTraceno());
                    abnInfoMsg.setPlat_time(mivs325.getSysTime());
                    abnInfoMsg.setInstg_pty(msgHdr.getInstgPty().getInstgPty());
                    abnInfoMsg.setMsg_id(msgHdr.getMsgId());
                    abnInfoMsg.setCre_dt_tm(msgHdr.getCreDtTm());
                    abnInfoMsg.setOrig_msg_id(orgnlBizQry.getMsgId());
                    abnInfoMsg.setOrig_instg_drct_pty(orgnlBizQry.getInstgPty().getInstgDrctPty());
                    abnInfoMsg.setOrig_instg_pty(orgnlBizQry.getInstgPty().getInstgPty());
                    abnInfoMsg.setPg_nb(mivs325.getRtrRegVrfctn().getMsgPgntn().getPgNb());
                    abnInfoMsg.setAbn_info_nb(++abnInfoNb);
                    abnInfoMsg.setAbnml_cause(Info.getAbnmlCause());
                    abnInfoMsg.setAbnml_date(Info.getAbnmlDate());
                    abnInfoMsg.setAbnml_cause_dcsn_auth(Info.getAbnmlCauseDcsnAuth());
                    abnInfoMsg.setRmv_cause(Info.getRmvCause());
                    abnInfoMsg.setRmv_date(Info.getRmvDate());
                    abnInfoMsg.setRmv_cause_dcsn_auth(Info.getRmvCauseDcsnAuth());
                    ++abnInfoCnt;
                    abnInfoArrayMsg.add(abnInfoMsg);
                }
                regvrfctnInfoUmasAndIatt.setAbnmlBizInfo(abnInfoArrayMsg);
                regvrfctnInfoUmasAndIatt.setAbnml_biz_info_cnt(abnInfoCnt);
            }

            //取循环数据IllDscrtInfo附表数据
            List<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.IllDscrtInfo> illDscrtInfoList = mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getRegInf().getIllDscrtInfo();
            if(illDscrtInfoList != null && !illDscrtInfoList.isEmpty()) {
                myLog.info(logger, "*** illDscrtInfoList的值为:" + illDscrtInfoList.toString());
                //赋循环数据IllDscrtInfo附表数据
                List<MivsRegVrfctnInfoModel.IllDscrtInfo> illInfoArrayMsg = new ArrayList<MivsRegVrfctnInfoModel.IllDscrtInfo>();
                myLog.info(logger,"严重违法失信信息的条数 = " + regvrfctnInfoTablSelectMaster.getIll_dscrt_info_cnt());
                int illInfoCnt;
                if(regvrfctnInfoTablSelectMaster.getIll_dscrt_info_cnt() == null ){
                    illInfoCnt = 0;
                }else{
                    illInfoCnt = regvrfctnInfoTablSelectMaster.getIll_dscrt_info_cnt();
                }
                int illInfoNb = 0;
                for (MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.IllDscrtInfo Info:illDscrtInfoList) {
                    MivsRegVrfctnInfoModel.IllDscrtInfo illInfoMsg = new MivsRegVrfctnInfoModel.IllDscrtInfo();
                    illInfoMsg.setPlat_date(mivs325.getSysDate());
                    illInfoMsg.setPlat_trace(mivs325.getSysTraceno());
                    illInfoMsg.setPlat_time(mivs325.getSysTime());
                    illInfoMsg.setInstg_pty(msgHdr.getInstgPty().getInstgPty());
                    illInfoMsg.setMsg_id(msgHdr.getMsgId());
                    illInfoMsg.setCre_dt_tm(msgHdr.getCreDtTm());
                    illInfoMsg.setOrig_msg_id(orgnlBizQry.getMsgId());
                    illInfoMsg.setOrig_instg_drct_pty(orgnlBizQry.getInstgPty().getInstgDrctPty());
                    illInfoMsg.setOrig_instg_pty(orgnlBizQry.getInstgPty().getInstgPty());
                    illInfoMsg.setPg_nb(mivs325.getRtrRegVrfctn().getMsgPgntn().getPgNb());
                    illInfoMsg.setIll_info_nb(++illInfoNb);
                    illInfoMsg.setIll_dscrt_cause(Info.getIllDscrtCause());
                    illInfoMsg.setAbnml_date(Info.getAbnmlDate());
                    illInfoMsg.setAbnml_cause_dcsn_auth(Info.getAbnmlCauseDcsnAuth());
                    illInfoMsg.setRmv_cause(Info.getRmvCause());
                    illInfoMsg.setRmv_date(Info.getRmvDate());
                    illInfoMsg.setRmv_cause_dcsn_auth(Info.getRmvCauseDcsnAuth());
                    ++illInfoCnt;
                    illInfoArrayMsg.add(illInfoMsg);
                }
                regvrfctnInfoUmasAndIatt.setIllDscrtInfo(illInfoArrayMsg);
                regvrfctnInfoUmasAndIatt.setIll_dscrt_info_cnt(illInfoCnt);
            }

            //取循环数据LicNull附表数据
            List<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.LicNull> licNullList = mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getRegInf().getLicNull();
            if(licNullList != null && !licNullList.isEmpty()) {
                myLog.info(logger, "*** licNullList的值为:" + licNullList.toString());
                //赋循环数据LicNull附表数据
                List<MivsRegVrfctnInfoModel.LicInfo> licInfoArrayMsg = new ArrayList<MivsRegVrfctnInfoModel.LicInfo>();
                myLog.info(logger,"营业执照作废声明的条数 = " + regvrfctnInfoTablSelectMaster.getLic_null_cnt());
                int licInfoCnt;
                if(regvrfctnInfoTablSelectMaster.getLic_null_cnt() == null ){
                    licInfoCnt = 0;
                }else{
                    licInfoCnt = regvrfctnInfoTablSelectMaster.getLic_null_cnt();
                }
                int licInfoNb = 0;
                for (MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.LicNull Info:licNullList) {
                    MivsRegVrfctnInfoModel.LicInfo licInfoMsg = new MivsRegVrfctnInfoModel.LicInfo();
                    licInfoMsg.setPlat_date(mivs325.getSysDate());
                    licInfoMsg.setPlat_trace(mivs325.getSysTraceno());
                    licInfoMsg.setPlat_time(mivs325.getSysTime());
                    licInfoMsg.setInstg_pty(msgHdr.getInstgPty().getInstgPty());
                    licInfoMsg.setMsg_id(msgHdr.getMsgId());
                    licInfoMsg.setCre_dt_tm(msgHdr.getCreDtTm());
                    licInfoMsg.setOrig_msg_id(orgnlBizQry.getMsgId());
                    licInfoMsg.setOrig_instg_drct_pty(orgnlBizQry.getInstgPty().getInstgDrctPty());
                    licInfoMsg.setOrig_instg_pty(orgnlBizQry.getInstgPty().getInstgPty());
                    licInfoMsg.setPg_nb(mivs325.getRtrRegVrfctn().getMsgPgntn().getPgNb());
                    licInfoMsg.setLic_info_nb(++licInfoNb);
                    licInfoMsg.setOrgnl_or_cp(Info.getOrgnlOrCp());
                    licInfoMsg.setLic_null_stm_cntt(Info.getLicNullStmCntt());
                    licInfoMsg.setLic_null_stm_dt(Info.getLicNullStmDt());
                    licInfoMsg.setRpl_sts(Info.getRplSts());
                    licInfoMsg.setRpl_dt(Info.getRplDt());
                    licInfoMsg.setLic_cp_nb(Info.getLicCpNb());
                    ++licInfoCnt;
                    licInfoArrayMsg.add(licInfoMsg);
                }
                regvrfctnInfoUmasAndIatt.setLicInfo(licInfoArrayMsg);
                regvrfctnInfoUmasAndIatt.setLic_null_cnt(licInfoCnt);
            }

            myLog.info(logger, "regvrfctnInfoUmasAndIatt的值为:" + regvrfctnInfoUmasAndIatt.toString());
            /*数据库中附表数据赋值结束*/
            //
            mivsRegvrfctnInfoService.uMasterAndiAttached(regvrfctnInfoUmasAndIatt);
            if(mivs325.getRtrRegVrfctn().getMsgPgntn().getLastPgInd().equals("true")){
                super.jedisPublish(myLog,channel.getBytes(), b325);
                myLog.info(logger, "此报文为最后一页，发布至redis成功");
            }
        }

        return mivs325;
    }

}