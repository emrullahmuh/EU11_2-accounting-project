package com.cydeo.fintracker.service.unit;

import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.dto.RoleDto;
import com.cydeo.fintracker.dto.UserDto;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.entity.Role;
import com.cydeo.fintracker.entity.User;
import com.cydeo.fintracker.repository.UserRepository;
import com.cydeo.fintracker.service.impl.SecurityServiceImpl;
import com.cydeo.fintracker.service.impl.UserServiceImpl;
import com.cydeo.fintracker.util.MapperUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplUnitTest {

    @Mock
    private MapperUtil mapperUtil;

    @Mock
    private SecurityServiceImpl securityService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void test_findUserById_returnsUserDtoWithSameId() {
        // Arrange
        Long id = 1L;
        User user = User.builder()
                .username("testUser")
                .password("testPassword")
                .firstname("John")
                .lastname("Doe")
                .phone("1234567890")
                .enabled(true)
                .role(Role.builder().description("Admin").build())
                .company(Company.builder().title("Test Company").build())
                .build();
        UserDto expectedUserDto = UserDto.builder()
                .id(id)
                .username("testUser")
                .password("testPassword")
                .firstname("John")
                .lastname("Doe")
                .phone("1234567890")
                .enabled(true)
                .role(RoleDto.builder().description("Admin").build())
                .company(CompanyDto.builder().title("Test Company").build())
                .build();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(mapperUtil.convert(user, new UserDto())).thenReturn(expectedUserDto);

        // Act
        UserDto actualUserDto = userService.findUserById(id);

        // Assert
        assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    void test_saveNewUserWithValidData_returnsUserDtoWithSameData() {
        // Arrange
        UserDto userDto = UserDto.builder()
                .username("testUser")
                .password("testPassword")
                .firstname("John")
                .lastname("Doe")
                .phone("1234567890")
                .role(RoleDto.builder().description("Admin").build())
                .company(CompanyDto.builder().title("Test Company").build())
                .build();
        User user = User.builder()
                .username("testUser")
                .password("testPassword")
                .firstname("John")
                .lastname("Doe")
                .phone("1234567890")
                .enabled(true)
                .role(Role.builder().description("Admin").build())
                .company(Company.builder().title("Test Company").build())
                .build();
        UserDto expectedUserDto = UserDto.builder()
                .id(1L)
                .username("testUser")
                .password("testPassword")
                .firstname("John")
                .lastname("Doe")
                .phone("1234567890")
                .enabled(true)
                .role(RoleDto.builder().description("Admin").build())
                .company(CompanyDto.builder().title("Test Company").build())
                .build();
        when(mapperUtil.convert(userDto, new User())).thenReturn(user);
        when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());
        when(userRepository.save(user)).thenReturn(user);
        when(mapperUtil.convert(user, new UserDto())).thenReturn(expectedUserDto);

        // Act
        UserDto actualUserDto = userService.save(userDto);

        // Assert
        assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    void test_updateUserWithValidData_returnsUpdatedUserDto() {
        // Arrange
        Long userId = 1L;
        UserDto userDto = UserDto.builder()
                .id(userId)
                .username("testuser")
                .password("testpassword")
                .firstname("John")
                .lastname("Doe")
                .phone("+1 (123) 456-7890")
                .role(RoleDto.builder().id(1L).description("Admin").build())
                .company(CompanyDto.builder().id(1L).title("Test Company").build())
                .enabled(true)
                .build();

        User existingUser = User.builder()
                .username("testuser")
                .password("oldpassword")
                .firstname("Jane")
                .lastname("Doe")
                .phone("+1 (987) 654-3210")
                .role(Role.builder().description("Admin").build())
                .company(Company.builder().title("Test Company").build())
                .enabled(true)
                .build();

        when(mapperUtil.convert(userDto, new User())).thenReturn(existingUser);
        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);


        // Act
        UserDto updatedUserDto = userService.update(userDto);

        // Assert
        assertEquals(userDto.getId(), updatedUserDto.getId());
        assertEquals(userDto.getUsername(), updatedUserDto.getUsername());
        assertEquals(userDto.getPassword(), updatedUserDto.getPassword());
        assertEquals(userDto.getFirstname(), updatedUserDto.getFirstname());
        assertEquals(userDto.getLastname(), updatedUserDto.getLastname());
        assertEquals(userDto.getPhone(), updatedUserDto.getPhone());
        assertEquals(userDto.getRole(), updatedUserDto.getRole());
        assertEquals(userDto.getCompany(), updatedUserDto.getCompany());
        assertTrue(updatedUserDto.isEnabled());
    }

    @Test
    void test_deleting_user_sets_isDeleted_flag_to_true() {
        // Arrange
        Long userId = 1L;
        User user = User.builder()
                .username("testUser")
                .password("testPassword")
                .firstname("John")
                .lastname("Doe")
                .phone("1234567890")
                .enabled(true)
                .role(Role.builder().description("Admin").build())
                .company(Company.builder().build())
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.delete(userId);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertTrue(savedUser.getIsDeleted());
    }
}
