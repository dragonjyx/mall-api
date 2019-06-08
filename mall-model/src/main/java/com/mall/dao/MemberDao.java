package com.mall.dao;

import com.mall.mapper.MemberMapper;
import com.mall.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Service
public class MemberDao {

    @Autowired
    private MemberMapper memberMapper;


    public int addNewMember(Member member){
        int result = memberMapper.insertSelective(member);
        return result;
    }


    public int updateMember(Member member){
        int result = memberMapper.updateByPrimaryKey(member);
        return result;
    }


    public Member findByPhoneNum(String phoneNum){
        Example example = new Example(Member.class);
        example.createCriteria().andEqualTo("mobile",phoneNum);
        List<Member> memberList = memberMapper.selectByExample(example);
        if(memberList.isEmpty()){
            return null;
        }
        return memberList.get(0);
    }


    public Member findByMemberId(String memberId){
        Example example = new Example(Member.class);
        example.createCriteria().andEqualTo("memberId",memberId);
        List<Member> memberList = memberMapper.selectByExample(example);
        if(memberList.isEmpty()){
            return null;
        }
        return memberList.get(0);
    }


    public Member findByUnionId(String unionId){
        Example example = new Example(Member.class);
        example.createCriteria().andEqualTo("unionId",unionId);
        List<Member> memberList = memberMapper.selectByExample(example);
        if(memberList.isEmpty()){
            return null;
        }
        return memberList.get(0);
    }








}
