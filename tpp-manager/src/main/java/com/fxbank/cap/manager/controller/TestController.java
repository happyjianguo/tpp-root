package com.fxbank.cap.manager.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fxbank.cap.manager.utils.ByteUtils;
import com.fxbank.cap.manager.utils.MyRefbdcUtil;
import com.fxbank.cap.paf.constant.PAF;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.log.MyLog;

import redis.clients.jedis.Jedis;

/** 
* @ClassName: TestController 
* @Description: 生产测试
* @author DuZhenduo
* @date 2018年11月15日 下午1:47:51 
*  
*/
@Controller
public class TestController {
	private static Logger logger = LoggerFactory.getLogger(TestController.class);
	@Resource
	private MyJedis myJedis;
	
	@Resource
    private MyRefbdcUtil myRefbdcUtil;
	
	private final static String COMMON_PREFIX = "paf_common.";
	private Socket socket;
	private OutputStream os;
	private InputStream is;
	
	@RequestMapping("/paf/test")
	@ResponseBody
	public String test(){
		MyLog myLog = new MyLog();
		String result = "";
		String ip = null,port = null,reqPack = null,txCode = null;
		try (Jedis jedis = myJedis.connect()) {
			ip = jedis.get(COMMON_PREFIX + "test_ip");
			port = jedis.get(COMMON_PREFIX + "test_port");
			reqPack = jedis.get(COMMON_PREFIX + "test_reqPack");
			txCode = jedis.get(COMMON_PREFIX + "test_txCode");
		}
		try {
			socket = new Socket(ip,Integer.parseInt(port));
			os = socket.getOutputStream();
		    is = socket.getInputStream();
			result = packAndSndRcvRequest("0",txCode,reqPack);
		} catch (Exception e) {
			myLog.error(logger, "测试通讯失败", e);
		}finally {
			try{if(is!=null)is.close();}catch(Exception e){}
	    	try{if(os!=null)os.close();}catch(Exception e){}
	    	try{if(socket!=null)socket.close();}catch(Exception e){}
		}
	    return result;
	}
	private String packAndSndRcvRequest(String isLogin,String txCode,String sndPack) throws Exception{
        logger.info("发送请求报文"+sndPack);
        StringBuffer ssb = new StringBuffer("123456");	//6位
        ssb.append(isLogin);	//1位
        ssb.append(txCode+"\0");	//7位
        ssb.append("\0\0");			//2位

        byte[] decryptMsg = myRefbdcUtil.encrypt(sndPack.getBytes(PAF.ENCODING));
        int len = decryptMsg.length;
        logger.info("报文长度["+len+"]");
        byte[] byteLens = ByteUtils.intToBytes2(len);
        logger.info("发送十六进制："+ByteUtils.byteArrToHexStr(byteLens));
        byte[] sndPackByte = new byte[16+4+len];
        System.arraycopy(ssb.toString().getBytes(PAF.ENCODING), 0, sndPackByte, 0, 16);
        System.arraycopy(byteLens, 0, sndPackByte, 16, 4);
        System.arraycopy(decryptMsg, 0, sndPackByte, 20, len);
        String ss=ByteUtils.byteArrToHexStr(sndPackByte);
        logger.info("发送报文密文十六进制["+ss+"]");
        this.os.write(sndPackByte);
        this.os.flush();

        InputStream is = socket.getInputStream();
        byte[] retHeader = new byte[16];
        for(int i=0;i<16;++i){
            retHeader[i]=(byte) is.read();
        }
        logger.info("接收到报文头["+new String(retHeader,PAF.ENCODING)+"]");

        byte[] retDataLen = new byte[4];
        for(int i=0;i<4;++i){
            retDataLen[i]=(byte) is.read();
        }
        int retLen = ByteUtils.bytesToInt2(retDataLen, 0);
        logger.info("接收到的报文长度["+retLen+"]");

        byte[] retData = new byte[retLen];
        for(int i=0;i<retLen;++i){
            retData[i]=(byte) is.read();
        }
        byte[] rcvByte = myRefbdcUtil.decrypt(retData);
        String rcvPack = new String(rcvByte,PAF.ENCODING);

        logger.info("接收到的报文"+rcvPack);
        return rcvPack;
    }
}
