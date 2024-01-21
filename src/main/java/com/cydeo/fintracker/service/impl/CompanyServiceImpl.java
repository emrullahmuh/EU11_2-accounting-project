package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.enums.CompanyStatus;
import com.cydeo.fintracker.repository.CompanyRepository;
import com.cydeo.fintracker.service.CompanyService;

import com.cydeo.fintracker.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Override
    public CompanyDto createCompany(CompanyDto newCompany) {

        newCompany.setCompanyStatus(CompanyStatus.PASSIVE);

        Company companyToBeSave = companyRepository.save(mapperUtil.convert(newCompany, new Company()));
        log.info("Company saved with title: '{}'", companyToBeSave.getTitle());

        CompanyDto createdCompany = mapperUtil.convert(companyToBeSave, new CompanyDto());
        log.info("Company created with title: '{}'", createdCompany.getTitle());

        return createdCompany;

    }

    @Override
    public CompanyDto updateCompany(CompanyDto companyDto) {

        Optional<Company> oldCompany = companyRepository.findById(companyDto.getId());

        if (oldCompany.isEmpty()) {
            throw new NoSuchElementException("Company not exist with title: " + companyDto.getTitle());
        }

        Company company = oldCompany.get();
        log.info("Company data will be updated : '{}'", company);

        CompanyStatus oldCompanyStatus = company.getCompanyStatus();
        companyDto.setCompanyStatus(oldCompanyStatus);
        Company savedCompany = companyRepository.save(mapperUtil.convert(companyDto, new Company()));

        CompanyDto updatedCompany = mapperUtil.convert(savedCompany, companyDto);
        log.info("Company updated for company '{}', '{}': ", updatedCompany.getTitle(), updatedCompany);

        return updatedCompany;

    }

    @Override
    public void activateCompany(Long companyId) {

        Company companyToBeActivate = companyRepository.findById(companyId).get();

        companyToBeActivate.setCompanyStatus(CompanyStatus.ACTIVE);
        log.info("Company status has changed to 'Activated' : '{}'", companyToBeActivate);

        companyRepository.save(companyToBeActivate);
        log.info("Company status has changed: '{}'", companyToBeActivate.getCompanyStatus());

    }

    @Override
    public void deactivateCompany(Long companyId) {

        Company companyToBeDeactivate = companyRepository.findById(companyId).get();

        companyToBeDeactivate.setCompanyStatus(CompanyStatus.PASSIVE);
        log.info("Company status has changed to 'Deactivated' : '{}'", companyToBeDeactivate);

        companyRepository.save(companyToBeDeactivate);
        log.info("Company status has changed: '{}'", companyToBeDeactivate.getCompanyStatus());

    }

}