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
public class BatchLoanTradeTest {
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
    	//文件类型 1：同行2：跨行3：混合
    	params.append("2");
    	params.append(",");
    	//批量交易总笔数
    	params.append("12");
        testUtils.testMain("REQ_BDC205", params.toString(),"BDC205");
    }

    @After
    public void destroy() throws IOException {
    	testUtils.destroy();
    }
}
