package com.fxbank.tpp.mivs.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fxbank.cip.base.log.MyLog;

/**
 * @Description: 手机号码联网核查申请报文
 * @Author: 周勇沩
 * @Date: 2019-04-20 09:51:24
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class CCMS_990_001_02 extends MODEL_BASE {

    private static final long serialVersionUID = -7575351098905821390L;

    private static final String MESGTYPE = "ccms.990.001.02";

    private CCMS_990_001_02_COMCONF ComConf = new CCMS_990_001_02_COMCONF();

    public CCMS_990_001_02() {
        super(null, 0, 0, 0);
    }

    @Override
    public String signData() {
        return ComConf.signData();
    }


    /**
     * @return the comConf
     */
    public CCMS_990_001_02_COMCONF getComConf() {
        return ComConf;
    }

    /**
     * @param comConf the comConf to set
     */
    public void setComConf(CCMS_990_001_02_COMCONF comConf) {
        this.ComConf = comConf;
    }

    public CCMS_990_001_02(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = CCMS_990_001_02.MESGTYPE;
    }

}