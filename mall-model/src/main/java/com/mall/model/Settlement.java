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
@Table(name = "t_settlement")
public class Settlement {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_title")
    private String title;

    @Column(name="p_sign")
    private String sign;

    @Column(name="p_amount")
    private BigDecimal amount;

    @Column(name="p_status")
    private Integer status;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_finished_time")
    private Date finishedTime;

    @Column(name="p_remark")
    private String remark;
}
