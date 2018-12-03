package com.fxbank.cap.manager.mapper;

import com.fxbank.cap.manager.entity.SysDepart;
import com.fxbank.cip.base.common.MyMapper;

import java.util.List;

public interface SysDepartMapper extends MyMapper<SysDepart> {
    List<SysDepart> selectAll();
    String selectMaxId();

    List<SysDepart> selectAvailableDepart(SysDepart sysDepart);

    void updateChildPid(SysDepart data);
}
