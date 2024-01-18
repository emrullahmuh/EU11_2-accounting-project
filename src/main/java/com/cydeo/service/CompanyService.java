package com.cydeo.service;

import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Company;

import java.util.List;

public interface CompanyService {

    List<CompanyDto> listAllCompanies();

    void activateCompany(Long companyId);

    void deactivateCompany(Long companyId);

    CompanyDto findById(Long companyId);

}