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
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_school")
public class School implements Serializable {

    private static final long serialVersionUID = 2736210912881957538L;

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_name")
    private String name;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_type")
    private Integer type;//1：学校 2：小区


}
