package com.fxbank.tpp.mivs.dto.mivs;

import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.mivs.model.request.MIVS_345_001_01_GetSysSts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 企业信息联网核查业务受理时间查询报文345 测试用
 * @Author: 王鹏
 * @Date: 2019/5/10 8:59
 */

@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class MIVS_345_001_01 extends DTO_BASE {

    private static final long serialVersionUID = 7043577496218974114L;
    private CCMS_990_001_02_ComConf comConf = new CCMS_990_001_02_ComConf();

    public MIVS_345_001_01() {
        super.txDesc = "企业信息联网核查业务受理时间查询报文345";
    }

    private MIVS_345_001_01_GetSysSts GetSysSts = new MIVS_345_001_01_GetSysSts();

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
        return this.GetSysSts.signData();
    }

    /**
     * @return the getSysSts
     */
    public MIVS_345_001_01_GetSysSts getSysSts() {
        return GetSysSts;
    }

    /**
     * @param getSysSts the getSysSts to set
     */
    public void setSysSts(MIVS_345_001_01_GetSysSts getSysSts) {
        GetSysSts = getSysSts;
    }


}