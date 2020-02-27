package com.fxbank.tpp.beps.pmts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @author : 周勇沩
 * @description: 业务应答信息组件
 * @Date : 2020/2/27 15:29
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"MsgId", "CreDtTm", "InstgPty", "InstdPty", "SysCd", "Rmk"})
public class GrpHdr implements Serializable {

    private static final long serialVersionUID = 5625503162408830155L;

    private String MsgId;
    private String CreDtTm;
    private InstgPty InstgPty = new InstgPty();
    private InstdPty InstdPty = new InstdPty();
    private String SysCd = "BEPS";
    private String Rmk;

    public void fill(String sndsabkno, String sndbankno, String rcvsabkno, String rcvbankno) {
        InstgPty.setInstgDrctPty(sndsabkno);
        InstgPty.setInstgPty(sndbankno);
        InstdPty.setInstdDrctPty(rcvsabkno);
        InstdPty.setInstdPty(rcvbankno);
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        this.MsgId = msgId;
    }

    public String getCreDtTm() {
        return CreDtTm;
    }

    public void setCreDtTm(String creDtTm) {
        this.CreDtTm = creDtTm;
    }

    public String getSysCd() {
        return SysCd;
    }

    public void setSysCd(String sysCd) {
        this.SysCd = sysCd;
    }

    public String getRmk() {
        return Rmk;
    }

    public void setRmk(String rmk) {
        this.Rmk = rmk;
    }

    public InstgPty getInstgPty() {
        return InstgPty;
    }

    public void setInstgPty(InstgPty instgPty) {
        this.InstgPty = instgPty;
    }

    public InstdPty getInstdPty() {
        return InstdPty;
    }

    public void setInstdPty(InstdPty instdPty) {
        this.InstdPty = instdPty;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "GrpHdr.InstgPty")
    public static class InstgPty implements Serializable {

        private static final long serialVersionUID = -6877011574990490256L;

        private String InstgDrctPty;
        private String InstgPty;

        public String getInstgDrctPty() {
            return InstgDrctPty;
        }

        public void setInstgDrctPty(String instgDrctPty) {
            this.InstgDrctPty = instgDrctPty;
        }

        public String getInstgPty() {
            return InstgPty;
        }

        public void setInstgPty(String instgPty) {
            this.InstgPty = instgPty;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "GrpHdr.InstdPty")
    public static class InstdPty implements Serializable {

        private static final long serialVersionUID = 8084891028404642070L;

        private String InstdDrctPty;
        private String InstdPty;

        public String getInstdDrctPty() {
            return InstdDrctPty;
        }

        public void setInstdDrctPty(String instdDrctPty) {
            this.InstdDrctPty = instdDrctPty;
        }

        public String getInstdPty() {
            return InstdPty;
        }

        public void setInstdPty(String instdPty) {
            this.InstdPty = instdPty;
        }
    }
}
