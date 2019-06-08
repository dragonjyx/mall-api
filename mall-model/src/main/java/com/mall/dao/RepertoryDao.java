package com.mall.dao;

import com.mall.mapper.RepertoryMapper;
import com.mall.model.Repertory;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class RepertoryDao {

    @Autowired
    private RepertoryMapper repertoryMapper;

    public Repertory findByName(String name) {
        Repertory repertory = new Repertory();
        repertory.setName(name);
        return repertoryMapper.selectOne(repertory);
    }

    public int insert(Repertory repertory) {
        int count = repertoryMapper.insert(repertory);
        return count;
    }

    public Repertory findById(Long id) {
        Repertory repertory = new Repertory();
        repertory.setId(id);
        return repertoryMapper.selectOne(repertory);
    }

    public int updateById(Repertory rep) {
        Example example = new Example(Repertory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", rep.getId());
        int count = repertoryMapper.updateByExample(rep, example);
        return count;
    }

    public int deleteById(Long id) {
        Example example = new Example(Repertory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int count = repertoryMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "status", "uid"};
        String[] legalLikeWords = { "name", "address", "abstracts", "remark"};
        String[] legalOrderWords = { "createTime", "updateTime"};
        PageHandler<Repertory> pageHandler = new PageHandler<Repertory>(Repertory.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, repertoryMapper);
        return page;
    }
}
