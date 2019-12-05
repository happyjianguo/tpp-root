package com.fxbank.tpp.mivs.dto.mivs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.mivs.model.request.MIVS_320_001_01_GetIdVrfctn;

/**
 * @Description: 人行请求320(测试用)
 * @Author: 周勇沩
 * @Date: 2019-04-28 09:27:07
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class MIVS_320_001_01 extends DTO_BASE {

	private static final long serialVersionUID = 2403591115611898513L;
	private CCMS_990_001_02_ComConf comConf = new CCMS_990_001_02_ComConf();

    public MIVS_320_001_01() {
        super.txDesc = "手机号码联网核查请求320";
    }

    private MIVS_320_001_01_GetIdVrfctn GetIdVrfctn = new MIVS_320_001_01_GetIdVrfctn();
    
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
        return this.GetIdVrfctn.signData();
    }

    /**
     * @return the getIdVrfctn
     */
    public MIVS_320_001_01_GetIdVrfctn getGetIdVrfctn() {
        return GetIdVrfctn;
    }

    /**
     * @param getIdVrfctn the getIdVrfctn to set
     */
    public void setGetIdVrfctn(MIVS_320_001_01_GetIdVrfctn getIdVrfctn) {
        GetIdVrfctn = getIdVrfctn;
    }


}