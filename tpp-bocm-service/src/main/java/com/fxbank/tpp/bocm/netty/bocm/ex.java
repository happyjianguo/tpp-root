package com.fxbank.tpp.bocm.netty.bocm;

import javax.annotation.Resource;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.tpp.bocm.dto.bocm.Rep_1001;
import com.fxbank.tpp.bocm.dto.bocm.Req_1001;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ex {

    private static Logger logger = LoggerFactory.getLogger(ex.class);

    @Resource
    private BocmClient client;

    @Bean
    public String abc() {
        Req_1001 req = new Req_1001();
        req.setTxcode("txcode");
        req.setData("data");
        Req_1001 rep = null;
		try {
			rep = client.comm(req);
		} catch (SysTradeExecuteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        logger.info(rep.toString());
        return "";
    }
}