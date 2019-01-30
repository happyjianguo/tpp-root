package com.fxbank.cip.manager.mapper;

import java.util.List;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.cip.manager.entity.SysRole;

public interface SysRoleMapper extends MyMapper<SysRole> {
    List<SysRole> selectListPage(SysRole data);

    int selectMaxId();
}