package com.mall.service.impl;

import com.java.redis.util.UniqueIdGenerate;
import com.java.utils.code.MD5Util;
import com.mall.dao.MemberDao;
import com.mall.dao.MemberLocationDao;
import com.mall.dao.UnionIdsDao;
import com.mall.model.Member;
import com.mall.model.MemberLocation;
import com.mall.model.UnionIds;
import com.mall.params.status.UserStatus;
import com.mall.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 会员
 */
@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;


    @Autowired
    private MemberLocationDao memberLocationDao;


    private static final String MEMBER_ID = "MEMBER_ID";

    @Override
    public String doLoginWithPhoneNum(String phoneNum,String ip) {
        if(StringUtils.isEmpty(phoneNum)){
            return null;
        }
        String memberId = null;
        Date now = new Date();
        Member member= memberDao.findByPhoneNum(phoneNum);
        if(member == null){//新增一个用户
            memberId = UniqueIdGenerate.getId(MEMBER_ID,16);
            member = new Member();
            member.setMemberId(memberId);
            member.setCreateDate(now);
            member.setUpdateDate(now);
            member.setCurrentLoginDate(now);
            member.setMobile(phoneNum);
            member.setStatus(UserStatus.CHECKED.value);
            member.setCurrentLoginIp(ip);
            member.setIsDelete(false);
            member.setRegisterIp(ip);
            member.setRegisterDate(now);
            member.setLoginNum(1L);
            member.setLoginErrorTimes(0);
            member.setLastLoginDate(now);
            int result = memberDao.addNewMember(member);
            if(result != 1){
                return null;
            }
        }else{//已经存在用户
            memberId = member.getMemberId();
            Long loginNum = member.getLoginNum();
            if(loginNum == null){
                loginNum = 0L;
            }
            loginNum++;
            member.setMemberId(memberId);
            member.setUpdateDate(now);
            member.setCurrentLoginDate(now);
            member.setCurrentLoginIp(ip);
            member.setIsDelete(false);
            member.setLoginNum(loginNum);
            member.setLastLoginDate(now);
            int result = memberDao.updateMember(member);
            log.warn("会员登录结果:{}",result);
        }
        return memberId;
    }

    @Override
    public String doLogin(String phoneNum, String password, String ip) {
        if(StringUtils.isEmpty(phoneNum)){
            return null;
        }
        if(StringUtils.isEmpty(password)){
            return null;
        }
        Member member = memberDao.findByPhoneNum(phoneNum);
        if(member == null){
            log.error("会员不存在");
            return null;
        }

        String dbPasswd = member.getPassword();
        String key      = member.getPkey();
        String md5Pwd   = MD5Util.md5Encrypt32(key + MD5Util.md5Encrypt32(password));

        Date now = new Date();
        if (!md5Pwd.equals(dbPasswd)) {
            Integer errorTimes = member.getLoginErrorTimes();
            if(errorTimes == null){
                errorTimes = 0;
            }
            errorTimes++;
            member.setUpdateDate(now);
            member.setLoginErrorTimes(errorTimes);
            int result = memberDao.updateMember(member);
            log.warn("会员更新登录结果:{}",result);
            return null;
        }



        Long loginNum = member.getLoginNum();
        if(loginNum == null){
            loginNum = 0L;
        }
        loginNum++;
        member.setUpdateDate(now);
        member.setCurrentLoginDate(now);
        member.setCurrentLoginIp(ip);
        member.setIsDelete(false);
        member.setLoginNum(loginNum);
        member.setLastLoginDate(now);
        member.setUpdateDate(now);
        member.setLoginErrorTimes(0);

        int result = memberDao.updateMember(member);
        log.warn("会员更新登录结果:{}",result);
        return member.getMemberId();
    }

    @Override
    public Member findByMemberId(String memberId) {
        Member member = memberDao.findByMemberId(memberId);
        return member;
    }

    @Autowired
    private UnionIdsDao unionIdsDao;

    @Override
    public Member findByOpenId(String openId) {
        UnionIds unionIds = unionIdsDao.findByOpenId(openId);
        if(unionIds == null){
            log.error("xxxxxxxxxxxxx unionIds is null xxxxxxxxxxxxx");
            return null;
        }
        Member member = memberDao.findByUnionId(unionIds.getUnionId());
        return member;
    }


    @Override
    public int updateMemberInfo(Member member) {
        int result = memberDao.updateMember(member);
        return result;
    }

    @Override
    public int addNewMemberInfo(Member member) {
        Date now = new Date();
        member.setUpdateDate(now);
        member.setCurrentLoginDate(now);
        member.setIsDelete(false);
        member.setLoginNum(1l);
        member.setLastLoginDate(now);
        member.setUpdateDate(now);
        member.setLoginErrorTimes(0);
        String memberId = UniqueIdGenerate.getSnowflakeId(MEMBER_ID,32);
        member.setMemberId(memberId);

        int result = memberDao.addNewMember(member);
        return result;
    }

    @Override
    public MemberLocation findMemberLocationByOpenId(String openId) {
        MemberLocation memberLocation = memberLocationDao.findByOpenId(openId);
        return memberLocation;
    }

    @Override
    public int setMemberLocation(MemberLocation memberLocation) {
        MemberLocation location = memberLocationDao.findByOpenId(memberLocation.getOpenId());
        if(location == null){
            return memberLocationDao.addNewLocation(memberLocation);
        }
        return memberLocationDao.updateByOpenId(memberLocation);
    }
}
