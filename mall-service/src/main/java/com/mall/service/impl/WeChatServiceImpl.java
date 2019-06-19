package com.mall.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.java.redis.cache.CacheManager;
import com.java.redis.cache.SessionCache;
import com.java.utils.http.HttpUtil;
import com.mall.dao.UserInfoDao;
import com.mall.dao.UserSchoolDormDao;
import com.mall.dao.UserSchoolDormManageDao;
import com.mall.dao.WechatUserInfoDao;
import com.mall.model.*;
import com.mall.params.status.OrderType;
import com.mall.service.WeChatService;
import com.mall.service.utils.TemplateMessage;
import com.mall.service.utils.TemplateParams;
import com.mall.service.utils.WxInfoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信服务
 */
@Slf4j
@Service
public class WeChatServiceImpl implements WeChatService {

    private final static String GLOBALACCESSTOKEN = "GLOBALACCESSTOKEN";
    private final static String JSTICKET = "JSTICKET";
    private final static int GLOBAL_ACCESS_TOKEN_TIMEOUT = 6000;
    private final static String INVITE_CARD_KEY = "INVITE_CARD_KEY";//邀请卡

    private String weixinurl = "https://api.weixin.qq.com";

    @Value("${wechat.xcx_appid}")
    private String xcx_appid;

    @Value("${wechat.order_notify.template_msg_id}")
    private String template_msg_id;

    @Value("${wechat.xcx_path}")
    private String xcx_path;

    @Value("${wechat.mp_appid}")
    private String appid;

    @Value("${wechat.mp_appsecret}")
    private String appsecret;

    @Autowired
    private WechatUserInfoDao wechatUserInfoDao;



