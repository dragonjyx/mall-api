package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResourceResp {
    private Long id;

    private Long parent_id;

    private String parentName;

    private String description;

    private Integer sort;

    private String title;
    private String name;

    private Boolean expanded = true;

    private List<RoleResourceResp> children;

    private Integer type;

    private String uri;

    private String path;

    private String platformCode;

    private Boolean checked;
    private Boolean selected;

}