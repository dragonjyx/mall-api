package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_role")
public class Role {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_role_no")
    private String roleNo;//唯一

    @Column(name="p_name")
    private String name;

    @Column(name="p_desc")
    private String description;

    @Column(name="p_platform_code")
    private String platformCode;

    @Column(name="p_platform_name")
    private String platformName;

    @Column(name="p_status")
    private Integer status;

    @Column(name="p_create_time")
    private Date createTime;

    @Column(name="p_update_time")
    private Date updateTime;

    @Column(name="p_remark")
    private String remark;

    @Column(name="p_is_super")
    private Boolean isSuper=false;
}