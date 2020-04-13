package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: hxy
 * @description: 文章Dao层
 * @date: 2017/10/24 16:06
 */
public interface StudentsDao {
    /**
     * 新增文章
     */
    int addStudents(JSONObject jsonObject);

    /**
     * 统计文章总数
     */
    int countStudents(JSONObject jsonObject);

    /**
     * 文章列表
     */
    List<JSONObject> listStudents(JSONObject jsonObject);

    /**
     * 更新文章
     */
    int updateStudents(JSONObject jsonObject);
}
