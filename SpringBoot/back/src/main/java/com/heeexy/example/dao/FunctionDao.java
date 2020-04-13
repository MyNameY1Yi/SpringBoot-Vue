package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface FunctionDao {
    //查询该用户的权限
    JSONObject listFunction(JSONObject jsonObject) ;

    //添加权限
    int addFunction(JSONObject jsonObject) ;

    int delFunction(JSONObject jsonObject) ;
}
