package com.mall.params.page;

import org.apache.ibatis.session.RowBounds;
import org.springframework.cglib.beans.BeanCopier;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * <p>
 * 分页查询--只能单表分页查询
 * <p>
 */
public class PageHandler<T> {

    // 降序
    public static final String ORDERBY_PATTERN_DESC = "DESC";
    // 升序
    public static final String ORDERBY_PATTERN_ASC = "ASC";

    private String[] legalOrderByLegalArray;
    private String[] legalKeyWordsArray;
    private String[] legalTimeKeyWordsArray;
    private String[] legalLikeKeyWordsArray;
    private String[] legalInkeyWordsArray;
    private Class<T> beanClass;
    private BeanCopier copier;

    public PageHandler() {

    }

    public PageHandler(Class<T> cls) {
        beanClass = cls;
    }

    public Page listPage(Class<T> cls, Page page, Mapper<T> dao) {

        Page requestPage = new Page(page.getCurrentPage(), page.getPageSize());
        Map<String, Object> keywordMap = page.getKeywords();

        Example example = new Example(cls);
        for (Iterator<String> iterator = keywordMap.keySet().iterator(); iterator
                .hasNext();) {
            String key = iterator.next().toString();
            example.createCriteria().andEqualTo(key, keywordMap.get(key));
        }
        example.setOrderByClause(page.getOrderBy());

        RowBounds rowBounds = new RowBounds(requestPage.getCurrentPage(),
                requestPage.getPageSize());

        List<T> lists = dao.selectByExampleAndRowBounds(example, rowBounds);
        int totalRows = dao.selectCountByExample(example);

        page.setList(lists);
        page.setTotalRows(totalRows);
        page.initDatas();

        return page;
    }

    /**
     * 不对查询结果进行类型转换
     *
     * @param page
     * @param dao
     * @return
     */
    public Page listPage(Page page, Mapper dao) {

        return this.listPage(page, dao, null);
    }

    /**
     * 按 voClass 类型对查询结果进行类型转换
     *
     * @param page
     * @param dao
     * @param voClass
     * @return
     */
    public Page listPage(Page page, Mapper dao, Class voClass) {

        Map<String, Object> keywordMap = new HashMap<String, Object>();

        // 查询条件
        Example example = new Example(beanClass);
        Example.Criteria criteria = example.createCriteria();

        // 判断时间类型的请求条件
        if (legalTimeKeyWordsArray != null) {
            for (String timeKeyWord : legalTimeKeyWordsArray) {
                String keyWordsValue = (String) page.getKeywords().get(
                        timeKeyWord);
                // 不存在些字段的相关值
                if (keyWordsValue == null) {
                    continue;
                }
                String[] values = keyWordsValue.split(",");
                // 值不合法
                if (values.length != 2) {
                    continue;
                }
                criteria.andBetween(timeKeyWord, values[0], values[1]);
            }
        }

        //判断like类型的请求条件
        if(legalLikeKeyWordsArray != null)
        {
            for (String likeKeyWord : legalLikeKeyWordsArray) {
                String keyWordsValue = (String) page.getKeywords().get(likeKeyWord);
                // 不存在些字段的相关值
                if (keyWordsValue == null) {
                    continue;
                }
                criteria.andLike(likeKeyWord, String.format("%%%s%%", keyWordsValue));
                //criteria.andLike(likeKeyWord, "%"+keyWordsValue+"%");
            }
        }

        if(legalInkeyWordsArray != null)
        {
            for(String inKeyWord : legalInkeyWordsArray) {
                List<Object> keyWordsValue = null ;
                try{
                    keyWordsValue = (List) page.getKeywords().get(inKeyWord);
                }catch(Exception e){
                    e.printStackTrace() ;
                }
                // 不存在些字段的相关值
                if (keyWordsValue == null) {
                    continue;
                }
                criteria.andIn(inKeyWord, keyWordsValue) ;
            }
        }

        // 过滤不合法的请求条件
        if (legalKeyWordsArray != null) {
            keywordMap = PageHandler.lawlessKeyWordsCheck(page.getKeywords(),
                    legalKeyWordsArray);
        } else {
            keywordMap = page.getKeywords();
        }
        // 设置查询的条件
        for (Iterator<String> iterator = keywordMap.keySet().iterator(); iterator
                .hasNext();) {
            String key = iterator.next().toString();
            criteria.andEqualTo(key, keywordMap.get(key));
        }

        example.setOrderByClause(orderContentCheck(page.getOrderBy(),
                legalOrderByLegalArray));

        RowBounds rowBounds = new RowBounds(page.getStartLine(),
                page.getPageSize());

        List<T> lists = dao.selectByExampleAndRowBounds(example, rowBounds);
        int totalRows = dao.selectCountByExample(example);

        if (voClass == null) {
            page.setList(lists);
        } else {
            page.setList(this.change(lists, voClass));
        }

        page.setTotalRows(totalRows);
        page.initDatas();

        return page;
    }

