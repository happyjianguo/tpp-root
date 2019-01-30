package com.fxbank.cip.manager.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fxbank.cip.manager.entity.SysDepart;
import com.fxbank.cip.manager.entity.SysUser;
import com.fxbank.cip.manager.entity.SysUserDepart;
import com.fxbank.cip.manager.mapper.SysDepartMapper;
import com.fxbank.cip.manager.mapper.SysUserDepartMapper;

@Service
public class SysUserDepartService {

    @Resource
    private SysUserDepartMapper sysUserDepartMapper;
    @Resource
    private SysDepartMapper sysDepartMapper;

    public List<SysUserDepart> getUserByDepart(SysUserDepart data) {
        return sysUserDepartMapper.select(data);
    }

    public List<SysUser> getAvailableUser(){
        return sysUserDepartMapper.selectAvailableUser();
    }

    public void save(String ids, int departId) {
        SysUserDepart sysUserDepart = new SysUserDepart();
        sysUserDepart.setDepartId(departId);
        sysUserDepartMapper.delete(sysUserDepart);

        String id[] = ids.split(",");
        for(int i=0;i<id.length;i++){
            SysUserDepart d = new SysUserDepart();
            d.setDepartId(departId);
            d.setUserId(Integer.parseInt(id[i]));
            sysUserDepartMapper.insert(d);
        }


    }

    public SysDepart getUserDepart(Integer id) {
        SysUserDepart sysUserDepart = new SysUserDepart();
        sysUserDepart.setUserId(id);
        SysUserDepart sysUserDepart1 = sysUserDepartMapper.selectOne(sysUserDepart);
        return sysDepartMapper.selectByPrimaryKey(sysUserDepart1.getDepartId());
    }
}
