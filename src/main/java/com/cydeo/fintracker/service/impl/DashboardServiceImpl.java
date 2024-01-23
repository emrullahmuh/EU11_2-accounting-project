package com.cydeo.fintracker.service.impl;

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

        // Total Cost:

        // Total Sales:

        // Profit:


        /*
        summaryCalculations.put("totalCost", totalCost);
        summaryCalculations.put("totalSales",totalSales);
        summaryCalculations.put("profitLoss",profitLoss);
        */

        return summaryCalculations;
    }
}
