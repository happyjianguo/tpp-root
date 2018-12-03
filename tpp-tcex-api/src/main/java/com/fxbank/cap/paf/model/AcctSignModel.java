package com.fxbank.cap.paf.model;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AcctSignModel extends ModelBase implements Serializable {

	private static final long serialVersionUID = 6512464761856916503L;

	public AcctSignModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

    /**
     * 流水id
     */
    @NotNull(message = "id不能为空")
    private Integer id;

    /**
     * 账号
     */
    @NotNull(message = "账号不能为空")
    private String acctNo;

    /**
     * 账号上报状态，0：未上报，1：已上报
     */
    private String status;

    /**
     * 账户性质,1:活期,2:定期,3:户通
     */
    private String acctNature;

    /**
     * 账户类型,1:归集户 2:结算户 3:增值收益户 4:贷款本金专户 5:贷款利息专户 6:贷款罚息专户 7:贷款违约金专户
     */
    private String acctType;

    /**
     * 账户类别,2-对公 1-对私
     */
    private String acctSort;


    /**
     * 公积金中心代码表id
     */
    private Integer tid;

    /**
     * 核心流水号
     */
    private String reference;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAcctNature() {
        return acctNature;
    }

    public void setAcctNature(String acctNature) {
        this.acctNature = acctNature;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getAcctSort() {
        return acctSort;
    }

    public void setAcctSort(String acctSort) {
        this.acctSort = acctSort;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }
}
