package com.fxbank.cap.paf.model;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

import java.io.Serializable;

public class AcctSingUpdStsModel extends ModelBase implements Serializable {
    private static final long serialVersionUID = -8723369977224783462L;

    public AcctSingUpdStsModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

    /**
     * 账号上报状态，0：未上报，1：已上报
     */
    private String status;

    /**
     * 公积金中心代码表id
     */
    private Integer tid;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }
}
