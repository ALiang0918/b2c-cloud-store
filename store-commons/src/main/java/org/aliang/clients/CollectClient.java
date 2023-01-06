package org.aliang.clients;

import org.aliang.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("collect-service")
public interface CollectClient {

    @PostMapping("/collect/remove/product")
    R removeByPid(@RequestBody Integer productId);
}
