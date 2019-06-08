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
public class StatisticsOrderAgent {

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

    @Column(name="p_amount")
    private BigDecimal amount;

    @Column(name="p_order_amount")
    private BigDecimal orderAmount;

    @Column(name="p_order_num")
    private Integer orderNum;

    @Column(name="p_earnings")
    private String earnings;

    @Column(name="p_ord_num")
    private String ordNum;

    @Column(name="p_order_earnings")
    private String orderEarnings;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_update_time")
    private Date updateTime;

    @Column(name="p_task_id")
    private String taskId;

}