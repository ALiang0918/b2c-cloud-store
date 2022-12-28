package org.aliang.controller;

import org.aliang.param.ProductHotParam;
import org.aliang.param.ProductIdParam;
import org.aliang.param.ProductIdsParam;
import org.aliang.param.ProductPromoParam;
import org.aliang.service.ProductService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/promo")
    public R promo(@RequestBody @Validated ProductPromoParam productPromoParam, BindingResult result){
        if (result.hasErrors()){
            return R.fail("参数异常！");
        }
        return productService.promo(productPromoParam.getCategoryName());
    }

    @PostMapping("/hots")
    public R hots(@RequestBody @Validated ProductHotParam productHotParam, BindingResult result){

        if (result.hasErrors()){
            return R.fail("数据查询失败!");
        }

        return productService.hots(productHotParam);
    }

    @PostMapping("/category/list")
    public R getCategoryList(){
        return productService.getCategoryList();
    }

    /**
     * 类别商品接口
     * @param productIdsParam
     * @param result
     * @return
     */
    @PostMapping("/bycategory")
    public R byCategory(@RequestBody @Validated ProductIdsParam productIdsParam,BindingResult result){
        if (result.hasErrors()){
            return R.fail("查询失败!");
        }
        return productService.byCategory(productIdsParam);
    }

    @PostMapping("/all")
    public R all(@RequestBody @Validated ProductIdsParam productIdsParam,BindingResult result){
        if (result.hasErrors()){
            return R.fail("查询失败!");
        }
        return productService.byCategory(productIdsParam);
    }

    @PostMapping("/detail")
    public R detail(@RequestBody @Validated ProductIdParam productIdParam,BindingResult result){
        if (result.hasErrors()){
            return R.fail("参数异常！");
        }
        return productService.detail(productIdParam.getProductID());
    }

    @PostMapping("/pictures")
    public R pictures(@RequestBody @Validated ProductIdParam productIdParam,BindingResult result){
        if (result.hasErrors()){
            return R.fail("参数异常！");
        }
        return productService.pictures(productIdParam.getProductID());
    }
}
