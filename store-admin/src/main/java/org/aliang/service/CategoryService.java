package org.aliang.service;

import org.aliang.param.PageParam;
import org.aliang.pojo.Category;
import org.aliang.utils.R;

public interface CategoryService {
    /**
     * 商品类别分页查询
     * @param pageParam
     * @return
     */
    R getCategoryList(PageParam pageParam);

    /**
     * 新增类别
     * @param category
     * @return
     */
    R saveCategory(Category category);

    R remove(Integer categoryId);

    R update(Category category);
}
