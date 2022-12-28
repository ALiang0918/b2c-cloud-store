package org.aliang.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductIdParam {
    @NotNull
    private Integer productID;
}
