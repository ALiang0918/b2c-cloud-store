package org.aliang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.aliang.pojo.Order;
import org.aliang.vo.AdminOrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 分页查询数据,返回order封装vo
     * @param offset
     * @param number
     * @return
     */
    List<AdminOrderVo> selectAdminOrders(@Param("offset") int offset, @Param("number")int number);

}
