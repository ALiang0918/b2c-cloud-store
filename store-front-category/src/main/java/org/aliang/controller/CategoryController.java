package org.aliang.controller;

import org.aliang.service.CategoryService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/promo/{categoryName}")
    public R byName(@PathVariable String categoryName){
        return categoryService.byName(categoryName);
    }
}
