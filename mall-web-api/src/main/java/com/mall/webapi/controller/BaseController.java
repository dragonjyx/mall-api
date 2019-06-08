package com.mall.webapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.java.redis.cache.CacheManager;
import com.java.redis.cache.SessionCache;
import com.mall.webapi.util.SessionStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础控制器
 */
@Slf4j
@Controller
public class BaseController implements EnvironmentAware {

    protected String env;
    public final static int SESSION_TIMEOUT = 3600*24*30;//一个月

    public final static String XCX_OPEN_ID   = "XCX_OPEN_ID";//小程序
    public final static String MP_OPEN_ID    = "MP_OPEN_ID";//公众号
    public final static String XCX_USER_ID    = "XCX_USER_ID";//用户id




    public final static int SESSION_30_MIN       = 1800;
    public final static String SESSION_USERID    = "SESSION_USERID";
    public final static String SESSION_TOKEN     = "token";
    public final static String SESSION_SESSIONID = "SESSION_SESSIONID";
    public final static String SESSION_USER_NAME = "SESSION_USER_NAME";

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment.getProperty("spring.profiles.active");
    }

    protected SessionCache getSessionCache() {
        SessionCache sessionCache = CacheManager.getSessionCache();
        return sessionCache;
    }

    //--------------------小程序--------------------

    protected String getToken(HttpServletRequest request){
        return request.getParameter(SESSION_TOKEN);
    }


    protected boolean saveUserId(String token, String userId) {
        JSONObject session = getSession(token);
        if(session == null){
            return false;
        }
        session.put(XCX_USER_ID, userId);
        if (!this.env.equals("prod")) {
            SessionStore.getInstance().put(token, session);//测试环境
            return true;
        }
        return getSessionCache().setObject(token, session, SESSION_TIMEOUT);
    }


    protected String getUserId(String token){
        JSONObject session = null;
        if (!this.env.equals("prod")) {
            session = SessionStore.getInstance().get(token);//测试环境
        }else{
            session = (JSONObject) getSessionCache().getObject(token);
        }
        if(session == null){
            return null;
        }
        return session.getString(XCX_USER_ID);
    }


    protected boolean saveSession(String token, String openId) {
        JSONObject session = new JSONObject();
        session.put(XCX_OPEN_ID, openId);
        if (!this.env.equals("prod")) {
            SessionStore.getInstance().put(token, session);//测试环境
            return true;
        }
        return getSessionCache().setObject(token, session, SESSION_TIMEOUT);
    }


    protected String getOpenId(String token){
        JSONObject session = null;
        if (!this.env.equals("prod")) {
            session = SessionStore.getInstance().get(token);//测试环境
        }else{
            session = (JSONObject) getSessionCache().getObject(token);
        }
        if(session == null){
            return null;
        }
        log.warn("getOpenId session:{}",session.toJSONString());
        return session.getString(XCX_OPEN_ID);
    }


    protected JSONObject getSession(String token){
        JSONObject session = null;
        if (!this.env.equals("prod")) {
            session = SessionStore.getInstance().get(token);//测试环境
        }else{
            session = (JSONObject) getSessionCache().getObject(token);
        }
        if(session == null){
            return null;
        }
        log.warn("getSession session:{}",session.toJSONString());
        return session;
    }




    protected boolean removeSession(String token) {
        if(StringUtils.isEmpty(token)){
            return false;
        }
        if (!this.env.equals("prod")) {
            SessionStore.getInstance().remove(token);
            return true;
        }
        return getSessionCache().removeKey(token);
    }

    //--------------------公众号--------------------




}
