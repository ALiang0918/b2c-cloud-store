package org.aliang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.aliang.param.ProductHotParam;
import org.aliang.param.ProductIdsParam;
import org.aliang.param.ProductSearchParam;
import org.aliang.pojo.Product;
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
}
