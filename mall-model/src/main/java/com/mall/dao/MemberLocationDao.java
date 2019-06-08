package com.mall.dao;

import com.mall.mapper.MemberLocationMapper;
import com.mall.model.MemberLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class MemberLocationDao {

    @Autowired
    private MemberLocationMapper memberLocationMapper;


    public MemberLocation findByOpenId(String openId) {
        Example example = new Example(MemberLocation.class);
        example.createCriteria().andEqualTo("openId",openId);
        List<MemberLocation> memberLocationList = memberLocationMapper.selectByExample(example);
        if (memberLocationList.isEmpty()){
            return null;
        }
        return memberLocationList.get(0);
    }

    public int addNewLocation(MemberLocation location) {
        return memberLocationMapper.insertSelective(location);
    }

    public int updateByOpenId(MemberLocation memberLocation) {
        Example example = new Example(MemberLocation.class);
        example.createCriteria().andEqualTo("openId",memberLocation.getOpenId());
        return memberLocationMapper.updateByExample(memberLocation,example);
    }
}
