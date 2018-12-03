package com.fxbank.tpp.manager.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class ListDataUtil {
    public static JSONObject beanListToJson(List<?> ListData){
        JSONArray jsonArray= JSONArray.parseArray(JSON.toJSONString(ListData));
        JSONObject json=new JSONObject();
        json.put("msg","");
        json.put("code",0);
        json.put("is",true);
        json.put("tip","");
        json.put("count",ListData.size());
        json.put("data",jsonArray.toArray());
        return json;
    }
}
