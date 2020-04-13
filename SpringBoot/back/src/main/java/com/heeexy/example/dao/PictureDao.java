package com.heeexy.example.dao;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface PictureDao {
    int addPicture(JSONObject jsonObject) ;

    int countPicture(JSONObject jsonObject);

    List<JSONObject> listPicture(JSONObject jsonObject);

    int updatePicture(JSONObject jsonObject);
}
