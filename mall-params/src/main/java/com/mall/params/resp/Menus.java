package com.mall.params.resp;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menus implements Serializable {

    private Long id;
    private Long pid;
    private String icon;
    private String name;
    private String path;
    private String title;
    private JSONObject meta;

    private List<Menus> children;
}
