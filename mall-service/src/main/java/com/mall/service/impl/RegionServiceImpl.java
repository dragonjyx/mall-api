package com.mall.service.impl;

import com.mall.dao.RegionDao;
import com.mall.model.Region;
import com.mall.params.resp.RegionResp;
import com.mall.service.RegionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionDao regionDao;

    @Override
    public List<RegionResp> findByParentId(String parentId) {
        if(StringUtils.isEmpty(parentId)){
            return new ArrayList<RegionResp>();
        }
        List<RegionResp> regionRespList = regionDao.findByParentId(parentId);
        return regionRespList;
    }

    @Override
    public List<RegionResp> allRegion() {
        List<Region> regionList = regionDao.allRegions();


        List<RegionResp> regionList1 = new ArrayList<RegionResp>();
        List<RegionResp> subRegionList = new ArrayList<RegionResp>();

        RegionResp regionResp = null;
        for(Region region:regionList){
            regionResp = new RegionResp();
            regionResp.setParentId(region.getParentId());
            regionResp.setValue(region.getCode());
//            regionResp.setLoading(false);
            regionResp.setLabel(region.getName());
            regionResp.setId(region.getId());
            if(region.getParentId().equals("-1")){
                regionList1.add(regionResp);
            }else{
                subRegionList.add(regionResp);
            }
        }

        for(RegionResp region:regionList1){
            accembleRegion(region,subRegionList);
        }
        return regionList1;
    }


    //递归合并
    private void accembleRegion(RegionResp region, List<RegionResp> regionList){
        List<RegionResp> subRegionList = findSubRegion(region,regionList);
        if(subRegionList.isEmpty()){
            return;//如果没有子地区，就返回
        }
        region.setChildren(subRegionList);
        for(RegionResp reg:subRegionList){
            accembleRegion(reg,regionList);
        }
    }

    //查询子节点
    private List<RegionResp> findSubRegion(RegionResp parentRegion,List<RegionResp> subRegionList){
        List<RegionResp> regionList = new ArrayList<RegionResp>();
        for(RegionResp region:subRegionList){
            if(region.getParentId().equals(parentRegion.getId())){
                regionList.add(region);
            }
        }
        return regionList;
    }



}
