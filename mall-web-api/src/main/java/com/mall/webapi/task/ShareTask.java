package com.mall.webapi.task;

import com.java.utils.date.DateUtils;
import com.java.validate.ServiceException;
import com.mall.dao.UserInfoDao;
import com.mall.dao.UserSchoolDormDao;
import com.mall.model.Account;
import com.mall.model.AccountBill;
import com.mall.params.status.AccountBillStatus;
import com.mall.service.AccountService;
import com.mall.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 分润任务
 * 解冻账单
 */
@Slf4j
public class ShareTask extends QuartzJobBean {

    @Value("${order_share_day}")
    private int order_share_day;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserSchoolDormDao userSchoolDormDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private AccountService accountService;



    @Transactional
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        log.warn("------------ShareTask-------------");
        //1.已经支付，超过两天的账单
        Date now = new Date();
        Date offectDay = DateUtils.offectTime(order_share_day*-1);
        List<AccountBill> accountBillList = accountService.findShareAccountBill(offectDay);

        int result = 0;
        for(AccountBill accountBill:accountBillList){
            String accountId = accountBill.getAccountId();
            Account account  = accountService.findAccount(accountId);
            if(account == null){
                log.error("accountId:{}账户查询异常",accountId);
                continue;
            }
            BigDecimal amount = accountBill.getAmount();
            BigDecimal money  = account.getMoney();
            BigDecimal sum    = money.add(amount);

            account.setMoney(sum);
            account.setUpdateTime(now);

            result = accountService.updateAccount(account);
            if(result != 1){
                log.error("分润入账失败");
                continue;
            }

            accountBill.setStatus(AccountBillStatus.UNFREE.value);
            accountBill.setBillStatus(AccountBillStatus.UNFREE.value);
            accountBill.setUpdateTime(now);

            result = accountService.updateAccountBill(accountBill);
            if(result != 1){
                log.error("分润入账成功，更新账单失败");
                throw new ServiceException("分润入账成功，更新账单失败");
            }
        }

    }
}
