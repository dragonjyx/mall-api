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
@Table(name = "t_after_sale_evaluation")
public class AfterSaleEvaluation {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_after_sale_id")
    private Long afterSaleId;

    @Column(name="p_uid")
    private String uid;

    @Column(name="p_name")
    private String name;

    @Column(name="p_score")
    private Double score;

    @Column(name="p_msg")
    private String msg;

    @Column(name="p_create_time")
    private Date createTime;

    
}