package com.fxbank.cip.manager.controller;


import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
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
import com.fxbank.cip.manager.entity.SysDepart;
import com.fxbank.cip.manager.entity.SysRole;
import com.fxbank.cip.manager.entity.SysUser;
import com.fxbank.cip.manager.entity.SysUserDepart;
import com.fxbank.cip.manager.entity.SysUserRole;
import com.fxbank.cip.manager.service.SysUserService;

@Controller
public class UserManageController {

    @Resource
    private SysUserService userService;

    private static Logger logger= LoggerFactory.getLogger(UserManageController.class);
    MyLog myLog = new MyLog("123","223");


    @RequestMapping("/user/list")
    public String list(@ModelAttribute(value="data") SysUser data) {
        return "user/list";
    }


    @RequestMapping("/user/listPage")
    @ResponseBody
    public String listPage(SysUser data,Model model){
        data=userService.getList(data);
        List<SysUser> list=data.getPageList();
        JSONArray jsonArray=JSONArray.parseArray(JSON.toJSONString(list));
        JSONObject json=new JSONObject();
        json.put("msg","");
        json.put("code",0);
        json.put("count",data.getPageCountRows());
        json.put("data",jsonArray.toArray());
        return json.toString();
    }

    @RequestMapping("/user/add")
    public String add(){
        return "user/add";
    }


    @RequestMapping("/user/save")
    @ResponseBody
    public String save(SysUser data){
        JSONObject json=new JSONObject();
        SysUser d=new SysUser();
        d.setUsername(data.getUsername());
        boolean bool=userService.queryByUserName(d);
        if(bool){
            try {
                int id=userService.getMaxId();
                data.setId(id);
                String saltValue = "8d78869f470951332959580424d4bf4f";
                ByteSource salt = ByteSource.Util.bytes(saltValue);
                System.out.println("加密密码的盐：" + salt);
                Object md = new SimpleHash("MD5", data.getPassword(), salt, 1024);
                data.setPassword(md.toString());
                data.setSalt(saltValue);
                data.setState("1");

                userService.addUser(data);

                json.put("success",true);
                json.put("message","用户添加成功");
            } catch (Exception e) {
//                e.printStackTrace();
                json.put("success",false);
                json.put("message","用户新增失败，请联系管理员");
                myLog.info(logger,"用户新增时出错："+e);
            }
        }else{
            json.put("success",false);
            json.put("message","用户名已存在");
        }
        return json.toString();
    }


    @RequestMapping("/user/edit")
    public String edit(SysUser data,Model model){
        data=userService.getUser(data);
        model.addAttribute("data",data);
        return "user/edit";
    }

    @RequestMapping("/user/update")
    @ResponseBody
    public String update(SysUser data){
        JSONObject json=new JSONObject();
        try {
            userService.update(data);
            json.put("success",true);
            json.put("message","修改成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","修改失败，请联系管理员");
            myLog.info(logger,"用户修改时出错："+e);
        }
        return json.toString();
    }

    @RequestMapping("/user/pwd")
    public String pwd(SysUser data,Model model){
        data=userService.getUser(data);
        model.addAttribute("data",data);
        return "user/pwd";
    }

    @RequestMapping("/user/updatePwd")
    @ResponseBody
    public String updatePwd(SysUser data){
        JSONObject json=new JSONObject();
        String saltValue = data.getSalt();
        ByteSource salt = ByteSource.Util.bytes(saltValue);
        System.out.println("加密密码的盐：" + salt);
        Object md = new SimpleHash("MD5", data.getPassword(), salt, 1024);
        data.setPassword(md.toString());
        try {
            userService.update(data);
            json.put("success",true);
            json.put("message","密码修改成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","操作失败，请联系管理员");
            myLog.info(logger,"用户修改时出错："+e);
        }
        return json.toString();
    }

    @RequestMapping("/user/del")
    @ResponseBody
    public String del(SysUser data){
        JSONObject json=new JSONObject();
        try {
            userService.del(data);
            json.put("success",true);
            json.put("message","删除成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","删除失败，请联系管理员");
            myLog.info(logger,"用户删除时出错："+e);
        }
        return json.toString();
    }

    @RequestMapping("/user/roleList")
    public String roleList(@ModelAttribute(value="data") SysUserRole data){

        return "user/role_list";
    }

    @RequestMapping("/user/roleListData")
    @ResponseBody
    public String roleListData(SysUserRole data,Model model){
        List<SysRole> roleList=userService.getRoleList();
        List<SysUserRole> userRoleList=userService.getUserRoleList(data);
        JSONObject json=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        for(int i=0;i<roleList.size();i++){
            JSONObject jsonObject=new JSONObject();
            SysRole sr=roleList.get(i);
            jsonObject.put("id",sr.getId());
            jsonObject.put("pId",0);
            jsonObject.put("description",sr.getDescription());
            jsonObject.put("role",sr.getRole());
            jsonObject.put("available",sr.getAvailable());
            for(int j=0;j<userRoleList.size();j++){
                if(sr.getId()==userRoleList.get(j).getRoleId()){
                    jsonObject.put("lay_is_checked",true);
                    break;
                }
            }
            jsonArray.add(jsonObject);
        }
        json.put("msg","");
        json.put("code",0);
        json.put("data",jsonArray.toArray());
        return json.toString();
    }

    @RequestMapping("/user/roleSave")
    @ResponseBody
    public String roleSave(int id,String ids){
        JSONObject json=new JSONObject();
        try {
            userService.saveRole(ids,id);
            json.put("success",true);
            json.put("message","操作成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","操作失败，请联系管理员");
            myLog.info(logger,"用户修改角色时出错："+e);
        }
        return json.toString();
    }


    @RequestMapping("/user/depart")
    public String depart(@ModelAttribute(value="data") SysUserDepart data){

        return "user/depart";
    }

    @RequestMapping("/user/departListData")
    @ResponseBody
    public String departListData(SysUserDepart data){
        List<SysDepart> departList=userService.getDepart();
        List<SysUserDepart> userDepartList=userService.getUserDepart(data);
        JSONObject json=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        for (int i=0;i<departList.size();i++){
            JSONObject jsonObject=new JSONObject();
            SysDepart sd=departList.get(i);
            jsonObject.put("id",sd.getId());
            jsonObject.put("pId",sd.getParentId());
            jsonObject.put("name",sd.getName());
            jsonObject.put("code",sd.getCode());
            jsonObject.put("available",sd.getAvailable());
            for(int j=0;j<userDepartList.size();j++){
                if(sd.getId()==userDepartList.get(j).getDepartId()){
                    jsonObject.put("lay_is_radio",true);
                    break;
                }
            }
            jsonArray.add(jsonObject);
        }
        json.put("msg","");
        json.put("code",0);
        json.put("data",jsonArray);
        return json.toString();
    }


    @RequestMapping("/user/saveDepart")
    @ResponseBody
    public String saveDepart(SysUserDepart data){
        JSONObject json=new JSONObject();

        try {
            userService.updateUserDepart(data);
            json.put("success",true);
            json.put("message","操作成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","操作失败，请联系管理员");
            myLog.info(logger,"用户修改不门时出错："+e);
        }

        return json.toString();
    }

}
