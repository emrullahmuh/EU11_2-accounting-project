package com.cydeo.converter;

import com.cydeo.dto.CompanyDto;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CompanyDtoConverter implements Converter<String, CompanyDto> {

    private final CompanyRepository companyRepository;

    public CompanyDtoConverter(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyDto convert(String source) {

        if (source == null || source.equals("")) {
            return null;
        }

        return companyRepository.findByTitle(source);

    }


}