package org.aliang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@TableName(value = "product_picture")
public class Picture implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "product_id")
    @JsonProperty("product_id")
    private Integer productId;

    @TableField(value = "product_picture")
    @JsonProperty("product_picture")
    private String productPicture;

    @TableField(value = "intro")
    private String intro;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_PRODUCT_PICTURE = "product_picture";

    public static final String COL_INTRO = "intro";
}