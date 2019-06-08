package com.mall.dao;

import com.mall.mapper.DeviceMapper;
import com.mall.model.Device;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class DeviceDao {

    @Autowired
    private DeviceMapper deviceMapper;

    public Device findByDeviceSn(String deviceSn) {
        Device device = new Device();
        device.setDeviceSn(deviceSn);
        return deviceMapper.selectOne(device);
    }

    public Device findByDeviceId(String deviceId) {
        Device device = new Device();
        device.setDeviceId(deviceId);
        return deviceMapper.selectOne(device);
    }

    public int insert(Device device) {
        int count = deviceMapper.insert(device);
        return count;
    }

    public Device findById(Long id) {
        Device device = new Device();
        device.setId(id);
        return deviceMapper.selectOne(device);
    }

    public int updateById(Device device) {
        Example example = new Example(Device.class);
        Example.Criteria criteria =  example.createCriteria();
        criteria.andEqualTo("id", device.getId());
        int count = deviceMapper.updateByExample(device, example);
        return count;
    }

    public int deleteByDeviceSn(String deviceSn) {
        Example example = new Example(Device.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deviceSn", deviceSn);
        int count = deviceMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "deviceId", "deviceSn", "typeId", "repertoryId", "status"};
        String[] legalLikeWords = { "repertoryName", "remark"};
        String[] legalOrderWords = { "id", "createTime", "updateTime"};
        PageHandler<Device> pageHandler = new PageHandler<Device>(Device.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, deviceMapper);
        return page;
    }
}
