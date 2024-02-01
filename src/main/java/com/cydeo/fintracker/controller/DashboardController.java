package com.cydeo.fintracker.controller;


import com.cydeo.fintracker.client.ExchangeClient;
import com.cydeo.fintracker.service.DashboardService;
import com.cydeo.fintracker.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final ExchangeClient exchangeClient;
    private final InvoiceService invoiceService;

    public DashboardController(DashboardService dashboardService, ExchangeClient exchangeClient, InvoiceService invoiceService) {
        this.dashboardService = dashboardService;
        this.exchangeClient = exchangeClient;
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public String dashboardPage(Model model){

        model.addAttribute("invoices", invoiceService.getLast3ApprovedInvoices());
        model.addAttribute("summaryNumbers", dashboardService.summaryCalculation());
        model.addAttribute("exchangeRates", exchangeClient.getExchangesRates().getUsd());

        return "dashboard";
    }

}
