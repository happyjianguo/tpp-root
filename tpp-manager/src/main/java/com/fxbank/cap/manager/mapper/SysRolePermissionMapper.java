package com.fxbank.cap.manager.mapper;

import com.fxbank.cap.manager.entity.SysPermission;
import com.fxbank.cap.manager.entity.SysRolePermission;
import com.fxbank.cip.base.common.MyMapper;

import java.util.List;

public interface SysRolePermissionMapper extends MyMapper<SysRolePermission> {
    List<SysRolePermission> selectAll();
    List<SysPermission> selectPermissionList();
}