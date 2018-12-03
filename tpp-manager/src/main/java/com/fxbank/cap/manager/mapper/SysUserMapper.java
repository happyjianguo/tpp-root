package com.fxbank.cap.manager.mapper;

import com.fxbank.cap.manager.entity.SysUser;
import com.fxbank.cap.manager.entity.SysUserDepart;
import com.fxbank.cip.base.common.MyMapper;

import java.util.List;

public interface SysUserMapper extends MyMapper<SysUser> {
    List<SysUser> selectAll();
    Integer selectMaxId();
    List<SysUser> selectListPage(SysUser data);
    void deleteUserDepartByUserId(SysUserDepart data);
}