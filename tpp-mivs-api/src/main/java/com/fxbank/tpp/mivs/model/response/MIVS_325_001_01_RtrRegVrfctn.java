package com.fxbank.tpp.mivs.model.response;

import com.fxbank.tpp.mivs.model.SIGN_DATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: 王鹏
 * @Date: 2019/5/20 16:38
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "MsgHdr", "OrgnlBizQry","Rspsn" })
public class MIVS_325_001_01_RtrRegVrfctn implements Serializable, SIGN_DATA {
    private static final long serialVersionUID = -4933387881402294675L;
    private MIVS_325_001_01_RtrRegVrfctn.MsgHdr MsgHdr = new MIVS_325_001_01_RtrRegVrfctn.MsgHdr();
    private MIVS_325_001_01_RtrRegVrfctn.OrgnlBizQry OrgnlBizQry = new MIVS_325_001_01_RtrRegVrfctn.OrgnlBizQry();
    private MIVS_325_001_01_RtrRegVrfctn.Rspsn Rspsn = new MIVS_325_001_01_RtrRegVrfctn.Rspsn();

    @Override
    public String toString() {
        return "纳税信息联网核查应答报文[MIVS_325_001_01]";
    }

    /**
     * @return the msgHdr
     */
    public MIVS_325_001_01_RtrRegVrfctn.MsgHdr getMsgHdr() {
        return MsgHdr;
    }

    /**
     * @param msgHdr the msgHdr to set
     */
    public void setMsgHdr(MIVS_325_001_01_RtrRegVrfctn.MsgHdr msgHdr) {
        this.MsgHdr = msgHdr;
    }

    public MIVS_325_001_01_RtrRegVrfctn.OrgnlBizQry getOrgnlBizQry() {
        return OrgnlBizQry;
    }

    public void setOrgnlBizQry(MIVS_325_001_01_RtrRegVrfctn.OrgnlBizQry orgnlBizQry) {
        OrgnlBizQry = orgnlBizQry;
    }

    public MIVS_325_001_01_RtrRegVrfctn.Rspsn getRspsn() {
        return Rspsn;
    }

