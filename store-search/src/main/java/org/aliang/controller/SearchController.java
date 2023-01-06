package org.aliang.controller;

import org.aliang.param.ProductSaveParam;
import org.aliang.param.ProductSearchParam;
import org.aliang.pojo.Product;
import org.aliang.service.SearchService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;
    @PostMapping("/product")
    public R searchProduct(@RequestBody ProductSearchParam productSearchParam){
        return searchService.searchProduct(productSearchParam);
    }

    @PostMapping("/save")
    public R saveOrUpdate(@RequestBody Product product) throws IOException {
        return searchService.save(product);
    }

    @PostMapping("/remove")
    public R remove(@RequestBody Integer productId) throws IOException {
        return searchService.remove(productId);
    }
}
