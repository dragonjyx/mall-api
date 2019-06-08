package com.mall.webapi.controller;


import com.java.response.JsonResult;
import com.mall.params.resp.RegionResp;
import com.mall.service.RegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @ResponseBody
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public JsonResult getRegionList(){
        List<RegionResp>  regionList = regionService.allRegion();
        return JsonResult.success(regionList);
    }


}
