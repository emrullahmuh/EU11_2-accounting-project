package com.cydeo.fintracker.dto;

import com.cydeo.fintracker.enums.ProductUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private Integer quantityInStock;
    private Integer lowLimitAlert;
    private ProductUnit productUnit;
    private CategoryDto category;
    private boolean hasProduct;
}
