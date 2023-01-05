package org.aliang.controller;

import org.aliang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductCategoryController {

    @Autowired
    private ProductService productService;
    /**
     * 类别服务调用管理调用
     */
    @PostMapping("/category/count")
    public Long categoryCount(@RequestBody Integer categoryId){

        return productService.categoryCount(categoryId);
    }
}