    public void setRspsn(MIVS_325_001_01_RtrRegVrfctn.Rspsn rspsn) {
        Rspsn = rspsn;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "MsgId", "CreDtTm", "InstgPty" })
    public static class MsgHdr implements Serializable{
        private static final long serialVersionUID = 8127207925264274988L;
        private String MsgId = null;
        private String CreDtTm = null;
        private MIVS_325_001_01_RtrRegVrfctn.MsgHdr.InstgPty InstgPty = new MIVS_325_001_01_RtrRegVrfctn.MsgHdr.InstgPty();
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
         * @return the instgPty
         */
        public MIVS_325_001_01_RtrRegVrfctn.MsgHdr.InstgPty getInstgPty() {
            return InstgPty;
        }

        /**
         * @param instgPty the instgPty to set
         */
        public void setInstgPty(MIVS_325_001_01_RtrRegVrfctn.MsgHdr.InstgPty instgPty) {
            this.InstgPty = instgPty;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name="MsgHdr.InstgPty")
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = -784092537111925254L;
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
    @XmlType(propOrder = { "MsgId", "InstgPty" })
    public static class OrgnlBizQry implements Serializable{
        private static final long serialVersionUID = -1561794738551219893L;
        private String MsgId = null;
        private MIVS_325_001_01_RtrRegVrfctn.OrgnlBizQry.InstgPty InstgPty = new MIVS_325_001_01_RtrRegVrfctn.OrgnlBizQry.InstgPty();
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
         * @return the instgPty
         */
        public MIVS_325_001_01_RtrRegVrfctn.OrgnlBizQry.InstgPty getInstgPty() {
            return InstgPty;
        }

        /**
         * @param instgPty the instgPty to set
         */
        public void setInstgPty(MIVS_325_001_01_RtrRegVrfctn.OrgnlBizQry.InstgPty instgPty) {
            this.InstgPty = instgPty;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name="OrgnlBizQry.InstgPty")
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = -4572114621548591256L;
            private String InstgDrctPty = null;
            private String InstgPty = null;

            /**
             * @return the instgDrctPty
             */
            public String getInstgDrctPty() {
                return InstgDrctPty;
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

            /**
             * @param instgDrctPty the instgDrctPty to set
             */
            public void setInstgDrctPty(String instgDrctPty) {
                this.InstgDrctPty = instgDrctPty;
            }
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "VrfctnInf","OprlErr" })
    public static class Rspsn implements Serializable{
        private static final long serialVersionUID = 3266024638124569555L;
        private MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf VrfctnInf = new MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf();
        private MIVS_325_001_01_RtrRegVrfctn.Rspsn.OprlErr OprlErr = new MIVS_325_001_01_RtrRegVrfctn.Rspsn.OprlErr();

        public MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf getVrfctnInf() {
            return VrfctnInf;
        }

        public void setVrfctnInf(MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf vrfctnInf) {
            VrfctnInf = vrfctnInf;
        }

        public MIVS_325_001_01_RtrRegVrfctn.Rspsn.OprlErr getOprlErr() {
            return OprlErr;
        }


        public void setOprlErr(MIVS_325_001_01_RtrRegVrfctn.Rspsn.OprlErr oprlErr) {
            OprlErr = oprlErr;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(propOrder = { "Rslt","DataResrcDt","TxpmtInf"})
        public static class VrfctnInf implements Serializable{
            private static final long serialVersionUID = 293223418691401711L;
            private String Rslt = null;
            private String DataResrcDt = null;
            private List<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.TxpmtInf> TxpmtInf;

            public String getRslt() {
                return Rslt;
            }
            public void setRslt(String rslt) {
                Rslt = rslt;
            }

            public String getDataResrcDt() {
                return DataResrcDt;
            }
            public void setDataResrcDt(String dataResrcDt) {
                DataResrcDt = dataResrcDt;
            }

            public List<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.TxpmtInf> getTxpmtInf() {
                return TxpmtInf;
            }

            public void setTxpmtInf(List<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.TxpmtInf> txpmtInf) {
                TxpmtInf = txpmtInf;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "VrfctnInf.TxpmtInf")
            public static class TxpmtInf implements Serializable{
                private static final long serialVersionUID = 9160840032547969960L;
                private String TxAuthCd = null;
                private String TxAuthNm = null;
                private String TxpySts = null;

                /**
                 * @return the TxAuthCd
                 */
                public String getTxAuthCd() {
                    return TxAuthCd;
                }
                /**
                 * @param txAuthCd the txAuthCd to set
                 */
                public void setTxAuthCd(String txAuthCd) {
                    TxAuthCd = txAuthCd;
                }

                /**
                 * @return the TxAuthNm
                 */
                public String getTxAuthNm() {
                    return TxAuthNm;
                }
                /**
                 * @param txAuthNm the txAuthNm to set
                 */
                public void setTxAuthNm(String txAuthNm) {
                    TxAuthNm = txAuthNm;
                }

                /**
                 * @return the TxpySts
                 */
                public String getTxpySts() {
                    return TxpySts;
                }
                /**
                 * @param txpySts the txpySts to set
                 */
                public void setTxpySts(String txpySts) {
                    TxpySts = txpySts;
                }
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(propOrder = {"ProcSts","ProcCd","Rjctinf"})
        public static class OprlErr implements Serializable{
            private static final long serialVersionUID = 5982468996471624782L;
            private String ProcSts;
            private String ProcCd;
            private String Rjctinf;

            public String getProcSts() {
                return ProcSts;
            }
            public void setProcSts(String procSts) {
                ProcSts = procSts;
            }

            public String getProcCd() {
                return ProcCd;
            }
            public void setProcCd(String procCd) {
                ProcCd = procCd;
            }

            public String getRjctinf() {
                return Rjctinf;
            }
            public void setRjctinf(String rjctinf) {
                Rjctinf = rjctinf;
            }


        }
    }

    @Override
    public String signData() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getMsgHdr().getMsgId() + "|");
        sb.append(this.getMsgHdr().getCreDtTm() + "|");
        sb.append(this.getMsgHdr().getInstgPty().getInstgDrctPty() + "|");
        sb.append(this.getMsgHdr().getInstgPty().getInstgPty() + "|");
        sb.append(this.getOrgnlBizQry().getMsgId() + "|");
        sb.append(this.getOrgnlBizQry().getInstgPty().getInstgDrctPty() + "|");
        sb.append(this.getOrgnlBizQry().getInstgPty().getInstgPty() + "|");
        if(this.getRspsn().getVrfctnInf().getRslt().equals("MCHD")) {
            sb.append(this.getRspsn().getVrfctnInf().getRslt() + "|");
            sb.append(this.getRspsn().getVrfctnInf().getDataResrcDt() + "|");
            sb.append(this.getRspsn().getVrfctnInf().getTxpmtInf() + "|");
        }else if(this.getRspsn().getOprlErr().getProcCd()!=null){
            sb.append(this.getRspsn().getOprlErr().getProcSts() + "|");
            sb.append(this.getRspsn().getOprlErr().getProcCd() + "|");
        }
        return sb.toString();
    }

}