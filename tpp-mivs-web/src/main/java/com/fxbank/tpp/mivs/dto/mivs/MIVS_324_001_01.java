package com.fxbank.tpp.mivs.dto.mivs;

import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.mivs.model.request.MIVS_324_001_01_GetRegVrfctn;
import com.fxbank.tpp.mivs.model.request.MIVS_324_001_01_GetRegVrfctn;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 登记信息核查324 测试用
 * @Author: 王鹏
 * @Date: 2019/6/4 15:02
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class MIVS_324_001_01 extends DTO_BASE {

    private static final long serialVersionUID = 7043577496218974114L;
    private CCMS_990_001_02_ComConf comConf = new CCMS_990_001_02_ComConf();

    public MIVS_324_001_01() {
        super.txDesc = "登记信息核查324";
    }

    private MIVS_324_001_01_GetRegVrfctn GetRegVrfctn = new MIVS_324_001_01_GetRegVrfctn();

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

    /**
     * @return the getTxPmtVrfctn
     */
    public MIVS_324_001_01_GetRegVrfctn getTxPmtVrfctn() {
        return GetRegVrfctn;
    }

    /**
     * @param getTxPmtVrfctn the getTxPmtVrfctn to set
     */
    public void setTxPmtVrfctn(MIVS_324_001_01_GetRegVrfctn getTxPmtVrfctn) {
        GetRegVrfctn = getTxPmtVrfctn;
    }


}