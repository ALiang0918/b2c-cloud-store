package org.aliang.controller;

import org.aliang.param.PageParam;
import org.aliang.service.OrderService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderAdminController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/admin/list")
    public R adminList(@RequestBody PageParam pageParam){

        return orderService.adminList(pageParam);
    }
}
