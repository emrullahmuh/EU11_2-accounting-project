package com.cydeo.fintracker.entity;

import com.cydeo.fintracker.entity.common.BaseEntity;
import com.cydeo.fintracker.enums.Months;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment extends BaseEntity {

    private int year;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private boolean isPaid;

    private String companyStripeId;

    @Enumerated(EnumType.STRING)
    private Months month;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;


}
