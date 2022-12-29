package org.aliang.doc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.aliang.pojo.Product;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ProductDoc extends Product {

    /**
     * 用于模糊查询字段,由商品名,标题和描述组成
     */
    private String all;

    public ProductDoc(Product product){
        super(product.getProductId(),product.getProductName(),
                product.getCategoryId(),product.getProductTitle(),
                product.getProductIntro(),product.getProductPicture(),
                product.getProductPrice(),product.getProductSellingPrice(),
                product.getProductNum(),product.getProductSales());
        this.all = product.getProductName()+product.getProductTitle()+product.getProductIntro();
    }
}
