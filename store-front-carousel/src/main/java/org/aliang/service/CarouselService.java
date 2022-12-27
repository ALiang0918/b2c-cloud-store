package org.aliang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.aliang.pojo.Carousel;
import org.aliang.utils.R;

public interface CarouselService extends IService<Carousel> {

    /**
     * 查询首页数据,查询优先级最高的四条
     * @return
     */
    R getList();

}
