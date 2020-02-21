package com.fxbank.tpp.beps.pmts;

import com.fxbank.cip.base.log.MyLog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 
* @ClassName: BEPS_351_001_01 
* @Description: 实时客户支付协议管理报文 
* @author YePuLiang
* @date 2020年2月21日 下午3:48:34 
*  
*/
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class BEPS_351_001_01 extends MODEL_BASE {
	
	private static final long serialVersionUID = -8355675423783779001L;
	
    private static final String MESGTYPE = "beps.351.001.01";
    //TODO XMLNS  是不是用beps urn:cnaps:std:beps
    private static final String XMLNS = "urn:cnaps:std:beps:2010:tech:xsd:beps.351.001.01";
    private static final String XMLNS_XSI = "http://www.w3.org/2001/XMLSchema-instance";
    
    private BEPS_351_001_01_PtcSnReq PtcSnReq = new BEPS_351_001_01_PtcSnReq();

	public BEPS_351_001_01() {
        super(null, 0, 0, 0);
    }
	
    public BEPS_351_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = MESGTYPE;
        super.XMLNS = XMLNS;
        super.XMLNS_XSI = XMLNS_XSI;
    }
    
    @Override
    public String signData() {
        return PtcSnReq.signData();
    }
}
