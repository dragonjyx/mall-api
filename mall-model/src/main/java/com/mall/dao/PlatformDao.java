package com.mall.dao;

import com.mall.mapper.PlatformMapper;
import com.mall.model.Platform;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Slf4j
@Service
public class PlatformDao {

    @Autowired
    private PlatformMapper platformMapper;

    public int addPlatform(Platform platform){
        if(StringUtils.isEmpty(platform.getCode())){
            log.error("code is null");
            return 0;
        }
        int result = platformMapper.insertSelective(platform);
        return result;
    }

    public int updatePlatform(Platform platform){
        if(StringUtils.isEmpty(platform.getCode())){
            log.error("code is null");
            return 0;
        }
        int result = platformMapper.insertSelective(platform);
        return result;
    }


    public int delPlatform(long id){
        Example example = new Example(Platform.class);
        example.createCriteria().andEqualTo("id",id);
        int result = platformMapper.deleteByExample(example);
        return result;
    }


    public int updatePlatformByCode(Platform platform){
        Example example = new Example(Platform.class);
        example.createCriteria().andEqualTo("code",platform.getCode());
        int result = platformMapper.updateByExampleSelective(platform,example);
        return result;
    }


    public List<Platform> findByNameOrCode(String name,String code,Integer status){
        List<Platform> platformList  = platformMapper.findByNameOrCode(name,code,status);
        return platformList;
    }


    public int updatePlatformStatus(long id,int status){
        int result = platformMapper.updateStatus(id,status);
        return result;
    }

    public Platform findById(long id){
        Example example = new Example(Platform.class);
        example.createCriteria().andEqualTo("id",id);
        List<Platform> platformList  = platformMapper.selectByExample(example);
        if(platformList.isEmpty()){
            return null;
        }
        return platformList.get(0);
    }

    public Platform findByCode(String code){
        Example example = new Example(Platform.class);
        example.createCriteria().andEqualTo("code",code);
        List<Platform> platformList  = platformMapper.selectByExample(example);
        if(platformList.isEmpty()){
            return null;
        }
        return platformList.get(0);
    }




}
