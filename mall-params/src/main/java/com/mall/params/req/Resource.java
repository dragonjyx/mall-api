package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    private Integer id;

    private String name;

    private Integer type;

    private String content;

    private String desc;

    private Integer pid;
}