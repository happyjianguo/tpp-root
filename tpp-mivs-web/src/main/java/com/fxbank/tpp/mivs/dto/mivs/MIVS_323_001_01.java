package com.fxbank.tpp.mivs.dto.mivs;

import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.mivs.model.response.MIVS_323_001_01_RtrTxPmtVrfctn;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 纳税信息联网核查应答 mivs.323.001.01
 * @Author: 王鹏
 * @Date: 2019/4/29 15:54
 */

@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class MIVS_323_001_01 extends DTO_BASE {

    private static final long serialVersionUID = -4928890298018821288L;
    private CCMS_990_001_02_ComConf comConf = new CCMS_990_001_02_ComConf();

    public MIVS_323_001_01() {
        super.txDesc = "纳税信息联网核查应答323";
    }

    private MIVS_323_001_01_RtrTxPmtVrfctn RtrTxPmtVrfctn = new MIVS_323_001_01_RtrTxPmtVrfctn();

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
        return this.RtrTxPmtVrfctn.signData();
    }

    public MIVS_323_001_01_RtrTxPmtVrfctn getRtrTxPmtVrfctn() {
        return RtrTxPmtVrfctn;
    }

    public void setRtrTxPmtVrfctn(MIVS_323_001_01_RtrTxPmtVrfctn rtrTxPmtVrfctn) {
        RtrTxPmtVrfctn = rtrTxPmtVrfctn;
    }


}