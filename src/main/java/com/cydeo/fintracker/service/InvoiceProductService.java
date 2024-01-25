package com.cydeo.fintracker.service;


import com.cydeo.fintracker.dto.InvoiceProductDto;

import java.util.List;

public interface InvoiceProductService {
    InvoiceProductDto findById(Long id);
    List<InvoiceProductDto> listAllInvoiceProduct(Long id);
    InvoiceProductDto save(InvoiceProductDto invoiceProductDto, Long id);
    InvoiceProductDto delete(Long invoiceProductId);



}
