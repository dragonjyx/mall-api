package com.mall.service.impl;

import com.mall.dao.*;
import com.mall.mapper.UserSchoolDormSupplierMapper;
import com.mall.model.*;
import com.mall.params.resp.SchoolResp;
import com.mall.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.nio.cs.US_ASCII;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {


    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private SchoolDormDao schoolDormDao;

    @Autowired
    private UserSchoolDormDao userSchoolDormDao;

    @Autowired
    private UserSchoolDormManageDao userSchoolDormManageDao;

    @Autowired
    private UserSchoolDormSupplierDao userSchoolDormSupplierDao;

    @Override
    public List<SchoolResp> findSchoolTreeList(Integer type) {
        List<School> schoolList = schoolDao.findAllSchool(type);

        List<SchoolResp> schoolRespList = new ArrayList<SchoolResp>();
        List<SchoolResp> dormList = null;

        SchoolResp schoolResp = null;
        SchoolResp schoolDormResp = null;

        List<SchoolDorm> schoolDormList = null;
        for (School school : schoolList) {
            schoolResp = new SchoolResp();
            long id = school.getId();
            schoolResp.setId(id);
            schoolResp.setSchoolName(school.getName());

            dormList = new ArrayList<SchoolResp>();

            schoolDormList = schoolDormDao.findAllDrom(school.getId());
            for (SchoolDorm schoolDorm : schoolDormList){
                schoolDormResp = new SchoolResp();
                schoolDormResp.setId(id);
                schoolDormResp.setSchoolName(school.getName());
                schoolDormResp.setDormNum(schoolDorm.getDormNum());
                schoolDormResp.setDormId(schoolDorm.getId());
                schoolDormResp.setDormName(schoolDorm.getName());
                dormList.add(schoolDormResp);
            }

            if(!dormList.isEmpty()){
                schoolResp.setLists(dormList);
            }
            schoolRespList.add(schoolResp);

        }
        return schoolRespList;
    }

    @Override
    public List<UserSchoolDorm> findByUserSchoolDorm(String userId) {
        List<UserSchoolDorm>  userSchoolDormList = userSchoolDormDao.findByUserId(userId);
        return userSchoolDormList;
    }

    @Override
    public List<UserSchoolDormManage> findByUserId(String userId) {
        List<UserSchoolDormManage> userSchoolDormManages = userSchoolDormManageDao.findByUserId(userId);
        return userSchoolDormManages;
    }

    @Override
    public UserSchoolDorm findByUserIdAndDromId(String userId, Long dormId) {
        UserSchoolDorm userSchoolDorm = userSchoolDormDao.findByUserIdAndDromId(userId,dormId);
        return userSchoolDorm;
    }

    @Override
    public UserSchoolDormManage findByDormId(Long dormId) {
        UserSchoolDormManage userSchoolDormManage = userSchoolDormManageDao.findByDormId(dormId);
        return userSchoolDormManage;
    }

    @Override
    public List<SchoolDorm> findBySchoolId(Long schoolId) {
        List<SchoolDorm> schoolDormList = schoolDormDao.findBySchoolId(schoolId);
        return schoolDormList;
    }

    @Override
    public List<UserSchoolDormSupplier> findSupplierByUserId(String userId) {
        return userSchoolDormSupplierDao.findByUserId(userId);
    }
}

