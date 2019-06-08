package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallTypeSpecResp {

    private Long id;

    private Long typeId;

    private Long specId;

    private String typeName;

    private String specName;
}