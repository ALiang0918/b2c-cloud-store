package org.aliang.clients;

import org.aliang.param.PageParam;
import org.aliang.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("order-service")
public interface OrderClient {
    @PostMapping("/order/admin/list")
    R adminList(@RequestBody PageParam pageParam);
}
