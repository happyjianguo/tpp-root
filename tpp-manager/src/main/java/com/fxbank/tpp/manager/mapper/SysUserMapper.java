package com.fxbank.tpp.manager.mapper;

import java.util.List;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.manager.entity.SysUser;
import com.fxbank.tpp.manager.entity.SysUserDepart;

public interface SysUserMapper extends MyMapper<SysUser> {
    List<SysUser> selectAll();
    Integer selectMaxId();
    List<SysUser> selectListPage(SysUser data);
    void deleteUserDepartByUserId(SysUserDepart data);
}