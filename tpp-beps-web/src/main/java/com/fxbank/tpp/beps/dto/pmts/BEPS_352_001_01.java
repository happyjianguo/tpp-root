package com.fxbank.tpp.beps.dto.pmts;

import com.fxbank.tpp.beps.pmts.BEPS_352_001_01_ResFrPtcSn;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @description: 客户支付协议管理应答报文
 * @author     : 周勇沩
 * @Date       : 2020/2/26 12:34
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class BEPS_352_001_01 extends DTO_BASE {

    private static final long serialVersionUID = 7453025526754655312L;

    public BEPS_352_001_01() {
        super.txDesc = "客户支付协议管理应答报文";
    }

    private BEPS_352_001_01_ResFrPtcSn resFrPtcSn = new BEPS_352_001_01_ResFrPtcSn();

    public BEPS_352_001_01_ResFrPtcSn getResFrPtcSn() {
        return resFrPtcSn;
    }

    public void setResFrPtcSn(BEPS_352_001_01_ResFrPtcSn resFrPtcSn) {
        this.resFrPtcSn = resFrPtcSn;
    }

    @Override
    public String signData() {
        return resFrPtcSn.signData();
    }
}