package com.fxbank.cap.manager.mapper;

import com.fxbank.cap.manager.entity.SysUser;
import com.fxbank.cap.manager.entity.SysUserDepart;
import com.fxbank.cip.base.common.MyMapper;

import java.util.List;

public interface SysUserDepartMapper extends MyMapper<SysUserDepart> {
    List<SysUserDepart> selectAll();
    List<SysUser> selectAvailableUser();
}
