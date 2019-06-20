package com.mall.service;


import com.github.pagehelper.PageInfo;
import com.mall.model.Account;
import com.mall.model.AccountBill;
import com.mall.model.AccountRank;
import com.mall.model.Payment;
import com.mall.params.page.PageCondition;
import com.mall.params.resp.AccountBillResp;
import com.mall.params.resp.UserAccountInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountService {

    int addOrUpdateAccount(Account account);

    Account queryAccount(String userId);

    PageInfo<UserAccountInfo> userAccountlistPage(PageCondition condition, String userId, String name, String phone, String email, Integer status);

    int updataUserAccountStatus(long id, Integer status);

    List<Payment> paymentList();

    PageInfo<AccountBillResp> userAccountBilllistPage(PageCondition condition, String userId, String name, String phone, String email, Integer status, Integer type, Date stateDate, Date endDate);

    PageInfo<AccountBillResp> myAccountBilllistPage(PageCondition condition, String userId, Integer status, Integer type, Date stateDate, Date endDate);

    List<AccountRank> findAllAccountRank();

    List<AccountBill> findShareAccountBill(Date offectDay);

    Account findAccount(String accountId);

    int updateAccount(Account account);

    int updateAccountBill(AccountBill accountBill);

    Account findByUserId(String userId);

    int widthdrawMoney(Account account, String orderSn);

    int generateAccountBill(AccountBill accountBill);

    void accountShare(int order_share_day);



}
