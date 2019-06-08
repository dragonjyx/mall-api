package com.mall.dao;

import com.mall.mapper.DeviceCategoryMapper;
import com.mall.model.DeviceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class DeviceCategoryDao {

    @Autowired
    private DeviceCategoryMapper deviceCategoryMapper;

    public List<DeviceCategory> allCategory(){
        Example example = new Example(DeviceCategory.class);
        List<DeviceCategory> list = deviceCategoryMapper.selectByExample(example);
        return list;
    }


    public DeviceCategory findById(Long deviceCategoryId) {
        DeviceCategory deviceCategory = new DeviceCategory();
        deviceCategory.setId(deviceCategoryId);
        deviceCategory = deviceCategoryMapper.selectOne(deviceCategory);
        return deviceCategory;
    }
}
