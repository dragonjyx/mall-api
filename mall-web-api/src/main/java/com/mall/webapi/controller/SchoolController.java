package com.mall.webapi.controller;


import com.alibaba.fastjson.JSONObject;
import com.java.response.JsonResult;
import com.mall.params.resp.SchoolResp;
import com.mall.service.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "school")
public class SchoolController extends BaseController {


    @Autowired
    private SchoolService schoolService;


    /**
     * 获取学校列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "treeList",method = RequestMethod.POST)
    public JsonResult schoolTree(HttpServletRequest request, @RequestBody JSONObject params){
        Integer type = params.getInteger("type");
        List<SchoolResp> schoolRespList = schoolService.findSchoolTreeList(type);
        return JsonResult.success(schoolRespList);
    }


    /**
     * 所有学校列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "all-list",method = RequestMethod.GET)
    public JsonResult findAllSchoolList(){
        return JsonResult.success("");
    }



    /**
     * 根据学校查id询宿舍
     * @param request
     * @param schoolId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "dorm/query-by-schoolid/{schoolId}",method = RequestMethod.GET)
    public JsonResult querySchoolDormBySchoolId(HttpServletRequest request,@PathVariable("schoolId") Long schoolId){
        return JsonResult.success("");
    }









}
