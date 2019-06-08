package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_resource")
public class Resource {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_resource_no")
    private String resourceNo;//唯一

    @Column(name="p_name")
    private String name;

    @Column(name="p_type")
    private Integer type;

    @Column(name="p_uri")
    private String uri;

    @Column(name="p_path")
    private String path;

    @Column(name="p_icon")
    private String icon;

    @Column(name="p_desc")
    private String description;

    @Column(name="p_pid")
    private Long pid;

    @Column(name="p_sort")
    private Integer sort;

    @Column(name="p_platform_code")
    private String platformCode;

    @Column(name="p_enable")
    private Boolean enable;

    @Transient
    private List<Resource> children;


}