package com.fxbank.tpp.beps.pmts;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.fxbank.cip.base.anno.EsbSimuAnno;

/**
 * @author : 周勇沩
 * @description: 批量包组头组件报文
 * @Date : 2020/2/27 15:29
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"MsgId", "CreDtTm", "InstgPty", "InstdPty", "NbOfTxs", "CtrlSum", "SysCd", "Rmk"})
public class PKGGrpHdr implements Serializable {

    private static final long serialVersionUID = -5708344033132677204L;
    @EsbSimuAnno.EsbField(type = "Date", value = "yyyyMMddhhmmss")
    private String MsgId;
    @EsbSimuAnno.EsbField(type = "Date", value = "yyyy-MM-dd HH:mm:ss")
    private String CreDtTm;
    @EsbSimuAnno.EsbField(type = "String", value = "313131000016")
    private InstgPty InstgPty;
    @EsbSimuAnno.EsbField(type = "String", value = "313131000016")
    private InstdPty InstdPty;
    @EsbSimuAnno.EsbField(type = "String", value = "1")
    private String NbOfTxs;
    @EsbSimuAnno.EsbField(type = "String", value = "1.00")
    private String CtrlSum;
    @EsbSimuAnno.EsbField(type = "String", value = "SAPS")
    private String SysCd;
    @EsbSimuAnno.EsbField(type = "String", value = "备注")
    private String Rmk;

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    public String getCreDtTm() {
        return CreDtTm;
    }

    public void setCreDtTm(String creDtTm) {
        CreDtTm = creDtTm;
    }

    public InstgPty getInstgPty() {
        return InstgPty;
    }

    public void setInstgPty(InstgPty instgPty) {
        InstgPty = instgPty;
    }

    public InstdPty getInstdPty() {
        return InstdPty;
    }

    public void setInstdPty(InstdPty instdPty) {
        InstdPty = instdPty;
    }

    public String getNbOfTxs() {
        return NbOfTxs;
    }

    public void setNbOfTxs(String nbOfTxs) {
        NbOfTxs = nbOfTxs;
    }

    public String getCtrlSum() {
        return CtrlSum;
    }

    public void setCtrlSum(String ctrlSum) {
        CtrlSum = ctrlSum;
    }

    public String getSysCd() {
        return SysCd;
    }

    public void setSysCd(String sysCd) {
        SysCd = sysCd;
    }

    public String getRmk() {
        return Rmk;
    }

    public void setRmk(String rmk) {
        Rmk = rmk;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "PKGGrpHdr.InstgPty")
    public static class InstgPty implements Serializable {

        private static final long serialVersionUID = -6076661591627295135L;

        private String InstgDrctPty;

        public String getInstgDrctPty() {
            return InstgDrctPty;
        }

        public void setInstgDrctPty(String instgDrctPty) {
            InstgDrctPty = instgDrctPty;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "PKGGrpHdr.InstdPty")
    public static class InstdPty implements Serializable {

        private static final long serialVersionUID = 5813270587375350736L;

        private String InstdDrctPty;

        public String getInstdDrctPty() {
            return InstdDrctPty;
        }

        public void setInstdDrctPty(String instdDrctPty) {
            InstdDrctPty = instdDrctPty;
        }


    }

}
