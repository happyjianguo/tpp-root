package com.fxbank.cap.paf.trade.paf;

import java.io.IOException;
import java.net.UnknownHostException;
import javax.annotation.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TranResultTradeTest {
	@Resource
	private TestUtils testUtils;
    @Before
    public void init() throws UnknownHostException, IOException {
    	testUtils.init();
    }

    @Test
    public void main() throws Exception {
    	StringBuffer params = new StringBuffer();
    	//机构编号；阜新： 210900000000000；盘锦：211100000000000
    	params.append("211100000000000");
    	params.append(",");
    	//原交易日期
    	params.append("20151110");
    	params.append(",");
    	//原交易流水号
    	params.append("cef206ebd71d42eb88671d1a6d0ff7a2");
        testUtils.testMain("REQ_BDC219", params.toString(),"BDC219");
    }

    @After
    public void destroy() throws IOException {
    	testUtils.destroy();
    }
}
