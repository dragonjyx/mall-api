package com.mall.webapi.controller;


import com.java.redis.cache.SessionCache;
import com.mall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "/")
public class IndexController extends BaseController {


    @Autowired
    private UserService userService;


    @ResponseBody
    @RequestMapping(value = {"","/","index"},method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        SessionCache sessionCache = getSessionCache();
        sessionCache.setStr("test","hello",100);
        log.warn("----------:{}",sessionCache.getStr("test"));


        return "index";
    }


    @RequestMapping(value = "page",method = RequestMethod.GET)
    public String page(Map map){
        map.put("name","spring boot 哈哈哈哈");
        return "index";
    }







}
