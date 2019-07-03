package com.fxbank.tpp.mivs.model.request;

import com.fxbank.tpp.mivs.model.SIGN_DATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 登记信息核查反馈
 * @Author: 王鹏
 * @Date: 2019/7/2 19:54
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "MsgHdr", "Fdbk" })
public class MIVS_349_001_01_RegVrfctnFdbk implements Serializable, SIGN_DATA {
    private static final long serialVersionUID = -287946374715284937L;
    private MsgHdr MsgHdr = new MsgHdr();
    private Fdbk Fdbk = new Fdbk();

    @Override
    public String toString() {
        return "登记信息核查结果疑义反馈报文[MIVS_349_001_01]";
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
        private static final long serialVersionUID = -9208539630451334750L;
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
            private static final long serialVersionUID = -3012110208208402354L;
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

        @XmlAccessorType(XmlAccessType.FIELD)
        public static class InstdPty implements Serializable{
            private static final long serialVersionUID = -1605396023096532633L;
            private String InstdDrctPty = null;
            private String InstdPty = null;

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
        private static final long serialVersionUID = 5301715329604874765L;
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
            private static final long serialVersionUID = 8353059006832032672L;
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
            @XmlType(propOrder = { "OrgnlVrfctnInfOfEnt", "OrgnlVrfctnInfOfSlfEplydPpl", "Rslt", "DataResrcDt"})
            public static class OrgnlVrfctnInfo implements Serializable {
                private static final long serialVersionUID = -1788371789925809875L;
                private OrgnlVrfctnInfOfEnt OrgnlVrfctnInfOfEnt = new OrgnlVrfctnInfOfEnt();
                private OrgnlVrfctnInfOfSlfEplydPpl OrgnlVrfctnInfOfSlfEplydPpl = new OrgnlVrfctnInfOfSlfEplydPpl();
                private String Rslt = null;
                private String DataResrcDt = null;

                public OrgnlVrfctnInfOfEnt getOrgnlVrfctnInfOfEnt() {
                    return OrgnlVrfctnInfOfEnt;
                }

                public void setOrgnlVrfctnInfOfEnt(OrgnlVrfctnInfOfEnt orgnlVrfctnInfOfEnt) {
                    OrgnlVrfctnInfOfEnt = orgnlVrfctnInfOfEnt;
                }

                public OrgnlVrfctnInfOfSlfEplydPpl getOrgnlVrfctnInfOfSlfEplydPpl() {
                    return OrgnlVrfctnInfOfSlfEplydPpl;
                }

                public void setOrgnlVrfctnInfOfSlfEplydPpl(OrgnlVrfctnInfOfSlfEplydPpl orgnlVrfctnInfOfSlfEplydPpl) {
                    OrgnlVrfctnInfOfSlfEplydPpl = orgnlVrfctnInfOfSlfEplydPpl;
                }

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

                @XmlAccessorType(XmlAccessType.FIELD)
                public static class OrgnlVrfctnInfOfEnt implements Serializable {
                    private static final long serialVersionUID = 6540684948059635933L;
                    private String EntNm = null;
                    private String UniSocCdtCd = null;
                    private String NmOfLglPrsn = null;
                    private String IdOfLglPrsn = null;

                    public String getEntNm() {
                        return EntNm;
                    }

                    public void setEntNm(String entNm) {
                        EntNm = entNm;
                    }

                    public String getUniSocCdtCd() {
                        return UniSocCdtCd;
                    }

                    public void setUniSocCdtCd(String uniSocCdtCd) {
                        UniSocCdtCd = uniSocCdtCd;
                    }

                    public String getNmOfLglPrsn() {
                        return NmOfLglPrsn;
                    }

                    public void setNmOfLglPrsn(String nmOfLglPrsn) {
                        NmOfLglPrsn = nmOfLglPrsn;
                    }

                    public String getIdOfLglPrsn() {
                        return IdOfLglPrsn;
                    }

                    public void setIdOfLglPrsn(String idOfLglPrsn) {
                        IdOfLglPrsn = idOfLglPrsn;
                    }
                }

                @XmlAccessorType(XmlAccessType.FIELD)
                public static class OrgnlVrfctnInfOfSlfEplydPpl implements Serializable {
                    private static final long serialVersionUID = 5030138981804130662L;
                    private String TraNm = null;
                    private String UniSocCdtCd = null;
                    private String Nm = null;
                    private String Id = null;

                    public String getTraNm() {
                        return TraNm;
                    }

                    public void setTraNm(String traNm) {
                        TraNm = traNm;
                    }

                    public String getUniSocCdtCd() {
                        return UniSocCdtCd;
                    }

                    public void setUniSocCdtCd(String uniSocCdtCd) {
                        UniSocCdtCd = uniSocCdtCd;
                    }

                    public String getNm() {
                        return Nm;
                    }

                    public void setNm(String nm) {
                        Nm = nm;
                    }

                    public String getId() {
                        return Id;
                    }

                    public void setId(String id) {
                        Id = id;
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