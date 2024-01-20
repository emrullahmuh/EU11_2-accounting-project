package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.RoleDto;
import com.cydeo.fintracker.entity.Role;
import com.cydeo.fintracker.util.MapperUtil;
import com.cydeo.fintracker.repository.RoleRepository;
import com.cydeo.fintracker.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;

    @Override
    public List<RoleDto> listAllRoles() {

        List<Role> roleList = roleRepository.findAll();



        return roleList.stream().map(role -> mapperUtil.convert(role, new RoleDto())).collect(Collectors.toList());


    }

    @Override
    public RoleDto findById(Long id) {
        return mapperUtil.convert(roleRepository.findById(id).get(),new RoleDto());
    }
}
