package com.cydeo.fintracker.controller;


import com.cydeo.fintracker.client.ExchangeClient;
import com.cydeo.fintracker.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final ExchangeClient exchangeClient;

    public DashboardController(DashboardService dashboardService, ExchangeClient exchangeClient) {
        this.dashboardService = dashboardService;
        this.exchangeClient = exchangeClient;
    }

    @GetMapping
    public String dashboardPage(Model model) {

        model.addAttribute("summaryNumbers", dashboardService.summaryCalculation());
        model.addAttribute("exchangeRates", exchangeClient.getExchangesRates().getUsd());

        return "/dashboard";
    }

}
