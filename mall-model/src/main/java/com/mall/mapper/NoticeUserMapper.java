package com.mall.mapper;

import com.mall.model.NoticeUser;
import com.mall.params.resp.NoticeUserResp;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

import java.util.List;

public interface NoticeUserMapper extends Mapper<NoticeUser> {

    List<NoticeUserResp> findByUserId(@Param("userId") String userId);

}