package com.cydeo.fintracker.service;

import com.cydeo.fintracker.dto.CompanyDto;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface CompanyService {

    CompanyDto findById(Long companyId);

    List<CompanyDto> getCompanies();

    CompanyDto createCompany(CompanyDto newCompany);

    CompanyDto updateCompany(CompanyDto companyDto);

    void activateCompany(Long companyId);

    void deactivateCompany(Long companyId);

    public BindingResult createUniqueTitle(String title, BindingResult bindingResult);

    public BindingResult updateUniqueTitle(CompanyDto companyDto, BindingResult bindingResult);

    public List<String> getAllCountries();

    List<CompanyDto> getCompanyDtoByLoggedInUser();

}
