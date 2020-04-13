package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.StudentsDao;
import com.heeexy.example.service.StudentsService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: hxy
 * @date: 2017/10/24 16:07
 */
@Service
public class StudentsServiceImpl implements StudentsService {

    @Autowired
    private StudentsDao studentsDao;

    /**
     * 新增文章
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addStudents(JSONObject jsonObject) {
        studentsDao.addStudents(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 文章列表
     */
    @Override
    public JSONObject listStudents(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = studentsDao.countStudents(jsonObject);
        List<JSONObject> list = studentsDao.listStudents(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 更新文章
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateStudents(JSONObject jsonObject) {
        studentsDao.updateStudents(jsonObject);
        return CommonUtil.successJson();
    }
}
