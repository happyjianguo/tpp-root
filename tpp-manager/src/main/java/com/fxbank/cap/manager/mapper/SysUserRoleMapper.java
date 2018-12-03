package com.fxbank.cap.manager.mapper;

import com.fxbank.cap.manager.entity.SysUserRole;
import com.fxbank.cip.base.common.MyMapper;

import java.util.List;

public interface SysUserRoleMapper extends MyMapper<SysUserRole> {
    List<SysUserRole> selectAll();
}