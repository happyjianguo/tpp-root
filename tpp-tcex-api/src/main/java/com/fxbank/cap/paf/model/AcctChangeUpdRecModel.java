package com.fxbank.cap.paf.model;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AcctChangeUpdRecModel extends ModelBase implements Serializable {

    private static final long serialVersionUID = -6931814320255664111L;

    public AcctChangeUpdRecModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

    /**
     * 主键
     */
    @NotNull(message = "主键id不能为空")
    private Integer id;

    /**
     * 核心流水号
     */
    @NotNull(message = "核心流水号不能为空")
    private String reference;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
