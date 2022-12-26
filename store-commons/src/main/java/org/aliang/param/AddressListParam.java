package org.aliang.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddressListParam {

    @JsonProperty("user_id")
    private Integer userId;
}
