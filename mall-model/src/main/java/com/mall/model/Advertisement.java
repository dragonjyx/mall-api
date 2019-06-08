package com.mall.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_ad")
public class Advertisement {


    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_name")
    private String name;

    @Column(name="p_image_url")
    private String imageUrl;

    @Column(name="p_url")
    private String url;

    @Column(name="p_content")
    private String content;

    @Column(name="p_discribe")
    private String discribe;

    @Column(name="p_type")
    private Integer type;

    @Column(name="p_remark")
    private Integer remark;



}
