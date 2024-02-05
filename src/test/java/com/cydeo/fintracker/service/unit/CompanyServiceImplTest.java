package com.cydeo.fintracker.service.unit;

import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.repository.CompanyRepository;
import com.cydeo.fintracker.service.impl.CompanyServiceImpl;
import com.cydeo.fintracker.util.MapperUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    @Disabled
    void should_list_all_companies() {

        Company company1 = new Company();
        company1.setId(3L);
        Company company2 = new Company();
        company2.setId(2L);
        Company notIncludedCompany = new Company();
        notIncludedCompany.setId(1L);

        List<Company> companyList = Arrays.asList(company1, company2, notIncludedCompany);

        when(companyRepository.findAll(any(Sort.class))).thenReturn(companyList);

        when(mapperUtil.convert(any(Company.class), any(CompanyDto.class)))
                .thenAnswer(invocation -> {
                    Company sourceCompany = invocation.getArgument(0);
                    CompanyDto targetCompanyDto = new CompanyDto();

                    if (sourceCompany.getId() != 1) {
                        targetCompanyDto.setId(sourceCompany.getId());
                    }

                    return targetCompanyDto;
                });

        List<CompanyDto> result = companyService.getCompanies();

        assertThat(result.size()).isEqualTo(companyList.size() - 1);

        verify(companyRepository).findAll(any(Sort.class));

        verify(mapperUtil, times(companyList.size() - 1))
                .convert(any(Company.class), any(CompanyDto.class));

    }

    @Test
    @Disabled
    void should_activate_company() {

        Company company1 = new Company();
        company1.setId(1L);

        when(companyRepository.findById(company1.getId())).thenReturn(Optional.of(company1));

        companyService.activateCompany(company1.getId());

        verify(companyRepository, times(1)).save(company1);

        verify(mapperUtil, times(1)).convert(eq(company1), any(CompanyDto.class));

    }

    @Test
    @Disabled
    void should_deactivate_company() {

        Company company1 = new Company();
        company1.setId(1L);

        when(companyRepository.findById(company1.getId())).thenReturn(Optional.of(company1));

        companyService.deactivateCompany(company1.getId());

        verify(companyRepository, times(1)).save(company1);

        verify(mapperUtil, times(1)).convert(eq(company1), any(CompanyDto.class));

    }

}