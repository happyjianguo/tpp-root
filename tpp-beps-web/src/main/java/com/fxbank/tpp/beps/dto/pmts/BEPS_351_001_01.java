package com.fxbank.tpp.beps.dto.pmts;

import com.fxbank.tpp.beps.pmts.BEPS_351_001_01_PtcSnReq;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @description: 客户支付协议管理报文
 * @author     : 周勇沩
 * @Date       : 2020/2/24 12:17
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class BEPS_351_001_01 extends DTO_BASE {

    private static final long serialVersionUID = 3958550152175762461L;

    public BEPS_351_001_01() {
        super.txDesc = "客户支付协议管理报文";
    }

    private BEPS_351_001_01_PtcSnReq ptcSnReq = new BEPS_351_001_01_PtcSnReq();

    public BEPS_351_001_01_PtcSnReq getPtcSnReq() {
        return ptcSnReq;
    }

    public void setPtcSnReq(BEPS_351_001_01_PtcSnReq ptcSnReq) {
        this.ptcSnReq = ptcSnReq;
    }

    @Override
    public String signData() {
        return ptcSnReq.signData();
    }
}