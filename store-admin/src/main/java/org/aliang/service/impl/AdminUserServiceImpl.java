package org.aliang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.aliang.Mapper.AdminUserMapper;
import org.aliang.constants.UserConstants;
import org.aliang.param.AdminUserParam;
import org.aliang.pojo.AdminUser;
import org.aliang.service.AdminUserService;
import org.aliang.utils.MD5Util;
import org.aliang.utils.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;
    /**
     * 登录校验
     *
     * @param adminUserParam
     * @return
     */
    @Override
    public R login(AdminUserParam adminUserParam) {
        String newPwd = MD5Util.encode(adminUserParam.getUserPassword() + UserConstants.USER_SALT);
        LambdaQueryWrapper<AdminUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(AdminUser::getUserAccount,adminUserParam.getUserAccount());
        lambdaQueryWrapper.eq(AdminUser::getUserPassword,newPwd);
        AdminUser adminUser = adminUserMapper.selectOne(lambdaQueryWrapper);
        if (adminUser == null){
            return R.fail("账号或者密码错误!");
        }
        R ok = R.ok("用户登录成功!", adminUser);
        log.info("AdminUserServiceImpl.login业务结束，结果:{}",ok);
        return ok;
    }
}
