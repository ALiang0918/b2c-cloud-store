package org.aliang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aliang.mapper.CollectMapper;
import org.aliang.param.CollectParam;
import org.aliang.pojo.Collect;
import org.aliang.service.CollectService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Autowired
    private CollectMapper collectMapper;
    /**
     * 保存收藏
     *
     * @param collectParam
     * @return
     */
    @Override
    public R saveCollect(CollectParam collectParam) {
        //1.参数解析
        Integer userId = collectParam.getUserId();
        Integer productId = collectParam.getProductId();
        //2.查询数据库是否已收藏
        LambdaQueryWrapper<Collect> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Collect::getUserId,userId);
        lambdaQueryWrapper.eq(Collect::getProductId,productId);
        Long count = collectMapper.selectCount(lambdaQueryWrapper);
        //2.1已收藏 返回提示信息
        if (count != 0){
            log.info("org.aliang.service.impl.CollectServiceImpl.saveCollect业务结束，结果为{}",count);
            R.fail("商品已在收藏夹! 无需二次添加!");
        }
        //2.2 未收藏 数据封装 插入到数据库
        Collect collect = new Collect();
        collect.setUserId(userId);
        collect.setProductId(productId);
        collect.setCollectTime(System.currentTimeMillis());
        int rows = collectMapper.insert(collect);
        if (rows == 0){
            log.info("org.aliang.service.impl.CollectServiceImpl.saveCollect业务结束，结果为{}",rows);
            return R.fail("收藏失败！");
        }
        log.info("org.aliang.service.impl.CollectServiceImpl.saveCollect业务结束，结果为{}",rows);
        return R.ok("商品添加成功!");
    }
}
