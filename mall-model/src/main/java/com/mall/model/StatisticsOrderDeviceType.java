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
@Table(name = "t_statistics_order_device_type")
public class StatisticsOrderDeviceType {

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

    @Column(name="p_price")
    private BigDecimal price;

    @Column(name="p_market_price")
    private BigDecimal marketPrice;

    @Column(name="p_earnings")
    private String earnings;

    @Column(name="p_market_earnings")
    private String marketEarnings;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_update_time")
    private Date updateTime;

    @Column(name="p_task_id")
    private String taskId;

}