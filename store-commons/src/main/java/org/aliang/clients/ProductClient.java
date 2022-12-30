package org.aliang.clients;

import org.aliang.param.ProductCollectParam;
import org.aliang.pojo.Product;
import org.aliang.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "product-service")
public interface ProductClient {

    /**
     * 搜索服务调用，进行全部数据查询！用于搜索数据库同步数据！
     * @return
     */
    @GetMapping("/product/list")
    List<Product> getProductList();

    @PostMapping("/product/collect/list")
    R getCollectList(@RequestBody ProductCollectParam productCollectParam);
}
