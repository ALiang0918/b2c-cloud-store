package org.aliang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aliang.constants.UserConstants;
import org.aliang.mapper.UserMapper;
import org.aliang.param.UserCheckParam;
import org.aliang.param.UserLoginParam;
import org.aliang.pojo.User;
import org.aliang.service.UserService;
import org.aliang.utils.MD5Util;
import org.aliang.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    /**
     * 账号检查
     *
     * @return R 返回结果
     */
    @Override
    public R check(UserCheckParam userCheckParam) {
        //1.参数校验
        if (userCheckParam == null){
            return R.fail("账号为null,不可使用!");
        }
        if (StringUtils.isBlank(userCheckParam.getUserName())){
            return R.fail("账号为空,不可使用!");
        }
        if (userCheckParam.getUserName().length() < 6){
            return R.fail("账号长度少于六位,不可使用!");
        }
        //2.查询db
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName,userCheckParam.getUserName());
        Long count = userMapper.selectCount(lambdaQueryWrapper);
        if (count != 0){
            return R.fail("账号已经存在,不可注册!");
        }
        //3.结果封装返回
        log.info("UserServiceImpl.check()业务方法结束,账号:{},可以注册",userCheckParam.getUserName());
        return R.ok("账号不存在,可以使用!");
    }

    /**
     * 账号注册
     *
     * @param user
     * @return
     */
    @Override
    public R register(User user) {
        //1.参数校验
        if (user == null
                || StringUtils.isBlank(user.getUserName())
                || StringUtils.isBlank(user.getPassword())
                || user.getUserName().length() < UserConstants.MIN_LENGTH){
            return R.fail("参数异常，注册失败");
        }
        //2.查询账号是否已存在
        //2.1 账号存在，返回注册失败信息
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName,user.getUserName());
        Long count = userMapper.selectCount(lambdaQueryWrapper);
        if (count > 0){
            return R.fail("账号已存在，请重新输入账号！");
        }
        //2.2 账号未存在，密码加盐，存入数据库
        String newPwd = MD5Util.encode(user.getPassword() + UserConstants.USER_SALT);
        user.setPassword(newPwd);
        int rows = userMapper.insert(user);
        if (rows == 0){
            log.info("UserServiceImpl.register()业务结束，账号:{},注册失败！",user.getUserName());
            return R.fail("注册失败!请稍后再试!");
        }
        //3.结果封装返回
        log.info("UserServiceImpl.register()业务结束，账号:{},注册成功！",user.getUserName());
        return R.ok("注册成功！");
    }

    /**
     * 账号登录
     *
     * @param userLoginParam 登录参数，未经过校验
     * @return 返回userId userName
     */
    @Override
    public R login(UserLoginParam userLoginParam) {
        //1.参数校验
        if (userLoginParam == null
                || StringUtils.isBlank(userLoginParam.getUserName())
                || StringUtils.isBlank(userLoginParam.getPassword())){
            return R.fail("账号或密码错误");
        }
        //2.密码比对
        String newPwd = MD5Util.encode(userLoginParam.getPassword() + UserConstants.USER_SALT);
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName,userLoginParam.getUserName());
        lambdaQueryWrapper.eq(User::getPassword,newPwd);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        if (user == null){
            return R.fail("账号或密码错误");
        }
        user.setPassword(null);
        if (StringUtils.isBlank(user.getUserPhonenumber())){
            user.setUserPhonenumber(null);
        }
        log.info("UserServiceImpl.login()业务方法结束，账号：{},登录成功！",userLoginParam.getUserName());
        return R.ok("登录成功！",user);
    }
}
