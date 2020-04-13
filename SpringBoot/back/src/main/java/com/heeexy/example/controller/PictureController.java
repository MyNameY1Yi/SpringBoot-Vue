package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.PictureService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: hxy
 * @description: 文章相关Controller
 * @date: 2017/10/24 16:04
 */
@RestController
@CrossOrigin(value = "*", allowedHeaders ="*")
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    /**
     * 查询文章列表
     */
    //@RequiresPermissions("picture:list")
    @GetMapping("/listPicture")
    @CrossOrigin(value = "*", allowedHeaders ="*")
    public JSONObject listPicture(HttpServletRequest request) {
        return pictureService.listPicture(CommonUtil.request2Json(request));
    }

    /**
     * 新增文章
     */
    @RequiresPermissions("picture:add")
    @PostMapping("/addPicture")
    public JSONObject addPicture(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "car");
        return pictureService.addPicture(requestJson);
    }

    /**
     * 修改文章
     */
    @RequiresPermissions("picture:update")
    @PostMapping("/updatePicture")
    public JSONObject updatePicture(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "car");
        return pictureService.updatePicture(requestJson);
    }
}
