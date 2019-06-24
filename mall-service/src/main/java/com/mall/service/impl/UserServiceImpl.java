package com.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.java.redis.util.UniqueIdGenerate;
import com.java.utils.code.IDGenerate;
import com.java.utils.code.MD5Util;
import com.java.utils.regex.RegexVaildate;
import com.java.validate.ServiceException;
import com.mall.dao.*;
import com.mall.model.*;
import com.mall.params.page.PageCondition;
import com.mall.params.resp.LoginResp;
import com.mall.params.resp.LoginUserInfo;
import com.mall.params.resp.Menus;
import com.mall.params.status.ResourceType;
import com.mall.params.status.UserStatus;
import com.mall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 用户信息
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleResourceDao roleResourceDao;

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private UserInfoDao userInfoDao;

    private String USER_ID_KEY = "USER_ID_KEY";

    @Override
    public LoginResp doLogin(String account, String password, String ipaddress) {
        LoginResp loginResp = new LoginResp();
        if (StringUtils.isEmpty(account)) {
            loginResp.setResult(7);
            return loginResp;
        }
        if (StringUtils.isEmpty(password)) {
            loginResp.setResult(8);
            return loginResp;
        }
        if (StringUtils.isEmpty(ipaddress)) {
            loginResp.setResult(1);
            return loginResp;
        }

        UserInfo userInfo = null;
        if (RegexVaildate.isEmail(account)) {//邮箱
            userInfo = userInfoDao.findByEmail(account);
        } else {//手机号
            userInfo = userInfoDao.findByPhone(account);
        }

        if (userInfo == null) {
            loginResp.setResult(2);//账号不存在
            return loginResp;
        }

        Long userInfoId = userInfo.getId();
        User user = userDao.findByUserInfoId(userInfoId);
        if (user == null) {
            loginResp.setResult(2);//账号不存在
            return loginResp;
        }

        if (user.getIsLock()) {
            loginResp.setResult(4);//用户被锁定，请向管理员申请开锁
            return loginResp;
        }

        if (user.getStatus() != null && user.getStatus()== UserStatus.FREEZE.value) {
            loginResp.setResult(9);//用户被冻结
            return loginResp;
        }

        if (user.getStatus() != null && user.getStatus()== UserStatus.INIT.value) {
            loginResp.setResult(10);//用户未激活
            return loginResp;
        }


        String dbPasswd = user.getPassword();
        String key      = user.getPkey();
        String md5Pwd   = MD5Util.md5Encrypt32(key + MD5Util.md5Encrypt32(password));

        //登录错误
        Integer errorCount = user.getErrorCount();
        if (errorCount == null) {
            errorCount = 0;
        }

        Long visitCount = user.getVisitCount();
        if (visitCount == null) {
            visitCount = 0L;
        }
        user.setLoginIp(ipaddress);

        Date now = new Date();
        Date loginDate = user.getLoginTime();
        if (loginDate == null) {
            loginDate = now;
        }
        long loginTime = loginDate.getTime();
        long offect = now.getTime() - loginTime;

        if (errorCount >= 5) {//登录错误超过5次 , 判断是否已经超时
            if (offect < 30 * 60 * 1000) {
                loginResp.setResult(5);
                return loginResp;
            }

            //超过半小时
            if (!dbPasswd.equals(md5Pwd)) {
                user.setErrorCount(1);//从新计数
                user.setLoginTime(now);
//                user.setIsLock(false);
                int result = userDao.updateUser(user);
                log.warn("输入密码错误，登录更新登录信息 result：{}", result);
                loginResp.setUserName(userInfo.getName());
                loginResp.setResult(3);
                return loginResp;
            }

            visitCount++;
            //登录成功
            user.setErrorCount(0);//从新计数
            user.setLoginTime(now);
//            user.setIsLock(false);
            user.setVisitCount(visitCount);
            int result = userDao.updateUser(user);
            log.warn("登录成,更新登录信息 result：{}", result);

            loginResp.setResult(0);
            loginResp.setUserName(userInfo.getName());
            loginResp.setUserId(user.getUid());
//            loginResp.setOpenId(user.getWxOpenId());
            return loginResp;
        }


        if (!dbPasswd.equals(md5Pwd)) {
            errorCount++;
            user.setErrorCount(errorCount);//每次错误都+1
            user.setLoginTime(now);
            if(errorCount == 5){
//                user.setIsLock(true);
            }
            int result = userDao.updateUser(user);
            log.warn("输入密码错误，登录更新登录信息 result：{},输入密码错误超过:{}次", result, errorCount);

            loginResp.setUserName(userInfo.getName());
            loginResp.setResult(3);
            return loginResp;
        }


        visitCount++;
        //登录成功
        user.setErrorCount(0);//从新计数
        user.setLoginTime(now);
//        user.setIsLock(false);
        user.setVisitCount(visitCount);
        int result = userDao.updateUser(user);
        log.warn("登录成,更新登录信息 result：{}", result);

        loginResp.setResult(0);
        loginResp.setUserName(userInfo.getName());
        loginResp.setUserId(user.getUid());
//        loginResp.setOpenId(user.getWxOpenId());
        return loginResp;
    }

    @Override
    public boolean sendPhoneCode(String phoneNum, String code) {
        //发送手机验证码
        return true;
    }

    @Override
    public boolean sendEmailCode(String email, String code) {
        return true;
    }

    @Override
    public boolean setPasswd(String userId, String passwd) {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(passwd)){
            return false;
        }
        User user = userDao.findByUserId(userId);
        if(user == null){
            log.error("user is not exist");
            return false;
        }
        String md5Pwd = MD5Util.md5Encrypt32(passwd);
        String pkey = user.getPkey();

        String password = MD5Util.md5Encrypt32(pkey + md5Pwd);
        user.setPassword(password);

        int result = userDao.updateUser(user);
        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public int passwd(String userId, String sourcePasswd, String newPasswd) {
        User user = userDao.findByUserId(userId);
        if(user == null){
            log.error("用户异常");
            return 2;
        }

        String password = user.getPassword();
        String pkey = user.getPkey();

        String md5Pwd = MD5Util.md5Encrypt32(sourcePasswd);
        String md5SourcePwd =  MD5Util.md5Encrypt32(pkey + md5Pwd);

        if(!password.equals(md5SourcePwd)){
            log.error("原始密码错误");
            return 3;
        }

        md5Pwd = MD5Util.md5Encrypt32(newPasswd);
        String passwd = MD5Util.md5Encrypt32(pkey + md5Pwd);
        user.setPassword(passwd);

        int result = userDao.updateUser(user);
        return result;
    }

    @Override
    public User findByPhone(String phoneNum) {
        UserInfo userInfo = userInfoDao.findByPhone(phoneNum);
        if(userInfo == null){
            return null;
        }
        User user = userDao.findByUserInfoId(userInfo.getId());
        return user;
    }

    @Override
    public User findByEmail(String email) {
        UserInfo userInfo = userInfoDao.findByEmail(email);
        if(userInfo == null){
            return null;
        }
        User user = userDao.findByUserInfoId(userInfo.getId());
        return user;
    }

    @Override
    public User findByUserInfoId(long id) {
        User user = userDao.findByUserInfoId(id);
        return user;
    }

    @Override
    public User findByUid(String userId) {
        User user = userDao.findByUserId(userId);
        return user;
    }

    @Override
    public LoginUserInfo findByUserId(String userId) {
        if(StringUtils.isEmpty(userId)){
            return null;
        }
        User user = userDao.findByUserId(userId);
        if(user == null){
            return null;
        }
        UserInfo userInfo = userInfoDao.findById(user.getUserInfoId());
        if(userInfo == null){
            return null;
        }
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setUserName(userInfo.getName());
        loginUserInfo.setNickName(user.getAlias());
        loginUserInfo.setEmail(userInfo.getEmail());
        loginUserInfo.setPhone(userInfo.getPhone());
        loginUserInfo.setSex(userInfo.getSex());
        loginUserInfo.setAddress(userInfo.getAddress());
        loginUserInfo.setBirthday(userInfo.getBirthday());
        loginUserInfo.setLoginIp(user.getLoginIp());
        loginUserInfo.setAvatarUrl(userInfo.getAvatarUrl());
        return loginUserInfo;
    }

    @Override
    public UserInfo queryByUid(String userId) {
        User user = userDao.findByUserId(userId);
        if(user == null){
            return null;
        }
        UserInfo userInfo = userInfoDao.findById(user.getUserInfoId());
        return userInfo;
    }

    @Override
    public UserInfo queryByUserId(long id) {
        UserInfo userInfo = userInfoDao.findById(id);
        return userInfo;
    }


    @Override
    public int addNewUserInfo(UserInfo userInfo) {
        Date now = new Date();
        userInfo.setCreateTime(now);
        userInfo.setUpdateTime(now);

        UserInfo tmpUserInfo = userInfoDao.findByPhone(userInfo.getPhone());
        if(tmpUserInfo != null){
            return 2;
        }
        if(userInfo.getEmail()!=null){
            tmpUserInfo = userInfoDao.findByEmail(userInfo.getEmail());
            if(tmpUserInfo != null){
                return 3;
            }
        }

        int result = userInfoDao.addNewUserInfo(userInfo);
        return result;
    }

    @Override
    public int updateUserInfo(UserInfo userInfo) {
        UserInfo sourceUserInfo = userInfoDao.findById(userInfo.getId());
        if(sourceUserInfo == null){
            log.error("用户不存在");
            return 2;
        }

        if(!sourceUserInfo.getPhone().equals(userInfo.getPhone())){
            UserInfo tmpUserInfo = userInfoDao.findByPhone(userInfo.getPhone());
            if(tmpUserInfo != null){
                log.error("该手机用户已经存在");
                return 3;
            }
        }

        if(!sourceUserInfo.getEmail().equals(userInfo.getEmail())){
            UserInfo tmpUserInfo = userInfoDao.findByEmail(userInfo.getEmail());
            if(tmpUserInfo != null){
                log.error("该邮箱用户已经存在");
                return 4;
            }
        }


        int result = userInfoDao.updateUserInfo(userInfo);
        if(result == 0){
            log.error("更新用户信息失败");
            return 0;
        }

        User user = userDao.findByUserInfoId(userInfo.getId());
        if(user != null){
            user.setAlias(userInfo.getAlias());
            result = userDao.updateUser(user);
            if(result == 0){
                log.error("更新用户别名失败");
            }
        }
        return 1;
    }

    @Override
    public PageInfo<UserInfo> userlistPage(PageCondition condition, String userId, String name, String phone, String email, Integer status, Integer permissionCode) {
        PageHelper.startPage(condition.getCurrentPage(),condition.getPageSize());
        List<UserInfo> userInfoList = userInfoDao.findMyUserlist(userId, name, phone, email, status,permissionCode);
        PageInfo<UserInfo> userInfoPageInfo = new PageInfo<UserInfo>(userInfoList);
        return userInfoPageInfo;
    }

    @Override
    public int addUserLoginInfo(long id, String password) {
        UserInfo userInfo = userInfoDao.findById(id);
        if(userInfo == null){
            return 2;
        }
        User user = userDao.findByUserInfoId(id);
        if(user != null){
            return 3;
        }
        user = new User();
        String pkey = IDGenerate.UUID();
        String md5Pwd = MD5Util.md5Encrypt32(password);
        String passwd = MD5Util.md5Encrypt32(pkey + md5Pwd);
        Date now = new Date();

        String userId = UniqueIdGenerate.getId(USER_ID_KEY,16);
        user.setUid(userId);
        user.setUserInfoId(id);
        user.setPkey(pkey);
        user.setPassword(passwd);
        user.setIsLock(false);
        user.setErrorCount(0);
        user.setVisitCount(0L);
        user.setAlias(userInfo.getName());
        user.setIsSuper(false);
        user.setUsername(userInfo.getName());
        user.setCreateTime(now);
        user.setLoginIp("");
        user.setStatus(UserStatus.INIT.value);
        int result = userDao.addNewUser(user);

        return result;
    }

    @Override
    public int updatePasswd(long id, String passwd) {
        User user = userDao.findByUserInfoId(id);
        if(user == null){
            log.error("user is not exist");
            return 0;
        }
        String md5Pwd = MD5Util.md5Encrypt32(passwd);
        String pkey = user.getPkey();

        String password = MD5Util.md5Encrypt32(pkey + md5Pwd);
        user.setPassword(password);

        int result = userDao.updateUser(user);
        return result;
    }


    @Override
    public int updataUserStatus(long id, Integer status) {
        User user = userDao.findByUserInfoId(id);
        if(user == null){
            log.error("user is not exist");
            return 0;
        }
        user.setStatus(status);
        int result = userDao.updateUser(user);
        return result;
    }


    @Override
    public List<Menus> userMenus(String userId, String platformCode) {
        Set<Long> resourceIdSet = new HashSet<Long>();
        List<UserRole>  userRoleList = userRoleDao.findByUserId(userId);
        for(UserRole userRole:userRoleList){
            long roleId = userRole.getRoleId();
            List<RoleResource>  roleResourceList = roleResourceDao.findByRoleId(roleId);
            for(RoleResource roleResource:roleResourceList){
                resourceIdSet.add(roleResource.getResourceId());
            }
        }

        List<Object> types = new ArrayList<Object>();
        types.add(1);
        types.add(2);
        List<Resource> resourceList = new ArrayList<Resource>();
        for(Long resourceId: resourceIdSet){
            Resource resource = resourceDao.findByIdInTypes(resourceId,platformCode,types);
            if(resource == null){
                continue;
            }
            resourceList.add(resource);
        }

        List<Menus> firstResourceList = new ArrayList<Menus>();//一级
        List<Menus> nRoleResourceList = new ArrayList<Menus>();//n级

        Menus menus = null;
        //一级菜单
        for (Resource resource : resourceList) {
            if(StringUtils.isEmpty(resource.getIcon())){
                resource.setIcon("md-photos");
            }
            menus = new Menus();
            menus.setId(resource.getId());
            menus.setName(resource.getName());
            menus.setPath(resource.getPath());
            menus.setPid(resource.getPid());
            menus.setTitle(resource.getName());
            menus.setIcon(resource.getIcon());
            JSONObject meta = new JSONObject();
            meta.put("icon",resource.getIcon());
            meta.put("title",resource.getName());
            /*
            if(resource.getName()!=null && resource.getName().equals("首页")){
                meta.put("hideInMenu",true);
            }*/
            menus.setMeta(meta);
            if (resource.getType() == ResourceType.FIRST_LEVEL.value) {
                firstResourceList.add(menus);
            } else {
                nRoleResourceList.add(menus);
            }
        }

        //n级递归
        for (Menus menus1 : firstResourceList) {
            accembleResource(menus1, nRoleResourceList);
        }
        return firstResourceList;
    }

    //递归合并
    private void accembleResource(Menus resource, List<Menus> resourceList) {
        List<Menus> subResourceList = findSubResource(resource, resourceList);
        if (subResourceList.isEmpty()) {
            return;//如果没有子菜单，就返回
        }
        resource.setChildren(subResourceList);
        for (Menus res : subResourceList) {
            accembleResource(res, resourceList);
        }
    }

    //查询子节点
    private List<Menus> findSubResource(Menus parentResource, List<Menus> subResourceList) {
        List<Menus> sResourceList = new ArrayList<Menus>();
        for (Menus resource : subResourceList) {
            if (resource.getPid() == parentResource.getId()) {
                sResourceList.add(resource);
            }
        }
        return sResourceList;
    }

    @Override
    public int updateUserOpenId(User user) {
        int result = userDao.updateUser(user);
        return result;
    }

    @Override
    public LoginUserInfo findUserInfoByOpenId(String openId) {
        User user = userDao.findByUnionId(openId);
        if(user == null){
            return null;
        }
        UserInfo userInfo = userInfoDao.findById(user.getUserInfoId());
        if(userInfo == null){
            return null;
        }
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setUserName(userInfo.getName());
        loginUserInfo.setNickName(user.getAlias());
        loginUserInfo.setEmail(userInfo.getEmail());
        loginUserInfo.setPhone(userInfo.getPhone());
        loginUserInfo.setSex(userInfo.getSex());
        loginUserInfo.setAddress(userInfo.getAddress());
        loginUserInfo.setBirthday(userInfo.getBirthday());
        loginUserInfo.setLoginIp(user.getLoginIp());
        loginUserInfo.setAvatarUrl(userInfo.getAvatarUrl());
        return loginUserInfo;
    }


    @Override
    public User findByUnionId(String unionId) {
        User user = userDao.findByUnionId(unionId);
        return user;
    }


    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AccountBillDao accountBillDao;

    @Autowired
    private AccountBillOfflineDao accountBillOfflineDao;

    @Autowired
    private SettlementDao settlementDao;


    @Override
    public JSONObject findAccountInfo(String uid) {
        JSONObject result = new JSONObject();

        Account account = accountDao.findByUserId(uid);
        if(account == null){
            throw new ServiceException("用户未注册账号");
        }
        result.put("withdrawAmount",account.getMoney());//可提现金额

        BigDecimal hasWithdrawAmount = accountBillDao.sumWithdrawAmount(account.getAid());
        if(hasWithdrawAmount == null){
            hasWithdrawAmount = new BigDecimal(0);
        }
        result.put("hasWithdrawAmount",hasWithdrawAmount);//已提现金额

        BigDecimal freeAmount = accountBillDao.sumFreeAmount(account.getAid());
        if(freeAmount == null){
            freeAmount = new BigDecimal(0);
        }
        result.put("freeAmount",freeAmount);//冻结金额



        //--------线下
        BigDecimal offlineFreeAmount = accountBillOfflineDao.sumFreeAmount(account.getAid());
        if(offlineFreeAmount == null){
            offlineFreeAmount = new BigDecimal(0);
        }
        result.put("offlineFreeAmount",offlineFreeAmount);//线下冻结金额


        BigDecimal offlineSettlementAmount = settlementDao.sumSettlement(uid,2);
        if(offlineSettlementAmount == null){
            offlineSettlementAmount = new BigDecimal(0);
        }
        result.put("offlineSettlementAmount",offlineSettlementAmount);//线下已结算金额

        return result;
    }


    @Override
    public PageInfo<AccountBill> userAccountBillListPage(PageCondition condition, Integer type, String userId, Date stateDate, Date endDate) {
        Account account = accountDao.findByUserId(userId);
        if(account == null){
            throw new ServiceException("用户未注册账号");
        }
        PageHelper.startPage(condition.getCurrentPage(),condition.getPageSize());
        List<AccountBill> accountBillList = accountBillDao.findBillList(account.getAid(),type,stateDate,endDate);
        PageInfo<AccountBill> accountBillPageInfo = new PageInfo<AccountBill>(accountBillList);
        return accountBillPageInfo;
    }


    @Override
    public PageInfo<AccountBillOffline> userAccountBillOfflineListPage(PageCondition condition, Integer type, String userId, Date stateDate, Date endDate) {
        Account account = accountDao.findByUserId(userId);
        if(account == null){
            throw new ServiceException("用户未注册账号");
        }
        PageHelper.startPage(condition.getCurrentPage(),condition.getPageSize());
        List<AccountBillOffline> accountBillOfflineList = accountBillOfflineDao.findBillList(account.getAid(),type,stateDate,endDate);
        PageInfo<AccountBillOffline> accountBillOfflinePageInfo = new PageInfo<AccountBillOffline>(accountBillOfflineList);
        return accountBillOfflinePageInfo;
    }

    @Override
    public int bindUnionId(User user) {
        int result = userDao.updateUser(user);
        return result;
    }
}
