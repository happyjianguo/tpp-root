package com.fxbank.cap.paf.trade.paf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fxbank.cap.paf.common.MyRefbdcUtil;
import com.fxbank.cap.paf.constant.PAF;
import com.fxbank.cap.paf.dto.GenerateReqCapXml;
import com.fxbank.cap.paf.dto.SER_REQ_BDC;
import com.fxbank.cap.paf.model.BODY;
import com.fxbank.cap.paf.model.FIELD;
import com.fxbank.cap.paf.model.FIELDS;
import com.fxbank.cap.paf.nettty.server.ServerConfig;
import com.fxbank.cap.paf.utils.ByteUtils;
import com.fxbank.cap.paf.utils.JAXBUtils;

/** 
* @ClassName: TestUtils 
* @Description: 测试公共方法类 
* @author DuZhenduo
* @date 2018年10月19日 下午4:51:29 
*  
*/
@Component
public class TestUtils {
	private static Logger logger = LoggerFactory.getLogger(TestUtils.class);

   //	private static String HOST="127.0.0.1";								//目标地址
   // private static Integer PORT=6000;
   // private static String HOST="57.25.3.166";								//目标地址
   // private static Integer PORT=6000;
	private static String HOST="57.25.3.118";								//目标地址
    private static Integer PORT=10032;
   // private static String HOST="57.25.8.121";								//目标地址
   // private static Integer PORT=10032;

	private Socket socket;
    private OutputStream os;
    private BufferedReader br;

    @Resource
    private ServerConfig config;

    public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public OutputStream getOs() {
		return os;
	}

	public void setOs(OutputStream os) {
		this.os = os;
	}

	public BufferedReader getBr() {
		return br;
	}

	public void setBr(BufferedReader br) {
		this.br = br;
	}

	public MyRefbdcUtil getMyRefbdcUtil() {
		return myRefbdcUtil;
	}

	public void setMyRefbdcUtil(MyRefbdcUtil myRefbdcUtil) {
		this.myRefbdcUtil = myRefbdcUtil;
	}

	@Resource
    private MyRefbdcUtil myRefbdcUtil;

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
    public void init() throws UnknownHostException, IOException{
    	this.setSocket( new Socket(HOST, PORT==0?config.getPort():PORT));
    	this.setOs( this.getSocket().getOutputStream());
    }
    public void destroy() throws IOException{
    	 if(this.getBr()!=null)
             this.getBr().close();
         if(this.getOs()!=null)
             this.getOs().close();
         if(this.getSocket()!=null)
             this.getSocket().close();
    }
    public void testMain(String dto,String params,String txCode) throws Exception{
    	  String sndPack = GenerateReqCapXml.generateXml(dto,params);
          String rcvPack = packAndSndRcvRequest("0",txCode,sndPack);
          SER_REQ_BDC repDto = (SER_REQ_BDC) JAXBUtils.unmarshal(rcvPack.getBytes(), SER_REQ_BDC.class);
          FIELDS head = repDto.getHead();
          BODY body = repDto.getBody();
          for(FIELD field : head.getField()){
              if(field.getName().equals("TxStatus")){
                  assertEquals(field.getValue(),"0");
              }
              if(field.getName().equals("RtnCode")){
                  assertEquals(field.getValue(),"00000");
              }
          }
          for(FIELD field : body.getField()){
              if(field.getName().equals("AcctNo")){
                  assertNull(field.getValue());
              }
          }
    }

}
