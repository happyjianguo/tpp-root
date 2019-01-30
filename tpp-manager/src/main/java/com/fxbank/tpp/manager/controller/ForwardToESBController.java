package com.fxbank.tpp.manager.controller;

import java.nio.charset.Charset;

import javax.annotation.Resource;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.common.utils.LogUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.exception.SysTradeExecuteException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@Controller
public class ForwardToESBController{

	private static Logger logger = LoggerFactory.getLogger(ForwardToESBController.class);
	
	private static final String serviceKey = "ecc_common.testSelfKey";
	
	private static final String servicePack = "ecc_common.testSelfPack";
	
	@Resource
	private JedisSentinelPool jedisPool;
	
	
	@RequestMapping("/test/sendtomyself")
	public void sendToMyself() throws SysTradeExecuteException {
		String url ;
		String jsonReq;
		try(Jedis jedis=jedisPool.getResource()){
			url = jedis.get(serviceKey);
			jsonReq = jedis.get(servicePack);
		}
//		String jsonReq = "{  \"APP_HEAD\": {    \"CURRENT_NUM\": \"0\",    \"PAGE_END\": \"0\",    \"PAGE_START\": \"0\",    \"PGUP_OR_PGDN\": \"0\",    \"TOTAL_NUM\": \"-1\"  },  \"BODY\": {    \"PROTOCOL_NO\": \"20181114240000028591\",    \"AGREEMENT_TYPE\":\"1\",    \"PRO_BELONG_BRANCH\":\"Z2004944000010\",    \"TRAN_AMT\":\"0.00\"},  \"LOCAL_HEAD\": {},  \"SYS_HEAD\": {    \"APPR_FLAG\": \"1\",    \"APPR_USER_ID\": \"1100001002\",    \"AUTH_FLAG\": \"N\",    \"AUTH_USER_ID\": \"1100001002\",    \"BRANCH_ID\": \"8888\",    \"CNSM_SYS_SVRID\": \"abc\",    \"COMPANY\": \"abc\",    \"DEST_BRANCH_NO\": \"02001\",    \"EXTEND_ARRAY\": [      \"\"    ],    \"EXTEND_FIELD\": \"abc\",    \"FILE_PATH\": \"abc\",    \"GLOBAL_SEQ_NO\": \"4012002018032909433201\",    \"MAC_ORG_ID\": \"abc\",    \"MAC_VALUE\": \"UCA|ucaToesb|RZAK|BD8CA9773B808028F403964C1264521E\",    \"PROGRAM_ID\": \"3112\",    \"SCENE_ID\": \"01\",    \"SEQ_NO\": \"000220130707\",    \"SERVICE_ID\": \"300130027\",    \"SOURCE_BRANCH_NO\": \"000001\",    \"SOURCE_TYPE\": \"MT\",    \"SRC_SYS_SVRID\": \"abc\",    \"SYSTEM_ID\": \"301907\",    \"THREAD_NO\": \"abc\",    \"TRAN_DATE\": \"20180329\",    \"TRAN_MODE\": \"ONLINE\",    \"TRAN_TIMESTAMP\": \"0943320120\",    \"USER_ID\": \"0001\",    \"USER_LANG\": \"CHINESE\",    \"VERSION\": \"abc\",    \"globalSeqNo\": \"4012002018032909433201\"  }}";
		
		
		logger.debug("发送请求至ESB：" + jsonReq);
		
		
	    HttpPost post = null;
	    try {
	        HttpClient httpClient = new DefaultHttpClient();

	        post = new HttpPost(url);
	        // 构造消息头
	        post.setHeader("Content-type", "application/json; charset=utf-8");
	        post.setHeader("Connection", "Close");
	                    
	        // 构建消息实体
	        StringEntity entity = new StringEntity(jsonReq.toString(), Charset.forName("UTF-8"));
	        entity.setContentEncoding("UTF-8");
	        // 发送Json格式的数据请求
	        entity.setContentType("application/json");
	        post.setEntity(entity);
	            
	        HttpResponse response = httpClient.execute(post);
	            
	        // 检验返回码
	        int statusCode = response.getStatusLine().getStatusCode();
	        if(statusCode != HttpStatus.SC_OK){
	        	logger.info("请求出错: "+statusCode);
	        }else{
	        	logger.info("请求成功: "+statusCode);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }finally{
	        if(post != null){
	            try {
	                post.releaseConnection();
	                Thread.sleep(500);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	
	}

}
