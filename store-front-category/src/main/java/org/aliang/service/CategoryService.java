package org.aliang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.aliang.pojo.Category;
import org.aliang.utils.R;

public interface CategoryService extends IService<Category> {

    /**
     * 根据类别名称查询类别并封装返回
     * @param categoryName 类别名称
     * @return
     */
    R byName(String categoryName);
}
