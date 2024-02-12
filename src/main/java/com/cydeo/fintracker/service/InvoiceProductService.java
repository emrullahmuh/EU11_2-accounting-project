package com.cydeo.fintracker.service;

import com.cydeo.fintracker.dto.InvoiceProductDto;
import com.cydeo.fintracker.entity.InvoiceProduct;
import org.springframework.validation.BindingResult;

import org.springframework.validation.BindingResult;

import com.cydeo.fintracker.enums.InvoiceType;
import com.cydeo.fintracker.enums.InvoiceStatus;

import java.math.BigDecimal;
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

    BigDecimal getProfitLossBasedOnMonth(int year, int month, Long id, InvoiceType invoiceType);
    void setProfitLossForInvoiceProduct(InvoiceProductDto toBeSoldProduct);

    BindingResult checkProductStockBeforeAddingToInvoice(InvoiceProductDto invoiceProductDto, Long invoiceId, BindingResult bindingResult);

}
