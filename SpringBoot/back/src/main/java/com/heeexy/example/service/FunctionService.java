package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

public interface FunctionService {
    JSONObject addFunction(JSONObject jsonObject) ;

    JSONObject listFunction(JSONObject jsonObject) ;

    JSONObject delFunction(JSONObject jsonObject) ;
}
