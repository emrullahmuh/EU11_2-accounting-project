package com.cydeo.fintracker.controller;

import com.cydeo.fintracker.service.ReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportingController {

    private final ReportingService reportingService;

    @GetMapping("/stockData")
    public String getStockReport(Model model){

        model.addAttribute("invoiceProducts",reportingService.generateStockReport());

        return "report/stock-report";
    }


    @GetMapping("/profitLossData")
    public String getProfitLossList(Model model) {

        model.addAttribute("monthlyProfitLossDataMap", reportingService.getMonthlyProfitLoss());

        return "report/profit-loss-report";
    }
}
