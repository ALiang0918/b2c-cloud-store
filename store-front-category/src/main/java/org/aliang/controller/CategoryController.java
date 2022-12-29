package org.aliang.controller;

import org.aliang.param.ProductHotParam;
import org.aliang.service.CategoryService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据类别名称查询类别信息
     * @param categoryName 类别名称
     * @return
     */
    @GetMapping("/promo/{categoryName}")
    public R byName(@PathVariable String categoryName){
        return categoryService.byName(categoryName);
    }

    /**
     * 热门类别id查询
     * @param productHotParam
     * @param result
     * @return
     */
    @PostMapping("/hots")
    public R hots(@RequestBody @Validated ProductHotParam productHotParam, BindingResult result){
        if (result.hasErrors()){
            return R.fail("类别集合查询失败！");
        }
        return categoryService.hots(productHotParam);
    }

    /**
     * 查询商品类别集合
     * @return
     */
    @GetMapping("/list")
    public R getCategoryList(){
        return categoryService.getCategoryList();
    }

    /**
     * 根据商品名称查询商品id
     * @return
     */
    @PostMapping("/names")
    public R names(@RequestBody @Validated ProductHotParam productHotParam,BindingResult result){
        if (result.hasErrors()){
            return R.fail("参数异常");
        }
        return categoryService.names(productHotParam);
    }
}
