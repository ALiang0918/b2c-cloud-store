package org.aliang.service;

import org.aliang.param.CartListParam;
import org.aliang.param.PageParam;
import org.aliang.pojo.User;
import org.aliang.utils.R;

public interface UserService {
    /**
     * 查询用户列表
     * @param pageParam
     * @return
     */
    R userList(PageParam pageParam);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    R userRemove(CartListParam cartListParam);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    R updateUserInfo(User user);

    /**
     * 添加用户
     * @param user
     * @return
     */
    R save(User user);
}
