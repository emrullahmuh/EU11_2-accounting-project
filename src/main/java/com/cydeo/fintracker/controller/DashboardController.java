package com.cydeo.fintracker.controller;


import com.cydeo.fintracker.client.ExchangeClient;
import com.cydeo.fintracker.client.ExchangeClientTr;
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
    private final ExchangeClientTr exchangeClientTr;

    public DashboardController(DashboardService dashboardService, ExchangeClient exchangeClient, ExchangeClientTr exchangeClientTr) {
        this.dashboardService = dashboardService;
        this.exchangeClient = exchangeClient;
        this.exchangeClientTr = exchangeClientTr;
    }

    @GetMapping
    public String dashboardPage(Model model) {

        model.addAttribute("summaryNumbers", dashboardService.summaryCalculation());
        model.addAttribute("exchangeRates", exchangeClient.getExchangesRates().getUsd());
        model.addAttribute("exchangeRates", exchangeClientTr.getExchangesRatesTr().getUsd());

        return "/dashboard";
    }

}
