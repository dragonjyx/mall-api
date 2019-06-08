package com.mall.dao;

import com.mall.mapper.SendOrderDeviceMapper;
import com.mall.model.SendOrderDevice;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SendOrderDeviceDao {

    @Autowired
    private SendOrderDeviceMapper deviceMapper;

    public int insert(SendOrderDevice sendOrderDevice) {
        int count = deviceMapper.insert(sendOrderDevice);
        return count;
    }

    public int deleteId(Long id) {
        Example example = new Example(SendOrderDevice.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int count = deviceMapper.deleteByExample(example);
        return count;
    }

    public List<SendOrderDevice> findByOrderSn(String orderSn) {
        Example example = new Example(SendOrderDevice.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn", orderSn);
        List<SendOrderDevice> sendOrderDevices = deviceMapper.selectByExample(example);
        return sendOrderDevices;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "deviceSn", "orderSn", "sendSn", "deviceTypeId"};
        String[] legalLikeWords = { "deviceTypeName"};
        String[] legalOrderWords = { "id"};
        PageHandler<SendOrderDevice> pageHandler = new PageHandler<SendOrderDevice>(SendOrderDevice.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, deviceMapper);
        return page;
    }

    public SendOrderDevice findById(Long id) {
        SendOrderDevice sendOrderDevice = new SendOrderDevice();
        sendOrderDevice.setId(id);
        return deviceMapper.selectOne(sendOrderDevice);
    }

    public SendOrderDevice findBySendSnByDeviceSn(String sendSn, String deviceSn) {
        SendOrderDevice sendOrderDevice = new SendOrderDevice();
        sendOrderDevice.setDeviceSn(deviceSn);
        sendOrderDevice.setSendSn(sendSn);
        return deviceMapper.selectOne(sendOrderDevice);
    }
}
