package com.cydeo.fintracker.controller;


import com.cydeo.fintracker.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public String dashboardPage(Model model) {

        model.addAttribute("summaryNumbers", dashboardService.summaryCalculation());
        model.addAttribute("exchangeRates", dashboardService.getAllMoney());

        return "/dashboard";
    }

}
