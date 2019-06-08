package com.mall.params.req;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayNotifyRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8455088480202440261L;
	private String type ;
	private String data ;
}
