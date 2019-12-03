package com.fxbank.tpp.mivs.model;

import com.fxbank.tpp.mivs.model.SIGN_DATA;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @Description: 通讯级确认应答报文主节点
 * @Author: 周勇沩
 * @Date: 2019-04-23 20:41:27
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "ConfInf" })
public class CCMS_990_001_02_ComConf implements Serializable, SIGN_DATA {

    private static final long serialVersionUID = -704157491490561912L;

    private ConfInf ConfInf = new ConfInf();

    @Override
    public String toString() {
        return "通用处理确认[CCMS_990_001_02][" + this.ConfInf.getMsgId() + "][" + this.ConfInf.getMsgPrcCd() + "]";
    }

    /**
     * @return the confInf
     */
    public ConfInf getConfInf() {
        return ConfInf;
    }

    /**
     * @param confInf the confInf to set
     */
    public void setConfInf(ConfInf confInf) {
        this.ConfInf = confInf;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "OrigSndr", "OrigSndDt", "MT", "MsgId", "MsgRefId", "MsgPrcCd" })
    public static class ConfInf implements Serializable {
        private static final long serialVersionUID = -9024584215900455140L;
        private String OrigSndr = null;
        private String OrigSndDt = null;
        private String MT = null;
        private String MsgId = null;
        private String MsgRefId = null;
        private String MsgPrcCd = null;

        /**
         * @return the origSndr
         */
        public String getOrigSndr() {
            return OrigSndr;
        }

        /**
         * @return the msgPrcCd
         */
        public String getMsgPrcCd() {
            return MsgPrcCd;
        }

        /**
         * @param msgPrcCd the msgPrcCd to set
         */
        public void setMsgPrcCd(String msgPrcCd) {
            this.MsgPrcCd = msgPrcCd;
        }

        /**
         * @return the msgRefId
         */
        public String getMsgRefId() {
            return MsgRefId;
        }

        /**
         * @param msgRefId the msgRefId to set
         */
        public void setMsgRefId(String msgRefId) {
            this.MsgRefId = msgRefId;
        }

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
         * @return the mT
         */
        public String getMT() {
            return MT;
        }

        /**
         * @param mT the mT to set
         */
        public void setMT(String mT) {
            this.MT = mT;
        }

        /**
         * @return the origSndDt
         */
        public String getOrigSndDt() {
            return OrigSndDt;
        }

        /**
         * @param origSndDt the origSndDt to set
         */
        public void setOrigSndDt(String origSndDt) {
            this.OrigSndDt = origSndDt;
        }

        /**
         * @param origSndr the origSndr to set
         */
        public void setOrigSndr(String origSndr) {
            this.OrigSndr = origSndr;
        }

    }

    /**
     * @Description: 通讯级确认报文无签名域
     * @Author: 周勇沩
     * @Date: 2019-04-23 21:03:21
     */
    @Override
    public String signData() {
        return null;
    }

}