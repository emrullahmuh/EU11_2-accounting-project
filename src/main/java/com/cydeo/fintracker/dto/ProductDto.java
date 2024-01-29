package com.cydeo.fintracker.dto;

import com.cydeo.fintracker.enums.ProductUnit;
import lombok.*;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProductDto {

    private Long id;

    @NotBlank(message = "Product Name is required field.")
    @Size(min = 2, max = 100, message = "Product Name must be between 2 and 100 characters long.")
    private String name;

    private Integer quantityInStock;

    @NotNull(message = "Low Limit Alert is a required field.")
    @Min(value = 1, message = "Low Limit Alert should be at least 1.")
    private Integer lowLimitAlert;

    @NotNull(message = "Please select a Product Unit.")
    private ProductUnit productUnit;

    @NotNull(message = "Please select a category.")
    private CategoryDto category;

    private boolean hasProduct;

}
