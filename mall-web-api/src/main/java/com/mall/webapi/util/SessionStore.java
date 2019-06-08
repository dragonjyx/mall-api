package com.mall.webapi.util;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存
 */
public class SessionStore extends ConcurrentHashMap<String,JSONObject> {
    private static SessionStore sessionStore;

    private SessionStore() {
        super();
    }

    public static SessionStore getInstance(){
        if(sessionStore == null){
            synchronized (SessionStore.class){
                if(sessionStore == null){
                    sessionStore = new SessionStore();
                }
            }
        }
        return sessionStore;
    }
}
