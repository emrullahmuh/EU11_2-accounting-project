package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.InvoiceProductDto;
import com.cydeo.fintracker.entity.Invoice;
import com.cydeo.fintracker.entity.InvoiceProduct;
import com.cydeo.fintracker.enums.InvoiceStatus;
import com.cydeo.fintracker.service.InvoiceProductService;
import com.cydeo.fintracker.service.InvoiceService;
import com.cydeo.fintracker.service.ReportingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportingServiceImpl implements ReportingService {

    private final InvoiceProductService invoiceProductService;

    public List<InvoiceProductDto> generateStockReport() {
        return invoiceProductService.findAllApprovedInvoiceProducts(InvoiceStatus.APPROVED);
    }

}
