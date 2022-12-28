package org.aliang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.aliang.param.ProductHotParam;
import org.aliang.pojo.Category;
import org.aliang.utils.R;

public interface CategoryService extends IService<Category> {

    /**
     * 根据类别名称查询类别并封装返回
     * @param categoryName 类别名称
     * @return
     */
    R byName(String categoryName);

    /**
     * 热门类别id查询
     * @param productHotParam 类别名称集合
     * @return
     */
    R hots(ProductHotParam productHotParam);

    /**
     * 查询商品类别集合
     * @return
     */
    R getCategoryList();
}
