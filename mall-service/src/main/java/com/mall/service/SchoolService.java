package com.mall.service;

import com.mall.model.SchoolDorm;
import com.mall.model.UserSchoolDorm;
import com.mall.model.UserSchoolDormManage;
import com.mall.model.UserSchoolDormSupplier;
import com.mall.params.resp.SchoolResp;

import java.util.List;

public interface SchoolService {

    List<SchoolResp> findSchoolTreeList(Integer type);

    List<UserSchoolDorm> findByUserSchoolDorm(String userId);

    List<UserSchoolDormManage> findByUserId(String userId);

    UserSchoolDorm findByUserIdAndDromId(String userId,Long dormId);

    UserSchoolDormManage findByDormId(Long dormId);

    List<SchoolDorm> findBySchoolId(Long schoolId);

    List<UserSchoolDormSupplier> findSupplierByUserId(String userId);
}
