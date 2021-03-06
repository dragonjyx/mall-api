package com.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.java.redis.util.UniqueIdGenerate;
import com.java.utils.date.DateUtils;
import com.java.validate.ServiceException;
import com.mall.dao.*;
import com.mall.model.*;
import com.mall.params.page.PageCondition;
import com.mall.params.resp.AccountBillResp;
import com.mall.params.resp.UserAccountInfo;
import com.mall.params.status.AccountBillStatus;
import com.mall.params.status.AccountBillType;
import com.mall.params.status.AccountStatus;
import com.mall.params.status.BillStatus;
import com.mall.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public int widthdrawMoney(Account account, String orderSn) {
        List<AccountBill> accountBillList =  accountBillDao.findByOrderSn(orderSn);
        if(accountBillList.isEmpty()){
            throw new ServiceException("提现账单查询失败");
        }
        AccountBill accountBill = accountBillList.get(0);
        accountBill.setStatus(AccountBillStatus.UNFREE.value);
        accountBill.setUpdateTime(account.getUpdateTime());
        accountBill.setBillStatus(BillStatus.SUCCESS.value);
        int result = accountBillDao.addAccountBill(accountBill);

        if(result != 1){
            throw new ServiceException("修改提现账单失败");
        }
        result = accountDao.updateAccount(account);
        if(result != 1){
            throw new ServiceException("更新账户余额失败");
        }
        return 1;
    }

    @Override
    public int generateAccountBill(AccountBill accountBill) {
        int result = accountBillDao.addAccountBill(accountBill);
        return result;
    }

    @Transactional
    @Override
    public void accountShare(int order_share_day) {
        //1.已经支付，超过两天的账单
        Date now = new Date();
        Date offectDay = DateUtils.offectTime(order_share_day*-1);
        List<AccountBill> accountBillList = this.findShareAccountBill(offectDay);

        int result = 0;
        for(AccountBill accountBill:accountBillList){
            String accountId = accountBill.getAccountId();
            Account account  = this.findAccount(accountId);
            if(account == null){
                log.error("accountId:{}账户查询异常",accountId);
                continue;
            }
            BigDecimal amount = accountBill.getAmount();
            BigDecimal money  = account.getMoney();
            BigDecimal sum    = money.add(amount);

            account.setMoney(sum);
            account.setUpdateTime(now);

            result = this.updateAccount(account);
            if(result != 1){
                log.error("分润入账失败");
                continue;
            }

            accountBill.setStatus(AccountBillStatus.UNFREE.value);
            accountBill.setBillStatus(BillStatus.SUCCESS.value);
            accountBill.setUpdateTime(now);

            result = this.updateAccountBill(accountBill);
            if(result != 1){
                log.error("分润入账成功，更新账单失败");
                throw new ServiceException("分润入账成功，更新账单失败");
            }
        }
    }
}
