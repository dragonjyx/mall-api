package com.mall.service.utils;

/**
 * Created by DRAGON on 2017/11/2.
 */
public class WechatMessage {

    private String appid;
    private String appsecret;
    private String openId;
    private String message;
    private String saveMessage;


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSaveMessage() {
        return saveMessage;
    }

    public void setSaveMessage(String saveMessage) {
        this.saveMessage = saveMessage;
    }

    public WechatMessage(String appid, String appsecret, String openId, String message, String saveMessage) {
        this.appid = appid;
        this.appsecret = appsecret;
        this.openId = openId;
        this.message = message;
        this.saveMessage = saveMessage;
    }
}
