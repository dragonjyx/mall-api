package com.mall.webapi.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.java.redis.cache.CacheManager;
import com.java.redis.cache.SessionCache;
import com.java.response.JsonResult;
import com.mall.webapi.controller.BaseController;
import com.mall.webapi.util.SessionStore;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoginIntercepter implements HandlerInterceptor {

    private String env;

    public LoginIntercepter(String env) {
        this.env = env;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

//        log.warn("--------LoginIntercepter preHandle---------");

        String token = request.getParameter("token");

        if(StringUtils.isEmpty(token)){
            JsonResult jsonResult = JsonResult.fail("token is null");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            try {
                response.getWriter().append(JSONObject.toJSONString(jsonResult));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }


        String openId = getOpenId(token);
        if(StringUtils.isEmpty(openId)){
            JsonResult jsonResult = JsonResult.fail("用户未登录");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            try {
                response.getWriter().append(JSONObject.toJSONString(jsonResult));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }


        return true;
    }


    protected SessionCache getSessionCache() {
        SessionCache sessionCache = CacheManager.getSessionCache();
        return sessionCache;
    }


    protected String getOpenId(String token){
        JSONObject session = null;
        if (!env.equals("prod")) {
            session = SessionStore.getInstance().get(token);//测试环境
        }else{
            session = (JSONObject) getSessionCache().getObject(token);
        }

        if(session == null){
            return null;
        }

        return session.getString(BaseController.XCX_OPEN_ID);
    }



}
