package org.aliang.controller;

import org.aliang.param.CollectParam;
import org.aliang.pojo.Collect;
import org.aliang.service.CollectService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;
    @PostMapping("/save")
    public R save(@RequestBody Collect collect){
        return collectService.saveCollect(collect);
    }

    @PostMapping("/list")
    public R getCollectList(@RequestBody Collect collect){
        return collectService.getCollectList(collect.getUserId());
    }

    @PostMapping("/remove")
    public R remove(@RequestBody Collect collect){
        return collectService.removeCollect(collect);
    }

    @PostMapping("/remove/product")
    public R removeByPid(@RequestBody Integer productId){
        return collectService.removeByPid(productId);
    }
}
