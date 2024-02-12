package com.cydeo.fintracker.service.unit;

import com.cydeo.fintracker.dto.RoleDto;
import com.cydeo.fintracker.dto.UserDto;
import com.cydeo.fintracker.entity.Role;
import com.cydeo.fintracker.repository.RoleRepository;
import com.cydeo.fintracker.service.impl.RoleServiceImpl;
import com.cydeo.fintracker.service.impl.SecurityServiceImpl;
import com.cydeo.fintracker.util.MapperUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mapping.MappingException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplUnitTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private MapperUtil mapperUtil;

    @Mock
    private SecurityServiceImpl securityService;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    public void testListAllRoles() {

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
    void test_returns_empty_list_when_repository_has_no_roles() {
        List<Role> roleList = new ArrayList<>();

        when(roleRepository.findAll()).thenReturn(roleList);

        List<RoleDto> result = roleService.listAllRoles();

        verify(roleRepository, times(1)).findAll();
        verify(mapperUtil, never()).convert(any(Role.class), any(RoleDto.class));

        assertTrue(result.isEmpty());
    }

    @Test
    void test_returns_list_of_roles_with_correct_mapping_when_repository_has_large_number_of_roles() {
        List<Role> roleList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Role role = new Role();
            role.setDescription("Role " + i);
            roleList.add(role);
        }

        when(roleRepository.findAll()).thenReturn(roleList);

        when(mapperUtil.convert(any(Role.class), eq(new RoleDto())))
                .thenAnswer(invocation -> {
                    Role inputRole = invocation.getArgument(0);
                    RoleDto outputRoleDto = new RoleDto();
                    outputRoleDto.setDescription(inputRole.getDescription());
                    // Add other mappings as needed
                    return outputRoleDto;
                });

        List<RoleDto> result = roleService.listAllRoles();

        verify(roleRepository, times(1)).findAll();
        verify(mapperUtil, times(1000)).convert(any(Role.class), any(RoleDto.class));

        assertEquals(1000, result.size());
        for (int i = 0; i < 1000; i++) {
            assertEquals("Role " + i, result.get(i).getDescription());
        }
    }

    @Test
    void testFindById() {


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

    @Test
    void testGetAllRolesForLoggedInUserRootUser() {
        // Arrange
        UserDto rootUser = new UserDto();
        rootUser.setRole(new RoleDto(1L, "Root User")); // Assuming RoleDto constructor takes id and description
        when(securityService.getLoggedInUser()).thenReturn(rootUser);

        Role rootRole = new Role(); // Assuming Role class has a default constructor
        when(roleRepository.getAllRoleForRoot()).thenReturn(rootRole);

        RoleDto expectedRoleDto = new RoleDto();
        expectedRoleDto.setId(1L);
        expectedRoleDto.setDescription("Root User");
        when(mapperUtil.convert(rootRole, new RoleDto())).thenReturn(expectedRoleDto);

        // Act
        List<RoleDto> result = roleService.getAllRolesForLoggedInUser();

        // Assert
        assertEquals(Collections.singletonList(expectedRoleDto), result);
    }

    @Test
    void testGetAllRolesForLoggedInUserAdminUser() {
        // Arrange
        UserDto adminUser = new UserDto();
        adminUser.setRole(new RoleDto(2L, "Admin")); // Assuming RoleDto constructor takes id and description
        when(securityService.getLoggedInUser()).thenReturn(adminUser);

        List<Role> adminRoles = Arrays.asList(new Role(), new Role());
        when(roleRepository.getAllRoleForAdmin()).thenReturn(adminRoles);

        RoleDto expectedRoleDto1 = new RoleDto();
        expectedRoleDto1.setId(1L);
        expectedRoleDto1.setDescription("Description1");
        RoleDto expectedRoleDto2 = new RoleDto();
        expectedRoleDto2.setId(2L);
        expectedRoleDto2.setDescription("Description2");
//        when(mapperUtil.convert(adminRoles.get(0), new RoleDto())).thenReturn(expectedRoleDto1);
//        when(mapperUtil.convert(adminRoles.get(1), new RoleDto())).thenReturn(expectedRoleDto2);

        when(mapperUtil.convert(any(Role.class), any(RoleDto.class)))
                .thenReturn(expectedRoleDto1)
                .thenReturn(expectedRoleDto2);
        // Act
        List<RoleDto> result = roleService.getAllRolesForLoggedInUser();

        // Assert
        assertEquals(Arrays.asList(expectedRoleDto1, expectedRoleDto2), result);
    }

    @Test
    void test_getAllRolesForLoggedInUser_NullLoggedInUser() {
        // Arrange
        when(securityService.getLoggedInUser()).thenReturn(null);

        // Act and Assert
        assertThrows(NullPointerException.class, () -> roleService.getAllRolesForLoggedInUser(), "Logged-in user is null");
    }

    @Test
    void test_mapperUtilConvertThrowsMappingException() {
        // Arrange
        UserDto loggedInUser = new UserDto();
        loggedInUser.setRole(new RoleDto(1L, "Root User")); // Assuming RoleDto constructor takes id and description
        when(securityService.getLoggedInUser()).thenReturn(loggedInUser);

        Role rootRole = new Role(); // Assuming Role class has a default constructor
        when(roleRepository.getAllRoleForRoot()).thenReturn(rootRole);

        when(mapperUtil.convert(rootRole, new RoleDto())).thenThrow(new MappingException("Error converting Role to RoleDto"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> roleService.getAllRolesForLoggedInUser(), "Error converting Role to RoleDto");
    }



}
