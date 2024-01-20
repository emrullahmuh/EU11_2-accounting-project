package com.cydeo.fintracker.service;


import com.cydeo.fintracker.dto.UserDto;


public interface UserService {

    UserDto findUserById(Long id);

    UserDto findByUsername(String username);

}
