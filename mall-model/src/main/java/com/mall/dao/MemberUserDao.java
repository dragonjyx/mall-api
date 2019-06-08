package com.mall.dao;

import com.mall.mapper.MemberUserMapper;
import com.mall.model.MemberUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class MemberUserDao {

    @Autowired
    private MemberUserMapper memberUserMapper;

    public MemberUser findByMemberIdAndUserId(String memberId, String userId){
        Example example = new Example(MemberUser.class);
        example.createCriteria().andEqualTo("memberId",memberId).andEqualTo("userId",userId);
        List<MemberUser> memberUserList = memberUserMapper.selectByExample(example);
        if(memberUserList.isEmpty()){
            return null;
        }
        return memberUserList.get(0);
    }

    public int addMemberUser(MemberUser memberUser){
        int result = memberUserMapper.insertSelective(memberUser);
        return result;
    }




}
