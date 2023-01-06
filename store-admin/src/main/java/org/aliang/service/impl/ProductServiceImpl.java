package org.aliang.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aliang.clients.ProductClient;
import org.aliang.clients.SearchClient;
import org.aliang.param.ProductSaveParam;
import org.aliang.param.ProductSearchParam;
import org.aliang.pojo.Product;
import org.aliang.service.ProductService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private SearchClient searchClient;

    @Autowired
    private ProductClient productClient;
    /**
     * 后台商品管理搜索
     *
     * @param productSearchParam
     * @return
     */
    @Override
    public R search(ProductSearchParam productSearchParam) {
        R r = searchClient.searchProduct(productSearchParam);
        log.info("org.aliang.service.impl.ProductServiceImpl.search业务结束，结果为:{}",r);
        return r;
    }

    @Override
    public R save(ProductSaveParam productSaveParam) {
        return productClient.adminSave(productSaveParam);
    }

    @Override
    public R update(Product product) {
        return productClient.adminUpdate(product);
    }

    @Override
    public R remove(Integer productId) {
        return productClient.adminRemove(productId);
    }
}
