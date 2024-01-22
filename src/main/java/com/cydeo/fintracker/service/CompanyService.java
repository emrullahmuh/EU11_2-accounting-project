package com.cydeo.fintracker.service;

import com.cydeo.fintracker.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    CompanyDto findById(Long companyId);

    List<CompanyDto> getCompanies();

    CompanyDto createCompany(CompanyDto newCompany);

    CompanyDto updateCompany(CompanyDto companyDto);

    void activateCompany(Long companyId);

    void deactivateCompany(Long companyId);

    List<CompanyDto> getCompanyDtoByLoggedInUser();

}
