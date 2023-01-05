package org.aliang.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aliang.clients.UserClient;
import org.aliang.param.CartListParam;
import org.aliang.param.PageParam;
import org.aliang.pojo.User;
import org.aliang.service.UserService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserClient userClient;
    /**
     * 查询用户列表
     *
     * @param pageParam
     * @return
     */
    @Override
    @Cacheable(value = "list.user",key = "#pageParam.currentPage+'-'+#pageParam.pageSize")
    public R userList(PageParam pageParam) {
        R r = userClient.adminListPage(pageParam);
        log.info("org.aliang.service.impl.UserServiceImpl#userList业务结束，结果为:{}",r);
        return r;
    }

    /**
     * 删除用户
     * @param cartListParam
     * @return
     */
    @Override
    @CacheEvict(value = "list.user",allEntries = true)
    public R userRemove(CartListParam cartListParam) {
        R r = userClient.adminRemove(cartListParam);
        return r;
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @Override
    @CacheEvict(value = "list.user",allEntries = true)
    public R updateUserInfo(User user) {
        return userClient.update(user);
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @Override
    @CacheEvict(value = "list.user",allEntries = true)
    public R save(User user) {
        return userClient.save(user);
    }
}
