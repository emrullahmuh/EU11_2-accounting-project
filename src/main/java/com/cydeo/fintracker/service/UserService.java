package com.cydeo.fintracker.service;


import com.cydeo.fintracker.dto.UserDto;

import java.util.List;


public interface UserService {

    UserDto findUserById(Long id);

    UserDto findByUsername(String username);

    List<UserDto> listAllUsers();

    UserDto save(UserDto user);

    UserDto update(UserDto user);

    void delete(Long id);
}
