package org.aliang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.aliang.param.CollectParam;
import org.aliang.pojo.Collect;
import org.aliang.utils.R;

public interface CollectService extends IService<Collect> {

    /**
     * 保存收藏
     * @return
     */
    R saveCollect(Collect collect);

    /**
     * 根据用户id查询用户收藏列表
     * @param userId
     * @return
     */
    R getCollectList(Integer userId);

    /**
     * 根据用户id 和 商品id 移除收藏列表 参数未校验
     * @param collect
     * @return
     */
    R removeCollect(Collect collect);
}
