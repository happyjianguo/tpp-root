package com.fxbank.tpp.manager.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.manager.entity.SysUser;
import com.fxbank.tpp.manager.entity.SysUserDepart;

import java.util.List;

public interface SysUserDepartMapper extends MyMapper<SysUserDepart> {
    List<SysUserDepart> selectAll();
    List<SysUser> selectAvailableUser();
}
