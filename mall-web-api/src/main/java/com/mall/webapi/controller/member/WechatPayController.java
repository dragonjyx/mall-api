package com.mall.webapi.controller.member;

import com.alibaba.fastjson.JSONObject;
import com.java.redis.util.UniqueIdGenerate;
import com.java.response.JsonResult;
import com.java.utils.code.MD5Util;
import com.java.utils.date.DateUtils;
import com.mall.model.*;
import com.mall.params.status.*;
import com.mall.service.*;
import com.mall.webapi.controller.BaseController;
import com.mall.webapi.controller.pay.wechat.IWxPayConfig;
import com.mall.webapi.controller.pay.wechat.WXPay;
import com.mall.webapi.controller.pay.wechat.WXPayUtil;
import com.mall.webapi.controller.pay.wechat.WechatUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 微信支付
 */
@Slf4j
@Controller
@RequestMapping("wechat")
public class WechatPayController extends BaseController {


    //微信账单下载url
    @Value("${wechat.wx_downloadbill_url}")
    private String wxBillUrl;

    @Value("${wechat.xcx_appid}")
    private String WX_XCX_APPID;//小程序 服务号的应用号

    @Value("${wechat.xcx_appsecret}")
    private String WX_XCX_APP_SECRECT;//小程序 服务号的应用密码

    @Value("${wechat.mp_appid}")
    private String WX_MP_APPID;//公众号 服务号的应用号

    @Value("${wechat.mp_appsecret}")
    private String WX_MP_APP_SECRECT;//公众号 服务号的应用密码

    @Value("${wechat.wx_token}")
    private String WX_TOKEN;//服务号的配置token

    @Value("${wechat.mch_id}")
    private String WX_MCH_ID;//商户号

    @Value("${wechat.mch_appid}")
    private String MCH_APP_ID;//商户号appid

    @Value("${wechat.api_key}")
    private String WX_API_KEY;//API密钥

    @Value("${wechat.sign_type}")
    private String WX_SIGN_TYPE;//签名加密方式

    @Value("${wechat.cert_path}")
    private String WX_CERT_PATH;//微信支付证书存放路径地址

    @Value("${wechat.bill_path}")
    private String WX_BILL_PATH;

    @Value("${wechat.pay_notify_url}")
    private String wechat_pay_notify_url;

    @Value("${wechat.wx_ip}")
    private String WX_IP;//发起支付服务器IP

    @Value("${order_share_day}")
    private int order_share_day;

    //微信支付类型
    private String trade_type="JSAPI" ;

    private String SUCCESS="SUCCESS" ;


    private WXPay wxPay = null;


    @Autowired
    private OrderService orderService;

    @Autowired
    private WeChatService weChatService;


    private static String TRADE_NO_KEY = "TRADE_NO_KEY";


