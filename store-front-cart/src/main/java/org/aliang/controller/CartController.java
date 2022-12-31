package org.aliang.controller;

import org.aliang.param.CartListParam;
import org.aliang.param.CartSaveParam;
import org.aliang.service.CartService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @PostMapping("/save")
    public R save(@RequestBody @Validated CartSaveParam cartSaveParam, BindingResult result){
        if (result.hasErrors()){
            return R.fail("参数异常，无法添加到购物车！");
        }
        return cartService.save(cartSaveParam);
    }

    @PostMapping("/list")
    public R list(@RequestBody @Validated CartListParam cartListParam,BindingResult result){
        if (result.hasErrors()){
            return R.fail("参数异常！");
        }
        return cartService.list(cartListParam.getUserId());
    }

    @PostMapping("/update")
    public R update(@RequestBody @Validated CartSaveParam cartSaveParam,BindingResult result){
        if (result.hasErrors()){
            return R.fail("参数异常！");
        }
        return cartService.update(cartSaveParam);
    }

    @PostMapping("/remove")
    public R remove(@RequestBody @Validated CartSaveParam cartSaveParam,BindingResult result){
        if (result.hasErrors()){
            return R.fail("参数异常！");
        }
        return cartService.remove(cartSaveParam);
    }
}
