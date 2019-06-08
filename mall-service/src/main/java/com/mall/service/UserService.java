package com.mall.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.mall.model.AccountBill;
import com.mall.model.AccountBillOffline;
import com.mall.model.User;
import com.mall.model.UserInfo;
import com.mall.params.page.PageCondition;
import com.mall.params.resp.LoginResp;
import com.mall.params.resp.LoginUserInfo;
import com.mall.params.resp.Menus;

import java.util.Date;
import java.util.List;

public interface UserService{

    LoginResp doLogin(String account, String password, String ipaddress);

    boolean sendPhoneCode(String phoneNum, String code);
    boolean sendEmailCode(String email, String code);
    boolean setPasswd(String userId, String passwd);
    int passwd(String userId, String sourcePasswd, String newPasswd);

    User findByPhone(String phoneNum);
    User findByEmail(String email);
    User findByUserInfoId(long id);
    User findByUid(String userId);

    LoginUserInfo findByUserId(String userId);
    UserInfo queryByUid(String userId);
    UserInfo queryByUserId(long id);
    int addNewUserInfo(UserInfo user);
    int updateUserInfo(UserInfo userInfo);
    int addUserLoginInfo(long id, String password);
    PageInfo<UserInfo> userlistPage(PageCondition condition, String userId, String name, String phone, String email, Integer status, Integer permissionCode);
    int updatePasswd(long id, String passwd);
    int updataUserStatus(long id, Integer status);

    //用户菜单
    List<Menus> userMenus(String userId, String platformCode);


    //微信公众号
    int updateUserOpenId(User user);
    LoginUserInfo findUserInfoByOpenId(String openId);

    User findByUnionId(String unionId);

    JSONObject findAccountInfo(String uid);


    PageInfo<AccountBill> userAccountBillListPage(PageCondition condition, Integer type, String userId, Date stateDate, Date endDate);

    PageInfo<AccountBillOffline> userAccountBillOfflineListPage(PageCondition condition, Integer type, String userId, Date stateDate, Date endDate);
}


