package org.aliang.service;


import org.aliang.param.ProductSearchParam;
import org.aliang.utils.R;

public interface SearchService {

    /**
     * 商品搜索
     * @param productSearchParam
     * @return
     */
    R searchProduct(ProductSearchParam productSearchParam);
}
