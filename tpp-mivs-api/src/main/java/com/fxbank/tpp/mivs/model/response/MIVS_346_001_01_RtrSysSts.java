package com.fxbank.tpp.mivs.model.response;

import com.fxbank.tpp.mivs.model.SIGN_DATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @Description: 企业信息联网核查查业务受理时间查询应答报文 mivs.346.001.01.xml
 * @Author: 王鹏
 * @Date: 2019/4/30 11:35
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "MsgHdr", "OrgnlQryInf","RplyInf" })
public class MIVS_346_001_01_RtrSysSts implements Serializable, SIGN_DATA {

    private static final long serialVersionUID = 1263721004940538490L;
    private MsgHdr MsgHdr = new MsgHdr();
    private OrgnlQryInf OrgnlQryInf = new OrgnlQryInf();
    private RplyInf RplyInf = new RplyInf();

    @Override
    public String toString() {
        return "纳税信息联网核查应答报文[MIVS_323_001_01]";
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

    public OrgnlQryInf getOrgnlQryInf() {
        return OrgnlQryInf;
    }

    public void setOrgnlQryInf(OrgnlQryInf orgnlQryInf) {
        OrgnlQryInf = orgnlQryInf;
    }

    public RplyInf getRplyInf() {
        return RplyInf;
    }

    public void setRplyInf(RplyInf rplyInf) {
        RplyInf = rplyInf;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "MsgId", "CreDtTm", "InstgPty" })
    public static class MsgHdr implements Serializable{
        private static final long serialVersionUID = 6271499178058714356L;
        private String MsgId = null;
        private String CreDtTm = null;
        private InstgPty InstgPty = new InstgPty();

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
        @XmlType(name="MsgHdr.InstgPty")
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = -5711200171632257673L;
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
    public static class OrgnlQryInf implements Serializable{
        private static final long serialVersionUID = 3006277826196141249L;
        private String MsgId = null;
        private InstgPty InstgPty = new InstgPty();

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
        @XmlType(name="OrgnlQryInf.InstgPty")
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = 2465530143411584852L;
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
    @XmlType(propOrder = { "OrgnlQueDt","ProcSts","ProcCd","RjctInf","SvcInf" })
    public static class RplyInf implements Serializable{
        private static final long serialVersionUID = -8075776250820164979L;
        private String OrgnlQueDt = null;
        private String ProcSts = null;
        private String ProcCd = null;
        private String RjctInf = null;
        private SvcInf SvcInf = new SvcInf();

        /**
         * @return the OrgnlQueDt
         */
        public String getOrgnlQueDt() {
            return OrgnlQueDt;
        }
        /**
         * @param orgnlQueDt the orgnlQueDt to set
         */
        public void setOrgnlQueDt(String orgnlQueDt) {
            this.OrgnlQueDt = orgnlQueDt;
        }

        /**
         * @return the ProcSts
         */
        public String getProcSts() {
            return ProcSts;
        }
        /**
         * @param procSts the procSts to set
         */
        public void setProcSts(String procSts) {
            this.ProcSts = procSts;
        }

        /**
         * @return the ProcCd
         */
        public String getProcCd() {
            return ProcCd;
        }
        /**
         * @param procCd the procCd to set
         */
        public void setProcCd(String procCd) {
            this.ProcCd = procCd;
        }

        /**
         * @return the RjctInf
         */
        public String getRjctInf() {
            return RjctInf;
        }
        /**
         * @param rjctInf the rjctInf to set
         */
        public void setRjctInf(String rjctInf) {
            this.RjctInf = rjctInf;
        }

        /**
         * @return the SvcInf
         */
        public SvcInf getSvcInf() {
            return SvcInf;
        }
        /**
         * @param svcInf the svcInf to set
         */
        public void setSvcInf(SvcInf svcInf) {
            SvcInf = svcInf;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(propOrder = { "SysInd","SvcInd","SysOpTm","SysClTm" })
        public static class SvcInf implements Serializable{
            private static final long serialVersionUID = 8266735857570065992L;
            private String SysInd = null;
            private String SvcInd = null;
            private String SysOpTm = null;
            private String SysClTm = null;

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

            public String getSysOpTm() {
                return SysOpTm;
            }
            public void setSysOpTm(String sysOpTm) {
                SysOpTm = sysOpTm;
            }

            public String getSysClTm() {
                return SysClTm;
            }
            public void setSysClTm(String sysClTm) {
                SysClTm = sysClTm;
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
        sb.append(this.getOrgnlQryInf().getMsgId() + "|");
        sb.append(this.getOrgnlQryInf().getInstgPty().getInstgDrctPty() + "|");
        sb.append(this.getOrgnlQryInf().getInstgPty().getInstgPty() + "|");
        sb.append(this.getRplyInf().getOrgnlQueDt() + "|");
        sb.append(this.getRplyInf().getProcSts() + "|");
        sb.append(this.getRplyInf().getProcCd() + "|");
        if(this.getRplyInf().getProcSts().equals("PR09")) {
            sb.append(this.getRplyInf().getRjctInf() + "|");
        }
        if(!this.getRplyInf().getProcSts().equals("PR09")) {
            sb.append(this.getRplyInf().getSvcInf().getSysInd() + "|");
            sb.append(this.getRplyInf().getSvcInf().getSvcInd() + "|");
            sb.append(this.getRplyInf().getSvcInf().getSysOpTm() + "|");
            sb.append(this.getRplyInf().getSvcInf().getSysClTm() + "|");
        }

        return sb.toString();
    }

}