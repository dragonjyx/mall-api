package com.mall.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 支付回调数据表
 * @author lagoon
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name="t_pay_notify_data")
public class PayNotifyData {

	@Id
	@Column(name = "p_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	
	@Column(name="p_type")
	private String type ;
	
	@Column(name="p_data")
	private String data;
	
	@Column(name="p_result_data")
	private String resultData;
	
	@Column(name="p_order_sn")
	private String orderSn;
	
	@Column(name="p_create_time")
	private Long createTime;
}
