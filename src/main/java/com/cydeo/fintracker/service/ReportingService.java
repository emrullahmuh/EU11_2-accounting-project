package com.cydeo.fintracker.service;

import com.cydeo.fintracker.dto.InvoiceProductDto;

import java.util.List;

public interface ReportingService {
    public List<InvoiceProductDto> generateStockReport();

}
