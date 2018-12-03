package com.fxbank.tpp.manager.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.manager.entity.SysPermission;
import com.fxbank.tpp.manager.entity.SysRolePermission;

import java.util.List;

public interface SysRolePermissionMapper extends MyMapper<SysRolePermission> {
    List<SysRolePermission> selectAll();
    List<SysPermission> selectPermissionList();
}