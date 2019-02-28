package com.fxbank.tpp.manager.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.fxbank.tpp.manager.entity.SysPermission;
import com.fxbank.tpp.manager.service.SysPermissionService;
import com.fxbank.tpp.manager.utils.ListDataUtil;

@Controller
public class PermissionController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @Resource
    private SysPermissionService sysPermissionService;

    @RequestMapping("/permission/list")
    public String list(){
        return "permission/list";
    }

    @RequestMapping("/permission/getList")
    @ResponseBody
    public String getListData(){
        List<SysPermission> sysPermissionList = sysPermissionService.getAllMenu();
        JSONObject json= ListDataUtil.beanListToJson(sysPermissionList);
        return json.toString();
    }



    @RequestMapping("/permission/add")
    public String add(Model model){
        List<SysPermission> sysPermissionList = sysPermissionService.getTopMenu(null);
        model.addAttribute("parentIdList",sysPermissionList);
        return "permission/add";
    }

    @RequestMapping("/permission/save")
    @ResponseBody
    public String save(SysPermission data){
        JSONObject json=new JSONObject();
        try {
            data.setResourceType("menu");
            sysPermissionService.save(data);
            json.put("success",true);
            json.put("message","保存成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","操作失败，请联系管理员");
            logger.info("保存菜单时出错："+e);
        }
        return json.toString();
    }


    @RequestMapping("/permission/edit")
    public String edit(SysPermission sysPermission,Model model){
        SysPermission sysPermissionData = sysPermissionService.getMenuById(sysPermission);
        List<SysPermission> sysPermissionList = sysPermissionService.getTopMenu(sysPermission);
        model.addAttribute("parentIdList",sysPermissionList);
        model.addAttribute("sysPermissionData",sysPermissionData);
        return "permission/edit";
    }

    @RequestMapping("/permission/update")
    @ResponseBody
    public String update(SysPermission data){
        JSONObject json=new JSONObject();
        try {
            sysPermissionService.update(data);
            json.put("success",true);
            json.put("message","保存成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","操作失败，请联系管理员");
            logger.info("修改菜单时出错："+e);
        }
        return json.toString();
    }

    @RequestMapping("/permission/del")
    @ResponseBody
    public String del(SysPermission data){
        JSONObject json=new JSONObject();
        try {
            sysPermissionService.updateChildPid(data);
            sysPermissionService.del(data);
            json.put("success",true);
            json.put("message","删除成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","操作失败，请联系管理员");
            logger.info("删除菜单时出错："+e);
        }
        return json.toString();
    }

    @Value("${spring.localFilePath}")
    String saveurl;

    @RequestMapping("/permission/upload")
    @ResponseBody
    public String uploadImg(@RequestParam("file") MultipartFile file){
        JSONObject json = new JSONObject();
        try{
            String oldName = file.getOriginalFilename();
            String ext= "png";
            if(oldName.indexOf(".")>0)ext = oldName.substring(oldName.indexOf(".")+1);
            String fileName = UUID.randomUUID().toString()+"."+ext;
            File file1 = new File(saveurl + fileName);
            file.transferTo(file1);
            json.put("success",true);
            json.put("msg","");
            json.put("fileName",fileName);
        }catch (Exception e){
            json.put("success",false);
            json.put("fileName","");
            json.put("msg",e.getMessage());
            logger.error("上传图片错误："+e.toString());
        }
        return json.toString();
    }



}
