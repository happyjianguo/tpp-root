package com.fxbank.tpp.mivs;

import javax.annotation.Resource;

import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.model.MIVS_320_001_01;
import com.fxbank.tpp.mivs.mq.MqQaClient;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ServletComponentScan
@SpringBootApplication
@EnableScheduling
@ComponentScan("com.fxbank")
public class MivsServer implements CommandLineRunner {

    public static final String REDIS_PREFIX = "mivs.";

    @Resource
    private IForwardToPmtsService pmtsService;

    @Resource
    private MyJedis myJedis;

    @Resource
    private MqQaClient mqQaClient;

    public static void main(String[] args) {
        SpringApplication.run(MivsServer.class, args);
    }

    @Override
    public void run(String... strings) {
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

        try {
            pmtsService.sendToPmts(mivs);
        } catch (SysTradeExecuteException e) {
            e.printStackTrace();
        }
    }

}
