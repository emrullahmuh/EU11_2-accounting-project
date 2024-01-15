package com.cydeo.controller;

import com.cydeo.dto.CompanyDto;
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
    public String getCompanyList(Model model){

        model.addAttribute("companies", companyService.getCompanyList());

        return "/company/company-list";

    }

    @GetMapping("/create")
    public String createCompany(Model model){

        model.addAttribute("newCompany", new CompanyDto());
        model.addAttribute("countries", List.of("United States"));

        return "/company/company-create";

    }

    @PostMapping("/create")
    public String createCompany(@ModelAttribute("newCompany") CompanyDto newCompany){

        companyService.createCompany(newCompany);

        return "redirect:/company/list";

    }

    @GetMapping("/update/{id}")
    public String updateCompany(@PathVariable("id") Long companyId, Model model){

        model.addAttribute("company", companyService.findById(companyId));
        model.addAttribute("countries", List.of("United States"));

        return "/company/company-update";

    }

    @PostMapping("/update/{id}")
    public String updateCompany(@ModelAttribute("company") CompanyDto companyDto){

        companyService.updateCompany(companyDto);

        return "redirect:/companies/list";

    }

    @GetMapping("/activate/{id}")
    public String activateCompany(@PathVariable("id") Long companyId){

        companyService.activateCompany(companyId);

        return "redirect:/companies/list";

    }

    @GetMapping("/deactivate/{id}")
    public String deactivateCompany(@PathVariable("id") Long companyId){

        companyService.deactivateCompany(companyId);

        return "redirect:/companies/list";

    }

}
