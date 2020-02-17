package com.fxbank.tpp.beps.model;

import com.fxbank.tpp.beps.model.SIGN_DATA;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/** 
* @ClassName: CCMS_911_001_02_DscrdMsgNtfctn 
* @Description: 报文丢弃通知报文主节点
* @author Duzhenduo
* @date 2019年4月25日 上午10:26:05 
*  
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "DscrdInf" })
public class CCMS_911_001_02_DscrdMsgNtfctn implements Serializable, SIGN_DATA {

	private static final long serialVersionUID = 2379816446943226265L;
	private DscrdInf DscrdInf = new DscrdInf();

    @Override
    public String toString() {
        return "报文丢弃通知[CCMS_911_001_02][" + this.DscrdInf.getMsgId() + "][" + this.DscrdInf.getPrcCd() + "][" + this.DscrdInf.getRjctInf() + "]";
    }

   

    public DscrdInf getDscrdInf() {
		return DscrdInf;
	}


	public void setDscrdInf(DscrdInf dscrdInf) {
		DscrdInf = dscrdInf;
	}



	@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "OrigSndr", "OrigSndDt", "MT", "MsgId", "MsgRefId", "PrcCd" , "RjctInf" })
    public static class DscrdInf implements Serializable {
		private static final long serialVersionUID = 5457555235557827766L;
		private String OrigSndr = null;
        private String OrigSndDt = null;
        private String MT = null;
        private String MsgId = null;
        private String MsgRefId = null;
        private String PrcCd = null;
        private String RjctInf = null;
		public String getOrigSndr() {
			return OrigSndr;
		}
		public void setOrigSndr(String origSndr) {
			OrigSndr = origSndr;
		}
		public String getOrigSndDt() {
			return OrigSndDt;
		}
		public void setOrigSndDt(String origSndDt) {
			OrigSndDt = origSndDt;
		}
		public String getMT() {
			return MT;
		}
		public void setMT(String mT) {
			MT = mT;
		}
		public String getMsgId() {
			return MsgId;
		}
		public void setMsgId(String msgId) {
			MsgId = msgId;
		}
		public String getMsgRefId() {
			return MsgRefId;
		}
		public void setMsgRefId(String msgRefId) {
			MsgRefId = msgRefId;
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