package com.fxbank.cap.manager.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "sys_user_depart")
public class SysUserDepart {

    @Column(name="user_id")
    private Integer userId;
    @Column(name="depart_id")
    private Integer departId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDepartId() {
        return departId;
    }

    public void setDepartId(Integer departId) {
        this.departId = departId;
    }
}
