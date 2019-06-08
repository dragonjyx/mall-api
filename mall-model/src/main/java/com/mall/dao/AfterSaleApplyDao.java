package com.mall.dao;

import com.mall.mapper.AfterSaleApplyMapper;
import com.mall.model.AfterSaleApply;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class AfterSaleApplyDao {

    @Autowired
    private AfterSaleApplyMapper afterSaleApplyMapper;

    public AfterSaleApply findByDeviceTypeIdAndErrType(Long deviceTypeId, String errType) {
        AfterSaleApply afterSaleApply = new AfterSaleApply();
        return afterSaleApplyMapper.selectOne(afterSaleApply);
    }

    public int insert(AfterSaleApply afterSaleApply) {
        int count = afterSaleApplyMapper.insert(afterSaleApply);
        return count;
    }

    public AfterSaleApply findById(Long id) {
        AfterSaleApply afterSaleApply = new AfterSaleApply();
        afterSaleApply.setId(id);
        return afterSaleApplyMapper.selectOne(afterSaleApply);
    }

    public int updateById(AfterSaleApply afterSaleApply) {
        Example example = new Example(AfterSaleApply.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", afterSaleApply.getId());
        int count = afterSaleApplyMapper.updateByExample(afterSaleApply, example);
        return count;
    }

    public int deleteById(Long id) {
        Example example = new Example(AfterSaleApply.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int count = afterSaleApplyMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "orderSn", "deviceSn", "uid", "agentId", "status", "dispenseStatus",
                "isEvaluation", "phone"};
        String[] legalLikeWords = { "agentType", "types", "detail", "province", "city", "district", "address",
                "remark"};
        String[] legalOrderWords = { "id", "createTime", "finishedTime"};
        PageHandler<AfterSaleApply> pageHandler = new PageHandler<AfterSaleApply>(AfterSaleApply.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, afterSaleApplyMapper);
        return page;
    }

    public int updateSelectiveById(AfterSaleApply apply) {
        Example example = new Example(AfterSaleApply.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", apply.getId());
        int count = afterSaleApplyMapper.updateByExampleSelective(apply, example);
        return count;
    }

    public AfterSaleApply findByUidAndId(String uid, Long id) {
        AfterSaleApply afterSaleApply = new AfterSaleApply();
        afterSaleApply.setId(id);
        afterSaleApply.setUid(uid);
        return afterSaleApplyMapper.selectOne(afterSaleApply);
    }
}
