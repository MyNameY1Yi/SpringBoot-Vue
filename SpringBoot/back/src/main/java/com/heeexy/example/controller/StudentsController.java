package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.StudentsService;
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
@RequestMapping("/students")
public class StudentsController {

    @Autowired
    private StudentsService studentsService;

    /**
     * 查询文章列表
     */
    @RequiresPermissions("students:list")
    @GetMapping("/listStudents")
    @CrossOrigin(value = "*", allowedHeaders ="*")
    public JSONObject listStudents(HttpServletRequest request) {
        return studentsService.listStudents(CommonUtil.request2Json(request));
    }

    /**
     * 新增文章
     */
    @RequiresPermissions("students:add")
    @PostMapping("/addStudents")
    public JSONObject addStudents(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "sname");
        return studentsService.addStudents(requestJson);
    }

    /**
     * 修改文章
     */
    @RequiresPermissions("students:update")
    @PostMapping("/updateStudents")
    public JSONObject updateStudents(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "sname");
        return studentsService.updateStudents(requestJson);
    }
}
