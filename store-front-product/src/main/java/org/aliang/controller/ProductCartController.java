package org.aliang.controller;

import org.aliang.param.ProductCollectParam;
import org.aliang.param.ProductIdParam;
import org.aliang.pojo.Product;
import org.aliang.service.ProductService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductCartController {

    @Autowired
    private ProductService productService;
    @PostMapping("/cart/detail")
    public Product cartDetail(@RequestBody @Validated ProductIdParam productIdParam, BindingResult result){
        if (result.hasErrors()){
            return null;
        }
        R r = productService.detail(productIdParam.getProductID());
        return (Product) r.getData();
    }

    @PostMapping("/cart/list")
    public List<Product> cartList(@RequestBody @Validated ProductCollectParam productCollectParam,BindingResult result){
        if (result.hasErrors()){
            return new ArrayList<Product>();
        }
        R r = productService.getProductByIds(productCollectParam);
        return (List<Product>) r.getData();
    }
}
