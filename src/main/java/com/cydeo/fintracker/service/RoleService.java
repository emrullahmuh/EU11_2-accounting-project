package com.cydeo.fintracker.service;

import com.cydeo.fintracker.dto.RoleDto;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<RoleDto> listAllRoles();
    RoleDto findById(Long id);
}
