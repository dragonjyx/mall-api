package com.mall.dao;

import java.util.List;

import com.mall.mapper.AfterSaleMemberDeviceMapper;
import com.mall.model.AfterSaleMemberDevice;
import com.mall.params.resp.MemberAddEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfterSaleMemberDeviceDao {

    @Autowired
    private AfterSaleMemberDeviceMapper afterSaleMemberDeviceMapper;

    public AfterSaleMemberDevice findByMembersDevice(String memberId, String deviceSn, String goodsSn, String goodsCode){
        AfterSaleMemberDevice afterSaleMemberDevice = new AfterSaleMemberDevice();
        afterSaleMemberDevice.setMemberId(memberId);
        afterSaleMemberDevice.setDeviceSn(deviceSn);
        afterSaleMemberDevice.setGoodsSn(goodsSn);
        afterSaleMemberDevice.setGoodsCode(goodsCode);
        AfterSaleMemberDevice  saleMemberDevice= afterSaleMemberDeviceMapper.selectOne(afterSaleMemberDevice);
        return saleMemberDevice;
    }


    public int addMembersDevice(AfterSaleMemberDevice afterSaleMemberDevice){
        int result = afterSaleMemberDeviceMapper.insertSelective(afterSaleMemberDevice);
        return result;
    }

    public List<MemberAddEquipment> findMemberEquitments(String memberId, long categoryId){
        List<MemberAddEquipment> memberAddEquipmentList= afterSaleMemberDeviceMapper.findByCategoryIdAndMemberId(memberId,categoryId);
        return memberAddEquipmentList;
    }





}
