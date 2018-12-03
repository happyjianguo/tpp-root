package com.fxbank.tpp.manager.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sys_role")
public class SysRole extends BaseData{
	/**
	 * 角色编号
	 */
	@Id
	@Column(name = "id")
	private Integer id;

	/**
	 * 角色标识程序中判断使用,如"admin",这个是唯一的
	 */
	@Column(name = "role")
	private String role;

	/**
	 * 角色描述,UI界面显示使用
	 */
	@Column(name = "description")
	private String description;

	/**
	 * 是否可用,如果不可用将不会添加给用户,0:可用，1：不可用
	 */
	@Column(name = "available")
	private Byte available;

	// 角色 -- 权限关系：多对多关系;
	private List<SysPermission> permissions;

	// 用户 - 角色关系定义;
	private List<SysUser> userInfos;// 一个角色对应多个用户

	/**
	 * 角色编号
	 * 
	 * @return id 角色编号
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 角色编号
	 * 
	 * @param id
	 *            角色编号
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 角色标识程序中判断使用,如"admin",这个是唯一的
	 * 
	 * @return role 角色标识程序中判断使用,如"admin",这个是唯一的
	 */
	public String getRole() {
		return role;
	}

	/**
	 * 角色标识程序中判断使用,如"admin",这个是唯一的
	 * 
	 * @param role
	 *            角色标识程序中判断使用,如"admin",这个是唯一的
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * 角色描述,UI界面显示使用
	 * 
	 * @return description 角色描述,UI界面显示使用
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 角色描述,UI界面显示使用
	 * 
	 * @param description
	 *            角色描述,UI界面显示使用
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 是否可用,如果不可用将不会添加给用户,0:可用，1：不可用
	 * 
	 * @return available 是否可用,如果不可用将不会添加给用户,0:可用，1：不可用
	 */
	public Byte getAvailable() {
		return available;
	}

	/**
	 * 是否可用,如果不可用将不会添加给用户,0:可用，1：不可用
	 * 
	 * @param available
	 *            是否可用,如果不可用将不会添加给用户,0:可用，1：不可用
	 */
	public void setAvailable(Byte available) {
		this.available = available;
	}

	public List<SysPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<SysPermission> permissions) {
		this.permissions = permissions;
	}

	public List<SysUser> getUserInfos() {
		return userInfos;
	}

	public void setUserInfos(List<SysUser> userInfos) {
		this.userInfos = userInfos;
	}
	
}