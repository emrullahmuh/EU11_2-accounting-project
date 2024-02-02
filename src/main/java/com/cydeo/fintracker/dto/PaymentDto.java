package com.cydeo.fintracker.dto;

import com.cydeo.fintracker.enums.Months;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDto {

    private Long id;
    private int year;
    private Months month;
    private LocalDate paymentDate;
    private BigDecimal amount;
    private boolean isPaid;
    private String companyStripeId;
    private String description;
    private CompanyDto company;

}
