package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallTypeReq {

    private Long id;

    private String typeName;

    private Integer sort;

    private String status;
}