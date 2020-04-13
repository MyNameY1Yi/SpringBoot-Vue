package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.FunctionDao;
import com.heeexy.example.service.FunctionService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class FunctionServiceImpl implements FunctionService{
    @Autowired
    private FunctionDao functionDao;

    @Override
    public JSONObject listFunction(JSONObject jsonObject){
        JSONObject list = functionDao.listFunction(jsonObject);
        System.out.println(list);
        return CommonUtil.successJson(list);
    }

    @Override
    //@Transactional(rollbackFor = Exception.class)
    public JSONObject addFunction(JSONObject jsonObject){
        if (jsonObject.containsValue("123abc") == false)
            return CommonUtil.errorJson(ErrorEnum.E_20184197);
        functionDao.addFunction(jsonObject) ;
        return CommonUtil.successJson() ;
    }

    @Override
    public JSONObject delFunction(JSONObject jsonObject){
        functionDao.delFunction(jsonObject) ;
        return CommonUtil.successJson() ;
    }
}
