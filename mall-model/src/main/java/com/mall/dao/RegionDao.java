package com.mall.dao;

import com.mall.mapper.RegionMapper;
import com.mall.model.Region;
import com.mall.params.resp.RegionResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionDao {

    @Autowired
    private RegionMapper regionMapper;

    public List<RegionResp> findByParentId(String parentId){
        List<Region> regionList = regionMapper.findByParentId(parentId);
        List<RegionResp> regionRespList = new ArrayList<RegionResp>();
        RegionResp regionResp = null;
        for(Region region:regionList){
            regionResp = new RegionResp();
            regionResp.setId(region.getId());
            regionResp.setLabel(region.getName());
            regionResp.setValue(region.getCode());
            regionResp.setParentId(region.getParentId());
            regionRespList.add(regionResp);
        }
       return regionRespList;
    }


    public List<Region> allRegions(){
        Example example = new Example(Region.class);
        List<Region> regionList = regionMapper.selectByExample(example);
        return regionList;
    }



}
