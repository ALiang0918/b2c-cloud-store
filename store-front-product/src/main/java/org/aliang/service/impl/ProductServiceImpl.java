package org.aliang.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aliang.clients.CategoryClient;
import org.aliang.clients.SearchClient;
import org.aliang.mapper.PictureMapper;
import org.aliang.mapper.ProductMapper;
import org.aliang.param.ProductCollectParam;
import org.aliang.param.ProductHotParam;
import org.aliang.param.ProductIdsParam;
import org.aliang.param.ProductSearchParam;
import org.aliang.pojo.Picture;
import org.aliang.pojo.Product;
import org.aliang.service.ProductService;
import org.aliang.to.OrderToProduct;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SearchClient searchClient;
    /**
     * 根据类别名称查询热门产品
     *
     * @param categoryName 类别名称
     * @return
     */
    @Override
    @Cacheable(value = "list.product",key = "#categoryName")
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
    @Cacheable(value = "list.product",key = "#productHotParam.categoryName")
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

    /**
     * 类别商品接口
     *
     * @param productIdsParam
     * @return
     */
    @Override
    @Cacheable(value = "list.product",key = "#productIdsParam.categoryID +'-'+#productIdsParam.currentPage+'-'+#productIdsParam.pageSize")
    public R byCategory(ProductIdsParam productIdsParam) {
        LambdaQueryWrapper<Product> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (productIdsParam.getCategoryID().size() != 0){
            lambdaQueryWrapper.in(Product::getCategoryId,productIdsParam.getCategoryID());
        }
        IPage<Product> page = new Page<>(productIdsParam.getCurrentPage(),productIdsParam.getPageSize());
        page = productMapper.selectPage(page,lambdaQueryWrapper);
        List<Product> productList = page.getRecords();
        Long total = (Long) page.getTotal();
        log.info("org.aliang.service.impl.ProductServiceImpl.byCategory()业务方法结束，查询结果为：{}",total);
        return R.ok("查询成功！",productList,total);
    }

    /**
     * 根据商品id查询商品详情
     *
     * @param productID 商品id 已校验
     * @return
     */
    @Override
    @Cacheable(value = "product",key = "#productID")
    public R detail(Integer productID) {
        Product product = productMapper.selectById(productID);
        log.info("org.aliang.service.impl.ProductServiceImpl.detail()业务方法执行完毕,查询商品id为：{}",productID);
        return R.ok("查询成功！",product);
    }

    /**
     * 根据商品id查询商品的图片
     *
     * @param productID
     * @return
     */
    @Override
    @Cacheable(value = "picture",key = "#productID")
    public R pictures(Integer productID) {
        LambdaQueryWrapper<Picture> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Picture::getProductId,productID);
        List<Picture> pictureList = pictureMapper.selectList(lambdaQueryWrapper);
        if (pictureList == null || pictureList.size() == 0){
            return R.fail("查询商品图片失败！");
        }
        return R.ok("查询商品图片成功！",pictureList);
    }

    /**
     * 商品搜索服务，获取全部商品信息
     *
     * @return
     */
    @Override
    @Cacheable(value = "list.category",key = "#root.methodName")
    public List<Product> getProductList() {
        List<Product> productList = productMapper.selectList(null);
        log.info("org.aliang.service.impl.ProductServiceImpl.getProductList()业务方法执行完毕");
        return productList;
    }

    /**
     * 商品搜索服务，需要远程调用搜索服务
     *
     * @param productSearchParam
     * @return
     */
    @Override
    public R search(ProductSearchParam productSearchParam) {
        R r = searchClient.searchProduct(productSearchParam);
        log.info("org.aliang.service.impl.ProductServiceImpl.search业务结束，结果为：{}",r);
        return r;
    }

    /**
     * 根据商品id集合查询商品集合
     *
     * @param productCollectParam
     * @return
     */
    @Override
    @Cacheable(value = "list.product",key = "#productCollectParam.productIds")
    public R getProductByIds(ProductCollectParam productCollectParam) {
        LambdaQueryWrapper<Product> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Product::getProductId,productCollectParam.getProductIds());
        List<Product> productList = productMapper.selectList(lambdaQueryWrapper);
        R r = R.ok("商品信息查询成功！", productList);
        log.info("org.aliang.service.impl.ProductServiceImpl.getProductByIds业务结束,结果为：{}",productList);
        return r;
    }

    /**
     * 修改库存和增加销售量
     *
     * @param orderToProducts
     */
    @Override
    public void subNumber(List<OrderToProduct> orderToProducts) {
        //将集合转成map key:value productId : ordreToProduct
        Map<Integer, OrderToProduct> map = orderToProducts.stream().collect(Collectors.toMap(OrderToProduct::getProductId, v -> v));
        //获取商品的id集合
        Set<Integer> productIds = map.keySet();
        //查询集合对应的商品信息
        List<Product> productList = productMapper.selectBatchIds(productIds);
        //修改商品信息
        for (Product product : productList) {
            Integer num = map.get(product.getProductId()).getNum();
            //减少库存
            product.setProductNum(product.getProductNum() - num);
            //增加销量
            product.setProductSales(product.getProductSales() + num);
        }
        this.updateBatchById(productList);
        log.info("org.aliang.service.impl.ProductServiceImpl.subNumber业务结束，结果：库存和销售量修改完毕");
    }
}
