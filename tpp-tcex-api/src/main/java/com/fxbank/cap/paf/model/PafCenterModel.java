package com.fxbank.cap.paf.model;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

import java.io.Serializable;

public class PafCenterModel extends ModelBase implements Serializable {

    private static final long serialVersionUID = 5944581822022127699L;

    public PafCenterModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

    /**
     * 公积金中心机构代码
     */
    private String pafcNo;

    /**
     * 公积金中心名称
     */
    private String pafcName;

    /**
     * 部门编码
     */
    private String departCode;

    /**
     * 行内机构代码
     */
    private String txBrno;

    /**
     * 行内上级机构代码
     */
    private String upBrno;

    /**
     * 行内柜员编号
     */
    private String txTel;

    /**
     * 批量交易过度账号
     */
    private String inAcno;

    public String getPafcNo() {
        return pafcNo;
    }

    public void setPafcNo(String pafcNo) {
        this.pafcNo = pafcNo;
    }

    public String getPafcName() {
        return pafcName;
    }

    public void setPafcName(String pafcName) {
        this.pafcName = pafcName;
    }

    public String getDepartCode() {
        return departCode;
    }

    public void setDepartCode(String departCode) {
        this.departCode = departCode;
    }

    public String getTxBrno() {
        return txBrno;
    }

    public void setTxBrno(String txBrno) {
        this.txBrno = txBrno;
    }

    public String getUpBrno() {
        return upBrno;
    }

    public void setUpBrno(String upBrno) {
        this.upBrno = upBrno;
    }

    public String getTxTel() {
        return txTel;
    }

    public void setTxTel(String txTel) {
        this.txTel = txTel;
    }

    public String getInAcno() {
        return inAcno;
    }

    public void setInAcno(String inAcno) {
        this.inAcno = inAcno;
    }
}
