package com.fxbank.tpp.mivs.trade.esb;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.esb.REP_30041000901;
import com.fxbank.tpp.mivs.dto.esb.REQ_30041000901;
import com.fxbank.tpp.mivs.dto.mivs.DTO_BASE;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_321_001_01;
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

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        REQ_30041000901 req = (REQ_30041000901) dto;

        MIVS_320_001_01 mivs320 = new MIVS_320_001_01(new MyLog(), 20190909, 12323, 12);
        //TODO 通过req组合mivs320
        mivs320.getHeader().setOrigSender("313131000008"); // TODO 通过机构号查询渠道接口获取（机构号查行号）
        mivs320.getHeader().setOrigReceiver("0000");
        mivs320.getGetIdVrfctn().getMsgHdr().getInstgPty().setInstgDrctPty("3131310000008");
        mivs320.getGetIdVrfctn().getMsgHdr().getInstgPty().setDrctPtyNm("阜新银行清算中心");
        mivs320.getGetIdVrfctn().getMsgHdr().getInstgPty().setInstgPty("313131000009");
        mivs320.getGetIdVrfctn().getMsgHdr().getInstgPty().setPtyNm("阜新银行银河支行");

        mivs320.getGetIdVrfctn().getVryDef().setMobNb("18309872813");
        mivs320.getGetIdVrfctn().getVryDef().setNm("周勇沩");
        mivs320.getGetIdVrfctn().getVryDef().setIdTp("1");
        mivs320.getGetIdVrfctn().getVryDef().setId("210381198302252714");
        mivs320.getGetIdVrfctn().getVryDef().setUniSocCdtCd("uniSocCdtCd");
        mivs320.getGetIdVrfctn().getVryDef().setOpNm("张");

        mivs320 = (MIVS_320_001_01) pmtsService.sendToPmts(mivs320); // 发送请求，实时等待990

        String msgid= mivs320.getGetIdVrfctn().getMsgHdr().getMsgId();    //为同步等待321，组合三要素
        String channel = "321_"+msgid;
        DTO_BASE dtoBase = syncCom.get(myLog, channel, 60, TimeUnit.SECONDS);

        REP_30041000901 rep = new REP_30041000901();
        if(dtoBase.getHead().getMesgType().equals("911")){  //根据911组织应答报文

        }else if(dtoBase.getHead().getMesgType().equals("321")){
            MIVS_321_001_01 mivs321 = new MIVS_321_001_01();
        }

        //TODO 通过mivs321 组成给请求端应答

        return rep;
    }
}