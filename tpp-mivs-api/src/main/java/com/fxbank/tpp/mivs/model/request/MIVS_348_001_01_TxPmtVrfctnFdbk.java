package com.fxbank.tpp.mivs.model.request;

import com.fxbank.tpp.mivs.model.SIGN_DATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @Description:
 * @Author: 王鹏
 * @Date: 2019/5/13 17:46
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "MsgHdr", "Fdbk" })
public class MIVS_348_001_01_TxPmtVrfctnFdbk implements Serializable, SIGN_DATA {
    private static final long serialVersionUID = 3079722949164892776L;
    private MsgHdr MsgHdr = new MsgHdr();
    private Fdbk Fdbk = new Fdbk();

    @Override
    public String toString() {
        return "纳税信息核查结果疑义反馈报文[MIVS_348_001_01]";
    }

    /**
     * @return the msgHdr
     */
    public MsgHdr getMsgHdr() {
        return MsgHdr;
    }

    /**
     * @param msgHdr the msgHdr to set
     */
    public void setMsgHdr(MsgHdr msgHdr) {
        this.MsgHdr = msgHdr;
    }

    /**
     * @return the fdbk
     */
    public Fdbk getFdbk() {
        return Fdbk;
    }

    /**
     * @param fdbk the fdbk to set
     */
    public void setFdbk(Fdbk fdbk) {
        this.Fdbk = fdbk;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "MsgId", "CreDtTm", "InstgPty", "InstdPty" })
    public static class MsgHdr implements Serializable {
        private static final long serialVersionUID = 8422215765668873527L;
        private String MsgId = null;
        private String CreDtTm = null;
        private InstgPty InstgPty = new InstgPty();
        private InstdPty InstdPty = new InstdPty();

        /**
         * @return the msgId
         */
        public String getMsgId() {
            return MsgId;
        }

        /**
         * @return the instdPty
         */
        public InstdPty getInstdPty() {
            return InstdPty;
        }

        /**
         * @param instdPty the instdPty to set
         */
        public void setInstdPty(InstdPty instdPty) {
            this.InstdPty = instdPty;
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
         * @param msgId the msgId to set
         */
        public void setMsgId(String msgId) {
            this.MsgId = msgId;
        }

        /**
         * @return the instgPty
         */
        public InstgPty getInstgPty() {
            return InstgPty;
        }

        /**
         * @param instgPty the instgPty to set
         */
        public void setInstgPty(InstgPty instgPty) {
            this.InstgPty = instgPty;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = -2970202727987414731L;
            private String InstgDrctPty = null;
            private String DrctPtyNm = null;
            private String InstgPty = null;
            private String PtyNm = null;

            /**
             * @return the instgDrctPty
             */
            public String getInstgDrctPty() {
                return InstgDrctPty;
            }

            /**
             * @return the ptyNm
             */
            public String getPtyNm() {
                return PtyNm;
            }

            /**
             * @param ptyNm the ptyNm to set
             */
            public void setPtyNm(String ptyNm) {
                this.PtyNm = ptyNm;
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
             * @return the drctPtyNm
             */
            public String getDrctPtyNm() {
                return DrctPtyNm;
            }

            /**
             * @param drctPtyNm the drctPtyNm to set
             */
            public void setDrctPtyNm(String drctPtyNm) {
                this.DrctPtyNm = drctPtyNm;
            }

            /**
             * @param instgDrctPty the instgDrctPty to set
             */
            public void setInstgDrctPty(String instgDrctPty) {
                this.InstgDrctPty = instgDrctPty;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        public static class InstdPty implements Serializable{
            private static final long serialVersionUID = -9101025784924839328L;
            private String InstdDrctPty = "0000";
            private String InstdPty = "0000";

            /**
             * @return the instdDrctPty
             */
            public String getInstdDrctPty() {
                return InstdDrctPty;
            }

            /**
             * @return the instdPty
             */
            public String getInstdPty() {
                return InstdPty;
            }

            /**
             * @param instdPty the instdPty to set
             */
            public void setInstdPty(String instdPty) {
                this.InstdPty = instdPty;
            }

            /**
             * @param instdDrctPty the instdDrctPty to set
             */
            public void setInstdDrctPty(String instdDrctPty) {
                this.InstdDrctPty = instdDrctPty;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "SysInd", "OrgnlVrfctn", "Cntt", "ContactNm", "ContactNb"})
    public static class Fdbk implements Serializable{
        private static final long serialVersionUID = -1920158947608994691L;
        private String SysInd = null;
        private OrgnlVrfctn OrgnlVrfctn = new OrgnlVrfctn();
        private String Cntt = null;
        private String ContactNm = null;
        private String ContactNb = null;

        /**
         * @return the SysInd
         */
        public String getSysInd() {
            return SysInd;
        }
        /**
         * @param sysInd the sysInd to set
         */
        public void setSysInd(String sysInd) {
            this.SysInd = sysInd;
        }

        public OrgnlVrfctn getOrgnlVrfctn() {
            return OrgnlVrfctn;
        }
        public void setOrgnlVrfctn(OrgnlVrfctn orgnlVrfctn) {
            this.OrgnlVrfctn = orgnlVrfctn;
        }

        /**
         * @return the cntt
         */
        public String getCntt() {
            return Cntt;
        }
        /**
         * @param cntt the cntt to set
         */
        public void setCntt(String cntt) {
            this.Cntt = cntt;
        }

        /**
         * @return the contactNm
         */
        public String getContactNm() {
            return ContactNm;
        }
        /**
         * @param contactNm the contactNm to set
         */
        public void setContactNm(String contactNm) {
            this.ContactNm = contactNm;
        }

        /**
         * @return the contactNb
         */
        public String getContactNb() {
            return ContactNb;
        }
        /**
         * @param contactNb the contactNb to set
         */
        public void setContactNb(String contactNb) {
            this.ContactNb = contactNb;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(propOrder = { "OrgnlDlvrgMsgId", "OrgnlRcvgMsgId", "OrgnlVrfctnInfo"})
        public static class OrgnlVrfctn implements Serializable {
            private static final long serialVersionUID = -8455053784531181518L;
            private String OrgnlDlvrgMsgId = null;
            private String OrgnlRcvgMsgId = null;
            private OrgnlVrfctnInfo OrgnlVrfctnInfo = new OrgnlVrfctnInfo();

            public String getOrgnlDlvrgMsgId() {
                return OrgnlDlvrgMsgId;
            }
            public void setOrgnlDlvrgMsgId(String orgnlDlvrgMsgId) {
                this.OrgnlDlvrgMsgId = orgnlDlvrgMsgId;
            }

            public String getOrgnlRcvgMsgId() {
                return OrgnlRcvgMsgId;
            }
            public void setOrgnlRcvgMsgId(String orgnlRcvgMsgId) {
                this.OrgnlRcvgMsgId = orgnlRcvgMsgId;
            }

            public OrgnlVrfctnInfo getOrgnlVrfctnInfo() {
                return OrgnlVrfctnInfo;
            }
            public void setOrgnlVrfctnInfo(OrgnlVrfctnInfo orgnlVrfctnInfo) {
                this.OrgnlVrfctnInfo = orgnlVrfctnInfo;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            public static class OrgnlVrfctnInfo implements Serializable {
                private static final long serialVersionUID = 2786932349128861617L;
                private String CoNm = null;
                public String getCoNm() {
                    return CoNm;
                }
                public void setCoNm(String coNm) {
                    this.CoNm = coNm;
                }

                private String UniSocCdtCd = null;
                public String getUniSocCdtCd() {
                    return UniSocCdtCd;
                }
                public void setUniSocCdtCd(String uniSocCdtCd) {
                    this.UniSocCdtCd = uniSocCdtCd;
                }

                private String TxpyrIdNb = null;
                public String getTxpyrIdNb() {
                    return TxpyrIdNb;
                }
                public void setTxpyrIdNb(String txpyrIdNb) {
                    this.TxpyrIdNb = txpyrIdNb;
                }

                private String Rslt = null;
                public String getRslt() {
                    return Rslt;
                }
                public void setRslt(String rslt) {
                    this.Rslt = rslt;
                }

                private String DataResrcDt = null;
                public String getDataResrcDt() {
                    return DataResrcDt;
                }
                public void setDataResrcDt(String dataResrcDt) {
                    this.DataResrcDt = dataResrcDt;
                }

                private TxpmtInf TxpmtInf = new TxpmtInf();
                public TxpmtInf getTxpmtInf() {
                    return TxpmtInf;
                }
                public void setTxpmtInf(TxpmtInf txpmtInf) {
                    this.TxpmtInf = txpmtInf;
                }

                @XmlAccessorType(XmlAccessType.FIELD)
                public static class TxpmtInf implements Serializable {
                    private static final long serialVersionUID = 4678125740927063368L;

                    private String TxAuthCd = null;
                    public String getTxAuthCd() {
                        return TxAuthCd;
                    }
                    public void setTxAuthCd(String txAuthCd) {
                        this.TxAuthCd = txAuthCd;
                    }

                    private String TxAuthNm = null;
                    public String getTxAuthNm() {
                        return TxAuthNm;
                    }
                    public void setTxAuthNm(String txAuthNm) {
                        this.TxAuthNm = txAuthNm;
                    }

                    private String TxpyrSts = null;
                    public String getTxpyrSts() {
                        return TxpyrSts;
                    }
                    public void setTxpyrSts(String txpyrSts) {
                        this.TxpyrSts = txpyrSts;
                    }
                }
            }
        }

    }

    @Override
    public String signData() {
        StringBuffer sb = new StringBuffer();
//        sb.append(this.getMsgHdr().getMsgId() + "|");
//        sb.append(this.getMsgHdr().getCreDtTm() + "|");
//        sb.append(this.getMsgHdr().getInstgPty().getInstgDrctPty() + "|");
//        sb.append(this.getMsgHdr().getInstgPty().getDrctPtyNm() + "|");
//        sb.append(this.getMsgHdr().getInstgPty().getInstgPty() + "|");
//        sb.append(this.getMsgHdr().getInstgPty().getPtyNm() + "|");
//        sb.append(this.getMsgHdr().getInstdPty().getInstdDrctPty() + "|");
//        sb.append(this.getMsgHdr().getInstdPty().getInstdPty() + "|");
//        sb.append(this.getVryDef().getSystemIndicator() + "|");
//        sb.append(this.getVryDef().getQueryDate() + "|");
//        return sb.toString();
        return null;
    }

}