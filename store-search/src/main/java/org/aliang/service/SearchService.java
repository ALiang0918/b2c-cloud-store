package org.aliang.service;


import org.aliang.param.ProductSearchParam;
import org.aliang.pojo.Product;
import org.aliang.utils.R;

import java.io.IOException;

public interface SearchService {

    /**
     * 商品搜索
     * @param productSearchParam
     * @return
     */
    R searchProduct(ProductSearchParam productSearchParam);

    R save(Product product) throws IOException;

    R remove(Integer productId) throws IOException;
}
