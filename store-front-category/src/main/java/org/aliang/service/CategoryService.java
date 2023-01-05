package org.aliang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.aliang.param.PageParam;
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

    /**
     * 根据商品名称查询商品id 供商品服务调用
     * @param productHotParam
     * @return
     */
    R names(ProductHotParam productHotParam);

    /**
     * 后台管理 类别分页查询
     * @param pageParam
     * @return
     */
    R pageList(PageParam pageParam);

    /**
     * 后台管理 新增类别
     * @param category
     * @return
     */
    R saveCategory(Category category);

    /**
     * 根据id删除类别
     * @param categoryId
     * @return
     */
    R remove(Integer categoryId);

    R update(Category category);
}
