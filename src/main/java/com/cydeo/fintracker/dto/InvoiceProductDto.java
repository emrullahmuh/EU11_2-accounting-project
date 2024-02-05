package com.cydeo.fintracker.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProductDto {

    private Long id;
    @Min(value = 1, message = "Quantity must be at least 1.")
    @Max(value = 100, message = "Quantity cannot be greater than 100.")
    @NotNull(message = "Quantity is a required field.")
    private Integer quantity;
    @Min(value = 1, message = "Price must be at least 1.")
    @NotNull(message = "Price is a required field.")
    private BigDecimal price;
    @NotNull(message = "Tax is a required field.")
    @Min(value = 0, message = "Tax should be between 0% and 20%.")
    @Max(value = 20, message = "Tax should be between 0% and 20%.")
    private Integer tax;
    private BigDecimal total;
    private BigDecimal profitLoss;
    private Integer remainingQuantity;
    private InvoiceDto invoice;
    @NotNull(message = "Product is a required field")
    private ProductDto product;
}
