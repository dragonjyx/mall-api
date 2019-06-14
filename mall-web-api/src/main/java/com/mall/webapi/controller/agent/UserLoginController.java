package com.mall.webapi.controller.agent;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.java.response.JsonResult;
import com.java.validate.ServiceException;
import com.mall.model.*;
import com.mall.params.page.PageCondition;
import com.mall.service.UnionIdService;
import com.mall.service.UserService;
import com.mall.service.WeChatService;
import com.mall.webapi.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * 用户/管理员登录
 */
@Slf4j
@Controller
@RequestMapping("user")
public class UserLoginController extends BaseController {


    @Autowired
    private UserService userService;

    @Autowired
    private UnionIdService unionIdService;

    @Autowired
    private WeChatService weChatService;


    /**
     * 管理员/配送员登录入口
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "entry",method = RequestMethod.GET)
    public JsonResult userEntry(HttpServletRequest request){
        String token    = request.getParameter("token");
        String openId   = getOpenId(token);

        UnionIds unionIds = unionIdService.findByOpenId(openId);
        if(unionIds == null){
            return JsonResult.fail("用户未认证");
        }

        WechatUserInfo wechatUserInfo = weChatService.findByUnionId(unionIds.getUnionId());
        if(wechatUserInfo == null){
            return JsonResult.fail("用户未绑定公众号，请联系管理员扫码绑定");
        }

        String phoneNum = wechatUserInfo.getPhoneNum();
        User user = userService.findByPhone(phoneNum);
        if(user == null){
            return JsonResult.fail("用户不存在");
        }
        UserInfo userInfo = userService.queryByUid(user.getUid());
        if(userInfo == null){
            return JsonResult.fail("用户不存在");
        }

        //绑定unionId
        user.setUnionId(unionIds.getUnionId());
        int res = userService.bindUnionId(user);
        if(res != 1){
            return JsonResult.fail("绑定unionId失败");
        }
        try {
            Integer utype = userInfo.getUtype();
            String userId = user.getUid();
            String name   = userInfo.getName();

            //保存userId
            saveUserId(token,userId);

            JSONObject result = new JSONObject();
            result.put("utype",utype);//0:超级管理员 1:平台管理员 2:代理商 3:配送员 4:供货商
            result.put("name",name);

            return JsonResult.success(result);//返回到前端
        }catch (Exception e){
            log.error("认证异常",e);
            return JsonResult.fail("微信登录失败，解释结果异常");
        }

    }


    /**
     * 用户账户信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "account-info",method = RequestMethod.GET)
    public JsonResult userAccountInfo(HttpServletRequest request){
        String token    = request.getParameter("token");
        String openId   = getOpenId(token);

        UnionIds unionIds = unionIdService.findByOpenId(openId);
        if(unionIds == null){
            return JsonResult.fail("用户未认证");
        }

        WechatUserInfo wechatUserInfo = weChatService.findByUnionId(unionIds.getUnionId());
        if(wechatUserInfo == null){
            return JsonResult.fail("用户未绑定公众号，请联系管理员扫码绑定");
        }

        String phoneNum = wechatUserInfo.getPhoneNum();
        User user = userService.findByPhone(phoneNum);
        if(user == null){
            return JsonResult.fail("用户不存在");
        }

        try {
            JSONObject accountInfo = userService.findAccountInfo(user.getUid());
            return JsonResult.success(accountInfo);
        }catch (ServiceException e){
            return JsonResult.fail(e.getMessage());
        }
    }



    /**
     * 我的账单  type: 0:入账 1：提现
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "account-bill/list-page",method = RequestMethod.POST)
    public JsonResult userAccountBillPage(HttpServletRequest request, @RequestBody JSONObject params){
        Integer type = params.getInteger("type");
        if(type == null){
            type = 0;
        }
        Integer pageSize    = params.getInteger("pageSize");
        Integer currentPage = params.getInteger("currentPage");
        if(currentPage == null){
            currentPage = 0;
        }
        if(pageSize == null){
            pageSize = 20;
        }

        Date startDate = params.getDate("startDate");
        Date endDate  = params.getDate("endDate");

        if(startDate == null || endDate == null){
            startDate = null;
            endDate = null;
        }


        PageCondition condition = new PageCondition();
        condition.setCurrentPage(currentPage);
        condition.setPageSize(pageSize);

        String token   = request.getParameter("token");
        String userId  = getUserId(token);

        PageInfo<AccountBill> accountBillPageInfo = userService.userAccountBillListPage(condition,type,userId,startDate,endDate);
        return JsonResult.success(accountBillPageInfo);
    }


    /**
     * 我的账单  type: 0:入账 1：提现
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "offline-account-bill/list-page",method = RequestMethod.POST)
    public JsonResult userAccountBillOfflinePage(HttpServletRequest request, @RequestBody JSONObject params){
        Integer type = params.getInteger("type");
        if(type == null){
            type = 0;
        }

        Date startDate = params.getDate("startDate");
        Date endDate  = params.getDate("endDate");

        if(startDate == null || endDate == null){
            startDate = null;
            endDate = null;
        }


        Integer pageSize    = params.getInteger("pageSize");
        Integer currentPage = params.getInteger("currentPage");
        if(currentPage == null){
            currentPage = 0;
        }
        if(pageSize == null){
            pageSize = 20;
        }
        PageCondition condition = new PageCondition();
        condition.setCurrentPage(currentPage);
        condition.setPageSize(pageSize);

        String token   = request.getParameter("token");
        String userId  = getUserId(token);

        PageInfo<AccountBillOffline> accountBillOfflinePageInfo = userService.userAccountBillOfflineListPage(condition,type,userId,startDate,endDate);
        return JsonResult.success(accountBillOfflinePageInfo);
    }




}
