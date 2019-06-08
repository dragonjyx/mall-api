package com.mall.dao;

import com.mall.mapper.SchoolDormMapper;
import com.mall.model.SchoolDorm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SchoolDormDao {

    @Autowired
    private SchoolDormMapper schoolDormMapper;

    public List<SchoolDorm> findAllDrom(Long schoolId) {
        Example example = new Example(SchoolDorm.class);
        if(schoolId != null){
            example.createCriteria().andEqualTo("schoolId",schoolId);
        }
        List<SchoolDorm> schoolDormList = schoolDormMapper.selectByExample(example);
        return schoolDormList;
    }


    public List<SchoolDorm> findBySchoolId(Long schoolId){
        Example example = new Example(SchoolDorm.class);
        example.createCriteria().andEqualTo("schoolId",schoolId);
        List<SchoolDorm> schoolDormList = schoolDormMapper.selectByExample(example);
        return schoolDormList;
    }


}
