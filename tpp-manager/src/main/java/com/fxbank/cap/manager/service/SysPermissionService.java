package com.fxbank.cap.manager.service;

import com.fxbank.cap.manager.entity.SysPermission;
import com.fxbank.cap.manager.entity.SysUser;
import com.fxbank.cap.manager.mapper.SysPermissionMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysPermissionService {
    @Resource
    private SysPermissionMapper permissionMapper;

    /**
     * 获取当前用户对应的菜单
     * @param parentId 父菜单id
     * @return
     */
    public List<SysPermission> getMenu(int parentId){
        SysUser data = (SysUser) SecurityUtils.getSubject().getPrincipal();
        String userName=data.getUsername();
        List <SysPermission> sysPermissions = permissionMapper.selectMenuByUser(userName,parentId);
        return sysPermissions;
    }

    /**
     * 获取所有菜单
     * @return
     */
    public List<SysPermission> getAllMenu(){
        List <SysPermission> sysPermissions = permissionMapper.selectAll();
        return sysPermissions;
    }

    /**
     * 获取除自己外，其他一级菜单
     * @param sysPermission
     * @return
     */
    public List<SysPermission> getTopMenu(SysPermission sysPermission){
        List <SysPermission> sysPermissions = permissionMapper.selectTopMenu(sysPermission);
        return sysPermissions;
    }

    /**
     * 新增菜单
     * @param sysPermission
     */
    public void save(SysPermission sysPermission){
        SysPermission parentSys = permissionMapper.selectByPrimaryKey(sysPermission.getParentId());
        String id=permissionMapper.selectMaxId();
        if(id==null)id="0";
        sysPermission.setId(Integer.parseInt(id)+1);
        sysPermission.setParentIds((sysPermission.getParentId()==0?"0":parentSys.getParentIds()+"/"+sysPermission.getParentId()));

        permissionMapper.insert(sysPermission);
    }

    /**
     * 根据id获取菜单信息
     * @param sysP
     * @return
     */
    public SysPermission getMenuById(SysPermission sysP){
        SysPermission sysPermission = permissionMapper.selectByPrimaryKey(sysP);
        return sysPermission;
    }

    /**
     * 修改菜单信息
     * @param sysPermission
     */
    public void update(SysPermission sysPermission)
    {
        SysPermission parentSys = permissionMapper.selectByPrimaryKey(sysPermission.getParentId());
        sysPermission.setParentIds((sysPermission.getParentId()==0?"0":parentSys.getParentIds()+"/"+sysPermission.getParentId()));
        permissionMapper.updateByPrimaryKeySelective(sysPermission);
    }

    /**
     * 修改子菜单的父节点为根节点，删除时使用
     * @param sysPermission
     */
    public void updateChildPid(SysPermission sysPermission){
        permissionMapper.updateChildPid(sysPermission.getId());
    }

    /**
     * 删除菜单
     * @param sysPermission
     */
    public void del(SysPermission sysPermission){
        permissionMapper.delete(sysPermission);
    }
}
