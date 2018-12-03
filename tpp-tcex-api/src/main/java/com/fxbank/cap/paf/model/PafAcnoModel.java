package com.fxbank.cap.paf.model;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

import java.io.Serializable;

public class PafAcnoModel extends ModelBase implements Serializable {

    private static final long serialVersionUID = -5919240620142361749L;

    public PafAcnoModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

    /**
     * 账号
     */
    private String acNo;

    /**
     * 公积金中心机构代码
     */
    private String pafcNo;

    /**
     * 客户编号
     */
    private String cliNo;

    /**
     * 账户名称
     */
    private String acName;

    /**
     * 开户机构号
     */
    private String brNo;

    /**
     * 开户机构名
     */
    private String brName;

    /**
     * 账户性质，1:活期 2:定期 3:户通
     */
    private String acType;

    /**
     * 账户类型，1:归集户 2:结算户 3:增值收益户 4:贷款本金专户 5:贷款利息专户 6:贷款罚息专户 7:贷款违约金专户
     */
    private String acKind;

    /**
     * 账户类别，1-对私 2-对公
     */
    private String acBitype;

    /**
     * 账户状态，0-注销 1-正常
     */
    private String acSts;

    /**
     * 账户种类，1-公积金账户 2-企业账户 3-个人账户
     */
    private String acVariety;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getPafcNo() {
        return pafcNo;
    }

    public void setPafcNo(String pafcNo) {
        this.pafcNo = pafcNo;
    }

    public String getCliNo() {
        return cliNo;
    }

    public void setCliNo(String cliNo) {
        this.cliNo = cliNo;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getBrNo() {
        return brNo;
    }

    public void setBrNo(String brNo) {
        this.brNo = brNo;
    }

    public String getBrName() {
        return brName;
    }

    public void setBrName(String brName) {
        this.brName = brName;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getAcKind() {
        return acKind;
    }

    public void setAcKind(String acKind) {
        this.acKind = acKind;
    }

    public String getAcBitype() {
        return acBitype;
    }

    public void setAcBitype(String acBitype) {
        this.acBitype = acBitype;
    }

    public String getAcSts() {
        return acSts;
    }

    public void setAcSts(String acSts) {
        this.acSts = acSts;
    }

    public String getAcVariety() {
        return acVariety;
    }

    public void setAcVariety(String acVariety) {
        this.acVariety = acVariety;
    }
}
