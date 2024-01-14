package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Company;
import com.cydeo.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public CompanyDto findById(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NoSuchElementException("Company with id: " + companyId + " Not Found "));
        return mapperUtil.convert(company, new CompanyDto());
    }

}
