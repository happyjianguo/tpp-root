package com.fxbank.cap.manager.mapper;

import com.fxbank.cap.manager.entity.SysRole;
import com.fxbank.cip.base.common.MyMapper;

import java.util.List;

public interface SysRoleMapper extends MyMapper<SysRole> {
    List<SysRole> selectListPage(SysRole data);

    int selectMaxId();
}