package org.aliang.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProductIdsParam extends PageParam{
    @NotNull
    private List<Integer> categoryID;

}
