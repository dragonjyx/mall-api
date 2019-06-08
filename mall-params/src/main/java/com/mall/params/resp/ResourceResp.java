package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceResp {
    private Long id;

    private Long parent_id;

    private String parentName;

    private String description;

    private Integer order;

    private String name;

    private Boolean open = true;

    private List<ResourceResp> lists;

    private Integer type;

    private String uri;

    private String path;

    private String icon;

    private String platformCode;

}