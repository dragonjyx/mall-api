package com.mall.service;

import com.mall.params.resp.RegionResp;

import java.util.List;

public interface RegionService {


    List<RegionResp> findByParentId(String parentId);

    List<RegionResp> allRegion();


}