    @Override
    public WechatUserInfo bindWechatUserInfo(String url, String phoneNum, String appid, String appsecret) {
        SessionCache sessionCache = CacheManager.getSessionCache();
        String result = HttpUtil.doGet(url,0,null);
        log.warn("get openId and accessToken:\n{}",result);
        if(StringUtils.isEmpty(result)){
            log.error("get openId and accessToken fail");
            return null;
        }

        JSONObject jsonObject = JSONObject.parseObject(result);
        //{"errcode":40029,"errmsg":"invalid code"}
        if (jsonObject.getString("errcode") != null) {
            log.error("get openId and accessToken fail errcode：{}",jsonObject.getString("errcode"));
            return null;
        }

        String openId = jsonObject.getString("openid");
        Object globalAccessToken = sessionCache.getStr(GLOBALACCESSTOKEN);
        log.warn("从cache获取全局的token:{}", globalAccessToken);



        JSONObject wxUserInfo = null;
        if (globalAccessToken != null) {
            //获取微信用户
            wxUserInfo = WxInfoUtils.getUserInfoByGlobalAccessToken(globalAccessToken.toString(), openId, weixinurl);
            if (wxUserInfo == null) {//accessToken过期
                JSONObject accessTokenJson = WxInfoUtils.getGlobalAccessToken(appid, appsecret, weixinurl);
                if (accessTokenJson != null) {
                    log.info("再次获取全局accessToken");
                    String accessToken = accessTokenJson.getString("access_token");
                    log.info("获取微信全局的token:{}", accessToken);
                    //获取微信用户
                    wxUserInfo = WxInfoUtils.getUserInfoByGlobalAccessToken(accessToken, openId, weixinurl);
                    sessionCache.setStr(GLOBALACCESSTOKEN, accessToken, GLOBAL_ACCESS_TOKEN_TIMEOUT);
                }
            }
        } else {
            JSONObject accessTokenJson = WxInfoUtils.getGlobalAccessToken(appid, appsecret, weixinurl);
            if (accessTokenJson != null) {
                String accessToken = accessTokenJson.getString("access_token");
                log.info("获取微信全局的token:{}", accessToken);
                //获取微信用户
                wxUserInfo = WxInfoUtils.getUserInfoByGlobalAccessToken(accessToken, openId, weixinurl);
                sessionCache.setStr(GLOBALACCESSTOKEN, accessToken, GLOBAL_ACCESS_TOKEN_TIMEOUT);
            }
        }


        if(wxUserInfo == null){
            log.error("get wxUserInfo fail");
            return null;
        }

        if(StringUtils.isEmpty(wxUserInfo.getString("unionid"))){
            log.error("unionid is null");
            return null;
        }


        WechatUserInfo wechatUserInfo  = wechatUserInfoDao.findByOpenId(openId);
        if(wechatUserInfo !=null){//已经存在更新
            wechatUserInfo.setPhoneNum(phoneNum);
            wechatUserInfo.setUnionId(wxUserInfo.getString("unionid"));
            wechatUserInfo.setCity(wxUserInfo.getString("city"));
            wechatUserInfo.setCountry(wxUserInfo.getString("country"));
            wechatUserInfo.setHeadimgurl(wxUserInfo.getString("headimgurl"));
            wechatUserInfo.setProvince(wxUserInfo.getString("province"));
            wechatUserInfo.setSex(wxUserInfo.getString("sex"));
            wechatUserInfo.setLanguage(wxUserInfo.getString("language"));
            wechatUserInfo.setSubscribe_time(wxUserInfo.getString("subscribe_time"));
            wechatUserInfo.setNickname(wxUserInfo.getString("nickname"));
            int res = wechatUserInfoDao.updateWechatUserInfo(wechatUserInfo);
            log.warn("update wechat userInfo result：{}",res);
            return wechatUserInfo;
        }


        wechatUserInfo  = new WechatUserInfo();
        wechatUserInfo.setUnionId(wxUserInfo.getString("unionid"));
        wechatUserInfo.setPhoneNum(phoneNum);
        wechatUserInfo.setOpenId(openId);
        wechatUserInfo.setCity(wxUserInfo.getString("city"));
        wechatUserInfo.setCountry(wxUserInfo.getString("country"));
        wechatUserInfo.setHeadimgurl(wxUserInfo.getString("headimgurl"));
        wechatUserInfo.setProvince(wxUserInfo.getString("province"));
        wechatUserInfo.setSex(wxUserInfo.getString("sex"));
        wechatUserInfo.setLanguage(wxUserInfo.getString("language"));
        wechatUserInfo.setSubscribe_time(wxUserInfo.getString("subscribe_time"));
        wechatUserInfo.setNickname(wxUserInfo.getString("nickname"));
        int res = wechatUserInfoDao.addNewWechatInfo(wechatUserInfo);
        log.warn("insert new wechat userInfo result：{}",res);
        return wechatUserInfo;
    }

    @Override
    public JSONObject sendMessage(String openId, String message, String appid, String appsecret) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser", openId);
        jsonObject.put("msgtype", "text");

        JSONObject contentjsonObject = new JSONObject();
        contentjsonObject.put("content", message);
        jsonObject.put("text", contentjsonObject);

