package com.fxbank.tpp.manager.config;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.fxbank.tpp.manager.entity.SysPermission;
import com.fxbank.tpp.manager.entity.SysRole;
import com.fxbank.tpp.manager.entity.SysUser;
import com.fxbank.tpp.manager.service.SysUserService;

import java.util.HashSet;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {

	@Resource
	private SysUserService sysUserService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
		// 获取用户的输入的账号.
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		// 通过username从数据库中查找 User对象，如果找到，没找到.
		// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
		SysUser sysUser = sysUserService.findByUsername(username);
		if (sysUser == null) {
			return null;
		}
		ByteSource salt = ByteSource.Util.bytes(sysUser.getSalt());
		System.out.println("加密密码的盐：" + salt);
		// 得到盐值加密后的密码：只用于方便数据库测试，后期不会用到。
		Object md = new SimpleHash("MD5", upToken.getPassword(), salt, 1024);
		System.out.println("盐值加密后的密码：" + md);
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(sysUser, // 用户名
				sysUser.getPassword(), // 密码
				salt, // salt=username+salt
				getName() // realm name
		);
		return authenticationInfo;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		String userName = (String)principals.getPrimaryPrincipal();
		System.out.println("权限配置-->userName:  "+userName);
		SysUser userInfo = sysUserService.findByUsername(userName);
		for (SysRole role : userInfo.getRoleList()) {
			authorizationInfo.addRole(role.getRole());
			for (SysPermission p : role.getPermissions()) {
				authorizationInfo.addStringPermission(p.getPermission());
			}
		}



		return authorizationInfo;
	}
}
