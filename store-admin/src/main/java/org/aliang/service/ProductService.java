package org.aliang.service;

import org.aliang.param.ProductSaveParam;
import org.aliang.param.ProductSearchParam;
import org.aliang.pojo.Product;
import org.aliang.utils.R;

public interface ProductService {
    /**
     * 后台商品管理搜索
     * @param productSearchParam
     * @return
     */
    R search(ProductSearchParam productSearchParam);

    R save(ProductSaveParam productSaveParam);

    R update(Product product);

    R remove(Integer productId);
}
