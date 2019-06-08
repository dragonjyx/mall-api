package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_member_location")
public class MemberLocation implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="location_name")
    private String locationName;


    @Column(name="location_id")
    private String locationId;


    @Column(name="open_id")
    private String openId;

}
