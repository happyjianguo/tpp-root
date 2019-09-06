package com.fxbank.tpp.mivs.model.response;

import com.fxbank.tpp.mivs.model.SIGN_DATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * @Description: 企业信息联网核查业务受理时间通知报文
 * @Author: 王鹏
 * @Date: 2019/7/5 14:35
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "MsgHdr", "SysStsInf"})
public class MIVS_801_001_01_SysStsNtfctn implements Serializable, SIGN_DATA {
    private static final long serialVersionUID = 5496119707971949568L;
    private MsgHdr MsgHdr = new MsgHdr();
    private SysStsInf SysStsInf = new SysStsInf();

    @Override
    public String toString() {
        return "企业信息联网核查业务受理时间通知报文[MIVS_801_001_01]";
    }

    public MsgHdr getMsgHdr() {
        return MsgHdr;
    }

    public void setMsgHdr(MsgHdr msgHdr) {
        MsgHdr = msgHdr;
    }

    public SysStsInf getSysStsInf() {
        return SysStsInf;
    }

    public void setSysStsInf(SysStsInf sysStsInf) {
        SysStsInf = sysStsInf;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "MsgId", "CreDtTm", "InstgPty", "InstdPty" })
    public static class MsgHdr implements Serializable {
        private static final long serialVersionUID = -528705758036061541L;
        private String MsgId = null;
        private String CreDtTm = null;
        private MsgHdr.InstgPty InstgPty = new MsgHdr.InstgPty();
        private MsgHdr.InstdPty InstdPty = new MsgHdr.InstdPty();

        public String getMsgId() {
            return MsgId;
        }

        public void setMsgId(String msgId) {
            MsgId = msgId;
        }

        public String getCreDtTm() {
            return CreDtTm;
        }

        public void setCreDtTm(String creDtTm) {
            CreDtTm = creDtTm;
        }

        public MsgHdr.InstgPty getInstgPty() {
            return InstgPty;
        }

        public void setInstgPty(MsgHdr.InstgPty instgPty) {
            InstgPty = instgPty;
        }

        public MsgHdr.InstdPty getInstdPty() {
            return InstdPty;
        }

        public void setInstdPty(MsgHdr.InstdPty instdPty) {
            InstdPty = instdPty;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name="MsgHdr.InstgPty")
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = 4596738630350265587L;
            private String InstgDrctPty = null;
            private String InstgPty = null;

            public String getInstgDrctPty() {
                return InstgDrctPty;
            }

            public void setInstgDrctPty(String instgDrctPty) {
                InstgDrctPty = instgDrctPty;
            }

            public String getInstgPty() {
                return InstgPty;
            }

            public void setInstgPty(String instgPty) {
                InstgPty = instgPty;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name="MsgHdr.InstdPty")
        public static class InstdPty implements Serializable{
            private static final long serialVersionUID = -4268165198877244970L;
            private String InstdDrctPty = null;
            private String InstdPty = null;

            public String getInstdDrctPty() {
                return InstdDrctPty;
            }

            public void setInstdDrctPty(String instdDrctPty) {
                InstdDrctPty = instdDrctPty;
            }

            public String getInstdPty() {
                return InstdPty;
            }

            public void setInstdPty(String instdPty) {
                InstdPty = instdPty;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "CurSysDt", "NxtSysDt", "SvcInf" })
    public static class SysStsInf implements Serializable{
        private static final long serialVersionUID = -5837765195992959243L;
        private String CurSysDt = null;
        private String NxtSysDt = null;
        private List<SvcInf> SvcInf;

        public String getCurSysDt() {
            return CurSysDt;
        }

        public void setCurSysDt(String curSysDt) {
            CurSysDt = curSysDt;
        }

        public String getNxtSysDt() {
            return NxtSysDt;
        }

        public void setNxtSysDt(String nxtSysDt) {
            NxtSysDt = nxtSysDt;
        }

        public List<MIVS_801_001_01_SysStsNtfctn.SysStsInf.SvcInf> getSvcInf() {
            return SvcInf;
        }

        public void setSvcInf(List<MIVS_801_001_01_SysStsNtfctn.SysStsInf.SvcInf> svcInf) {
            SvcInf = svcInf;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(propOrder = {"SysInd", "SvcInd", "NxtSysOpTm", "NxtSysClTm"})
        public static class SvcInf implements Serializable{
            private static final long serialVersionUID = -3383623409035110703L;
            private String SysInd = null;
            private String SvcInd = null;
            private String NxtSysOpTm = null;
            private String NxtSysClTm = null;

            public String getSysInd() {
                return SysInd;
            }

            public void setSysInd(String sysInd) {
                SysInd = sysInd;
            }

            public String getSvcInd() {
                return SvcInd;
            }

            public void setSvcInd(String svcInd) {
                SvcInd = svcInd;
            }

            public String getNxtSysOpTm() {
                return NxtSysOpTm;
            }

            public void setNxtSysOpTm(String nxtSysOpTm) {
                NxtSysOpTm = nxtSysOpTm;
            }

            public String getNxtSysClTm() {
                return NxtSysClTm;
            }

            public void setNxtSysClTm(String nxtSysClTm) {
                NxtSysClTm = nxtSysClTm;
            }
        }
    }

    @Override
    public String signData() {
        return null;
    }

}

