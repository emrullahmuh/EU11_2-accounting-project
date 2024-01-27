package com.cydeo.fintracker.service.impl;


import com.cydeo.fintracker.dto.UserDto;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.entity.User;
import com.cydeo.fintracker.exception.CompanyNotFoundException;
import com.cydeo.fintracker.exception.UserNotFoundException;
import com.cydeo.fintracker.repository.CompanyRepository;
import com.cydeo.fintracker.repository.UserRepository;
import com.cydeo.fintracker.service.UserService;
import com.cydeo.fintracker.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final CompanyRepository companyRepository;


    @Override
    public UserDto findUserById(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found."));

        log.info(String.valueOf(user));

        return mapperUtil.convert(user, new UserDto());
    }

    @Override
    public UserDto findByUsername(String username) {

        User user = userRepository.findByUsername(username);
        return mapperUtil.convert(user, new UserDto());
    }


    @Override
    public List<UserDto> listAllUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByUsername(auth.getName());

        if (LoggedInUser.getId() != 1) {
            Company company = companyRepository
                    .findById(LoggedInUser.getCompany().getId())
                    .orElseThrow(() -> new CompanyNotFoundException("Company can't found with id " + LoggedInUser.getCompany().getId()));

            List<User> userList = userRepository.findAllUserWithCompanyAndIsDeleted(company, false);
            return userList.stream().map(user -> mapperUtil.convert(user, new UserDto())).
                    collect(Collectors.toList());
        } else {
            List<User> userList = userRepository.findAllAdminRole("Admin",false);
            return userList.stream()
                    .map(user -> mapperUtil.convert(user, new UserDto()))
                    .peek(dto -> dto.setOnlyAdmin(isOnlyAdmin(dto)))
                    .collect(Collectors.toList());
        }
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
        User storedUser = userRepository.findByUsername(user.getUsername());

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






    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).get();
        user.setIsDeleted(true);
        user.setUsername(user.getUsername() + " deleted" + user.getId());
        userRepository.save(user);
    }

}

