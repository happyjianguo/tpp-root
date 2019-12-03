package com.fxbank.tpp.mivs.dto.mivs;

import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.mivs.model.response.MIVS_346_001_01_RtrSysSts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 企业信息联网核查查业务受理时间查询 mivs.346.001.01
 * @Author: 王鹏
 * @Date: 2019/4/30 11:27
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class MIVS_346_001_01 extends DTO_BASE {

    private static final long serialVersionUID = -4015904053170713774L;
    private CCMS_990_001_02_ComConf comConf = new CCMS_990_001_02_ComConf();

    public MIVS_346_001_01() {
        super.txDesc = "企业信息联网核查查业务受理时间查询346";
    }

    private MIVS_346_001_01_RtrSysSts RtrSysSts = new MIVS_346_001_01_RtrSysSts();

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
        return this.RtrSysSts.signData();
    }

    public MIVS_346_001_01_RtrSysSts getRtrSysSts() {
        return RtrSysSts;
    }

    public void setRtrSysSts(MIVS_346_001_01_RtrSysSts rtrSysSts) {
        RtrSysSts = rtrSysSts;
    }


}