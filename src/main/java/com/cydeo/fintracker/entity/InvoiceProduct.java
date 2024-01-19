package com.cydeo.fintracker.entity;

import com.cydeo.fintracker.entity.common.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice_products")
public class InvoiceProduct extends BaseEntity {

    int quantity;
    BigDecimal price;
    int tax;
    BigDecimal profitLoss;
    int remainingQuantity;
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    Invoice invoice;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
}
