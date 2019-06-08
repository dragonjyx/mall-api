package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_school_dorm")
public class SchoolDorm implements Serializable {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_school_id")
    private Long schoolId;

    @Column(name="p_school_name")
    private String schoolName;

    @Column(name="p_dorm_num")
    private String dormNum;

    @Column(name="p_name")
    private String name;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_ship_type")
    private Integer shipType;

    @Column(name="p_address")
    private String address;

}