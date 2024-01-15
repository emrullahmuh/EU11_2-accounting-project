package com.cydeo.service;

import com.cydeo.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    CompanyDto findById(Long companyId);

    List<CompanyDto> getCompanyList();

    void createCompany(CompanyDto newCompany);

    CompanyDto updateCompany(CompanyDto companyDto);

    void activateCompany(Long companyId);

    void deactivateCompany(Long companyId);

}
