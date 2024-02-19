package com.cydeo.fintracker.entity;

import com.cydeo.fintracker.entity.common.BaseEntity;
import com.cydeo.fintracker.enums.InvoiceType;
import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "report")
public class ReportView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private int year;

    @Column(name = "month")
    private int month;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_type")
    private InvoiceType invoiceType;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

}
