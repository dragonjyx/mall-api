package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_device_type_parts")
public class DeviceTypeParts {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_name")
    private String name;

    @Column(name="p_type_name")
    private String typeName;

    @Column(name="p_type_id")
    private Long typeId;

    @Column(name="p_price")
    private BigDecimal price;
}