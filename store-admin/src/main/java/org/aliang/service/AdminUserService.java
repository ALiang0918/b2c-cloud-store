package org.aliang.service;

import org.aliang.param.AdminUserParam;
import org.aliang.pojo.AdminUser;
import org.aliang.utils.R;

public interface AdminUserService {
    /**
     * 登录校验
     * @param adminUserParam
     * @return
     */
    R login(AdminUserParam adminUserParam);
}
