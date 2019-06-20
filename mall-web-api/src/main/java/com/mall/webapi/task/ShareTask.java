package com.mall.webapi.task;

import com.java.utils.date.DateUtils;
import com.java.validate.ServiceException;
import com.mall.dao.UserInfoDao;
import com.mall.dao.UserSchoolDormDao;
import com.mall.model.Account;
import com.mall.model.AccountBill;
import com.mall.params.status.AccountBillStatus;
import com.mall.params.status.BillStatus;
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
    private AccountService accountService;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.warn("------------ShareTask time:{}-------------",DateUtils.Long2String(System.currentTimeMillis(),DateUtils.YYYY_MM_dd_HH_mm_ss));
        accountService.accountShare(order_share_day);
    }
}
