package com.mall.dao;

import com.mall.mapper.ResourceMapper;
import com.mall.model.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Slf4j
@Service
public class ResourceDao {

    @Autowired
    private ResourceMapper resourceMapper;


    public int addResource(Resource resource){
        int result = resourceMapper.insertSelective(resource);
        return result;
    }

    public int deleteById(long id){
        Example example = new Example(Resource.class);
        example.createCriteria().andEqualTo("id",id);
        int result = resourceMapper.deleteByExample(example);
        return result;
    }

    public int updateResourceById(Resource resource){
        Long id = resource.getId();
        if(id == null){
            log.error("id is null");
            return 0;
        }
        int result =  resourceMapper.updateByPrimaryKeySelective(resource);
        return result;
    }


    public List<Resource> findByPlatformCode(String platformCode){
        Example example = new Example(Resource.class);
        example.createCriteria().andEqualTo("platformCode",platformCode);
        example.setOrderByClause("sort DESC");
        List<Resource> resourceList = resourceMapper.selectByExample(example);
        return resourceList;
    }

    public Resource findById(long id){
        Example example = new Example(Resource.class);
        example.createCriteria().andEqualTo("id",id);
        List<Resource> resourceList = resourceMapper.selectByExample(example);
        if(resourceList.isEmpty()){
            return null;
        }
        return resourceList.get(0);
    }

    public Resource findByResourceNo(String resourceNo){
        Example example = new Example(Resource.class);
        example.createCriteria().andEqualTo("resourceNo",resourceNo);
        List<Resource> resourceList = resourceMapper.selectByExample(example);
        if(resourceList.isEmpty()){
            return null;
        }
        return resourceList.get(0);
    }



    public Resource findByIdInTypes(long id,String platformCode,List<Object> types){
        Example example = new Example(Resource.class);
        example.createCriteria().andEqualTo("id",id).andIn("type",types).andEqualTo("platformCode",platformCode);
        List<Resource> resourceList = resourceMapper.selectByExample(example);
        if(resourceList.isEmpty()){
            return null;
        }
        return resourceList.get(0);
    }



    public List<Resource> findByParentId(long pid){
        Example example = new Example(Resource.class);
        example.createCriteria().andEqualTo("pid",pid);
        List<Resource> resourceList = resourceMapper.selectByExample(example);
        return resourceList;
    }



}
