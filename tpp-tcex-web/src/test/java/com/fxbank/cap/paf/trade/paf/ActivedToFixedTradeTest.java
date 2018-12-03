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
public class ActivedToFixedTradeTest {
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
    	//活期账号
    	params.append("1200100003301583");
    	params.append(",");
    	//活期户名
    	params.append("盘锦市公积金");
    	params.append(",");
    	//定期账号
    	params.append("1205700000000010");
    	params.append(",");
    	//定期户名
    	params.append("盘锦市公积金");
    	params.append(",");
    	//存期；0：三个月；1：半年；2：一年；3：两年；4：三年；5：五年
    	params.append("4");
    	params.append(",");
    	//交易金额
    	params.append("10000.00");
        testUtils.testMain("REQ_BDC211", params.toString(),"BDC211");
    }

    @After
    public void destroy() throws IOException {
    	testUtils.destroy();
    }
}
