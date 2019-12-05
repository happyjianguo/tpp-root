package com.fxbank.tpp.mivs.dto.mivs;

import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.mivs.model.request.MIVS_326_001_01_AcctInfoFdbk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 企业开销户状态反馈模拟用
 * @Author: 王鹏
 * @Date: 2019/6/14 16:50
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class MIVS_326_001_01 extends DTO_BASE {
    private static final long serialVersionUID = 1833317025608900429L;
    private CCMS_990_001_02_ComConf comConf = new CCMS_990_001_02_ComConf();

    public MIVS_326_001_01() {
        super.txDesc = "企业开销户状态反馈326";
    }

    private MIVS_326_001_01_AcctInfoFdbk AcctInfoFdbk = new MIVS_326_001_01_AcctInfoFdbk();

    /**
     * @return the comConf
     */
    public CCMS_990_001_02_ComConf getComConf() {
        return this.comConf;
    }

    /**
     * @param comConf the comConf to set
     */
    public void setComConf(CCMS_990_001_02_ComConf comConf) {
        this.comConf = comConf;
    }

    @Override
    public String signData() {
        return this.AcctInfoFdbk.signData();
    }

    /**
     * @return the getAcctInfoFdbk
     */
    public MIVS_326_001_01_AcctInfoFdbk getAcctInfoFdbk() {
        return AcctInfoFdbk;
    }

    /**
     * @param getAcctInfoFdbk the getAcctInfoFdbk to set
     */
    public void setAcctInfoFdbk(MIVS_326_001_01_AcctInfoFdbk getAcctInfoFdbk) {
        AcctInfoFdbk = getAcctInfoFdbk;
    }


}