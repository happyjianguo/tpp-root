package com.fxbank.cap.paf.model;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

import java.io.Serializable;

public class SysTxUnitModel extends ModelBase implements Serializable {

    private static final long serialVersionUID = 5440001670013151382L;

    public SysTxUnitModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

    /**
     * 流水id
     */
    private Integer id;

    /**
     * 公积金中心代码
     */
    private String txUnitNo;

    /**
     * 公积金中心名称
     */
    private String txUnitName;

    /**
     * 上级机构代码
     */
    private String parentUnitNo;

    /**
     * 中心节点号
     */
    private String centerNode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxUnitNo() {
        return txUnitNo;
    }

    public void setTxUnitNo(String txUnitNo) {
        this.txUnitNo = txUnitNo;
    }

    public String getTxUnitName() {
        return txUnitName;
    }

    public void setTxUnitName(String txUnitName) {
        this.txUnitName = txUnitName;
    }

    public String getParentUnitNo() {
        return parentUnitNo;
    }

    public void setParentUnitNo(String parentUnitNo) {
        this.parentUnitNo = parentUnitNo;
    }

    public String getCenterNode() {
        return centerNode;
    }

    public void setCenterNode(String centerNode) {
        this.centerNode = centerNode;
    }
}

