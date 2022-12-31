package org.aliang.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CartListParam {

    @NotNull
    @JsonProperty(value = "user_id")
    private Integer userId;
}
