package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.dto.CountryDto;
import com.cydeo.fintracker.dto.UserDto;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.enums.CompanyStatus;
import com.cydeo.fintracker.feignclient.CountryFeignClient;
import com.cydeo.fintracker.repository.CompanyRepository;
import com.cydeo.fintracker.service.CompanyService;

import com.cydeo.fintracker.service.SecurityService;
import com.cydeo.fintracker.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final MapperUtil mapperUtil;
    private final CompanyRepository companyRepository;
    private final SecurityService securityService;
    private final CountryFeignClient countryFeignClient;

    @Value("${API_COUNTRIES_KEY}")
    private String apiCountriesKey;

    @Override
    public CompanyDto findById(Long companyId) {

        Optional<Company> company = companyRepository.findById(companyId);
        CompanyDto companyResponse = mapperUtil.convert(company, new CompanyDto());
        log.info("Company found by id: '{}', '{}'", companyId, companyResponse);

        return companyResponse;

    }

    @Override
    public List<CompanyDto> getCompanies() {

        Sort sort = Sort.by(
                Sort.Order.asc("companyStatus"),
                Sort.Order.asc("title")
        );

        List<Company> companies = companyRepository.getCompanies(sort);

        if (!companies.isEmpty()) {
            return companies.stream()
                    .map(company -> mapperUtil.convert(company, new CompanyDto()))
                    .collect(Collectors.toList());
        }
        return Collections.singletonList(mapperUtil.convert(companies, new CompanyDto()));

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

    @Override
    public BindingResult createUniqueTitle(String title, BindingResult bindingResult) {

        if (companyRepository.existsByTitle(title)){
            bindingResult.addError(new FieldError("newCompany", "title", "This title already exists."));
        }

        return bindingResult;

    }

    @Override
    public BindingResult updateUniqueTitle(CompanyDto companyDto, BindingResult bindingResult) {

        if (companyRepository.existsByTitleAndIdNot(companyDto.getTitle(),companyDto.getId())){
            bindingResult.addError(new FieldError("newCompany", "title", "This title already exists."));
        }

        return bindingResult;

    }


    @Override
    public List<String> getAllCountries() {

        ResponseEntity<List<CountryDto>> apiCountries = countryFeignClient.getApiCountries(apiCountriesKey);

        if (apiCountries.getStatusCode().is2xxSuccessful()) {
            return apiCountries.getBody().stream()
                    .map(CountryDto::getName)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();

    }

    @Override
    public List<CompanyDto> getCompanyDtoByLoggedInUser() {

        UserDto loggedInUser = securityService.getLoggedInUser();

        if(loggedInUser.getRole().getDescription().equals("Root User")){
            List<Company> companies = companyRepository.getAllCompaniesForRoot(loggedInUser.getCompany().getId());
            List<CompanyDto> companyDtoList = companies.stream().map(company -> mapperUtil.convert(company, new CompanyDto()))
                    .collect(Collectors.toList());

            log.info("Companies are retrieved by root user '{}'", companyDtoList.size() );

            return companyDtoList;

        } else {
            Company company = companyRepository.getCompanyForCurrent(loggedInUser.getCompany().getId());
            List<CompanyDto> companyDtoList = Collections.singletonList(mapperUtil.convert(company,new CompanyDto()));

            log.info("Company is retrieved by logged in user '{}'",companyDtoList.size());

            return companyDtoList;
        }
    }

}
