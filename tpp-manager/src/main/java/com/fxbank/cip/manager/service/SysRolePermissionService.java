package com.fxbank.cip.manager.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fxbank.cip.manager.entity.SysPermission;
import com.fxbank.cip.manager.entity.SysRolePermission;
import com.fxbank.cip.manager.mapper.SysRolePermissionMapper;

@Service
public class SysRolePermissionService {

    @Resource
    private SysRolePermissionMapper mapper;


    public List<SysPermission> getPermissionList(){
        return mapper.selectPermissionList();
    }

    public List<SysRolePermission> getRolePermissionList(SysRolePermission data){
        List<SysRolePermission> list=mapper.select(data);
        return list;
    }

    public void save(String ids,int roleId) throws Exception{
        String []arr=ids.split(",");
        //先删除当前角色下所有权限数据
        SysRolePermission data=new SysRolePermission();
        data.setRoleId(roleId);
        mapper.delete(data);
        //将勾选的权限重新添加到角色下
        for(int i=0;i<arr.length;i++){
            SysRolePermission d=new SysRolePermission();
            d.setPermissionId(Integer.parseInt(arr[i]));
            d.setRoleId(roleId);
            mapper.insert(d);
        }
    }

}
