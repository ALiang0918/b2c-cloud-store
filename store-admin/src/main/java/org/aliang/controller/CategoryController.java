package org.aliang.controller;

import org.aliang.param.PageParam;
import org.aliang.pojo.Category;
import org.aliang.service.CategoryService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public R getCategoryList(PageParam pageParam){
        return categoryService.getCategoryList(pageParam);
    }

    @PostMapping("/save")
    public R saveCategory(Category category){
        return categoryService.saveCategory(category);
    }

    @PostMapping("/remove")
    public R remove(Integer categoryId){

        return categoryService.remove(categoryId);
    }

    @PostMapping("/update")
    public R update(Category category){

        return categoryService.update(category);
    }
}
