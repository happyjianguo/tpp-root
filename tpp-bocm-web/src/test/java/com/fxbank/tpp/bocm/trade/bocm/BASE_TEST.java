package com.fxbank.tpp.bocm.trade.bocm;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.model.REQ_BASE;
import com.fxbank.tpp.bocm.service.IBocmSafeService;

public class BASE_TEST {
    private static Logger logger = LoggerFactory.getLogger(BASE_TEST.class);

    private static final String IP = "127.0.0.1";
  private static final Integer PORT = 6006;
//    private static final String IP = "57.25.3.166";
//    private static final Integer PORT = 8501;

    public static final String CODING = "GB18030";
    
    @Reference(version = "1.0.0")
    private IBocmSafeService safeService;
    
    @Resource
	private LogPool logPool;

    public String comm(String reqData) throws Exception {
    	MyLog myLog = this.logPool.get();
        Socket socket = new Socket(BASE_TEST.IP, BASE_TEST.PORT);
//        socket.setSoTimeout(1000);
        InputStream is = null;
        OutputStream os = null;
        String repData = null;
        try {
        	
        	//添加MAC
            //reqData = reqData + "FFFFFFFFFFFFFFFF";
//            safeService.
           String mac = safeService.calcBocmMac(myLog, reqData);
           this.logger.info("MAC 【" + mac + "】");
           reqData = reqData + mac;
            
            os = socket.getOutputStream();
            String reqLen = String.format("%08d", reqData.getBytes(BASE_TEST.CODING).length);
            this.logger.info("发送请求报文[" + reqData + "]");
            os.write(reqLen.getBytes(BASE_TEST.CODING));
            os.write(reqData.getBytes(BASE_TEST.CODING));

            is = socket.getInputStream();
            byte[] lenByte = new byte[8];
            is.read(lenByte);
            String slen = new String(lenByte);
            int len = Integer.valueOf(slen);
            byte[] dataByte = new byte[len];
            is.read(dataByte);
            repData = new String(dataByte, BASE_TEST.CODING);
            this.logger.info("接收应答报文[" + repData + "]");
        } catch (Exception e) {
            this.logger.error("处理连接异常", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (os != null)
                    os.close();
                if (is != null)
                    is.close();
                if (socket != null)
                    socket.close();
            } catch (Exception e) {
                this.logger.error("关闭连接异常", e);
            } finally {
                this.logger.info("关闭连接");
            }
        }
        return repData;
    }

    public void initReqHeader(String tTxnCd,REQ_BASE base){
        String sDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        Integer seq = Math.abs(new Random().nextInt(100000000));
        base.setTtxnCd(tTxnCd);
        //301221000011  交通银行股份有限公司辽宁省分行账务中心
        //301100000015	交通银行北京市分行
        //"BANK_NUMBER":"313229000442","BNK_NM_T":"阜新银行液压园支行",
        //"SETTLEMENT_BANK_NO":"313229000008","LQTN_BNK_NM_T1":"阜新银行结算中心"  
        //313221099020 阜新银行沈阳分行营业部
        base.setSbnkNo("301100000015");
        base.setRbnkNo("313229000008");
        base.setTtxnDat(Integer.valueOf(sDate.substring(0, 8)));
        base.setTtxnTim(Integer.valueOf(sDate.substring(8))); 
        base.setSlogNo(String.format("%6s%08d", sDate.substring(2, 8),seq));
        
//        base.setTtxnDat(20191204);
//        base.setTtxnTim(141200);
//        base.setSlogNo("20191204001");
    }

}