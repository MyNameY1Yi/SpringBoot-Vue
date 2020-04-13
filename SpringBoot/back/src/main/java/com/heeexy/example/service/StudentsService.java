package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: hxy
 * @date: 2017/10/24 16:06
 */
public interface StudentsService {
    /**
     * 新增文章
     */
    JSONObject addStudents(JSONObject jsonObject);

    /**
     * 文章列表
     */
    JSONObject listStudents(JSONObject jsonObject);

    /**
     * 更新文章
     */
    JSONObject updateStudents(JSONObject jsonObject);
}
