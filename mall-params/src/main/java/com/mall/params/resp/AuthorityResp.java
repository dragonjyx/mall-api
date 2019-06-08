package com.mall.params.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityResp {

    private Long id;

    private String code;

    private String name;

    private String uid;

    private List<RoleResp> roleList;

}
