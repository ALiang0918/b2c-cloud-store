package org.aliang.controller;

import org.aliang.param.CollectParam;
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
    public R save(@RequestBody CollectParam collectParam){
        return collectService.saveCollect(collectParam);
    }
}
