package com.mall.dao;

import com.mall.mapper.MallAddressMapper;
import com.mall.model.MallAddress;
import com.mall.params.page.Page;
import com.mall.params.page.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class MallAddressDao {

    @Autowired
    private MallAddressMapper mallAddressMapper;

    public Integer findAddressNumByUid(String uid) {

        Example example =  new Example(MallAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid",uid);

        return mallAddressMapper.selectCountByExample(example);
    }

    public Boolean updateUidIsDefault(String addressSn, String uid) {

        MallAddress mallAddress = new MallAddress();
        mallAddress.setIsDefault(0);
        Example example = new Example(MallAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andNotEqualTo("addressSn", addressSn);
        criteria.andEqualTo("uid", uid);
        int res = mallAddressMapper.updateByExampleSelective(mallAddress, example);
        return true;
    }

    public Integer addNewAddress(MallAddress mallAddress) {
        return mallAddressMapper.insertSelective(mallAddress);
    }

    public MallAddress findAddressByAddressSn(String addressSn) {

        MallAddress mallAddress = new MallAddress();
        mallAddress.setAddressSn(addressSn);
        mallAddress = mallAddressMapper.selectOne(mallAddress);

        return mallAddress;
    }

    public int updateMallAddress(MallAddress mallAddress) {
        Example example = new Example(MallAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("addressSn", mallAddress.getAddressSn());
        return mallAddressMapper.updateByExampleSelective(mallAddress, example);
    }

    public int deleteByAddressSn(String addressSn) {
        Example example = new Example(MallAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("addressSn", addressSn);
        return mallAddressMapper.deleteByExample(example);
    }

    public List<MallAddress> findAllMallAddressByUid(String uid) {
        Example example = new Example(MallAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid", uid);
        return mallAddressMapper.selectByExample(example);
    }

    public Page pageAddress(Page page) {
        String[] legalKeyWords = { "id", "uid", "addressSn", "isDefault"};
        String[] legalLikeWords = { "consignee", "country", "province", "city", "district", "address", "zipcode", "phone"};
        String[] legalOrderWords = { "isDefault"};
        PageHandler<MallAddress> pageHandler = new PageHandler<MallAddress>(MallAddress.class);
        pageHandler.setLegalKeyWordsArray(legalKeyWords);
        pageHandler.setLegalLikeKeyWordsArray(legalLikeWords);
        pageHandler.setLegalOrderByLegalArray(legalOrderWords);
        page = pageHandler.listPage(page, mallAddressMapper);
        return page;
    }

    public int setDefault(String memberId, String addressSn, Integer isDefault) {
        return mallAddressMapper.setAddressDetault(memberId,addressSn,isDefault);
    }

    public MallAddress findDefaultAddress(String memberId) {
        Example example = new Example(MallAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid", memberId).andEqualTo("isDefault",1);
        List<MallAddress> addressList = mallAddressMapper.selectByExample(example);
        if(addressList.isEmpty()){
            return null;
        }
        return addressList.get(0);
    }
}
