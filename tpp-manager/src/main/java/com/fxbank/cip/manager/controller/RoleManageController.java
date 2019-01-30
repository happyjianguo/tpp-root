package com.fxbank.cip.manager.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.manager.entity.SysRole;
import com.fxbank.cip.manager.service.SysRoleService;

@Controller
public class RoleManageController {


    @Resource
    private SysRoleService service;

    private static Logger logger= LoggerFactory.getLogger(RoleManageController.class);

    MyLog myLog = new MyLog("123","223");

    @RequestMapping("/role/list")
    public String list(@ModelAttribute(value="data") SysRole data){
//        model.addAttribute("data",data);
        return "role/list";
    }


    @RequestMapping("/role/listPage")
    @ResponseBody
    public String listPage(SysRole data){
        data=service.getList(data);
        List<SysRole> list=data.getPageList();
        JSONArray jsonArray=JSONArray.parseArray(JSON.toJSONString(list));
        JSONObject json=new JSONObject();
        json.put("msg","");
        json.put("code",0);
        json.put("count",data.getPageCountRows());
        json.put("data",jsonArray.toArray());
        return json.toString();
    }


    @RequestMapping("/role/add")
    public String add(){
        return "role/add";
    }

    @RequestMapping("/role/save")
    @ResponseBody
    public String save(SysRole data){
        JSONObject json=new JSONObject();
        try {
            service.save(data);
            json.put("success",true);
            json.put("message","保存成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","操作失败，请联系管理员");
            myLog.info(logger,"保存角色时出错："+e);
        }
        return json.toString();
    }

    @RequestMapping("/role/del")
    @ResponseBody
    public String del(SysRole data){
        JSONObject json=new JSONObject();
        try {
            service.del(data);
            json.put("success",true);
            json.put("message","删除成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","删除失败，请联系管理员");
            myLog.info(logger,"删除角色时出错："+e);
        }
        return json.toString();
    }

    @RequestMapping("/role/edit")
    public String edit(SysRole data,Model model){
        data=service.getSysRole(data);
        model.addAttribute("data",data);
        return "role/edit";
    }

    @RequestMapping("/role/update")
    @ResponseBody
    public String update(SysRole data){
        JSONObject json=new JSONObject();
        try {
            service.update(data);
            json.put("success",true);
            json.put("message","修改成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","修改失败，请联系管理员");
            myLog.info(logger,"修改角色时出错："+e);
        }
        return json.toString();
    }

    @RequestMapping("/role/read")
    public String read(SysRole data,Model model){
        data=service.getSysRole(data);
        model.addAttribute("data",data);
        return "role/read";
    }

}
