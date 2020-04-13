package com.heeexy.example.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.faceInfoService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/faceInfo")
public class faceInfoController {
    @Autowired
    public faceInfoService faceInfoservice ;

    @PostMapping("/listfaceInfo")
    public JSONObject listfaceInfo(@RequestBody JSONObject requestJson){
        return faceInfoservice.listfaceInfo(requestJson) ;
    }

    @PostMapping("/addfaceInfo")
    public JSONObject addfaceInfo(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "user");
        return faceInfoservice.addfaceInfo(requestJson) ;
    }

    @RequiresPermissions("faceInfo:list")
        @PostMapping("/listAllfaceInfo")
    public JSONObject listAllfaceInfo(){
        return faceInfoservice.listAllfaceInfo() ;
    }

    @PostMapping("/cancelApply")
    public JSONObject cancelApply(@RequestBody JSONObject requestJson){
        return faceInfoservice.cancelApply(requestJson) ;
    }

    @PostMapping("/cancelPassedApply")
    public JSONObject cancelPassedApply(@RequestBody JSONObject requestJson){
        return faceInfoservice.cancelPassedApply(requestJson) ;
    }

    @RequiresPermissions("faceInfo:pass")
    @PostMapping("/passfaceInfo")
    public JSONObject passfaceInfo(@RequestBody JSONObject requestJson){
        return faceInfoservice.passfaceInfo(requestJson) ;
    }
}
