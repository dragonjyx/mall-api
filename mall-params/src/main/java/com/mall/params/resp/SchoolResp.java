package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolResp implements Serializable {
    private static final long serialVersionUID = 8004714846940505463L;

    private Long id;

    private Long dormId;

    private String name;

    private String schoolName;

    private String dormNum;

    private String dormName;

    private List<SchoolResp> lists;

}
