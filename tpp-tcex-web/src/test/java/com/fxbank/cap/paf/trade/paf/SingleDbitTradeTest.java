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
public class SingleDbitTradeTest {
	@Resource
	private TestUtils testUtils;
    @Before
    public void init() throws UnknownHostException, IOException {
    	testUtils.init();
    }

    @Test
    public void main() throws Exception {
    	/**
    	 * params[0] 结算模式1：同行2：跨行实时3：跨行非实时
    	 * params[1] 业务类型  05 贷款扣款
    	 */
    	StringBuffer params = new StringBuffer();
    	//机构编号；阜新： 210900000000000；盘锦：211100000000000
    	params.append("211100000000000");
    	params.append(",");
    	//收款账号
    	params.append("1200100003301583");
    	params.append(",");
    	//收款户名
    	params.append("盘锦市公积金");
    	params.append(",");
    	//付款账号
    	params.append("1200100003301617");
    	params.append(",");
    	//付款户名
    	params.append("无用");
    	params.append(",");
    	//交易金额
    	params.append("1000.00");
    	params.append(",");
    	//结算模式1：同行2：跨行实时3：跨行非实时
    	params.append("2");
    	params.append(",");
    	//业务类型  05 贷款扣款
    	params.append("05");
    	params.append(",");
        testUtils.testMain("REQ_BDC202", params.toString(),"BDC202");
    }

    @After
    public void destroy() throws IOException {
    	testUtils.destroy();
    }
}
