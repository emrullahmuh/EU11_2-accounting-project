package com.cydeo.converter;

import com.cydeo.dto.CompanyDto;
import com.cydeo.service.CompanyService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CompanyDtoConverter implements Converter<String, CompanyDto> {

    private final CompanyService companyService;

    public CompanyDtoConverter(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public CompanyDto convert(String id) {

        if (id == null || id.equals("")) {
            return null;
        }

        return companyService.findById(Long.parseLong(id));

    }

}
