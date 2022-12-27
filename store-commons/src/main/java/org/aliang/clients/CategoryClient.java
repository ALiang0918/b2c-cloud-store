package org.aliang.clients;

import org.aliang.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * projectName: b2c-store
 * <p>
 * description: 类别的调用接口
 */
@FeignClient("category-service")
public interface CategoryClient {

    @GetMapping("/category/promo/{categoryName}")
    R byName(@PathVariable String categoryName);

}
