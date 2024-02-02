package com.cydeo.fintracker.service;


import com.cydeo.fintracker.dto.InvoiceProductDto;
import com.cydeo.fintracker.entity.InvoiceProduct;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;
import java.util.Locale;

public interface InvoiceProductService {
    InvoiceProductDto findById(Long id);

    List<InvoiceProductDto> listAllInvoiceProduct(Long id);

    InvoiceProductDto save(InvoiceProductDto invoiceProductDto, Long id);
    InvoiceProductDto delete(Long invoiceProductId);

    List<InvoiceProductDto> findAll();

    List<InvoiceProductDto> findAllByInvoiceIdAndIsDeleted(Long id, Boolean isDeleted);
BigDecimal getProfitLossBasedOnMonth(int year, Month month, Long id);


}
