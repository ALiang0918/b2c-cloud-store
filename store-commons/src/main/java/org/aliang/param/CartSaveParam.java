package org.aliang.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartSaveParam {

    @NotNull
    @JsonProperty(value = "product_id")
    private Integer productId;
    @NotNull
    @JsonProperty(value = "user_id")
    private Integer userId;

    private Integer num;

}
