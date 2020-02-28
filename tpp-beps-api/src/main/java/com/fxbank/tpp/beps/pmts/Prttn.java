package com.fxbank.tpp.beps.pmts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.fxbank.cip.base.anno.EsbSimuAnno;

import java.io.Serializable;

/**
 * @author : 周勇沩
 * @description: 报文分片组件报文
 * @Date : 2020/2/27 15:29
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"TtlNb", "StartNb", "EndNb"})
public class Prttn implements Serializable {

    private static final long serialVersionUID = 2794349176112940263L;
    @EsbSimuAnno.EsbField(type = "value", value = "2")
    private String TtlNb;
    @EsbSimuAnno.EsbField(type = "value", value = "0")
    private String StartNb;
    @EsbSimuAnno.EsbField(type = "value", value = "1")
    private String EndNb;

    public String getTtlNb() {
        return TtlNb;
    }

    public void setTtlNb(String ttlNb) {
        TtlNb = ttlNb;
    }

    public String getStartNb() {
        return StartNb;
    }

    public void setStartNb(String startNb) {
        StartNb = startNb;
    }

    public String getEndNb() {
        return EndNb;
    }

    public void setEndNb(String endNb) {
        EndNb = endNb;
    }


}
