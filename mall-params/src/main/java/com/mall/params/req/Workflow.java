package com.mall.params.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Workflow {
    private Integer id;

    private String name;

    private String type;

    private Integer isDefault;
}