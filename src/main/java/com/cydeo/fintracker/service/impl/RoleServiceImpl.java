package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.RoleDto;
import com.cydeo.fintracker.entity.Role;
import com.cydeo.fintracker.util.MapperUtil;
import com.cydeo.fintracker.util.RoleMapper;
import com.cydeo.fintracker.repository.RoleRepository;
import com.cydeo.fintracker.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final MapperUtil mapperUtil;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper, MapperUtil mapperUtil) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<RoleDto> listAllRoles() {

        List<Role> roleList = roleRepository.findAll();



        return roleList.stream().map(role -> mapperUtil.convert(role, new RoleDto())).collect(Collectors.toList());


    }

    @Override
    public RoleDto findById(Long id) {
        return roleMapper.convertToDto(roleRepository.findById(id).get());
    }
}
