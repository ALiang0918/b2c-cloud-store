package org.aliang.controller;

import org.aliang.param.ProductSaveParam;
import org.aliang.pojo.Product;
import org.aliang.service.ProductService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductAdminController {

    @Autowired
    private ProductService productService;
    /**
     * 类别服务调用管理调用
     */
    @PostMapping("/admin/count")
    public Long adminCount(@RequestBody Integer categoryId){

        return productService.categoryCount(categoryId);
    }

    @PostMapping("/admin/save")
    public R adminSave(@RequestBody ProductSaveParam productSaveParam){
        return productService.adminSave(productSaveParam);
    }

    @PostMapping("/admin/update")
    public R adminSave(@RequestBody Product product){
        return productService.adminUpdate(product);
    }

    @PostMapping("/admin/remove")
    public R adminRemove(@RequestBody Integer productId){
        return productService.adminRemove(productId);
    }

}
