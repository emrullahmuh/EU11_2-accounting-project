package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.client.ExchangeClient;
import com.cydeo.fintracker.dto.response.ExchangeResponse;
import com.cydeo.fintracker.dto.response.Usd;
import com.cydeo.fintracker.service.DashboardService;
import com.cydeo.fintracker.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final InvoiceService invoiceService;
    private final ExchangeClient exchangeClient;

    public DashboardServiceImpl(InvoiceService invoiceService, ExchangeClient exchangeClient) {
        this.invoiceService = invoiceService;
        this.exchangeClient = exchangeClient;
    }

    @Override
    public Map<String, BigDecimal> summaryCalculation() {

        Map<String, BigDecimal> summaryCalculations = new HashMap<>();

        //Later delete-template;
        // Total Cost:
        BigDecimal totalCost = new BigDecimal(25000);

        // Total Sales:
        BigDecimal totalSales = new BigDecimal(35000);

        // Profit/Loss:
        BigDecimal profitLoss = totalSales.subtract(totalCost);

        summaryCalculations.put("totalCost", totalCost);
        summaryCalculations.put("totalSales",totalSales);
        summaryCalculations.put("profitLoss",profitLoss);

        return summaryCalculations;
    }

    @Override
    public Usd getAllMoney() {

        ExchangeResponse apiExchange = exchangeClient.getExchangesRates();
        return apiExchange.getUsd();
    }

}
