package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.FunctionService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/function")
public class FunctionController {
    @Autowired
    private FunctionService functionService ;

    @PostMapping("/listFunction")
    public JSONObject listFunction(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "user");
        return functionService.listFunction(requestJson) ;
    }

    @PostMapping("/addFunction")
    public JSONObject addFunction(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "user");
        return functionService.addFunction(requestJson) ;
    }

    @PostMapping("/delFunction")
    public JSONObject delFunction(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "user");
        return functionService.delFunction(requestJson) ;
    }
}
