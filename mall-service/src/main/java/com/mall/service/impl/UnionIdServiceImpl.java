package com.mall.service.impl;

import com.mall.dao.UnionIdsDao;
import com.mall.model.UnionIds;
import com.mall.service.UnionIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UnionIdServiceImpl implements UnionIdService {

    @Autowired
    private UnionIdsDao unionIdsDao;


    @Override
    public int bindUnionIdAndOpenId(String unionId, String openId) {
        UnionIds unionIds = findByOpenId(openId);
        if(unionIds != null){
            return 1;//已经存在，不需要再绑定
        }
        unionIds = new UnionIds();
        unionIds.setOpenId(openId);
        unionIds.setUnionId(unionId);
        int result = unionIdsDao.addNew(unionIds);
        return result;
    }

    @Override
    public UnionIds findByOpenId(String openId) {
        UnionIds unionIds = unionIdsDao.findByOpenId(openId);
        return unionIds;
    }




}


