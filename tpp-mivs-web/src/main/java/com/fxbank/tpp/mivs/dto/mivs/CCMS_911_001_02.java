package com.fxbank.tpp.mivs.dto.mivs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.fxbank.tpp.mivs.model.CCMS_911_001_02_DscrdMsgNtfctn;

/**
 * @Description: 人行请求911
 * @Author: 周勇沩
 * @Date: 2019-04-28 09:28:00
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class CCMS_911_001_02 extends DTO_BASE {

	private static final long serialVersionUID = -896812704197825588L;
	private CCMS_911_001_02_DscrdMsgNtfctn DscrdMsgNtfctn = new CCMS_911_001_02_DscrdMsgNtfctn();

    public CCMS_911_001_02() {
        super.txDesc = "报文丢弃通知911";
    }

    public CCMS_911_001_02_DscrdMsgNtfctn getDscrdMsgNtfctn() {
		return DscrdMsgNtfctn;
	}

	public void setDscrdMsgNtfctn(CCMS_911_001_02_DscrdMsgNtfctn dscrdMsgNtfctn) {
		DscrdMsgNtfctn = dscrdMsgNtfctn;
	}

	@Override
    public String signData() {
        return this.DscrdMsgNtfctn.signData();
    }

}