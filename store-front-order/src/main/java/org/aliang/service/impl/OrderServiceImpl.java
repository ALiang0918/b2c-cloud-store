package org.aliang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aliang.config.RedisIdWorker;
import org.aliang.mapper.OrderMapper;
import org.aliang.clients.ProductClient;
import org.aliang.param.OrderParam;
import org.aliang.param.PageParam;
import org.aliang.param.ProductCollectParam;
import org.aliang.pojo.Order;
import org.aliang.pojo.Product;
import org.aliang.service.OrderService;
import org.aliang.to.OrderToProduct;
import org.aliang.utils.R;
import org.aliang.vo.AdminOrderVo;
import org.aliang.vo.CartVo;
import org.aliang.vo.OrderVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private RedisIdWorker redisIdWorker;

    @Autowired
    private OrderMapper orderMapper;
    /**
     * 进行订单数据保存业务
     * 1.将购物车数据转成订单数据
     * 2.进行订单数据的批量插入
     * 3.商品库存修改信息
     * 4.发送购物车库存修改信息
     * @param orderParam
     * @return
     */
    @Override
    public R save(OrderParam orderParam) {
        if (orderParam.getUserId() == null || orderParam.getProducts() == null || orderParam.getProducts().size() == 0){
            return R.fail("参数异常！");
        }
        //准备数据
        ArrayList<Integer> cartIds = new ArrayList<>();
        ArrayList<OrderToProduct> orderToProducts = new ArrayList<>();
        ArrayList<Order> orderList = new ArrayList<>();

        //生成数据
        Integer userId = orderParam.getUserId();
        long orderId = redisIdWorker.nextId("b2c");
        for (CartVo cartVo : orderParam.getProducts()) {
            cartIds.add(cartVo.getId()); //保存删除的购物车项的id
            OrderToProduct orderToProduct = new OrderToProduct();
            orderToProduct.setNum(cartVo.getNum());
            orderToProduct.setProductId(cartVo.getProductID());
            orderToProducts.add(orderToProduct); //商品服务修改的数据

            Order order = new Order();
            order.setOrderId(orderId);
            order.setOrderTime(orderId);
            order.setUserId(userId);
            order.setProductId(cartVo.getProductID());
            order.setProductNum(cartVo.getNum());
            order.setProductPrice(cartVo.getPrice());
            orderList.add(order);
        }
        //订单数据批量保存
        saveBatch(orderList);
        //发送购物车消息
        rabbitTemplate.convertAndSend("topic.ex","clear.cart",cartIds);
        //发送商品服务消息
        rabbitTemplate.convertAndSend("topic.ex","sub.number",orderToProducts);
        return R.ok("订单保存成功！");
    }

    @Override
    public R check(Integer productId) {
        LambdaQueryWrapper<Order> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Order::getProductId,productId);
        Long aLong = orderMapper.selectCount(lambdaQueryWrapper);
        if (aLong > 0){
            return R.fail("订单："+ aLong +"项引用该商品，不能删除！");
        }
        return R.ok("无订单引用，可以删除！");
    }

    /**
     * 根据用户id查询订单列表 参数已校验
     * 1.查询用户对应的全部订单项
     * 2.利用stream流进行订单分组 orderId
     * 3.查询订单的全部商品集合，并stream组成map
     * 4.封装返回的OrderVo对象
     * @param userId
     * @return
     */
    @Override
    public R list(Integer userId) {
        LambdaQueryWrapper<Order> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Order::getUserId,userId);
        List<Order> orderList = list(lambdaQueryWrapper);
        //分组
        Map<Long, List<Order>> orderMap = orderList.stream().collect(Collectors.groupingBy(Order::getOrderId));
        //查询商品数据
        List<Integer> productIds = orderList.stream().map(Order::getProductId).collect(Collectors.toList());
        ProductCollectParam productCollectParam = new ProductCollectParam();
        productCollectParam.setProductIds(productIds);
        List<Product> productList = productClient.cartList(productCollectParam);
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));

        //结果封装
        List<List<OrderVo>> result = new ArrayList<>();
        //遍历订单项集合
        for (List<Order> orders : orderMap.values()) {
            //封装每一个订单
            List<OrderVo> orderVos = new ArrayList<>();
            for (Order order : orders) {
                OrderVo orderVo = new OrderVo();
                BeanUtils.copyProperties(order,orderVo);
                Product product = productMap.get(order.getProductId());
                orderVo.setProductName(product.getProductName());
                orderVo.setProductPicture(product.getProductPicture());
                orderVos.add(orderVo);
            }
            result.add(orderVos);
        }
        R r = R.ok("订单数据获取成功！", result);
        log.info("org.aliang.service.impl.OrderServiceImpl.list业务结束，结果为:{}",result);
        return r;
    }

    @Override
    public R adminList(PageParam pageParam) {
        int offset = (pageParam.getCurrentPage() - 1) * pageParam.getPageSize();
        int number = pageParam.getPageSize();

        //查询数量
        Long total = orderMapper.selectCount(null);
        //自定义查询
        List<AdminOrderVo> adminOrderVoList = orderMapper.selectAdminOrders(offset,number);


        return R.ok("查询成功",adminOrderVoList,total);
    }
}
