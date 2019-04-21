package com.fxbank.tpp.mivs.trade.mivs;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.fxbank.tpp.mivs.dto.mivs.REQ_HEADER;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BASE_TEST {
    private static Logger logger = LoggerFactory.getLogger(BASE_TEST.class);

    private static final String IP = "127.0.0.1";
    private static final Integer PORT = 6006;
    private static final String CODING = "UTF-8";

    public String comm(String reqData) throws Exception {
        Socket socket = new Socket(BASE_TEST.IP, BASE_TEST.PORT);
        InputStream is = null;
        OutputStream os = null;
        String repData = null;
        try {
            reqData = reqData + "FFFFFFFFFFFFFFFF";
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

    public void initReqHeader(String tTxnCd,REQ_HEADER header){
        String sDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        Integer seq = Math.abs(new Random().nextInt(100000000));
        header.settTxnCd(tTxnCd);
        header.setsBnkNo("313131000008");
		header.setrBnkNo("313131000007");
		header.settTxnDat(Integer.valueOf(sDate.substring(0, 8)));
        header.settTxnTim(Integer.valueOf(sDate.substring(8))); 
        header.setsLogNo(String.format("%6s%08d", sDate.substring(2, 8),seq));
    }

}