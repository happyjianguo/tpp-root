package com.fxbank.cap.manager.mapper;

import com.fxbank.cap.manager.entity.SysPermission;
import com.fxbank.cip.base.common.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysPermissionMapper extends MyMapper<SysPermission> {
    List<SysPermission> selectAll();
    List<SysPermission> selectTopMenu(SysPermission sysPermission);
    List<SysPermission> selectMenuByUser(@Param("userName") String userName,@Param("parentId") int parentId);
    String selectMaxId();
    void updateChildPid(@Param("parentId")int parentId);
}