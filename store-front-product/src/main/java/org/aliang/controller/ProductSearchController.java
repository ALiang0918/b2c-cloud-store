package org.aliang.controller;

import org.aliang.param.ProductSearchParam;
import org.aliang.pojo.Product;
import org.aliang.service.ProductService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductSearchController {

    @Autowired
    private ProductService productService;
    @GetMapping("/list")
    public List<Product> getProductList(){
        return productService.getProductList();
    }

    @PostMapping("/search")
    public R search(@RequestBody ProductSearchParam productSearchParam){
        return productService.search(productSearchParam);
    }
}
