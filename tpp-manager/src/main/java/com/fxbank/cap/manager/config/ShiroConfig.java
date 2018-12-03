package com.fxbank.cap.manager.config;

import java.util.LinkedHashMap;
import java.util.Map;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

	private static Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

	@Bean
	public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * cookie对象;
	 * 
	 * @return
	 */
	@Bean(name = "rememberMeCookie")
	public SimpleCookie rememberMeCookie() {
		// 这个参数是cookie的名称，对应前端的checkbox 的name = rememberMe
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		// <!-- 记住我cookie生效时间30天（259200） ,单位秒;-->
		simpleCookie.setMaxAge(259200);
		return simpleCookie;
	}

	/**
	 * 记住我管理器 cookie管理对象;
	 * 
	 * @return
	 */
	@Bean(name = "cookieRememberMeManager")
	public CookieRememberMeManager rememberMeManager(@Qualifier("rememberMeCookie") SimpleCookie rememberMeCookie) {
		System.out.println("rememberMeManager()");
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie);
		return cookieRememberMeManager;
	}

	/**
	 * 密码匹配凭证管理器
	 * 
	 * @return
	 */
	@Bean(name = "hashedCredentialsMatcher")
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("MD5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(1024);// 散列的次数，比如散列两次，相当于 md5(md5(""));
		return hashedCredentialsMatcher;
	}

//	/**
//	 * 配置shiro redisManager
//	 * 使用的是shiro-redis开源插件
//	 * @return
//	 */
//	public RedisManager redisManager() {
//
//		System.out.println("创建shiro redisManager,连接Redis..URL= 127.0.0.1:6379");
//		RedisManager redisManager = new RedisManager();
//		redisManager.setHost("127.0.0.1");
//		redisManager.setPort(6379);
//		redisManager.setExpire(1800);// 配置缓存过期时间
//		redisManager.setTimeout(0);
//		redisManager.setPassword("foobared");
//		return redisManager;
//	}

//	/**
//	 * cacheManager 缓存 redis实现
//	 * 使用的是shiro-redis开源插件
//	 * @return
//	 */
//	public RedisCacheManager cacheManager() {
//		System.out.println("创建RedisCacheManager...");
//		RedisCacheManager redisCacheManager = new RedisCacheManager();
//		redisCacheManager.setRedisManager(redisManager());
//		return redisCacheManager;
//	}

//	/**
//	 * RedisSessionDAO shiro sessionDao层的实现 通过redis
//	 * 使用的是shiro-redis开源插件
//	 */
//	@Bean
//	public RedisSessionDAO redisSessionDAO() {
//		System.out.println("创建RedisSessionDAO...");
//		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//		redisSessionDAO.setRedisManager(redisManager());
//		return redisSessionDAO;
//	}

//	/**
//	 * Session Manager
//	 * 使用的是shiro-redis开源插件
//	 */
//	@Bean
//	public DefaultWebSessionManager sessionManager() {
//		System.out.println("创建DefaultWebSessionManager...");
//		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//		//取消url 后面的 JSESSIONID
//		sessionManager.setSessionIdUrlRewritingEnabled(false);
//		sessionManager.setSessionDAO(redisSessionDAO());
//		return sessionManager;
//	}

	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		System.out.println("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/cip/img/favicon.ico", "anon");//解决登录成功跳转到ico文件页面问题
		filterChainDefinitionMap.put("/ui/**", "anon");
		filterChainDefinitionMap.put("/bower_components/**", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");
		filterChainDefinitionMap.put("/headimg/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/misc/**", "anon");
		filterChainDefinitionMap.put("/php-version/**", "anon");
		filterChainDefinitionMap.put("/tologin", "anon");
		// 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");
		// <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;

		filterChainDefinitionMap.put("/index", "authc");

//		filterChainDefinitionMap.put("/userlist", "perms[test]");
		// <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		filterChainDefinitionMap.put("/**", "authc");
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/index");

		// 未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean(name = "authRealm")
	public MyShiroRealm myShiroRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		// 设置密码凭证匹配器
		myShiroRealm.setCredentialsMatcher(matcher); // myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
	}

	@Bean
	public SecurityManager securityManager(@Qualifier("authRealm") MyShiroRealm authRealm,
			@Qualifier("cookieRememberMeManager") CookieRememberMeManager cookieRememberMeManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置rememberMe管理器
		securityManager.setRememberMeManager(cookieRememberMeManager);
		securityManager.setRealm(authRealm);
//		// 自定义缓存实现 使用redis
//		securityManager.setCacheManager(cacheManager());
//		// 自定义session管理 使用redis
//		securityManager.setSessionManager(sessionManager());

		return securityManager;
	}

	//加入注解的使用，不加入这个注解不生效
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}
}