package org.aliang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.aliang.param.PageParam;
import org.aliang.param.UserCheckParam;
import org.aliang.param.UserLoginParam;
import org.aliang.pojo.User;
import org.aliang.utils.R;

public interface UserService extends IService<User> {

    /**
     * 账号检查
     * @param userCheckParam username
     * @return 结果返回
     */
    R check(UserCheckParam userCheckParam);

    /**
     * 账号注册
     * @param user
     * @return
     */
    R register(User user);

    /**
     * 账号登录
     * @param userLoginParam 登录参数，未经过校验
     * @return 返回userId userName
     */
    R login(UserLoginParam userLoginParam);

    /**
     * 后台管理调用，查询全部用户数据
     * @param pageParam
     * @return
     */
    R listPage(PageParam pageParam);

    /**
     * 根据用户id进行删除
     * @param userId
     * @return
     */
    R removeById(Integer userId);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    R update(User user);


    /**
     * 添加用户
     * @param user
     * @return
     */
    R saveUser(User user);
}
