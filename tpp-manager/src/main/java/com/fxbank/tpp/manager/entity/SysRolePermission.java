package com.fxbank.tpp.manager.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="sys_role_permission")
public class SysRolePermission {
    /**
     * 权限编号
     */
    @Column(name = "permission_id")
    private Integer permissionId;

    /**
     * 角色编号
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 权限编号
     * @return permission_id 权限编号
     */
    public Integer getPermissionId() {
        return permissionId;
    }

    /**
     * 权限编号
     * @param permissionId 权限编号
     */
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

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
}