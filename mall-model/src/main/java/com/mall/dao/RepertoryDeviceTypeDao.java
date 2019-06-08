package com.mall.dao;

import com.mall.mapper.RepertoryDeviceTypeMapper;
import com.mall.model.RepertoryDeviceType;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class RepertoryDeviceTypeDao {

    @Autowired
    private RepertoryDeviceTypeMapper repertoryDeviceTypeMapper;

    public RepertoryDeviceType findByRepertoryIdAndTypeId(Long repertoryId, Long typeId) {
        RepertoryDeviceType repertoryDeviceType = new RepertoryDeviceType();
        repertoryDeviceType.setRepertoryId(repertoryId);
        repertoryDeviceType.setTypeId(typeId);
        return repertoryDeviceTypeMapper.selectOne(repertoryDeviceType);
    }

    public int insert(RepertoryDeviceType repertoryDeviceType) {
        int count = repertoryDeviceTypeMapper.insert(repertoryDeviceType);
        return count;
    }

    public RepertoryDeviceType findById(Long id) {
        RepertoryDeviceType repertoryDeviceType = new RepertoryDeviceType();
        repertoryDeviceType.setId(id);
        return repertoryDeviceTypeMapper.selectOne(repertoryDeviceType);
    }

    public int updateById(RepertoryDeviceType deviceType) {
        Example example = new Example(RepertoryDeviceType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", deviceType.getId());
        int count = repertoryDeviceTypeMapper.updateByExample(deviceType, example);
        return count;
    }

    public int deleteById(Long id) {
        Example example = new Example(RepertoryDeviceType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int count = repertoryDeviceTypeMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "repertoryId", "typeId", "status"};
        String[] legalLikeWords = { "remark"};
        String[] legalOrderWords = { "createTime", "updateTime"};
        PageHandler<RepertoryDeviceType> pageHandler = new PageHandler<RepertoryDeviceType>(RepertoryDeviceType.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, repertoryDeviceTypeMapper);
        return page;
    }
}
