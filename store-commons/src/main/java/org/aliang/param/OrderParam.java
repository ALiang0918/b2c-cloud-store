package org.aliang.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.aliang.vo.CartVo;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderParam implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("user_id")
    private Integer userId;
    private List<CartVo> products;
}
