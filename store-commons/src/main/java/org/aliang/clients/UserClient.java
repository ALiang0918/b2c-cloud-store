package org.aliang.clients;

import org.aliang.param.CartListParam;
import org.aliang.param.PageParam;
import org.aliang.pojo.User;
import org.aliang.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("user-service")
public interface UserClient {

    @PostMapping("/user/admin/list")
    R adminListPage(@RequestBody PageParam pageParam);

    @PostMapping("/user/admin/remove")
    R adminRemove(@RequestBody CartListParam cartListParam);

    @PostMapping("/user/admin/update")
    R update(@RequestBody User user);

    @PostMapping("/user/admin/save")
    R save(@RequestBody User user);
}
