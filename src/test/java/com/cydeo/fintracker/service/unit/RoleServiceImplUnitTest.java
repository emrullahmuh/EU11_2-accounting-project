package com.cydeo.fintracker.service.unit;

import com.cydeo.fintracker.dto.RoleDto;
import com.cydeo.fintracker.entity.Role;
import com.cydeo.fintracker.repository.RoleRepository;
import com.cydeo.fintracker.service.impl.RoleServiceImpl;
import com.cydeo.fintracker.util.MapperUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplUnitTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private MapperUtil mapperUtil;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    public void testListAllRoles(){

        Role role = new Role();
        Role role1 = new Role();

        List<Role> roleList = new ArrayList<>();

        roleList.add(role);
        roleList.add(role1);

        when(roleRepository.findAll()).thenReturn(roleList);

        RoleDto roleDto1 = new RoleDto();
        RoleDto roleDto2 = new RoleDto();

        when(mapperUtil.convert(any(Role.class), any(RoleDto.class))).thenReturn(roleDto1, roleDto2);

        List<RoleDto> result = roleService.listAllRoles();

        verify(roleRepository, times(1)).findAll();
        verify(mapperUtil, times(2)).convert(any(Role.class), any(RoleDto.class));

        assertEquals(2, result.size());



    }

    @Test
    public void testFindById() {


        Long roleId = 1L;
        Role mockRole = new Role();
        RoleDto mockRoleDto = new RoleDto();

        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(mockRole));

        when(mapperUtil.convert(any(Role.class), any(RoleDto.class))).thenReturn(mockRoleDto);

        RoleDto result = roleService.findById(roleId);

        verify(roleRepository, times(1)).findById(roleId);
        verify(mapperUtil, times(1)).convert(mockRole, new RoleDto());

        assertEquals(mockRoleDto, result);

    }


}
