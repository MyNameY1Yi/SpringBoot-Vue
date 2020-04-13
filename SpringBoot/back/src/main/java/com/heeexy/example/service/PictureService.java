package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: hxy
 * @date: 2017/10/24 16:06
 */
public interface PictureService {
    /**
     * 新增文章
     */
    JSONObject addPicture(JSONObject jsonObject);

    /**
     * 文章列表
     */
    JSONObject listPicture(JSONObject jsonObject);

    /**
     * 更新文章
     */
    JSONObject updatePicture(JSONObject jsonObject);
}
