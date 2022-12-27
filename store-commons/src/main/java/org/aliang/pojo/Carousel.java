package org.aliang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@TableName(value = "carousel")
public class Carousel implements Serializable {
    @TableId(value = "carousel_id", type = IdType.AUTO)
    @JsonProperty("carousel_id")
    private Integer carouselId;

    @TableField(value = "img_path")
    private String imgPath;

    @TableField(value = "describes")
    private String describes;

    /**
     * 广告关联的商品图片
     */
    @TableField(value = "product_id")
    @JsonProperty("product_id")
    private Integer productId;

    /**
     * 优先级
     */
    @TableField(value = "priority")
    private Integer priority;

    private static final long serialVersionUID = 1L;

    public static final String COL_CAROUSEL_ID = "carousel_id";

    public static final String COL_IMG_PATH = "img_path";

    public static final String COL_DESCRIBES = "describes";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_PRIORITY = "priority";
}