package com.mall.dao;

import com.mall.mapper.DeviceTypePartsMapper;
import com.mall.model.DeviceTypeParts;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class DeviceTypePartsDao {

    @Autowired
    private DeviceTypePartsMapper deviceTypePartsMapper;

    public DeviceTypeParts findByNameAndTypeId(String name, Long typeId) {

        DeviceTypeParts deviceTypeParts = new DeviceTypeParts();
        deviceTypeParts.setName(name);
        deviceTypeParts.setTypeId(typeId);
        deviceTypeParts = deviceTypePartsMapper.selectOne(deviceTypeParts);
        return deviceTypeParts;
    }

    public int insert(DeviceTypeParts deviceTypeParts) {
        int count = deviceTypePartsMapper.insert(deviceTypeParts);
        return count;
    }

    public DeviceTypeParts findById(Long id) {
        DeviceTypeParts deviceTypeParts = new DeviceTypeParts();
        deviceTypeParts.setId(id);
        deviceTypeParts = deviceTypePartsMapper.selectOne(deviceTypeParts);
        return deviceTypeParts;
    }

    public int updateById(DeviceTypeParts deviceTypeParts) {
        Example example = new Example(DeviceTypeParts.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", deviceTypeParts.getId());
        int count = deviceTypePartsMapper.updateByExample(deviceTypeParts, example);
        return count;
    }

    public int deleteById(Long id) {
        Example example = new Example(DeviceTypeParts.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int count = deviceTypePartsMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "price", "typeId"};
        String[] legalLikeWords = { "name"};
        String[] legalOrderWords = { "id"};
        PageHandler<DeviceTypeParts> pageHandler = new PageHandler<DeviceTypeParts>(DeviceTypeParts.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, deviceTypePartsMapper);
        return page;
    }
}
