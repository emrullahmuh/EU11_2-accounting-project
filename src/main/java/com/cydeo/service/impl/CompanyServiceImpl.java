package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Company;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final MapperUtil mapperUtil;
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(MapperUtil mapperUtil, CompanyRepository companyRepository) {
        this.mapperUtil = mapperUtil;
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyDto findById(Long companyId) {
        Company company = companyRepository.findById(companyId).get();
        return mapperUtil.convert(company, new CompanyDto());
    }

}
