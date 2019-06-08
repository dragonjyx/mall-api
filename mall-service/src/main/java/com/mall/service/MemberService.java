package com.mall.service;

import com.mall.model.Member;
import com.mall.model.MemberLocation;

public interface MemberService {

    String doLoginWithPhoneNum(String phoneNum, String ip);
    String doLogin(String phoneNum, String password, String ip);

    Member findByMemberId(String memberId);
    Member findByOpenId(String openId);

    int updateMemberInfo(Member member);
    int addNewMemberInfo(Member member);

    MemberLocation findMemberLocationByOpenId(String openId);

    int setMemberLocation(MemberLocation memberLocation);
}
