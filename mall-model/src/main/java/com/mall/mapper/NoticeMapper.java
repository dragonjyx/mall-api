package com.mall.mapper;

import com.mall.model.Notice;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface NoticeMapper extends Mapper<Notice> {

    List<Notice> findByTitleAndTypeAndStatus(@Param("userId") String userId, @Param("title") String title, @Param("type") Integer type, @Param("status") Integer status, @Param("stateDate") Date stateDate, @Param("endDate") Date endDate);

}