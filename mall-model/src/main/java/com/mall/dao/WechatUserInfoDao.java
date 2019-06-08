package com.mall.dao;

import com.mall.mapper.WechatUserInfoMapper;
import com.mall.model.WechatUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 微信用户信息
 */
@Service
public class WechatUserInfoDao {

    @Autowired
    private WechatUserInfoMapper wechatUserInfoMapper;


    public WechatUserInfo findByOpenId(String openId) {
        Example example = new Example(WechatUserInfo.class);
        example.createCriteria().andEqualTo("openId",openId);
        List<WechatUserInfo> wechatUserInfoList = wechatUserInfoMapper.selectByExample(example);
        if(wechatUserInfoList.isEmpty()){
            return null;
        }
        return wechatUserInfoList.get(0);
    }

    public int updateWechatUserInfo(WechatUserInfo wechatUserInfo) {
        Example example = new Example(WechatUserInfo.class);
        example.createCriteria().andEqualTo("openId",wechatUserInfo.getOpenId());
        return wechatUserInfoMapper.updateByExampleSelective(wechatUserInfo,example);
    }

    public int addNewWechatInfo(WechatUserInfo wechatUserInfo) {
        return wechatUserInfoMapper.insertSelective(wechatUserInfo);
    }

    public WechatUserInfo findByPhoneNum(String phoneNum) {
        Example example = new Example(WechatUserInfo.class);
        example.createCriteria().andEqualTo("phoneNum",phoneNum);
        List<WechatUserInfo> wechatUserInfoList = wechatUserInfoMapper.selectByExample(example);
        if(wechatUserInfoList.isEmpty()){
            return null;
        }
        return wechatUserInfoList.get(0);
    }

    public WechatUserInfo findByUnionId(String unionId) {
        Example example = new Example(WechatUserInfo.class);
        example.createCriteria().andEqualTo("unionId",unionId);
        List<WechatUserInfo> wechatUserInfoList = wechatUserInfoMapper.selectByExample(example);
        if(wechatUserInfoList.isEmpty()){
            return null;
        }
        return wechatUserInfoList.get(0);
    }
}
