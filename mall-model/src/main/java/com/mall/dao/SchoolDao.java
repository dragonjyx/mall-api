package com.mall.dao;

import com.mall.mapper.SchoolMapper;
import com.mall.model.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SchoolDao {

    @Autowired
    private SchoolMapper schoolMapper;


    public List<School> findAllSchool(Integer type) {
        Example example = new Example(School.class);
        example.createCriteria().andEqualTo("type",type);
        List<School> schoolList = schoolMapper.selectByExample(example);
        return schoolList;
    }

    public School findById(Long id){
        Example example = new Example(School.class);
        example.createCriteria().andEqualTo("id",id);
        List<School> schoolList = schoolMapper.selectByExample(example);
        if(schoolList.isEmpty()){
            return null;
        }
        return schoolList.get(0);
    }


}
