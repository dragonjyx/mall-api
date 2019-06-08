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
@Table(name = "t_store_user")
public class StoreUser {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_store_id")
    private Long storeId;

    @Column(name="p_uid")
    private String uid;

    @Column(name="p_user_name")
    private String userName;

    @Column(name="p_job")
    private String job;

    @Column(name="p_create_time")
    private Date createTime;

}
