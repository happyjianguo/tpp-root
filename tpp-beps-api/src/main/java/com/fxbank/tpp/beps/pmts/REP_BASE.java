package com.fxbank.tpp.beps.pmts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * @author : 周勇沩
 * @description: 应答报文基类
 * @Date : 2020/2/27 16:15
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class REP_BASE implements Serializable {

    private static final long serialVersionUID = -8361998671005382131L;

    @XmlTransient
    protected String mesgType; // 报文类型代码

    public String getMesgType() {
        return mesgType;
    }

    public void setMesgType(String mesgType) {
        this.mesgType = mesgType;
    }
}
