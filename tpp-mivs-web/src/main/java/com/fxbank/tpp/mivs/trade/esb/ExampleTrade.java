package com.fxbank.tpp.mivs.trade.esb;

import java.util.concurrent.Executors;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.model.MIVS_320_001_01;

import redis.clients.jedis.Jedis;

public class ExampleTrade {
   public void exec(){
    MIVS_320_001_01 mivs = new MIVS_320_001_01(new MyLog(), 20190909, 12323, 12);
    mivs.getHeader().setOrigSender("313131000008"); // TODO 通过机构号查询渠道接口获取（机构号查行号）
    mivs.getHeader().setOrigReceiver("0000");
    mivs.getGetIdVrfctn().getMsgHdr().getInstgPty().setInstgDrctPty("3131310000008");
    mivs.getGetIdVrfctn().getMsgHdr().getInstgPty().setDrctPtyNm("阜新银行清算中心");
    mivs.getGetIdVrfctn().getMsgHdr().getInstgPty().setInstgPty("313131000009");
    mivs.getGetIdVrfctn().getMsgHdr().getInstgPty().setPtyNm("阜新银行银河支行");

    mivs.getGetIdVrfctn().getVryDef().setMobNb("18309872813");
    mivs.getGetIdVrfctn().getVryDef().setNm("周勇沩");
    mivs.getGetIdVrfctn().getVryDef().setIdTp("1");
    mivs.getGetIdVrfctn().getVryDef().setId("210381198302252714");
    mivs.getGetIdVrfctn().getVryDef().setUniSocCdtCd("uniSocCdtCd");
    mivs.getGetIdVrfctn().getVryDef().setOpNm("张志宏");

    /*
    try {
        pmtsService.sendToPmts(mivs);
    } catch (SysTradeExecuteException e) {
        e.printStackTrace();
    }
    */
   } 
}