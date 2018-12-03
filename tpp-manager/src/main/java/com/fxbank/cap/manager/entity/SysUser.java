package com.fxbank.cap.manager.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sys_user")
public class SysUser extends BaseData{
	/**
	 * 主键
	 */
	@Id
	@Column(name = "id")
	private Integer id;

	/**
	 * 登录名
	 */
	@Column(name = "username")
	private String username;

	/**
	 * 用户名/昵称
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 登录密码
	 */
	@Column(name = "password")
	private String password;

	/**
	 * 盐
	 */
	@Column(name = "salt")
	private String salt;

	/**
	 * 用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定
	 */
	@Column(name = "state")
	private String state;

	// 一个用户具有多个角色
	private List<SysRole> roleList;

	/**
	 * 主键
	 * 
	 * @return uid 主键
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 主键
	 * 
	 * @param id
	 *            主键
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 登录名
	 * 
	 * @return username 登录名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 登录名
	 * 
	 * @param username
	 *            登录名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 用户名/昵称
	 * 
	 * @return name 用户名/昵称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 用户名/昵称
	 * 
	 * @param name
	 *            用户名/昵称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 登录密码
	 * 
	 * @return password 登录密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 登录密码
	 * 
	 * @param password
	 *            登录密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 盐
	 * 
	 * @return salt 盐
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * 盐
	 * 
	 * @param salt
	 *            盐
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * 用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定
	 * 
	 * @return state 用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定
	 */
	public String getState() {
		return state;
	}

	/**
	 * 用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定
	 * 
	 * @param state
	 *            用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定
	 */
	public void setState(String state) {
		this.state = state;
	}

	public List<SysRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<SysRole> roleList) {
		this.roleList = roleList;
	}

}