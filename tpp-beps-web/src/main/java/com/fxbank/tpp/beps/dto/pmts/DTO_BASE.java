package com.fxbank.tpp.beps.dto.pmts;

import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.tpp.beps.pmts.PMTS_HEAD;
import com.fxbank.tpp.beps.pmts.PMTS_SIGN;
import com.fxbank.tpp.beps.pmts.SIGN_DATA;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;


/**
 * @author : 周勇沩
 * @description: 人行请求接口基类
 * @Date : 2020/2/20 22:53
 */
public abstract class DTO_BASE extends DataTransObject implements Serializable, SIGN_DATA {

    private static final long serialVersionUID = 2994344733147128067L;

    @XmlTransient
    private PMTS_HEAD head;

    @XmlTransient
    private PMTS_SIGN sign;

    @XmlTransient
    private String signChkRes;  //SUCCESS FAIL

    public PMTS_HEAD getHead() {
        return head;
    }

    public void setHead(PMTS_HEAD head) {
        this.head = head;
    }

    public PMTS_SIGN getSign() {
        return sign;
    }

    public void setSign(PMTS_SIGN sign) {
        this.sign = sign;
    }

    public String getSignChkRes() {
        return signChkRes;
    }

    public void setSignChkRes(String signChkRes) {
        this.signChkRes = signChkRes;
    }
}