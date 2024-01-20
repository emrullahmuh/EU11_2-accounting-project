package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.repository.CompanyRepository;
import com.cydeo.fintracker.service.CompanyService;
import com.cydeo.fintracker.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final MapperUtil mapperUtil;
    private final CompanyRepository companyRepository;

    @Override
    public List<CompanyDto> getCompanies() {

      Sort sort = Sort.by(
                Sort.Order.asc("companyStatus"),
                Sort.Order.asc("title")
        );

        List<Company> companyList = companyRepository.findAll(sort);

        return companyList.stream()
                .filter(company -> company.getId() != 1)
                .map(company -> mapperUtil.convert(company, new CompanyDto()))
                .collect(Collectors.toList());

    }

    @Override
    public CompanyDto findById(Long companyId) {

        Optional company = companyRepository.findById(companyId);
        CompanyDto companyResponse = mapperUtil.convert(company, new CompanyDto());
        log.info("Company found by id: '{}', '{}'", companyId, companyResponse);

        return companyResponse;
    }

}