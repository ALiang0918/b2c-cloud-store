package org.aliang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@TableName(value = "`user`")
public class User implements Serializable {
    @TableId(value = "user_id", type = IdType.AUTO)
    @JsonProperty("user_id")
    private Integer userId;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "`password`")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    @TableField(value = "user_phonenumber")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userPhonenumber;

    private static final long serialVersionUID = 1L;

    public static final String COL_USER_ID = "user_id";

    public static final String COL_USER_NAME = "user_name";

    public static final String COL_PASSWORD = "password";

    public static final String COL_USER_PHONENUMBER = "user_phonenumber";
}