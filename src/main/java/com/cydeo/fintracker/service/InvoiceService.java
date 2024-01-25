package com.cydeo.fintracker.service;



import com.cydeo.fintracker.dto.InvoiceDto;
import com.cydeo.fintracker.enums.InvoiceType;

import java.util.List;

public interface InvoiceService {
    InvoiceDto findById(Long id);
    List<InvoiceDto> listAllInvoices(InvoiceType invoiceType);
    void save(InvoiceDto invoiceDto, InvoiceType invoiceType);
    InvoiceDto update(InvoiceDto invoiceDto);
    void delete(Long id);
    InvoiceDto createNewPurchaseInvoice();
    String findInvoiceId();
    InvoiceDto approve(Long id);
    InvoiceDto createNewSalesInvoice();


}
