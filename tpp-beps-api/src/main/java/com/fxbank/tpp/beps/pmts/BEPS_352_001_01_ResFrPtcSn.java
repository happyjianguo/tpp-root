package com.fxbank.tpp.beps.pmts;

import com.fxbank.cip.base.anno.EsbSimuAnno;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/**
 * @author 周勇沩
 * @Description: 实时客户支付协议管理回执报文
 * @date 2020/2/21 10:12:54
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"GrpHdr", "OrgnlGrpHdr","CtrctChngRspnInf"})
public class BEPS_352_001_01_ResFrPtcSn extends REP_BASE implements Serializable, SIGN_DATA {

    private static final long serialVersionUID = 145626186505490648L;

    private static final String MESGTYPE = "beps.352.001.01";

    @EsbSimuAnno.EsbField(type = "Object")
    private GrpHdr GrpHdr = new GrpHdr();

    @EsbSimuAnno.EsbField(type = "Object")
    private OrgnlGrpHdr OrgnlGrpHdr = new OrgnlGrpHdr();

    @EsbSimuAnno.EsbField(type = "Object")
    private CtrctChngRspnInf CtrctChngRspnInf = new CtrctChngRspnInf();

    @Override
    public String toString() {
        return "实时客户支付协议管理回执报文[BEPS_352_001_01][" + this.GrpHdr.getMsgId() + "][" + this.CtrctChngRspnInf.getRspnInf() + "][" + this.CtrctChngRspnInf.getCtrctNb() + "]";
    }

    public BEPS_352_001_01_ResFrPtcSn() {
        super.mesgType = MESGTYPE;
    }

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

    public CtrctChngRspnInf getCtrctChngRspnInf() {
        return CtrctChngRspnInf;
    }

    public void setCtrctChngRspnInf(CtrctChngRspnInf ctrctChngRspnInf) {
        CtrctChngRspnInf = ctrctChngRspnInf;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"RspnInf", "CtrctRtrFlg", "CtrctNb", "OrgnlReqId", "AuthMd",
            "AuthChl", "AuthUrl", "Rmk"})
    public static class CtrctChngRspnInf implements Serializable {

        private static final long serialVersionUID = -5865956041555846419L;
        @EsbSimuAnno.EsbField(type = "Object")
        private RspnInf RspnInf = new RspnInf();
        @EsbSimuAnno.EsbField(type = "String", value = "RE00")
        private String CtrctRtrFlg;
        @EsbSimuAnno.EsbField(type = "Date", value = "yyyyMMddhhmmss")
        private String CtrctNb;
        @EsbSimuAnno.EsbField(type = "Date", value = "yyyyMMddhhmmss")
        private String OrgnlReqId;
        @EsbSimuAnno.EsbField(type = "String", value = "AM00")
        private String AuthMd;
        @EsbSimuAnno.EsbField(type = "String", value = "AH00")
        private String AuthChl;
        @EsbSimuAnno.EsbField(type = "String", value = "www")
        private String AuthUrl;
        @EsbSimuAnno.EsbField(type = "String", value = "备注")
        private String Rmk;

        public RspnInf getRspnInf() {
            return RspnInf;
        }

        public void setRspnInf(RspnInf rspnInf) {
            RspnInf = rspnInf;
        }

        public String getCtrctRtrFlg() {
            return CtrctRtrFlg;
        }

        public void setCtrctRtrFlg(String ctrctRtrFlg) {
            CtrctRtrFlg = ctrctRtrFlg;
        }

        public String getCtrctNb() {
            return CtrctNb;
        }

        public void setCtrctNb(String ctrctNb) {
            CtrctNb = ctrctNb;
        }

        public String getOrgnlReqId() {
            return OrgnlReqId;
        }

        public void setOrgnlReqId(String orgnlReqId) {
            OrgnlReqId = orgnlReqId;
        }

        public String getAuthMd() {
            return AuthMd;
        }

        public void setAuthMd(String authMd) {
            AuthMd = authMd;
        }

        public String getAuthChl() {
            return AuthChl;
        }

        public void setAuthChl(String authChl) {
            AuthChl = authChl;
        }

        public String getAuthUrl() {
            return AuthUrl;
        }

        public void setAuthUrl(String authUrl) {
            AuthUrl = authUrl;
        }

        public String getRmk() {
            return Rmk;
        }

        public void setRmk(String rmk) {
            Rmk = rmk;
        }

    }

    @Override
    public String signData() {
        StringBuffer sb = new StringBuffer();
        //GrpHdr组包
        sb.append(this.getGrpHdr().getMsgId() + "|");
        sb.append(this.getGrpHdr().getCreDtTm() + "|");
        sb.append(this.getGrpHdr().getInstgPty().getInstgDrctPty() + "|");
        sb.append(this.getGrpHdr().getInstgPty().getInstgPty() + "|");
        sb.append(this.getGrpHdr().getInstdPty().getInstdDrctPty() + "|");
        sb.append(this.getGrpHdr().getInstdPty().getInstdPty() + "|");
        sb.append(this.getGrpHdr().getSysCd() + "|");
        sb.append(this.getGrpHdr().getRmk() + "|");
        //OrgnlGrpHdr
        sb.append(this.getOrgnlGrpHdr().getOrgnlMsgId() + "|");
        sb.append(this.getOrgnlGrpHdr().getOrgnlInstgPty() + "|");
        sb.append(this.getOrgnlGrpHdr().getOrgnlMT() + "|");
        //CtrctChngRspnInf组包
        sb.append(this.CtrctChngRspnInf.getRspnInf().getSts() == null ? "" : this.CtrctChngRspnInf.getRspnInf().getSts() + "|");
        sb.append(this.CtrctChngRspnInf.getRspnInf().getRjctCd() == null ? "" : this.CtrctChngRspnInf.getRspnInf().getRjctCd() + "|");
        sb.append(this.CtrctChngRspnInf.getRspnInf().getRjctInf() == null ? "" : this.CtrctChngRspnInf.getRspnInf().getRjctInf() + "|");
        sb.append(this.CtrctChngRspnInf.getRspnInf().getPrcPty() == null ? "" : this.CtrctChngRspnInf.getRspnInf().getPrcPty() + "|");
        sb.append(this.CtrctChngRspnInf.getCtrctRtrFlg() == null ? "" : this.CtrctChngRspnInf.getCtrctRtrFlg() + "|");
        sb.append(this.CtrctChngRspnInf.getCtrctNb() == null ? "" : this.CtrctChngRspnInf.getCtrctNb() + "|");
        sb.append(this.CtrctChngRspnInf.getOrgnlReqId() == null ? "" : this.CtrctChngRspnInf.getOrgnlReqId() + "|");
        sb.append(this.CtrctChngRspnInf.getAuthMd() == null ? "" : this.CtrctChngRspnInf.getAuthMd() + "|");
        sb.append(this.CtrctChngRspnInf.getAuthChl() == null ? "" : this.CtrctChngRspnInf.getAuthChl() + "|");
        sb.append(this.CtrctChngRspnInf.getAuthUrl() == null ? "" : this.CtrctChngRspnInf.getAuthUrl() + "|");
        sb.append(this.CtrctChngRspnInf.getRmk() == null ? "" : this.CtrctChngRspnInf.getRmk() + "|");
        return sb.toString();
    }
    

}
