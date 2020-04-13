package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.faceInfoDao;
import com.heeexy.example.service.faceInfoService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class faceInfoServiceImpl implements faceInfoService {
    @Autowired
    public faceInfoDao faceInfodao;

    @Override
    public JSONObject addfaceInfo(JSONObject jsonObject){
        faceInfodao.addfaceInfo(jsonObject) ;
        jsonObject.put("status" , 1) ;
        faceInfodao.updfaceInfo( jsonObject ) ;
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject listAllfaceInfo() {
        List<JSONObject> list = faceInfodao.listAllfaceInfo() ;
        return CommonUtil.successJson(list) ;
    }

    @Override
    public JSONObject listfaceInfo(JSONObject jsonObject) {
        int haveExist = faceInfodao.QueryExist(jsonObject) ;
        if (haveExist == 0){
            faceInfodao.addItem(jsonObject) ;
        }
        List<JSONObject> list = faceInfodao.listfaceInfo(jsonObject) ;
        return CommonUtil.successJson(list) ;
    }

    @Override
    public JSONObject cancelApply(JSONObject jsonObject){
        faceInfodao.cancelApply(jsonObject) ;
        faceInfodao.cancelPassedApply(jsonObject) ;
        jsonObject.put("number","null") ;
        jsonObject.put("status",0) ;
        faceInfodao.updfaceInfo( jsonObject ) ;
        return CommonUtil.successJson() ;
    }

    @Override
    public JSONObject cancelPassedApply(JSONObject jsonObject){
        faceInfodao.cancelApply(jsonObject) ;
        faceInfodao.cancelPassedApply(jsonObject) ;
        jsonObject.put("number","null") ;
        jsonObject.put("status",0) ;
        faceInfodao.updfaceInfo( jsonObject ) ;
        return CommonUtil.successJson() ;
    }

    @Override
    public JSONObject passfaceInfo(JSONObject jsonObject){
        faceInfodao.delfaceInfo(jsonObject) ;
        faceInfodao.addPassedfaceInfo(jsonObject) ;
        jsonObject.put("status",2) ;
        faceInfodao.updfaceInfo( jsonObject ) ;
        return CommonUtil.successJson() ;
    }


}
