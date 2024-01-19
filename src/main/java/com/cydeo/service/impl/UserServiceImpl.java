package com.cydeo.service.impl;


import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import com.cydeo.service.UserService;
import com.cydeo.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityService securityService;
    private final MapperUtil mapperUtil;


    @Override
    public UserDto findUserById(Long id) {
        return null;
    }

    @Override
    public UserDto findByUsername(String username) {

        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new NoSuchElementException("There is no user with given username");
        }
        return mapperUtil.convert(user, new UserDto());
    }
}
