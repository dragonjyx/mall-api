package com.mall.dao;


import com.mall.mapper.AdvertisementMapper;
import com.mall.model.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AdvertisementDao {

    @Autowired
    private AdvertisementMapper advertisementMapper;


    public List<Advertisement> findByType(Integer type){
        Example example = new Example(Advertisement.class);
        if(type != null){
            example.createCriteria().andEqualTo("type",type);
        }
        List<Advertisement>  advertisementList = advertisementMapper.selectByExample(example);
        return advertisementList;
    }




}
