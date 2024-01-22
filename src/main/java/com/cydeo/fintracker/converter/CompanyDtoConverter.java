package com.cydeo.fintracker.converter;

import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.service.CompanyService;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CompanyDtoConverter implements Converter<String, CompanyDto> {

    private final CompanyService companyService;

    public CompanyDtoConverter(@Lazy CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public CompanyDto convert(String id) {

        if (id.isBlank()) {
            return null;
        }

        return companyService.findById(Long.parseLong(id));

    }


}
