package com.fxbank.tpp.simu.trade.beps;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.pub.service.IPublicService;
import com.fxbank.tpp.beps.pmts.BEPS_351_001_01;
import com.fxbank.tpp.beps.pmts.BEPS_351_001_01_PtcSnReq;
import com.fxbank.tpp.beps.service.IForwardToPmtsService;
import com.fxbank.tpp.simu.route.trade.TradeExecutionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("REQ_TP35101A01")
public class P35101 extends TradeBase implements TradeExecutionStrategy {
    private static Logger logger = LoggerFactory.getLogger(P35101.class);

    @Reference(version = "1.0.0")
    private IPublicService publicService;

    @Reference(version = "1.0.0")
    private IForwardToPmtsService forwardToPmtsService;

    @Override
    public ESB_BASE execute(String txCode, ESB_BASE dto) throws SysTradeExecuteException {

        Integer sysDate = publicService.getSysDate("CIP");
        Integer sysTime = publicService.getSysTime();
        Integer sysTraceno = publicService.getSysTraceno();

        BEPS_351_001_01 beps351 = new BEPS_351_001_01(new MyLog(),sysDate,sysTime,sysTraceno);
        BEPS_351_001_01_PtcSnReq ptcSnReq = beps351.getPtcSnReq();

        BEPS_351_001_01_PtcSnReq.CtrctChngInf ctrctChngInf = new BEPS_351_001_01_PtcSnReq.CtrctChngInf();
        ctrctChngInf.setChngTp("CC00");
        ctrctChngInf.setCtrctTp("CO00");
        ctrctChngInf.setCstmrId("0123456789");
        ctrctChngInf.setRegId("110000");
        ctrctChngInf.setCstmrNm("张三");
        ctrctChngInf.setUniSocCdtCd("123abc");
        ctrctChngInf.setNbOfPmtItms("10");
        BEPS_351_001_01_PtcSnReq.CtrctChngInf.PmtItms pmtItms = new BEPS_351_001_01_PtcSnReq.CtrctChngInf.PmtItms();
        List<String> pmtItmCd = new ArrayList<String>();
        pmtItmCd.add("00100");
        pmtItms.setPmtItmCd(pmtItmCd);
        ctrctChngInf.setPmtItms(pmtItms);
        ctrctChngInf.setCtrctNb("123abc");
        ctrctChngInf.setReqId("123abc");
        ctrctChngInf.setIssr("104100004370");
        ctrctChngInf.setCstmrAcctType("AO00");
        ctrctChngInf.setAcctId("62170007");
        ctrctChngInf.setOncDdctnLmt("CNY650000.00");
        ctrctChngInf.setCycDdctnNumLmt("5");
        ctrctChngInf.setCtrctDueDt("2020-01-01");
        ctrctChngInf.setCtrctSgnDt("2020-04-03");
        ctrctChngInf.setEctDt("2020-05-01");
        BEPS_351_001_01_PtcSnReq.CtrctChngInf.PyrInf pyrInf = new BEPS_351_001_01_PtcSnReq.CtrctChngInf.PyrInf();
        pyrInf.setNm("张三");
        pyrInf.setIdTp("IC00");
        pyrInf.setId("110000201912151234");
        ctrctChngInf.setTelNb("13387625431");
        ctrctChngInf.setAdrLine("地址地址");
        ctrctChngInf.setRmk("备注备注");
        ctrctChngInf.setAuthMd("AM00");
        ctrctChngInf.setTmUt("TU00");
        ctrctChngInf.setTmSp("1");
        ctrctChngInf.setTmDc("扣款时间,描述信息");
        ctrctChngInf.setCycDdctnLmt("CNY650000.00");
        ctrctChngInf.setCtrctAddtlInf("协议附加数据");
        ptcSnReq.setCtrctChngInf(ctrctChngInf);

        forwardToPmtsService.sendToPmtsNoWait(beps351);

        return null;
    }

}
