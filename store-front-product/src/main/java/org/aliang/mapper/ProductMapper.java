package org.aliang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.aliang.pojo.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
