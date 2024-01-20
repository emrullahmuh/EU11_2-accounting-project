package com.cydeo.fintracker.controller;

import com.cydeo.fintracker.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/list")
    public String getCompanyList(Model model) {

        model.addAttribute("companies", companyService.getCompanies());

        return "company/company-list";

    }

}