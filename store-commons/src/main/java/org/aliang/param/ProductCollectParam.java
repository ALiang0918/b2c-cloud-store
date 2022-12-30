package org.aliang.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProductCollectParam {

    @NotEmpty
    private List<Integer> productIds;
}
