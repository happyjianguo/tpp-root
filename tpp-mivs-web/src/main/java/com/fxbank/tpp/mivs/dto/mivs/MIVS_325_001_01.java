package com.fxbank.tpp.mivs.dto.mivs;

import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.mivs.model.response.MIVS_325_001_01_RtrRegVrfctn;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description:
 * @Author: 王鹏
 * @Date: 2019/5/20 16:51
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class MIVS_325_001_01 extends DTO_BASE {

    private static final long serialVersionUID = -4928890298018821288L;
    private CCMS_990_001_02_ComConf comConf = new CCMS_990_001_02_ComConf();

    public MIVS_325_001_01() {
        super.txDesc = "纳税信息联网核查应答323";
    }

    private MIVS_325_001_01_RtrRegVrfctn RtrRegVrfctn = new MIVS_325_001_01_RtrRegVrfctn();

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
        return this.comConf.signData();
    }

    public MIVS_325_001_01_RtrRegVrfctn getRtrRegVrfctn() {
        return RtrRegVrfctn;
    }

    public void setRtrRegVrfctn(MIVS_325_001_01_RtrRegVrfctn rtrRegVrfctn) {
        RtrRegVrfctn = rtrRegVrfctn;
    }


}