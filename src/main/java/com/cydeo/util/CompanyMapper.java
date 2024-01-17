package com.cydeo.util;

import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Company;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    private final ModelMapper modelMapper;

    public CompanyMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Company convertToEntity(CompanyDto dto){
        return modelMapper.map(dto,Company.class);
    }

    public CompanyDto convertToDto(Company entity){
        return modelMapper.map(entity,CompanyDto.class);
    }

}
