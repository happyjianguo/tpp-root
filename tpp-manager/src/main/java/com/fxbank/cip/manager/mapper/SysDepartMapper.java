package com.fxbank.cip.manager.mapper;

import java.util.List;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.cip.manager.entity.SysDepart;

public interface SysDepartMapper extends MyMapper<SysDepart> {
    List<SysDepart> selectAll();
    String selectMaxId();

    List<SysDepart> selectAvailableDepart(SysDepart sysDepart);

    void updateChildPid(SysDepart data);
}
