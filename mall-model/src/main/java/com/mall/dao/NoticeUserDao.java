package com.mall.dao;

import com.mall.mapper.NoticeUserMapper;
import com.mall.model.NoticeUser;
import com.mall.params.resp.NoticeUserResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class NoticeUserDao {

    @Autowired
    private NoticeUserMapper noticeUserMapper;

    public int addNewNoticUser(NoticeUser noticeUser){
        int result = noticeUserMapper.insertSelective(noticeUser);
        return result;
    }

    public int deleteByNoticeId(Long noticeId){
        Example example = new Example(NoticeUser.class);
        example.createCriteria().andEqualTo("noticeId",noticeId);
        int result = noticeUserMapper.deleteByExample(example);
        return result;
    }

    public int deleteById(String userId,Long id){
        Example example = new Example(NoticeUser.class);
        example.createCriteria().andEqualTo("id",id).andEqualTo("uid",userId);
        int result = noticeUserMapper.deleteByExample(example);
        return result;
    }

    public NoticeUser findById(Long id){
        Example example = new Example(NoticeUser.class);
        example.createCriteria().andEqualTo("id",id);
        List<NoticeUser> noticeUserList = noticeUserMapper.selectByExample(example);
        if(noticeUserList.isEmpty()){
            return null;
        }
        return noticeUserList.get(0);
    }


    public int updateById(NoticeUser noticeUser){
        Example example = new Example(NoticeUser.class);
        example.createCriteria().andEqualTo("id",noticeUser.getId());
        int result = noticeUserMapper.updateByExampleSelective(noticeUser,example);
        return result;
    }



    public List<NoticeUserResp> findByUserId(String userId){
        List<NoticeUserResp> noticeUserRespList = noticeUserMapper.findByUserId(userId);
        return noticeUserRespList;
    }


    public int countMyNotice(String userId,Integer status){
        Example example = new Example(NoticeUser.class);
        example.createCriteria().andEqualTo("uid",userId).andEqualTo("status",status);
        int count = noticeUserMapper.selectCountByExample(example);
        return count;
    }


}
