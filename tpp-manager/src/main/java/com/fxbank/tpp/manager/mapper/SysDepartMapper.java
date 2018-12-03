package com.fxbank.tpp.manager.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.manager.entity.SysDepart;

import java.util.List;

public interface SysDepartMapper extends MyMapper<SysDepart> {
    List<SysDepart> selectAll();
    String selectMaxId();

    List<SysDepart> selectAvailableDepart(SysDepart sysDepart);

    void updateChildPid(SysDepart data);
}
