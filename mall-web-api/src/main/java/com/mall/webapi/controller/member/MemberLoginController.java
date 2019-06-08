package com.mall.webapi.controller.member;

import com.alibaba.fastjson.JSONObject;
import com.java.redis.util.UniqueIdGenerate;
import com.java.response.JsonResult;
import com.java.utils.code.AES_CBC_PKCS7;
import com.java.utils.code.Base64Util;
import com.java.utils.http.HttpUtil;
import com.mall.model.Member;
import com.mall.service.MemberService;
import com.mall.service.UnionIdService;
import com.mall.webapi.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * 会员授权登录
 */
@Slf4j
@Controller
@RequestMapping("member")
public class MemberLoginController extends BaseController {


    @Autowired
    private MemberService memberService;

    @Value("${wechat.xcx_appid}")
    private String xcxAppid;

    @Value("${wechat.xcx_appsecret}")
    private String xcxAppsecret;

    @Value("${is_test_wechat}")
    private boolean is_test_wechat;

    @Value("${wechat.authorization.code_url}")
    private String authorization_code_url;

    @Autowired
    private UnionIdService unionIdService;

    /**
     * 获取open id
     * @param request
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "check-login",method = RequestMethod.POST)
    public JsonResult bingWxXCXUserInfo(HttpServletRequest request,@RequestBody JSONObject params){
        log.warn("请求params:{}",params.toJSONString());
        String code      = params.getString("code");
        //用于解密unionId
        String encryptedData = params.getString("encryptedData");
        String iv        = params.getString("iv");

        if(StringUtils.isEmpty(code)){
            return JsonResult.fail("code is null");
        }
        if(StringUtils.isEmpty(encryptedData)){
            return JsonResult.fail("encryptedData is null");
        }
        if(StringUtils.isEmpty(iv)){
            return JsonResult.fail("iv is null");
        }


        String url = String.format(authorization_code_url,this.xcxAppid,this.xcxAppsecret,code);
        String result = HttpUtil.doGet(url,0,null);
        log.warn("request by code :{}",result);

        if(StringUtils.isEmpty(result)) {
            return JsonResult.fail("微信登录失败");
        }

        try {
            JSONObject resultJson = JSONObject.parseObject(result);
            log.warn("resultJson:{}",resultJson.toJSONString());
            Integer errcode = resultJson.getInteger("errcode");
            if(errcode != null){
                if(errcode != 0){
                    return JsonResult.fail(resultJson.getString("errmsg"));
                }
            }

            String openId     = resultJson.getString("openid");
            String sessionKey = resultJson.getString("session_key");
            String unionId    = resultJson.getString("unionid");

            byte[] content = Base64Util.decode(encryptedData);
            byte[] aesKey  = Base64Util.decode(sessionKey);
            byte[] ivByte  = Base64Util.decode(iv);

            String nickName  = null;
            String avatarUrl = null;
            Integer gender   = null;
            String country   = "";
            String province  = "";
            String city      = "";
            try {
                byte[] descryptBytes = AES_CBC_PKCS7.decrypt(content,aesKey,ivByte);
                String contentJson = new String(descryptBytes);
                log.warn("解密内容：{}",contentJson);

                JSONObject userInfoJson = JSONObject.parseObject(contentJson);
                //String unionId     = userInfoJson.getString("unionId");

                if(StringUtils.isEmpty(unionId)){
                    return JsonResult.fail("unionId is null");
                }

                nickName  = userInfoJson.getString("nickName");
                avatarUrl = userInfoJson.getString("avatarUrl");
                gender    = userInfoJson.getInteger("gender");
                country   = userInfoJson.getString("country");
                province  = userInfoJson.getString("province");
                city      = userInfoJson.getString("city");

            }catch (Exception e){
                log.error("解密失败",e);
            }

            //加入绑定
            int res = unionIdService.bindUnionIdAndOpenId(unionId,openId);
            if(res != 1){
                return JsonResult.fail("bind unionid fail");
            }

            String token  = UniqueIdGenerate.getSnowflakeId("LOGIN_TOEKN",32);
            log.warn("登录token:{}",token);
            saveSession(token,openId);//登录成功

            Member member = memberService.findByOpenId(openId);

            if(member == null){
                member = new Member();
                member.setAddress(country + " " + province + " " + city);
                member.setGender(gender);
                member.setAvatar(avatarUrl);
                member.setNickname(nickName);
                member.setUnionId(unionId);
                member.setLastLoginIp(request.getRemoteHost());
                res = memberService.addNewMemberInfo(member);
            }else{
                member.setAddress(country + " " + province + " " + city);
                member.setGender(gender);
                member.setAvatar(avatarUrl);
                member.setNickname(nickName);
                member.setUnionId(unionId);
                res = memberService.updateMemberInfo(member);
            }
            if(res != 1){
                return JsonResult.fail("绑定微信信息失败");
            }
            return JsonResult.success(token);//返回到前端
        }catch (Exception e){
            log.error("认证异常",e);
            return JsonResult.fail("微信登录失败，解释结果异常");
        }

    }

}
