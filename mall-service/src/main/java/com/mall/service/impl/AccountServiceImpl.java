package com.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.java.redis.util.UniqueIdGenerate;
import com.java.validate.ServiceException;
import com.mall.dao.*;
import com.mall.model.*;
import com.mall.params.page.PageCondition;
import com.mall.params.resp.AccountBillResp;
import com.mall.params.resp.UserAccountInfo;
import com.mall.params.status.AccountBillStatus;
import com.mall.params.status.AccountBillType;
import com.mall.params.status.AccountStatus;
import com.mall.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private AccountBillDao accountBillDao;

    @Autowired
    private AccountRankDao accountRankDao;


    private String ACCOUNT_ID_KEY = "ACCOUNT_ID_KEY";

    @Override
    public int addOrUpdateAccount(Account account) {
        int result = 0;
        Date now = new Date();
        BigDecimal money = new BigDecimal(0);
        Account account1 = accountDao.findByUserId(account.getUid());
        if (account1 == null) {
            String aid = UniqueIdGenerate.getSnowflakeId(ACCOUNT_ID_KEY, 16);
            account.setAid(aid);
            account.setCreditUsed(money);
            account.setMoney(money);
            account.setAllAmount(money);
            account.setForzenMoney(money);
            account.setCreateTime(now);
            account.setUpdateTime(now);
            account.setStatus(AccountStatus.ENABLE.value);
            account.setVersion(1);
            result = accountDao.addAccount(account);
        } else {
            account1.setIdCardF(account.getIdCardF());
            account1.setIdCardZ(account.getIdCardZ());
            account1.setLicenseUrl(account.getLicenseUrl());
            account1.setRegion(account.getRegion());
            account1.setRegionId(account.getRegionId());
            account1.setCredit(account.getCredit());
            account1.setRankPoints(account.getRankPoints());
            account1.setContractNum(account.getContractNum());
            account1.setRemark(account.getRemark());
            account1.setUpdateTime(now);
            result = accountDao.updateAccount(account1);
        }
        User user = userDao.findByUserId(account.getUid());
        if (user != null) {
            user.setIsAccount(true);
            userDao.updateUser(user);
        }
        return result;
    }

    @Override
    public Account queryAccount(String userId) {
        Account account = accountDao.findByUserId(userId);
        return account;
    }

    @Override
    public PageInfo<UserAccountInfo> userAccountlistPage(PageCondition condition, String userId, String name, String phone, String email, Integer status) {
        PageHelper.startPage(condition.getCurrentPage(), condition.getPageSize());
        List<UserAccountInfo> accountInfoList = accountDao.findByNameOrPhoneOrEmailAndParentUserid(name, phone, email, userId, status);
        PageInfo<UserAccountInfo> userAccountInfoPageInfo = new PageInfo<UserAccountInfo>(accountInfoList);
        return userAccountInfoPageInfo;
    }


    @Override
    public int updataUserAccountStatus(long id, Integer status) {
        Account account = accountDao.findById(id);
        if (account == null) {
            log.error("account is not exist");
            return 0;
        }
        account.setStatus(status);
        int result = accountDao.updateAccount(account);
        return result;
    }




    @Override
    public List<Payment> paymentList() {
        return paymentDao.allPaymentList();
    }


    @Override
    public PageInfo<AccountBillResp> userAccountBilllistPage(PageCondition condition, String userId, String name, String phone, String email, Integer status, Integer type, Date stateDate, Date endDate) {
        PageHelper.startPage(condition.getCurrentPage(), condition.getPageSize());
        List<AccountBillResp> accountBillList = accountBillDao.findAccountBillList(name, phone, email, userId, status, type,stateDate,endDate);
        PageInfo<AccountBillResp> accountBillRespPageInfo = new PageInfo<AccountBillResp>(accountBillList);
        return accountBillRespPageInfo;
    }


    @Override
    public PageInfo<AccountBillResp> myAccountBilllistPage(PageCondition condition, String userId, Integer status, Integer type, Date stateDate,  Date endDate) {
        PageHelper.startPage(condition.getCurrentPage(), condition.getPageSize());
        List<AccountBillResp> accountBillList = accountBillDao.findAccountBillList(userId, status, type,stateDate,endDate);
        PageInfo<AccountBillResp> accountBillRespPageInfo = new PageInfo<AccountBillResp>(accountBillList);
        return accountBillRespPageInfo;
    }


    @Override
    public List<AccountRank> findAllAccountRank() {
        return accountRankDao.allRank();
    }

    @Override
    public List<AccountBill> findShareAccountBill(Date offectDay) {
        List<AccountBill> accountBillList = accountBillDao.findFreeAccountBill(offectDay,AccountBillStatus.FREE.value,AccountBillType.ENTER.value);
        return accountBillList;
    }

    @Override
    public Account findAccount(String accountId) {
        Account account = accountDao.findByAccountId(accountId);
        return account;
    }

    @Override
    public Account findByUserId(String userId) {
        Account account = accountDao.findByUserId(userId);
        return account;
    }

    @Override
    public int updateAccount(Account account) {
        int result = accountDao.updateAccount(account);
        return result;
    }

    @Override
    public int updateAccountBill(AccountBill accountBill) {
        int result = accountBillDao.updateAccountBill(accountBill);
        return result;
    }

    @Override
    public int widthdrawMoney(Account account, BigDecimal widthdrawAmount) {
        AccountBill accountBill = new AccountBill();
        accountBill.setAccountId(account.getAid());
        accountBill.setType(AccountBillType.WIDTHDRAW.value);
        accountBill.setOrderSn(null);
        accountBill.setStatus(AccountBillStatus.UNFREE.value);
        accountBill.setAmount(widthdrawAmount);
        accountBill.setIsDelete(Boolean.FALSE);
        accountBill.setCreateTime(account.getUpdateTime());
        accountBill.setUpdateTime(account.getUpdateTime());
        accountBill.setBillStatus(AccountBillStatus.FREE.value);
        int result = accountBillDao.addAccountBill(accountBill);

        if(result != 1){
            throw new ServiceException("生成提现账单失败");
        }

        result = accountDao.updateAccount(account);
        if(result != 1){
            throw new ServiceException("更新账户余额失败");
        }

        return 1;
    }
}
