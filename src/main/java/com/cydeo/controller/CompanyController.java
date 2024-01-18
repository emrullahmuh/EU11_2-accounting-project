package com.cydeo.controller;

import com.cydeo.dto.CompanyDto;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String listAllCompanies(Model model){

        model.addAttribute("companies", companyService.listAllCompanies());

        return "/company/company-list";

    }

}