package org.aliang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.aliang.param.ProductHotParam;
import org.aliang.param.ProductPromoParam;
import org.aliang.pojo.Product;
import org.aliang.utils.R;

public interface ProductService extends IService<Product> {

    /**
     * 根据类别名称查询热门产品
     * @param categoryName 类别名称
     * @return
     */
    R promo(String categoryName);

    /**
     * 根据类别集合查询热门产品
     * @param productHotParam
     * @return
     */
    R hots(ProductHotParam productHotParam);
}
