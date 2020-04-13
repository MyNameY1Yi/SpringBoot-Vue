package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.PictureDao;
import com.heeexy.example.service.PictureService;
import com.heeexy.example.service.UserService;
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
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureDao pictureDao;

    /**
     * 新增文章
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addPicture(JSONObject jsonObject) {
        pictureDao.addPicture(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 文章列表
     */
    @Override
    public JSONObject listPicture(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = pictureDao.countPicture(jsonObject);
        List<JSONObject> list = pictureDao.listPicture(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 更新文章
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updatePicture(JSONObject jsonObject) {
        pictureDao.updatePicture(jsonObject);
        return CommonUtil.successJson();
    }
}
