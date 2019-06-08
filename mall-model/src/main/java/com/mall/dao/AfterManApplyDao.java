package com.mall.dao;

import com.mall.mapper.AfterManApplyMapper;
import com.mall.model.AfterManApply;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class AfterManApplyDao {

    @Autowired
    private AfterManApplyMapper afterManApplyMapper;

    public int insert(AfterManApply afterManApply) {
        int count = afterManApplyMapper.insert(afterManApply);
        return count;
    }

    public int updateByAfterSaleId(AfterManApply afterManApply) {
        Example example = new Example(AfterManApply.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("afterSaleId", afterManApply.getAfterSaleId());
        int count = afterManApplyMapper.updateByExample(afterManApply, example);
        return count;
    }

    public int deleteByAfterSaleId(Long afterSaleId) {
        Example example = new Example(AfterManApply.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("afterSaleId", afterSaleId);
        int count = afterManApplyMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "afterSaleId", "uid"};
        String[] legalLikeWords = { "remark"};
        String[] legalOrderWords = { "id", "distributionTime"};
        PageHandler<AfterManApply> pageHandler = new PageHandler<AfterManApply>(AfterManApply.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, afterManApplyMapper);
        return page;
    }

    public AfterManApply findById(Long id) {
        AfterManApply afterManApply = new AfterManApply();
        afterManApply.setId(id);
        return afterManApplyMapper.selectOne(afterManApply);
    }

    public int updateById(AfterManApply afterManApply) {
        Example example = new Example(AfterManApply.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", afterManApply.getId());
        int count = afterManApplyMapper.updateByExample(afterManApply, example);
        return count;
    }

    public AfterManApply findByAfterSaleIdAndUid(Long afterSaleId, String uid) {
        AfterManApply afterManApply = new AfterManApply();
        afterManApply.setAfterSaleId(afterSaleId);
        afterManApply.setUid(uid);
        return afterManApplyMapper.selectOne(afterManApply);
    }

    public AfterManApply findByAfterSaleId(Long afterSaleId) {
        AfterManApply afterManApply = new AfterManApply();
        afterManApply.setAfterSaleId(afterSaleId);
        return afterManApplyMapper.selectOne(afterManApply);
    }
}
