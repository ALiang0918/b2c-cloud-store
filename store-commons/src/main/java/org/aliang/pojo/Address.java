package org.aliang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@TableName(value = "address")
public class Address implements Serializable {
    /**
     * 地址主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 联系人
     */
    @TableField(value = "linkman")
    private String linkman;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 详细地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 用户主键
     */
    @TableField(value = "user_id")
    private Integer userId;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_LINKMAN = "linkman";

    public static final String COL_PHONE = "phone";

    public static final String COL_ADDRESS = "address";

    public static final String COL_USER_ID = "user_id";
}