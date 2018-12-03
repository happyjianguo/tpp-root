package com.fxbank.tpp.manager.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="sys_user_role")
public class SysUserRole {
    /**
     * 角色编号
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 用户信息编号
     */
    @Column(name = "id")
    private Integer id;

    /**
     * 角色编号
     * @return role_id 角色编号
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 角色编号
     * @param roleId 角色编号
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 用户信息编号
     * @return uid 用户信息编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 用户信息编号
     * @param id 用户信息编号
     */
    public void setId(Integer id) {
        this.id = id;
    }
}