package org.aliang.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class ProductHotParam {
    @NotEmpty
    private List<String> categoryName;
}

