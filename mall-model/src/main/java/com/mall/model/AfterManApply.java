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
@Table(name = "t_afterman_apply")
public class AfterManApply {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_after_sale_id")
    private Long afterSaleId;

    @Column(name="p_uid")
    private String uid;

    @Column(name="p_distribution_time")
    private Date distributionTime;

    @Column(name="p_remark")
    private String remark;
}