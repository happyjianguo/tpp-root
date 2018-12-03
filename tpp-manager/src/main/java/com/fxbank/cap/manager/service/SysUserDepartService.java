package com.fxbank.cap.manager.service;

import com.fxbank.cap.manager.entity.SysDepart;
import com.fxbank.cap.manager.entity.SysUser;
import com.fxbank.cap.manager.entity.SysUserDepart;
import com.fxbank.cap.manager.mapper.SysDepartMapper;
import com.fxbank.cap.manager.mapper.SysUserDepartMapper;
import com.fxbank.cap.manager.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
