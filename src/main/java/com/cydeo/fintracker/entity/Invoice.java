package com.cydeo.fintracker.entity;

import com.cydeo.fintracker.entity.common.BaseEntity;
import com.cydeo.fintracker.enums.InvoiceStatus;
import com.cydeo.fintracker.enums.InvoiceType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoices")
@Builder
public class Invoice extends BaseEntity {

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_status")
    private InvoiceStatus invoiceStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_type")
    private InvoiceType invoiceType;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "client_vendor_id")
    private ClientVendor clientVendor;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
