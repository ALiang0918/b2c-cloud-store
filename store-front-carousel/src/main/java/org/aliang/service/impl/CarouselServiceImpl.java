package org.aliang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.aliang.mapper.CarouselMapper;
import org.aliang.pojo.Carousel;
import org.aliang.service.CarouselService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;
    /**
     * 查询首页数据,查询优先级最高的六条
     *
     * @return
     */
    @Override
    @Cacheable(value = "list.carousel",key = "#root.methodName")
    public R getList() {
        LambdaQueryWrapper<Carousel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Carousel::getPriority);
        List<Carousel> carouselList = carouselMapper.selectList(lambdaQueryWrapper);
        List<Carousel> collect = carouselList.stream().limit(6).collect(Collectors.toList());
        return R.ok(collect);
    }
}
