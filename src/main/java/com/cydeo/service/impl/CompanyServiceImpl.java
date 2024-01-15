package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Company;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<CompanyDto> getCompanyList() {

        List<Company> companies = companyRepository.getCompanyList();

        if (companies.size() !=0){
            return companies.stream()
                    .map(company -> mapperUtil.convert(company,new CompanyDto()))
                    .collect(Collectors.toList());
        }
        return null;

    }

    @Override
    public void createCompany(CompanyDto newCompany) {

        newCompany.setCompanyStatus(CompanyStatus.PASSIVE);

        companyRepository.save(mapperUtil.convert(newCompany,new Company()));

    }

    @Override
    public CompanyDto updateCompany(CompanyDto companyDto) {

        Optional<Company> oldCompany = companyRepository.findById(companyDto.getId());

        if (oldCompany.isPresent()) {
            CompanyStatus oldCompanyStatus = oldCompany.get().getCompanyStatus();
            companyDto.setCompanyStatus(oldCompanyStatus);
            Company savedCompany = companyRepository.save(mapperUtil.convert(companyDto, new Company()));

            return mapperUtil.convert(savedCompany, companyDto);
        }

        return null;

    }

    @Override
    public void activateCompany(Long companyId) {

        Company companyToBeActivate = companyRepository.findById(companyId).get();
        companyToBeActivate.setCompanyStatus(CompanyStatus.ACTIVE);

        companyRepository.save(companyToBeActivate);

    }

    @Override
    public void deactivateCompany(Long companyId) {

        Company companyToBeDeactivate = companyRepository.findById(companyId).get();
        companyToBeDeactivate.setCompanyStatus(CompanyStatus.PASSIVE);

        companyRepository.save(companyToBeDeactivate);
    }

}
