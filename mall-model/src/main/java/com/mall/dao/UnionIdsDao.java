package com.mall.dao;

import com.mall.mapper.UnionIdsMapper;
import com.mall.model.UnionIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UnionIdsDao {

    @Autowired
    private UnionIdsMapper unionIdsMapper;


    public UnionIds findByOpenId(String openId){
        Example example = new Example(UnionIds.class);
        example.createCriteria().andEqualTo("openId",openId);
        List<UnionIds> unionIdsList = unionIdsMapper.selectByExample(example);
        if(unionIdsList.isEmpty()){
            return null;
        }
        return unionIdsList.get(0);
    }

    public int addNew(UnionIds unionIds){
        int result = unionIdsMapper.insert(unionIds);
        return result;
    }


    public int delete(String openId,String unionId){
        Example example = new Example(UnionIds.class);
        example.createCriteria().andEqualTo("openId",openId).andEqualTo("unionId",unionId);
        int result = unionIdsMapper.deleteByExample(example);
        return result;
    }




}
