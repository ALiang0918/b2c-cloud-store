package org.aliang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aliang.clients.ProductClient;
import org.aliang.mapper.CollectMapper;
import org.aliang.param.CollectParam;
import org.aliang.param.ProductCollectParam;
import org.aliang.pojo.Collect;
import org.aliang.service.CollectService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private ProductClient productClient;
    /**
     * 保存收藏
     *
     * @param collect
     * @return
     */
    @Override
    public R saveCollect(Collect collect) {
        //1.参数解析
        Integer userId = collect.getUserId();
        Integer productId = collect.getProductId();
        //2.查询数据库是否已收藏
        LambdaQueryWrapper<Collect> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Collect::getUserId,userId);
        lambdaQueryWrapper.eq(Collect::getProductId,productId);
        Long count = collectMapper.selectCount(lambdaQueryWrapper);
        //2.1已收藏 返回提示信息
        if (count != 0){
            log.info("org.aliang.service.impl.CollectServiceImpl.saveCollect业务结束，结果为{}",count);
            return R.fail("商品已在收藏夹! 无需二次添加!");
        }
        //2.2 未收藏 数据封装 插入到数据库
        collect.setCollectTime(System.currentTimeMillis());
        int rows = collectMapper.insert(collect);
        if (rows == 0){
            log.info("org.aliang.service.impl.CollectServiceImpl.saveCollect业务结束，结果为{}",rows);
            return R.fail("收藏失败！");
        }
        log.info("org.aliang.service.impl.CollectServiceImpl.saveCollect业务结束，结果为{}",rows);
        return R.ok("商品添加成功!");
    }

    /**
     * 根据用户id查询用户收藏列表
     *
     * @param userId
     * @return
     */
    @Override
    public R getCollectList(Integer userId) {
        LambdaQueryWrapper<Collect> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Collect::getUserId,userId);
        List<Collect> collectList = collectMapper.selectList(lambdaQueryWrapper);
        List<Integer> productIdList = collectList.stream().map(Collect::getProductId).collect(Collectors.toList());
        ProductCollectParam productCollectParam = new ProductCollectParam();
        productCollectParam.setProductIds(productIdList);
        R r = productClient.getCollectList(productCollectParam);
        log.info("org.aliang.service.impl.CollectServiceImpl.getCollectList业务结束，结果为：{}",productIdList);
        return r;
    }

    /**
     * 根据用户id 和 商品id 移除收藏列表 参数未校验
     *
     * @param collect
     * @return
     */
    @Override
    public R removeCollect(Collect collect) {
        //1.参数校验
        if (collect.getUserId() == null || collect.getProductId() == null){
            return R.fail("参数异常！");
        }
        //2.删除收藏
        LambdaQueryWrapper<Collect> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Collect::getUserId,collect.getUserId());
        lambdaQueryWrapper.eq(Collect::getProductId,collect.getProductId());
        int rows = collectMapper.delete(lambdaQueryWrapper);
        if (rows == 0){
            return R.fail("删除失败！");
        }
        log.info("org.aliang.service.impl.CollectServiceImpl.removeCollect业务结束，结果为：{}",rows);
        //3.结果封装
        return R.ok("收藏移除成功!");
    }
}
