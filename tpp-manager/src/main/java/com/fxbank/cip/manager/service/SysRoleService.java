package com.fxbank.cip.manager.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fxbank.cip.manager.entity.SysRole;
import com.fxbank.cip.manager.entity.SysRolePermission;
import com.fxbank.cip.manager.mapper.SysRoleMapper;
import com.fxbank.cip.manager.mapper.SysRolePermissionMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class SysRoleService {

    @Resource
    private SysRoleMapper mapper;

    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;


    public SysRole getList(SysRole data){
        //分页查询，传入页码，每页行数
        Page<SysRole> page= PageHelper.startPage(data.getPage(),data.getLimit());
        List<SysRole> list=mapper.selectListPage(data);
        //分页信息
        PageInfo<SysRole> pageInfo = new PageInfo<SysRole>(list);
        data.setPageList(list);
        data.setPageCountRows((int)pageInfo.getTotal());//总条数
        return data;
    }

    /**
     * 保存角色
     * @param data
     * @throws Exception
     */
    public void save(SysRole data) throws Exception{
        int id=mapper.selectMaxId();
        data.setId(id);
        mapper.insert(data);
    }

    /**
     * 删除角色
     * @param data
     * @throws Exception
     */
    public void del(SysRole data) throws Exception{
        mapper.deleteByPrimaryKey(data);
        SysRolePermission d=new SysRolePermission();
        d.setRoleId(data.getId());
        sysRolePermissionMapper.delete(d);
    }

    /**
     * 根据id查询角色
     * @param data
     * @return
     */
    public SysRole getSysRole(SysRole data){
        data=mapper.selectByPrimaryKey(data);
        return data;
    }

    public void update(SysRole data) throws Exception{
        mapper.updateByPrimaryKey(data);
    }
}
