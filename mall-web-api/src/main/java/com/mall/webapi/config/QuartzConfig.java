package com.mall.webapi.config;

import com.mall.webapi.task.OrderTask;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置定时任务
 */
@Configuration
public class QuartzConfig {

    private String orderTaskIdentity = "orderTask";
    private String shareTaskIdentity = "shareTask";

    @Value("${corn.order_task}")
    private String orderTaskCron;

    @Value("${corn.share_task}")
    private String shareTaskCron;

    @Bean
    public JobDetail orderJobDetail(){
        return JobBuilder.newJob(OrderTask.class).withIdentity(orderTaskIdentity).storeDurably().build();
    }

    @Bean
    public Trigger orderJobTrigger(){
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(orderTaskCron);
        Trigger trigger = TriggerBuilder.newTrigger().forJob(orderJobDetail()).withIdentity(orderTaskIdentity).withSchedule(cronScheduleBuilder).build();
        return trigger;
    }


    @Bean
    public JobDetail shareJobDetail(){
        return JobBuilder.newJob(OrderTask.class).withIdentity(shareTaskIdentity).storeDurably().build();
    }

    @Bean
    public Trigger shareJobTrigger(){
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(shareTaskCron);
        Trigger trigger = TriggerBuilder.newTrigger().forJob(orderJobDetail()).withIdentity(shareTaskIdentity).withSchedule(cronScheduleBuilder).build();
        return trigger;
    }





}
