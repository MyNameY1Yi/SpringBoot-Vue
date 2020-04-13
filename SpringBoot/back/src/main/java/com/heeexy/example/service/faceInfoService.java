package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface faceInfoService {
    //add face information
    JSONObject addfaceInfo(JSONObject jsonObject) ;

    //query all face information
    JSONObject listAllfaceInfo() ;

    //query one face information
    JSONObject listfaceInfo(JSONObject jsonObject) ;

    //cancle apply
    JSONObject cancelApply(JSONObject jsonObject) ;

    //pass one face apply
    JSONObject passfaceInfo(JSONObject jsonObject) ;
    JSONObject cancelPassedApply(JSONObject jsonObject) ;

}