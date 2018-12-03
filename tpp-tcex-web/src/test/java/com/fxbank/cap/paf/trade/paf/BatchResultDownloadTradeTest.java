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
public class BatchResultDownloadTradeTest {
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
    	params.append("210900000000000");
    	params.append(",");
    	//类型； 1：成功清单下载；2：失败清单下载；3：结果数据下载
    	params.append("3");
    	params.append(",");
    	//批量编号；批量付款：CRDT开头；贷款扣款：LOAN开头
    	params.append("LOAN2018110958816");
        testUtils.testMain("REQ_BDC208", params.toString(),"BDC208");
    }

    @After
    public void destroy() throws IOException {
    	testUtils.destroy();
    }
}
