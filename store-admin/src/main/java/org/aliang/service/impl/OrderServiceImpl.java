package org.aliang.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aliang.clients.OrderClient;
import org.aliang.param.PageParam;
import org.aliang.service.OrderService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderClient orderClient;
    @Override
    public R list(PageParam pageParam) {
        return orderClient.adminList(pageParam);
    }
}
