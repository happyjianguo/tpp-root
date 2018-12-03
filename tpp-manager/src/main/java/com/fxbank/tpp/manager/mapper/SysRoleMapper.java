package com.fxbank.tpp.manager.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.manager.entity.SysRole;

import java.util.List;

public interface SysRoleMapper extends MyMapper<SysRole> {
    List<SysRole> selectListPage(SysRole data);

    int selectMaxId();
}