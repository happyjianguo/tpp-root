package com.fxbank.tpp.mivs.dto.mivs;

import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.mivs.model.response.MIVS_332_001_01_FreeFrmt;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 公告信息报文
 * @Author: 王鹏
 * @Date: 2019/7/3 16:36
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class MIVS_332_001_01 extends DTO_BASE {
    private static final long serialVersionUID = -852433851834186525L;
    private CCMS_990_001_02_ComConf comConf = new CCMS_990_001_02_ComConf();

    public MIVS_332_001_01() {
        super.txDesc = "公告信息报文332";
    }

    private MIVS_332_001_01_FreeFrmt FreeFrmt = new MIVS_332_001_01_FreeFrmt();

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
        return this.FreeFrmt.signData();
    }

    public MIVS_332_001_01_FreeFrmt getFreeFrmt() {
        return FreeFrmt;
    }

    public void setFreeFrmt(MIVS_332_001_01_FreeFrmt freeFrmt) {
        this.FreeFrmt = freeFrmt;
    }
}