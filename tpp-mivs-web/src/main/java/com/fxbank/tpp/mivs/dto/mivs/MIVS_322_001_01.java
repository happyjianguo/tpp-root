package com.fxbank.tpp.mivs.dto.mivs;

import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.mivs.model.request.MIVS_322_001_01_GetTxPmtVrfctn;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 纳税信息网核查请求320 测试用
 * @Author: 王鹏
 * @Date: 2019/4/29 16:16
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class MIVS_322_001_01 extends DTO_BASE {

    private static final long serialVersionUID = 7043577496218974114L;
    private CCMS_990_001_02_ComConf comConf = new CCMS_990_001_02_ComConf();

    public MIVS_322_001_01() {
        super.txDesc = "纳税信息网核查请求320";
    }

    private MIVS_322_001_01_GetTxPmtVrfctn GetTxPmtVrfctn = new MIVS_322_001_01_GetTxPmtVrfctn();

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
        return this.GetTxPmtVrfctn.signData();
    }

    /**
     * @return the getTxPmtVrfctn
     */
    public MIVS_322_001_01_GetTxPmtVrfctn getTxPmtVrfctn() {
        return GetTxPmtVrfctn;
    }

    /**
     * @param getTxPmtVrfctn the getTxPmtVrfctn to set
     */
    public void setTxPmtVrfctn(MIVS_322_001_01_GetTxPmtVrfctn getTxPmtVrfctn) {
        GetTxPmtVrfctn = getTxPmtVrfctn;
    }


}