    /**
     * list类型转换
     *
     * @param list
     *            数据
     * @param voClass
     *            目标对象
     * @return
     */
    private List change(List<T> list, Class voClass) {

        if (list == null) {
            return null;
        }
        if (voClass == null || voClass.equals(beanClass)) {
            return list;
        }
        if (copier == null) {
            copier = BeanCopier.create(beanClass, voClass, false);
        }

        List results = new ArrayList();
        for (T dao : list) {
            Object tragetBean;
            try {
                tragetBean = voClass.newInstance();
                copier.copy(dao, tragetBean, null);
                results.add(tragetBean);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    /**
     * 检验是否存在非法的字段
     *
     * @param checkMap
     *            得检验的Map
     * @param legalStr
     *            合法的字符串数组
     * @return 合法的Map
     */
    public static Map<String, Object> lawlessKeyWordsCheck(
            Map<String, Object> checkMap, String[] legalStr) {
        Map<String, Object> tmpMap = new HashMap<String, Object>();
        for (String legalKey : legalStr) {
            if (checkMap.get(legalKey) != null) {
                tmpMap.put(legalKey, checkMap.get(legalKey));
            }
        }
        return tmpMap;
    }

    /**
     * 检验是否存在非法的排序字段
     *
     * @param legalStr
     *            合法的字符串数组
     * @return 合法的Map
     */
    public static String orderContentCheck(String content, String[] legalStr) {
        // 如果为空
        if (content == null) {
            return null;
        }
        // 如果排序字段不合法
        String[] contentPart = content.split(" ");
        if (contentPart.length < 2) {
            return "";
        }
        String orderBy = "";
        if (legalStr == null) {
            orderBy = PageHandler.orderContentCheck(contentPart[0],
                    contentPart[1]);
        } else {
            for (String legalKey : legalStr) {
                if (legalKey.equals(contentPart[0])) {
                    orderBy = PageHandler.orderContentCheck(contentPart[0],
                            contentPart[1]);
                    break;
                }
            }
        }

        return orderBy;
    }

    public static String orderContentCheck(String orderKey, String sort) {
        sort = sort.toUpperCase();
        if (PageHandler.ORDERBY_PATTERN_DESC.equals(sort)) {
            orderKey += " " + PageHandler.ORDERBY_PATTERN_DESC;
        } else if (PageHandler.ORDERBY_PATTERN_ASC.equals(sort)) {
            orderKey += " " + PageHandler.ORDERBY_PATTERN_ASC;
        } else {
            orderKey += " " + PageHandler.ORDERBY_PATTERN_DESC;
        }
        return orderKey;
    }

    /**
     * 检验是否存在非法的字段
     *
     * @param checkMap
     *            得检验的Map
     * @param legalStr
     *            合法的字符串数组
     * @return 合法的Map
     */
    public static Map<String, String> lawlessKeyWordsCheckString(
            Map<String, String> checkMap, String[] legalStr) {
        Map<String, String> tmpMap = new HashMap<String, String>();
        for (String legalKey : legalStr) {
            if (checkMap.get(legalKey) != null) {
                tmpMap.put(legalKey, checkMap.get(legalKey));
            }
        }
        return tmpMap;
    }

    /**
     * 是否包换在数组里面
     *
     * @param arr
     * @param targetValue
     * @return
     */
    public static boolean isContain(Object[] arr, Object targetValue) {
        for (Object s : arr) {
            if (s.equals(targetValue))
                return true;
        }
        return false;
    }

    public String[] getLegalOrderByLegalArray() {
        return legalOrderByLegalArray;
    }

    public void setLegalOrderByLegalArray(String[] legalOrderByLegalArray) {
        this.legalOrderByLegalArray = legalOrderByLegalArray;
    }

    public String[] getLegalKeyWordsArray() {
        return legalKeyWordsArray;
    }

    public void setLegalKeyWordsArray(String[] legalKeyWordsArray) {
        this.legalKeyWordsArray = legalKeyWordsArray;
    }

    public String[] getLegalTimeKeyWordsArray() {
        return legalTimeKeyWordsArray;
    }

    public void setLegalTimeKeyWordsArray(String[] legalTimeKeyWordsArray) {
        this.legalTimeKeyWordsArray = legalTimeKeyWordsArray;
    }

    public String[] getLegalLikeKeyWordsArray() {
        return legalLikeKeyWordsArray;
    }

    public void setLegalLikeKeyWordsArray(String[] legalLikeKeyWordsArray) {
        this.legalLikeKeyWordsArray = legalLikeKeyWordsArray;
    }

    public String[] getLegalInkeyWordsArray() {
        return legalInkeyWordsArray;
    }

    public void setLegalInkeyWordsArray(String[] legalInkeyWordsArray) {
        this.legalInkeyWordsArray = legalInkeyWordsArray;
    }


}
