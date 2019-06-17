package com.fxbank.tpp.mivs.trade.simu;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_324_001_01;
import com.fxbank.tpp.mivs.model.CCMS_911_001_02;
import com.fxbank.tpp.mivs.model.CCMS_990_001_02;
import com.fxbank.tpp.mivs.model.response.MIVS_325_001_01_RtrRegVrfctn;
import com.fxbank.tpp.mivs.model.sim.MIVS_325_001_01;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 模拟来账
 * @Author: 王鹏
 * @Date: 2019/6/4 14:58
 */
@Service("MIVS_324_001_01")
public class SIMU_GetRegVrfctn implements TradeExecutionStrategy {

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
        // 1、接收324请求
        MIVS_324_001_01 mivs324 = (MIVS_324_001_01) dto;
        // 2、根据324内容模拟返回990
        CCMS_990_001_02 mivs = new CCMS_990_001_02(new MyLog(), 20190909, 122321, 12);
        mivs.getHeader().setOrigSender("313131000008"); // TODO 通过机构号查询渠道接口获取（机构号查行号）
        mivs.getHeader().setOrigReceiver("0000");
        mivs.getHeader().setMesgID(mivs324.getHead().getMesgID());
        mivs.getComConf().getConfInf().setMT("MT");
        mivs.getComConf().getConfInf().setMsgId(mivs324.getHead().getMesgID());
        mivs.getComConf().getConfInf().setMsgPrcCd("PM1I0000");
        mivs.getComConf().getConfInf().setMsgRefId("msgRefId");
        mivs.getComConf().getConfInf().setOrigSndDt("20190909");
        mivs.getComConf().getConfInf().setOrigSndr("origSndr");

        try {
            pmtsService.sendToPmtsNoWait(mivs);
        } catch (SysTradeExecuteException e) {
            e.printStackTrace();
        }

        // 3、根据324内容模拟返回325

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        MIVS_325_001_01 mivs325 = new MIVS_325_001_01(new MyLog(), 20190612, 122321, 13);
        MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf vrfctnInf = mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf();
        vrfctnInf.setRslt("MCHD");
        vrfctnInf.setDataResrcDt("2019-04-29");

        //赋循环数据
        //BasInfo
        List<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.BasInfo> basInfoList = new ArrayList<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.BasInfo>();
        for (int i=0 ; i<1; i++) {
            MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.BasInfo basInfoListarraymsg = new MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.BasInfo();
            basInfoListarraymsg.setEntNm("照面信息");
            basInfoListarraymsg.setUniSocCdtCd("1231245434231235");
            basInfoListarraymsg.setCoTp("一二三四五");
			basInfoListarraymsg.setDom("北京市海淀区");
			basInfoListarraymsg.setRegCptl("10000000.23");
			basInfoListarraymsg.setDtEst("2015-01-01");
			basInfoListarraymsg.setOpPrdFrom("2015-01-01");
			basInfoListarraymsg.setOpPrdTo("2025-01-01");
			basInfoListarraymsg.setRegSts("XXX");
			basInfoListarraymsg.setNmOfLglPrsn("老苗");
			basInfoListarraymsg.setRegAuth("登记机关");
			basInfoListarraymsg.setBizScp("经营范围");
			basInfoListarraymsg.setDtAppr("2019-06-01");
            basInfoList.add(basInfoListarraymsg);
        }
		myLog.info(logger,"basInfoList循环列表内容为：" + basInfoList.toString());
        if(basInfoList !=null && !basInfoList.isEmpty()){
            mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getRegInf().setBasInfo(basInfoList);
        }

        //CoShrhdrFndInfo
        List<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.CoShrhdrFndInfo> coShrhdrFndInfoList = new ArrayList<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.CoShrhdrFndInfo>();
        for (int i=0 ; i<2; i++) {
            MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.CoShrhdrFndInfo coShrhdrFndInfoListarraymsg = new MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.CoShrhdrFndInfo();
            coShrhdrFndInfoListarraymsg.setInvtrNm("企业股东及出资信息"+i);
            coShrhdrFndInfoListarraymsg.setInvtrId("2251231436256000"+i);
            coShrhdrFndInfoListarraymsg.setSubscrCptlConAmt("88800.23");
            coShrhdrFndInfoListarraymsg.setActlCptlConAmt("88800.23");
            coShrhdrFndInfoListarraymsg.setSubscrCptlConFm("五四三二一");
            coShrhdrFndInfoListarraymsg.setSubscrCptlConDt("2019-01-01");
            coShrhdrFndInfoList.add(coShrhdrFndInfoListarraymsg);
        }
        myLog.info(logger,"coShrhdrFndInfoList循环列表内容为：" + coShrhdrFndInfoList.toString());
        if(coShrhdrFndInfoList !=null && !coShrhdrFndInfoList.isEmpty()){
            mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getRegInf().setCoShrhdrFndInfo(coShrhdrFndInfoList);
        }

