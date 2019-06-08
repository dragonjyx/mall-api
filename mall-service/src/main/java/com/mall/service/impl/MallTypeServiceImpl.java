package com.mall.service.impl;

import com.mall.dao.MallTypeDao;
import com.mall.model.MallType;
import com.mall.service.MallTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MallTypeServiceImpl implements MallTypeService {

    @Autowired
    private MallTypeDao mallTypeDao;


    @Override
    public List<MallType> findAllMallType() {
        List<MallType> mallTypeList = mallTypeDao.findAllType();
        return mallTypeList;
    }
}
