package com.cydeo.fintracker.controller;

import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/create")
    public String createCompany(Model model) {

        model.addAttribute("newCompany", new CompanyDto());
        model.addAttribute("countries", companyService.getAllCountries());

        return "company/company-create";

    }

    @PostMapping("/create")
    public String createCompany(@Valid @ModelAttribute("newCompany") CompanyDto newCompany, BindingResult bindingResult, Model model) {

        bindingResult = companyService.createUniqueTitle(newCompany.getTitle(), bindingResult);

        if (bindingResult.hasFieldErrors()) {

            model.addAttribute("countries", companyService.getAllCountries());

            return "company/company-create";

        }

        companyService.createCompany(newCompany);

        return "redirect:/companies/list";

    }

    @GetMapping("/update/{id}")
    public String updateCompany(@PathVariable("id") Long companyId, Model model) {

        model.addAttribute("company", companyService.findById(companyId));
        model.addAttribute("countries", companyService.getAllCountries());

        return "company/company-update";

    }

    @PostMapping("/update/{id}")
    public String updateCompany(@Valid @ModelAttribute("company") CompanyDto companyDto, BindingResult bindingResult, Model model) {

        bindingResult = companyService.updateUniqueTitle(companyDto, bindingResult);

        if (bindingResult.hasFieldErrors()) {

            model.addAttribute("countries", companyService.getAllCountries());

            return "company/company-update";

        }

        companyService.updateCompany(companyDto);

        return "redirect:/companies/list";

    }

    @GetMapping("/activate/{id}")
    public String activateCompany(@PathVariable("id") Long companyId) {

        companyService.activateCompany(companyId);

        return "redirect:/companies/list";

    }

    @GetMapping("/deactivate/{id}")
    public String deactivateCompany(@PathVariable("id") Long companyId) {

        companyService.deactivateCompany(companyId);

        return "redirect:/companies/list";

    }


}
