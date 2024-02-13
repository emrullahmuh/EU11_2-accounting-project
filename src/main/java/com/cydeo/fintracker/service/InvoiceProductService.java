package com.cydeo.fintracker.service;

import com.cydeo.fintracker.dto.InvoiceProductDto;
import org.springframework.validation.BindingResult;

import com.cydeo.fintracker.enums.InvoiceStatus;


import java.util.List;


public interface InvoiceProductService {
    InvoiceProductDto findById(Long id);

    List<InvoiceProductDto> listAllInvoiceProduct(Long id);

    InvoiceProductDto save(InvoiceProductDto invoiceProductDto, Long id);

    InvoiceProductDto delete(Long invoiceProductId);

    List<InvoiceProductDto> findAll();

    List<InvoiceProductDto> findAllByInvoiceIdAndIsDeleted(Long id, Boolean isDeleted);

    public List<InvoiceProductDto> findAllApprovedInvoiceProducts(InvoiceStatus invoiceStatus);

    void setProfitLossForInvoiceProduct(InvoiceProductDto toBeSoldProduct);

    BindingResult checkProductStockBeforeAddingToInvoice(InvoiceProductDto invoiceProductDto, Long invoiceId, BindingResult bindingResult);

}
