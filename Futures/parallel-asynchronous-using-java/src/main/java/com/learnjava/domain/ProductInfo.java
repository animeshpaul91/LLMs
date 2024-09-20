package com.learnjava.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data // generates getters, setters, toString() methods for this class
@NoArgsConstructor // generates constructor with no args
@AllArgsConstructor // generates constructor with all args
@Builder
public class ProductInfo {
    private String productId;
    private List<ProductOption> productOptions;
}
