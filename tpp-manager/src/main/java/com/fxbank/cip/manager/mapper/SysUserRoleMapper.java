package com.fxbank.cip.manager.mapper;

import java.util.List;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.cip.manager.entity.SysUserRole;

public interface SysUserRoleMapper extends MyMapper<SysUserRole> {
    List<SysUserRole> selectAll();
}