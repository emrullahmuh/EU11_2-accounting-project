package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.enums.InvoiceStatus;
import com.cydeo.fintracker.enums.InvoiceType;
import com.cydeo.fintracker.service.DashboardService;
import com.cydeo.fintracker.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final InvoiceService invoiceService;

    public DashboardServiceImpl(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public Map<String, BigDecimal> summaryCalculation() {

        Map<String, BigDecimal> summaryCalculations = new HashMap<>();

        BigDecimal totalCost = invoiceService.listAllInvoices(InvoiceType.PURCHASE).stream()
                .filter(invoiceDto -> invoiceDto.getInvoiceStatus() == InvoiceStatus.APPROVED)
                .map(invoiceDto -> invoiceDto.getTotal()).reduce(BigDecimal.ZERO,BigDecimal::add);

        BigDecimal totalSales =invoiceService.listAllInvoices(InvoiceType.SALES).stream()
                .filter(invoiceDto -> invoiceDto.getInvoiceStatus() == InvoiceStatus.APPROVED)
                .map(invoiceDto -> invoiceDto.getTotal()).reduce(BigDecimal.ZERO,BigDecimal::add);

        //TODO  Complete profitLoss  after report functionality done
        BigDecimal profitLoss = totalSales.subtract(totalCost);

        summaryCalculations.put("totalCost", totalCost);
        summaryCalculations.put("totalSales",totalSales);
        summaryCalculations.put("profitLoss",profitLoss);

        return summaryCalculations;
    }
}
