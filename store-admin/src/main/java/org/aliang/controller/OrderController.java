package org.aliang.controller;

import org.aliang.param.PageParam;
import org.aliang.service.OrderService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController
{
    @Autowired
    private OrderService orderService;

    @GetMapping("list")
    public R list(PageParam pageParam){

        return orderService.list(pageParam);
    }

}
