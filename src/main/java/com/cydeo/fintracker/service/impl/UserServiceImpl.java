package com.cydeo.fintracker.service.impl;


import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.dto.UserDto;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.entity.User;
import com.cydeo.fintracker.exception.CompanyNotFoundException;
import com.cydeo.fintracker.exception.UserNotFoundException;
import com.cydeo.fintracker.repository.CompanyRepository;
import com.cydeo.fintracker.repository.UserRepository;
import com.cydeo.fintracker.service.CompanyService;
import com.cydeo.fintracker.service.UserService;
import com.cydeo.fintracker.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final CompanyService companyService;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil, @Lazy CompanyService companyService) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
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
        User storedUser = userRepository.save(user);
        log.info("User has been created with username: '{}'", storedUser.getUsername());
        return mapperUtil.convert(user, new UserDto());
    }


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

        if ("Root".equals(loggedInUser.getRole().getDescription())) {
            // Root User can list only admins of all companies
            return userRepository.findAllAdminRole("Admin", false).stream()
                    .map(user -> {
                        UserDto userDto = mapperUtil.convert(user, new UserDto());
                        userDto.setOnlyAdmin(isOnlyAdmin(userDto));
                        return userDto;
                    })
                    .collect(Collectors.toList());
        } else {
            // Admin can only see his/her company's users
            return null;
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


