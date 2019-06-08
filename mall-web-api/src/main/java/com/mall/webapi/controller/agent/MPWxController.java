package com.mall.webapi.controller.agent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.java.response.JsonResult;
import com.mall.model.WechatUserInfo;
import com.mall.service.WeChatService;
import com.mall.webapi.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 微信公众号绑定，消息推送
 */
@Slf4j
@Controller
@RequestMapping("wechat/mp")
public class MPWxController extends BaseController {

    @Value("${wechat.verfy_token}")
    private String token;

    @Value("${wechat.mp_appid}")
    private String appid;

    @Value("${wechat.mp_appsecret}")
    private String appsecret;

    @Value("${wechat.authorize_url}")
    private String authorize_url;

    @Value("${wechat.redirect_proxy_url}")
    private String redirect_url;

    @Value("${wechat.global_access_token}")
    private String global_access_token;

    @Value("${wechat.order_notify.template_msg_id}")
    private String templateMsgId0;

    @Autowired
    private WeChatService weChatService;


    /*
     * 校验测试号
     * @param request
     * @return
     */
    @RequestMapping(value="message/receive",method=RequestMethod.GET)
    public void verify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        log.warn("echostr:{},\n nonce:{},\n timestamp：{}, \n signature:{} ,\n token:{}",echostr,nonce,timestamp,signature,token);

        PrintWriter out = response.getWriter();
        out.print(echostr);
        out.close();
    }

    /**
     * 接收消息
     * @param request
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value="message/receive",method=RequestMethod.POST)
    public String messageReceive(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        StringBuffer sb = new StringBuffer();
        byte[] b = new byte[4096];
        for(int n;(n=inputStream.read(b))!= -1;){
            sb.append(new String(b,0,n,"UTF-8"));
        }
        String xml = sb.toString();
        log.warn("****************************************************************");
        log.warn("xml:\n{}",xml);
        log.warn("****************************************************************");
        return xml;
    }


    /**
     * 微信公众号认证入口
     * @param phoneNum
     * @return
     */
    @RequestMapping(value="index/{phoneNum}",method = RequestMethod.GET)
    public String wechatMPIndex(@PathVariable("phoneNum") String phoneNum, Map map) throws UnsupportedEncodingException {
        //查询是否已经绑定

        WechatUserInfo wechatUserInfo = weChatService.findByPhoneNum(phoneNum);
        if(wechatUserInfo != null){
            map.put("result","绑定成功");
            return "bind-result";
        }

        String red_url  = URLEncoder.encode(redirect_url,Charset.defaultCharset().name());
        String auth_url = String.format(authorize_url,appid,red_url,"snsapi_base",phoneNum);

        return "redirect:" + auth_url;
    }



    /**
     * 微信授权
     * https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx862ea6971d2f690b
     * &secret=0e3ba55e2d7b6d70187f337aa561fc19&
     * code=001fixeK10YK2a0Or5fK1TZDeK1fixem&grant_type=authorization_code
     */
    @RequestMapping(value="callback",method={RequestMethod.GET,RequestMethod.POST})
    public String bindOpenId(HttpServletRequest request, Map map){
        String code  = request.getParameter("code");//授权code
        String phoneNum = request.getParameter("state");//回传状态 手机号码


        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + appid +
                "&secret=" + appsecret +
                "&code=" + code +
                "&grant_type=authorization_code";


        //String url = String.format(global_access_token,appid,appsecret);

        WechatUserInfo wechatUserInfo = weChatService.bindWechatUserInfo(url,phoneNum,appid,appsecret);

        if(wechatUserInfo == null){
            map.put("result","绑定失败");
            return "bind-result";
        }

        request.getSession().setAttribute("openid",wechatUserInfo.getOpenId());
        request.getSession().setAttribute("wechatUserInfo",wechatUserInfo);

        map.put("result","绑定成功");
        return "bind-result";
    }






    /**
     * 发送消息
     * @param request
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/order-notify/send",method=RequestMethod.POST)
    public JsonResult sendOrderNotifyMsg(HttpServletRequest request, @RequestBody JSONObject params){
        log.warn("通知参数:{}",params.toJSONString());
        JSONArray phoneNums = params.getJSONArray("notifyPhoneNums");
        JSONObject message  = params.getJSONObject("message");
        Integer    online   = params.getInteger("online");
        if(online == null){
            online = 0;
        }

        if(message == null){
            return JsonResult.fail("message is empty or null");
        }
        if(phoneNums == null || phoneNums.size() == 0){
            return JsonResult.fail("phoneNums is empty or null");
        }
        try {
            int result = weChatService.sendMessage2Wechat(online,phoneNums,message,appid,appsecret);
            if (result == 0){
                return JsonResult.success("all send success");
            }
            return JsonResult.success(result + " message send fail");
        }catch (Exception e){
            return JsonResult.success("send message send fail");
        }

    }







}
