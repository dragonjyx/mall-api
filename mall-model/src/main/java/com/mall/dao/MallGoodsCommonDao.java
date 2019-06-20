package com.mall.dao;

import com.mall.mapper.MallGoodsCommonMapper;
import com.mall.model.MallGoodsCommon;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import com.mall.params.resp.MallGoodsCommonResp;
import com.mall.params.status.GoodsStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class MallGoodsCommonDao {

    @Autowired
    private MallGoodsCommonMapper mallGoodsCommonMapper;


    public List<MallGoodsCommon> findMallGoodsCommonByName(String name,Long typeId,Long schoolDormId) {
        Example example = new Example(MallGoodsCommon.class);
        Example.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            name = "%" + name + "%";
            criteria.andLike("name",name);
        }

        if(typeId != null){
            criteria.andEqualTo("typeId",typeId);
        }
        if(schoolDormId != null){
            criteria.andEqualTo("schoolDormId",schoolDormId);
        }
        criteria.andEqualTo("isDelete",0).andEqualTo("status",GoodsStatus.PUTAWAY.value);
        List<MallGoodsCommon> mallGoodsCommons = mallGoodsCommonMapper.selectByExample(example);
        return mallGoodsCommons;
    }



    public MallGoodsCommon findByName(String name) {
        MallGoodsCommon mallGoodsCommon = new MallGoodsCommon();
        mallGoodsCommon.setName(name);
        mallGoodsCommon.setIsDelete(0);
        mallGoodsCommon = mallGoodsCommonMapper.selectOne(mallGoodsCommon);
        return mallGoodsCommon;
    }

    public int insert(MallGoodsCommon mallGoodsCommon) {
        int count = mallGoodsCommonMapper.insert(mallGoodsCommon);
        return count;
    }

    public MallGoodsCommon findBySn(String sn) {
        MallGoodsCommon mallGoodsCommon = new MallGoodsCommon();
        mallGoodsCommon.setSn(sn);
        mallGoodsCommon.setIsDelete(0);
        mallGoodsCommon = mallGoodsCommonMapper.selectOne(mallGoodsCommon);
        return mallGoodsCommon;
    }

    public int updateBySn(MallGoodsCommon mallGoodsCommon) {
        Example example = new Example(MallGoodsCommon.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sn", mallGoodsCommon.getSn());
        int count = mallGoodsCommonMapper.updateByExample(mallGoodsCommon, example);
        return count;
    }

    public int deleteBySn(String sn) {

        Example example = new Example(MallGoodsCommon.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sn", sn);
        int count = mallGoodsCommonMapper.deleteByExample(example);
        return count;
    }

    public Page page(Page page) {
        String[] legalKeyWords = { "id", "sn", "categoryId", "brandId", "typeId", "status", "freight", "isVat",
                "isCommend", "isHot", "regionId", "isDelete"};
        String[] legalLikeWords = { "jingle", "name", "categoryName", "brandName", "commonDesc", "region", "guarantee",
                "unit", "remark"};
        String[] legalOrderWords = { "createTime", "sellTime"};
        PageHandler<MallGoodsCommon> pageHandler = new PageHandler<MallGoodsCommon>(MallGoodsCommon.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, mallGoodsCommonMapper);
        return page;
    }

    public List<MallGoodsCommon> findBySns(List<Object> goodsSns) {
        Example example = new Example(MallGoodsCommon.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("sn", goodsSns);
        List<MallGoodsCommon> mallGoodsCommons = mallGoodsCommonMapper.selectByExample(example);
        return mallGoodsCommons;
    }


    public Page multiListPage(Page page) {
        Integer pageSize = page.getPageSize();
        Integer startLine = page.getStartLine();
        Map<String, Object> keywords = page.getKeywords();
        String name = (String) keywords.get("name");
        if (!StringUtils.isEmpty(name)) {
            name = "%" + name + "%";
        }
        String brandName = (String) keywords.get("brandName");
        if (!StringUtils.isEmpty(name)) {
            brandName = "%" + brandName + "%";
        }
        Integer isCommend = (Integer) keywords.get("isCommend");
        Integer isHot = (Integer) keywords.get("isHot");
        Integer regionId = (Integer) keywords.get("regionId");
        String bottomPrice = (String) keywords.get("bottomPrice");
        String topPrice = (String) keywords.get("topPrice");
        Integer categoryId = (Integer) keywords.get("categoryId");
        if (!StringUtils.isEmpty(topPrice) && StringUtils.isEmpty(bottomPrice)) {
            bottomPrice = "0";
        }
        String orderBy = page.getOrderBy();
        List<MallGoodsCommonResp> mallGoodsCommonResp = mallGoodsCommonMapper.findPage(categoryId, name, brandName, isCommend, isHot,
                regionId, bottomPrice, topPrice, startLine, pageSize, orderBy);
        int count = mallGoodsCommonMapper.findPageCount(categoryId, name, brandName, isCommend, isHot,
                regionId, bottomPrice, topPrice);
        page.setList(mallGoodsCommonResp);
        page.setTotalRows(count);
        page.initDatas();
        return page;
    }
}
