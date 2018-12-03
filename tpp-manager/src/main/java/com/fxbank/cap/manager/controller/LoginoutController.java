package com.fxbank.cap.manager.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fxbank.cap.manager.entity.SysPermission;
import com.fxbank.cap.manager.service.SysPermissionService;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginoutController {

	@Resource
	private SysPermissionService sysPermissionService;

    @RequestMapping("/tologin")
    public String toLogin() {
        return "user/login";
    }
    
    @RequestMapping("/index")
    public String index() {
		System.out.println("--------登录成功---------");
    	return "index";
    }
	@RequestMapping("/login")
	public String login(HttpServletRequest request, Map<String, Object> map) throws Exception{
	    System.out.println("HomeController.login()");
	    // 登录失败从request中获取shiro处理的异常信息。
	    // shiroLoginFailure:就是shiro异常类的全类名.
	    String exception = (String) request.getAttribute("shiroLoginFailure");
	    System.out.println("exception=" + exception);
	    String msg = "";
	    if (exception != null) {
	        if (UnknownAccountException.class.getName().equals(exception)) {
	            System.out.println("UnknownAccountException -- > 账号不存在：");
	            msg = "账号不存在";
	        } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
	            System.out.println("IncorrectCredentialsException -- > 密码不正确：");
	            msg = "密码不正确";
	        } else if ("kaptchaValidateFailed".equals(exception)) {
	            System.out.println("kaptchaValidateFailed -- > 验证码错误");
	            msg = "验证码错误";
	        } else {
	            msg = exception;
	            System.out.println("else -- >" + exception);
	        }
	    }
	    map.put("msg", msg);
	    // 此方法不处理登录成功,由shiro进行处理
	    return "user/login";
	}

	@RequestMapping("/getMenu")
	@ResponseBody
	public String getMenu(){
    	//拼接一级菜单
		List<SysPermission> list=sysPermissionService.getMenu(0);
		JSONArray jsonArray=new JSONArray();
    	for(int i=0;i<list.size();i++){
    		JSONObject json=new JSONObject();
    		json.put("text",list.get(i).getName());
    		JSONArray jarr=new JSONArray();//二级菜单列表
			json.put("childArr",jarr);
			List<SysPermission> list2=sysPermissionService.getMenu(list.get(i).getId());
			for(int j=0;j<list2.size();j++){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("id",list2.get(j).getId());
				jsonObject.put("text",list2.get(j).getName());
				jsonObject.put("imgUrl",(list2.get(j).getImgUrl()==null||list2.get(j).getImgUrl().equals(""))?"ui/images/image1.jpg":list2.get(j).getImgUrl());
				jsonObject.put("url",list2.get(j).getUrl());
				jarr.add(jsonObject);
			}
			jsonArray.add(json);
		}
		return  jsonArray.toString();
	}
	
}
