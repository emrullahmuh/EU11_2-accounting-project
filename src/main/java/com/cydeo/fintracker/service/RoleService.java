package com.cydeo.fintracker.service;

import com.cydeo.fintracker.dto.RoleDto;

import java.util.List;

public interface RoleService {

    List<RoleDto> listAllRoles();
    RoleDto findById(Long id);
}
