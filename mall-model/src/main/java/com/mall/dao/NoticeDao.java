package com.mall.dao;

import com.mall.mapper.NoticeMapper;
import com.mall.model.Notice;
import com.mall.params.status.NoticeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class NoticeDao {

    @Autowired
    private NoticeMapper noticeMapper;

    public int addNotice(Notice notice){
        int result = noticeMapper.insertSelective(notice);
        return result;
    }

    public int updateNotice(Notice notice){
        int result = noticeMapper.updateByPrimaryKeySelective(notice);
        return result;
    }

    public List<Notice> findByTitleAndTypeAndStatus(String userId, String title, Integer type, Integer status, Date stateDate, Date endDate){
        return noticeMapper.findByTitleAndTypeAndStatus(userId,title,type,status,stateDate,endDate);
    }

    public Notice findById(long id){
        Example example = new Example(Notice.class);
        example.createCriteria().andEqualTo("id",id);
        List<Notice> noticeList = noticeMapper.selectByExample(example);
        if(noticeList.isEmpty()){
            return null;
        }
        return noticeList.get(0);
    }


    public int deleteNoticById(long id){
        Example example = new Example(Notice.class);
        example.createCriteria().andEqualTo("id",id);
        int result = noticeMapper.deleteByExample(example);
        return result;
    }


    //查询未发布的公告
    public List<Notice> findNoPublishNotice(){
        Example example = new Example(Notice.class);
        example.createCriteria().andEqualTo("status",NoticeStatus.NOT_ISSUE.value).andLessThan("startTime",new Date());
        List<Notice> noticeList = noticeMapper.selectByExample(example);
        return noticeList;
    }

}
