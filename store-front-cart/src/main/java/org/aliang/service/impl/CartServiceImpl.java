package org.aliang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.aliang.clients.ProductClient;
import org.aliang.mapper.CartMapper;
import org.aliang.param.CartSaveParam;
import org.aliang.param.ProductCollectParam;
import org.aliang.param.ProductIdParam;
import org.aliang.pojo.Cart;
import org.aliang.pojo.Product;
import org.aliang.service.CartService;
import org.aliang.utils.R;
import org.aliang.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductClient productClient;
    /**
     * 添加购物车
     *
     * @param cartSaveParam
     * @return 001 成功 002 已经存在 003 没有库存
     */
    @Override
    public R save(CartSaveParam cartSaveParam) {
        //查询商品
        ProductIdParam productIdParam = new ProductIdParam();
        productIdParam.setProductID(cartSaveParam.getProductId());
        Product product = productClient.productDetail(productIdParam);
        if (product == null){
            return R.fail("商品已经被删除，无法添加到购物车！");
        }
        //查询库存
        if (product.getProductNum() == 0){
            R r = R.ok("没有库存数据！无法购买！");
            r.setCode("003");
            log.info("org.aliang.service.impl.CartServiceImpl.save业务结束，结果为:{}",r);
            return r;
        }
        //检查是否添加过
        LambdaQueryWrapper<Cart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Cart::getUserId,cartSaveParam.getUserId());
        lambdaQueryWrapper.eq(Cart::getProductId,cartSaveParam.getProductId());
        Cart cart = cartMapper.selectOne(lambdaQueryWrapper);
        if (cart != null){
            cart.setNum(cart.getNum() + 1);
            if (cart.getNum() > product.getProductNum()){
                return R.fail("添加商品至购物车失败！已超出库存数量！");
            }
            int rows = cartMapper.updateById(cart);
            if (rows == 0){
                return R.fail("添加数量失败！");
            }
            R r = R.ok("购物车存在该商品，数量+1");
            r.setCode("002");
            log.info("org.aliang.service.impl.CartServiceImpl.save业务结束，结果为:{}",r);
            return r;
        }
        //添加购物车
        cart = new Cart();
        cart.setNum(1);
        cart.setUserId(cartSaveParam.getUserId());
        cart.setProductId(cartSaveParam.getProductId());
        int rows = cartMapper.insert(cart);
        if (rows == 0){
            log.info("org.aliang.service.impl.CartServiceImpl.save业务结束，结果为:{}",rows);
            return R.fail("购物车添加失败！请重新添加！");
        }
        //结果封装返回
        CartVo cartVo = new CartVo(product, cart);
        log.info("org.aliang.service.impl.CartServiceImpl.save业务结束，结果为:{}",cartVo);
        return R.ok("购物车添加成功!",cartVo);
    }

    /**
     * 根据用户id 查询用户购物车数据
     * 即使数据为空，也需要返回一个空数据
     * @param userId
     * @return
     */
    @Override
    public R list(Integer userId) {
        //1.用户id 查询数据库
        LambdaQueryWrapper<Cart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Cart::getUserId,userId);
        List<Cart> cartList = cartMapper.selectList(lambdaQueryWrapper);
        //2.判断是否存在
        if (cartList == null || cartList.size() == 0){
            cartList = new ArrayList<>();
            return R.ok("购物车空空如也！",cartList);
        }
        //3.存在获取商品的id集合，并且调用商品服务查询
        ArrayList<Integer> productIds = new ArrayList<>();
        for (Cart cart : cartList) {
            Integer productId = cart.getProductId();
            productIds.add(productId);
        }
        ProductCollectParam productCollectParam = new ProductCollectParam();
        productCollectParam.setProductIds(productIds);
        List<Product> productList = productClient.cartList(productCollectParam);
        //商品集合 - 商品map 商品的id = key 商品 =value
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));

        //4.进行vo的封装
        ArrayList<CartVo> cartVoList = new ArrayList<>();
        for (Cart cart : cartList) {
            CartVo cartVo = new CartVo(productMap.get(cart.getProductId()),cart);
            cartVoList.add(cartVo);
        }
        log.info("org.aliang.service.impl.CartServiceImpl.list业务结束，结果为:{}",cartVoList);
        R r = R.ok("查询购物车数据成功！", cartVoList);
        return r;
    }

    /**
     * 修改购物车数据
     *
     * @param cartSaveParam
     * @return
     */
    @Override
    public R update(CartSaveParam cartSaveParam) {
        //1.查询商品详情
        ProductIdParam productIdParam = new ProductIdParam();
        productIdParam.setProductID(cartSaveParam.getProductId());
        Product product = productClient.productDetail(productIdParam);
        //2.判断库存
        if (cartSaveParam.getNum() > product.getProductNum()){
            log.info("org.aliang.service.impl.CartServiceImpl.update业务结束，结果为:{}","修改失败，超出库存数量！");
            return R.fail("修改失败，超出库存数量！");
        }
        //3.未超过库存数量
        LambdaQueryWrapper<Cart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Cart::getUserId,cartSaveParam.getUserId());
        lambdaQueryWrapper.eq(Cart::getProductId,cartSaveParam.getProductId());
        Cart cart = cartMapper.selectOne(lambdaQueryWrapper);
        if (cart == null){
            return R.fail("该商品未加入购物车，非法接口！");
        }
        cart.setNum(cartSaveParam.getNum());
        int rows = cartMapper.updateById(cart);
        if (rows == 0){
            return R.fail("更新购物车数量失败！");
        }
        log.info("org.aliang.service.impl.CartServiceImpl.update业务结束，结果为:{}","更新购物车数量成功！");
        return R.ok("更新购物车数量成功！");
    }

    /**
     * 删除购物车数据
     *
     * @param cartSaveParam
     * @return
     */
    @Override
    public R remove(CartSaveParam cartSaveParam) {
        LambdaQueryWrapper<Cart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Cart::getUserId,cartSaveParam.getUserId());
        lambdaQueryWrapper.eq(Cart::getProductId,cartSaveParam.getProductId());
        int rows = cartMapper.delete(lambdaQueryWrapper);
        if (rows == 0){
            log.info("org.aliang.service.impl.CartServiceImpl.remove业务结束，结果为:{}","删除购物车数据失败！");
            return R.fail("删除购物车数据失败！");
        }
        log.info("org.aliang.service.impl.CartServiceImpl.remove业务结束，结果为:{}","删除购物车数据成功！");
        return R.ok("删除数据成功!");
    }
}
