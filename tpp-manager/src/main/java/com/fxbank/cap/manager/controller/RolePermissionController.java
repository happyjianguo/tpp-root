package com.fxbank.cap.manager.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fxbank.cap.manager.entity.SysPermission;
import com.fxbank.cap.manager.entity.SysRolePermission;
import com.fxbank.cap.manager.service.SysPermissionService;
import com.fxbank.cap.manager.service.SysRolePermissionService;
import com.fxbank.cip.base.log.MyLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class RolePermissionController {

    @Resource
    private SysRolePermissionService service;

    private static Logger logger= LoggerFactory.getLogger(RoleManageController.class);

    MyLog myLog = new MyLog("123","223");


    @RequestMapping("/rolePermission/list")
    public String list(@ModelAttribute(value="data") SysRolePermission data){

        return "role_permission/list";
    }



    @RequestMapping("/rolePermission/listData")
    @ResponseBody
    public String listData(SysRolePermission data){
        List<SysPermission> permissionList=service.getPermissionList();
        List<SysRolePermission> sysRolePermissionList=service.getRolePermissionList(data);
        JSONObject json=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        for(int i=0;i<permissionList.size();i++){
            JSONObject jsonObject=new JSONObject();
            SysPermission d=permissionList.get(i);
            jsonObject.put("id",d.getId());
            jsonObject.put("pId",d.getParentId());
            jsonObject.put("name",d.getName());
            jsonObject.put("url",d.getUrl());
            jsonObject.put("available",d.getAvailable());
            for (int j=0;j<sysRolePermissionList.size();j++){
                if(d.getId()==sysRolePermissionList.get(j).getPermissionId()){
                    jsonObject.put("lay_is_checked",true);
                    break;
                }
            }
            jsonArray.add(jsonObject);
        }
        json.put("msg","");
        json.put("code",0);
//        json.put("is",true);
//        json.put("tip","aaaa");
//        json.put("count",200);
        json.put("data",jsonArray.toArray());
        return  json.toString();
    }

    @RequestMapping("/rolePermission/save")
    @ResponseBody
    public String save(String ids,int roleId){
        JSONObject json=new JSONObject();
        try {
            service.save(ids,roleId);
            json.put("success",true);
            json.put("message","操作成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","操作失败，请联系管理员");
            myLog.info(logger,"保存权限是出错："+e);
        }
        return json.toString();
    }
}
