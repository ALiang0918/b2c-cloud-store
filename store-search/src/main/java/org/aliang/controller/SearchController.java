package org.aliang.controller;

import org.aliang.param.ProductSearchParam;
import org.aliang.service.SearchService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;
    @PostMapping("/product")
    public R searchProduct(@RequestBody ProductSearchParam productSearchParam){
        return searchService.searchProduct(productSearchParam);
    }
}
