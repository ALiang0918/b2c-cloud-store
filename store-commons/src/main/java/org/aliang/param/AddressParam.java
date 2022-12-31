package org.aliang.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.aliang.pojo.Address;

import javax.validation.constraints.NotNull;

@Data
public class AddressParam {
    @NotNull
    @JsonProperty("user_id")
    private Integer userId;

    private Address add;
}
