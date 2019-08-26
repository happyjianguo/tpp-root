package com.fxbank.tpp.mivs.dto.mivs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.mivs.model.response.MIVS_321_001_01_RtrIdVrfctn;

/**
 * @Description: 人行应答321
 * @Author: 周勇沩
 * @Date: 2019-04-28 09:27:32
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class MIVS_321_001_01 extends DTO_BASE {

	private static final long serialVersionUID = 7316738795732586625L;
	private CCMS_990_001_02_ComConf comConf = new CCMS_990_001_02_ComConf();

    public MIVS_321_001_01() {
        super.txDesc = "手机号码联网核查应答321";
    }

    private MIVS_321_001_01_RtrIdVrfctn RtrIdVrfctn = new MIVS_321_001_01_RtrIdVrfctn();
    
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
        return this.RtrIdVrfctn.signData();
    }

	public MIVS_321_001_01_RtrIdVrfctn getRtrIdVrfctn() {
		return RtrIdVrfctn;
	}

	public void setRtrIdVrfctn(MIVS_321_001_01_RtrIdVrfctn rtrIdVrfctn) {
		RtrIdVrfctn = rtrIdVrfctn;
	}
   

}