package com.cydeo.fintracker.service;

import java.math.BigDecimal;
import java.util.Map;

import com.cydeo.fintracker.dto.InvoiceProductDto;

import java.util.List;

public interface ReportingService {
    Map<String, BigDecimal> getMonthlyProfitLoss();
    public List<InvoiceProductDto> generateStockReport();

}