        //DirSupSrMgrInfo
        List<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.DirSupSrMgrInfo> dirSupSrMgrInfoList = new ArrayList<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.DirSupSrMgrInfo>();
        for (int i=0 ; i<2; i++) {
            MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.DirSupSrMgrInfo dirSupSrMgrInfoListarraymsg = new MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.DirSupSrMgrInfo();
            dirSupSrMgrInfoListarraymsg.setNm("董事监事及高管信息"+i);
            dirSupSrMgrInfoListarraymsg.setPosn("董事"+i);
            dirSupSrMgrInfoList.add(dirSupSrMgrInfoListarraymsg);
        }
        myLog.info(logger,"dirSupSrMgrInfoList循环列表内容为：" + dirSupSrMgrInfoList.toString());
        if(dirSupSrMgrInfoList !=null && !dirSupSrMgrInfoList.isEmpty()){
            mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getRegInf().setDirSupSrMgrInfo(dirSupSrMgrInfoList);
        }

        //ChngInfo
        List<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.ChngInfo> chngInfoList = new ArrayList<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.ChngInfo>();
        for (int i=0 ; i<2; i++) {
            MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.ChngInfo chngInfoListarraymsg = new MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.ChngInfo();
            chngInfoListarraymsg.setChngItm("变更事项"+i);
            chngInfoListarraymsg.setBfChng("变更前内容"+i);
            chngInfoListarraymsg.setAftChng("变更后内容"+i);
            chngInfoListarraymsg.setDtOfChng("2019-04-1"+i);
            chngInfoList.add(chngInfoListarraymsg);
        }
        myLog.info(logger,"chngInfoList循环列表内容为：" + chngInfoList.toString());
        if(chngInfoList !=null && !chngInfoList.isEmpty()){
            mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getRegInf().setChngInfo(chngInfoList);
        }

        //AbnmlBizInfo
        List<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.AbnmlBizInfo> abnmlBizInfoList = new ArrayList<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.AbnmlBizInfo>();
        for (int i=0 ; i<2; i++) {
            MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.AbnmlBizInfo abnmlBizInfoListarraymsg = new MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.AbnmlBizInfo();
            abnmlBizInfoListarraymsg.setAbnmlCause("列入经营异常名录原因类型"+i);
            abnmlBizInfoListarraymsg.setAbnmlDate("2019-05-1"+i);
            abnmlBizInfoListarraymsg.setAbnmlCauseDcsnAuth("列入决定机关"+i);
            abnmlBizInfoListarraymsg.setRmvCause("移出经营异常名录原因"+i);
            abnmlBizInfoListarraymsg.setRmvDate("2019-05-1"+i);
            abnmlBizInfoListarraymsg.setRmvCauseDcsnAuth("移出决定机关"+i);
            abnmlBizInfoList.add(abnmlBizInfoListarraymsg);
        }
        myLog.info(logger,"abnmlBizInfoList循环列表内容为：" + abnmlBizInfoList.toString());
        if(chngInfoList !=null && !chngInfoList.isEmpty()){
            mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getRegInf().setAbnmlBizInfo(abnmlBizInfoList);
        }

        //IllDscrtInfo
        List<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.IllDscrtInfo> illDscrtInfoList = new ArrayList<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.IllDscrtInfo>();
        for (int i=0 ; i<2; i++) {
            MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.IllDscrtInfo illDscrtInfoListarraymsg = new MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.IllDscrtInfo();
            illDscrtInfoListarraymsg.setIllDscrtCause("列入事由或情形"+i);
            illDscrtInfoListarraymsg.setAbnmlDate("2019-05-1"+i);
            illDscrtInfoListarraymsg.setAbnmlCauseDcsnAuth("列入决定机关"+i);
            illDscrtInfoListarraymsg.setRmvCause("移出事由"+i);
            illDscrtInfoListarraymsg.setRmvCauseDcsnAuth("移出决定机关"+i);
            illDscrtInfoListarraymsg.setRmvDate("2019-05-1"+i);
            illDscrtInfoList.add(illDscrtInfoListarraymsg);
        }
        myLog.info(logger,"illDscrtInfoList循环列表内容为：" + illDscrtInfoList.toString());
        if(chngInfoList !=null && !chngInfoList.isEmpty()){
            mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getRegInf().setIllDscrtInfo(illDscrtInfoList);
        }

        //LicNull
        List<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.LicNull> licNullList = new ArrayList<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.LicNull>();
        for (int i=0 ; i<1; i++) {
            MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.LicNull licNullListarraymsg = new MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.RegInf.LicNull();
            licNullListarraymsg.setOrgnlOrCp("ORGN");//正副本标识 ORCY：正副本  ORGN：正本  COPY：副本
            licNullListarraymsg.setLicNullStmCntt("声明内容"+i);
            licNullListarraymsg.setLicNullStmDt("2019-05-1"+i);
            licNullListarraymsg.setRplSts("RPLC");//补领标识 RPLC：补领  NULL：未补领
            licNullListarraymsg.setRplDt("2019-06-1"+i);
            licNullListarraymsg.setLicCpNb("1234009700"+i);//营业执照副本编号
            licNullList.add(licNullListarraymsg);
        }
        myLog.info(logger,"licNullList循环列表内容为：" + licNullList.toString());
        if(chngInfoList !=null && !chngInfoList.isEmpty()){
            mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getRegInf().setLicNull(licNullList);
        }

