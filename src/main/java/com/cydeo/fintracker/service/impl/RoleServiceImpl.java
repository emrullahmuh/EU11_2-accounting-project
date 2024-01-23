package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.RoleDto;
import com.cydeo.fintracker.entity.Role;
import com.cydeo.fintracker.exception.RoleNotFoundException;
import com.cydeo.fintracker.util.MapperUtil;
import com.cydeo.fintracker.repository.RoleRepository;
import com.cydeo.fintracker.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;

    @Override
    public List<RoleDto> listAllRoles() {

        List<Role> roleList = roleRepository.findAll();

        log.info("List of roles: '{}'",roleList.toString());

        return roleList.stream().map(role -> mapperUtil.convert(role, new RoleDto())).collect(Collectors.toList());


    }

    @Override
    public RoleDto findById(Long id) {

        Role role = roleRepository.findById(id).orElseThrow(()-> new RoleNotFoundException("Role not found."));

        log.info("Role found by id : '{}', '{}' ", id, role);

        return mapperUtil.convert(role,new RoleDto());
    }
}
