package com.fxbank.cip.manager.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fxbank.cip.manager.entity.SysUser;
import com.fxbank.cip.manager.entity.SysUserDepart;
import com.fxbank.cip.manager.service.SysUserDepartService;

@Controller
public class UserDepartController {
    private static Logger logger= LoggerFactory.getLogger(UserDepartController.class);

    @Resource
    private SysUserDepartService sysUserDepartService;

    @RequestMapping("/userdepart/userlist")
    public String userList(@ModelAttribute(value="data")SysUserDepart data){
        return "userdepart/userlist";
    }

    @RequestMapping("/userdepart/getUserList")
    @ResponseBody
    public String getUserList(SysUserDepart data){
        System.out.println("data  ==  "+data.getDepartId());
        List<SysUserDepart> sysUserDepartList = sysUserDepartService.getUserByDepart(data);
        List<SysUser> sysUserList = sysUserDepartService.getAvailableUser();
        JSONObject json=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        for(int i=0;i<sysUserList.size();i++){
            JSONObject jsonObject=new JSONObject();
            SysUser d=sysUserList.get(i);
            jsonObject.put("id",d.getId());
            jsonObject.put("name",d.getName());
            jsonObject.put("userName",d.getUsername());
            for (int j=0;j<sysUserDepartList.size();j++){
                System.out.println("d.getId()  =  "+d.getId()+" id= "+sysUserDepartList.get(j).getUserId());
                if(d.getId()==sysUserDepartList.get(j).getUserId()){
                    jsonObject.put("lay_is_checked",true);
                    break;
                }
            }
            jsonArray.add(jsonObject);
        }
        json.put("msg","");
        json.put("code",0);
        json.put("count",sysUserList.size());
        json.put("data",jsonArray.toArray());

        return json.toString();
    }

    @RequestMapping("/userdepart/save")
    @ResponseBody
    public String save(String ids,int departId){
        JSONObject json=new JSONObject();
        try {
            sysUserDepartService.save(ids,departId);
            json.put("success",true);
            json.put("message","操作成功");
        } catch (Exception e) {
            logger.error(e.toString());
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","操作失败，请联系管理员");
        }
        return json.toString();
    }
}
