package com.fxbank.tpp.beps.pmts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.fxbank.cip.base.anno.EsbSimuAnno;

import java.io.Serializable;

/**
 * @author : 周勇沩
 * @description: 通用处理确认报文
 * @Date : 2020/2/20 20:07
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"GrpHdr", "OrgnlGrpHdr", "CmonConfInf"})
public class CCMS_900_001_02_CmonConf extends REP_BASE implements Serializable, SIGN_DATA {

    private static final long serialVersionUID = 7763154310187493781L;

    private static final String MESGTYPE = "ccms.900.001.02";

    @EsbSimuAnno.EsbField(type = "Object")
    private GrpHdr GrpHdr = new GrpHdr();
    @EsbSimuAnno.EsbField(type = "Object")
    private OrgnlGrpHdr OrgnlGrpHdr = new OrgnlGrpHdr();
    @EsbSimuAnno.EsbField(type = "Object")
    private CmonConfInf CmonConfInf = new CmonConfInf();

    public GrpHdr getGrpHdr() {
        return GrpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        GrpHdr = grpHdr;
    }

    public OrgnlGrpHdr getOrgnlGrpHdr() {
        return OrgnlGrpHdr;
    }

    public void setOrgnlGrpHdr(OrgnlGrpHdr orgnlGrpHdr) {
        OrgnlGrpHdr = orgnlGrpHdr;
    }

    @Override
    public String toString() {
        return "通用处理确认报文[CCMS_900_001_02][" + this.GrpHdr.getMsgId() + "][" + this.CmonConfInf.getPrcCd() + "][" + this.CmonConfInf.getRjctInf() + "]";
    }

    public CCMS_900_001_02_CmonConf() {
        super.mesgType = MESGTYPE;
    }


    public CCMS_900_001_02_CmonConf.CmonConfInf getCmonConfInf() {
        return CmonConfInf;
    }

    public void setCmonConfInf(CCMS_900_001_02_CmonConf.CmonConfInf cmonConfInf) {
        CmonConfInf = cmonConfInf;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"PrcSts", "PrcCd", "PtyId", "PtyPrcCd", "RjctInf", "PrcDt", "NetgRnd"})
    public static class CmonConfInf implements Serializable {

        private static final long serialVersionUID = 5485526951875655276L;

        @EsbSimuAnno.EsbField(type = "String", value = "PR03")
        private String PrcSts;      
        private String PtyId;
        private String PtyPrcCd;
        @EsbSimuAnno.EsbField(type = "String", value = "CB1I0000")
        private String PrcCd;
        private String RjctInf;
        @EsbSimuAnno.EsbField(type = "Date", value = "yyyy-MM-dd")
        private String PrcDt;
        @EsbSimuAnno.EsbField(type = "String", value = "03")
        private String NetgRnd;

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

    @Override
    public String signData() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getGrpHdr().getMsgId() + "|");
        sb.append(this.getGrpHdr().getCreDtTm() + "|");
        sb.append(this.getGrpHdr().getInstgPty().getInstgDrctPty() + "|");
        sb.append(this.getGrpHdr().getInstgPty().getInstgPty() + "|");
        sb.append(this.getGrpHdr().getInstdPty().getInstdDrctPty() + "|");
        sb.append(this.getGrpHdr().getInstdPty().getInstdPty() + "|");
        sb.append(this.getGrpHdr().getSysCd() + "|");
        sb.append(this.getOrgnlGrpHdr().getOrgnlMsgId() + "|");
        sb.append(this.getOrgnlGrpHdr().getOrgnlInstgPty() + "|");
        sb.append(this.getOrgnlGrpHdr().getOrgnlMT() + "|");
        sb.append(this.getCmonConfInf().getPrcSts() + "|");
        sb.append(this.getCmonConfInf().getPrcCd() == null ? "" : this.getCmonConfInf().getPrcCd() + "|");
        sb.append(this.getCmonConfInf().getPtyId() == null ? "" : this.getCmonConfInf().getPtyId() + "|");
        sb.append(this.getCmonConfInf().getPtyPrcCd() == null ? "" : this.getCmonConfInf().getPtyPrcCd() + "|");
        sb.append(this.getCmonConfInf().getPrcDt() == null ? "" : this.getCmonConfInf().getPrcDt() + "|");
        sb.append(this.getCmonConfInf().getNetgRnd() == null ? "" : this.getCmonConfInf().getNetgRnd() + "|");
        return sb.toString();
    }
}