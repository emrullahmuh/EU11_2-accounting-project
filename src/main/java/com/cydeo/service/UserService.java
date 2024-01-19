package com.cydeo.service;


import com.cydeo.dto.UserDto;


public interface UserService {

    UserDto findUserById(Long id);
    UserDto findByUsername(String username);

}
