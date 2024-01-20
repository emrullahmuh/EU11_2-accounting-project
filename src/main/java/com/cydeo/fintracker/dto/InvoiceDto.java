package com.cydeo.fintracker.dto;

import com.cydeo.fintracker.enums.InvoiceStatus;
import com.cydeo.fintracker.enums.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDto {

    private Long id;
    private String invoiceNo;
    private InvoiceStatus invoiceStatus;
    private InvoiceType invoiceType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private CompanyDto company;
    private ClientVendorDto clientVendor;
    private BigDecimal price;
    private BigDecimal tax;
    private BigDecimal total;

}