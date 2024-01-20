package com.cydeo.fintracker.service;

import com.cydeo.fintracker.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    List<CompanyDto> getCompanies();

    CompanyDto findById(Long companyId);

}