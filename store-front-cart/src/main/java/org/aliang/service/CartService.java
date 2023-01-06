package org.aliang.service;

import org.aliang.param.CartSaveParam;
import org.aliang.utils.R;

import java.util.List;

public interface CartService {
    /**
     * 添加购物车
     * @param cartSaveParam
     * @return 001 成功 002 已经存在 003 没有库存
     */
    R save(CartSaveParam cartSaveParam);

    /**
     * 根据用户id 查询用户购物车数据
     * @param userId
     * @return
     */
    R list(Integer userId);

    /**
     * 修改购物车数据
     * @param cartSaveParam
     * @return
     */
    R update(CartSaveParam cartSaveParam);

    /**
     * 删除购物车数据
     * @param cartSaveParam
     * @return
     */
    R remove(CartSaveParam cartSaveParam);

    /**
     * 清空对应id 购物车项
     * @param cartIds
     */
    void clearIds(List<Integer> cartIds);

    R check(Integer productId);
}