        String access_token = getAccessToken(appid,appsecret);
        if(access_token == null){
            log.error("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx获取accessToken失败xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            return null;
        }
        JSONObject res0 = WxInfoUtils.sendMessageToUser(access_token, this.weixinurl, jsonObject.toJSONString());
        log.warn("发送结果{}",res0);
        if(res0 == null){
            log.error("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx调用发送接口失败xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        }else{
            Long errcode = res0.getLong("errcode");
            if (errcode!= null && !("".equals(errcode))&& errcode != 0) {
                log.error("主动给用户推送新消息请求失败,errcode:{}",errcode);
                return null;
            }
        }
        return res0;
    }


    private String getAccessToken(String appid,String appsecret){
        SessionCache sessionCache = CacheManager.getSessionCache();
        String access_token = null;
        Object globalAccessToken = sessionCache.getStr(GLOBALACCESSTOKEN);
        if (globalAccessToken == null) {
            JSONObject accessTokenJson = WxInfoUtils.getGlobalAccessToken(appid, appsecret, weixinurl);
            if(accessTokenJson == null){
                log.error("获取accessToken失败");
                return null;
            }
            access_token = accessTokenJson.getString("access_token");
            sessionCache.setStr(GLOBALACCESSTOKEN, access_token, GLOBAL_ACCESS_TOKEN_TIMEOUT);
        }else{
            access_token = globalAccessToken.toString();
        }
        return access_token;
    }


    @Override
    public int bindPhoneNumber(String openId,String phoneNum) {
        WechatUserInfo wechatUserInfo = wechatUserInfoDao.findByOpenId(openId);
        if (wechatUserInfo == null){
            return -1;
        }
        wechatUserInfo.setPhoneNum(phoneNum);
        int result = wechatUserInfoDao.updateWechatUserInfo(wechatUserInfo);
        return result;
    }

    @Override
    public WechatUserInfo findByOpenId(String openId) {
        WechatUserInfo wechatUserInfo = wechatUserInfoDao.findByOpenId(openId);
        return wechatUserInfo;
    }

    @Override
    public WechatUserInfo findByPhoneNum(String phoneNum) {
        WechatUserInfo wechatUserInfo = wechatUserInfoDao.findByPhoneNum(phoneNum);
        return wechatUserInfo;
    }

    @Override
    public int sendMessage2Wechat(Integer online,JSONArray phoneNums, JSONObject message, String appid, String appsecret) {
        int failsend = 0;
        for(int i = 0 ; i <  phoneNums.size()  ; i++){
            String phoneNum = phoneNums.getString(i);
            WechatUserInfo wechatUserInfo = findByPhoneNum(phoneNum);
            if(wechatUserInfo == null){
                log.error("phoneNum is not correct");
                failsend ++;
                continue;
            }
            UserInfo userInfo = userInfoDao.findByPhone(phoneNum);
            if(userInfo == null){
                log.error("userInfo is not null，推送用户未注册，请联系管理员");
                continue;
            }

            final String openid = wechatUserInfo.getOpenId();
            Integer utype = userInfo.getUtype();

            new Thread(new Runnable(){
                @Override
                public void run() {
//                    JSONObject jsonObject = sendMessage(openid,message,appid,appsecret);

                    JSONObject jsonObject = sendTmpMessage(utype,online,openid,template_msg_id,appid,appsecret,message);
                    if(jsonObject != null){
                        log.warn("send result：{}",jsonObject.toJSONString());
                    }else{
                        log.error("xxxx send wechat fail");
                    }

                }
            }).start();

        }
        return failsend;
    }

    /*
    @Override
    public int sendMessage2Wechat(JSONArray phoneNums, JSONObject params, String tmpId, String appid, String appsecret) {
        int failsend = 0;
        for(int i = 0 ; i <  phoneNums.size()  ; i++){
            String phoneNum = phoneNums.getString(i);

            WechatUserInfo wechatUserInfo = wechatUserInfoDao.findByPhoneNum(phoneNum);
            if(wechatUserInfo == null){
                log.error("----------------手机号:{}未绑定微信公众号--------------",phoneNum);
                continue;
            }
            String openId = wechatUserInfo.getOpenId();

            JSONObject jsonObject = sendTmpMessage(openId,tmpId,appid,appsecret,params);
            if(jsonObject != null){
                log.warn("send result：{}",jsonObject.toJSONString());
            }else{
                log.error("xxxx send wechat fail");
            }

            new Thread(new Runnable(){
                @Override
                public void run() {
                    JSONObject jsonObject = sendTmpMessage(openId,tmpId,appid,appsecret,params);
                    if(jsonObject != null){
                        log.warn("send result：{}",jsonObject.toJSONString());
                    }else{
                        log.warn("xxxx send wechat fail");
                    }

                }
            }).start();

        }
        return failsend;
    }
    */

    /**
     * 发送模板消息
     * @param openId
     * @param templateId
     * @param appid
     * @param appsecret
     * @param params
     * @return
     */
    public JSONObject sendTmpMessage(Integer utype,Integer online,String openId,String templateId,String appid,String appsecret,JSONObject params){
        TemplateMessage template = new TemplateMessage();
        template.setTemplateId(templateId);
        template.setToUser(openId);
        template.setUrl("https://www.101store.shop");
        List<TemplateParams> paramsList=new ArrayList<TemplateParams>();


        String path = String.format(xcx_path,utype+"",online+"");
        log.warn("--------------------------------小程序跳转路径:{}",path);

        JSONObject miniprogram = new JSONObject();
        miniprogram.put("appid",xcx_appid);
        miniprogram.put("path",path);//小程序跳转路径
        template.setMiniprogram(miniprogram);

        /**
         {{first.DATA}}
         订单号：{{keyword1.DATA}}
         订单概要：{{keyword2.DATA}}
         所属会员：{{keyword3.DATA}}
         会员手机：{{keyword4.DATA}}
         配送地址：{{keyword5.DATA}}
         {{remark.DATA}}
         */

        paramsList.add(new TemplateParams("first",params.getString("title"),"#000000"));
        paramsList.add(new TemplateParams("keyword1",params.getString("orderSn"),"#000000"));
        paramsList.add(new TemplateParams("keyword2",params.getString("subject"),"#000000"));
        paramsList.add(new TemplateParams("keyword3",params.getString("receiveName"),"#000000"));
        paramsList.add(new TemplateParams("keyword4",params.getString("phoneNum"),"#000000"));
        paramsList.add(new TemplateParams("keyword5",params.getString("address"),"#000000"));
        String remark = params.getString("remark");
        remark = remark==null?"":remark;
        paramsList.add(new TemplateParams("remark",remark,"#000000"));
        template.setTemplateParamList(paramsList);


        String access_token = getAccessToken(appid,appsecret);
        if(access_token == null){
            log.error("获取accessToken失败");
            return null;
        }


        JSONObject res0 = WxInfoUtils.sendTmpMessageToUser(access_token, this.weixinurl, template.toJSON());
        log.warn("发送结果{}",res0);
        if(res0 == null){
            log.error("调用发送接口失败");
        }else{
            Long errcode = res0.getLong("errcode");
            if (errcode!= null && !("".equals(errcode))&& errcode != 0) {
                log.error("主动给用户推送模板消息请求失败,errcode:\n"+errcode);
                return null;
            }
        }

        return res0;
    }



    @Autowired
    private UserSchoolDormDao userSchoolDormDao;

    @Autowired
    private UserSchoolDormManageDao userSchoolDormManageDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public void notify2send(OrderCommon orderCommon) {
        if(orderCommon == null){
            log.error("订单不能为空");
            return;
        }

        JSONArray phoneNums = new JSONArray();

        Long dormId = orderCommon.getDormId();
        UserSchoolDorm userSchoolDorm = userSchoolDormDao.findByDormId(dormId);
        if(userSchoolDorm != null) {
            String userId = userSchoolDorm.getUserId();
            UserInfo userInfo = userInfoDao.findByUserId(userId);

            if(userInfo == null){
                log.error("xxxxxxxxxxxxxxxxx找不到小区楼/宿舍管理员 userxxxxxxxxxxxxxxxxxxx");
            }
            String phoneNum = userInfo.getPhone();
            phoneNums.add(phoneNum);
        }else{
            log.error("xxxxxxxxxxxxxxxxx找不到小区楼/宿舍管理员xxxxxxxxxxxxxxxxxxx");
        }

        UserSchoolDormManage userSchoolDormManage = userSchoolDormManageDao.findByDormId(dormId);
        if(userSchoolDormManage != null){
            String userId = userSchoolDormManage.getUserId();
            UserInfo userInfo = userInfoDao.findByUserId(userId);
            if(userInfo == null){
                log.error("xxxxxxxxxxxxxxxxx找不到小配送员xxxxxxxxxxxxxxxxxxx");
            }
            String phoneNum = userInfo.getPhone();
            phoneNums.add(phoneNum);
        }else{
            log.error("xxxxxxxxxxxxxxxxx找不到小配送员xxxxxxxxxxxxxxxxxxx");
        }


        JSONObject message  = new JSONObject();
        message.put("title","下单通知");
        message.put("orderSn",orderCommon.getOrderSn());
        String subject = orderCommon.getSchoolName()+ orderCommon.getDormName() + orderCommon.getReceiverName() + "支付了" + orderCommon.getOrderAmount() + "购买商品";
        message.put("subject",subject);
        message.put("receiveName", orderCommon.getReceiverName());
        message.put("phoneNum", orderCommon.getPhoneNum());
        message.put("address", orderCommon.getProvince() + orderCommon.getCity() + orderCommon.getDistrict() + orderCommon.getAddress());
        message.put("remark", orderCommon.getRemark());


        /**
         {
             "online":0,
             "message":{
             "title":"下单通知",
             "orderSn":"100000100101",
             "subject":"广州大学小明购买方便面两个",
             "receiveName":"黄晓明",
             "phoneNum":"1008610086",
             "address":"广东省广州市番禺区广州大学美丽居101",
             "remark":"留言：请准时配送"
             },
             "notifyPhoneNums":[15626222192]
         }
         */

        //发送消息
        this.sendMessage2Wechat(OrderType.ONLINE.value,phoneNums,message,appid,appsecret);

    }

    @Override
    public void notify2send(OrderCommonOffLine orderCommonOffLine) {
        if(orderCommonOffLine == null){
            log.error("订单不能为空");
            return;
        }

        JSONArray phoneNums = new JSONArray();

        Long dormId = orderCommonOffLine.getDormId();
        UserSchoolDorm userSchoolDorm = userSchoolDormDao.findByDormId(dormId);
        if(userSchoolDorm != null) {
            String userId = userSchoolDorm.getUserId();
            UserInfo userInfo = userInfoDao.findByUserId(userId);

            if(userInfo == null){
                log.error("xxxxxxxxxxxxxxxxx找不到小区楼/宿舍管理员 userxxxxxxxxxxxxxxxxxxx");
            }
            String phoneNum = userInfo.getPhone();
            phoneNums.add(phoneNum);
        }else{
            log.error("xxxxxxxxxxxxxxxxx找不到小区楼/宿舍管理员xxxxxxxxxxxxxxxxxxx");
        }

        UserSchoolDormManage userSchoolDormManage = userSchoolDormManageDao.findByDormId(dormId);
        if(userSchoolDormManage != null){
            String userId = userSchoolDormManage.getUserId();
            UserInfo userInfo = userInfoDao.findByUserId(userId);
            if(userInfo == null){
                log.error("xxxxxxxxxxxxxxxxx找不到小配送员xxxxxxxxxxxxxxxxxxx");
            }
            String phoneNum = userInfo.getPhone();
            phoneNums.add(phoneNum);
        }else{
            log.error("xxxxxxxxxxxxxxxxx找不到小配送员xxxxxxxxxxxxxxxxxxx");
        }


        JSONObject message  = new JSONObject();
        message.put("title","下单通知");
        message.put("orderSn",orderCommonOffLine.getOrderSn());
        String subject = orderCommonOffLine.getSchoolName()+ orderCommonOffLine.getDormName() + orderCommonOffLine.getReceiverName() + "选择线下支付，" + orderCommonOffLine.getOrderAmount() + "购买商品";
        message.put("subject",subject);
        message.put("receiveName", orderCommonOffLine.getReceiverName());
        message.put("phoneNum", orderCommonOffLine.getPhoneNum());
        message.put("address", orderCommonOffLine.getProvince() + orderCommonOffLine.getCity() + orderCommonOffLine.getDistrict() + orderCommonOffLine.getAddress());
        message.put("remark", orderCommonOffLine.getRemark());

        //发送消息
        this.sendMessage2Wechat(OrderType.OFFLINE.value,phoneNums,message,appid,appsecret);
    }

    @Override
    public WechatUserInfo findByUnionId(String unionId) {
        WechatUserInfo wechatUserInfo = wechatUserInfoDao.findByUnionId(unionId);
        return wechatUserInfo;
    }
}
