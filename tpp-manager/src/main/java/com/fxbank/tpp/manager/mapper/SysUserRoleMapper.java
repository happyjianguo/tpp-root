package com.fxbank.tpp.manager.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.manager.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleMapper extends MyMapper<SysUserRole> {
    List<SysUserRole> selectAll();
}