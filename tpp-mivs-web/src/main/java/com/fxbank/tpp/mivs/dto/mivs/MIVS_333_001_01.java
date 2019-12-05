package com.fxbank.tpp.mivs.dto.mivs;

import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.mivs.model.request.MIVS_333_001_01_FreeFrmtConf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 测试用
 * @Author: 王鹏
 * @Date: 2019/7/5 11:07
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class MIVS_333_001_01 extends DTO_BASE {

    private static final long serialVersionUID = 2403591115611898513L;
    private CCMS_990_001_02_ComConf comConf = new CCMS_990_001_02_ComConf();

    public MIVS_333_001_01() {
        super.txDesc = "公告信息确认请求333";
    }

    private MIVS_333_001_01_FreeFrmtConf FreeFrmtConf = new MIVS_333_001_01_FreeFrmtConf();

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
        return this.FreeFrmtConf.signData();
    }

    /**
     * @return the freefrmtconf
     */
    public MIVS_333_001_01_FreeFrmtConf getFreeFrmtConf() {
        return FreeFrmtConf;
    }

    /**
     * @param freefrmtconf the freefrmtconf to set
     */
    public void setFreeFrmtConf(MIVS_333_001_01_FreeFrmtConf freefrmtconf) {
        FreeFrmtConf = freefrmtconf;
    }


}