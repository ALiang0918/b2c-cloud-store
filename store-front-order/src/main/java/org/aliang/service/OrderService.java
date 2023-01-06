package org.aliang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.aliang.param.OrderParam;
import org.aliang.param.PageParam;
import org.aliang.pojo.Order;
import org.aliang.utils.R;

public interface OrderService extends IService<Order> {
    /**
     * 进行订单数据保存业务
     * @param orderParam
     * @return
     */
    R save(OrderParam orderParam);

    /**
     * 根据用户id查询订单列表 参数已校验
     *
     * @param userId
     * @return
     */
    R list(Integer userId);

    R adminList(PageParam pageParam);
}
