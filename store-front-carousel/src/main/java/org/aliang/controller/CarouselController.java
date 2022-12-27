package org.aliang.controller;

import org.aliang.service.CarouselService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carousel")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;

    @PostMapping("/list")
    public R getList(){
        return carouselService.getList();
    }

}
