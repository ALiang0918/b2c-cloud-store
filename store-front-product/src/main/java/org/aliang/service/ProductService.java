package org.aliang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.aliang.param.*;
import org.aliang.pojo.Product;
import org.aliang.to.OrderToProduct;
import org.aliang.utils.R;

import java.util.List;

public interface ProductService extends IService<Product> {

    /**
     * 根据类别名称查询热门产品
     * @param categoryName 类别名称
     * @return
     */
    R promo(String categoryName);

    /**
     * 根据类别集合查询热门产品
     * @param productHotParam
     * @return
     */
    R hots(ProductHotParam productHotParam);

    /**
     * 查询商品类别集合
     * @return
     */
    R getCategoryList();

    /**
     * 类别商品接口
     * @param productIdsParam
     * @return
     */
    R byCategory(ProductIdsParam productIdsParam);

    /**
     * 根据商品id查询商品详情
     * @param productID 商品id 已校验
     * @return
     */
    R detail(Integer productID);

    /**
     * 根据商品id查询商品的图片
     * @param productID
     * @return
     */
    R pictures(Integer productID);

    /**
     * 商品搜索服务，获取全部商品信息
     * @return
     */
    List<Product> getProductList();

    /**
     * 商品搜索服务，需要远程调用搜索服务
     * @return
     */
    R search(ProductSearchParam productSearchParam);

    /**
     * 根据商品id集合查询商品集合
     * @param productCollectParam
     * @return
     */
    R getProductByIds(ProductCollectParam productCollectParam);


    /**
     * 修改库存和增加销售量
     * @param orderToProducts
     */
    void subNumber(List<OrderToProduct> orderToProducts);

    /**
     * 根据类别id 查询该类别下的商品数量
     * @param categoryId
     * @return
     */
    Long categoryCount(Integer categoryId);

    /**
     * 商品保存
     * @param productSaveParam
     * @return
     */
    R adminSave(ProductSaveParam productSaveParam);

    /**
     * 商品更新
     * @param product
     * @return
     */
    R adminUpdate(Product product);

    R adminRemove(Integer productId);
}
