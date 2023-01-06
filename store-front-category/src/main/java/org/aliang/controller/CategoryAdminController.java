package org.aliang.controller;

import org.aliang.param.PageParam;
import org.aliang.pojo.Category;
import org.aliang.service.CategoryService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryAdminController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/admin/list")
    public R getCategoryList(@RequestBody PageParam pageParam){
        return categoryService.pageList(pageParam);
    }

    @PostMapping("/admin/save")
    public R save(@RequestBody Category category){
        return categoryService.saveCategory(category);
    }

    @PostMapping("admin/remove")
    public R remove(@RequestBody Integer categoryId){
        return categoryService.remove(categoryId);
    }

    @PostMapping("admin/update")
    public R update(@RequestBody Category category){
        return categoryService.update(category);
    }
}
