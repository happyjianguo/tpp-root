package com.fxbank.tpp.beps.trade.pmts;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.pub.service.IPublicService;
import com.fxbank.tpp.beps.pmts.BEPS_351_001_01;
import com.fxbank.tpp.beps.service.IForwardToPmtsService;
import com.fxbank.tpp.beps.trade.utils.BeanUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;


/**
 * @author : 周勇沩
 * @description: 351来账报文测试
 * @Date : 2020/2/24 21:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class P35101Test {

    private static Logger logger = LoggerFactory.getLogger(P35101.class);

    @Reference(version = "1.0.0")
    private IPublicService publicService;

    @Reference(version = "1.0.0")
    private IForwardToPmtsService forwardToPmtsService;

    @Before
    public void before() {
    }

    @Test
    public void payOk() throws Exception {
        BEPS_351_001_01 beps351 = new BEPS_351_001_01(new MyLog(), publicService.getSysDate("CIP"), publicService.getSysTime(), publicService.getSysTraceno());
        BeanUtil.fillBean(beps351);
        forwardToPmtsService.sendToPmtsNoWait(beps351);
    }

    @After
    public void after() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
    }
}
