package com.fxbank.tpp.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.fxbank.tpp.manager.entity.SysDepart;
import com.fxbank.tpp.manager.service.SysDepartService;
import com.fxbank.tpp.manager.utils.ListDataUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class DepartController {
    private static Logger logger = LoggerFactory.getLogger(DepartController.class);

    @Resource
    private SysDepartService sysDepartService;

    @RequestMapping("/depart/list")
    public String list(){
        return "depart/list";
    }

    @RequestMapping("/depart/getList")
    @ResponseBody
    public String getList(){
        List<SysDepart> sysDepartList = sysDepartService.getAllDepart();
        JSONObject json = ListDataUtil.beanListToJson(sysDepartList);
        return json.toString();
    }

    @RequestMapping("/depart/add")
    public String add(Model model){
        List<SysDepart> sysDepartList = sysDepartService.getAvailableDepart(null);
        model.addAttribute("parentIdList",sysDepartList);
        return "depart/add";
    }

    @RequestMapping("/depart/save")
    @ResponseBody
    public String save(SysDepart data){
        JSONObject json=new JSONObject();
        try {
            sysDepartService.save(data);
            json.put("success",true);
            json.put("message","保存成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","操作失败，请联系管理员");
            logger.info("保存部门时出错："+e);
        }
        return json.toString();
    }

    @RequestMapping("/depart/edit")
    public String edit(SysDepart sysDepart,Model model){
        SysDepart sysDepartData = sysDepartService.getDepartById(sysDepart);
        List<SysDepart> SysDepartList = sysDepartService.getAvailableDepart(sysDepart);
        model.addAttribute("parentIdList",SysDepartList);
        model.addAttribute("SysDepartData",sysDepartData);
        return "depart/edit";
    }

    @RequestMapping("/depart/update")
    @ResponseBody
    public String update(SysDepart data){
        JSONObject json=new JSONObject();
        try {
            sysDepartService.update(data);
            json.put("success",true);
            json.put("message","保存成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","操作失败，请联系管理员");
            logger.info("修改部门时出错："+e);
        }
        return json.toString();
    }

    @RequestMapping("/depart/del")
    @ResponseBody
    public String del(SysDepart data){
        JSONObject json=new JSONObject();
        try {
            sysDepartService.updateChildPid(data);
            sysDepartService.del(data);
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

}
