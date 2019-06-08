package com.mall.dao;

import com.mall.mapper.DeviceTypeMapper;
import com.mall.model.DeviceType;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class DeviceTypeDao {

    @Autowired
    private DeviceTypeMapper deviceTypeMapper;

    public DeviceType findByName(String name) {
        DeviceType deviceType = new DeviceType();
        deviceType.setName(name);
        return deviceTypeMapper.selectOne(deviceType);
    }

    public DeviceType findByModel(String model) {
        DeviceType deviceType = new DeviceType();
        deviceType.setModel(model);
        return deviceTypeMapper.selectOne(deviceType);
    }

    public int insert(DeviceType deviceType) {
        int count = deviceTypeMapper.insert(deviceType);
        return count;
    }

    public DeviceType findById(Long id) {
        DeviceType deviceType = new DeviceType();
        deviceType.setId(id);
        return deviceTypeMapper.selectOne(deviceType);
    }

    public int updateById(DeviceType deviceType) {
        Example example = new Example(DeviceType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", deviceType.getId());
        int count = deviceTypeMapper.updateByExample(deviceType, example);
        return count;
    }

    public int deleteById(Long id) {
        Example example = new Example(DeviceType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int count = deviceTypeMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "deviceCategoryId", "factoryId"};
        String[] legalLikeWords = { "name", "model", "remark", "deviceCategoryName", "factoryName"};
        String[] legalOrderWords = { "id"};
        PageHandler<DeviceType> pageHandler = new PageHandler<DeviceType>(DeviceType.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, deviceTypeMapper);
        return page;
    }

    public List<DeviceType> findAll() {
        Example example = new Example(DeviceType.class);
        List<DeviceType> deviceTypes = deviceTypeMapper.selectByExample(example);
        return deviceTypes;
    }
}
