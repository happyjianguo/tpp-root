package com.fxbank.cip.manager.mapper;

import java.util.List;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.cip.manager.entity.SysUser;
import com.fxbank.cip.manager.entity.SysUserDepart;

public interface SysUserDepartMapper extends MyMapper<SysUserDepart> {
    List<SysUserDepart> selectAll();
    List<SysUser> selectAvailableUser();
}
