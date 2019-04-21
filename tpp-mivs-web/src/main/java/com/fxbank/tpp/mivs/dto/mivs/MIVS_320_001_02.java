package com.fxbank.tpp.mivs.dto.mivs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fxbank.tpp.mivs.util.PmtsXmlUtil;
   
/**
 * @Description: 解包配置例子
 * @Author: 周勇沩
 * @Date: 2019-04-20 08:57:44
 */
@XmlRootElement(name="Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class MIVS_320_001_02 {

    private static final long serialVersionUID = -3477015544924491885L;

    private GetIdVrfctn GetIdVrfctn = new GetIdVrfctn();

    /**
     * @return the getIdVrfctn
     */
    public GetIdVrfctn getGetIdVrfctn() {
        return GetIdVrfctn;
    }

    /**
     * @param getIdVrfctn the getIdVrfctn to set
     */
    public void setGetIdVrfctn(GetIdVrfctn getIdVrfctn) {
        GetIdVrfctn = getIdVrfctn;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class GetIdVrfctn {
        private MsgHdr MsgHdr = new MsgHdr();

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
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class MsgHdr {
        private String MsgId;

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
    }

    
}