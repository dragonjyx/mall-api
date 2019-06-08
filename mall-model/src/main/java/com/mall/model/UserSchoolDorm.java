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
@Table(name = "t_user_school_dorm")
public class UserSchoolDorm {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_user_id")
    private String userId;

    @Column(name="p_user_name")
    private String userName;

    @Column(name="p_school_name")
    private String schoolName;

    @Column(name="p_school_id")
    private Long schoolId;

    @Column(name="p_dorm_name")
    private String dormName;

    @Column(name="p_dorm_id")
    private Long dormId;

}
