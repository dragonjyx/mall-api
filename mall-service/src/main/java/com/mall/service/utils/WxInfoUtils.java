package com.mall.service.utils;

import com.alibaba.fastjson.JSONObject;
import com.java.utils.http.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>微信接入<p>
 * <p>@author DRAGON<p>
 * <p>@date 2017年1月6日<p>
 * <p>@version 1.0<p>
 */
public class WxInfoUtils {

	/** 日志 **/
	private static Logger logger = LoggerFactory.getLogger(WxInfoUtils.class);

	// 第一步：用户同意授权，获取code
	private final static String wxCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?"
			+ "appid=%s&redirect_uri=%s" + "&response_type=code&scope=snsapi_base" + "&state=%s#wechat_redirect";

	// 第二步：通过code换取网页授权access_token
	private final static String wxAccessTokenUrl = "%s/sns/oauth2/access_token?"
			+ "appid=%s&secret=%s&code=%s&grant_type=authorization_code";

	// 第三步：刷新access_token（如果需要）
	private final static String wxRefreshAccessTokenUrl = "%s/sns/oauth2/refresh_token?"
			+ "appid=%s&grant_type=refresh_token&refresh_token=%s";

	// 第四步：拉取用户信息(需scope为 snsapi_userinfo)
	private final static String wxUserInfoUrl = "%s/sns/userinfo?" + "access_token=%s&openid=%s&lang=zh_CN";

	// ================
	// 全局access_token
	private final static String wxGlobalAccessTokenUrl = "%s/cgi-bin/token?"
			+ "grant_type=client_credential&appid=%s&secret=%s";
	// 用户基本信息
	private final static String wxBaseUserInfoUrl = "%s/cgi-bin/user/info?" + "access_token=%s&openid=%s&lang=zh_CN";

	// 微信公众平台创建菜单
	private final static String wxMenuUrl = "%s/cgi-bin/menu/create?access_token=%s";

	// 获取jsticket
	private final static String getJSticket = "%s/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

	// http服务
	private static HttpUtil http = null;

	// ======支付
	private final static String payBaseUrl = "https://api.mch.weixin.qq.com/";
	
	
	//=======公众号二维码
	//临时二维 30天
	private final static String temporaryCodeUrl = "%s/cgi-bin/qrcode/create?access_token=%s";
	
	//永久二维
	private final static String permanentCodeUrl = "%s/cgi-bin/qrcode/create?access_token=%s";
	
	//主动给用户推送新消息 （客服消息）
	private final static String sendMessageToNewUserUrl = "%s/cgi-bin/message/custom/send?access_token=%s";
	//主动给用户发送模板消息
	private final static String sendTemplateMessageToUserUrl = "%s/cgi-bin/message/template/send?access_token=%s";

	//会员提现
	private final static String memberWidthdrawURL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";


	/**
	 * 获取全局的用户信息
	 *
	 * @param access_token
	 * @param openid
	 * @return
	 */
	public static JSONObject getUserInfoByGlobalAccessToken(String access_token, String openid, String weixinurl) {
		logger.warn("获取基本用户信息");
		String url = String.format(wxBaseUserInfoUrl, weixinurl, access_token, openid);
		logger.warn("url:" + url);
		String result = null;
		try {
			result = HttpUtil.doGet(url,0,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (StringUtils.isEmpty(result)) {// 获取失败
			return null;
		}
		logger.warn("https:获取微信用户信息:{}", result);

		JSONObject jsonOjbect = JSONObject.parseObject(result);
		Long errcode = jsonOjbect.getLong("errcode");
		if (errcode != null) {
			logger.error("获取获取基本用户信息失败,errcode:{}", errcode);
			return null;
		}
		return jsonOjbect;
	}



	/**
	 * 获取全局的accessToken
	 *
	 * @param appid
	 * @param secret
	 * @return
	 */
	public static JSONObject getGlobalAccessToken(String appid, String secret, String weixinurl) {
		logger.info("获取全局的accessToken");
		String url = String.format(wxGlobalAccessTokenUrl, weixinurl, appid, secret);
		logger.info("url:" + url);
		String result = null;
		try {
			result = HttpUtil.doGet(url,0,null);
		} catch (Exception e) {
			e.printStackTrace();
 		}
		logger.info("获取access_token:{}", result);
		if (StringUtils.isEmpty(result)) {// 获取失败
			return null;
		}
		JSONObject jsonOjbect = JSONObject.parseObject(result);
		Long errcode = jsonOjbect.getLong("errcode");
		if (errcode != null) {
			logger.error("获取access_token失败,errcode:{}", errcode);
			return null;
		}
		return jsonOjbect;
	}


	/**
	 * 主动给用户推送新消息
	 * @param accessToken
	 * @param weixinurl
	 * @param json
	 * @return
	 */
	public static JSONObject sendMessageToUser(String accessToken, String weixinurl,String json){
		logger.info("主动给用户推送新消息:{}",json);
		String url = String.format(sendMessageToNewUserUrl, weixinurl , accessToken);
		String result = HttpUtil.doPostWithJSON(url, json , 0 ,null);
		logger.info("result:\n{}",result);
		if (result==null || "".equals(result)) {//获取失败
			logger.error("主动给用户推送新消息请求失败");
			return null;
		}
		JSONObject jsonOjbect = JSONObject.parseObject(result);
		return jsonOjbect;
	}


	/**
	 * 发送模板消息
	 * @return
	 */
	public static JSONObject sendTmpMessageToUser(String accessToken,String weixinurl,String json){
		logger.warn("sendTmpMessageToUser 主动给用户推送模板消息:{}",json);
		String url = String.format(sendTemplateMessageToUserUrl, weixinurl , accessToken);
		String result = HttpUtil.doPostWithJSON(url, json,0,null);
		logger.info("result:\n{}",result);
		if (result==null || "".equals(result)) {//获取失败
			logger.error("主动给用户推送模板消息请求失败");
			return null;
		}
		JSONObject jsonOjbect = JSONObject.parseObject(result);
		return jsonOjbect;
	}



	/**
	 * 自定义菜单
	 * @param accessToken
	 * @param json
	 * @return
	 */
	public static JSONObject createMenu(String accessToken,String weixinurl,String json){
		logger.info("创建微信公众号菜单:{}",json);
		String url = String.format(wxMenuUrl, weixinurl , accessToken);
		String result = HttpUtil.doPostWithJSON(url, json,0 ,null);
		logger.info("result:\n{}",result);
		if (result!=null && !("".equals(result))) {//获取失败
			logger.error("创建菜单请求失败");
			return null;
		}
		JSONObject jsonOjbect = JSONObject.parseObject(result);
		Long errcode = jsonOjbect.getLong("errcode");
		if (errcode!= null && !("".equals(errcode))&& errcode != 0) {
			logger.error("创建菜单请求失败,errcode:\n"+errcode);
			return null;
		}
		return jsonOjbect;
	}



}
