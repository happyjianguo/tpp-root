package com.fxbank.tpp.mivs.model.response;

import com.fxbank.tpp.mivs.model.response.CCMS_900_001_02_CmonConf;
import com.fxbank.tpp.mivs.model.SIGN_DATA;

import javax.print.DocFlavor;
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
    private static final long serialVersionUID = -3004970587967549485L;
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

    public CCMS_900_001_02_CmonConf.CmonConfInf getCmonConf() {
        return CmonConfInf;
    }
    public void setCmonConf(CCMS_900_001_02_CmonConf.CmonConfInf cmonConfInf) {
        CmonConfInf = cmonConfInf;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "MsgId", "CreDtTm", "InstgPty", "SysCd", "Rmk" })
    public static class GrpHdr implements Serializable{
        private static final long serialVersionUID = 8121528476392753031L;
        private String MsgId = null;
        private String CreDtTm = null;
        private String SysCd = null;
        private String Rmk = null;
        private CCMS_900_001_02_CmonConf.GrpHdr.InstgPty InstgPty = new CCMS_900_001_02_CmonConf.GrpHdr.InstgPty();

        /**
         * @return the msgId
         */
        public String getMsgId() {
            return MsgId;
        }

        /**
         * @param msgId the msgId to set
         */
        public void setMsgId(String msgId) {
            this.MsgId = msgId;
        }

        /**
         * @return the creDtTm
         */
        public String getCreDtTm() {
            return CreDtTm;
        }

        /**
         * @param creDtTm the creDtTm to set
         */
        public void setCreDtTm(String creDtTm) {
            this.CreDtTm = creDtTm;
        }

        /**
         * @return the SysCd
         */
        public String getSysCd() {
            return SysCd;
        }

        /**
         * @param sysCd the msgId to set
         */
        public void setSysCd(String sysCd) {
            this.SysCd = sysCd;
        }

        /**
         * @return the Rmk
         */
        public String getRmk() {
            return Rmk;
        }

        /**
         * @param rmk the msgId to set
         */
        public void setRmk(String rmk) {
            this.Rmk = rmk;
        }

        /**
         * @return the instgPty
         */
        public CCMS_900_001_02_CmonConf.GrpHdr.InstgPty getInstgPty() {
            return InstgPty;
        }

        /**
         * @param instgPty the instgPty to set
         */
        public void setInstgPty(CCMS_900_001_02_CmonConf.GrpHdr.InstgPty instgPty) {
            this.InstgPty = instgPty;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name="MsgHdr.InstgPty")
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = -5453943109475404050L;
            private String InstgDrctPty = null;
            private String InstgPty = null;

            /**
             * @return the instgDrctPty
             */
            public String getInstgDrctPty() {
                return InstgDrctPty;
            }

            /**
             * @param instgDrctPty the instgDrctPty to set
             */
            public void setInstgDrctPty(String instgDrctPty) {
                this.InstgDrctPty = instgDrctPty;
            }

            /**
             * @return the instgPty
             */
            public String getInstgPty() {
                return InstgPty;
            }

            /**
             * @param instgPty the instgPty to set
             */
            public void setInstgPty(String instgPty) {
                this.InstgPty = instgPty;
            }
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "OrgnlMsgId", "OrgnlInstgPty", "OrgnlMT" })
    public static class OrgnlGrpHdr implements Serializable {
        private static final long serialVersionUID = -2552734227495157774L;
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
        private static final long serialVersionUID = 8969553749240316746L;
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
     * @Description:通讯级确认报文无签名域
     * @param @return    设定文件
     * @throws
     */
    @Override
    public String signData() {
        return null;
    }

}