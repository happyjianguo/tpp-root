package com.fxbank.tpp.manager.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.manager.entity.SysUser;
import com.fxbank.tpp.manager.entity.SysUserDepart;

import java.util.List;

public interface SysUserMapper extends MyMapper<SysUser> {
    List<SysUser> selectAll();
    Integer selectMaxId();
    List<SysUser> selectListPage(SysUser data);
    void deleteUserDepartByUserId(SysUserDepart data);
}