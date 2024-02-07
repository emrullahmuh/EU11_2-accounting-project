package com.cydeo.fintracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResultDto {

    private Long id;
    private String status;
    private String chargeId;
    private String balanceTransaction;
}
