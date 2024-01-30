package com.cydeo.fintracker.service.impl;


import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.dto.UserDto;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.entity.User;
import com.cydeo.fintracker.exception.UserNotFoundException;
import com.cydeo.fintracker.repository.UserRepository;
import com.cydeo.fintracker.service.CompanyService;
import com.cydeo.fintracker.service.UserService;
import com.cydeo.fintracker.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final PasswordEncoder passwordEncoder;

    private final CompanyService companyService;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil, PasswordEncoder passwordEncoder, @Lazy CompanyService companyService) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.passwordEncoder = passwordEncoder;
        this.companyService = companyService;
    }

    @Override
    public UserDto findUserById(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found."));

        log.info(String.valueOf(user));

        return mapperUtil.convert(user, new UserDto());
    }


    @Override
    public UserDto findByUsername(String username) {

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UserNotFoundException("There is no user with given username: " + username);
        }

        User storedUser = user.get();
        log.info("User found by username : '{}'", storedUser.getUsername());
        return mapperUtil.convert(user, new UserDto());
    }






    @Override
    public UserDto save(UserDto userDto) {


        User user = mapperUtil.convert(userDto, new User());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setEnabled(true);
        User storedUser = userRepository.save(user);

        log.info("User has been created with username: '{}'", storedUser.getUsername());

        return mapperUtil.convert(storedUser, new UserDto()); }


    @Override
    public UserDto update(UserDto user) {
        User storedUser = userRepository.findByUsername(user.getUsername()).get();

        User convertedUser = mapperUtil.convert(user, new User());

        convertedUser.setId(storedUser.getId());

        userRepository.save(convertedUser);

        return findByUsername(user.getUsername());
    }

    private boolean isOnlyAdmin(UserDto userDTO) {
        User user = mapperUtil.convert(userDTO, new User());
        Integer userOnlyAdmin = userRepository.isUserOnlyAdmin(user.getCompany(), user.getRole());
        return userOnlyAdmin == 1;

    }
    public List<UserDto> listAllUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + auth.getName()));

        if (!"Root User".equals(loggedInUser.getRole().getDescription())) {
            CompanyDto companyDto = companyService.findById(loggedInUser.getCompany().getId());
            List<User> userList = userRepository.findAllUserWithCompanyAndIsDeleted(
                    mapperUtil.convert(companyDto, new Company()), false);
            return userList.stream()
                    .map(user -> mapperUtil.convert(user, new UserDto()))
                    .collect(Collectors.toList());

        } else {
            List<User> userList = userRepository.findAllAdminRole("Admin", false);
            return userList.stream()
                    .map(user -> mapperUtil.convert(user, new UserDto()))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).get();
        user.setIsDeleted(true);
        user.setUsername(user.getUsername() + " deleted" + user.getId());
        userRepository.save(user);
    }

}


