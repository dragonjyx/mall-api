package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_account_rank")
public class AccountRank {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_name")
    private String name;

    @Column(name="p_min_points")
    private Integer minPoints;

    @Column(name="p_max_points")
    private Integer maxPoints;

    @Column(name="p_discount")
    private String discount;

    @Column(name="p_show_pirce")
    private Boolean showPirce;

    @Column(name="p_status")
    private Boolean status;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_remark")
    private String remark;
}