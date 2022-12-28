package org.aliang.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aliang.clients.CategoryClient;
import org.aliang.mapper.ProductMapper;
import org.aliang.param.ProductHotParam;
import org.aliang.pojo.Product;
import org.aliang.service.ProductService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryClient categoryClient;
    /**
     * 根据类别名称查询热门产品
     *
     * @param categoryName 类别名称
     * @return
     */
    @Override
    public R promo(String categoryName) {
        R r = categoryClient.byName(categoryName);
        if (r.getCode().equals(R.FAIL_CODE)){
            log.info("ProductServiceImpl.promo业务结束，结果:{}","类别查询失败!");
            return r;
        }
        LinkedHashMap<String,Object>  map = (LinkedHashMap<String, Object>) r.getData();

        Integer categoryId = (Integer) map.get("category_id");
        //封装查询参数
        LambdaQueryWrapper<Product> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Product::getCategoryId,categoryId);
        lambdaQueryWrapper.orderByDesc(Product::getProductSales);

        IPage<Product> page = new Page<>(1,7);
        //返回的是包装数据! 内部有对应的商品集合,也有分页的参数 例如: 总条数 总页数等等
        page = productMapper.selectPage(page, lambdaQueryWrapper);
        List<Product> productList = page.getRecords(); //指定页的数据
        long total = page.getTotal(); //获取总条数
        log.info("ProductServiceImpl.promo业务结束，结果:{}",productList);
        return R.ok("数据查询成功",productList);
    }

    /**
     * 根据类别集合查询热门产品
     *
     * @param productHotParam
     * @return
     */
    @Override
    public R hots(ProductHotParam productHotParam) {
        R hots = categoryClient.hots(productHotParam);
        if (hots.getCode().equals(R.FAIL_CODE)){
            log.info("ProductServiceImpl.hots业务结束，结果:{}",hots.getMsg());
            return hots;
        }
        List<Object> idList = (List<Object>) hots.getData();
        LambdaQueryWrapper<Product> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Product::getCategoryId,idList);
        lambdaQueryWrapper.orderByDesc(Product::getProductSales);
        IPage<Product> page = new Page<>(1,7);
        page = productMapper.selectPage(page, lambdaQueryWrapper);
        List<Product> productList = page.getRecords();
        if (productList == null || productList.size() == 0){
            log.info("org.aliang.service.impl.ProductServiceImpl.hots()业务方法执行完毕，结果为：{}","查询商品失败");
            return R.fail("查询商品失败！");
        }
        log.info("org.aliang.service.impl.ProductServiceImpl.hots()业务方法执行完毕，结果为：{}","查询商品成功");
        return R.ok("查询成功！",productList);
    }

    /**
     * 查询商品类别集合
     *
     * @return
     */
    @Override
    public R getCategoryList() {
        return categoryClient.getCategoryList();
    }
}
