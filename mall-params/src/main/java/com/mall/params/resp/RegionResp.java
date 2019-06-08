package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionResp implements Serializable {

    private String id;

    private String label;

    private String value;

    private String parentId;

//    private Boolean loading = false;

    private List<RegionResp> children;

}
