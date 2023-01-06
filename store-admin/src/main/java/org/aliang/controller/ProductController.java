package org.aliang.controller;

import org.aliang.param.ProductSaveParam;
import org.aliang.param.ProductSearchParam;
import org.aliang.pojo.Product;
import org.aliang.service.ProductService;
import org.aliang.utils.AliyunOSSUtils;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;

    @GetMapping("/list")
    public R adminList(ProductSearchParam productSearchParam){
        return productService.search(productSearchParam);
    }

    @PostMapping("/upload")
    public R upload(MultipartFile img) throws Exception {
        String filename = img.getOriginalFilename();
        filename = UUID.randomUUID().toString().replaceAll("-","") + filename;
        String contentType = img.getContentType();
        byte[] bytes = img.getBytes();
        int hours = 1000;
        String url = aliyunOSSUtils.uploadImage(filename, bytes, contentType, hours);
        System.out.println(url);
        return R.ok("图片上传成功！",url);
    }

    @PostMapping("/save")
    public R save(ProductSaveParam productSaveParam){
        return productService.save(productSaveParam);
    }

    @PostMapping("/update")
    public R update(Product product){
        return productService.update(product);
    }

    @PostMapping("/remove")
    public R remove(Integer productId){
        return productService.remove(productId);
    }
}
