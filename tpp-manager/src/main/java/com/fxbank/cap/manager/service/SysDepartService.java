package com.fxbank.cap.manager.service;

import com.fxbank.cap.manager.entity.SysDepart;
import com.fxbank.cap.manager.entity.SysPermission;
import com.fxbank.cap.manager.entity.SysUserDepart;
import com.fxbank.cap.manager.mapper.SysDepartMapper;
import com.fxbank.cap.manager.mapper.SysUserDepartMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysDepartService {

    @Resource
    private SysDepartMapper sysDepartMapper;

    @Resource
    private SysUserDepartMapper sysUserDepartMapper;

    public List<SysDepart> getAllDepart(){
        List<SysDepart> sysDepartList = sysDepartMapper.selectAll();
        return sysDepartList;
    }

    public List<SysDepart> getAvailableDepart(SysDepart sysDepart) {
        List<SysDepart> sysDepartList = sysDepartMapper.selectAvailableDepart(sysDepart);
        return sysDepartList;
    }

    public void save(SysDepart data) {
        SysDepart parentSys = sysDepartMapper.selectByPrimaryKey(data.getParentId());
        String id=sysDepartMapper.selectMaxId();
        if(id==null) id="0";
        data.setId(Integer.parseInt(id)+1);
        data.setParentIds((data.getParentId()==0?"/0":parentSys.getParentIds())+data.getId()+"/");

        sysDepartMapper.insert(data);
    }

    public SysDepart getDepartById(SysDepart sysDepart) {
        SysDepart sysDepart1 = sysDepartMapper.selectByPrimaryKey(sysDepart);
        return  sysDepart1;
    }

    public void update(SysDepart data) {
        SysDepart parentSys = sysDepartMapper.selectByPrimaryKey(data.getParentId());
        data.setParentIds((data.getParentId()==0?"/0":parentSys.getParentIds())+data.getId()+"/");
        sysDepartMapper.updateByPrimaryKeySelective(data);
    }

    public void updateChildPid(SysDepart data) {
        SysDepart parentSys = sysDepartMapper.selectByPrimaryKey(data);
        data.setParentId(parentSys.getParentId());
        data.setParentIds(parentSys.getParentIds());
        sysDepartMapper.updateChildPid(data);
    }

    public void del(SysDepart data) {
        SysUserDepart sysUserDepart = new SysUserDepart();
        sysUserDepart.setDepartId(data.getId());
        sysUserDepartMapper.delete(sysUserDepart);
        sysDepartMapper.delete(data);
    }
}
