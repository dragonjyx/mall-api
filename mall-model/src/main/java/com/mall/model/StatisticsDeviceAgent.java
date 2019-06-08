package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_statistics_order_agent")
public class StatisticsDeviceAgent {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_date_condition")
    private Integer dateCondition;

    @Column(name="p_agent_uid")
    private String agentUid;

    @Column(name="p_agent_name")
    private String agentName;

    @Column(name="p_send_num")
    private Integer sendNum;

    @Column(name="p_choice_num")
    private Integer choiceNum;

    @Column(name="p_send_earnings")
    private String sendEarnings;

    @Column(name="p_choice_earnings")
    private String choiceEarnings;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_update_time")
    private Date updateTime;

    @Column(name="p_task_id")
    private String taskId;

}