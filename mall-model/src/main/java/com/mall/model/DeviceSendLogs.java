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
@Table(name = "t_device_send_logs")
public class DeviceSendLogs {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_device_id")
    private String deviceId;

    @Column(name="p_repertory_id")
    private Integer repertoryId;

    @Column(name="p_action")
    private String action;

    @Column(name="p_action_time")
    private Date actionTime;
}