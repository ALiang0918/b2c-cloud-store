package org.aliang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.aliang.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}