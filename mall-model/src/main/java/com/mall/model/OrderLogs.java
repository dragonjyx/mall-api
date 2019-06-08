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
@Table(name = "t_order_logs")
public class OrderLogs {

    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="p_order_sn")
    private String orderSn;

    @Column(name="p_handler_uid")
    private String handlerUid;

    @Column(name="p_msg")
    private String msg;

    @Column(name="p_handle_note")
    private String handleNote;

    @Column(name="p_log_time")
    private Date logTime;
}