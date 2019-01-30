package com.fxbank.tpp.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.manager.entity.SysPermission;

public interface SysPermissionMapper extends MyMapper<SysPermission> {
    List<SysPermission> selectAll();
    List<SysPermission> selectTopMenu(SysPermission sysPermission);
    List<SysPermission> selectMenuByUser(@Param("userName") String userName,@Param("parentId") int parentId);
    String selectMaxId();
    void updateChildPid(@Param("parentId")int parentId);
}