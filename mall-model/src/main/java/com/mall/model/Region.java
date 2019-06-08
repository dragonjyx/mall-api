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
@Table(name = "t_region")
public class Region {

    @Id
    @Column(name="p_id")
    private String id;

    @Column(name="p_name")
    private String name;

    @Column(name="p_short_name")
    private String shortName;

    @Column(name="p_code")
    private String code;

    @Column(name="p_parent_id")
    private String parentId;

    @Column(name="p_level")
    private Integer level;

    @Transient
    private List<Region> children;

}