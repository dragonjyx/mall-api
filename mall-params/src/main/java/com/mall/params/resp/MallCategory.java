package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallCategory {
    private Integer id;

    private String name;

    private Integer typeId;

    private String typeName;

    private String desc;

    private Integer pid;

    private String sort;

    private String status;

    private String remark;
}