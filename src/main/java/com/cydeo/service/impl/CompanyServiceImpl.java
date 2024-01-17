package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.entity.common.UserPrincipal;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.CompanyService;

import com.cydeo.util.CompanyMapper;
import com.cydeo.util.MapperUtil;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyMapper companyMapper;
    private final MapperUtil mapperUtil;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    public UserDetails userDetails;
    public CompanyServiceImpl(CompanyMapper companyMapper, MapperUtil mapperUtil, CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyMapper = companyMapper;
        this.mapperUtil = mapperUtil;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CompanyDto> listAllCompanies() {

      Sort sort = Sort.by(
                Sort.Order.asc("companyStatus"),
                Sort.Order.asc("title")
        );

        List<Company> companyList = companyRepository.findAll(sort);

        return companyList.stream()
                .filter(company -> company.getId() != 1)
                .map(companyMapper::convertToDto)
                .collect(Collectors.toList());

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

        /*User user = userRepository.findByCompanyId(companyId);
        if (userDetails.isAccountNonLocked() != true){          //Need more details. it gives error right now.
            user.setEnabled(true);
        }*/

        companyRepository.save(companyToBeActivate);
        //userRepository.save(user);
    }

    @Override
    public void deactivateCompany(Long companyId) {

        Company companyToBeDeactivate = companyRepository.findById(companyId).get();
        companyToBeDeactivate.setCompanyStatus(CompanyStatus.PASSIVE);

        /*User user = userRepository.findByCompanyId(companyId);

        if (userDetails.isAccountNonLocked() == true){           //Need more details. It gives error.
            user.setEnabled(false);
        }*/

        companyRepository.save(companyToBeDeactivate);
        //userRepository.save(user);
    }

}