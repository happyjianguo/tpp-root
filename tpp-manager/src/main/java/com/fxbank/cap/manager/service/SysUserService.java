package com.fxbank.cap.manager.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.fxbank.cap.manager.entity.*;
import com.fxbank.cap.manager.mapper.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import org.xmlunit.util.Mapper;

@Service
public class SysUserService {
	
	@Resource
	private SysUserMapper userMapper;

	@Resource
	private SysRoleMapper roleMapper;
	
	@Resource
	private SysPermissionMapper permissionMapper;
	
	@Resource
	private SysUserRoleMapper userRoleMapper;
	
	@Resource
	private SysRolePermissionMapper rolePermissionMapper;

	@Resource
	private SysDepartMapper sysDepartMapper;

	@Resource
	private SysUserDepartMapper sysUserDepartMapper;

	public SysUser findByUsername(String username){
		SysUser user = new SysUser();
		user.setUsername(username);
		user = userMapper.selectOne(user);
		if(user==null){
			return null;
		}
		SysUserRole userRole = new SysUserRole();
		userRole.setId(user.getId());
		List<SysUserRole> userRoleList = userRoleMapper.select(userRole);
		List<SysRole> sysRoleList = new ArrayList<SysRole>();
		user.setRoleList(sysRoleList);
		for(SysUserRole e:userRoleList){
			SysRole sysRole = roleMapper.selectByPrimaryKey(e.getRoleId());
			sysRoleList.add(sysRole);
			SysRolePermission sysRolePermission = new SysRolePermission();
			sysRolePermission.setRoleId(sysRole.getId());
			List<SysRolePermission> sysRolePermissionList = rolePermissionMapper.select(sysRolePermission);
			List<SysPermission> sysPermissionList = new ArrayList<SysPermission>();
			sysRole.setPermissions(sysPermissionList);
			for(SysRolePermission e1:sysRolePermissionList){
				SysPermission permission = permissionMapper.selectByPrimaryKey(e1.getPermissionId());
				sysPermissionList.add(permission);
			}
		}
		
		return user;
	}
	public int addUser(SysUser user) throws Exception{
		
		int result = userMapper.insertSelective(user);

		return result;
	}

	public Integer getMaxId(){
		int count=1;
		if(userMapper.selectMaxId()!=null){
			count=userMapper.selectMaxId()+1;
		}
		return count;
	}

	public SysUser getList(SysUser data){
		Page<SysRole> page= PageHelper.startPage(data.getPage(),data.getLimit());
		List<SysUser> list= userMapper.selectListPage(data);
		PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(list);
		data.setPageList(list);
		data.setPageCountRows((int)pageInfo.getTotal());//总条数
		return data;
	}


	public boolean queryByUserName(SysUser data){
		boolean bool=false;
		int count=userMapper.selectCount(data);
		if(count==0)
			bool=true;
		return bool;
	}

	public SysUser getUser(SysUser data){
		data=userMapper.selectByPrimaryKey(data);
		return data;
	}

	public void update(SysUser data) throws Exception{
		userMapper.updateByPrimaryKey(data);
	}


	public void del(SysUser data) throws Exception{
		userMapper.delete(data);
		SysUserRole sur=new SysUserRole();
		sur.setId(data.getId());
		userRoleMapper.delete(sur);
		SysUserDepart sysUserDepart = new SysUserDepart();
		sysUserDepart.setUserId(data.getId());
		sysUserDepartMapper.delete(sysUserDepart);
	}

	public List<SysRole> getRoleList(){
		List<SysRole> list=roleMapper.selectAll();
		return list;
	}

	public List<SysUserRole> getUserRoleList(SysUserRole data){
		List<SysUserRole> list=userRoleMapper.select(data);
		return list;
	}


	public void saveRole(String roleIds,int id) throws Exception{
		String []arr=roleIds.split(",");
		SysUserRole data=new SysUserRole();
		data.setId(id);
		userRoleMapper.delete(data);
		for(int i=0;i<arr.length;i++){
			SysUserRole sur=new SysUserRole();
			sur.setId(id);
			sur.setRoleId(Integer.parseInt(arr[i]));
			userRoleMapper.insert(sur);
		}
	}

	public List<SysDepart> getDepart(){
		List<SysDepart> list=sysDepartMapper.selectAll();
		return list;
	}

	public List<SysUserDepart> getUserDepart(SysUserDepart data){
		List<SysUserDepart> list=sysUserDepartMapper.select(data);
		return list;
	}

	public void updateUserDepart(SysUserDepart data) throws Exception{
		userMapper.deleteUserDepartByUserId(data);
		sysUserDepartMapper.insert(data);
	}

}
