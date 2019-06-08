package com.mall.dao;

import com.mall.mapper.AfterSaleEvaluationMapper;
import com.mall.model.AfterSaleEvaluation;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class AfterSaleEvaluationDao {

    @Autowired
    private AfterSaleEvaluationMapper afterSaleEvaluationMapper;

    public int insert(AfterSaleEvaluation afterSaleEvaluation) {
        int count = afterSaleEvaluationMapper.insert(afterSaleEvaluation);
        return count;
    }

    public int updateByAfterSaleId(AfterSaleEvaluation afterSaleEvaluation) {
        Example example = new Example(AfterSaleEvaluation.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("afterSaleId", afterSaleEvaluation.getAfterSaleId());
        int count = afterSaleEvaluationMapper.updateByExample(afterSaleEvaluation, example);
        return count;
    }

    public int deleteByAfterSaleId(Long afterSaleId) {
        Example example = new Example(AfterSaleEvaluation.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("afterSaleId", afterSaleId);
        int count = afterSaleEvaluationMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "afterSaleId", "uid"};
        String[] legalLikeWords = { "msg", "name"};
        String[] legalOrderWords = { "id", "score", "createTime"};
        PageHandler<AfterSaleEvaluation> pageHandler = new PageHandler<AfterSaleEvaluation>(AfterSaleEvaluation.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, afterSaleEvaluationMapper);
        return page;
    }

    public AfterSaleEvaluation findById(Long id) {
        AfterSaleEvaluation afterSaleEvaluation = new AfterSaleEvaluation();
        afterSaleEvaluation.setId(id);
        return afterSaleEvaluationMapper.selectOne(afterSaleEvaluation);
    }

    public int updateById(AfterSaleEvaluation afterSaleEvaluation) {
        Example example = new Example(AfterSaleEvaluation.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", afterSaleEvaluation.getId());
        int count = afterSaleEvaluationMapper.updateByExample(afterSaleEvaluation, example);
        return count;
    }

    public AfterSaleEvaluation findByAfterSaleIdAndUid(Long afterSaleId, String uid) {
        AfterSaleEvaluation afterSaleEvaluation = new AfterSaleEvaluation();
        afterSaleEvaluation.setAfterSaleId(afterSaleId);
        afterSaleEvaluation.setUid(uid);
        return afterSaleEvaluationMapper.selectOne(afterSaleEvaluation);
    }

    public AfterSaleEvaluation findByAfterSaleId(Long afterSaleId) {
        AfterSaleEvaluation afterSaleEvaluation = new AfterSaleEvaluation();
        afterSaleEvaluation.setAfterSaleId(afterSaleId);
        return afterSaleEvaluationMapper.selectOne(afterSaleEvaluation);
    }
}
