package com.fxbank.tpp.bocm.simu;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.tpp.bocm.model.REP_10101;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimuBocmServer {

    public static final String CODING = "UTF-8";
    public static final Integer PORT = 6003;

    public static void main(String[] args) throws Exception {
        new Work().listen(SimuBocmServer.PORT);
    }

}

class Work {

    private static Logger logger = LoggerFactory.getLogger(Work.class);

    public String listen(Integer port) throws Exception {
        ServerSocket ss = new ServerSocket(port);
        while (true) {
            this.logger.info("监听端口" + port + "，等待连接...");
            Executors.newSingleThreadExecutor().execute(new Run(ss.accept()));
        }
    }
}

class Run implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(Run.class);

    private Socket socket;

    public Run(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = this.socket.getInputStream();
            byte[] lenByte = new byte[8];
            is.read(lenByte);
            String slen = new String(lenByte);
            int len = Integer.valueOf(slen);
            byte[] dataByte = new byte[len];
            is.read(dataByte);
            String data = new String(dataByte, SimuBocmServer.CODING);
            this.logger.info("接收请求报文[" + data + "]");
            String seq = data.substring(46, 46+14);
            this.logger.info("SEQ="+seq);

            REP_10101 rep = new REP_10101(new MyLog(), 20190909, 125609, 1);
            rep.setTmsgTyp("N");
            rep.setTrspCd("JH0000");
            rep.setTrspMsg("成功");
            rep.setRlogNo(seq);
            rep.setCcyCod("CNY");
            rep.setActNo("62316600000123");
            rep.setActNam("zzh");
            rep.setActBal(12.23d);
            String repData = FixedUtil.toFixed(rep,"UTF-8");
            repData = repData + "FFFFFFFFFFFFFFFF";
            os = socket.getOutputStream();
            String repLen = String.format("%08d", repData.getBytes(SimuBocmServer.CODING).length);
            this.logger.info("发送应答报文[" + repData + "]");
            os.write(repLen.getBytes(SimuBocmServer.CODING));
            os.write(repData.getBytes(SimuBocmServer.CODING));
        } catch (Exception e) {
            this.logger.error("处理连接异常", e);
            // throw new RuntimeException(e);
        } finally {
            try {
                if (os != null)
                    os.close();
                if (is != null)
                    is.close();
                if (this.socket != null)
                    this.socket.close();
            } catch (Exception e) {
                this.logger.error("关闭连接异常", e);
            } finally {
                this.logger.info("关闭连接");
            }
        }
    }
}