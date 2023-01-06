package org.aliang.param;

import lombok.Data;
import org.aliang.pojo.Product;

@Data
public class ProductSaveParam extends Product {

    /**
     * 保存商品详情的图片地址！ 图片之间使用 + 拼接
     */
    private String pictures;
}
