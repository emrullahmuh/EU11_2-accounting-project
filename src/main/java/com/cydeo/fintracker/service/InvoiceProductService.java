package com.cydeo.fintracker.service;


import com.cydeo.fintracker.dto.InvoiceProductDto;
import com.cydeo.fintracker.enums.InvoiceType;

import java.math.BigDecimal;
import java.util.List;


public interface InvoiceProductService {
    InvoiceProductDto findById(Long id);

    List<InvoiceProductDto> listAllInvoiceProduct(Long id);

    InvoiceProductDto save(InvoiceProductDto invoiceProductDto, Long id);

    InvoiceProductDto delete(Long invoiceProductId);

    List<InvoiceProductDto> findAll();

    List<InvoiceProductDto> findAllByInvoiceIdAndIsDeleted(Long id, Boolean isDeleted);

    BigDecimal getProfitLossBasedOnMonth(int year, int month, Long id, InvoiceType invoiceType);


}
