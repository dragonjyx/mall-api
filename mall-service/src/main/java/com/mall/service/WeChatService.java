package com.mall.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mall.model.OrderCommon;
import com.mall.model.OrderCommonOffLine;
import com.mall.model.WechatUserInfo;

public interface WeChatService {

     WechatUserInfo bindWechatUserInfo(String url, String phoneNum, String appid, String appsecret);

     JSONObject sendMessage(String openId, String message, String appid, String appsecret);

     int bindPhoneNumber(String openId,String phoneNum);

     WechatUserInfo findByOpenId(String openId);

     WechatUserInfo findByPhoneNum(String phoneNum);

     int sendMessage2Wechat(Integer online,JSONArray phoneNums, JSONObject message, String appid, String appsecret);

//     int sendMessage2Wechat(JSONArray phoneNums,JSONObject params,String tmpId, String appid, String appsecret);

     void notify2send(OrderCommon orderCommon);
     void notify2send(OrderCommonOffLine orderCommonOffLine);

     WechatUserInfo findByUnionId(String unionId);
}
