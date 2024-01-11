package com.cydeo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
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
