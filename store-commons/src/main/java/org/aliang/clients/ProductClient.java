package org.aliang.clients;

import org.aliang.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "product-service")
public interface ProductClient {

    /**
     * 搜索服务调用，进行全部数据查询！用于搜索数据库同步数据！
     * @return
     */
    @GetMapping("/product/list")
    List<Product> getProductList();
}