    /**
     * 微信支付
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "pay",method = RequestMethod.POST)
    public JsonResult wechatPay(HttpServletRequest request, @RequestBody JSONObject params){

        String token  = request.getParameter("token");
        String openId = getOpenId(token);

        String orderSn = params.getString("orderSn");
        if(StringUtils.isEmpty(orderSn)){
            return JsonResult.fail("请提交订单信息");
        }

        OrderCommon orderCommon = orderService.findByOrderSn(orderSn);
        if(orderCommon == null){
            return JsonResult.fail("订单异常");
        }


        //1.生成订单流水号
        String tradeNo = UniqueIdGenerate.getSnowflakeId(TRADE_NO_KEY,32);
        orderCommon.setTradeNo(tradeNo);
        int result = orderService.updateOrderBySn(orderCommon);
        if(result != 1){
            return JsonResult.fail("订单生成流水号异常");
        }

        String out_trade_no = tradeNo;
        String subject = orderCommon.getRemark()+ "订单支付";
        Integer total_amount = orderCommon.getOrderAmount().multiply(new BigDecimal(100)).intValue();
        String body = subject + "，支付" + orderCommon.getOrderAmount() + "元";
        String ip = WX_IP;


        //2.调用微信统一下单
        String nonceStr = WechatUtils.createNoncestr();
        log.warn("生成nonceStr:{}",nonceStr);
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("appid", WX_XCX_APPID); // APPid
        parameters.put("mch_id", WX_MCH_ID); // 商户id
        parameters.put("openid",openId);
        parameters.put("nonce_str",nonceStr);
        parameters.put("body", body);
        parameters.put("out_trade_no", out_trade_no);//商户订单号
        parameters.put("total_fee", total_amount+"");//订单总金额，单位为分
        parameters.put("spbill_create_ip", ip);//终端IP
        parameters.put("notify_url", wechat_pay_notify_url);//通知地址
        parameters.put("trade_type", trade_type);//交易类型
        parameters.put("sign_type", WX_SIGN_TYPE);//签名类型

        try {
            wxPay = new WXPay(new IWxPayConfig(this.WX_XCX_APPID,this.WX_API_KEY,this.WX_MCH_ID,null));
        } catch (Exception e) {
            log.error("wxPay 异常：",e);
        }

        //3.下单成功，返回二维码
        Map<String, String> response = null;
        try {
            response = wxPay.unifiedOrder(parameters);
            log.warn("统一下单：response:{}\n",JSONObject.toJSONString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String returnCode = response.get("return_code");
        if (!SUCCESS.equals(returnCode)) {
            return JsonResult.fail("下单失败");
        }
        String resultCode = response.get("result_code");
        if (!SUCCESS.equals(resultCode)) {
            return JsonResult.fail("下单失败");
        }
        String prepay_id = response.get("prepay_id");
        if (prepay_id == null) {
            return JsonResult.fail("下单失败");
        }

        //String sign = response.get("sign");
        String timeStamp = "" + new Date().getTime()/1000;
        String nonce_str = WXPayUtil.generateNonceStr();

        /*
        String signUrlStr = "appId=%s&nonceStr=%s&package=%s&signType=%s&timeStamp=%s&key=%s";
        String signUrl = String.format(signUrlStr,this.WX_XCX_APPID,nonce_str,"prepay_id="+prepay_id,this.WX_SIGN_TYPE,timeStamp+"",this.WX_API_KEY);
        log.warn("signUrl:{}",signUrl);
        String paySign = MD5Util.md5Encrypt32(signUrl).toUpperCase();
        */

        //返回给小程序
        Map payRequest = new HashMap();
        payRequest.put("appId",this.WX_XCX_APPID);
//        payRequest.put("sub_appid",this.WX_XCX_APPID);
        payRequest.put("timeStamp",timeStamp);
        payRequest.put("nonceStr",nonce_str);
        payRequest.put("package","prepay_id="+prepay_id);
        payRequest.put("signType",this.WX_SIGN_TYPE);
        try {
            String paySign = WXPayUtil.generateSignature(payRequest,this.WX_API_KEY);
            payRequest.put("paySign",paySign);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("签名异常",e);
            return JsonResult.fail("签名异常");
        }
        payRequest.put("mch_id",this.WX_MCH_ID);
        payRequest.put("prepayid",prepay_id);
        payRequest.put("api_key",this.WX_API_KEY);
        log.warn("pay sign:{}",JSONObject.toJSONString(payRequest));

