package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallCategoryReq {

    private Long id;

    private String categoryName;

    private Long typeId;

    private String typeName;

    private String categoryDesc;

    private Long pid;

    private String sort;

    private String status;

    private String remark;
}