package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageOrderSend implements Serializable {


    private String title;
    private String orderSn;
    private String subject;
    private String receiveName;
    private String phoneNum;
    private String address;
    private String remark;

    private List<String> notifyPhoneNums;//通知手机号


}
