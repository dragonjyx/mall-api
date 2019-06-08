package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
public class StoreUserReq {

    private Long id;

    private Long storeId;

    private String uid;

    private String userName;

    private String job;

    private Date createTime;

}