        //模拟返回325报文false
        int traceno = dto.getSysTraceno();
        MIVS_325_001_01 mivs325false = new MIVS_325_001_01(new MyLog(), dto.getSysDate(), dto.getSysTime(), traceno);
        mivs325false.getRtrRegVrfctn().setRspsn(mivs325.getRtrRegVrfctn().getRspsn());
        mivs325false.getHeader().setOrigSender("313131000008");
        mivs325false.getHeader().setOrigReceiver("0000");
        mivs325false.getHeader().setMesgID(mivs325false.getHeader().getMesgID());
        mivs325false.getRtrRegVrfctn().getMsgHdr().getInstgPty().setInstgDrctPty("0000");
        mivs325false.getRtrRegVrfctn().getMsgHdr().getInstgPty().setInstgPty("000012345678");
        mivs325false.getRtrRegVrfctn().getMsgHdr().setMsgId(mivs325false.getRtrRegVrfctn().getMsgHdr().getMsgId());
        mivs325false.getRtrRegVrfctn().getOrgnlBizQry().setMsgId(mivs324.getHead().getMesgID());
        mivs325false.getRtrRegVrfctn().getOrgnlBizQry().getInstgPty().setInstgDrctPty(mivs324.getTxPmtVrfctn().getMsgHdr().getInstgPty().getInstgDrctPty());
        mivs325false.getRtrRegVrfctn().getOrgnlBizQry().getInstgPty().setInstgPty(mivs324.getTxPmtVrfctn().getMsgHdr().getInstgPty().getInstgPty());
        mivs325false.getRtrRegVrfctn().getMsgPgntn().setPgNb(1);
        mivs325false.getRtrRegVrfctn().getMsgPgntn().setLastPgInd("false");
        pmtsService.sendToPmtsNoWait(mivs325false);

        //模拟返回325报文true
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        traceno = traceno - 2010 ;
        MIVS_325_001_01 mivs325true = new MIVS_325_001_01(new MyLog(), dto.getSysDate(), dto.getSysTime(), traceno);
        mivs325true.getRtrRegVrfctn().setRspsn(mivs325.getRtrRegVrfctn().getRspsn());
        mivs325true.getHeader().setOrigSender(mivs324.getHead().getOrigSender());
        mivs325true.getHeader().setOrigSendDate(mivs324.getHead().getOrigSendDate());
        mivs325true.getHeader().setOrigReceiver("0000");
        mivs325true.getHeader().setMesgID(mivs324.getHead().getMesgID());
        mivs325true.getRtrRegVrfctn().getMsgHdr().getInstgPty().setInstgDrctPty("0000");
        mivs325true.getRtrRegVrfctn().getMsgHdr().getInstgPty().setInstgPty("000012345678");
        mivs325true.getRtrRegVrfctn().getMsgHdr().setMsgId(mivs325true.getRtrRegVrfctn().getMsgHdr().getMsgId());
        mivs325true.getRtrRegVrfctn().getOrgnlBizQry().setMsgId(mivs324.getHead().getMesgID());
        mivs325true.getRtrRegVrfctn().getOrgnlBizQry().getInstgPty().setInstgDrctPty(mivs324.getTxPmtVrfctn().getMsgHdr().getInstgPty().getInstgDrctPty());
        mivs325true.getRtrRegVrfctn().getOrgnlBizQry().getInstgPty().setInstgPty(mivs324.getTxPmtVrfctn().getMsgHdr().getInstgPty().getInstgPty());
        mivs325true.getRtrRegVrfctn().getMsgPgntn().setPgNb(2);
        mivs325true.getRtrRegVrfctn().getMsgPgntn().setLastPgInd("true");
        pmtsService.sendToPmtsNoWait(mivs325true);


         //3、根据324内容模拟返回911
//         CCMS_911_001_02 ccms911 = new CCMS_911_001_02(new MyLog(), 20190909, 122321, 13);
//         ccms911.getHeader().setOrigSender("313131000008"); // TODO 通过机构号查询渠道接口获取（机构号查行号）
//         ccms911.getHeader().setOrigReceiver("0000");
//         ccms911.getHeader().setMesgID(mivs324.getHead().getMesgID());
//         ccms911.getDscrdMsgNtfctn().getDscrdInf().setMT("mivs.321.001.01");
//         ccms911.getDscrdMsgNtfctn().getDscrdInf().setMsgId(mivs324.getTxPmtVrfctn().getMsgHdr().getMsgId());
//         ccms911.getDscrdMsgNtfctn().getDscrdInf().setPrcCd("O1106");
//         ccms911.getDscrdMsgNtfctn().getDscrdInf().setRjctInf("原报文类型非法");
//         ccms911.getDscrdMsgNtfctn().getDscrdInf().setMsgRefId("msgRefId");
//         ccms911.getDscrdMsgNtfctn().getDscrdInf().setOrigSndDt("20190909");
//         ccms911.getDscrdMsgNtfctn().getDscrdInf().setOrigSndr("origSndr");
//         pmtsService.sendToPmtsNoWait(ccms911);

        return mivs324;
    }
}
