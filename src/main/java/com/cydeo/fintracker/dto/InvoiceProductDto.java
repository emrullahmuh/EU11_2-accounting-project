package com.cydeo.fintracker.dto;

import lombok.*;

import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProductDto {

    Long id;
    Integer quantity;
    BigDecimal price;
    Integer tax;
    BigDecimal total;
    BigDecimal profitLoss;
    Integer remainingQuantity;
    InvoiceDto invoice;
    ProductDto product;
}
