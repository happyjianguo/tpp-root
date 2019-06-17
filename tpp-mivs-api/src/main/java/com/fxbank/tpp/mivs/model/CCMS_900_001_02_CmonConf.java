package com.fxbank.tpp.mivs.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @Description: 通用处理确认报文 CCMS.900.001.02
 * @Author: 王鹏
 * @Date: 2019/5/7 8:56
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "GrpHdr", "OrgnlGrpHdr", "CmonConfInf" })
public class CCMS_900_001_02_CmonConf implements Serializable, SIGN_DATA {
    private static final long serialVersionUID = -343137596164008712L;
    private GrpHdr GrpHdr = new GrpHdr();
    private OrgnlGrpHdr OrgnlGrpHdr = new OrgnlGrpHdr();
    private CmonConfInf CmonConfInf = new CmonConfInf();


    @Override
    public String toString() {
        return "通用处理确认报文[CCMS_900_001_02][" + this.GrpHdr.getMsgId() + "][" + this.CmonConfInf.getPrcCd() + "][" + this.CmonConfInf.getRjctInf() + "]";
    }

    public CCMS_900_001_02_CmonConf.GrpHdr getGrpHdr() {
        return GrpHdr;
    }

    public void setGrpHdr(CCMS_900_001_02_CmonConf.GrpHdr grpHdr) {
        GrpHdr = grpHdr;
    }

    public CCMS_900_001_02_CmonConf.OrgnlGrpHdr getOrgnlGrpHdr() {
        return OrgnlGrpHdr;
    }

    public void setOrgnlGrpHdr(CCMS_900_001_02_CmonConf.OrgnlGrpHdr orgnlGrpHdr) {
        OrgnlGrpHdr = orgnlGrpHdr;
    }

    public CCMS_900_001_02_CmonConf.CmonConfInf getCmonConfInf() {
        return CmonConfInf;
    }

    public void setCmonConfInf(CCMS_900_001_02_CmonConf.CmonConfInf cmonConfInf) {
        CmonConfInf = cmonConfInf;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "MsgId", "CreDtTm", "InstgPty", "InstdPty", "SysCd", "Rmk" })
    public static class GrpHdr implements Serializable{
        private static final long serialVersionUID = 7064458568354829448L;
        private String MsgId = null;
        private String CreDtTm = null;
        private String SysCd = null;
        private String Rmk = null;
        private InstgPty InstgPty = new InstgPty();
        private InstdPty InstdPty = new InstdPty();

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
        @XmlType(name="GrpHdr.InstgPty")
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = -3001592118091189025L;
            private String InstgDrctPty = null;
            private String InstgPty = null;

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
        @XmlType(name="GrpHdr.InstdPty")
        public static class InstdPty implements Serializable{
            private static final long serialVersionUID = 9055744269135507563L;
            private String InstdDrctPty = null;
            private String InstdPty = null;

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

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "OrgnlMsgId", "OrgnlInstgPty", "OrgnlMT" })
    public static class OrgnlGrpHdr implements Serializable {
        private static final long serialVersionUID = 6992811309663856775L;
        private String OrgnlMsgId = null; //原报文标识号
        private String OrgnlInstgPty = null; //原发起参与机构
        private String OrgnlMT = null; //原报文类型

        public String getOrgnlMsgId() {
            return OrgnlMsgId;
        }
        public void setOrgnlMsgId(String orgnlMsgId) {
            this.OrgnlMsgId = orgnlMsgId;
        }

        public String getOrgnlInstgPty() {
            return OrgnlInstgPty;
        }
        public void setOrgnlInstgPty(String orgnlInstgPty) {
            this.OrgnlInstgPty = orgnlInstgPty;
        }

        public String getOrgnlMT() {
            return OrgnlMT;
        }
        public void setOrgnlMT(String orgnlMT) {
            this.OrgnlMT = orgnlMT;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "PrcSts", "PrcCd", "PtyId", "PtyPrcCd", "RjctInf", "PrcDt" , "NetgRnd" })
    public static class CmonConfInf implements Serializable {
        private static final long serialVersionUID = 6614959647434104151L;
        private String PrcSts = null; //业务状态
        private String PtyId = null; //业务处理码
        private String PtyPrcCd = null; //拒绝业务的参与机构行号
        private String PrcCd = null; //参与机构业务拒绝码
        private String RjctInf = null; //业务拒绝信息
        private String PrcDt = null; //处理日期（终态日期）
        private String NetgRnd = null; //轧差场次
        public String getPrcSts() {
            return PrcSts;
        }
        public void setPrcSts(String prcSts) {
            PrcSts = prcSts;
        }
        public String getPtyId() {
            return PtyId;
        }
        public void setPtyId(String ptyId) {
            PtyId = ptyId;
        }
        public String getPtyPrcCd() {
            return PtyPrcCd;
        }
        public void setPtyPrcCd(String ptyPrcCd) {
            PtyPrcCd = ptyPrcCd;
        }
        public String getPrcCd() {
            return PrcCd;
        }
        public void setPrcCd(String prcCd) {
            PrcCd = prcCd;
        }
        public String getRjctInf() {
            return RjctInf;
        }
        public void setRjctInf(String rjctInf) {
            RjctInf = rjctInf;
        }
        public String getPrcDt() {
            return PrcDt;
        }
        public void setPrcDt(String prcDt) {
            PrcDt = prcDt;
        }
        public String getNetgRnd() {
            return NetgRnd;
        }
        public void setNetgRnd(String netgRnd) {
            NetgRnd = netgRnd;
        }

    }


    /**
     * @Title: signData
     * @Description: 通讯级确认报文无签名域
     * @param @return    设定文件
     * @throws
     */
    @Override
    public String signData() {
        return null;
    }

}