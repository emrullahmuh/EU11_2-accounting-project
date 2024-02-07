package com.cydeo.fintracker.service;

import com.cydeo.fintracker.dto.InvoiceProductDto;
import com.cydeo.fintracker.entity.InvoiceProduct;
import com.cydeo.fintracker.enums.InvoiceStatus;

import java.util.List;
import java.util.stream.Collectors;

public interface InvoiceProductService {
    InvoiceProductDto findById(Long id);
    List<InvoiceProductDto> listAllInvoiceProduct(Long id);
    InvoiceProductDto save(InvoiceProductDto invoiceProductDto, Long id);
    InvoiceProductDto delete(Long invoiceProductId);
    List<InvoiceProductDto> findAll();
    List<InvoiceProductDto> findAllByInvoiceIdAndIsDeleted(Long id, Boolean isDeleted);
    public List<InvoiceProductDto> findAllApprovedInvoiceProducts(InvoiceStatus invoiceStatus);



}
