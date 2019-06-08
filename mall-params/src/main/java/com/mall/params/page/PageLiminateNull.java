package com.mall.params.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>清除page类中keywords传入为空值的参数
 * <p>@author CJH
 * <p>@date 2017年8月7日
 * <p>@version 1.0
 */
public class PageLiminateNull {

    public static Page liminateNull(Page page){
        Map<String, Object> keywords = new HashMap<String, Object>();
        keywords = page.getKeywords();
        List<String> keys = new ArrayList<String>();
        for (String key : keywords.keySet()) {
            if (keywords.get(key) == null || "".equals(keywords.get(key))) {
                keys.add(key);
            }
        }
        for (String key : keys) {
            page.getKeywords().remove(key);
        }
        return page;
    }
}
