package org.aliang.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProductIdsParam {
    @NotNull
    private List<Integer> categoryID;
    private int currentPage;
    private int pageSize;
}
