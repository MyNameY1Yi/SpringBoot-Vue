package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface faceInfoDao {
    //add waiting face information
    int addfaceInfo(JSONObject jsonObject) ;

    //add passed face information
    int addPassedfaceInfo(JSONObject jsonObject) ;

    //query all face information
    List<JSONObject> listAllfaceInfo() ;

    //query one faec information
    List<JSONObject> listfaceInfo(JSONObject jsonObject) ;

    //delete face information
    int delfaceInfo(JSONObject jsonObject) ;

    //update one face information
    int updfaceInfo(JSONObject jsonObject) ;

    //query exist or not
    int QueryExist(JSONObject jsonObject) ;

    //add item
    int addItem(JSONObject jsonObject) ;

    //cancel apply
    int cancelApply(JSONObject jsonObject) ;
    int cancelPassedApply(JSONObject jsonObject) ;

}
