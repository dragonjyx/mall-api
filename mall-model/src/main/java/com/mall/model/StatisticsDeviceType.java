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
@Table(name = "t_statistics_device_type")
public class StatisticsDeviceType {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_date_condition")
    private Integer dateCondition;

    @Column(name="p_device_type_id")
    private Long deviceTypeId;

    @Column(name="p_device_type_name")
    private String deviceTypeName;

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