        return JsonResult.success(payRequest);
    }



    /**
     * 微信异步通知
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "notify", method = RequestMethod.POST)
    public String wechatNotify(HttpServletRequest request, HttpServletResponse response) {
        log.info("进入微信支付异步通知");
        String resXml="";
        try{
            //
            InputStream is = request.getInputStream();
            //将InputStream转换成String
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                log.error("微信支付通知异常",e);
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resXml=sb.toString();
            log.info("微信支付异步通知请求包: {}", resXml);

            return payBack(resXml);
        }catch (Exception e){
            log.error("微信支付回调通知失败",e);
            String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            return result;
        }
    }


    private String payBack(String notifyData) {
        log.info("payBack() start, notifyData={}", notifyData);
        String xmlBack="";
        Map<String, String> notifyMap = null;
        try {
            try {
                wxPay = new WXPay(new IWxPayConfig(this.WX_XCX_APPID,this.WX_API_KEY,this.WX_MCH_ID,null));
            } catch (Exception e) {
                e.printStackTrace();
            }

            notifyMap = WXPayUtil.xmlToMap(notifyData);

            log.error("---------------------------------------------");
            log.error("notifyMap:{}",notifyMap.toString());
            log.error("---------------------------------------------");

            // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
            String return_code = notifyMap.get("return_code");//状态
            if(!return_code.equals(SUCCESS)){
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[支付失败]]></return_msg>" + "</xml> ";
                return xmlBack;
            }


            if (wxPay.isPayResultNotifySignatureValid(notifyMap)) {
                // 签名正确,进行处理。

                String out_trade_no = notifyMap.get("out_trade_no");//订单号

                if (out_trade_no == null) {
                    log.error("微信支付回调失败订单号: {}", notifyMap);
                    xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                    return xmlBack;
                }

                // 业务逻辑处理 ****************************
                log.info("微信支付回调成功订单号: {}", notifyMap);


                OrderCommon orderCommon = orderService.findByTradeNo(out_trade_no);
                if(orderCommon == null){
                    log.error("微信支付回调失败，订单流水号异常: {}", notifyMap);
                    xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[订单流水号异常]]></return_msg>" + "</xml> ";
                    return xmlBack;
                }

                orderCommon.setStatus(OrderStatus.PAYED_NO_DELIVER.value);//支付成功，未派送
                orderCommon.setPayType(PayType.WECHAT.value);//支付类型
                orderCommon.setPayTime(new Date());//支付时间

                int result = orderService.updateOrderBySnAndShare(orderCommon);
                if(result != 1){
                    log.error("微信支付回调失败，订单更新状态异常: {}", notifyMap);
                    xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[订单更新异常]]></return_msg>" + "</xml> ";
                    return xmlBack;
                }


                //通知楼长，配送员
                weChatService.notify2send(orderCommon);


                xmlBack = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[SUCCESS]]></return_msg>" + "</xml> ";
                return xmlBack;
            } else {
                log.error("微信支付回调通知签名错误");
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                return xmlBack;
            }
        } catch (Exception e) {
            log.error("微信支付回调通知失败",e);
            xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        return xmlBack;
    }



    /**
     * 退款
     * @param request
     * @return
     */
    @RequestMapping(value = "refund", method = RequestMethod.POST)
    public JsonResult wechatRefund(HttpServletRequest request,@RequestBody JSONObject params) {
        String orderSn = params.getString("orderSn");//获取订单号
        if (StringUtils.isEmpty(orderSn)) {
            return JsonResult.fail("订单号不能为空");
        }
        //TODO 查询订单
        OrderCommon orderCommon = orderService.findByOrderSn(orderSn);
        if(orderCommon.getRefundStatus() == RefundStatus.REFUND.value){

        }

        Date payTime = orderCommon.getPayTime();

        Date day = DateUtils.offectTime(order_share_day*-1);

        if(day.getTime() > payTime.getTime()){
            return JsonResult.fail("订单已经超过"+ order_share_day + "天，不能进行退款");
        }

        String tradeNo = orderCommon.getTradeNo();//支付流水号
        BigDecimal refundAmount = orderCommon.getAmount();
        if(refundAmount == null){
            return JsonResult.fail("订单金额异常");
        }
        String amount = refundAmount.doubleValue() * 100 + "";

        try {
            wxPay = new WXPay(new IWxPayConfig(this.WX_XCX_APPID,this.WX_API_KEY,this.WX_MCH_ID,WX_CERT_PATH));
        } catch (Exception e) {
            log.error("wxPay 异常：",e);
        }


        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("appid", WX_XCX_APPID); // APPid
        parameters.put("mch_id", WX_MCH_ID); // 商户id
        parameters.put("nonce_str", WechatUtils.createNoncestr());
        parameters.put("out_trade_no", tradeNo);//商户订单号
        parameters.put("total_fee", amount);//订单总金额，单位为分
        parameters.put("refund_fee", amount);//退款金额

        try {
            Map<String, String> response = wxPay.refund(parameters);
            String returnCode = response.get("return_code");
            if (!SUCCESS.equals(returnCode)) {
                return JsonResult.fail("退款失败");
            }
            String resultCode = response.get("result_code");
            if (!SUCCESS.equals(resultCode)) {
                return JsonResult.fail("退款失败");
            }

            //TODO 修改订单状态
            orderCommon.setRefundStatus(RefundStatus.REFUND.value);//退款订单
            int result = orderService.updateOrderBySnAndShareRefund(orderCommon);
            if (result != 1){
                return JsonResult.fail("退款成功，修改订单状态失败.请联系管理员处理！");
            }
            return JsonResult.fail("退款成功");
        } catch (Exception e) {
            log.error("退款失败",e);
            return JsonResult.fail("退款失败");
        }
    }


    @Autowired
    private UserService userService;

    @Autowired
    private UnionIdService unionIdService;

    @Autowired
    private AccountService accountService;

    /**
     * 提现
     * @param request
     * @return
     */
    @RequestMapping(value = "widthdraw", method = RequestMethod.POST)
    public JsonResult wechatWithdraw(HttpServletRequest request,@RequestBody JSONObject params) {
        BigDecimal widthdrawAmount = params.getBigDecimal("widthdrawAmount");
        if(widthdrawAmount == null){
            return JsonResult.fail("请输入提现金额");
        }

        String token  = request.getParameter("token");
        String openId = getOpenId(token);
        UnionIds unionIds = unionIdService.findByOpenId(openId);
        if(unionIds == null){
            return JsonResult.fail("用户未绑定unionid");
        }

        User user = userService.findByUnionId(unionIds.getUnionId());
        if(user == null){
            return JsonResult.fail("用户异常");
        }
        String userId = user.getUid();
        Account account = accountService.findByUserId(userId);

        if(account == null){
            return JsonResult.fail("用户账户异常");
        }

        BigDecimal money = account.getMoney();
        if(money.compareTo(widthdrawAmount)<0){
            return JsonResult.fail("账户余额不足");
        }

        Integer widthdrawMoney = widthdrawAmount.multiply(new BigDecimal(100)).intValue();
        String body = userId + "申请付款" + widthdrawAmount.toString() + "元";
        String ip = WX_IP;


        try {
            wxPay = new WXPay(new IWxPayConfig(this.WX_XCX_APPID,this.WX_API_KEY,this.WX_MCH_ID,WX_CERT_PATH));
        } catch (Exception e) {
            log.error("wxPay 异常：",e);
        }
        String tradeNo = UniqueIdGenerate.getSnowflakeId(TRADE_NO_KEY,32);

        AccountBill accountBill = new AccountBill();
        accountBill.setAccountId(account.getAid());
        accountBill.setAmount(widthdrawAmount);
        accountBill.setBillStatus(BillStatus.INIT.value);
        accountBill.setCreateTime(new Date());
        accountBill.setIsDelete(false);
        accountBill.setStatus(AccountBillStatus.FREE.value);
        accountBill.setType(AccountBillType.WIDTHDRAW.value);
        accountBill.setUpdateTime(new Date());
        accountBill.setOrderSn(tradeNo);
        //生成体现记录账单
        int result = accountService.generateAccountBill(accountBill);
        if(result != 1){
            return JsonResult.fail("生成体现账单失败");
        }


        //微信请求企业付款
        String nonceStr = WechatUtils.createNoncestr();
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("mch_appid",this.MCH_APP_ID);
        parameters.put("mchid",this.WX_MCH_ID);
        parameters.put("nonce_str",nonceStr);
        parameters.put("partner_trade_no",tradeNo);
        parameters.put("openid",openId);
        parameters.put("check_name","NO_CHECK");
        parameters.put("amount",widthdrawMoney+"");
        parameters.put("desc",body+"");
        parameters.put("spbill_create_ip",ip);

        try {
            Map<String, String> response = wxPay.widthdraw(parameters);

            String returnCode = response.get("return_code");
            if (!SUCCESS.equals(returnCode)) {
                return JsonResult.fail("企业付款失败");
            }
            String resultCode = response.get("result_code");
            if (!SUCCESS.equals(resultCode)) {
                return JsonResult.fail("企业付款失败");
            }

            String partner_trade_no = response.get("partner_trade_no");
//            String payment_no       = response.get("payment_no");


            //生成提现账单，并且修改账单金额
            BigDecimal leftMoeny = money.subtract(widthdrawAmount);
            account.setMoney(leftMoeny);
            account.setUpdateTime(new Date());
            result = accountService.widthdrawMoney(account,partner_trade_no);

            if(result !=1){
                return JsonResult.fail("提现成功，提现扣款失败");
            }

            return JsonResult.success("提现成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("企业付款失败:",e);
            return JsonResult.fail("提现失败");
        }
    }
}
