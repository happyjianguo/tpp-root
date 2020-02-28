package com.fxbank.tpp.beps.pmts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @author 周勇沩
 * @Description: 客户身份认证回执报文
 * @date 2020/2/21 10:12:54
 */
public class BEPS_353_001_01_TxAuthReq extends REP_BASE implements Serializable, SIGN_DATA {

    private static final long serialVersionUID = 7027401474114391377L;

    private static final String MESGTYPE = "beps.353.001.01";

    private GrpHdr GrpHdr = new GrpHdr();

    private OrgnlGrpHdr OrgnlGrpHdr = new OrgnlGrpHdr();

    private CtrctChngRspnInf CtrctChngRspnInf = new CtrctChngRspnInf();

    public BEPS_353_001_01_TxAuthReq() {
        super.mesgType = MESGTYPE;
    }

    @Override
    public String toString() {
        return "客户身份认证回执报文[BEPS_353_001_01][" + this.GrpHdr.getMsgId() + "][" + this.CtrctChngRspnInf.getCtrctNb() + "][" + this.CtrctChngRspnInf.getAuthCnt() + "]";
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
    @XmlType(propOrder = {"CtrctNb", "OrgnlReqId", "AuthCnt", "Rmk"})
    public static class CtrctChngRspnInf implements Serializable {

        private static final long serialVersionUID = -3564252489098769732L;

        private String CtrctNb;
        private String OrgnlReqId;
        private String AuthCnt;
        private String Rmk;

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

        public String getAuthCnt() {
            return AuthCnt;
        }

        public void setAuthCnt(String authCnt) {
            AuthCnt = authCnt;
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
        //OrgnlGrpHdr组包
        sb.append(this.getOrgnlGrpHdr().getOrgnlMsgId() + "|");
        sb.append(this.getOrgnlGrpHdr().getOrgnlInstgPty() + "|");
        sb.append(this.getOrgnlGrpHdr().getOrgnlMT() + "|");
        //CtrctChngRspnInf组包
        sb.append(this.CtrctChngRspnInf.getCtrctNb() == null ? "" : this.CtrctChngRspnInf.getCtrctNb() + "|");
        sb.append(this.CtrctChngRspnInf.getOrgnlReqId() == null ? "" : this.CtrctChngRspnInf.getOrgnlReqId() + "|");
        sb.append(this.CtrctChngRspnInf.getAuthCnt() == null ? "" : this.CtrctChngRspnInf.getAuthCnt() + "|");
        sb.append(this.CtrctChngRspnInf.getRmk() == null ? "" : this.CtrctChngRspnInf.getRmk() + "|");
        return sb.toString();
    }

}
