package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.repository.CompanyRepository;
import com.cydeo.fintracker.util.MapperUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private MapperUtil mapperUtil;

    @InjectMocks
    private CompanyServiceImpl companyService;


    @Test
    void should_list_all_companies() {

        // Sample company
        Company company1 = new Company();
        company1.setId(3L);
        Company company2 = new Company();
        company2.setId(2L);
        Company notIncludedCompany = new Company();
        notIncludedCompany.setId(1L);

        List<Company> companyList = Arrays.asList(company1, company2, notIncludedCompany);

        //Behavior of the companyRepository
        when(companyRepository.findAll(any(Sort.class))).thenReturn(companyList);

        //Behavior of the mapper
        when(mapperUtil.convert(any(Company.class), any(CompanyDto.class)))
                .thenAnswer(invocation -> {
                    Company sourceCompany = invocation.getArgument(0);
                    CompanyDto targetCompanyDto = new CompanyDto();

                    // if companyId is 1 it should not be included
                    if (sourceCompany.getId() != 1) {
                        targetCompanyDto.setId(sourceCompany.getId());
                    }

                    return targetCompanyDto;
                });

        List<CompanyDto> result = companyService.getCompanies();

        //Verifies that the expected result matches excluding company with ID 1
        assertThat(result.size()).isEqualTo(companyList.size() - 1);

        //Verifies that company repository method called and sorted
        verify(companyRepository).findAll(any(Sort.class));

        //Verifies that company mapper method called for each company in the list
        verify(mapperUtil, times(companyList.size() - 1))
                .convert(any(Company.class), any(CompanyDto.class));

    }
}