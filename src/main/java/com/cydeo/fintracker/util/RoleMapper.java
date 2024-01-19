package com.cydeo.fintracker.util;

import com.cydeo.fintracker.dto.RoleDto;
import com.cydeo.fintracker.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

//We create mapper package and RoleMapper class to use as mapper for Role entities and DTOs.
@Component
public class RoleMapper {

    private ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Role convertToEntity(RoleDto dto){
        return modelMapper.map(dto,Role.class);

    }

    public RoleDto convertToDto(Role entity){
        return modelMapper.map(entity,RoleDto.class);
    }
}
